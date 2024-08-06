package gui;

import gui.util.Alerts;
import gui.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;


public class PFRFormController implements Initializable {

    @FXML
    private Button insertButton, nextButton, exitButton, saveButton;

    @FXML
    private TextField textReaction, textConcA, textConcC, textConcB, textConcD, textFrequencyFactor, textActivationEnergy;

    @FXML
    private TextField width, flow, density, diffusion, initialTemperature, specificHeat, coefficientHeat, deltaH;

    @FXML
    private CheckBox abstractCheckBox, isothermalCheckBox, isobaricCheckBox;

    @FXML
    private Label concA, concB, concC, concD, frequencyFactor, activationEnergy, textInitialTemperature, textSpecificHeat;

    @FXML
    private Label textCoefficientHeat, textDeltaH;

    @FXML
    private Label un1, un2, un3, un4, un5, un6, un7, un8, un9;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab reaction, getData;

    private String kinetics = "";
    private Integer numberCompounds = 0;
    private double CA0;
    private double CB0;
    private double CC0;
    private double CD0;
    private double K0;
    private double Ea;
    private double L;
    private double Q;
    private double rho;
    private double Dab;


    Utils util = new Utils();

    @FXML
    private void onInsertButtonAction() {

        String reaction = textReaction.getText();

        if (reaction.contains("->")) {

            startLabels();

            concA.setVisible(true);
            concB.setVisible(true);
            textConcB.setVisible(true);
            textConcA.setVisible(true);
            un1.setVisible(true);
            un2.setVisible(true);
            frequencyFactor.setVisible(true);
            textFrequencyFactor.setVisible(true);

            String[] parts = reaction.split("->");

            if (parts.length != 2) {
                Alerts.showAlert("Error", "Error", "Invalid reaction format", Alert.AlertType.ERROR);
            }

            String reagents = parts[0].trim();
            String products = parts[1].trim();

            if (util.validateNumberOfCompounds(reaction)) {
                Alerts.showAlert("Error", "Error", "Maximum of 4 compounds allowed in the reaction", Alert.AlertType.ERROR);
                return;
            }

            if (abstractCheckBox.isSelected()) {

                List<Character> reagentsList = util.extractLetters(reagents);
                List<Character> productsList = util.extractLetters(products);
                List<Integer> reagentsNumber = util.extractNumber(reagents);

                numberCompounds = reagentsNumber.size();

                if (reagentsList.size() == 1){

                    concA.setText("Initial Concentration of " + reagentsList.get(0));
                    concB.setText("Initial Concentration of " + productsList.get(0));

                    kinetics = "[A0](t,z)^" + reagentsNumber.get(0).toString();
                    if (productsList.size() == 2){
                        concB.setText("Initial Concentration of " + productsList.get(0));
                        concC.setText("Initial Concentration of " + productsList.get(1));

                        concC.setVisible(true);
                        textConcC.setVisible(true);
                        un3.setVisible(true);
                        
                    } else if (productsList.size() == 3) {
                        concB.setText("Initial Concentration of " + productsList.get(0));
                        concC.setText("Initial Concentration of " + productsList.get(1));
                        concD.setText("Initial Concentration of " + productsList.get(2));

                        concD.setVisible(true);
                        textConcD.setVisible(true);
                        un4.setVisible(true);
                    }
                } else if (reagentsList.size() == 2 ) {
                    concA.setText("Initial Concentration of " + reagentsList.get(0));
                    concB.setText("Initial Concentration of " + reagentsList.get(1));
                    concC.setText("Initial Concentration of " + productsList.get(0));

                    concC.setVisible(true);
                    textConcC.setVisible(true);
                    un3.setVisible(true);

                    kinetics = "([A0](t,z)^" + reagentsNumber.get(0).toString() + ")*([B0](t,z)^" + reagentsNumber.get(1).toString() + ")";
                    if (productsList.size() == 2) {
                        concC.setText("Initial Concentration of " + productsList.get(0));
                        concD.setText("Initial Concentration of " + productsList.get(1));

                        concD.setVisible(true);
                        textConcD.setVisible(true);
                        un4.setVisible(true);
                    }
                } else if (reagentsList.size() == 3) {
                    concA.setText("Initial Concentration of " + reagentsList.get(0));
                    concB.setText("Initial Concentration of " + reagentsList.get(1));
                    concC.setText("Initial Concentration of " + reagentsList.get(2));
                    concD.setText("Initial Concentration of " + productsList.get(0));

                    concC.setVisible(true);
                    textConcC.setVisible(true);
                    un3.setVisible(true);
                    concD.setVisible(true);
                    textConcD.setVisible(true);
                    un4.setVisible(true);

                    kinetics = "([A0](t,z)^" + reagentsNumber.get(0).toString() + ")*([B0](t,z)^" + reagentsNumber.get(1).toString()
                               + ")*([C0](t,z)^" + reagentsNumber.get(2).toString() +  ")";
                }

            } else {

                Map<String, Double> reagentsMap = util.parseCompounds(reagents);
                Map<String, Double> productsMap = util.parseCompounds(products);

                if (util.isBalanced(reagentsMap, productsMap)) {
                    Alerts.showAlert("Balanced", "Balanced", "The reaction is balanced", Alert.AlertType.INFORMATION);
                } else {
                    Alerts.showAlert("Unbalanced", "Unbalanced", "The reaction is unbalanced", Alert.AlertType.WARNING);
                }
            }
        } else if (reaction.contains("<>")) {
            // Handle reversible reaction similarly
        } else {
            Alerts.showAlert("Error", "Error", "Invalid reaction format", Alert.AlertType.ERROR);
        }

        if (!isothermalCheckBox.isSelected()) {
            activationEnergy.setVisible(true);
            textActivationEnergy.setVisible(true);
            un5.setVisible(true);
        }

    }

