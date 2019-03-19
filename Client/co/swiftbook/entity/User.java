package co.swiftbook.entity;

import co.swiftbook.exception.ApiObjectException;

public class User extends ApiObject {

	private int userID = -1;
	private Organization organization;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private String hash;
	private boolean administrator;

	/**
	 * Create a new User
	 * @param username the username to set
	 * @param email the email to set
	 * @param firstName the firstName to set
	 * @param lastName the lastName to set
	 * @param org the organization to set
	 * @param administrator the administrator value to set
	 */
	public User(String username, String email, String firstName,
			String lastName, Organization org, boolean administrator) {
		setUsername(username);
		setEmail(email);
		setFirstName(firstName);
		setLastName(lastName);
		setOrganization(org);
		setAdministrator(administrator);
	}

	/**
	 * @return the userID
	 */
	@Override
	public int getID() {
		return this.userID;
	}

	/**
	 * @param id the userID to set
	 */
	@Override
	protected void setID(int id) {
		this.userID = id;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization org) {
		if(org.getID() < 0)
			throw new ApiObjectException("Organization does not have an ID");
		
		this.organization = org;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @return the administrator
	 */
	public boolean isAdministrator() {
		return administrator;
	}

	/**
	 * @param administrator the administrator to set
	 */
	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	@Override
	public String toJson() {
		String str = "{ ";
		str += this.getID();
		str += " }";
		
		return str;
	}

}
