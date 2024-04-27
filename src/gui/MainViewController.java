package gui;

import OdeSolver.DiferencasFinitas;
import OdeSolver.RK4SistemasEdos;
import OdeSolver.RKF45Storage;
import application.Main;
import gui.util.Alerts;

import gui.util.DraggableMaker;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.PFR;
import model.entities.Reactor;
import model.entities.Simulation;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;


public class MainViewController implements Initializable {


    @FXML
    private MenuItem menuItemCSTR;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    private MenuItem menuItemPFR;

    @FXML
    private Button buttonrun;



    private List<Rectangle> rectangles = new ArrayList<>();

    DraggableMaker draggableMaker = new DraggableMaker();

    public void onMenuItemCSTRAction() {

        Scene mainScene = Main.getMainScene();
        VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

        HBox hBox = (HBox) mainVBox.getChildren().get(1);
        Pane pane = (Pane) hBox.getChildren().get(0);


        Rectangle rectangle = new Rectangle(100, 100, Color.BLUE);
        pane.getChildren().add(rectangle);

        rectangle.relocate(pane.getPrefWidth()/2, pane.getPrefHeight()/2);
        

        rectangles.add(rectangle);

        draggableMaker.makeDraggable(rectangle);

        rectangle.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CSTRForm.fxml"));
                    Pane anchorPane = loader.load();

                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Enter CSTR data");
                    dialogStage.setScene(new Scene(anchorPane));
                    dialogStage.setResizable(false);
                    dialogStage.initOwner(mainScene.getWindow());
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.showAndWait();
                }
                catch (IOException e){
                    e.printStackTrace();
                    Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });



    }

    public void onMenuItemPFRAction() {

        Scene mainScene = Main.getMainScene();
        VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

        HBox hBox = (HBox) mainVBox.getChildren().get(1);
        Pane pane = (Pane) hBox.getChildren().get(0);


        Rectangle rectangle = new Rectangle(100, 100, Color.PINK);
        pane.getChildren().add(rectangle);

        rectangle.relocate(pane.getPrefWidth()/2, pane.getPrefHeight()/2);


        rectangles.add(rectangle);

        draggableMaker.makeDraggable(rectangle);

        rectangle.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PFRForm.fxml"));
                    Pane anchorPane = loader.load();

                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Enter PFR data");
                    dialogStage.setScene(new Scene(anchorPane));
                    dialogStage.setResizable(false);
                    dialogStage.initOwner(mainScene.getWindow());
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.showAndWait();
                }
                catch (IOException e){
                    e.printStackTrace();
                    Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });


    }


    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVbox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVbox.getChildren());

            T controller = loader.getController();
            initializingAction.accept(controller);
        }
        catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void btSimulateAction() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GraphForm.fxml"));
            Pane anchorPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Results");
            dialogStage.setScene(new Scene(anchorPane));
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
