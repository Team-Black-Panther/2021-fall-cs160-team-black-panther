package net.tbp.interval.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.tbp.interval.backup.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
	
	@Query(SELECT * FROM currentevent WHERE eventID IN (SELECT eventID FROM userhascurrentevent WHERE userID = uid))
	// WARNING: ORDER OF PARAMETERS MAY NOT BE CORRECT
	// THEY MUST MATCH THE ORDER OF PARAM IN THE QUERY CALL
	List<Event> findAllCurrentEvents(Integer uid);
	
	@Query(INSERT INTO currentevent(eventID, name, description, location, category, alarm, start, end) VALUES(newEvent.getID(), newEvent.getName(), newEvent.getDescription(), newEvent.getLocation(), newEvent.getCategory(), newEvent.getAlarm(), newEvent.getStartTime(), newEvent.getEndTime()))
	@Query(INSERT INTO userhascurrentevent VALUES (uid, newEvent.getID))
	// WARNING: ORDER OF PARAMETERS MAY NOT BE CORRECT
	// THEY MUST MATCH THE ORDER OF PARAM IN THE QUERY CALL
	void addNewCurrentEvent(Integer uid, Event newEvent);
	
	@Query(UPDATE currentevent SET name = newEvent.getName(), description = newEvent.getDescription(), location = newEvent.getLocation(), category = newEvent.getCategory(), alarm = newEvent.getAlarm(), start = newEvent.getStartTime(), end = newEvent.getEndTime() WHERE eventID = eventid)
	// WARNING: ORDER OF PARAMETERS MAY NOT BE CORRECT
	// THEY MUST MATCH THE ORDER OF PARAM IN THE QUERY CALL
	void updateCurrentEvent(Integer uid, Integer eventid, Event newEvent);
	
	@Query(DELETE FROM currentevent WHERE eventID = eventid)
	@Query(DELETE FROM userhascurrenteent WHERE userID = uid AND eventID = eventid)
	// WARNING: ORDER OF PARAMETERS MAY NOT BE CORRECT
	// THEY MUST MATCH THE ORDER OF PARAM IN THE QUERY CALL
	void deleteCurrentEvent(Integer uid, Integer eventid);
	
}
