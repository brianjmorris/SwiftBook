package co.swiftbook.entity;

import java.util.Date;

import co.swiftbook.apiClient.ApiClient;
import co.swiftbook.apiClient.BookingApiClient;
import co.swiftbook.exception.ApiObjectException;

public class Booking extends ApiObject {

	private int bookingID = -1;
	private User user;
	private Room room;
	private Date startTime;
	private Date endTime;
	
	public Booking(User user, Room room, Date startTime, Date endTime) {
		setUser(user);
		setRoom(room);
		setEndTime(endTime);
		setStartTime(startTime);
	}

	/**
	 * @return the bookingID
	 */
	@Override
	public int getID() {
		return this.bookingID;
	}

	/**
	 * @param id the bookingID to set
	 */
	@Override
	protected void setID(int id) {
		this.bookingID = id;
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
		if(user.getID() < 0)
			throw new ApiObjectException("User does not have an ID");
		
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
		if(room.getID() < 0)
			throw new ApiObjectException("Room does not have an ID");
		
		this.room = room;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toJson() {
		String str = "{ ";
		str += "bookingID : " + this.bookingID + ", ";
		str += "userID : " + this.user.getID() + ", ";
		str += "roomID : " + this.room.getID() + ", ";
		str += "startTime : " + this.bookingID + ", ";
		str += "endTime : " + this.bookingID;
		str += " }";
		
		return str;
	}

}
