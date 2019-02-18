package co.swiftbook.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.*;
 
public class LoginScreen extends Application {
	
    public static void main(String[] args) {
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
        Group elements = new Group();
        
        // Logo and Software Name
        Image softwareName = new Image("https://raw.githubusercontent.com/brianjmorris/SwiftBook/master/Client/co/swiftbook/view/img/swiftbook.png?token=Agadc0FD5YbaHysmv4fFWzXJI-IjKDRDks5cZiIVwA%3D%3D", 0, 115, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        
        // Register
        Button register = new Button("Register");
        
        // Credentials
        VBox credentials = new VBox();
        credentials.setPadding(new Insets(10, 10, 10, 10));
        credentials.setSpacing(15);
        
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        Button login = new Button("Login");
        credentials.setPadding(new Insets(100, 0, 0, 0));
        root.setCenter(credentials);
        root.setMargin(credentials,  new Insets(10, 300, 0, 300));
        
        // Styling
        root.getStyleClass().add("root");
        username.setPromptText("Username");
        password.setPromptText("Password");
        credentials.getStyleClass().add("credentials");
        login.getStyleClass().add("login");
        register.getStyleClass().add("register");
     
        // Add credentials to group
        credentials.getChildren().addAll(username, password, login);
        root.setTop(imageView);
        root.setAlignment(imageView, Pos.TOP_CENTER);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}