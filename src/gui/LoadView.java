package gui;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public class LoadView {

    private Stage dialogStage;


    public synchronized <T> void loadNewView(String absoluteName, String title, Consumer<T> initializingAction) {

        Scene mainScene = Main.getMainScene();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Parent pane = loader.load();

            T controller = loader.getController();
            initializingAction.accept(controller);

            dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(mainScene.getWindow());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public synchronized <T> void loadLoadingView(String absoluteName, String title, Consumer<T> initializingAction) {

        Scene mainScene = Main.getMainScene();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Parent pane = loader.load();

            T controller = loader.getController();
            initializingAction.accept(controller);

            dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(mainScene.getWindow());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void closeView() {
        dialogStage.close();
    }

}
