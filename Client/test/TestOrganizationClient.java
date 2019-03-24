package test;

import co.swiftbook.apiClient.OrganizationApiClient;
import co.swiftbook.entity.*;

public class TestOrganizationClient {

	public static void main(String[] args) {
		
		// create an API client object
		OrganizationApiClient organizationApiClient = new OrganizationApiClient();
		
		// create a new organization
		Organization testOrganization = new Organization("Test Organization");
		testOrganization = organizationApiClient.create(testOrganization);
		if(testOrganization != null) {
			System.out.println("New Organization ID: " + testOrganization.getID());
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// update a organization
		testOrganization.setName("Test Organization Renamed");
		if(organizationApiClient.update(testOrganization)) {
			System.out.println("\nUpdated Organization: " + testOrganization);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
		// get a organization by id
		Organization organizationById = organizationApiClient.get(testOrganization.getID());
		System.out.println("\nRetrieved Organization " + organizationById.getID() + ": " + organizationById);
 		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get all organizations
//		ArrayList<Organization> organizations = organizationClient.getAll();
		Organization[] organizations = organizationApiClient.getAll();
		
		System.out.println("\nAll Organizations:");
		for(int i = 0; i < organizations.length; i++) {
			System.out.println(" " + organizations[i].getID() + ": " + organizations[i]);
		}
		
		// delete a organization
		if(organizationApiClient.delete(testOrganization.getID())) {
			System.out.println("\nDeleted Organization: " + testOrganization);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Organization[] organizationsAgain = organizationApiClient.getAll();
		
		System.out.println("\nAll Organizations:");
		for(int i = 0; i < organizationsAgain.length; i++) {
			System.out.println(" " + organizationsAgain[i].getID() + ": " + organizationsAgain[i]);
		}
	}

}
