package co.swiftbook.apiClient;

import java.io.*;
import java.net.*;

import com.google.gson.*;

import co.swiftbook.entity.ApiObject;
import co.swiftbook.exception.RestClientException;

public abstract class ApiClient<T extends ApiObject> {

	protected String address = "https://swiftbook.co/api/";
	protected String[] booleanFields;
	
	protected Class entityClass;
	protected Class entityArrayClass;
	protected Gson gson;
	
	public ApiClient(Class entityClass, Class entityArrayClass, String[] booleanFields) {
		gson = new Gson();
		
		this.entityClass = entityClass;
		this.entityArrayClass = entityArrayClass;
		this.booleanFields = booleanFields;
		
		this.address += this.entityClass.getSimpleName() + "/";
	}
	
	public ApiClient(Class entityClass, Class entityArrayClass, String endpoint) {
		gson = new Gson();
		
		this.entityClass = entityClass;
		this.entityArrayClass = entityArrayClass;
		
		this.address += endpoint + "/";
	}

	public T[] getAll() {
		
//		ArrayList<T> result = null;
		T[] result = null;

		try {
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

			result = jsonToArray(jsonStringBuilder.toString());
			
			conn.disconnect();

		} catch (Exception e) {
			throw new RestClientException(e.getMessage());
		}

		return result;
	}
	
	public T get(int i) {
		
		T result = null;

		try {
			URL url = new URL(address + i);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				if(conn.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
					return null;
				} else {
					throw new Exception("Could not retrieve object, received code "
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
			
			T[] temp = jsonToArray(jsonStringBuilder.toString());
			
			if(0 < temp.length) {
				result = temp[0];
			}
			
			conn.disconnect();

		} catch (Exception e) {
			throw new RestClientException(e.getMessage());
		}

		return result;
	}

	public T create(T newObject) {
		
		T result = null;
		
		try {
			URL url = new URL(address);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String newObjectString = newObject.toJson();

			OutputStream out = conn.getOutputStream();
			out.write(newObjectString.getBytes());
			out.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new Exception("Error creating object, received code "
						+ conn.getResponseCode());
			}

			StringBuilder jsonStringBuilder = new StringBuilder();
			String input = null;

			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {	
				while ((input = buffer.readLine()) != null) {
					jsonStringBuilder.append(input);
				}
			}
			
			result = jsonToObject(jsonStringBuilder.toString());

			conn.disconnect();

		} catch (Exception e) {
			throw new RestClientException(e.getMessage());
		}

		return result;
	}

	public boolean update(T object) {
		
		boolean success = true;
		
		try {
			if(get(object.getID()) == null) {
				success = false;
				throw new RestClientException("Object ID does not exist in database");
			}
			
			URL url = new URL(address + object.getID());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");

			String newObjectString = object.toJson();

			OutputStream out = conn.getOutputStream();
			out.write(newObjectString.getBytes());
			out.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
				success = false;
				
				throw new RestClientException("Error updating object, received code "
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
				throw new Exception("Error deleting object, received code "
						+ conn.getResponseCode());
			}

			conn.disconnect();

		} catch (Exception e) {
			success = false;
			throw new RestClientException(e.getMessage());
		}

		return success;
	}
	
	protected T jsonToObject(String object) {

		object = convertBooleans(object);
		
		return (T) gson.fromJson(object, this.entityClass);
	}
	
	protected T[] jsonToArray(String objects) {

		objects = convertBooleans(objects);
		
		return (T[]) gson.fromJson(objects, this.entityArrayClass);
	}
	
	protected int getReferenceIDFromJson(String idField, String object, int startIndex) {
		Boolean firstField = false;
		int indexBefore = object.indexOf(",\"" + idField + "\":", startIndex);
		if(indexBefore < 0) {
			firstField = true;
			indexBefore = object.indexOf("\"" + idField + "\":", startIndex);
		}

		int indexAfter = object.indexOf(",", indexBefore + idField.length() + (firstField?3:4));
		if(indexAfter < 0) {
			indexAfter = object.indexOf("}", indexBefore + idField.length() + (firstField?3:4));
		}
		
		String idString = object.substring(indexBefore + idField.length() + (firstField?3:4), indexAfter);
		idString = idString.replace('\"', ' ');
		idString = idString.trim();
		
		return Integer.parseInt(idString);
	}

	protected String convertBooleans(String str) {
		for(int i = 0; i < this.booleanFields.length; i++) {
			String temp = "\"" + this.booleanFields[i] + "\":";
			str = str.replaceAll(temp + "0", temp + "\"false\"");
			str = str.replaceAll(temp + "1", temp + "\"true\"");
		}
		return str;
	}

}
