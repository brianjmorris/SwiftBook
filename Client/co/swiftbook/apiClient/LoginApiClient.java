
package co.swiftbook.apiClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.mindrot.jbcrypt.*;

import com.google.gson.Gson;

import co.swiftbook.entity.User;
import co.swiftbook.exception.RestClientException;

public class LoginApiClient extends ApiClient<User> {

	public LoginApiClient() {
		super(User.class, User[].class, "Login");
	}

	@Override
	protected User jsonToObject(String object) {

		object = convertBooleans(object);
		// TODO convert foreign keys to object references
		
		return (User) gson.fromJson(object, this.entityClass);
	}

	@Override
	protected User[] jsonToArray(String objects) {

		objects = convertBooleans(objects);
		// TODO convert foreign keys to object references
		
		return (User[]) gson.fromJson(objects, this.entityArrayClass);
	}
	
	public boolean login(User user, String password) {
		try {
			URL url = new URL(this.address);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("User-Agent", "SwiftBook");

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

}
