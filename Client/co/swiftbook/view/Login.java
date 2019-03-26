package co.swiftbook.view;

import co.swiftbook.entity.User;
import co.swiftbook.apiClient.LoginApiClient;
import co.swiftbook.apiClient.UserApiClient;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.*;

public class Login extends Application {

	private static UserApiClient userApiClient;
	private static LoginApiClient loginApiClient;
	
    public static void main(String[] args) {
    	userApiClient = new UserApiClient();
    	loginApiClient = new LoginApiClient();
    	
    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(220, 0, 0, 0));
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        // Logo and Software Name
        // TODO change image to local file
        Image softwareName = new Image("co/swiftbook/view/img/swiftbook.png", 0, 115, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        
        // Register
        Button register = new Button("New organization? Register here.");
        
        // Credentials
        VBox credentials = new VBox();
        credentials.setPadding(new Insets(10, 10, 10, 10));
        credentials.setSpacing(15);
        
        TextField username = new TextField();
        // TODO limit password to 50 characters (may not be applicable here)
        PasswordField password = new PasswordField();
        Button login = new Button("Login");
        credentials.setPadding(new Insets(100, 0, 0, 0));
        root.setCenter(credentials);
        BorderPane.setMargin(credentials,  new Insets(10, 300, 0, 300));
        
        // Styling
        root.getStyleClass().add("root");
        username.setPromptText("Username");
        password.setPromptText("Password");
        credentials.getStyleClass().add("form");
        login.getStyleClass().add("customButton");
        register.getStyleClass().add("register");
     
        // Add credentials to group
        credentials.getChildren().addAll(username, password, login);
        root.setTop(imageView);
        root.setBottom(register);
        register.setPadding(new Insets(0, 0, 110, 330));
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Event Handling
        login.setOnMouseEntered(new EventHandler<Event>() {
            public void handle(Event e) {
                scene.setCursor(Cursor.HAND);
            }
        });
        
        login.setOnMouseExited(new EventHandler<Event>() {
            public void handle(Event e) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        
        register.setOnMouseEntered(new EventHandler<Event>() {
            public void handle(Event e) {
                scene.setCursor(Cursor.HAND);
            }
        });
        
        register.setOnMouseExited(new EventHandler<Event>() {
            public void handle(Event e) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        
        register.setOnAction(e -> {
            Registration registration = new Registration();
            registration.start(primaryStage);
        });
        
        login.setOnAction(e -> {
        	// Retrieve login credentials + validate
    		User currentUser = userApiClient.getByUsername(username.getText());
    		
    		if(currentUser == null) {
          		System.out.println("User not found");

        		username.clear();
        		password.clear();
          		return;
          	} else if (loginApiClient.login(currentUser, password.getText())) {
                Registration registration = new Registration();
                registration.start(primaryStage);
    		} else {
          		System.out.println("Username and password do not match");

        		password.clear();
    			return;
    		}
        });
    }
}