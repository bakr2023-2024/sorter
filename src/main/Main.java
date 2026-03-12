package main;

import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private int width = 1280;
    private int height = 720;
    private GraphicsContext g;
    private Spinner<Integer> arraySizeSpinner;
    private ChoiceBox<String> sortAlgs;
    private Thread sortThread = null;
    private int[] original;
    private int[] arr;
    private int maxColWidth;
    private int maxColHeight;
    private double colWidth;

    public void renderArr() {
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
        g.setFill(Color.WHITE);
        for (int x = 0; x < arr.length; x++) {
            g.fillRect(x, maxColHeight - arr[x], colWidth, arr[x]);
        }
    }

    public Parent getAlgControls() {
        arraySizeSpinner = new Spinner<>(5, maxColWidth, 100);
        arraySizeSpinner.setEditable(true);
        sortAlgs = new ChoiceBox<>();
        for (SortingAlgs alg : SortingAlgs.values())
            sortAlgs.getItems().add(alg.toString());
        sortAlgs.setValue(SortingAlgs.SELECTION_SORT.toString());
        Button genBtn = new Button("Generate");
        genBtn.setOnAction(e -> {
            Random r = new Random();
            arr = new int[arraySizeSpinner.getValue()];
            colWidth = g.getCanvas().getWidth() / arr.length;
            for (int i = 0; i < arr.length; i++)
                arr[i] = r.nextInt(maxColHeight);
            Platform.runLater(this::renderArr);
        });
        Button sortBtn = new Button("Sort");
        sortBtn.setOnAction(e -> {

        });
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
                new Label("Sorter"),
                new Label("Sorting Algorithms"),
                arraySizeSpinner,
                genBtn,
                sortAlgs,
                sortBtn);
        return vbox;
    }
    @Override
    public void start(Stage stage) throws Exception {
        HBox main = new HBox();
        Canvas canvas = new Canvas(width * 0.9, height);
        g = canvas.getGraphicsContext2D();
        maxColWidth = (int) canvas.getWidth();
        maxColHeight = (int) canvas.getHeight();
        main.getChildren().addAll(canvas, getAlgControls());
        Scene scene = new Scene(main, width, height);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sorter");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
