package co.swiftbook.view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.*;

public class Registration extends Application {
	
    final BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1000, 800);
	
    public static void main(String[] args) {
    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook | Register");
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        // View Name
        Text viewTitle = new Text("Registration");
        BorderPane.setAlignment(viewTitle, Pos.CENTER);
        root.setTop(viewTitle);
        
        // Organization Details
        VBox organizationDetails = new VBox();
        viewTitle.requestFocus();
        
        TextField orgName = new TextField();
        orgName.setPromptText("Organization Name");
        Text adminPrompt = new Text("Administrator Details");
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField email = new TextField();
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        email.setPromptText("Email");
        
        // Submit
        Button submit = new Button("Submit");
        root.setBottom(submit);
        BorderPane.setAlignment(submit, Pos.CENTER);
        BorderPane.setMargin(submit, new Insets(0, 0, 75, 0));
        
        // Styling
        root.setCenter(organizationDetails);
        BorderPane.setMargin(organizationDetails,  new Insets(10, 300, 0, 300));
        root.getStyleClass().add("root");
        root.setPadding(new Insets(150, 0, 0, 0));
        
        organizationDetails.setPadding(new Insets(30, 10, 30, 10));
        organizationDetails.setSpacing(15);
        
        viewTitle.getStyleClass().add("staticText");
        organizationDetails.getStyleClass().add("form");
        submit.getStyleClass().add("customButton");
        submit.setFont( new Font("Raleway", 24));
        adminPrompt.getStyleClass().add("promptText");
        
        // Add company details to group
        organizationDetails.getChildren().addAll(orgName, adminPrompt, firstName, lastName, email, username, password);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Event Handling
        submit.setOnMouseEntered(new EventHandler<Event>() {
            public void handle(Event e) {
                scene.setCursor(Cursor.HAND);
            }
        });
        
        submit.setOnMouseExited(new EventHandler<Event>() {
            public void handle(Event e) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
    }

	public Pane getRoot() {
		return root;
	}
	
	public Scene getScene() {
		return scene;
	}
}