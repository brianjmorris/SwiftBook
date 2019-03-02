package co.swiftbook.entity;

public class User implements ApiObject {
	
	private int UserID;
	private String Username;
	private String Email;
	private String FirstName;
	private String LastName;
	private String Organization;
	private String Hash;
	private boolean Administrator;
	
	/**
	 * Create a new User
	 * @param userID the UserID to set
	 * @param username the Username to set
	 * @param email the Email to set
	 * @param firstName the FirstName to set
	 * @param lastName the LastName to set
	 * @param organization the Organization to set
	 * @param administrator the Administrator to set
	 */
	public User(int userID, String username, String email, 
			String firstName, String lastName, String organization, 
			boolean administrator) {
		this.UserID = userID;
		this.Username = username;
		this.Email = email;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.Organization = organization;
		this.Administrator = administrator;
	}
	
	/**
	 * Create a new User
	 * @param username the Username to set
	 * @param email the Email to set
	 * @param firstName the FirstName to set
	 * @param lastName the LastName to set
	 * @param organization the Organization to set
	 * @param administrator the Administrator to set
	 */
	public User(String username, String email, String firstName, 
			String lastName, String organization, boolean administrator) {
		this.Username = username;
		this.Email = email;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.Organization = organization;
		this.Administrator = administrator;
	}
	
	/**
	 * Create a new User
	 * @param username the Username to set
	 * @param email the Email to set
	 * @param firstName the FirstName to set
	 * @param lastName the LastName to set
	 * @param administrator the Administrator to set
	 */
	public User(String username, String email, String firstName, 
			String lastName, boolean administrator) {
		this.Username = username;
		this.Email = email;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.Administrator = administrator;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return Username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		Username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		Email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return FirstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return LastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		LastName = lastName;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return Organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		Organization = organization;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		Hash = hash;
	}

	/**
	 * @return the administrator
	 */
	public boolean isAdministrator() {
		return Administrator;
	}

	/**
	 * @param administrator the administrator to set
	 */
	public void setAdministrator(boolean administrator) {
		Administrator = administrator;
	}

	/**
	 * @return the userID
	 */
	@Override
	public int getID() {
		return UserID;
	}

	/**
	 * @return User as string
	 */
	@Override
	public String toString() {
		return this.FirstName + " " + this.LastName;
	}
	
}
