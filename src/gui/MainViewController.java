package gui;

import gui.util.Alerts;
import gui.util.DraggableMaker;
import gui.util.LoadView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import model.service.WriterJson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemCSTR;

    @FXML
    private MenuItem menuItemBATE;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private MenuItem menuItemPFR;

    @FXML
    private Button buttonRun;

    @FXML
    private Pane pane;

    private final WriterJson writerJson = new WriterJson();

    DraggableMaker draggableMaker = new DraggableMaker();


    public void onMenuItemBATEAction() {
        if (!pane.getChildren().isEmpty()){
            Alerts.showAlert("Information", "Alert", "The BATELADA Reactor can only be used alone.", Alert.AlertType.INFORMATION);
            return;
        }
        writerJson.addReactor("/images/BATE.png", "BATE",  pane);
        String reactorID = writerJson.returID();
        draggableMaker.makeDraggable(writerJson.getLastAddedImageView(), "/gui/BATEForm.fxml", "Enter Batelada data", (BATEFormController controller) -> {
            controller.setID(reactorID);
            if (writerJson.getReactorDataMap().containsKey(reactorID)) {
                List<String> reactorData = writerJson.getReactorDataMap().get(reactorID);
                controller.setData(reactorData);
                controller.setWriterJson(writerJson);
                System.out.println(writerJson.getReactorDataMap());
            }
        });
    }

    public void onMenuItemCSTRAction() {
        if (!pane.getChildren().isEmpty() && pane.getChildren().get(0).getId().equals("BATE_1")) {
            Alerts.showAlert("Information", "Alert", "BATELADA Reactor can only be used alone, you must delete it before adding another type of reactor.", Alert.AlertType.INFORMATION);
            return;
        }
        writerJson.addReactor("images/CSTR.png", "CSTR", pane);
        String reactorID = writerJson.returID();
        draggableMaker.makeDraggable(writerJson.getLastAddedImageView(), "/gui/CSTRForm.fxml", "Enter CSTR data", (CSTRFormController controller) -> {
            controller.setID(reactorID);
            if (!writerJson.getReactorDataMap().isEmpty()){
                controller.reactorExists(reactorID);
            }
            if (writerJson.getReactorDataMap().containsKey(reactorID)) {
                List<String> reactorData = writerJson.getReactorDataMap().get(reactorID);
                controller.setData(reactorData);
                controller.setWriterJson(writerJson);
                System.out.println(writerJson.getReactorDataMap());
            }
        });
    }

    public void onMenuItemPFRAction() {
        if (!pane.getChildren().isEmpty() && pane.getChildren().get(0).getId().equals("BATE_1")) {
            Alerts.showAlert("Information", "Alert", "BATELADA Reactor can only be used alone, you must delete it before adding another type of reactor.", Alert.AlertType.INFORMATION);
            return;
        }
        writerJson.addReactor("images/PFR.png", "PFR", pane);
        String reactorID = writerJson.returID();
        draggableMaker.makeDraggable(writerJson.getLastAddedImageView(), "/gui/PFRForm.fxml", "Enter PFR data", (PFRFormController controller) -> {
            controller.setID(reactorID);
            if (!writerJson.getReactorDataMap().isEmpty()){
                controller.reactorExists(reactorID);
            }
            if (writerJson.getReactorDataMap().containsKey(reactorID)) {
                List<String> reactorData = writerJson.getReactorDataMap().get(reactorID);
                controller.setData(reactorData);
                controller.setWriterJson(writerJson);
                System.out.println(writerJson.getReactorDataMap());
            }
        });
    }


    @FXML
    private void btSimulateAction() {

        if (writerJson.getReactorDataMap().isEmpty()){
            Alerts.showAlert("Error", "Missing values", "Add at least one reactor before simulating", Alert.AlertType.ERROR);
            return;
        }

        String jsonOutput = writerJson.WriterDataToJson();
        String filePath = "src/model/simulator/input.json";

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
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
                File file = new File("src/model/simulator/results_REAC1.png");

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