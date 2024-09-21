package gui.util;

import javafx.scene.Node;
import javafx.stage.StageStyle;

import java.util.function.Consumer;

public class DraggableMaker {

    private double mouseAnchorX;
    private double mouseAnchorY;

    public <T> void makeDraggable(Node node, String absoluteName, String title, Consumer<T> initializingAction) {
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getSceneX() - node.getTranslateX();
            mouseAnchorY = mouseEvent.getSceneY() - node.getTranslateY();
            if (mouseEvent.getClickCount() == 2) {
                LoadView view = new LoadView();
                view.loadView(absoluteName, title, initializingAction, StageStyle.DECORATED);
            }
        });

        node.setOnMouseDragged(mouseEvent -> {
            double deltaX = mouseEvent.getSceneX() - mouseAnchorX;
            double deltaY = mouseEvent.getSceneY() - mouseAnchorY;
            node.setTranslateX(deltaX);
            node.setTranslateY(deltaY);
        });

    }

}
