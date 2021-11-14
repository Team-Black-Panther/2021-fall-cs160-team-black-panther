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
 * Class for encapsulating a singular Task object.
 * 
 * @author Hugo Wong
 *
 */
@Entity
@Table(name = "task")
public class Task {
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

	@Column(name = "priority")
	private Integer priority;

	// date-time format: 2011-12-03T10:15:30+01:00
	@Column(name = "deadline")
	private LocalDateTime deadline;

	/**
	 * No args constructor (do not use).m
	 */
	public Task() {
		this(0, 0, "ERROR", "ERROR", "ERROR", "ERROR", 0, null);
	}

	/**
	 * All args constructor.
	 * 
	 * @param id
	 * @param owner
	 * @param name
	 * @param description
	 * @param location
	 * @param priority
	 * @param deadline
	 */
	public Task(Integer id, Integer owner, String name, String description, String location, String category,
			Integer priority, LocalDateTime deadline) {
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.location = location;
		this.category = category;
		this.priority = priority;
		this.deadline = deadline;
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
	 * @return the owner
	 */
	public Integer getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Integer owner) {
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
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the deadline
	 */
	public LocalDateTime getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deadline, description, id, location, name, owner, priority);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(deadline, other.deadline) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(location, other.location)
				&& Objects.equals(name, other.name) && Objects.equals(owner, other.owner)
				&& Objects.equals(priority, other.priority);
	}

}
