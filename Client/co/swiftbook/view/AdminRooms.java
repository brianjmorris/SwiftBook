package co.swiftbook.view;

import co.swiftbook.apiClient.BuildingApiClient;
import co.swiftbook.apiClient.RoomApiClient;
import co.swiftbook.entity.Building;
import co.swiftbook.entity.Room;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.*;

public class AdminRooms extends Application {
	
    public static void main(String[] args) {

    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook | Room Administration");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5, 275, 115, 275));
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        // Logo and  Title
        Image softwareName = new Image("co/swiftbook/view/img/swiftbook.png", 0, 115, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        Text dashTitle = new Text("Add or Delete Rooms");
        VBox viewTitle = new VBox();
        viewTitle.requestFocus();

        // User Options
        VBox details = new VBox();
        VBox logBox = new VBox();
        
        ObservableList<String> options = 
        	    FXCollections.observableArrayList(
        	        "Add Room",
        	        "Delete Room"
        	    );
        
        final ComboBox<String> action = new ComboBox(options);
        action.setValue("Add Room");
       
        Text message = new Text("");
        TextField roomName = new TextField();
        TextField roomNumber = new TextField();
        TextField buildingName = new TextField();
        TextField floorNumber = new TextField();
        TextField buildingSection = new TextField();
        TextField roomType = new TextField();
        Button back = new Button("Back");
        Button submit = new Button("Submit");
        
        viewTitle.getChildren().addAll(imageView, dashTitle, message);
        details.getChildren().addAll(action, buildingName, roomName, roomNumber, floorNumber, buildingSection, roomType, submit);
        logBox.getChildren().addAll(details, back);
        
        // Styling
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        details.getStyleClass().add("vbox");
        details.setAlignment(Pos.CENTER);
        logBox.setAlignment(Pos.CENTER);
        details.setPadding(new Insets(10, 0, 5,  0));
        details.setSpacing(50);
        dashTitle.getStyleClass().add("dashTitle");
        submit.getStyleClass().add("dashButton");
        action.getStyleClass().add("form");
        roomName.getStyleClass().add("form");
        roomName.setPromptText("Room Name");
        roomNumber.getStyleClass().add("form");
        roomNumber.setPromptText("Room Number");
        floorNumber.getStyleClass().add("form");
        floorNumber.setPromptText("Floor Number");
        buildingSection.getStyleClass().add("form");
        buildingSection.setPromptText("Building Section");
        roomType.getStyleClass().add("form");
        roomType.setPromptText("Room Type");
        buildingName.getStyleClass().add("form");
        buildingName.setPromptText("Building Name");
        back.getStyleClass().add("logOutButton");
        message.getStyleClass().add("message");
        
        // Add To View
        root.setTop(viewTitle);
        root.setBottom(logBox);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
        BorderPane.setAlignment(logBox, Pos.TOP_CENTER);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Event Handling
        back.setOnAction(e -> {
            if (Login.adminAccess()) {
            	AdminDashboard dash = new AdminDashboard();
            	dash.start(primaryStage);
            }
            
            else {
            	UserDashboard dash = new UserDashboard();
            	dash.start(primaryStage);
            }
        });
        
        action.setOnAction(e -> {
        	if (action.getValue().toString().equals("Delete Room")) {
                details.getChildren().removeAll(buildingName, roomName, floorNumber, buildingSection, roomType);
                root.setPadding(new Insets(175, 275, 150, 275));
                details.setPadding(new Insets(10, 0, 160,  0));

        	}
        	if (action.getValue().toString().equals("Add Room")) {
                details.getChildren().removeAll(roomNumber, submit);
                details.getChildren().addAll(buildingName, roomNumber, roomName, floorNumber, buildingSection, roomType, submit);
                root.setPadding(new Insets(5, 275, 115, 275));
                details.setPadding(new Insets(10, 0, 5,  0));
        	}
        });
        
        submit.setOnAction(e -> {

    		// Create Room
        	if (action.getValue().toString().equals("Add Room")) {
        		RoomApiClient roomApiClient = new RoomApiClient();
        		BuildingApiClient buildingApiClient = new BuildingApiClient();
        		
        		Building[] allBuildings = buildingApiClient.getAll();
        		Building building = null;
        		
        		for (int i = 0; i < allBuildings.length; i++) {
        			if (allBuildings[i].getName().equals(buildingName.getText())) {
        				building = allBuildings[i];
        			}
        		}
        		
        		Room room = new Room(building, roomName.getText(), roomNumber.getText(), floorNumber.getText(), buildingSection.getText(), roomType.getText());
        		room = roomApiClient.create(room);
        		buildingName.clear();
        		roomName.clear();
        		roomNumber.clear();
        		floorNumber.clear();
        		buildingSection.clear();
        		roomType.clear();
	    		message.setText("Room Added");
        	}
        	
        	// Delete Room
        	if (action.getValue().toString().equals("Delete Room")) {
						        		
        		RoomApiClient roomApiClient = new RoomApiClient();
        		Room[] rooms = roomApiClient.getAll();
        		int roomID = 0;
        		
        		for (int i = 0; i < rooms.length; i++) {
        			if (rooms[i].getRoomNumber().equals(roomNumber.getText())) {
        				roomID = rooms[i].getID();
        			}
        		}
						        		
        		roomApiClient.delete(roomID);
        		roomNumber.clear();
        		message.setText("Room Deleted");
        	}
        });
    }
}