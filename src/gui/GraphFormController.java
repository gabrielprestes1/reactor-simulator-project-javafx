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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GraphFormController {

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private Button close;
    @FXML
    private Button saveAs;

    private static final Logger logger = Logger.getLogger(GraphFormController.class.getName());
    private static final String FILE_PATH_1 = "src/model/simulator/results_REAC1.png";
    private static final String FILE_PATH_2 = "src/model/simulator/results_REAC2.png";
    private static final String FILE_PATH_3 = "src/model/simulator/results_REAC3.png";

    private final File file1 = new File(FILE_PATH_1);
    private final File file2 = new File(FILE_PATH_2);
    private final File file3 = new File(FILE_PATH_3);

    @FXML
    private void initialize() {
        loadImages();

        close.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Stage stage = (Stage) newScene.getWindow();
                stage.setOnCloseRequest(event -> deleteFiles());
            }
        });
    }

    private void loadImages() {
        loadImage(file1, imageView1);
        loadImage(file2, imageView2);
        loadImage(file3, imageView3);
    }

    private void loadImage(File file, ImageView imageView) {
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else {
            logger.log(Level.WARNING, "File not found: " + file.getPath());
        }
    }

    private void deleteFiles() {
        deleteFile(file1);
        deleteFile(file2);
        deleteFile(file3);
    }

    private void deleteFile(File file) {
        if (file.exists() && !file.delete()) {
            logger.log(Level.WARNING, "Failed to delete file: " + file.getPath());
        }
    }

    @FXML
    private void onCloseButton() {
        deleteFiles();
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSaveAsButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Imagens como ZIP");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ZIP files (*.zip)", "*.zip"));

        File zipFile = fileChooser.showSaveDialog(null);
        if (zipFile != null) {

            if (!zipFile.getName().endsWith(".zip")) {
                zipFile = new File(zipFile.getAbsolutePath() + ".zip");
            }

            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
                addToZipFile(file1, zipOut);
                addToZipFile(file2, zipOut);
                addToZipFile(file3, zipOut);
                logger.log(Level.INFO, "Imagens salvas no arquivo ZIP: " + zipFile.getPath());
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Falha ao criar o arquivo ZIP", e);
            }
        }
    }

    private void addToZipFile(File sourceFile, ZipOutputStream zipOut) {
        if (sourceFile.exists()) {
            try (FileInputStream fis = new FileInputStream(sourceFile)) {
                ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zipOut.write(buffer, 0, length);
                }
                zipOut.closeEntry();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Falha ao adicionar arquivo ao ZIP: " + sourceFile.getName(), e);
            }
        } else {
            logger.log(Level.WARNING, "Arquivo não encontrado: " + sourceFile.getPath());
        }
    }

}
