package co.swiftbook.apiClient;

import java.util.ArrayList;

import co.swiftbook.entity.Building;

public class BuildingApiClient extends ApiClient<Building> {

	private OrganizationApiClient organizationApiClient;
	
	public BuildingApiClient() {
		super(Building.class, Building[].class, new String[] { "wheelchairAccessible" });
		organizationApiClient = new OrganizationApiClient();
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
	public Building jsonToObject(String object) {
		object = convertBooleans(object);
		
		Building building = (Building) gson.fromJson(object, this.entityClass);
		
		int organizationID = getReferenceIDFromJson("organizationID", object, 0);
		building.setOrganization(organizationApiClient.get(organizationID));
		
		return building;
	}

	@Override
	public Building[] jsonToArray(String objects) {

		objects = convertBooleans(objects);
		
		ArrayList<Integer> organizations = new ArrayList<Integer>();
		
		int openingBracket = objects.indexOf("{");
		while(0 < openingBracket) {
			
			organizations.add(getReferenceIDFromJson("organizationID", objects, openingBracket));
			
			openingBracket = objects.indexOf("{", openingBracket + 1);
		}
		
		Building[] buildings = (Building[]) gson.fromJson(objects, this.entityArrayClass);
		
		for(int i = 0; i < buildings.length; i++) {
			buildings[i].setOrganization(organizationApiClient.get(organizations.get(i)));
		}
		
		return buildings;
	}

}
