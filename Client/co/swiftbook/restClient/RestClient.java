package co.swiftbook.restClient;

import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.*;

import co.swiftbook.entity.ApiObject;
import co.swiftbook.exception.RestClientException;

import org.mindrot.jbcrypt.BCrypt;

public abstract class RestClient<T> {

	protected String address = "https://swiftbook.co/api/";
	protected String[] booleanFields = {
		"Administrator"	
	};
	
	private Class entityClass;
	private Class entityArrayClass;
	
	public RestClient(Class entityClass, Class entityArrayClass) {
		this.entityClass = entityClass;
		this.entityArrayClass = entityArrayClass;
		
		this.address += this.entityClass.getSimpleName() + "/";
	}

	public T[] getAll() {
		
//		ArrayList<T> result = null;
		T[] result = null;

		try {			
			Gson gson = new Gson();

			URL url = new URL(address);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception("Could not retrieve objects, received code "
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
			
			result = (T[]) gson.fromJson(jsonString, this.entityArrayClass);

			conn.disconnect();

		} catch (Exception e) {
			throw new RestClientException(e.getMessage());
		}

		return result;
	}
	
	public T get(int i) {
		
		T result = null;

		try {		
			Gson gson = new Gson();

			URL url = new URL(address + i);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception("Could not retrieve object, received code "
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
			
			T[] temp = (T[]) gson.fromJson(jsonString, this.entityArrayClass);
			
			if(0 < temp.length) {
				result = temp[0];
			}
			
			conn.disconnect();

		} catch (Exception e) {
			throw new RestClientException(e.getMessage());
		}

		return result;
	}

	public boolean create(T newObject) {
		
		boolean success = true;
		
		try {
			Gson gson = new Gson();
			
			URL url = new URL(address);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String newUserString = gson.toJson(newObject, entityClass);

			OutputStream out = conn.getOutputStream();
			out.write(newUserString.getBytes());
			out.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				success = false;
				throw new Exception("Error creating object, received code "
						+ conn.getResponseCode());
			}

			conn.disconnect();

		} catch (Exception e) {
			success = false;
			throw new RestClientException(e.getMessage());
		}

		return success;
	}

	public boolean update(T newObject) {
		
		boolean success = true;
		
		try {
			if(get(((ApiObject)newObject).getID()) == null) {
				success = false;
				throw new RestClientException("User does not exist");
			}
			
			Gson gson = new Gson();

			URL url = new URL(address + ((ApiObject)newObject).getID());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");

			String newUserString = gson.toJson(newObject, entityClass);

			OutputStream out = conn.getOutputStream();
			out.write(newUserString.getBytes());
			out.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
				success = false;
				
				throw new RestClientException("Error updating user, received code "
						+ conn.getResponseCode());
				
			}

			conn.disconnect();

		} catch (Exception e) {
			success = false;
			throw new RestClientException(e.getMessage());
		}

		return success;
	}

	public boolean delete(int i) {
		
		boolean success = true;
		
		try {
			URL url = new URL(address + i);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
				success = false;
				throw new Exception("Error deleting user, received code "
						+ conn.getResponseCode());
			}

			conn.disconnect();

		} catch (Exception e) {
			success = false;
			throw new RestClientException(e.getMessage());
		}

		return success;
	}

	protected String intsToBooleans(String str) {
		for(int i = 0; i < this.booleanFields.length; i++) {
			String temp = "\"" + this.booleanFields[i] + "\":";
			str = str.replaceAll(temp + "0", temp + "\"false\"");
			str = str.replaceAll(temp + "1", temp + "\"true\"");
		}
		return str;
	}

}
