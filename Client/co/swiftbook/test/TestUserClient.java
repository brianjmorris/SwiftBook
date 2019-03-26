package co.swiftbook.test;

import co.swiftbook.apiClient.OrganizationApiClient;
import co.swiftbook.apiClient.UserApiClient;
import co.swiftbook.entity.*;

public class TestUserClient {

	public static void main(String[] args) {
		
		// create an API client object
		UserApiClient userApiClient = new UserApiClient();
		OrganizationApiClient organizationApiClient = new OrganizationApiClient();
		
		// create a new user
		Organization testOrganization = organizationApiClient.getAll()[0];
		User testUser = new User("TestUser", "test@email.com", "Test", "User", testOrganization, false);
		testUser = userApiClient.create(testUser);
		if(testUser != null) {
			System.out.println("New User ID: " + testUser.getID());
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// get a user by username
		User userByUsername = userApiClient.getByUsername("TestUser");
		System.out.println("\nRetrieved User: " + userByUsername.getUsername() + " - " + userByUsername);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// update a user
		userByUsername.setUsername("TestUserAgain");
		if(userApiClient.update(userByUsername)) {
			System.out.println("\nUpdated User: " + userByUsername.getUsername() + " - " + userByUsername);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// get a user by id
		User userById = userApiClient.get(userByUsername.getID());
		System.out.println("\nRetrieved User " + userById.getID() + ": " + userById.getUsername() + " - " + userById);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// get all users
		User[] users = userApiClient.getAll();
		
		System.out.println("\nAll Users:");
		for(int i = 0; i < users.length; i++) {
			System.out.println(" " + users[i].getID() + ": " + users[i]);
		}
		
		// delete a user
		if(userApiClient.delete(userByUsername.getID())) {
			System.out.println("\nDeleted User: " + userByUsername);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		User[] usersAgain = userApiClient.getAll();
		
		System.out.println("\nAll Users:");
		for(int i = 0; i < usersAgain.length; i++) {
			System.out.println(" " + usersAgain[i].getID() + ": " + usersAgain[i]);
		}
	}

}
