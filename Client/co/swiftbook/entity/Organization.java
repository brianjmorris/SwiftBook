package co.swiftbook.entity;

public class Organization extends ApiObject {
	
	private int organizationID = -1;
	private String name;
	
	public Organization(String name) {
		setName(name);
	}
	
	@Override
	public String toJson() {
		// TODO prevent null references
		String json = "{ ";
		json += "\"organizationID\" : \"" + this.getID() + "\", ";
		json += "\"name\" : \"" + this.getName() + "\"";
		json += " }";
		return json;
	}

	/**
	 * @return the organizationID
	 */
	@Override
	public int getID() {
		return this.organizationID;
	}

	/**
	 * @param id the organizationID to set
	 */
	@Override
	protected void setID(int id) {
		this.organizationID = id;
	}

	/**
	 * @return the organizationName
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param organizationName the organizationName to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return toJson();
	}
	
}
