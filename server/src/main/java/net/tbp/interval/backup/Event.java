package net.tbp.interval.backup;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for encapsulating a singular Event object.
 * 
 * @author Hugo Wong
 *
 */
@Entity
@Table(name = "event")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "owner")
	private Integer owner;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "location")
	private String location;

	@Column(name = "category")
	private String category;

	// date-time format: 2011-12-03T10:15:30+01:00
	@Column(name = "start")
	private LocalDateTime startTime;

	@Column(name = "end")
	private LocalDateTime endTime;

	public Event() {
		this(0, 0, null, null, null, null, null, null);

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
	public Event(Integer id, Integer owner, String name, String description, String location, String category,
			LocalDateTime startTime, LocalDateTime endTime) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.location = location;
		this.category = category;
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
	 * @return the UID of the owner
	 */
	public Integer getOwner() {
		return owner;
	}

	/**
	 * @param id the new UID to set
	 */
	public void setOwner(int owner) {
		this.owner = owner;
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
		return Objects.hash(category, description, endTime, id, location, name, startTime);
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
		return Objects.equals(category, other.category) && Objects.equals(description, other.description)
				&& Objects.equals(endTime, other.endTime) && Objects.equals(id, other.id)
				&& Objects.equals(location, other.location) && Objects.equals(name, other.name)
				&& Objects.equals(startTime, other.startTime);
	}

	/**
	 * Replace all attributes in this event with non-null values from newEvent.
	 * 
	 * @param newEvent
	 */
	public void updateAttributes(Event newEvent) {
		if (newEvent.name != null) {
			this.name = newEvent.name;
		}
		if (newEvent.description != null) {
			this.description = newEvent.description;
		}
		if (newEvent.location != null) {
			this.location = newEvent.location;
		}
		if (newEvent.category != null) {
			this.category = newEvent.category;
		}
		if (newEvent.startTime != null) {
			this.startTime = newEvent.startTime;
		}
		if (newEvent.endTime != null) {
			this.endTime = newEvent.endTime;
		}
	}

}
