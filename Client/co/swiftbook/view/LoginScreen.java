package co.swiftbook.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.*;
 
public class LoginScreen extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    // This is a comment, and I am commenting my java code 
    // And thank you for the assignment files, I much appreciate i
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("Login Screen");
        Group elements = new Group();

        // Logo and Software Name
        
        
        // Input Fields
        TextField username = new TextField();
        elements.getChildren().add(username);
        
        // Buttons

        
        StackPane root = new StackPane();
        root.getChildren().add(elements);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}