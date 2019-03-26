package co.swiftbook.test;

import co.swiftbook.apiClient.RoomApiClient;
import co.swiftbook.apiClient.BuildingApiClient;
import co.swiftbook.entity.*;

public class TestRoomClient {

	public static void main(String[] args) {
		
		// create an API client object
		RoomApiClient roomApiClient = new RoomApiClient();
		BuildingApiClient buildingApiClient = new BuildingApiClient();
		
		// create a new room
		Building testBuilding = buildingApiClient.getAll()[0];
		Room testRoom = new Room(testBuilding, "Test Room", "100", "1", "Section", "Room Type");
		testRoom = roomApiClient.create(testRoom);
		if(testRoom != null) {
			System.out.println("New Room ID: " + testRoom.getID());
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// update a room
		testRoom.setName("Test Room Renamed");
		if(roomApiClient.update(testRoom)) {
			System.out.println("\nUpdated Room: " + testRoom);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 		
		// get a room by id
		Room roomById = roomApiClient.get(testRoom.getID());
		System.out.println("\nRetrieved Room " + roomById.getID() + ": " + roomById);
 		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// get all rooms
		Room[] rooms = roomApiClient.getAll();
		
		System.out.println("\nAll Rooms:");
		for(int i = 0; i < rooms.length; i++) {
			System.out.println(" " + rooms[i].getID() + ": " + rooms[i]);
		}
		
		// delete a room
		if(roomApiClient.delete(testRoom.getID())) {
			System.out.println("\nDeleted Room: " + testRoom);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Room[] roomsAgain = roomApiClient.getAll();
		
		System.out.println("\nAll Rooms:");
		for(int i = 0; i < roomsAgain.length; i++) {
			System.out.println(" " + roomsAgain[i].getID() + ": " + roomsAgain[i]);
		}
	}

}
