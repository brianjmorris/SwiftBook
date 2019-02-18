package co.swiftbook.restClient;

import java.util.ArrayList;
import java.util.List;

import co.swiftbook.entity.*;

public class TestRestClient {

	public static void main(String[] args) {
		
		RestClient<User> userClient = new RestClient<User>(User.class, User[].class);
		
		User newUser = new User("test2", "test2@email.com", "Test2", "User", "Organization", false);
		
		if(userClient.create(newUser)) {
			System.out.println("Created " + newUser);
		}

//		ArrayList<User> users = userClient.getAll();
		User[] users = userClient.getAll();
		
		System.out.println("Users:");
		
//		for(int i = 0; i < users.size(); i++) {
//			System.out.println(" - " + users.get(i));
//		}		
		for(int i = 0; i < users.length; i++) {
			System.out.println(" - " + users[i]);
		}
	}

}
