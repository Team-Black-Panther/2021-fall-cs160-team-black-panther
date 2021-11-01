package net.tbp.interval.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.tbp.interval.backup.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
	
	@Query("SELECT e FROM Event e WHERE owner = :uid")
	List<Event> findAllCurrentEvents(@Param("uid") Integer uid);
	
	/*
	@Query("INSERT INTO currentevent(eventID, name, description, location, category, alarm, start, end) "
			+ "VALUES(:newEvent.getID(), :newEvent.getName(), :newEvent.getDescription(), :newEvent.getLocation(), :newEvent.getCategory(), :newEvent.getAlarm(), :newEvent.getStartTime(), :newEvent.getEndTime()); "
			+ "INSERT INTO userhascurrentevent VALUES (:uid, :newEvent.getID)")
	void addNewCurrentEvent(@Param("uid") Integer uid, @Param("newEvent") Event newEvent);
	
	
	@Query("UPDATE currentevent "
			+ "SET name = :newEvent.getName(), description = :newEvent.getDescription(), location = :newEvent.getLocation(), category = :newEvent.getCategory(), alarm = :newEvent.getAlarm(), start = :newEvent.getStartTime(), end = :newEvent.getEndTime() "
			+ "WHERE eventID = :eventid")
	void updateCurrentEvent(@Param("uid") Integer uid, @Param("eventid") Integer eventid, @Param("newEvent") Event newEvent);
	
	@Query("DELETE FROM currentevent WHERE eventID = eventid;" 
			+ "DELETE FROM userhascurrentevent WHERE userID = :uid AND eventID = :eventid")
	void deleteCurrentEvent(@Param("uid") Integer uid, @Param("eventid") Integer eventid);
	*/

}
