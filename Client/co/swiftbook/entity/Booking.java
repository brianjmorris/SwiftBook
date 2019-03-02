package co.swiftbook.entity;

public class Booking implements ApiObject {

	private int bookingID;
	private User user;
	private Room room;
	
	public Booking(User user, Room room) {
		this.user = user;
		this.room = room;
	}
	
	@Override
	public int getID() {
		return this.bookingID;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

}
