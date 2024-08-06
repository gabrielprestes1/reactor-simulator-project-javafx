package model.entities;

import gui.PFRFormController;

public class Reactor {

    PFRFormController controller = new PFRFormController();

    private Double CA0 = controller.getCA0();
    private Double CB0 = controller.getCB0();
    private Double CC0 = controller.getCC0();
    private Double CD0 = controller.getCD0();
    private Double k0 = controller.getK0();
    private Double Ea = controller.getEa();
    private Double Rho = controller.getRho();
    private Double Vz = controller.getQ();
    private Double Dab = controller.getDab();
    private Double L = controller.getL();

    private Double Cp;
    private Double deltah;
    private Double U;
    private Double T0;

    private Double time = 500000.00;
    private Double R = 8.314;

    private Double FinalTime = 1000.00;
    private Double MinStep = 0.001;
    private Double MaxStep = 0.1;

    private Integer nParticoes = 20;

    public Integer getnParticoes() {
        return nParticoes;
    }

    public Double getFinalTime() {
        return FinalTime;
    }

    public Double getMinStep() {
        return MinStep;
    }

    public Double getMaxStep() {
        return MaxStep;
    }

    public Double getL() {
        return L;
    }

    public Double getCA0() {
        return CA0;
    }

    public Double getCB0() {
        return CB0;
    }

    public Double getK0() {
        return k0;
    }

    public Double getEa() {
        return Ea;
    }

    public Double getT0() {
        return T0;
    }

    public Double getCC0() {
        return CC0;
    }

    public Double getCD0() {
        return CD0;
    }

    public Double getTime() {
        return time;
    }

    public Double getR() {
        return R;
    }

    public Double getRho() {
        return Rho;
    }

    public Double getVz() {
        return Vz;
    }

    public Double getDab() {
        return Dab;
    }
}
