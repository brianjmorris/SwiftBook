package co.swiftbook.apiClient;

import java.util.ArrayList;

import co.swiftbook.entity.Organization;

public class OrganizationApiClient extends ApiClient<Organization> {
	
	public OrganizationApiClient() {
		super(Organization.class, Organization[].class, new String[] {});
	}

}
