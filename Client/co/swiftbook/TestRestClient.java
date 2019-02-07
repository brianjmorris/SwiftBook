package co.swiftbook;

import co.swiftbook.entity.*;

import java.util.List;

public class TestRestClient {

	public static void main(String[] args) {
		
//		User newUser = new User("test2", "test2@email.com", "Test2", "User", "Organization", false);
//		
//		if(RestClient.createUser(newUser)) {
//			System.out.println("Created " + newUser);
//		}
		
		User[] users = RestClient.getUsers();
		
		System.out.println("Users:");
		
		for(int i = 0; i < users.length; i++) {
			System.out.println(" - " + users[i]);
		}
	}

}
