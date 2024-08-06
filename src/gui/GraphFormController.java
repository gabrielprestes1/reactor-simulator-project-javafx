package gui;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class GraphFormController {

    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private String csvFilePath = "C:\\Users\\Gabri\\project-tcc\\reactor-simulator-project-javafx\\src\\model\\simulator\\Dados.csv"; // Substitua pelo caminho real do CSV

    @FXML
    private void initialize() {

        xAxis.setLabel("Distance");
        yAxis.setLabel("Value");


        try {
            loadCSVData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCSVData() throws IOException {
        File file = new File(csvFilePath);
        if (!file.exists()) {
            System.out.println("CSV file not found!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            String[] headers = null;
            XYChart.Series<String, Number> temperatureSeries = new XYChart.Series<>();
            temperatureSeries.setName("Temperature");

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    headers = line.split(",");
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(",");
                if (values.length > 0) {
                    String distance = values[0];
                    double temperature = Double.parseDouble(values[1]); // Supondo que temperatura é a segunda coluna

                    temperatureSeries.getData().add(new XYChart.Data<>(distance, temperature));
                }
            }

            lineChart.getData().add(temperatureSeries);
        }
    }
}

