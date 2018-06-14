package seng202.team2.controller;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller that create new stage that shows the progress bar
 * @author jmo146, hta55, abr120, esy14, ewi32, jha236
 */
public class ProgressBar {

    private Stage stage;

    javafx.scene.control.ProgressBar pb;


    /**
     * Creates and displays the stage containing a progress bar.
     */
    public void start() throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 200);
        Stage stage = new Stage();
        stage.setScene(scene);
        this.stage = stage;
        stage.setTitle("Welcome to CADMS");
        Pane p = new Pane();
        p.setStyle("-fx-background-color: white");
        Label label = new Label("LOADING...");
        p.getChildren().add(label);
        label.setLayoutY(50);
        label.setLayoutX(219);
        pb = new javafx.scene.control.ProgressBar(0);
        pb.setStyle("-fx-border-color: black;" +
                "-fx-border-width: 3px;" +
                "-fx-border-radius: 4px;" +
                "-fx-accent: limegreen;");
        p.getChildren().add(pb);
        pb.setPrefSize(426,30);
        pb.setLayoutX(44);
        pb.setLayoutY(130);
        scene.setRoot(p);
        stage.show();
    }


    void hideStage(){
        stage.hide();
    }

}