package co.swiftbook.restClient;

import org.mindrot.jbcrypt.BCrypt;

import co.swiftbook.entity.User;

public class TestLogin {

	public static void main(String[] args) {
		String salt = BCrypt.gensalt();
		String hash = BCrypt.hashpw("Password1", salt);
		
		System.out.println(hash);
		System.out.println("Length: " +  hash.length());

		User newUser = new User("TestUser", "test@email.com", "Glenn", "Smith", "Organization", false);
		newUser.setHash(hash);
	}

}
