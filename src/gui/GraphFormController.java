package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class GraphFormController {

    @FXML
    private ImageView imageView;
    @FXML
    private Button close;
    @FXML
    private Button saveAs;

    File file = new File("src/model/simulator/results.png");

    @FXML
    private void initialize() {
        loadPNG();

        close.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Stage stage = (Stage) newScene.getWindow();
                stage.setOnCloseRequest(event -> {
                    file.delete();
                });
            }
        });
    }

    public void loadPNG() {


        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    @FXML
    private void onCloseButton(){
        file.delete();

        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSaveAsButton(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Results");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        File fileToSave = fileChooser.showSaveDialog(null);

        if (fileToSave != null) {
            try (FileInputStream fis = new FileInputStream(file);
                 FileOutputStream fos = new FileOutputStream(fileToSave)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
