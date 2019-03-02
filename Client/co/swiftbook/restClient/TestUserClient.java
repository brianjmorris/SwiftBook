package co.swiftbook.restClient;

import java.util.ArrayList;
import java.util.List;

import co.swiftbook.entity.*;

public class TestUserClient {

	public static void main(String[] args) {
		
		// create an API client object
		UserClient userClient = new UserClient();
		
		// create a new user
		User newUser = new User("TestUser", "test@email.com", "Glenn", "Smith", new Organization("Test Organization"), false);
		if(userClient.create(newUser)) {
			System.out.println("Created User: " + newUser);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get a user by username
		User userByUsername = userClient.getByUsername("TestUser");
		System.out.println("\nRetrieved User: " + userByUsername.getUsername() + " - " + userByUsername);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// update a user
		userByUsername.setFirstName("Alasdair");
		if(userClient.update(userByUsername)) {
			System.out.println("\nUpdated User: " + userByUsername.getUsername() + " - " + userByUsername);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get a user by id
		User userById = userClient.get(userByUsername.getID());
		System.out.println("\nRetrieved User " + userById.getID() + ": " + userById.getUsername() + " - " + userById);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get all users
//		ArrayList<User> users = userClient.getAll();
		User[] users = userClient.getAll();
		
		System.out.println("\nAll Users:");
		for(int i = 0; i < users.length; i++) {
			System.out.println(" " + users[i].getID() + ": " + users[i]);
		}
		
		// delete a user
		if(userClient.delete(userByUsername.getID())) {
			System.out.println("\nDeleted User: " + userByUsername);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User[] usersAgain = userClient.getAll();
		
		System.out.println("\nAll Users:");
		for(int i = 0; i < usersAgain.length; i++) {
			System.out.println(" " + usersAgain[i].getID() + ": " + usersAgain[i]);
		}
	}

}
