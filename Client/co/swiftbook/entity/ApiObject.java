package co.swiftbook.entity;

public abstract class ApiObject {
	public abstract int getID();
	protected abstract void setID(int id);
	public abstract String toJson();
}
