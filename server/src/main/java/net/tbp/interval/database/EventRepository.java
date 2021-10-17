package net.tbp.interval.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.tbp.interval.backup.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
	
	@Query("TODO: WRITE THE CORRECT MYSQL QUERY")
	// WARNING: ORDER OF PARAMETERS MAY NOT BE CORRECT
	// THEY MUST MATCH THE ORDER OF PARAM IN THE QUERY CALL
	List<Event> findAllCurrentEvents(Integer uid);
	
	@Query("TODO: WRITE THE CORRECT MYSQL QUERY")
	// WARNING: ORDER OF PARAMETERS MAY NOT BE CORRECT
	// THEY MUST MATCH THE ORDER OF PARAM IN THE QUERY CALL
	void addNewCurrentEvent(Integer uid, Event newEvent);
	
	@Query("TODO: WRITE THE CORRECT MYSQL QUERY")
	// WARNING: ORDER OF PARAMETERS MAY NOT BE CORRECT
	// THEY MUST MATCH THE ORDER OF PARAM IN THE QUERY CALL
	void updateCurrentEvent(Integer uid, Integer eventid, Event newEvent);
	
	@Query("TODO: WRITE THE CORRECT MYSQL QUERY")
	// WARNING: ORDER OF PARAMETERS MAY NOT BE CORRECT
	// THEY MUST MATCH THE ORDER OF PARAM IN THE QUERY CALL
	void deleteCurrentEvent(Integer uid, Integer eventid);
	
}
