package co.swiftbook.entity;

import java.util.List;

import co.swiftbook.exception.ApiObjectException;

public class Organization extends ApiObject {
	
	private int organizationID = -1;
	private String organizationName;
	private List<Building> buildings;
	
	public Organization(String organizationName) {
		setOrganizationName(organizationName);
	}
	
	public Organization(String organizationName, List<Building> buildings) {
		setOrganizationName(organizationName);
		
		for(int i = 0; i < buildings.size(); i++)
			addBuilding(buildings.get(i));
	}

	/**
	 * @return the organizationID
	 */
	@Override
	public int getID() {
		return this.organizationID;
	}

	/**
	 * @param id the organizationID to set
	 */
	@Override
	protected void setID(int id) {
		this.organizationID = id;
	}

	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * @return the buildings
	 */
	public List<Building> getBuildings() {
		return buildings;
	}

	/**
	 * @param building the building to add
	 */
	public void addBuilding(Building building) {
		if(building.getID() < 0)
			throw new ApiObjectException("Building does not have an ID");
		
		this.buildings.add(building);
	}

	/**
	 * @param building the building to remove
	 */
	public void removeBuilding(Building building) {
		this.buildings.remove(building);
	}

	@Override
	public String toJson() {
		String str = "{ ";
		str += this.getID();
		str += " }";
		
		return str;
	}
	
}
