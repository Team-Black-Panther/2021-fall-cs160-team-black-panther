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
import net.tbp.interval.backup.Task;
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

	@Autowired
	private TaskRepository taskRepo;

	// Basic User Management
	@GetMapping(path = "/{username}")
	public @ResponseBody ResponseEntity<Integer> getUID(@PathVariable String username) {
		return new ResponseEntity<Integer>(userRepo.findProfileByName(username).getId(), HttpStatus.OK);
	}

	@PostMapping(path = "/register")
	public @ResponseBody ResponseEntity<Integer> registerUser(@RequestBody UserProfile newProfile) {
		userRepo.save(newProfile);
		newProfile.setId(userRepo.findProfileByName(newProfile.getUserName()).getId());
		return new ResponseEntity<Integer>(newProfile.getId(), HttpStatus.ACCEPTED);
	}

	@PutMapping(path = "/{uid}")
	public @ResponseBody ResponseEntity<String> updateUserName(@PathVariable Integer uid,
			@RequestBody String newUserName) {
		Optional<UserProfile> profile = userRepo.findById(uid);
		if (profile.isPresent()) {
			profile.get().setUserName(newUserName);
			return new ResponseEntity<String>(newUserName, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping(path = "/{uid}")
	public @ResponseBody ResponseEntity<Integer> deleteUser(@PathVariable Integer uid) {
		userRepo.deleteById(uid);
		return new ResponseEntity<Integer>(uid, HttpStatus.ACCEPTED);
	}

	// Current Events
	@GetMapping(path = "/{uid}/currentevent")
	public @ResponseBody ResponseEntity<Iterable<Event>> getAllCurrentEvents(@PathVariable Integer uid) {
		return new ResponseEntity<Iterable<Event>>(currentEventRepo.findAllCurrentEvents(uid), HttpStatus.OK);
	}

	@PostMapping(path = "/{uid}/currentevent")
	public @ResponseBody ResponseEntity<Event> addNewCurrentEvent(@RequestBody Event newEvent,
			@PathVariable Integer uid) {
		newEvent.setOwner(uid);
		currentEventRepo.save(newEvent);
		return new ResponseEntity<Event>(newEvent, HttpStatus.ACCEPTED);
	}

	@PutMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody ResponseEntity<Event> updateCurrentEvent(@RequestBody Event newEvent,
			@PathVariable Integer uid, @PathVariable Integer eventid) {
		currentEventRepo.deleteById(eventid);
		newEvent.setOwner(uid);
		currentEventRepo.save(newEvent);
		return new ResponseEntity<Event>(newEvent, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody ResponseEntity<Integer> deleteCurrentEvent(@PathVariable Integer uid,
			@PathVariable Integer eventid) {
		currentEventRepo.deleteById(eventid);
		return new ResponseEntity<Integer>(eventid, HttpStatus.ACCEPTED);
	}

	// Active Tasks
	@GetMapping(path = "/{uid}/task")
	public @ResponseBody ResponseEntity<Iterable<Task>> getAllActiveTasks(@PathVariable Integer uid) {
		return new ResponseEntity<Iterable<Task>>(taskRepo.findAllActiveTasks(uid), HttpStatus.OK);
	}

	@PostMapping(path = "/{uid}/task")
	public @ResponseBody ResponseEntity<Task> addNewActiveTask(@RequestBody Task newTask, @PathVariable Integer uid) {
		newTask.setOwner(uid);
		taskRepo.save(newTask);
		return new ResponseEntity<Task>(newTask, HttpStatus.ACCEPTED);
	}

	@PutMapping(path = "/{uid}/task/{taskid}")
	public @ResponseBody ResponseEntity<Task> updateActiveTask(@RequestBody Task newTask, @PathVariable Integer uid,
			@PathVariable Integer taskid) {
		taskRepo.deleteById(taskid);
		newTask.setOwner(uid);
		taskRepo.save(newTask);
		return new ResponseEntity<Task>(newTask, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(path = "/{uid}/task/{taskid}")
	public @ResponseBody ResponseEntity<Integer> deleteActiveTask(@PathVariable Integer uid,
			@PathVariable Integer taskid) {
		taskRepo.deleteById(taskid);
		return new ResponseEntity<Integer>(taskid, HttpStatus.ACCEPTED);
	}

}
