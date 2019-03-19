package co.swiftbook.apiClient;

import co.swiftbook.entity.Room;

public class RoomApiClient extends ApiClient<Room> {

	public RoomApiClient() {
		super(Room.class, Room[].class);
	}

}
