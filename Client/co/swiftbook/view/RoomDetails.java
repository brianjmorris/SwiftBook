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

public class RoomDetails extends Application {
	
    public static void main(String[] args) {

    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(100, 250, 115, 250));
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
        TextArea roomDetails = new TextArea("TeeiwfwandwaTeeiwfwandwadwdbwdpqwncekrbebfilevfleavveTeeiwfwandwadwdbwdpqwncekrbebfilevfleavveTeeiwfwandwadwdbwTeeiwfwandwaTeeiwfwandwadwdbwdpqwncekrbebfilevfleavveTeeiwfwandwadwdbwdpqwncekrbebfilevfleavveTeeiwfwandwadwdbwdpqwncekrbebfilevfleavvedwdbwdpqwncekrbebfilevfleavvedpqwncekrbebfilevfleavvedwdbwdpqwncekrbebfilevfleavve");
        Button search = new Button("Search");
        Button back = new Button("Back");
        
        viewTitle.getChildren().addAll(imageView, dashTitle);
        details.getChildren().addAll(roomNum, date, roomDetails, search, back);
        
        // Styling
        roomDetails.setWrapText(true);
        roomDetails.setVisible(false);
        viewTitle.getStyleClass().add("vbox");
        viewTitle.setAlignment(Pos.CENTER);
        details.getStyleClass().add("vbox");
        details.setAlignment(Pos.CENTER);
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
        });
        
    }
}