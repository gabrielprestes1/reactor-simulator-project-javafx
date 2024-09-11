package model.service;

import javafx.scene.control.ChoiceBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.Arrays;

public class DirectoryManager {
    private File currentDirectory;
    private ChoiceBox<String> choiceBox;

    public DirectoryManager(File currentDirectory, ChoiceBox<String> choiceBox) {
        this.currentDirectory = currentDirectory;
        this.choiceBox = choiceBox;
    }

    public void updateYamlFileList() {
        if (currentDirectory.isDirectory()) {
            File[] files = currentDirectory.listFiles((dir, name) -> name.endsWith(".yaml"));
            if (files != null) {
                choiceBox.getItems().clear();
                Arrays.stream(files)
                        .map(File::getName)
                        .forEach(choiceBox.getItems()::add);
            }
        }
    }

    public void openDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(currentDirectory);
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            currentDirectory = selectedDirectory;
            updateYamlFileList();
        }
    }
}
