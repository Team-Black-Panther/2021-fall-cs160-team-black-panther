package net.tbp.interval.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

	private UserProfile testProfile;
	private Event testEvent1, testEvent2, testEvent3;

	@BeforeEach
	void setUp() {
		testProfile = new UserProfile(2, "John", "Doe", "jd123", "pepela", "jd123@gmail.com", "0001234567");
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
		eventRepo.addNewCurrentEvent(testProfile.getId(), testEvent1);
		eventRepo.addNewCurrentEvent(testProfile.getId(), testEvent2);
		eventRepo.addNewCurrentEvent(testProfile.getId(), testEvent3);

		// fetch the events
		List<Event> currentEvents = eventRepo.findAllCurrentEvents(testProfile.getId());

		// check if all events are added
		assertEquals(currentEvents.size(), 3);
		assertEquals(currentEvents.get(0), testEvent1);
		assertEquals(currentEvents.get(1), testEvent2);
		assertEquals(currentEvents.get(2), testEvent3);

	}

	@Test
	void updateOneEvent() {
		Event testEvent4 = new Event(3, "Meeting updated", "Team meeting stuff.", "Home", "Schoolwork", "none",
				LocalDateTime.of(2021, 7, 24, 7, 2), LocalDateTime.of(2021, 7, 24, 8, 2));
		eventRepo.updateCurrentEvent(testProfile.getId(), testEvent3.getId(), testEvent4);

		Optional<Event> targetCheck = eventRepo.findById(3);
		assertTrue(targetCheck.isPresent());
		assertEquals(targetCheck.get().getName(), testEvent4.getName());

	}

	@AfterEach
	void tearDown() {
		eventRepo.deleteAll();
	}

}
