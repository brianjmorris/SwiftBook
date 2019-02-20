package co.swiftbook.entity;

public class Room implements ApiObject {
	
	private int roomID;
	private int buildingID;
	private String room;
	private String floorNumber;
	private String buildingSection;
	private String roomType; // classroom, conference room, etc.
	
	@Override
	public int getID() {
		return this.roomID;
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
