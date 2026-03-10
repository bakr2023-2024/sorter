package main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        VBox vbox = new VBox(new Label("Hello World"));
        Scene scene = new Scene(vbox, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("Sorter");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
