package model.entities;


import flanagan.integration.DerivnFunction;
import flanagan.integration.RungeKutta;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.L;

public class CSTR {

    Double CA0 = 900.00;
    Double CB0 = 0.0;
    Double FA0 = 133.700;
    Double A = 21.91;
    Double Rho = 0.9;
    Double V = 3.6;
    Double k0 = 2.26*Math.pow(10, 14);
    Double E = 14570.00;
    Double T0 = 436.00;
    Double Cp = 500.00;
    Double deltah = 83000.00;
    Double U = 20000.00;
    Double Tm = 433.00;

    Double tConv = FA0/(Rho * V);

    public List<String> pdeBuilder() {

        List<String> equations = new ArrayList<String>();

        /*
        Double tConv = -vz / L;
        Double tDiff = DL / (L * L);
        Double tReac = rhoc * 1000.0 / (3600.0*porosity);



        String a = "A= " + tConv + "*d[A0](t,z)/dz +" + tDiff + "*d2[A0](t,z)/dz2+" + -tReac + "*" + reaction;
        String b = "B= " + tConv + "*d[B0](t,z)/dz +" + tDiff + "*d2[B0](t,z)/dz2+" + -tReac + "*" + reaction;
        String c = "C= " + tConv + "*d[C0](t,z)/dz +" + tDiff + "*d2[C0](t,z)/dz2+" + 2 * tReac + "*" + reaction;
        String d = "D= " + tConv + "*d[D0](t,z)/dz +" + tDiff + "*d2[D0](t,z)/dz2+" + 2 * tReac + "*" + reaction;

        equations.add(a);
        equations.add(b);
        equations.add(c);
        equations.add(d);

        if (!energyController.isotermicoCheckBox.isSelected()) {


            Double condeff = constantes.CondTermEff(Q1, Q2, Tin, porosity, Dt, P);
            Double tTimeT = rhog * CpMix; // OK
            Double tReacT = -2.0*(DeltaH * rhoc / (3600.0*tTimeT));
            Double tDiffT = -(porosity*condeff / (L * L * tTimeT)) ;
            Double tConvT = -(porosity*rhog * CpMix * vz / (L * tTimeT));
            Double tUTC = -(4.0 * Utc / (Dt * tTimeT));



            String e = "T= (" + tReacT + "*" + reaction + "+" + tDiffT + "*d2[T0](t,z)/dz2" + "+" + tConvT
                    + "*d[T0](t,z)/dz+" + tUTC + "*([T0](t,z)-" + Tw + "))";

            equations.add(e);

        }

         */

        return equations;

    }












}
