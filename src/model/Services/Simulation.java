package model.Services;

import OdeSolver.DiferencasFinitas;
import OdeSolver.RKF45Storage;
import gui.LoadingBarController;
import gui.util.Alerts;
import javafx.scene.control.Alert;
import model.entities.PFR;
import model.entities.Reactor;



import java.util.ArrayList;
import java.util.List;

public class Simulation{


    private Double L = 0.0;
    private Integer nParticoes = 1;

    private Double progress;

    private List<Double> tValues = new ArrayList<Double>();
    private List<String> equacoes = new ArrayList<String>();
    private List<String> listaVariaveis = new ArrayList<String>();
    private List<String> condicoesIniciais = new ArrayList<String>();
    private List<List<String>> resultadoDiferencasFinitas = new ArrayList<List<String>>();
    private List<String> variaveis = new ArrayList<String>();
    private List<String> funcoes = new ArrayList<String>();
    private  List<Double> resultadosIniciais = new ArrayList<Double>();
    private List<List<Double>> resultados = new ArrayList<List<Double>>();

    DiferencasFinitas diferencasFinitas = new DiferencasFinitas();
    Reactor reactor = new Reactor();

    public List<List<Double>> simulated(){

        resultados.clear();
        equacoes.clear();
        tValues.clear();


        L = reactor.getL();

        equacoes = new PFR().pdeBuilder(reactor);

        try{
            nParticoes = Integer.parseInt(reactor.getnParticoes().toString());
        }
        catch(Exception e) {
            String errorParameter = "Axial Partitions";
            Alerts.showAlert("Parameter not set or invalid", "Parameter: " + errorParameter, null, Alert.AlertType.ERROR);
        }

        Double z0 = 0.0;
        Double zf = 1.0;

        condicoesIniciais.add(reactor.getCA0().toString());
        condicoesIniciais.add(reactor.getCB0().toString());
        condicoesIniciais.add(reactor.getT0().toString());

        Integer numeroEquacoes = equacoes.size();

        listaVariaveis.add("t");


        for (int i = 0; i < numeroEquacoes; i++) {
            String equacao = equacoes.get(i).split("=")[1];
            resultadoDiferencasFinitas
                    .add(diferencasFinitas.diferencasFinitas(equacao, nParticoes, z0, zf, condicoesIniciais.get(i)));
            String variavel = equacoes.get(i).split("=")[0];
            variaveis.add(variavel);
        }

        for (int i = 0; i <= nParticoes; i++) {
            for (int j = 0; j < variaveis.size(); j++) {
                listaVariaveis.add(variaveis.get(j) + String.valueOf(i));
            }
        }

        for (int i = 0; i < resultadoDiferencasFinitas.get(0).size(); i++) {
            for (int j = 0; j < numeroEquacoes; j++) {
                funcoes.add(resultadoDiferencasFinitas.get(j).get(i));
            }

        }

        for (int i = 1; i < listaVariaveis.size(); i++) {
            resultadosIniciais.add(Double.valueOf(condicoesIniciais.get(0)));
            resultadosIniciais.add(Double.valueOf(condicoesIniciais.get(1)));
            resultadosIniciais.add(Double.valueOf(condicoesIniciais.get(2)));
        }

        RKF45Storage rkf45 = new RKF45Storage();

        List<List<Double>> Resultados = rkf45.Resolve(funcoes, listaVariaveis, resultadosIniciais,
                Double.valueOf(reactor.getFinalTime().toString()),
                Double.valueOf(reactor.getMinStep().toString()), Double.valueOf(reactor.getMaxStep().toString()));

        resultados.addAll(Resultados);
        return resultados;
    }


}
