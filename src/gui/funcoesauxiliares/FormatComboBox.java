package gui.funcoesauxiliares;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.util.List;

public class FormatComboBox {
	
	public void updateComboBox(ComboBox<Image> comboBox, List<Image> equacoes, Boolean central) {

		final ObservableList<Image> data = FXCollections.observableArrayList(equacoes);


		comboBox.getItems().clear();
		
		comboBox.getItems().addAll(data);

		comboBox.setButtonCell(new StatusListCell());

		comboBox.setCellFactory(new Callback<ListView<Image>, ListCell<Image>>() {

			@Override
			public ListCell<Image> call(ListView<Image> p) {
				
		

				return new ListCell<Image>() {
					private final ImageView view;
					
					{
						view = new ImageView();
						view.setSmooth(true);
						view.setPreserveRatio(true);
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

					}

					@Override
					protected void updateItem(Image item, boolean empty) {
						super.updateItem(item, empty);
						String sysOs = System.getProperty("os.name");
						String[] newOs = sysOs.split(" ");
						double k = 0.0; 
						if(newOs[0].contentEquals("Windows")) {
							k = 1.0;
						}else {
							k = 0.65;
						}
						
						
						if (item == null || empty) {
							
							setGraphic(null);
							if (central == true) {
								setAlignment(Pos.CENTER);
							}
						} else {
							view.setFitWidth(item.getWidth()*k);
							view.setImage(item);
							setGraphic(view);
						}

					}

				};

			}

		});
	}
	
	public class StatusListCell extends ListCell<Image> {
		protected void updateItem(Image item, boolean empty) {
			String sysOs = System.getProperty("os.name");
			String[] newOs = sysOs.split(" ");
			double k = 0.0; 
			if(newOs[0].contentEquals("Windows")) {
				k = 1;
			}else {
				k = 0.65;
			}
			
			super.updateItem(item, empty);
			setGraphic(null);
			setText(null);
			if (item != null) {
				ImageView imageView = new ImageView(item);
				imageView.setPreserveRatio(true);

				imageView.setFitWidth(item.getWidth()*k);
				setGraphic(imageView);
				setText("");
			}
		}

	}
}
