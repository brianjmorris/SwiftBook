package co.swiftbook.entity;

public class Organization implements ApiObject {
	
	int organizationID;
	String organizationName;
	
	@Override
	public int getID() {
		return this.organizationID;
	}
	
}
