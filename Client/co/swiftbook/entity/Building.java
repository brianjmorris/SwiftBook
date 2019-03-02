package co.swiftbook.entity;

import java.util.List;

public class Building implements ApiObject {

	private int buildingID;
	private Organization organization;
	private List<Room> rooms;
	private String address;
	private Boolean wheelchairAccessible;
	private List<String> sections;	// i.e. East Wing, etc.
	
	public Building(Organization organization, List<Room> rooms, String address, Boolean wheelchairAccessible, List<String> sections) {
		this.organization = organization;
		this.rooms = rooms;
		this.address = address;
		this.wheelchairAccessible = wheelchairAccessible;
		this.sections = sections;
	}
	
	@Override
	public int getID() {
		return this.buildingID;
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
	public List<String> getSections() {
		return sections;
	}

	/**
	 * @param section the section to add
	 */
	public void addSection(String section) {
		this.sections.add(section);
	}

	/**
	 * @param section the section to remove
	 */
	public void removeSection(String section) {
		this.sections.remove(section);
	}
	
}
