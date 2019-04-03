package co.swiftbook.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import co.swiftbook.apiClient.BookingApiClient;
import co.swiftbook.apiClient.RoomApiClient;
import co.swiftbook.entity.Booking;
import co.swiftbook.entity.Building;
import co.swiftbook.entity.Room;
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
        primaryStage.setTitle("SwiftBook | Room Booking");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(100, 275, 100, 275));
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
        
        	final ComboBox<String> action = new ComboBox(options);
        	action.setValue("Book Room");
       
        Text message = new Text("");
        TextField roomNumber = new TextField();
        TextField startTime = new TextField();
        TextField endTime = new TextField();
        Button back = new Button("Back");
        Button submit = new Button("Submit");
        
        viewTitle.getChildren().addAll(imageView, dashTitle, message);
        details.getChildren().addAll( action, roomNumber, startTime, endTime, submit, back);

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
        roomNumber.getStyleClass().add("form");
        roomNumber.setPromptText("Room Number");
        startTime.getStyleClass().add("form");
       startTime.setPromptText("Start Time [Format: 2019-04-02 11:30:00]");
        endTime.getStyleClass().add("form");
        endTime.setPromptText("End Time [Format: 2019-04-02 12:30:00]");
        back.getStyleClass().add("logOutButton");
        
        // Add To View
        root.setTop(viewTitle);
        root.setBottom(details);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
        BorderPane.setAlignment(details, Pos.TOP_CENTER);
        
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
        
        submit.setOnAction(e -> {
        	if (action.getValue().toString().equals("Book Room")) {
        		BookingApiClient bookingApiClient = new BookingApiClient();
        		RoomApiClient roomApiClient = new RoomApiClient();
        		Room[] allRooms = roomApiClient.getAll();
        		Room room = null;

        		for (int i = 0; i < allRooms.length; i++) {
        			if (roomNumber.getText().equals(allRooms[i].getRoomNumber())) {
        				room = allRooms[i];
        				System.out.println("Test");
        			}
        		}
        		
        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        		
        		Booking booking = new Booking(Login.getUser(), room, LocalDateTime.parse(startTime.getText(), formatter), LocalDateTime.parse(endTime.getText(), formatter));
        		
        		booking = bookingApiClient.create(booking);
        		roomNumber.clear();
        		startTime.clear();
        		endTime.clear();
        		message.setText("You have booked the room.");
        	}      
        	
        	if (action.getValue().toString().equals("Release Booking")) {
        		BookingApiClient bookingApiClient = new BookingApiClient();
        		RoomApiClient roomApiClient = new RoomApiClient();
        		
        		int bookingID = 0;
        		
        		// Get booking ID
        		Booking[] allBookings = bookingApiClient.getAll();
        		Room[] allRooms = roomApiClient.getAll();
        		
        		for (int i = 0; i < allRooms.length; i++) {
        			if (allBookings[i].getRoom().getRoomNumber().equals(roomNumber.getText())) {
        				bookingID = allBookings[i].getID();
        			}
        		}
        		
        		roomNumber.clear();
        		startTime.clear();
        		endTime.clear();
        		bookingApiClient.delete(bookingID);
        		message.setText("You have removed the booking.");
        	}    
        });
    }
}