package gui;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class GraphFormController {

    @FXML
    private ImageView imageView;


    @FXML
    private void initialize() {
        loadPNG();
    }

    public void loadPNG() {
        File file = new File("src/model/simulator/results.png");

        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            file.delete();
        } else {
            Alerts.showAlert("Error", "Error result not found", "", Alert.AlertType.ERROR );
        }
    }
}
