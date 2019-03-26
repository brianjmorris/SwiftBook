
package co.swiftbook.apiClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import co.swiftbook.entity.User;
import co.swiftbook.exception.RestClientException;

public class UserApiClient extends ApiClient<User> {
	
	private OrganizationApiClient organizationApiClient;

	public UserApiClient() {
		super(User.class, User[].class, new String[] { "administrator" });
		organizationApiClient = new OrganizationApiClient();
	}

	@Override
	public User jsonToObject(String object) {
		object = convertBooleans(object);
		
		User user = (User) gson.fromJson(object, this.entityClass);
		
		int organizationID = getReferenceIDFromJson("organizationID", object, 0);
		user.setOrganization(organizationApiClient.get(organizationID));
		
		return user;
	}

	@Override
	public User[] jsonToArray(String objects) {

		objects = convertBooleans(objects);
		
		ArrayList<Integer> organizations = new ArrayList<Integer>();
		
		int openingBracket = objects.indexOf("{");
		while(0 < openingBracket) {
			organizations.add(getReferenceIDFromJson("organizationID", objects, openingBracket));
			
			openingBracket = objects.indexOf("{", openingBracket + 1);
		}
		
		User[] users = (User[]) gson.fromJson(objects, this.entityArrayClass);
		
		for(int i = 0; i < users.length; i++) {
			users[i].setOrganization(organizationApiClient.get(organizations.get(i)));
		}
		
		return users;
	}

	public User getByUsername(String username) {
		User result = null;

		try {
			URL url = new URL(this.address + "ByUsername/" + username);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("User-Agent", "SwiftBook");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				if(conn.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
					return null;
				} else {
					throw new Exception("Could not retrieve user, received code "
							+ conn.getResponseCode());
				}
			}

			StringBuilder jsonStringBuilder = new StringBuilder();
			String input = null;

			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {	
				while ((input = buffer.readLine()) != null) {
					jsonStringBuilder.append(input);
				}
			}

			String jsonString = jsonStringBuilder.toString();
			jsonString = convertBooleans(jsonString);

			User[] temp = jsonToArray(jsonStringBuilder.toString());
			
			if(0 < temp.length) {
				result = temp[0];
			}

			conn.disconnect();

		} catch (Exception e) {
			throw new RestClientException(e.getMessage());
		}

		return result;
	}

}
