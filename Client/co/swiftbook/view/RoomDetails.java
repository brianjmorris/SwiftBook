package co.swiftbook.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import co.swiftbook.apiClient.BookingApiClient;
import co.swiftbook.apiClient.RoomApiClient;
import co.swiftbook.entity.Booking;
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

public class RoomDetails extends Application {
	
    public static void main(String[] args) {

    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(100, 225, 115, 225));
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
       
        TextField roomNum = new TextField();
        TextField date = new TextField();
        TextArea roomDetails = new TextArea("");
        Button search = new Button("Search");
        Button back = new Button("Back");
        HBox dateBox = new HBox();
        dateBox.setSpacing(15);
        
        viewTitle.getChildren().addAll(imageView, dashTitle);
        details.getChildren().addAll(roomNum, dateBox, roomDetails, search, back);
        
        ObservableList<Integer> yearOps = 
        	    FXCollections.observableArrayList(
        	        2019,
        	        2020,
        	        2021,
        	        2022,
        	        2023,
        	        2024
        	    );
        
        final ComboBox<Integer> year = new ComboBox(yearOps);
        
        ObservableList<Integer> monthOps = 
        	    FXCollections.observableArrayList(
        	        1,
        	        2,
        	        3,
        	        4,
        	        5,
        	        6,
        	        7,
        	        8,
        	        9,
        	        10,
        	        11,
        	        12
        	    );
        
        final ComboBox<Integer> month = new ComboBox(monthOps);
        
        ObservableList<Integer> dayOps = 
        	    FXCollections.observableArrayList(
        	        0,
        	        1,
        	        2,
        	        3,
        	        4,
        	        5,
        	        6,
        	        7,
        	        8,
        	        9,
        	        10,
        	        11,
        	        12,
        	        13,
        	        14,
        	        15,
        	        16,
        	        17,
        	        18,
        	        19,
        	        20,
        	        21,
        	        22,
        	        23,
        	        24,
        	        25,
        	        26,
        	        27,
        	        28,
        	        29,
        	        30,
        	        31
        	    );
        
        final ComboBox<Integer> day = new ComboBox(dayOps);
        year.setValue(2019);
        month.setValue(4);
        day.setValue(3);
        
        dateBox.getChildren().addAll(year, month, day);
        
        // Styling
        roomDetails.setWrapText(true);
        roomDetails.setVisible(false);
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        details.getStyleClass().add("vbox");
        details.setAlignment(Pos.CENTER);
        dateBox.setAlignment(Pos.CENTER);
        details.setPadding(new Insets(40, 0, 40, 0));
        details.setSpacing(60);
        dashTitle.getStyleClass().add("dashTitle");
        search.getStyleClass().add("dashButton");
        roomNum.getStyleClass().add("form");
        roomNum.setPromptText("Room Number");
        date.getStyleClass().add("form");
        date.setPromptText("Enter Date");
        roomDetails.getStyleClass().add("text-area");
        roomDetails.setMaxHeight(200);
        roomDetails.setPromptText("Click Search to Display Room Details");
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
        
        search.setOnAction(e -> {
            roomDetails.setVisible(true);
            
    		BookingApiClient bookingApiClient = new BookingApiClient();
    		
    		Booking[] allBookings = bookingApiClient.getAll();
    		StringBuilder str = new StringBuilder();
    		String newline = "\n";
    		    		
    		for (int i = 0; i < allBookings.length; i++  ) {
    			if (allBookings[i].getRoom().getRoomNumber().equals(roomNum.getText())) {
    				
    				int bookYear = allBookings[i].getStart().getYear();
    				int bookMonth = allBookings[i].getStart().getMonthValue();
    				int bookDay = allBookings[i].getStart().getDayOfMonth();

    				System.out.println(bookYear + ", " + bookMonth + ", " + bookDay);
    				
    				if (bookYear == year.getValue() && bookMonth == month.getValue() && bookDay == day.getValue()) {
            			roomDetails.appendText("Username: " + allBookings[i].getUser().getUsername() + newline + "Start Time: "  + allBookings[i].getStart().toString() + newline + "End Time: "  + allBookings[i].getEnd().toString());
            			roomDetails.appendText(newline + newline);
    				}
    			}
    		}
        }); 
    }
}