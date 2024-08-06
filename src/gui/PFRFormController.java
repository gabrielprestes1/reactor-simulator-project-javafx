package gui;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


import java.io.*;
import java.util.Arrays;
import java.util.Properties;

import static java.lang.Double.parseDouble;


public class PFRFormController {

    @FXML
    private ChoiceBox<String> yamlFileChoiceBox;
    @FXML
    private Button changeButton;
    @FXML
    private TextField compositionTextField;
    @FXML
    private TextField inflowVelocityTextField;
    @FXML
    private TextField lengthTextField;
    @FXML
    private TextField diameterTextField;
    @FXML
    private TextField initialPressureTextField;
    @FXML
    private TextField initialTemperatureTextField;
    @FXML
    private Button saveButton;

    private File currentDirectory;



    @FXML
    private void initialize() {
        // Inicializar o diretório de trabalho
        currentDirectory = new File("C:\\Users\\Gabri\\project-tcc\\reactor-simulator-project-javafx\\data");

        // Atualizar a lista de arquivos no ChoiceBox
        updateYamlFileList();

        // Configurar ação do botão "Change"
        changeButton.setOnAction(event -> openDirectory());
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
    private void onSaveButton() throws IOException {

        SimulationData data = new SimulationData();

        String inputFilePath = "C:\\Users\\Gabri\\project-tcc\\reactor-simulator-project-javafx\\src\\model\\simulator\\input.json";
        writeDataToFile(data, inputFilePath);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
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
        }
    }

    private class SimulationData {
        String yamlFile = yamlFileChoiceBox.getValue();
        String composition = compositionTextField.getText();
        String yamlFilePath = new File("C:\\Users\\Gabri\\project-tcc\\reactor-simulator-project-javafx\\data", yamlFile).getAbsolutePath();;

        Double inflowVelocity = parseDouble(inflowVelocityTextField.getText());
        Double length = parseDouble(lengthTextField.getText());
        Double diameter = parseDouble(diameterTextField.getText());
        Double initialPressure = parseDouble(initialPressureTextField.getText());
        Double initialTemperature = parseDouble(initialTemperatureTextField.getText());
    }

}
