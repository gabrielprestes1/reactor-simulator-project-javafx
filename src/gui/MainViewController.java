package gui;

import gui.util.DraggableMaker;

import gui.util.LoadView;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import gui.PFRFormController;
import javafx.stage.StageStyle;

import static java.lang.Double.parseDouble;


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

        //draggableMaker.makeDraggable(rectangle,"/gui/CSTRForm.fxml", "Enter CSTR data", (CSTRFormController controller) -> {});


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

        try {
            String pythonPath = "C:\\Users\\Gabri\\project-tcc\\reactor-simulator-project-javafx\\venv\\Scripts\\python.exe";
            String pythonScriptPath = "C:\\Users\\Gabri\\project-tcc\\reactor-simulator-project-javafx\\src\\model\\simulator\\PFR.py";

            String[] command = {
                    pythonPath,
                    pythonScriptPath,
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }


        LoadView view = new LoadView();
        view.loadView("/gui/GraphForm.fxml", "Results", (GraphFormController controller) ->{}, StageStyle.DECORATED);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
