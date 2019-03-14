
package co.swiftbook.restClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import co.swiftbook.entity.User;
import co.swiftbook.exception.RestClientException;

public class OrganizationClient extends RestClient<User> {

	public OrganizationClient() {
		super(User.class, User[].class);
	}
		
	
	public User getByOrganization(String username) {
		User result = null;

		try {		
			Gson gson = new Gson();

			URL url = new URL(this.address + "ByOrganization/" + username);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RestClientException("Could not retrieve user, received code "
						+ conn.getResponseCode());
			}

			StringBuilder jsonStringBuilder = new StringBuilder();
			String input = null;

			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {	
				while ((input = buffer.readLine()) != null) {
					jsonStringBuilder.append(input);
				}
			}

			String jsonString = jsonStringBuilder.toString();
			jsonString = intsToBooleans(jsonString);
			
			User[] temp = (User[]) gson.fromJson(jsonString, User[].class);
			
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
