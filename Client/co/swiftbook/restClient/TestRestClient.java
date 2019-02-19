package co.swiftbook.restClient;

import java.util.ArrayList;
import java.util.List;

import co.swiftbook.entity.*;

public class TestRestClient {

	public static void main(String[] args) {
		
		UserClient userClient = new UserClient();
		
//		User newUser = new User("test2", "test2@email.com", "Test2", "User", "Organization", false);
//		
//		if(userClient.create(newUser)) {
//			System.out.println("Created " + newUser);
//		}
		
		User byId = userClient.get(1);
		System.out.println("User 1: " + byId);

		User byUsername = userClient.getByUsername("brianjmorris");
		System.out.println("\nbrianjmorris: " + byUsername);

//		ArrayList<User> users = userClient.getAll();
		User[] users = userClient.getAll();
		
		System.out.println("\nAll Users:");
		
//		for(int i = 0; i < users.size(); i++) {
//			System.out.println(" - " + users.get(i));
//		}		
		for(int i = 0; i < users.length; i++) {
			System.out.println(" - " + users[i]);
		}
	}

}
