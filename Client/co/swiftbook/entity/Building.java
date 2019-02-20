package co.swiftbook.entity;

import java.util.ArrayList;

public class Building implements ApiObject {

	private int buildingID;
	private int organizationId;
	private ArrayList<Room> buildingRooms;
	private String wheelchairAccessible;
	private String[] sections;	// i.e. East Wing, etc.
	
	@Override
	public int getID() {
		return this.buildingID;
	}
}
