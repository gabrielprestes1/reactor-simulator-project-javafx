package gui;

import com.google.gson.Gson;
import gui.util.Alerts;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CSTRFormController {

    @FXML
    private ChoiceBox<String> yamlFileChoiceBox;
    @FXML
    private Button changeButton;
    @FXML
    private TextField compositionTextField;
    @FXML
    private TextField volumetricFlowTextField;
    @FXML
    private TextField totalTimeField;
    @FXML
    private TextField VolumeField;
    @FXML
    private TextField initialPressureTextField;
    @FXML
    private TextField initialTemperatureTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton Isothermal;
    @FXML
    private RadioButton Adiabatic;

    private File currentDirectory;

    private List<String> reactorData = new ArrayList<>();

    @FXML
    private void initialize() {
        Constraints.setTextFieldDouble(volumetricFlowTextField);
        Constraints.setTextFieldDouble(totalTimeField);
        Constraints.setTextFieldDouble(VolumeField);
        Constraints.setTextFieldDouble(initialPressureTextField);
        Constraints.setTextFieldDouble(initialTemperatureTextField);

        ToggleGroup Group = new ToggleGroup();

        Isothermal.setToggleGroup(Group);
        Adiabatic.setToggleGroup(Group);

        currentDirectory = new File("data");
        updateYamlFileList();
        changeButton.setOnAction(event -> openDirectory());

        saveButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Stage stage = (Stage) newScene.getWindow();
                stage.setOnCloseRequest(event -> {
                    Optional<ButtonType> result = Alerts.showConfirmation("Exit", "exit without saving?");

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        stage.close();
                    } else {
                        stage.close();
                    }
                });
            }
        });

    }

    private void updateYamlFileList() {
        if (currentDirectory.isDirectory()) {
            File[] files = currentDirectory.listFiles((dir, name) -> name.endsWith(".yaml"));
            if (files != null) {
                yamlFileChoiceBox.getItems().clear();
                Arrays.stream(files)
                        .map(File::getName)
                        .forEach(yamlFileChoiceBox.getItems()::add);
            }
        }
    }

    private void openDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(currentDirectory);
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            currentDirectory = selectedDirectory;
            updateYamlFileList();
        }
    }

    @FXML
    private void onSaveButton() {

        try{
            SimulationData data = new SimulationData();

            if (Adiabatic.isSelected()){
                data.energyChoice = 1;
            } else if (Isothermal.isSelected()) {
                data.energyChoice = 0;
            }

            reactorData.add(0, yamlFileChoiceBox.getValue());
            reactorData.add(1, compositionTextField.getText());
            reactorData.add(2, volumetricFlowTextField.getText());
            reactorData.add(3, VolumeField.getText());
            reactorData.add(4, totalTimeField.getText());
            reactorData.add(5, initialPressureTextField.getText());
            reactorData.add(6, initialTemperatureTextField.getText());
            reactorData.add(7, Adiabatic.isSelected() ? "1" : "0");

            String inputFilePath = "src/model/simulator/inputCSTR.json";
            writeDataToFile(data, inputFilePath);

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } catch (IOException | NullPointerException e) {
            Alerts.showAlert("Error", "Missing values", "Please fill in all fields", Alert.AlertType.WARNING);
        }

    }

    @FXML
    private void onCancelButton(){
        if (reactorData.isEmpty()) {
            Optional<ButtonType> result = Alerts.showConfirmation("Exit", "exit without saving?");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            } else {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    private Double parseDouble(String text) {
        try {
            return text != null && !text.trim().isEmpty() ? Double.parseDouble(text.trim()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void writeDataToFile(SimulationData data, String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            writer.flush();
        }
    }

    private class SimulationData {
        String yamlFile = yamlFileChoiceBox.getValue();
        String composition = compositionTextField.getText();
        String yamlFilePath = new File("C:data", yamlFile).getAbsolutePath();;
        Double volumetricFlow = parseDouble(volumetricFlowTextField.getText());
        Double Volume = parseDouble(VolumeField.getText());
        Double totalTime = parseDouble(totalTimeField.getText());
        Double initialPressure = parseDouble(initialPressureTextField.getText());
        Double initialTemperature = parseDouble(initialTemperatureTextField.getText());
        Integer energyChoice;
    }

    public void setData(List<String> data) {
        this.reactorData = data;
        if (data != null && !data.isEmpty()) {
            yamlFileChoiceBox.setValue(data.get(0));
            compositionTextField.setText(data.get(1));
            volumetricFlowTextField.setText(data.get(2));
            VolumeField.setText(data.get(3));
            totalTimeField.setText(data.get(4));
            initialPressureTextField.setText(data.get(5));
            initialTemperatureTextField.setText(data.get(6));

            if (data.size() > 7 && data.get(7).equals("1")) {
                Adiabatic.setSelected(true);
            } else {
                Isothermal.setSelected(true);
            }
        }
    }

}
