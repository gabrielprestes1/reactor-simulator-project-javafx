package gui.funcoesauxiliares;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ManipulateTables {

	
	ManipulateNode manipulateNode = new ManipulateNode();
	
	
	public void addColumnAction(Integer numeroLinhas, 
			Integer numeroColunas,
			Integer numeroParametros,
			GridPane dadosExperimentaisGridPane,
			GridPane resultadosGridPane,
			GridPane gridPaneMedida,
			GridPane gridPaneResultadosMedida,
			GridPane parametrosGridPane,
			ComboBox<Text> newComboBoxMetodo
			) {

		for (int i = 0; i < numeroLinhas; i++) {

			TextField txt = new TextField();
			txt.setMaxHeight(40);
			txt.setMaxWidth(100);
			txt.setAlignment(Pos.CENTER);
			dadosExperimentaisGridPane.add(txt, numeroColunas, i);

			if (i == 0) {
				txt.setPromptText("Digite a Variável");
			}
		}

		numeroColunas += 1;

		setConstraints(numeroColunas, dadosExperimentaisGridPane, gridPaneMedida, gridPaneResultadosMedida,resultadosGridPane,parametrosGridPane);

	}

	
	public void addRowAction(Integer numeroLinhas, 
			Integer numeroColunas,
			Integer numeroParametros,
			GridPane dadosExperimentaisGridPane,
			GridPane resultadosGridPane,
			GridPane gridPaneMedida,
			GridPane gridPaneResultadosMedida,
			GridPane parametrosGridPane,
			ComboBox<Text> newComboBoxMetodo) {

		for (int i = 0; i < numeroColunas; i++) {

			TextField txt = new TextField();
			txt.setMaxHeight(40);
			txt.setMaxWidth(100);
			txt.setAlignment(Pos.CENTER);
			dadosExperimentaisGridPane.add(txt, i, numeroLinhas);

		}

		TextField txt = new TextField();
		txt.setAlignment(Pos.CENTER);
		txt.setMaxHeight(40);
		txt.setMinWidth(140);
		txt.setPrefWidth(140);
		txt.setMaxWidth(140);
		resultadosGridPane.add(txt, 0, numeroLinhas - 1);

		numeroLinhas += 1;
		setConstraints(numeroColunas, dadosExperimentaisGridPane, gridPaneMedida, gridPaneResultadosMedida,resultadosGridPane,parametrosGridPane);
	}

	
	public void addParametroAction(Integer numeroLinhas, 
			Integer numeroColunas,
			Integer numeroParametros,
			GridPane dadosExperimentaisGridPane,
			GridPane resultadosGridPane,
			GridPane gridPaneMedida,
			GridPane gridPaneResultadosMedida,
			GridPane parametrosGridPane,
			ComboBox<Text> newComboBoxMetodo
			) {

		if (newComboBoxMetodo.getSelectionModel().getSelectedIndex() < 1) {
			TextField txt = new TextField();
			txt.setAlignment(Pos.CENTER);
			txt.setMaxWidth(60);
			txt.setMinWidth(60);
			txt.setPrefWidth(60);

			TextField textMin = new TextField();
			textMin.setAlignment(Pos.CENTER);
			textMin.setMaxWidth(100);
			textMin.setMinWidth(100);
			textMin.setPrefWidth(100);
			textMin.setPromptText("Mínimo");

			TextField textMax = new TextField();
			textMax.setAlignment(Pos.CENTER);
			textMax.setMaxWidth(100);
			textMax.setMinWidth(100);
			textMax.setPrefWidth(100);
			textMax.setPromptText("Máximo");

			parametrosGridPane.add(txt, 0, numeroParametros);
			parametrosGridPane.add(textMin, 1, numeroParametros);
			parametrosGridPane.add(textMax, 2, numeroParametros);
		} else {
			TextField txt = new TextField();
			txt.setAlignment(Pos.CENTER);
			txt.setMaxWidth(60);
			txt.setMinWidth(60);
			txt.setPrefWidth(60);

			TextField textInicio = new TextField();
			textInicio.setAlignment(Pos.CENTER);
			textInicio.setMaxWidth(100);
			textInicio.setMinWidth(100);
			textInicio.setPrefWidth(100);
			textInicio.setPromptText("Ponto Inicial");

			parametrosGridPane.add(txt, 0, numeroParametros);
			parametrosGridPane.add(textInicio, 1, numeroParametros);
		}

		numeroParametros += 1;
		setConstraints(numeroColunas, dadosExperimentaisGridPane, gridPaneMedida, gridPaneResultadosMedida,resultadosGridPane,parametrosGridPane);

	}

	
	public void removeColumnAction(Integer numeroLinhas, 
			Integer numeroColunas,
			Integer numeroParametros,
			GridPane dadosExperimentaisGridPane,
			GridPane resultadosGridPane,
			GridPane gridPaneMedida,
			GridPane gridPaneResultadosMedida,
			GridPane parametrosGridPane,
			ComboBox<Text> newComboBoxMetodo) {

		if (numeroColunas > 1) {
			for (int i = 0; i < numeroLinhas; i++) {
				manipulateNode.removeNodeByRowColumnIndex(i, numeroColunas - 1, dadosExperimentaisGridPane);
			}
			numeroColunas -= 1;
			setConstraints(numeroColunas, dadosExperimentaisGridPane, gridPaneMedida, gridPaneResultadosMedida,resultadosGridPane,parametrosGridPane);
		}
	}
	
	
	private void setConstraints(Integer numeroColunas,
			GridPane dadosExperimentaisGridPane,
			GridPane gridPaneMedida,
			GridPane gridPaneResultadosMedida,
			GridPane resultadosGridPane,
			GridPane parametrosGridPane
			) {

		dadosExperimentaisGridPane.getColumnConstraints().clear();
		dadosExperimentaisGridPane.getRowConstraints().clear();

		gridPaneMedida.getColumnConstraints().clear();
		gridPaneMedida.getRowConstraints().clear();

		gridPaneResultadosMedida.getColumnConstraints().clear();
		gridPaneResultadosMedida.getRowConstraints().clear();
		gridPaneResultadosMedida.setAlignment(Pos.TOP_CENTER);

		resultadosGridPane.getColumnConstraints().clear();
		resultadosGridPane.getRowConstraints().clear();
		resultadosGridPane.setAlignment(Pos.TOP_CENTER);

		parametrosGridPane.getColumnConstraints().clear();
		parametrosGridPane.getRowConstraints().clear();
		parametrosGridPane.setAlignment(Pos.TOP_LEFT);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setFillWidth(false);
		col1.setMinWidth(100);
		col1.setMaxWidth(100);
		col1.setHalignment(HPos.CENTER);

		for (int i = 0; i < numeroColunas; i++) {
			dadosExperimentaisGridPane.getColumnConstraints().add(col1);
		}

		ColumnConstraints colParametro = new ColumnConstraints();
		colParametro.setFillWidth(false);
		colParametro.setMinWidth(50);
		colParametro.setMaxWidth(50);
		colParametro.setHalignment(HPos.LEFT);

		ColumnConstraints colMin = new ColumnConstraints();
		colMin.setFillWidth(false);
		colMin.setMinWidth(100);
		colMin.setMaxWidth(100);
		colMin.setHalignment(HPos.LEFT);
		parametrosGridPane.getColumnConstraints().addAll(colParametro, colMin);

		ColumnConstraints colResultados = new ColumnConstraints();
		colResultados.setFillWidth(false);
		colResultados.setMinWidth(100);
		colResultados.setMaxWidth(100);
		colResultados.setHalignment(HPos.CENTER);
		resultadosGridPane.getColumnConstraints().add(colResultados);

		dadosExperimentaisGridPane.setHgap(20);
		gridPaneMedida.setHgap(20);
		gridPaneMedida.setVgap(10);
		gridPaneResultadosMedida.setHgap(20);
		gridPaneResultadosMedida.setVgap(10);
		dadosExperimentaisGridPane.setVgap(5);
		resultadosGridPane.setVgap(5);
		parametrosGridPane.setHgap(20);
		parametrosGridPane.setVgap(5);

	}
	

	public void removeParametroAction(Integer numeroLinhas, 
			Integer numeroColunas,
			Integer numeroParametros,
			GridPane dadosExperimentaisGridPane,
			GridPane resultadosGridPane,
			GridPane gridPaneMedida,
			GridPane gridPaneResultadosMedida,
			GridPane parametrosGridPane,
			ComboBox<Text> newComboBoxMetodo) {

		if (numeroParametros > 0) {

			if (parametrosGridPane.getColumnCount() > 2) {

				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 0, parametrosGridPane);
				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 1, parametrosGridPane);
				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 2, parametrosGridPane);
			} else {
				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 0, parametrosGridPane);
				manipulateNode.removeNodeByRowColumnIndex(numeroParametros - 1, 1, parametrosGridPane);
			}
			numeroParametros -= 1;
			setConstraints(numeroColunas, dadosExperimentaisGridPane, gridPaneMedida, gridPaneResultadosMedida,resultadosGridPane,parametrosGridPane);

		}

		// parametrosGridPane.getChildren().clear();

	}
	
	public void removeRowAction(Integer numeroLinhas, 
			Integer numeroColunas,
			Integer numeroParametros,
			GridPane dadosExperimentaisGridPane,
			GridPane resultadosGridPane,
			GridPane gridPaneMedida,
			GridPane gridPaneResultadosMedida,
			GridPane parametrosGridPane,
			ComboBox<Text> newComboBoxMetodo) {

		if (numeroLinhas > 1) {
			for (int i = 0; i < numeroColunas; i++) {

				manipulateNode.removeNodeByRowColumnIndex(numeroLinhas - 1, i, dadosExperimentaisGridPane);
			}
			manipulateNode.removeNodeByRowColumnIndex(numeroLinhas - 2, 0, resultadosGridPane);
			numeroLinhas -= 1;
			setConstraints(numeroColunas, dadosExperimentaisGridPane, gridPaneMedida, gridPaneResultadosMedida,resultadosGridPane,parametrosGridPane);
		}

	}


}
