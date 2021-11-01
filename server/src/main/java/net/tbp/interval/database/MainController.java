package net.tbp.interval.database;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.tbp.interval.backup.Event;
import net.tbp.interval.backup.UserProfile;

/**
 * Contains all core HTTP call handlers.
 * 
 * @author Hugo Wong
 *
 */
@Controller
@RequestMapping(path = "/user")
public class MainController {
	@Autowired
	private EventRepository currentEventRepo;

	@Autowired
	private UserRepository userRepo;

	// Basic User Management
	@GetMapping(path = "/{username}")
	public @ResponseBody Integer getUID(@PathVariable String username) {
		return userRepo.findProfileByName(username).getId();
	}

	@PostMapping(path = "/register")
	public @ResponseBody ResponseEntity<Integer> registerUser(@RequestBody UserProfile newProfile) {
		userRepo.save(newProfile);
		newProfile.setId(userRepo.findProfileByName(newProfile.getUserName()).getId());
		return new ResponseEntity<Integer>(newProfile.getId(), HttpStatus.OK);
	}

	@PutMapping(path = "/{uid}")
	public @ResponseBody String updateUserName(@PathVariable Integer uid, @RequestBody String newUserName) {
		Optional<UserProfile> profile = userRepo.findById(uid);
		if (profile.isPresent()) {
			profile.get().setUserName(newUserName);
			return newUserName;
		} else {
			return null;
		}

	}

	@DeleteMapping(path = "/{uid}")
	public @ResponseBody void deleteUser(@PathVariable Integer uid) {
		userRepo.deleteById(uid);
	}

	// Current Events

	@GetMapping(path = "/{uid}/currentevent")
	public @ResponseBody Iterable<Event> getAllCurrentEvents(@PathVariable Integer uid) {
		return currentEventRepo.findAllCurrentEvents(uid);
	}

	@PostMapping(path = "/{uid}/currentevent")
	public @ResponseBody ResponseEntity<Event> addNewCurrentEvent(@RequestBody Event newEvent, @PathVariable Integer uid) {
		newEvent.setOwner(uid);
		currentEventRepo.save(newEvent);
		return new ResponseEntity<Event>(newEvent, HttpStatus.OK);
	}

	@PutMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody ResponseEntity<Event> updateCurrentEvent(@RequestBody Event newEvent, @PathVariable Integer uid,
			@PathVariable Integer eventid) {
		currentEventRepo.deleteById(eventid);
		newEvent.setOwner(uid);
		currentEventRepo.save(newEvent);
		return new ResponseEntity<Event>(newEvent, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody void deleteCurrentEvent(@PathVariable Integer uid, @PathVariable Integer eventid) {
		currentEventRepo.deleteById(eventid);
	}

}
