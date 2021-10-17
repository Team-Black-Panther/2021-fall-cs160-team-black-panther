package net.tbp.interval.backup;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class for encapsulating a singlar user profile.
 * 
 * @author Hugo Wong
 *
 */
@Entity
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	String firstName, lastName;
	String userName;
	String passhash;
	String email;

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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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

	String phoneNumber;

}
