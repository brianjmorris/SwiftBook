package co.swiftbook.view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.*;

public class AdminDashboard extends Application {

    public static void main(String[] args) {

    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(170, 0,  150, 0));
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        // Logo and  Title
        Image softwareName = new Image("co/swiftbook/view/img/swiftbook.png", 0, 115, true, true);
        ImageView imageView = new ImageView();
        imageView.setImage(softwareName);
        Text dashTitle = new Text("Admin Dashboard");
        VBox viewTitle = new VBox();
        
        // User Options
        VBox userActions = new VBox();
        VBox adminActions = new VBox();
        HBox actions = new HBox();
        
        Button bookRoomBtn = new Button("Book Room");
        Button releaseRoomBtn = new Button("Release Booking");
        Button detailsBtn = new Button("Find Meeting Details");
        Button logOut = new Button("Log Out");
        
        Button userBtn = new Button("Add or Delete User");
        Button roomBtn = new Button("Add or Delete Room");
        
        viewTitle.getChildren().addAll(imageView, dashTitle);
        adminActions.getChildren().addAll(userBtn, roomBtn, bookRoomBtn);
        userActions.getChildren().addAll(releaseRoomBtn, detailsBtn, logOut);
        actions.getChildren().addAll(adminActions, userActions);

        // Styling
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        userActions.getStyleClass().add("vbox");
        userActions.setAlignment(Pos.CENTER_LEFT);
        adminActions.getStyleClass().add("vbox");
        adminActions.setAlignment(Pos.CENTER_LEFT);
        actions.getStyleClass().add("vbox");
        actions.setAlignment(Pos.CENTER);
        
        dashTitle.getStyleClass().add("dashTitle");
        bookRoomBtn.getStyleClass().add("dashButton");
        releaseRoomBtn.getStyleClass().add("dashButton");
        userBtn.getStyleClass().add("dashButton");
        roomBtn.getStyleClass().add("dashButton");
        detailsBtn.getStyleClass().add("dashButton");
        logOut.getStyleClass().add("logOutButton");

        // Add To View
        root.setTop(viewTitle);
        root.setBottom(actions);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
        BorderPane.setAlignment(userActions, Pos.TOP_CENTER);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Event Handling
       
        userBtn.setOnAction(e -> {
            AdminUsers view = new AdminUsers();
            view.start(primaryStage);
        });
        
        roomBtn.setOnAction(e -> {
            AdminRooms booking = new AdminRooms();
            booking.start(primaryStage);
        });
         
        bookRoomBtn.setOnAction(e -> {
            UserBooking view = new UserBooking();
            view.start(primaryStage);
        });
         
        detailsBtn.setOnAction(e -> {
            RoomDetails view = new RoomDetails();
            view.start(primaryStage);
        });
        
        logOut.setOnAction(e -> {
            Login view = new Login();
            view.start(primaryStage);
        });
    }
}