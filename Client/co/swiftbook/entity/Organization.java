package co.swiftbook.entity;

import java.util.List;

public class Organization implements ApiObject {
	
	private int organizationID;
	private String organizationName;
	private List<Building> buildings;
	
	public Organization(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public Organization(String organizationName, List<Building> buildings) {
		this.organizationName = organizationName;
		this.buildings = buildings;
	}
	
	
	@Override
	public int getID() {
		return this.organizationID;
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
		this.buildings.add(building);
	}

	/**
	 * @param building the building to remove
	 */
	public void removeBuilding(Building building) {
		this.buildings.remove(building);
	}
	
}
