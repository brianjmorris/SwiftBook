package co.swiftbook.test;

import co.swiftbook.apiClient.BuildingApiClient;
import co.swiftbook.apiClient.OrganizationApiClient;
import co.swiftbook.entity.*;

public class TestBuildingClient {

	public static void main(String[] args) {
		
		// create an API client object
		BuildingApiClient buildingApiClient = new BuildingApiClient();
		OrganizationApiClient organizationApiClient = new OrganizationApiClient();
		
		// create a new building
		Organization testOrganization = organizationApiClient.getAll()[0];
		Building testBuilding = new Building(testOrganization, "Test Building", "Address", true);
		testBuilding = buildingApiClient.create(testBuilding);
		if(testBuilding != null) {
			System.out.println("New Building ID: " + testBuilding.getID());
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// update a building
		testBuilding.setName("Test Building Renamed");
		if(buildingApiClient.update(testBuilding)) {
			System.out.println("\nUpdated Building: " + testBuilding);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
		// get a building by id
		Building buildingById = buildingApiClient.get(testBuilding.getID());
		System.out.println("\nRetrieved Building " + buildingById.getID() + ": " + buildingById);
 		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get all buildings
//		ArrayList<Building> buildings = buildingClient.getAll();
		Building[] buildings = buildingApiClient.getAll();
		
		System.out.println("\nAll Buildings:");
		for(int i = 0; i < buildings.length; i++) {
			System.out.println(" " + buildings[i].getID() + ": " + buildings[i]);
		}
		
		// delete a building
		if(buildingApiClient.delete(testBuilding.getID())) {
			System.out.println("\nDeleted Building: " + testBuilding);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Building[] buildingsAgain = buildingApiClient.getAll();
		
		System.out.println("\nAll Buildings:");
		for(int i = 0; i < buildingsAgain.length; i++) {
			System.out.println(" " + buildingsAgain[i].getID() + ": " + buildingsAgain[i]);
		}
	}

}
