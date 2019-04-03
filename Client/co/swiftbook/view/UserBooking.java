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
import javafx.scene.layout.HBox;
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
        HBox dateBox1 = new HBox();
        HBox dateBox2 = new HBox();
        
        ObservableList<Integer> yearOps = 
        	    FXCollections.observableArrayList(
        	        2019,
        	        2020,
        	        2021,
        	        2022,
        	        2023,
        	        2024,
        	        2025
        	    );
        
        final ComboBox<Integer> year = new ComboBox(yearOps);
        
        ObservableList<String> monthOps = 
        	    FXCollections.observableArrayList(
        	        "01",
        	        "02",
        	        "03",
        	        "04",
        	        "05",
        	        "06",
        	        "07",
        	        "08",
        	        "09",
        	        "10",
        	        "11",
        	        "12"
        	    );
        
        final ComboBox<String> month = new ComboBox(monthOps);
        
        ObservableList<String> dayOps = 
        	    FXCollections.observableArrayList(
        	        "00",
        	        "01",
        	        "02",
        	        "03",
        	        "04",
        	        "05",
        	        "06",
        	        "07",
        	        "08",
        	        "09",
        	        "10",
        	        "11",
        	        "12",
        	        "13",
        	        "14",
        	        "15",
        	        "16",
        	        "17",
        	        "18",
        	        "19",
        	        "20",
        	        "21",
        	        "22",
        	        "23",
        	        "24",
        	        "25",
        	        "26",
        	        "27",
        	        "28",
        	        "29",
        	        "30",
        	        "31"
        	    );
        
        ObservableList<String> hourOps = 
        	    FXCollections.observableArrayList(
            	        "00",
            	        "01",
            	        "02",
            	        "03",
            	        "04",
            	        "05",
            	        "06",
            	        "07",
            	        "08",
            	        "09",
            	        "10",
            	        "11",
            	        "12",
            	        "13",
            	        "14",
            	        "15",
            	        "16",
            	        "17",
            	        "18",
            	        "19",
            	        "20",
            	        "21",
            	        "22",
            	        "23",
            	        "24"
        	    );
        
        final ComboBox<String> day = new ComboBox(dayOps);
        
        ObservableList<String> minOps = 
        	    FXCollections.observableArrayList(
        	        "00",
        	        "30"
        	    );
        
        final ComboBox<String> startMinute = new ComboBox(minOps);
        final ComboBox<String> endMinute = new ComboBox(minOps);
        
        Text until = new Text(" until ");
        
        final ComboBox<String> startHour = new ComboBox(hourOps);
        final ComboBox<String> endHour = new ComboBox(hourOps);
        
        // Default Values
        year.setValue(2019);
        month.setValue("04");
        day.setValue("03");
        startHour.setValue("08");
        startMinute.setValue("30");
        endHour.setValue("09");
        endMinute.setValue("00");
        
        viewTitle.getChildren().addAll(imageView, dashTitle, message);
        dateBox1.getChildren().addAll(year, month, day);
        dateBox2.getChildren().addAll(startHour, startMinute, until, endHour, endMinute);
        details.getChildren().addAll( action, roomNumber, dateBox1, dateBox2, submit);
        
        VBox wrapper = new VBox();
        wrapper.getChildren().addAll(details, back);
        
        // Styling
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        details.getStyleClass().add("vbox");
        details.setAlignment(Pos.CENTER);
        dateBox1.setAlignment(Pos.CENTER);
        dateBox2.setAlignment(Pos.CENTER);
        wrapper.setAlignment(Pos.CENTER);
        dateBox1.setSpacing(2);
        dateBox2.setSpacing(2);
        until.getStyleClass().add("message");
        details.setPadding(new Insets(40, 0, 100, 0));
        details.setSpacing(60);
        dashTitle.getStyleClass().add("dashTitle");
        submit.getStyleClass().add("dashButton");
        message.getStyleClass().add("message");
        action.getStyleClass().add("form");
        roomNumber.getStyleClass().add("form");
        roomNumber.setPromptText("Room Number");
        back.getStyleClass().add("logOutButton");
        
        // Add To View
        root.setTop(viewTitle);
        root.setBottom(wrapper);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
        BorderPane.setAlignment(wrapper, Pos.TOP_CENTER);
        
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
        		
        		String userStartTime = year.getValue().toString() + "-" + month.getValue()+ "-" + day.getValue() + " " + startHour.getValue() + ":" + startMinute.getValue() + ":00";
        		String userEndTime = year.getValue().toString() + "-" + month.getValue() + "-" + day.getValue()+ " " + endHour.getValue() + ":" + endMinute.getValue() + ":00";

        		Booking booking = new Booking(Login.getUser(), room, LocalDateTime.parse(userStartTime, formatter), LocalDateTime.parse((userEndTime), formatter));
        		
        		booking = bookingApiClient.create(booking);
        		roomNumber.clear();
                year.setValue(2019);
                month.setValue("04");
                day.setValue("03");
                startHour.setValue("08");
                startMinute.setValue("30");
                endHour.setValue("09");
                endMinute.setValue("00");
        		message.setText("You have booked the room.");
        	}      
        	
        	if (action.getValue().toString().equals("Release Booking")) {
        		BookingApiClient bookingApiClient = new BookingApiClient();
        		RoomApiClient roomApiClient = new RoomApiClient();
        		
        		int bookingID = 0;
        		
        		// Get Booking ID
        		Booking[] allBookings = bookingApiClient.getAll();
        		Room[] allRooms = roomApiClient.getAll();
        		
        		String userStartTimeStr = year.getValue().toString() + "-" + month.getValue() + "-" + day.getValue() + " " + startHour.getValue() + ":" + startMinute.getValue() + ":00";
        		String userEndTimeStr = year.getValue().toString() + "-" + month.getValue()+ "-" + day.getValue()+ " " + endHour.getValue()+ ":" + endMinute.getValue() + ":00";

        		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        		LocalDateTime userStartTime = LocalDateTime.parse(userStartTimeStr, formatter);
        		LocalDateTime userEndTime = LocalDateTime.parse(userStartTimeStr, formatter);
        		
        		for (int i = 0; i < allRooms.length; i++) {
        			if (allBookings[i].getRoom().getRoomNumber().equals(roomNumber.getText())) {	
            				bookingID = allBookings[i].getID();
        			}
        		}
        		
        		bookingApiClient.delete(bookingID);
        		roomNumber.clear();
                year.setValue(2019);
                month.setValue("04");
                day.setValue("03");
                startHour.setValue("08");
                startMinute.setValue("30");
                endHour.setValue("09");
                endMinute.setValue("00");
        		message.setText("You have removed the booking.");
        	}    
        });
    }
}