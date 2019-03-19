package co.swiftbook.apiClient;

import co.swiftbook.entity.Building;

public class BuildingApiClient extends ApiClient<Building> {

	public BuildingApiClient() {
		super(Building.class, Building[].class);
	}

}
