package model.entities;

public class Reactor {

    private Integer reaction;
    private Double CA0 = 900.0;
    private Double CB0 = 0.0;
    private Double k0 = 2.26*Math.pow(10, 14);
    private Double E = 14570.00;
    private Double T0 = 436.00;
    private Double L = 3.0;

    private Double FinalTime = 10.0;
    private Double MinStep = 0.001;
    private Double MaxStep = 0.1;

    private Integer nParticoes = 10;

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

    public Double getE() {
        return E;
    }

    public Double getT0() {
        return T0;
    }

    public Integer getReaction() {
        return reaction;
    }
}
