package co.swiftbook.apiClient;

import co.swiftbook.entity.Booking;

public class BookingApiClient extends ApiClient<Booking> {

	public BookingApiClient() {
		super(Booking.class, Booking[].class);
	}

}
