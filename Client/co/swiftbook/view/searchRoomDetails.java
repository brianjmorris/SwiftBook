package co.swiftbook.view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.*;

public class searchRoomDetails extends Application {
	
    public static void main(String[] args) {

    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(170, 200, 115, 200));
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        // Logo and  Title
        Image softwareName = new Image("co/swiftbook/view/img/swiftbook.png", 0, 115, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        Text dashTitle = new Text("Search Meeting Details");
        VBox viewTitle = new VBox();
        viewTitle.requestFocus();

        // User Options
        VBox details = new VBox();
       
        TextField roomID = new TextField();
        TextArea roomDetails = new TextArea();
        Button search = new Button("Search");
        
        viewTitle.getChildren().addAll(imageView, dashTitle);
        details.getChildren().addAll(roomID, roomDetails, search);

        // Styling
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        details.getStyleClass().add("vbox");
        details.setAlignment(Pos.CENTER);
        details.setPadding(new Insets(40, 0, 40, 0));
        details.setSpacing(60);
        dashTitle.getStyleClass().add("dashTitle");
        search.getStyleClass().add("dashButton");
        roomID.getStyleClass().add("form");
        roomID.setPromptText("Room ID");
        roomDetails.getStyleClass().add("form");
        roomDetails.setMaxHeight(200);
        roomDetails.setPromptText("Click Search to Display Room Details");
        
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