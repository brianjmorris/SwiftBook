package co.swiftbook.apiClient;

import java.util.Scanner;

import co.swiftbook.entity.Organization;
import co.swiftbook.entity.User;

public class TestLogin {

	// TODO: fix BCrypt "invalid salt version"
	public static void main(String[] args) {
		UserApiClient userApiClient = new UserApiClient();
		Scanner in = new Scanner(System.in);
		
		System.out.print("Username: ");
		String username = in.next();

		User newUser = new User(username, "test@email.com", "FirstName", "LastName", 
				new Organization("Test Organization"), false);
		
		System.out.print("Password: ");
		
		if(userApiClient.login(newUser, in.next())) {
			System.out.println("\nSuccessful login");
		} else {
			System.out.println("\nFailed login");
		}
		
		in.close();
	}

}
