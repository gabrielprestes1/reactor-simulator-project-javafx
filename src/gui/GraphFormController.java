package gui;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import model.entities.PFR;
import model.entities.Reactor;
import model.entities.Simulation;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class GraphFormController implements Initializable{

    @FXML
    private LineChart<?, ?> chartTemp;

    @FXML
    private LineChart<?, ?> chartCon;


    public void setResults(){

        Locale.setDefault(Locale.US);

        DecimalFormat formatter = new DecimalFormat("#0.00000");

        Simulation simulation = new Simulation();
        List<List<Double>> reults;
        reults = simulation.simulated();


        List<String> equacoes = new ArrayList<String>();
        Reactor reactor = new Reactor();
        equacoes = new PFR().pdeBuilder(reactor);

        Integer nParticoes = 1;
        try{
            nParticoes = Integer.parseInt(reactor.getnParticoes().toString());
        }
        catch(Exception e) {
            String errorParameter = "Axial Partitions";
            Alerts.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null, Alert.AlertType.ERROR);
        }

        final Integer particoes = nParticoes;

        XYChart.Series caXZ = new XYChart.Series();
        XYChart.Series cbXZ = new XYChart.Series();
        XYChart.Series tXZ = new XYChart.Series();



        for (int i = 0; i < reults.get(reults.size() - 1).size() / (equacoes.size()); i++) {

            Double exp = Double.parseDouble(formatter.format(i * reactor.getL() / Double.valueOf(particoes)));
            Double Ca = Double.parseDouble(formatter.format(reults.get(reults.size() - 1).get(equacoes.size() * i)));
            Double Cb = Double.parseDouble(formatter.format(reults.get(reults.size() - 1).get(equacoes.size() * i + 1)));
            Double T = Double.parseDouble(formatter.format(reults.get(reults.size() - 1).get(equacoes.size() * i + 2)));

            caXZ.getData().add(new XYChart.Data(exp.toString(), Ca));
            cbXZ.getData().add(new XYChart.Data(exp.toString(), Cb));
            tXZ.getData().add(new XYChart.Data(exp.toString(), T));

        }
        caXZ.setName("A Concentration");
        cbXZ.setName("B Concentration");
        tXZ.setName("Temperature");

        chartCon.getData().addAll(caXZ, cbXZ);
        chartTemp.getData().add(tXZ);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResults();
    }
}
