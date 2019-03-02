package co.swiftbook.view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.*;

// TODO the term/title 'Registration' may be more applicable to when an (admin) account is created. Maybe we can name this 'NewBuilding' or something?
public class Registration extends Application {
	
    final BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 1000, 800);
	
    public static void main(String[] args) {
    	launch(args);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
    	
    	// View title
        primaryStage.setTitle("SwiftBook | Register");
        root.setPadding(new Insets(150, 0, 0, 0));
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        Group elements = new Group();
        
        // View Name
        Text viewTitle = new Text("Registration");
        BorderPane.setAlignment(viewTitle, Pos.CENTER);
        root.setTop(viewTitle);
        
        // Organization Details
        VBox organizationDetails = new VBox();
        organizationDetails.setPadding(new Insets(10, 10, 10, 10));
        organizationDetails.setSpacing(15);
        root.setCenter(organizationDetails);
        root.setMargin(organizationDetails,  new Insets(10, 300, 0, 300));
        organizationDetails.setPadding(new Insets(100, 0, 0, 0));
        
        TextField companyName = new TextField();
        companyName.setPromptText("Organization Name");
        TextField contactEmail = new TextField();
        contactEmail.setPromptText("Contact Email");
        TextField address = new TextField();
        address.setPromptText("Address");

       // Toggle group for company size
        HBox toggles = new HBox();
        toggles.setSpacing(60);
        final ToggleGroup organizationSize = new ToggleGroup();
        VBox toggleTitleBox = new VBox();
        Text toggleTitle = new Text("# of Employees");
        toggleTitleBox.getChildren().add(toggleTitle);
        toggleTitleBox.setPadding(new Insets(10, 0, 5, 0));
        RadioButton rb1 = new RadioButton("< 50");
        RadioButton rb2 = new RadioButton("50 - 500");
        RadioButton rb3 = new RadioButton("> 500");
        toggles.getChildren().addAll(rb1, rb2, rb3);

        rb1.setToggleGroup(organizationSize);
        rb2.setToggleGroup(organizationSize);
        rb3.setToggleGroup(organizationSize);
        rb1.setSelected(true);
        
        // Submit
        Button submit = new Button("Submit");
        root.setBottom(submit);
        BorderPane.setAlignment(submit, Pos.CENTER);
        BorderPane.setMargin(submit, new Insets(0, 0, 75, 0));
        
        // Styling
        root.getStyleClass().add("root");
        viewTitle.getStyleClass().add("staticText");
        toggleTitle.getStyleClass().add("promptText");
        organizationDetails.getStyleClass().add("form");
        submit.getStyleClass().add("customButton");
        submit.setFont( new Font("Raleway", 24));
        rb1.getStyleClass().add("toggleButton");
        rb2.getStyleClass().add("toggleButton");
        rb3.getStyleClass().add("toggleButton");
        
        // Add company details to group
        organizationDetails.getChildren().addAll(companyName, address, contactEmail, toggleTitleBox, toggles);
        
        // Event Handling
        // Change mouse to hand icon on button hover
        submit.setOnMouseEntered(new EventHandler() {
            public void handle(Event e) {
                scene.setCursor(Cursor.HAND);
            }
        });
        
        submit.setOnMouseExited(new EventHandler() {
            public void handle(Event e) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	public Pane getRoot() {
		return root;
	}
	
	public Scene getScene() {
		return scene;
	}
	
}