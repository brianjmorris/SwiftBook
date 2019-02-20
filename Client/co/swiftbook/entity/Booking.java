package co.swiftbook.entity;

public class Booking implements ApiObject {

	private int bookingId;
	
	@Override
	public int getID() {
		return this.bookingId;
	}

}
