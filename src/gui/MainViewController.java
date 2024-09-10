package gui;

import gui.util.Alerts;
import gui.util.DraggableMaker;

import gui.util.LoadView;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;

import java.util.*;

import javafx.stage.Stage;
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

    private ImageView lastAddedImageView;
    private Map<String, List<String>> reactorDataMap = new HashMap<>();

    DraggableMaker draggableMaker = new DraggableMaker();


    public void onMenuItemBATEAction() {
        addReactor("/images/BATE.png");
        String reactorID = lastAddedImageView.getId();
        draggableMaker.makeDraggable(lastAddedImageView, "/gui/BATEForm.fxml", "Enter Batelada data", (BATEFormController controller) -> {
            List<String> reactorData = reactorDataMap.get(reactorID);
            controller.setData(reactorData);
        });
    }

    public void onMenuItemCSTRAction() {
        addReactor("images/CSTR.png");
        String reactorID = lastAddedImageView.getId();
        draggableMaker.makeDraggable(lastAddedImageView, "/gui/CSTRForm.fxml", "Enter CSTR data", (CSTRFormController controller) -> {
            List<String> reactorData = reactorDataMap.get(reactorID);
            controller.setData(reactorData);
        });
    }

    public void onMenuItemPFRAction() {
        addReactor("images/PFR.png");
        String reactorID = lastAddedImageView.getId();
        draggableMaker.makeDraggable(lastAddedImageView, "/gui/PFRForm.fxml", "Enter PFR data", (PFRFormController controller) -> {
            List<String> reactorData = reactorDataMap.get(reactorID);
            controller.setData(reactorData);
        });
    }

    private void addReactor(String imagePath) {

        if (reactorDataMap.size() >= 3) {
            Alerts.showAlert("Error", "Maximum reactors reached", "You can only add up to 3 reactors.", Alert.AlertType.ERROR);
            return;
        }

        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(true);
        String uniqueID = UUID.randomUUID().toString();
        imageView.setId(uniqueID);

        pane.getChildren().add(imageView);

        reactorDataMap.put(uniqueID, new ArrayList<>());
        lastAddedImageView = imageView;

        imageView.layoutXProperty().bind(pane.widthProperty().subtract(imageView.getFitWidth()).divide(2));
        imageView.layoutYProperty().bind(pane.heightProperty().subtract(imageView.getFitHeight()).divide(2));


        ContextMenu contextMenu = new ContextMenu();

        MenuItem propertiesItem = new MenuItem("Propriedades");
        propertiesItem.setOnAction(event -> {

        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> {
            pane.getChildren().remove(imageView);
            reactorDataMap.remove(uniqueID);
        });

        contextMenu.getItems().addAll(propertiesItem, deleteItem);

        imageView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
            }
        });

    }


    @FXML
    private void btSimulateAction() {

        if (reactorDataMap.isEmpty()){
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
