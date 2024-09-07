package gui;

import gui.util.Alerts;
import gui.util.DraggableMaker;

import gui.util.LoadView;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.StageStyle;


public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemCSTR;

    @FXML
    private MenuItem menuItemBATE;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private MenuItem menuItemPFR;

    @FXML
    private Button buttonRun;

    @FXML
    private Pane pane;

    private List<Rectangle> rectangles = new ArrayList<>();

    DraggableMaker draggableMaker = new DraggableMaker();

    public void onMenuItemBATEAction() {
        Rectangle rectangle = new Rectangle(100, 100, Color.RED);
        addRectangle(rectangle);
        draggableMaker.makeDraggable(rectangle, "/gui/BATEForm.fxml", "Enter Batelada data", (BATEFormController controller) -> {});
    }

    public void onMenuItemCSTRAction() {
        Rectangle rectangle = new Rectangle(100, 100, Color.BLUE);
        addRectangle(rectangle);
        draggableMaker.makeDraggable(rectangle, "/gui/CSTRForm.fxml", "Enter CSTR data", (CSTRFormController controller) -> {});
    }

    public void onMenuItemPFRAction() {
        Rectangle rectangle = new Rectangle(100, 100, Color.PINK);
        addRectangle(rectangle);
        draggableMaker.makeDraggable(rectangle, "/gui/PFRForm.fxml", "Enter PFR data", (PFRFormController controller) -> {});
    }

    private void addRectangle(Rectangle rectangle) {
        pane.getChildren().add(rectangle);
        rectangle.layoutXProperty().bind(pane.widthProperty().subtract(rectangle.getWidth()).divide(2));
        rectangle.layoutYProperty().bind(pane.heightProperty().subtract(rectangle.getHeight()).divide(2));

        rectangles.add(rectangle);

        ContextMenu contextMenu = new ContextMenu();

        MenuItem propertiesItem = new MenuItem("Propriedades");
        propertiesItem.setOnAction(event -> {
            LoadView view = new LoadView();
            view.loadView("/gui/PFRForm.fxml", "Enter PFR data", (PFRFormController controller) -> {}, StageStyle.DECORATED);
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> {
            pane.getChildren().remove(rectangle);
            rectangles.remove(rectangle);
        });


        contextMenu.getItems().addAll(propertiesItem, deleteItem);

        rectangle.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(rectangle, event.getScreenX(), event.getScreenY());
            }
        });
    }

    @FXML
    private void btSimulateAction() {

        if (rectangles.isEmpty()){
            Alerts.showAlert("Error", "Missing values", "Add at least one reactor before simulating", Alert.AlertType.ERROR);
            return;
        }

        try {
            LoadView view = new LoadView();
            String pythonPath = "venv/Scripts/python.exe";
            String pythonScriptPath = "src/model/simulator/Simulation.py";

            String[] command = {
                    pythonPath,
                    pythonScriptPath,
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                process.onExit();
                File file = new File("src/model/simulator/results.png");

                if (!file.exists()) {
                    Alerts.showAlert("Error", "Missing values", "Please enter the data in the reactors", Alert.AlertType.ERROR);
                    return;
                }
                view.loadView("/gui/GraphForm.fxml", "Results", (GraphFormController controller) -> {}, StageStyle.DECORATED);
            } else {
                System.out.println("Ocorreu um erro na execução do script Python. Código de saída: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
