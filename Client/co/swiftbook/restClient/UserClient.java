
package co.swiftbook.restClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.mindrot.jbcrypt.*;

import com.google.gson.Gson;

import co.swiftbook.entity.User;
import co.swiftbook.exception.RestClientException;

public class UserClient extends RestClient<User> {

	public UserClient() {
		super(User.class, User[].class);
	}
	
	public boolean login(User user, String password) {
		try {		
			Gson gson = new Gson();

			URL url = new URL(this.address + "Login/");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String newUserString = gson.toJson(user, this.entityClass);

			OutputStream out = conn.getOutputStream();
			out.write(newUserString.getBytes());
			out.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				if(conn.getResponseCode() == 418) {
					return false;
				} else {
					throw new RestClientException("Could not login, received code "
							+ conn.getResponseCode());
				}
			}

			StringBuilder jsonStringBuilder = new StringBuilder();
			String input = null;

			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				
				while ((input = buffer.readLine()) != null) {
					jsonStringBuilder.append(input);
				}
				
				Boolean valid = BCrypt.checkpw(password, jsonStringBuilder.toString());
				
				conn.disconnect();
				
				return valid;
			}

		} catch (Exception e) {
			throw new RestClientException(e.getMessage());
		}
	}

	public User getByUsername(String username) {
		User result = null;

		try {		
			Gson gson = new Gson();

			URL url = new URL(this.address + "ByUsername/" + username);

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
