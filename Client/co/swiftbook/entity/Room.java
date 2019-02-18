package co.swiftbook.entity;

public class Room {
	
	private int roomID;
	private String room;
	private String floorNumber;
	private String roomType; // classroom, conference room, etc. I used a space, please appreciate my efforts
	
	/**
	 * 
	 * @return the roomID
	 */
	public int getRoomID() {
		return roomID;
	}
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
}
