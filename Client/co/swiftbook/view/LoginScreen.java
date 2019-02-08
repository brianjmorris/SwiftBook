package co.swiftbook.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        Group elements = new Group();
        
        // Logo and Software Name
        Image softwareName = new Image("http://cdn.onlinewebfonts.com/svg/img_377100.png", 20, 0, true, false);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        
        // Credentials
        VBox credentials = new VBox();
        credentials.setPadding(new Insets(10, 10, 10, 10));
        credentials.setSpacing(15);
        
        TextField username = new TextField();
        TextField password = new TextField();
        Button login = new Button("Login");
        
        // Styling
        root.getStyleClass().add("root");
        username.setPromptText("Username");
        password.setPromptText("Password");
        credentials.getStyleClass().add("credentials");
        login.getStyleClass().add("login");
     
        // Add credentials to group
        credentials.getChildren().addAll(imageView, username, password, login);
        elements.getChildren().addAll(credentials);
        
        // Add all elements to scene
        root.getChildren().add(elements);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}