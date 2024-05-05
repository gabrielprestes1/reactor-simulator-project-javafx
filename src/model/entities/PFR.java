package model.entities;

import gui.funcoesauxiliares.KineticsBuilder;

import java.util.ArrayList;
import java.util.List;

public class PFR {

    Reactor reactor = new Reactor();


    Double Rho = 0.9;
    Double Cp = 50.00;
    Double deltah = 830.00;
    Double U = 20000.00;
    Double T0 = 436.00;
    Double Vz = 0.001;
    Double R = 8.314;
    Double Dab = 0.295;

    Double UTC = (2*U)/R;
    Double tConv = - Vz / reactor.getL();
    Double tDiff = Dab / (reactor.getL() * reactor.getL());



    public List<String> pdeBuilder(Reactor reactor) {

        List<String> equations = new ArrayList<String>();

        String reaction = new KineticsBuilder().kineticsBuilder(reactor);

        String a = "A= " +  tConv + "*d[A0](t,z)/dz +" + tDiff + "*d2[A0](t,z)/dz2+" + reaction;
        String b = "B= " +  tConv + "*d[B0](t,z)/dz +" + tDiff + "*d2[B0](t,z)/dz2-" + reaction;

        String c = "T= " + "-" + Vz*Rho*Cp + "*d[T0](t,z)/dz+" + UTC + "*([T0](t,z)-" + T0 + ")-" + reaction + "*(-" + deltah + ")";

        equations.add(a);
        equations.add(b);
        equations.add(c);


        return equations;

    }
}
