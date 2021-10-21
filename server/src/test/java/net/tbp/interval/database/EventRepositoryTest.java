package net.tbp.interval.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import net.tbp.interval.backup.Event;
import net.tbp.interval.backup.UserProfile;

@DataJpaTest
public class EventRepositoryTest {
	@Autowired
	private EventRepository eventRepo;

	private UserProfile testProfile1, testProfile2;
	private Event testEvent1, testEvent2, testEvent3;

	@BeforeEach
	void setUp() {
		testProfile1 = new UserProfile(1, "John", "Doe", "jd123", "pepela", "jd123@gmail.com", "0001234567");
		testProfile1 = new UserProfile(2, "Mary", "Jane", "mj441", "spooderman", "mj441@gmail.com", "0009876543");
		testEvent1 = new Event(1, "Dishes", "Do the dishes.", "Home", "Housework", "none",
				LocalDateTime.of(2021, 7, 24, 5, 2), LocalDateTime.of(2021, 7, 24, 6, 2));
		testEvent2 = new Event(2, "Homework", "Do my homework.", "Home", "Schoolwork", "none",
				LocalDateTime.of(2021, 7, 24, 6, 2), LocalDateTime.of(2021, 7, 24, 7, 2));
		testEvent3 = new Event(3, "Meeting", "Team meeting stuff.", "Home", "Schoolwork", "none",
				LocalDateTime.of(2021, 7, 24, 7, 2), LocalDateTime.of(2021, 7, 24, 8, 2));
	}

	@Test
	void tryAddTestEvents() {
		// add the 3 events
		eventRepo.addNewCurrentEvent(testProfile1.getId(), testEvent1);
		eventRepo.addNewCurrentEvent(testProfile1.getId(), testEvent2);
		eventRepo.addNewCurrentEvent(testProfile2.getId(), testEvent3);

		// fetch the events
		List<Event> currentEventsForT1 = eventRepo.findAllCurrentEvents(testProfile1.getId());
		List<Event> currentEventsForT2 = eventRepo.findAllCurrentEvents(testProfile2.getId());
		
		// check if all events are added
		assertEquals(currentEventsForT1.size(), 2);
		assertEquals(currentEventsForT2.size(), 1);
		
		assertEquals(currentEventsForT1.get(0), testEvent1);
		assertEquals(currentEventsForT1.get(1), testEvent2);
		assertEquals(currentEventsForT2.get(2), testEvent3);

	}

	
	@Test
	void updateOneEvent() {
		Event testEvent4 = new Event(3, "Meeting updated", "Team meeting stuff.", "Home", "Schoolwork", "none",
				LocalDateTime.of(2021, 7, 24, 7, 2), LocalDateTime.of(2021, 7, 24, 8, 2));
		eventRepo.updateCurrentEvent(testProfile1.getId(), testEvent3.getId(), testEvent4);

		Optional<Event> targetCheck = eventRepo.findById(3);
		assertTrue(targetCheck.isPresent());
		assertEquals(targetCheck.get().getName(), testEvent4.getName());

	}
	
	
	@Test
	void tryDeleteEvent() {
		eventRepo.deleteCurrentEvent(testProfile1.getId(), 2);
		List<Event> currentEventsForT1 = eventRepo.findAllCurrentEvents(testProfile1.getId());
		assertEquals(currentEventsForT1.size(), 1);
		assertFalse(currentEventsForT1.contains(testEvent2));
	}

	@AfterEach
	void tearDown() {
		eventRepo.deleteAll();
	}

}
