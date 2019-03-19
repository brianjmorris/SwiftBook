package co.swiftbook.entity;

import java.util.List;

import co.swiftbook.exception.ApiObjectException;

public class Building extends ApiObject {

	private int buildingID = -1;
	private Organization organization;
	private List<Room> rooms;
	private String address;
	private Boolean wheelchairAccessible;
	private String sections;	// i.e. East Wing, etc.
	
	public Building(Organization org, List<Room> rooms, 
			String address, Boolean wheelchairAccessible, String sections) {
		setOrganization(org);
		setAddress(address);
		setWheelchairAccessible(wheelchairAccessible);
		setSections(sections);
		
		for(int i = 0; i < rooms.size(); i++)
			addRoom(rooms.get(i));
	}

	/**
	 * @return the buildingID
	 */
	@Override
	public int getID() {
		return this.buildingID;
	}

	/**
	 * @param id the buildingID to set
	 */
	@Override
	protected void setID(int id) {
		this.buildingID = id;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organisation the organization to set
	 */
	public void setOrganization(Organization org) {
		if(org.getID() < 0)
			throw new ApiObjectException("Organization does not have an ID");
		
		this.organization = org;
	}

	/**
	 * @return the rooms
	 */
	public List<Room> getRooms() {
		return rooms;
	}

	/**
	 * @param room the room to add
	 */
	public void addRoom(Room room) {
		if(room.getID() < 0)
			throw new ApiObjectException("Room does not have an ID");
		
		this.rooms.add(room);
	}

	/**
	 * @param room the room to remove
	 */
	public void removeRoom(Room room) {
		this.rooms.remove(room);
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the wheelchairAccessible
	 */
	public Boolean isWheelchairAccessible() {
		return wheelchairAccessible;
	}

	/**
	 * @param wheelchairAccessible the wheelchairAccessible to set
	 */
	public void setWheelchairAccessible(Boolean wheelchairAccessible) {
		this.wheelchairAccessible = wheelchairAccessible;
	}

	/**
	 * @return the sections
	 */
	public String getSections() {
		return sections;
	}

	/**
	 * @param sections the sections to set
	 */
	public void setSections(String sections) {
		this.sections = sections;
	}

	@Override
	public String toJson() {
		String str = "{ ";
		str += this.getID();
		str += " }";
		
		return str;
	}
	
}
