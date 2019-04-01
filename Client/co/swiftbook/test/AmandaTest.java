package co.swiftbook.test;

import co.swiftbook.apiClient.OrganizationApiClient;
import co.swiftbook.apiClient.UserApiClient;
import co.swiftbook.entity.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.mindrot.jbcrypt.*;

import co.swiftbook.exception.RestClientException;

public class AmandaTest {

	public static void main(String[] args) {
		
		// create an API client object
		UserApiClient userApiClient = new UserApiClient();
		OrganizationApiClient organizationApiClient = new OrganizationApiClient();
		
		// create a new user
		Organization testOrganization = organizationApiClient.getAll()[0];

		User testUser2 = new User("AdminTest4", "AdminTest@email.com", "Admin", "Test", testOrganization, true);
		testUser2.setHash(BCrypt.hashpw("password", BCrypt.gensalt()));
		testUser2 = userApiClient.create(testUser2);
		
	}
}