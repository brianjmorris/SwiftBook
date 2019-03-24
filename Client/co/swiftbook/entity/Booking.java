package co.swiftbook.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import co.swiftbook.exception.ApiObjectException;

public class Booking extends ApiObject {

	private int bookingID = -1;
	private User user;
	private Room room;
	private LocalDateTime start;
	private LocalDateTime end;
	
	public Booking(User user, Room room, LocalDateTime start, LocalDateTime end) {
		setUser(user);
		setRoom(room);
		setEnd(end);
		setStart(start);
	}
	
	@Override
	public String toJson() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		String json = "{";
		json += "\"bookingID\":\"" + this.getID() + "\"";
		
		if(this.getStart() != null) {
			json += ",\"start\":\"" + formatter.format(this.getStart()) + "\"";
		}
		if(this.getEnd() != null) {
			json += ",\"end\":\"" + formatter.format(this.getEnd()) + "\"";
		}
		if(this.getUser() != null) {
			json += ",\"userID\":\"" + this.getUser().getID() + "\"";
		}
		if(this.getRoom() != null) {
			json += ",\"roomID\":\"" + this.getRoom().getID() + "\"";
		}
		
		json += "}";
		
		return json;
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
	 * @return the start
	 */
	public LocalDateTime getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public LocalDateTime getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	
	@Override
	public String toString() {
		return toJson();
	}

}
