package gui;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.service.DirectoryManager;
import model.service.WriterJson;

import java.io.File;
import java.util.*;

public class BATEFormController {

    @FXML
    private ChoiceBox<String> yamlFileChoiceBox;
    @FXML
    private Button changeButton;
    @FXML
    private TextField compositionTextField;
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

    private DirectoryManager directoryManager;
    private List<String> reactorData = new ArrayList<>();
    private WriterJson writerJson = new WriterJson();
    private String id;

    @FXML
    private void initialize() {
        Constraints.setTextFieldDouble(totalTimeField);
        Constraints.setTextFieldDouble(VolumeField);
        Constraints.setTextFieldDouble(initialPressureTextField);
        Constraints.setTextFieldDouble(initialTemperatureTextField);

        ToggleGroup Group = new ToggleGroup();

        Isothermal.setToggleGroup(Group);
        Adiabatic.setToggleGroup(Group);

        File initialDirectory = new File("data");
        directoryManager = new DirectoryManager(initialDirectory, yamlFileChoiceBox);
        directoryManager.updateYamlFileList();

        saveButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Stage stage = (Stage) newScene.getWindow();
                stage.setOnCloseRequest(event -> {
                    if (reactorData.isEmpty()) {
                        Optional<ButtonType> result = Alerts.showConfirmation("Exit", "exit without saving?");

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            stage.close();
                        }
                    }
                });
            }
        });
    }

    @FXML
    private void onOpenDirectory() {
        directoryManager.openDirectory();
    }

    @FXML
    private void onSaveButton() {

        if (areFieldsFilled()) {
            try {
                reactorData.clear();

                reactorData.add(0, yamlFileChoiceBox.getValue());
                reactorData.add(1, compositionTextField.getText());
                reactorData.add(2, VolumeField.getText());
                reactorData.add(3, totalTimeField.getText());
                reactorData.add(4, initialPressureTextField.getText());
                reactorData.add(5, initialTemperatureTextField.getText());
                reactorData.add(6, Adiabatic.isSelected() ? "1" : "0");

                writerJson.saveData(id, reactorData);
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();

            } catch (NullPointerException e) {
                Alerts.showAlert("Error", "Missing values", "Please fill in all fields", Alert.AlertType.WARNING);
            }
        } else {
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
            }
        } else {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    public void setData(List<String> data) {
        this.reactorData = data;
        if (data != null && !data.isEmpty()) {
            yamlFileChoiceBox.setValue(data.get(0));
            compositionTextField.setText(data.get(1));
            VolumeField.setText(data.get(2));
            totalTimeField.setText(data.get(3));
            initialPressureTextField.setText(data.get(4));
            initialTemperatureTextField.setText(data.get(5));

            if (data.size() > 6 && data.get(6).equals("1")) {
                Adiabatic.setSelected(true);
            } else {
                Isothermal.setSelected(true);
            }
        }
    }

    public void setID(String id){
        this.id = id;
    }

    public void setWriterJson(WriterJson writerJson) {
        this.writerJson = writerJson;
    }

    private boolean areFieldsFilled() {
        return isYamlFileChoiceBoxFilled() &&
                isTextFieldFilled(compositionTextField) &&
                isTextFieldFilled(VolumeField) &&
                isTextFieldFilled(totalTimeField) &&
                isTextFieldFilled(initialPressureTextField) &&
                isTextFieldFilled(initialTemperatureTextField);
    }

    private boolean isYamlFileChoiceBoxFilled() {
        return yamlFileChoiceBox.isDisable() || yamlFileChoiceBox.getValue() != null;
    }

    private boolean isTextFieldFilled(TextField textField) {
        return textField.isDisable() || !textField.getText().isEmpty();
    }

}
