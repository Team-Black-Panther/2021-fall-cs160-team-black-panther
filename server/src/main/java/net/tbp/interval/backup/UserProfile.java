package net.tbp.interval.backup;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class for encapsulating a singlar user profile.
 * 
 * @author Hugo Wong
 *
 */
@Entity
@Table(name = "user")
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@JsonProperty("username")
	@Column(name = "username")
	String username;
	
	@JsonProperty("firstName")
	@Column(name = "firstName")
	String firstName;

	@JsonProperty("lastName")
	@Column(name = "lastName")
	String lastName;

	@JsonProperty("passhash")
	@Column(name = "passhash")
	String passhash;

	@JsonProperty("email")
	@Column(name = "email")
	String email;

	@JsonProperty("phoneNumber")
	@Column(name = "phoneNumber")
	String phoneNumber;

	/**
	 * Default constructor.
	 */
	public UserProfile() {
		this(0, "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR");
	}

	/**
	 * All arg constructor.
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param passhash
	 * @param email
	 * @param phoneNumber
	 */
	public UserProfile(Integer id, String firstName, String lastName, String userName, String passhash, String email,
			String phoneNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = userName;
		this.passhash = passhash;
		this.email = email;
		this.phoneNumber = phoneNumber;
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
	 * @return the firstName
	 */
	@JsonProperty
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the username
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUserName(String userName) {
		this.username = userName;
	}

	/**
	 * @return the passhash
	 */
	public String getPasshash() {
		return passhash;
	}

	/**
	 * @param passhash the passhash to set
	 */
	public void setPasshash(String passhash) {
		this.passhash = passhash;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, passhash, phoneNumber, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(passhash, other.passhash) && Objects.equals(phoneNumber, other.phoneNumber)
				&& Objects.equals(username, other.username);
	}

}