    @FXML
    private void onNextButton(){

        tabPane.getSelectionModel().selectNext();

        if (reaction.isSelected()){
            nextButton.setText("Next");
            tabPane.getSelectionModel().selectNext();
        }

        if (getData.isSelected()){
            nextButton.setText("Back");
            tabPane.getSelectionModel().selectPrevious();
        }

    }

    @FXML
    private void onSaveButton(){

        if (textReaction.getText().isEmpty() ||
                textConcA.getText().isEmpty() ||
                textConcB.getText().isEmpty() ||
                textFrequencyFactor.getText().isEmpty() ||
                (textConcC.isVisible() && textConcC.getText().isEmpty()) ||
                (textConcD.isVisible() && textConcD.getText().isEmpty()) ||
                (activationEnergy.isVisible() && activationEnergy.getText().isEmpty())) {

            Alerts.showAlert("Error", "Error", "All fields must be filled", Alert.AlertType.ERROR);
        }

        CA0 = Double.parseDouble(textConcA.getText());
        CB0 = Double.parseDouble(textConcB.getText());
        K0 = Double.parseDouble(textFrequencyFactor.getText());

        if (textConcC.isVisible()){
            CC0 = Double.parseDouble(textConcB.getText());
        } else if (textConcD.isVisible()){
            CC0 = Double.parseDouble(textConcB.getText());
            CD0 = Double.parseDouble(textConcD.getText());
        }

        if (textActivationEnergy.isVisible()){
            Ea = Double.parseDouble(textActivationEnergy.getText());
        }
        /*
            if (width.getText().isEmpty() ||
                    flow.getText().isEmpty() ||
                    density.getText().isEmpty() ||
                    diffusion.getText().isEmpty() ||
                    (initialTemperature.isVisible() && initialTemperature.getText().isEmpty()) ||
                    (specificHeat.isVisible() && specificHeat.getText().isEmpty()) ||
                    (coefficientHeat.isVisible() && coefficientHeat.getText().isEmpty()) ||
                    (deltaH.isVisible() && deltaH.getText().isEmpty())) {

                Alerts.showAlert("Error", "Error", "All fields must be filled", Alert.AlertType.ERROR);
            }

             */

        L = Double.parseDouble(width.getText());
        Q = Double.parseDouble(flow.getText());
        rho = Double.parseDouble(density.getText());
        Dab = Double.parseDouble(diffusion.getText());

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

    }


    @FXML
    private void onExitButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onIsobaricPress(){
        if (isobaricCheckBox.isSelected()) {
            startLabels();
        } else {
            textInitialTemperature.setVisible(false);
            textSpecificHeat.setVisible(false);
            textCoefficientHeat.setVisible(false);
            textDeltaH.setVisible(false);
            initialTemperature.setVisible(false);
            specificHeat.setVisible(false);
            coefficientHeat.setVisible(false);
            deltaH.setVisible(false);
            un6.setVisible(false);
            un7.setVisible(false);
            un8.setVisible(false);
            un9.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startLabels();
    }

    private void startLabels(){
        concA.setVisible(false);
        concB.setVisible(false);
        concC.setVisible(false);
        concD.setVisible(false);
        activationEnergy.setVisible(false);
        frequencyFactor.setVisible(false);
        textConcA.setVisible(false);
        textConcB.setVisible(false);
        textConcC.setVisible(false);
        textConcD.setVisible(false);
        textActivationEnergy.setVisible(false);
        textFrequencyFactor.setVisible(false);
        un1.setVisible(false);
        un2.setVisible(false);
        un3.setVisible(false);
        un4.setVisible(false);
        un5.setVisible(false);

        textInitialTemperature.setVisible(false);
        textSpecificHeat.setVisible(false);
        textCoefficientHeat.setVisible(false);
        textDeltaH.setVisible(false);
        initialTemperature.setVisible(false);
        specificHeat.setVisible(false);
        coefficientHeat.setVisible(false);
        deltaH.setVisible(false);
        un6.setVisible(false);
        un7.setVisible(false);
        un8.setVisible(false);
        un9.setVisible(false);
    }

    public String getKinetics() {
        return kinetics;
    }

    public Integer getNumberCompounds() {
        return numberCompounds;
    }

    public Double getCA0() {
        return CA0;
    }

    public Double getCB0() {
        return CB0;
    }

    public Double getCC0() {
        return CC0;
    }

    public Double getCD0() {
        return CD0;
    }

    public Double getK0() {
        return K0;
    }

    public Double getEa() {
        return Ea;
    }

    public Double getDab() {
        return Dab;
    }

    public Double getRho() {
        return rho;
    }

    public Double getQ() {
        return Q;
    }

    public Double getL() {
        return L;
    }
}
