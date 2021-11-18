package net.tbp.interval.database;

import java.util.Optional;
import java.util.regex.Pattern;

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

	// helper function that checks if the username is valid
	private static boolean isUsernameValid(String username) {
		return Pattern.matches("^[a-z]+[0-9]+$", username) && username.length() <= 45 && username.length() > 2;
	}

	// helper function that checks if the uid is valid
	private static boolean isUIDValid(Integer uid) {
		return uid > 0;
	}

	// check if this object ID (task, event, reminder, etc.) is valid
	private static boolean isObjIDValid(Integer objID) {
		return objID > 0;
	}

	// Basic User Management
	@GetMapping(path = "/{username}")
	public @ResponseBody ResponseEntity<Integer> getUID(@PathVariable String username) {
		if (!isUsernameValid(username)) {
			// invalid username
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
		UserProfile target = userRepo.findProfileByName(username);
		if (target != null && !target.getUserName().equals("")) {
			return new ResponseEntity<Integer>(target.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/register")
	public @ResponseBody ResponseEntity<Integer> registerUser(@RequestBody UserProfile newProfile) {
		String profileName = newProfile.getUserName();
		System.out.println(profileName);
		if (!isUsernameValid(profileName)) {
			// invalid username
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		} else if (userRepo.findProfileByName(profileName) != null) {
			// username already used
			return new ResponseEntity<Integer>(HttpStatus.CONFLICT);
		} else {
			UserProfile createdProfile = userRepo.save(newProfile);
			return new ResponseEntity<Integer>(createdProfile.getId(), HttpStatus.ACCEPTED);
		}

	}

	@PutMapping(path = "/{uid}")
	public @ResponseBody ResponseEntity<String> updateUserName(@PathVariable Integer uid,
			@RequestBody String newUserName) {
		if (!isUIDValid(uid)) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		Optional<UserProfile> profile = userRepo.findById(uid);
		if (!profile.isPresent()) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		} else if (userRepo.findProfileByName(newUserName) != null) {
			// username already used
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		} else {
			profile.get().setUserName(newUserName);
			return new ResponseEntity<String>(newUserName, HttpStatus.ACCEPTED);
		}

	}

	@DeleteMapping(path = "/{uid}")
	public @ResponseBody ResponseEntity<Integer> deleteUser(@PathVariable Integer uid) {
		if (!isUIDValid(uid)) {
			// disallow uid access less than or equal to 1
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
		Optional<UserProfile> target = userRepo.findById(uid);
		if (target.isPresent()) {
			userRepo.deleteById(uid);
			return new ResponseEntity<Integer>(uid, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
	}

	// Current Events
	@GetMapping(path = "/{uid}/currentevent")
	public @ResponseBody ResponseEntity<Iterable<Event>> getAllCurrentEvents(@PathVariable Integer uid) {
		if (!isUIDValid(uid)) {
			return new ResponseEntity<Iterable<Event>>(HttpStatus.BAD_REQUEST);
		} else if (!userRepo.findById(uid).isPresent()) {
			// UID not in use
			return new ResponseEntity<Iterable<Event>>(HttpStatus.NOT_FOUND);
		} else {

			return new ResponseEntity<Iterable<Event>>(currentEventRepo.findAllCurrentEvents(uid), HttpStatus.OK);
		}
	}

	@PostMapping(path = "/{uid}/currentevent")
	public @ResponseBody ResponseEntity<Integer> addNewCurrentEvent(@RequestBody Event newEvent,
			@PathVariable Integer uid) {
		if (!isUIDValid(uid)) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		} else if (!userRepo.findById(uid).isPresent()) {
			// UID not in use
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		} else {
			newEvent.setOwner(uid);
			Integer newId = currentEventRepo.save(newEvent).getId();
			return new ResponseEntity<Integer>(newId, HttpStatus.ACCEPTED);
		}
	}

	@PutMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody ResponseEntity<Integer> updateCurrentEvent(@RequestBody Event newEvent,
			@PathVariable Integer uid, @PathVariable Integer eventid) {
		if (!isUIDValid(uid) || !isObjIDValid(eventid)) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		} else if (!userRepo.findById(uid).isPresent()) {
			// UID not in use
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		} else {
			Optional<Event> oldEventOp = currentEventRepo.findById(eventid);
			if (!oldEventOp.isPresent()) {
				return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
			}
			Event oldEvent = oldEventOp.get();
			oldEvent.updateAttributes(newEvent);
			currentEventRepo.save(oldEvent);
			return new ResponseEntity<Integer>(HttpStatus.ACCEPTED);
		}
	}

	@DeleteMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody ResponseEntity<Integer> deleteCurrentEvent(@PathVariable Integer uid,
			@PathVariable Integer eventid) {
		if (!isUIDValid(uid) || !isObjIDValid(eventid)) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		} else if (!userRepo.findById(uid).isPresent()) {
			// UID not in use
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		} else {
			Optional<Event> target = currentEventRepo.findById(eventid);
			if (target.isPresent()) {
				currentEventRepo.deleteById(eventid);
				return new ResponseEntity<Integer>(eventid, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
			}
		}
	}

	// Active Tasks
	@GetMapping(path = "/{uid}/task")
	public @ResponseBody ResponseEntity<Iterable<Task>> getAllActiveTasks(@PathVariable Integer uid) {
		if (!isUIDValid(uid)) {
			return new ResponseEntity<Iterable<Task>>(HttpStatus.BAD_REQUEST);
		} else if (!userRepo.findById(uid).isPresent()) {
			return new ResponseEntity<Iterable<Task>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Iterable<Task>>(taskRepo.findAllActiveTasks(uid), HttpStatus.OK);
		}

	}

	@PostMapping(path = "/{uid}/task")
	public @ResponseBody ResponseEntity<Integer> addNewActiveTask(@RequestBody Task newTask,
			@PathVariable Integer uid) {
		if (!isUIDValid(uid)) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		} else if (!userRepo.findById(uid).isPresent()) {
			// UID not in use
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		} else {
			newTask.setOwner(uid);
			Integer newId = taskRepo.save(newTask).getId();
			return new ResponseEntity<Integer>(newId, HttpStatus.ACCEPTED);
		}
	}

	@PutMapping(path = "/{uid}/task/{taskid}")
	public @ResponseBody ResponseEntity<Integer> updateActiveTask(@RequestBody Task newTask, @PathVariable Integer uid,
			@PathVariable Integer taskid) {
		if (!isUIDValid(uid) || !isObjIDValid(taskid)) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		} else if (!userRepo.findById(uid).isPresent()) {
			// UID not in use
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		} else {
			Optional<Task> oldEventOp = taskRepo.findById(taskid);
			if (!oldEventOp.isPresent()) {
				return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
			}
			Task oldTask = oldEventOp.get();
			oldTask.updateAttributes(newTask);
			taskRepo.save(oldTask);
			return new ResponseEntity<Integer>(HttpStatus.ACCEPTED);
		}
	}

	@DeleteMapping(path = "/{uid}/task/{taskid}")
	public @ResponseBody ResponseEntity<Integer> deleteActiveTask(@PathVariable Integer uid,
			@PathVariable Integer taskid) {
		if (!isUIDValid(uid) || !isObjIDValid(taskid)) {
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		} else if (!userRepo.findById(uid).isPresent()) {
			// UID not in use
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		} else {
			Optional<Task> target = taskRepo.findById(taskid);
			if (target.isPresent()) {
				taskRepo.deleteById(taskid);
				return new ResponseEntity<Integer>(taskid, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
			}
		}
	}

}
