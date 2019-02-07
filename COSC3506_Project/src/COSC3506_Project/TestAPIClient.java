package COSC3506_Project;

import java.util.List;

public class TestAPIClient {

	public static void main(String[] args) {
		
//		User newUser = new User("test2", "test2@email.com", "Test2", "User", "Organization", false);
//		
//		if(APIClient.createUser(newUser)) {
//			System.out.println("Created " + newUser);
//		}
		
		User[] users = APIClient.getUsers();
		
		System.out.println("Users:");
		
		for(int i = 0; i < users.length; i++) {
			System.out.println(" - " + users[i]);
		}
	}

}
