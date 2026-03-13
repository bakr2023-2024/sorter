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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static int delay = 1;
    private int width = 1280;
    private int height = 720;
    private GraphicsContext g;
    private Spinner<Integer> arraySizeSpinner;
    private Spinner<Integer> delaySpinner;
    private ChoiceBox<String> sortAlgs;
    private Thread sortThread = null;
    private int[] arr;
    private int maxColWidth;
    private int maxColHeight;
    private double colWidth;
    private Sorter sorter = new Sorter(
            (int[] a, int i, int j) -> {
                Platform.runLater(() -> {
                    drawCol(i, a[i], Color.RED);
                    drawCol(j, a[j], Color.RED);
                });
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                }
                Platform.runLater(() -> renderArr(a));
            },
            (int[] a) -> {
                Platform.runLater(() -> renderArr(a));
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                }
            });

    private void drawCol(int x, int val, Color color) {
        g.setFill(color);
        g.fillRect(x * colWidth, height - val, colWidth, val);
    }

    public void renderArr(int[] a) {
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
        g.setFill(Color.WHITE);
        for (int x = 0; x < a.length; x++) {
            g.fillRect(x * colWidth, maxColHeight - a[x], colWidth, a[x]);
        }
    }

    public Parent getAlgControls() {
        arraySizeSpinner = new Spinner<>(5, maxColWidth, 100);
        arraySizeSpinner.setEditable(true);
        delaySpinner = new Spinner<>(1, 1000, 1);
        delaySpinner.setEditable(true);
        sortAlgs = new ChoiceBox<>();
        for (SortingAlgs alg : SortingAlgs.values())
            sortAlgs.getItems().add(alg.toString());
        sortAlgs.setValue(SortingAlgs.SELECTION_SORT.toString());
        Button genBtn = new Button("Generate");
        genBtn.setOnAction(e -> {
            genBtn.setDisable(true);
            Random r = new Random();
            arr = new int[arraySizeSpinner.getValue()];
            colWidth = g.getCanvas().getWidth() / arr.length;
            for (int i = 0; i < arr.length; i++)
                arr[i] = r.nextInt(maxColHeight);
            Platform.runLater(() -> {
                renderArr(arr);
                genBtn.setDisable(false);
            });
        });
        Button sortBtn = new Button("Sort");
        sortBtn.setOnAction(e -> {
            sorter.stop = false;
            sortBtn.setDisable(true);
            delay = delaySpinner.getValue();
            SortingAlgs alg = SortingAlgs.valueOf(sortAlgs.getValue());
            sortThread = new Thread(() -> {
                sorter.sort(arr, alg);
                sortBtn.setDisable(false);
            });
            sortThread.start();
        });
        Button stopBtn = new Button("Stop");
        stopBtn.setOnAction(e -> {
            if (sortThread != null) {
                sorter.stop = true;
                sortThread.interrupt();
            }
        });
        VBox vbox = new VBox();
        vbox.setMaxWidth(width * 0.1);
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
                new Label("Sorter"),
                new Label("Array Size"),
                arraySizeSpinner,
                genBtn,
                new Label("Sorting Algorithms"),
                sortAlgs,
                new Label("Delay"),
                delaySpinner,
                sortBtn, stopBtn);
        return vbox;
    }
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane main = new BorderPane();
        Canvas canvas = new Canvas(width * 0.9, height);
        g = canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());
        maxColWidth = (int) canvas.getWidth();
        maxColHeight = (int) canvas.getHeight();
        main.setRight(getAlgControls());
        main.setCenter(canvas);
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
