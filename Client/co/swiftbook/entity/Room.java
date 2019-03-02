package co.swiftbook.entity;

public class Room implements ApiObject {
	
	private int roomID;
	private Building building;
	private String name;
	private String roomNumber;
	private String floorNumber;
	private String buildingSection;
	private String roomType; // classroom, conference room, etc.
	
	public Room(Building building, String name, String roomNumber, String floorNumber, String buildingSection, String roomType) {
		this.building = building;
		this.name = name;
		this.roomNumber = roomNumber;
		this.buildingSection = buildingSection;
		this.roomType = roomType;
	}
	
	
	@Override
	public int getID() {
		return this.roomID;
	}

	/**
	 * @return the building
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	/**
	 * @return the floorNumber
	 */
	public String getFloorNumber() {
		return floorNumber;
	}

	/**
	 * @param floorNumber the floorNumber to set
	 */
	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}

	/**
	 * @return the buildingSection
	 */
	public String getBuildingSection() {
		return buildingSection;
	}

	/**
	 * @param buildingSection the buildingSection to set
	 */
	public void setBuildingSection(String buildingSection) {
		this.buildingSection = buildingSection;
	}

	/**
	 * @return the roomType
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * @param roomType the roomType to set
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
}
