package gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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

        // Verifica se o arquivo existe antes de tentar carregar
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else {
            System.out.println("Arquivo de imagem não encontrado: " + file.getAbsolutePath());
        }
    }
}
