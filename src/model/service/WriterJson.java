package model.service;

import com.google.gson.Gson;
import gui.util.Alerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


import java.util.*;


public class WriterJson {

    private static final Map<String, List<String>> reactorDataMap = new LinkedHashMap<>();
    private ImageView lastAddedImageView;

    public String WriterDataToJson() {
        Gson gson = new Gson();
        return gson.toJson(reactorDataMap);
    }

    public void saveData(String reactorType, List<String> data) {
        reactorDataMap.put(reactorType, data);
    }

    public Map<String, List<String>> getReactorDataMap() {
        return reactorDataMap;
    }

    public String generateUniqueKey(String baseKey) {
        int count = 1;
        String uniqueKey = baseKey + "_" + count;

        while (reactorDataMap.containsKey(uniqueKey)) {
            count++;
            uniqueKey = baseKey + "_" + count;
        }

        return uniqueKey;
    }

    public void addReactor(String imagePath, String id, Pane pane) {

        if (reactorDataMap.size() >= 3) {
            Alerts.showAlert("Error", "Maximum reactors reached", "You can only add up to 3 reactors.", Alert.AlertType.ERROR);
            return;
        }

        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(true);

        pane.getChildren().add(imageView);

        lastAddedImageView = imageView;
        lastAddedImageView.setId(generateUniqueKey(id));

        imageView.layoutXProperty().bind(pane.widthProperty().subtract(imageView.getFitWidth()).divide(2));
        imageView.layoutYProperty().bind(pane.heightProperty().subtract(imageView.getFitHeight()).divide(2));


        ContextMenu contextMenu = new ContextMenu();

        MenuItem propertiesItem = new MenuItem("Propriedades");
        propertiesItem.setOnAction(event -> {

        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> {
            pane.getChildren().remove(imageView);
            reactorDataMap.remove(imageView.getId());
        });

        contextMenu.getItems().addAll(propertiesItem, deleteItem);

        imageView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
            }
        });

    }

    public ImageView getLastAddedImageView() {
        return lastAddedImageView;
    }

    public String returID(){
        return lastAddedImageView.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WriterJson that = (WriterJson) o;
        return Objects.equals(reactorDataMap, that.reactorDataMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reactorDataMap);
    }
}
