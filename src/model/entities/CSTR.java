package model.entities;

import gui.funcoesauxiliares.KineticsBuilder;

import java.util.ArrayList;
import java.util.List;


public class CSTR {

    Double CA0 = 900.00;
    Double CB0 = 0.0;
    Double FA0 = 133.700;
    Double A = 21.91;
    Double Rho = 0.9;
    Double V = 3.6;
    Double T0 = 436.00;
    Double Cp = 500.00;
    Double deltah = 83000.00;
    Double U = 20000.00;
    Double Tm = 433.00;

    Double tConv = FA0/(Rho * V);
    Double QExt = (U*A)/(Rho*Cp*V);

    public List<String> pdeBuilder(Reactor reactor) {

        List<String> equations = new ArrayList<String>();

        String reaction = new KineticsBuilder().kineticsBuilder(reactor);

        String a = "A= " + "d[A0](t)/dt+" + tConv + "*( ([A0](t))-" + CA0 + ")+" + reaction;
        String b = "B= " + "d[B0](t)/dt+" + tConv + "*( ([B0](t))-" + CB0 + ")-" + reaction;

        String T = "d[T0](t)/dt+" + tConv + "*([T0](t)-" + T0 + ")-" + deltah*V + "*([A0](t))*" + reaction + "+([T0](t)-" + Tm + ")*" + QExt;


        equations.add(a);
        equations.add(b);





        return equations;

    }












}
