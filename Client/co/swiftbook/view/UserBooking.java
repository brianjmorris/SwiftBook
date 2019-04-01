package co.swiftbook.view;

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

public class UserBooking extends Application {
	
    public static void main(String[] args) {

    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(170, 275, 100, 275));
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        // Logo and  Title
        Image softwareName = new Image("co/swiftbook/view/img/swiftbook.png", 0, 115, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        Text dashTitle = new Text("Room Booking");
        VBox viewTitle = new VBox();
        viewTitle.requestFocus();

        // User Options
        VBox details = new VBox();
        
        ObservableList<String> options = 
        	    FXCollections.observableArrayList(
        	        "Book Room",
        	        "Release Booking"
        	    );
        
        	final ComboBox action = new ComboBox(options);
       
        Text message = new Text("");
        TextField roomName = new TextField();
        TextField time = new TextField();
        Button back = new Button("Back");
        Button submit = new Button("Submit");
        
        viewTitle.getChildren().addAll(imageView, dashTitle);
        details.getChildren().addAll(message, action, roomName, time, submit, back);

        // Styling
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        details.getStyleClass().add("vbox");
        details.setAlignment(Pos.CENTER);
        details.setPadding(new Insets(40, 0, 40, 0));
        details.setSpacing(60);
        dashTitle.getStyleClass().add("dashTitle");
        submit.getStyleClass().add("dashButton");
        message.getStyleClass().add("message");
        action.getStyleClass().add("form");
        roomName.getStyleClass().add("form");
        roomName.setPromptText("Room Number");
        time.getStyleClass().add("form");
        time.setPromptText("Meeting Date/Time");
        back.getStyleClass().add("logOutButton");
        
        // Add To View
        root.setTop(viewTitle);
        root.setBottom(details);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
        BorderPane.setAlignment(details, Pos.TOP_CENTER);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Event Handling
        
        
    }
}