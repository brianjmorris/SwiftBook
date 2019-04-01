package co.swiftbook.view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.*;

public class UserDashboard extends Application {
	
    public static void main(String[] args) {

    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(170, 0, 175, 0));
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        // Logo and  Title
        Image softwareName = new Image("co/swiftbook/view/img/swiftbook.png", 0, 115, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        Text dashTitle = new Text("User Dashboard");
        VBox viewTitle = new VBox();
        
        // User Options
        VBox actions = new VBox();
        
        String test = Login.getOrganization();
        System.out.println(test);
        
        Button bookRoomBtn = new Button("Room Booking");
        Button detailsBtn = new Button("Display Meeting Details");
        Button logOut = new Button("Log Out");
        
        viewTitle.getChildren().addAll(imageView, dashTitle);
        actions.getChildren().addAll(bookRoomBtn, detailsBtn, logOut);

        // Styling
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        actions.getStyleClass().add("vbox");
        actions.setAlignment(Pos.CENTER);
        dashTitle.getStyleClass().add("dashTitle");
        bookRoomBtn.getStyleClass().add("dashButton");
        detailsBtn.getStyleClass().add("dashButton");
        logOut.getStyleClass().add("logOutButton");

        // Add To View
        root.setTop(viewTitle);
        root.setBottom(actions);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
        BorderPane.setAlignment(actions, Pos.TOP_CENTER);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Event Handling
        bookRoomBtn.setOnAction(e -> {
            UserBooking booking = new UserBooking();
            booking.start(primaryStage);
        });
         
        detailsBtn.setOnAction(e -> {
            RoomDetails details = new RoomDetails();
            details.start(primaryStage);
        });
        
        logOut.setOnAction(e -> {
            Login login = new Login();
            login.start(primaryStage);
        });
        
        // Cursor Changes
        bookRoomBtn.setOnMouseEntered(new EventHandler<Event>() {
            public void handle(Event e) { scene.setCursor(Cursor.HAND);}
        });
        bookRoomBtn.setOnMouseExited(new EventHandler<Event>() {
            public void handle(Event e) { scene.setCursor(Cursor.DEFAULT);}
        });
        detailsBtn.setOnMouseEntered(new EventHandler<Event>() {
            public void handle(Event e) { scene.setCursor(Cursor.HAND);}
        });
        detailsBtn.setOnMouseExited(new EventHandler<Event>() {
            public void handle(Event e) { scene.setCursor(Cursor.DEFAULT);}
        });
        logOut.setOnMouseEntered(new EventHandler<Event>() {
            public void handle(Event e) { scene.setCursor(Cursor.HAND);}
        });
        logOut.setOnMouseExited(new EventHandler<Event>() {
            public void handle(Event e) { scene.setCursor(Cursor.DEFAULT);}
        });
    }
}