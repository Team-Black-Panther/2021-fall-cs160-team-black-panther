package net.tbp.interval.database;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		for (UserProfile p : userRepo.findAll()) {
			if (p.getUserName().equals(username)) {
				return p.getId();
			}
		}
		return null;
	}

	@PostMapping(path = "/{username}")
	public @ResponseBody String allocateUIDToUser(@PathVariable String username) {
		UserProfile newProfile = new UserProfile();
		newProfile.setUserName(username);
		userRepo.save(newProfile);
		return username;
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
	public @ResponseBody void addNewUser(@PathVariable Integer uid) {
		userRepo.deleteById(uid);
	}

	// Current Events

	@GetMapping(path = "/{uid}/currentevent")
	public @ResponseBody Iterable<Event> getAllCurrentEvents(@PathVariable Integer uid) {
		return currentEventRepo.findAllCurrentEvents(uid);
	}

	@PostMapping(path = "/{uid}/currentevent")
	public @ResponseBody Event addNewCurrentEvent(@RequestBody Event newEvent, @PathVariable Integer uid) {
		currentEventRepo.addNewCurrentEvent(uid, newEvent);
		return newEvent;
	}

	@PutMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody Event updateCurrentEvent(@RequestBody Event newEvent, @PathVariable Integer uid,
			@PathVariable Integer eventid) {
		// perhaps slower than overwriting attributes?
		currentEventRepo.deleteCurrentEvent(uid, eventid);
		currentEventRepo.addNewCurrentEvent(uid, newEvent);
		return newEvent;
	}

	@DeleteMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody void deleteCurrentEvent(@PathVariable Integer uid, @PathVariable Integer eventid) {
		currentEventRepo.deleteCurrentEvent(uid, eventid);
	}
}
