package gui;



import application.Main;


import gui.util.DraggableMaker;


import gui.util.LoadView;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import javafx.stage.StageStyle;
import model.entities.PFR;
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

    @FXML
    private Pane pane;

    LoadView loadView = new LoadView();

    private List<Rectangle> rectangles = new ArrayList<>();

    DraggableMaker draggableMaker = new DraggableMaker();

    public void onMenuItemCSTRAction() {

        Rectangle rectangle = new Rectangle(100, 100, Color.BLUE);

        pane.getChildren().add(rectangle);

        rectangle.layoutXProperty().bind(pane.widthProperty().subtract(rectangle.getWidth()).divide(2));
        rectangle.layoutYProperty().bind(pane.heightProperty().subtract(rectangle.getHeight()).divide(2));

        rectangles.add(rectangle);

        draggableMaker.makeDraggable(rectangle,"/gui/CSTRForm.fxml", "Enter CSTR data", (CSTRFormController controller) -> {});


    }

    public void onMenuItemPFRAction() {
        Rectangle rectangle = new Rectangle(100, 100, Color.PINK);

        pane.getChildren().add(rectangle);

        rectangle.layoutXProperty().bind(pane.widthProperty().subtract(rectangle.getWidth()).divide(2));
        rectangle.layoutYProperty().bind(pane.heightProperty().subtract(rectangle.getHeight()).divide(2));

        rectangles.add(rectangle);

        draggableMaker.makeDraggable(rectangle, "/gui/PFRForm.fxml", "Enter PFR data", (PFRFormController controller) ->{});

    }


    @FXML
    private void btSimulateAction() {

        LoadingBarController loadingBarController = new LoadingBarController();

        loadView.loadView("/gui/LoadingBar.fxml", "Loading", (LoadingBarController controller) -> {
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
                List<String> equations = new PFR().pdeBuilder(reactor);
                Integer partitions = reactor.getnParticoes();

                Platform.runLater(() -> {

                    loadView.closeView();

                    Platform.runLater(()-> {
                        loadView.loadView("/gui/GraphForm.fxml", "Results", (GraphFormController graphController) -> {
                            graphController.setResults(results, equations, partitions, reactor);
                        }, StageStyle.DECORATED);
                    });

                });
            });
            simulationThread.start();
            loadingThread.start();
        }, StageStyle.UNDECORATED);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
