package co.swiftbook.entity;

import co.swiftbook.exception.ApiObjectException;

public class Building extends ApiObject {

	private int buildingID = -1;
	private Organization organization;
	private String name;
	private String address;
	private Boolean wheelchairAccessible;
	
	public Building(Organization org, String name,
			String address, Boolean wheelchairAccessible) {
		setOrganization(org);
		setName(name);
		setAddress(address);
		setWheelchairAccessible(wheelchairAccessible);
	}
	
	@Override
	public String toJson() {
		String json = "{ ";
		json += "\"buildingID\" : \"" + this.getID() + "\", ";
		json += "\"organizationID\" : \"" + this.getOrganization().getID() + "\", ";
		json += "\"name\" : \"" + this.getName() + "\", ";
		json += "\"address\" : \"" + this.getAddress() + "\", ";
		json += "\"wheelchairAccessible\" : \"" + this.isWheelchairAccessible() + "\"";
		json += " }";
		
		return json;
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
	
	@Override
	public String toString() {
		return toJson();
	}
	
}
