package co.swiftbook.apiClient;

import java.util.ArrayList;

import co.swiftbook.entity.Room;

public class RoomApiClient extends ApiClient<Room> {

	private BuildingApiClient buildingApiClient;
	
	public RoomApiClient() {
		super(Room.class, Room[].class, new String[] {});
		buildingApiClient = new BuildingApiClient();
	}
	
	protected int getReferenceIDFromJson(String idField, String object, int startIndex) {
		Boolean firstField = false;
		int indexBefore = object.indexOf(",\"" + idField + "\":", startIndex);
		if(indexBefore < 0) {
			firstField = true;
			indexBefore = object.indexOf("\"" + idField + "\":", startIndex);
		}

		int indexAfter = object.indexOf(",", indexBefore + idField.length() + (firstField?3:4));
		if(indexAfter < 0) {
			indexAfter = object.indexOf("}", indexBefore + idField.length() + (firstField?3:4));
		}
		
		String idString = object.substring(indexBefore + idField.length() + (firstField?3:4), indexAfter);
		idString = idString.replace('\"', ' ');
		idString = idString.trim();
		
		return Integer.parseInt(idString);
	}

	@Override
	public Room jsonToObject(String object) {
		object = convertBooleans(object);
		
		Room room = (Room) gson.fromJson(object, this.entityClass);
		
		int buildingID = getReferenceIDFromJson("buildingID", object, 0);
		room.setBuilding(buildingApiClient.get(buildingID));
		
		return room;
	}

	@Override
	public Room[] jsonToArray(String objects) {
		objects = convertBooleans(objects);
		
		ArrayList<Integer> buildings = new ArrayList<Integer>();
		
		int openingBracket = objects.indexOf("{");
		while(0 < openingBracket) {
			
			buildings.add(getReferenceIDFromJson("buildingID", objects, openingBracket));
			
			openingBracket = objects.indexOf("{", openingBracket + 1);
		}
		
		Room[] rooms = (Room[]) gson.fromJson(objects, this.entityArrayClass);
		
		for(int i = 0; i < rooms.length; i++) {
			rooms[i].setBuilding(buildingApiClient.get(buildings.get(i)));
		}
		
		return rooms;
	}

}
