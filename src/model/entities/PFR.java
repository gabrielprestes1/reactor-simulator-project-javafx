package model.entities;

import gui.funcoesauxiliares.KineticsBuilder;

import java.util.ArrayList;
import java.util.List;

public class PFR {



    Double Rho = 0.9;
    Double Cp = 50.00;
    Double deltah = 830.00;
    Double U = 20000.00;
    Double T0 = 436.00;
    Double Vz = 0.001;
    Double R = 8.314;
    Double Dab = 0.295;

    Double UTC = (2*U)/R;



    public List<String> pdeBuilder(Reactor reactor) {

        List<String> equations = new ArrayList<String>();

        String reaction = new KineticsBuilder().kineticsBuilder(reactor);

        String a = "A= " +  Vz + "*d[A0](t,z)/dz+" + Dab + "*d2[A0](t,z)/dz2-" + reaction;
        String b = "B= " +  Vz + "*d[B0](t,z)/dz+" + Dab + "*d2[B0](t,z)/dz2+" + reaction;

        String c = "T= " + "-" + Vz*Rho*Cp + "*d[T0](t,z)/dz+" + UTC + "*([T0](t,z)-" + T0 + ")-" + reaction + "*(-" + deltah + ")";

        equations.add(a);
        equations.add(b);
        equations.add(c);


        return equations;

    }
}
