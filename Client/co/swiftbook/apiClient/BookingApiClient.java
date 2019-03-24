package co.swiftbook.apiClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import co.swiftbook.entity.Booking;

public class BookingApiClient extends ApiClient<Booking> {

	private RoomApiClient roomApiClient;
	private UserApiClient userApiClient;
	
	public BookingApiClient() {
		super(Booking.class, Booking[].class, new String[] {});
		roomApiClient = new RoomApiClient();
		userApiClient = new UserApiClient();
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

	@Override
	public Booking jsonToObject(String object) {
		object = convertBooleans(object);
		
		String field = "start";

		Boolean firstField = false;
		int indexBefore = object.indexOf(",\"" + field + "\":\"");
		if(indexBefore < 0) {
			firstField = true;
			indexBefore = object.indexOf("\"" + field + "\":\"");
		}

		int indexAfter = object.indexOf("\",", indexBefore + field.length() + (firstField?4:5));
		if(indexAfter < 0) {
			indexAfter = object.indexOf("\"", indexBefore + field.length() + (firstField?4:5));
		}
		
		String dateString = object.substring(indexBefore + field.length() + (firstField?4:5), indexAfter);
		dateString = dateString.trim();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse(dateString, formatter);
		
		object = object.substring(0, indexBefore)
				+ object.substring(indexAfter + (firstField?2:1), object.length());
		
		field = "end";
		
		firstField = false;
		indexBefore = object.indexOf(",\"" + field + "\":\"");
		if(indexBefore < 0) {
			firstField = true;
			indexBefore = object.indexOf("\"" + field + "\":\"");
		}
		
		indexAfter = object.indexOf("\",", indexBefore + field.length() + (firstField?4:5));
		if(indexAfter < 0) {
			indexAfter = object.indexOf("\"", indexBefore + field.length() + (firstField?4:5));
		}
		
		dateString = object.substring(indexBefore + field.length() + (firstField?4:5), indexAfter);
		dateString = dateString.trim();
		
		LocalDateTime end = LocalDateTime.parse(dateString, formatter);
		
		object = object.substring(0, indexBefore)
				+ object.substring(indexAfter + (firstField?2:1), object.length());
		
		Booking booking = (Booking) gson.fromJson(object, this.entityClass);
		
		booking.setStart(start);
		booking.setEnd(end);
		
		int roomID = getReferenceIDFromJson("roomID", object, 0);
		booking.setRoom(roomApiClient.get(roomID));
		
		int userID = getReferenceIDFromJson("userID", object, 0);
		booking.setUser(userApiClient.get(userID));
		
		return booking;
	}

	@Override
	public Booking[] jsonToArray(String objects) {
		objects = convertBooleans(objects);
		
		ArrayList<LocalDateTime> starts = new ArrayList<LocalDateTime>();
		ArrayList<LocalDateTime> ends = new ArrayList<LocalDateTime>();
		ArrayList<Integer> rooms = new ArrayList<Integer>();
		ArrayList<Integer> users = new ArrayList<Integer>();
		
		int openingBracket = objects.indexOf("{");
		while(0 < openingBracket) {
			String field = "start";

			Boolean firstField = false;
			int indexBefore = objects.indexOf(",\"" + field + "\":\"");
			if(indexBefore < 0) {
				firstField = true;
				indexBefore = objects.indexOf("\"" + field + "\":\"");
			}

			int indexAfter = objects.indexOf("\",", indexBefore + field.length() + (firstField?4:5));
			if(indexAfter < 0) {
				indexAfter = objects.indexOf("\"", indexBefore + field.length() + (firstField?4:5));
			}
			
			String dateString = objects.substring(indexBefore + field.length() + (firstField?4:5), indexAfter);
			dateString = dateString.trim();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			starts.add(LocalDateTime.parse(dateString, formatter));
			
			objects = objects.substring(0, indexBefore)
					+ objects.substring(indexAfter + (firstField?2:1), objects.length());
			
			field = "end";
			
			firstField = false;
			indexBefore = objects.indexOf(",\"" + field + "\":\"");
			if(indexBefore < 0) {
				firstField = true;
				indexBefore = objects.indexOf("\"" + field + "\":\"");
			}
			
			indexAfter = objects.indexOf("\",", indexBefore + field.length() + (firstField?4:5));
			if(indexAfter < 0) {
				indexAfter = objects.indexOf("\"", indexBefore + field.length() + (firstField?4:5));
			}
			
			dateString = objects.substring(indexBefore + field.length() + (firstField?4:5), indexAfter);
			dateString = dateString.trim();
			
			ends.add(LocalDateTime.parse(dateString, formatter));
			
			objects = objects.substring(0, indexBefore)
					+ objects.substring(indexAfter + (firstField?2:1), objects.length());
			
			rooms.add(getReferenceIDFromJson("roomID", objects, openingBracket));
			users.add(getReferenceIDFromJson("userID", objects, openingBracket));
			
			openingBracket = objects.indexOf("{", openingBracket + 1);
		}
		
		Booking[] bookings = (Booking[]) gson.fromJson(objects, this.entityArrayClass);
		
		for(int i = 0; i < bookings.length; i++) {
			bookings[i].setStart(starts.get(i));
			bookings[i].setEnd(ends.get(i));
			bookings[i].setRoom(roomApiClient.get(rooms.get(i)));
			bookings[i].setUser(userApiClient.get(users.get(i)));
		}
		
		return bookings;
	}

}
