package test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import co.swiftbook.apiClient.BookingApiClient;
import co.swiftbook.apiClient.RoomApiClient;
import co.swiftbook.apiClient.UserApiClient;
import co.swiftbook.entity.*;

public class TestBookingClient {

	public static void main(String[] args) {
		
		// create an API client object
		BookingApiClient bookingApiClient = new BookingApiClient();
		RoomApiClient roomApiClient = new RoomApiClient();
		UserApiClient userApiClient = new UserApiClient();
		
		// create a new booking
		User testUser = userApiClient.getAll()[0];
		Room testRoom = roomApiClient.getAll()[0];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Booking testBooking = new Booking(testUser, testRoom, LocalDateTime.parse("2019-03-27 11:30:00", formatter), LocalDateTime.parse("2019-03-27 13:00:00", formatter));
		testBooking = bookingApiClient.create(testBooking);
		if(testBooking != null) {
			System.out.println("New Booking ID: " + testBooking.getID());
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// update a booking
		LocalDateTime temp = testBooking.getEnd().plusHours(1);
		testBooking.setEnd(temp);
		if(bookingApiClient.update(testBooking)) {
			System.out.println("\nUpdated Booking: " + testBooking);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
		// get a booking by id
		Booking bookingById = bookingApiClient.get(testBooking.getID());
		System.out.println("\nRetrieved Booking " + bookingById.getID() + ": " + bookingById);
 		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get all bookings
//		ArrayList<Booking> bookings = bookingClient.getAll();
		Booking[] bookings = bookingApiClient.getAll();
		
		System.out.println("\nAll Bookings:");
		for(int i = 0; i < bookings.length; i++) {
			System.out.println(" " + bookings[i].getID() + ": " + bookings[i]);
		}
		
		// delete a booking
		if(bookingApiClient.delete(testBooking.getID())) {
			System.out.println("\nDeleted Booking: " + testBooking);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Booking[] bookingsAgain = bookingApiClient.getAll();
		
		System.out.println("\nAll Bookings:");
		for(int i = 0; i < bookingsAgain.length; i++) {
			System.out.println(" " + bookingsAgain[i].getID() + ": " + bookingsAgain[i]);
		}
	}

}
