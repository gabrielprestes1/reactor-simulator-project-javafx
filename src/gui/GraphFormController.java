package gui;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.entities.Reactor;

import java.text.DecimalFormat;
import java.util.*;

public class GraphFormController {

    @FXML
    private LineChart<?, ?> chartTemp;

    @FXML
    private LineChart<?, ?> chartCon;

    //= new LineChart<>(new NumberAxis(), new NumberAxis());


    public void setResults(List<List<Double>> reults, List<String> equacoes, Integer particoes, Reactor reactor){

        Locale.setDefault(Locale.US);

        DecimalFormat formatter = new DecimalFormat("#0.00000");


        XYChart.Series caXZ = new XYChart.Series();
        XYChart.Series cbXZ = new XYChart.Series();
        XYChart.Series ccXZ = new XYChart.Series();
        //XYChart.Series cdXZ = new XYChart.Series();
        //XYChart.Series TXZ = new XYChart.Series();


        for (int i = 0; i < reults.get(reults.size() - 1).size() / (equacoes.size()); i++) {

            Double exp = Double.parseDouble(formatter.format(i*reactor.getL() / Double.valueOf(particoes)));
            Double Ca = Double.parseDouble(formatter.format(reults.get(reults.size() - 1).get(equacoes.size() * i)));
            Double Cb = Double.parseDouble(formatter.format(reults.get(reults.size() - 1).get(equacoes.size() * i + 1)));
            Double Cc = Double.parseDouble(formatter.format(reults.get(reults.size() - 1).get(equacoes.size() * i + 2)));
            //Double Cd = Double.parseDouble(formatter.format(reults.get(reults.size() - 1).get(equacoes.size() * i + 3)));
            //Double T = Double.parseDouble(formatter.format(reults.get(reults.size() - 1).get(equacoes.size() * i + 2)));


            caXZ.getData().add(new XYChart.Data(exp.toString(), Ca));
            cbXZ.getData().add(new XYChart.Data(exp.toString(), Cb));
            ccXZ.getData().add(new XYChart.Data(exp.toString(), Cc));
            //cdXZ.getData().add(new XYChart.Data(exp.toString(), Cd));
            //TXZ.getData().add(new XYChart.Data(exp.toString(), T));

        }

        caXZ.setName("A Concentration");
        cbXZ.setName("B Concentration");
        ccXZ.setName("C Concentration");
        //cdXZ.setName("D Concentration");
        //TXZ.setName("Temperatura K");

        chartCon.getData().addAll(caXZ, cbXZ, ccXZ);
        //chartTemp.getData().add(TXZ);

    }

}
