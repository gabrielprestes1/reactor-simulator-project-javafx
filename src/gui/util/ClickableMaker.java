package gui.util;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class ClickableMaker {

    public void clickableMakerLoad(String absoluteName, Node node) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            AnchorPane anchorPane = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            HBox hBox = (HBox) mainVBox.getChildren().get(1);

            node.setOnMousePressed(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {
                    hBox.getChildren().addAll(anchorPane.getChildren());
                }
            });
        }
        catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
