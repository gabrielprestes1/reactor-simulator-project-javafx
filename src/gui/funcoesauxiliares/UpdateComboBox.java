package gui.funcoesauxiliares;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

public class UpdateComboBox {

	public void updateComboBox(ComboBox<Text> comboBox, List<Text> lista, Boolean central) {

		final ObservableList<Text> data = FXCollections.observableArrayList(lista);

		comboBox.getItems().clear();
		
		comboBox.getItems().addAll(data);

		// comboBox.setButtonCell(new StatusListCell());

		comboBox.setCellFactory(new Callback<ListView<Text>, ListCell<Text>>() {

			@Override
			public ListCell<Text> call(ListView<Text> p) {

				return new ListCell<Text>() {

					private final Text view;
					{
						view = new Text();
						view.setFont(Font.font("System", 13));
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

					}

					@Override
					protected void updateItem(Text item, boolean empty) {

						super.updateItem(item, empty);
						super.setPrefHeight(40);
						if (item == null || empty) {

							setGraphic(null);
							if (central == true) {
								setAlignment(Pos.CENTER);
							}
						} else {

							view.setText(item.getText());

							setGraphic(view);

							if (central == true) {
								setAlignment(Pos.CENTER);
							}
						}

					}

				};

			}

		}

		);
	}

}
