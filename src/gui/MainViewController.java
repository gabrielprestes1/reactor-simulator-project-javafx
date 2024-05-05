package gui;



import application.Main;


import gui.util.DraggableMaker;


import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import model.entities.CSTR;
import model.entities.Reactor;


import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



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
    private Button buttonRun;

    LoadView loadView = new LoadView();

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
                loadView.loadNewView("/gui/CSTRForm.fxml", "Enter CSTR data", (CSTRFormController controller) ->{});
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
                loadView.loadNewView("/gui/PFRForm.fxml", "Enter PFR data", (PFRFormController controller) ->{});
            }
        });


    }


    @FXML
    private void btSimulateAction() {

        LoadingBarController loadingBarController = new LoadingBarController();

        loadView.loadLoadingView("LoadingBar.fxml", "Loading", (LoadingBarController controller) -> {
            Thread loadingThread = new Thread(() -> {
                while (true) {
                    controller.updateProgressBar(loadingBarController.getProgress());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread simulationThread = new Thread(() -> {
                Reactor reactor = new Reactor();
                List<List<Double>> results = loadingBarController.simulated();
                List<String> equations = new CSTR().pdeBuilder(reactor);
                Integer partitions = reactor.getnParticoes();

                Platform.runLater(() -> {

                    loadView.closeView();

                    Platform.runLater(()-> {
                        loadView.loadNewView("GraphForm.fxml", "Results", (GraphFormController graphController) -> {
                            graphController.setResults(results, equations, partitions, reactor);
                        });
                    });

                });
            });
            simulationThread.start();
            loadingThread.start();
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
