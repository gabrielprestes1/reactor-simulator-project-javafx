package gui.util;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.function.Consumer;

public class LoadView {

    private Stage dialogStage;

    public synchronized <T> void loadView(String absoluteName, String title, Consumer<T> initializingAction, StageStyle style) {

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
            dialogStage.initStyle(style);
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
