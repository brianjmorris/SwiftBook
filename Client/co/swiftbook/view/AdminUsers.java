package co.swiftbook.view;

import co.swiftbook.apiClient.BuildingApiClient;
import co.swiftbook.apiClient.OrganizationApiClient;
import co.swiftbook.apiClient.RoomApiClient;
import co.swiftbook.apiClient.UserApiClient;
import co.swiftbook.entity.Building;
import co.swiftbook.entity.Room;
import co.swiftbook.entity.User;
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
import javafx.scene.control.PasswordField;
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

public class AdminUsers extends Application {
	
    public static void main(String[] args) {

    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook | User Administration");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(2, 275, 115, 275));
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        // Logo and  Title
        Image softwareName = new Image("co/swiftbook/view/img/swiftbook.png", 0, 115, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        Text dashTitle = new Text("Add or Delete Users");
        VBox viewTitle = new VBox();
        viewTitle.requestFocus();

        // Options
        VBox details = new VBox();
        VBox logBox = new VBox();
        
        ObservableList<String> options = 
        	    FXCollections.observableArrayList(
        	        "Add User",
        	        "Delete User"
        	    );
        
        final ComboBox<String> action = new ComboBox(options);
        action.setValue("Add User");
       
        Text message = new Text("");
        TextField username = new TextField();
        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField email = new TextField();
        RadioButton isAdmin = new RadioButton("Admin Access");
        
        PasswordField userPassword = new PasswordField();
        Button back = new Button("Back");
        back.setLayoutX(100);
        Button submit = new Button("Submit");
        
        viewTitle.getChildren().addAll(imageView, dashTitle);
        details.getChildren().addAll(message, action, username, firstName, lastName, email, userPassword, isAdmin, submit);
        logBox.getChildren().addAll(details, back);

        // Styling
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        details.getStyleClass().add("vbox");
        details.setAlignment(Pos.CENTER);
        logBox.setAlignment(Pos.CENTER);
        details.setPadding(new Insets(20, 0, 17, 0));
        details.setSpacing(60);
        dashTitle.getStyleClass().add("dashTitle");
        submit.getStyleClass().add("dashButton");
        action.getStyleClass().add("form");
        action.setValue("Add User");
        username.getStyleClass().add("form");
        username.setPromptText("Username");
        firstName.getStyleClass().add("form");
        firstName.setPromptText("First Name");
        lastName.getStyleClass().add("form");
        lastName.setPromptText("Last Name");
        email.getStyleClass().add("form");
        email.setPromptText("Email");
        userPassword.getStyleClass().add("form");
        userPassword.setPromptText("User Password");
        back.getStyleClass().add("logOutButton");
        message.getStyleClass().add("message");
        isAdmin.getStyleClass().add("toggleButton");
        
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
        	if (action.getValue().toString().equals("Delete User")) {
                details.getChildren().removeAll(firstName, lastName, email, userPassword, isAdmin);
                root.setPadding(new Insets(150, 275, 115, 275));
                details.setPadding(new Insets(40, 0, 155, 0));
        	}
        	
        	if (action.getValue().toString().equals("Add User")) {
                details.getChildren().removeAll(submit, back);
                details.getChildren().addAll(firstName, lastName, email, userPassword, isAdmin, submit, back);
                root.setPadding(new Insets(2, 275, 115, 275));
                details.setPadding(new Insets(15, 0, 40, 0));
        	}
        });
        
        submit.setOnAction(e -> {

    		// Create User
        	if (action.getValue().toString().equals("Add User")) {
        		UserApiClient userApiClient = new UserApiClient();
        		
        		Boolean admin = false;
        		
        		if (isAdmin.isSelected()) {
        			admin = true;
        		}
        		
        		User newUser = new User(username.getText(), email.getText(), firstName.getText(), lastName.getText(), Login.getOrganizationObj(), admin);
        		newUser = userApiClient.create(newUser);        		
        		
        		username.clear();
        		email.clear();
        		firstName.clear();
        		lastName.clear();
        		message.setText("User Added");
        	}

        	// Delete User
			if (action.getValue().toString().equals("Delete User")) {
        		UserApiClient userApiClient = new UserApiClient();
        		
        		User[] allUsers = userApiClient.getAll();
        		int userID = 0;
        		
        		for (int i = 0; i < allUsers.length; i++) {
        			if (username.getText().equals(allUsers[i].getUsername())) {
        				userID = allUsers[i].getID();
        			}
        		}
        		
        		userApiClient.delete(userID);
        		username.clear();
				message.setText("User Deleted");
			}
		});
    }
}