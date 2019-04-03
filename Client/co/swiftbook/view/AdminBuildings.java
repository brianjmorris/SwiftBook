package co.swiftbook.view;

import co.swiftbook.apiClient.BuildingApiClient;
import co.swiftbook.apiClient.OrganizationApiClient;
import co.swiftbook.entity.Building;
import co.swiftbook.entity.Organization;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.*;

public class AdminBuildings extends Application {
	
    public static void main(String[] args) {

    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook | Building Administration");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(100, 275, 115, 275));
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        // Logo and  Title
        Image softwareName = new Image("co/swiftbook/view/img/swiftbook.png", 0, 115, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        Text dashTitle = new Text("Add or Delete Buildings");
        VBox viewTitle = new VBox(); 
        viewTitle.requestFocus();

        // User Options
        VBox details = new VBox();
        VBox logBox = new VBox();
        
        ObservableList<String> options = 
        	    FXCollections.observableArrayList(
        	        "Add Building",
        	        "Delete Building"
        	    );
        
        final ComboBox<String> action = new ComboBox(options);
        action.setValue("Add Building");
       
        TextField buildingName = new TextField();
        Text message = new Text("");
        TextField address = new TextField();
        RadioButton wheelchairAccess = new RadioButton("Wheelchair Accessible");
        Button back = new Button("Back");
        Button submit = new Button("Submit");
        
        viewTitle.getChildren().addAll(imageView, dashTitle, message);
        details.getChildren().addAll(action, buildingName, address, wheelchairAccess, submit);
        logBox.getChildren().addAll(details, back);

        // Styling
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        details.getStyleClass().add("vbox");
        details.setAlignment(Pos.CENTER);
        logBox.setAlignment(Pos.CENTER);
        details.setPadding(new Insets(40, 0, 85, 0));
        details.setSpacing(70);
        dashTitle.getStyleClass().add("dashTitle");
        submit.getStyleClass().add("dashButton");
        action.getStyleClass().add("form");
        buildingName.getStyleClass().add("form");
        buildingName.setPromptText("Building Name");
        address.getStyleClass().add("form");
        address.setPromptText("Address");
        message.getStyleClass().add("message");
        back.getStyleClass().add("logOutButton");
        wheelchairAccess.getStyleClass().add("toggleButton");
        
        // Add To View
        root.setTop(viewTitle);
        root.setBottom(logBox);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
        BorderPane.setAlignment(logBox, Pos.TOP_CENTER);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Event Handling
        action.setOnAction(e -> {
        	if (action.getValue().toString().equals("Delete Building")) {
                details.getChildren().removeAll(address, wheelchairAccess);
                details.setPadding(new Insets(40, 0, 203, 0));
        	}
        	if (action.getValue().toString().equals("Add Building")) {
                details.getChildren().removeAll(submit);
                details.getChildren().addAll(address, wheelchairAccess, submit);
                details.setPadding(new Insets(40, 0, 85, 0));
        	}
        });
        
        submit.setOnAction( e -> {
        	
        });
        
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
        
        submit.setOnAction(e -> {
    		BuildingApiClient buildingApiClient = new BuildingApiClient();

    		// Create Building
        	if (action.getValue().toString().equals("Add Building")) {
	    		Boolean wheelchairBool;
	    		if (wheelchairAccess.isSelected()) {
	    			wheelchairBool = true;
	    		}
	    		else {
	    			wheelchairBool = false;
	    		}
	
	    		Building building = new Building(Login.getOrganizationObj(), buildingName.getText(), address.getText(), wheelchairBool);
	    		building = buildingApiClient.create(building);
	    		
	    		buildingName.clear();
	    		address.clear();
	    		message.setText("New Building Created");
        	}
        	
        	// Delete Building
        	if (action.getValue().toString().equals("Delete Building")) {
        		int buildingID = 0;
        		
        		Building[] allBuildings = buildingApiClient.getAll();
        		for (int i = 0; i < allBuildings.length; i++) {
        			if (allBuildings[i].getName().equals(buildingName.getText())) {
        				buildingID = allBuildings[i].getID();
        			}
        		}
        		buildingApiClient.delete(buildingID);
        		buildingName.clear();
        		message.setText("Building Deleted");
        	}
        });
    }
}