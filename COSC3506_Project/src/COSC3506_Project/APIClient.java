package COSC3506_Project;

import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.*;
import org.mindrot.jbcrypt.*;

public class APIClient {

	private static String address = "http://www.cosc3506.ga/api/";

	public static User[] getUsers() {

		User[] users = new User[0];

		try {

			Gson gson = new Gson();

			URL url = new URL(address + "User/");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");

			int status = conn.getResponseCode();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception("Could not retrieve user, received code "
						+ conn.getResponseCode());
			}

			StringBuilder userStringBuilder = new StringBuilder();
			String input = null;

			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {	
				while ((input = buffer.readLine()) != null) {
					userStringBuilder.append(input);
				}
			}

			String userString = userStringBuilder.toString();
			userString = intToBoolean(userString, "Administrator");

			users = gson.fromJson(userString.toString(), User[].class);

			conn.disconnect();

		} catch (Exception e) {
			throw new APIClientException(e.getMessage());
		}

		return users;

	}

	public static boolean createUser(User newUser) {
		try {
			Gson gson = new Gson();

			URL url = new URL(address + "User/");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String newUserString = gson.toJson(newUser, User.class);

			OutputStream out = conn.getOutputStream();
			out.write(newUserString.getBytes());
			out.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new Exception("Error creating user, received code "
						+ conn.getResponseCode());
			}

			conn.disconnect();

		} catch (Exception e) {
			throw new APIClientException(e.getMessage());
		}

		return true;
	}

	private static String intToBoolean(String str, String field) {
		field = "\"" + field + "\":";
		str = str.replaceAll(field + "0", field + "\"false\"");
		str = str.replaceAll(field + "1", field + "\"true\"");
		return str;
	}

}
