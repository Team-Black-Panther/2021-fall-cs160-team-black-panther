package net.tbp.interval.core;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class for encapsulating a singular Event object.
 * 
 * @author Hugo Wong
 *
 */
public class Event {
	// TODO: make sure this works with our current tables
	private Integer id;

	private String name;

	private String description;

	private String location;

	private String category;

	private String alarm;

	// date-time format: 2011-12-03T10:15:30+01:00
	private LocalDateTime startTime, endTime;

	public Event() {
		this(0, "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", null, null);
	}

	/**
	 * All arg constructor.
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param location
	 * @param category
	 * @param alarm
	 * @param startTime
	 * @param endTime
	 */
	public Event(Integer id, String name, String description, String location, String category, String alarm,
			LocalDateTime startTime, LocalDateTime endTime) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.category = category;
		this.alarm = alarm;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the alarm
	 */
	public String getAlarm() {
		return alarm;
	}

	/**
	 * @param alarm the alarm to set
	 */
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(alarm, category, description, endTime, id, location, name, startTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(alarm, other.alarm) && Objects.equals(category, other.category)
				&& Objects.equals(description, other.description) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(id, other.id) && Objects.equals(location, other.location)
				&& Objects.equals(name, other.name) && Objects.equals(startTime, other.startTime);
	}

}