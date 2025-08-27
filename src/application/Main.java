package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main-movie-browser.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Movie Browser");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


