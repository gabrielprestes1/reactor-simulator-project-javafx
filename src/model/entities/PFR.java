package model.entities;

import gui.PFRFormController;
import gui.funcoesauxiliares.KineticsBuilder;

import java.util.ArrayList;
import java.util.List;

public class PFR {

    Reactor reactor = new Reactor();

    Double tConv = - reactor.getVz()/ reactor.getL();
    Double tDiff = reactor.getDab()/ (reactor.getL() * reactor.getL());

    public List<String> pdeBuilder(Reactor reactor) {

        List<String> equations = new ArrayList<String>();

        String reaction = new KineticsBuilder().kineticsBuilder(reactor);

        String a = "A= " +  tConv + "*d[A0](t,z)/dz +" + tDiff + "*d2[A0](t,z)/dz2-" + reaction;
        String b = "B= " +  tConv + "*d[B0](t,z)/dz +" + tDiff + "*d2[B0](t,z)/dz2-" + reaction;
        String c = "C= " +  tConv + "*d[C0](t,z)/dz +" + tDiff + "*d2[C0](t,z)/dz2+" + reaction;
        //String d = "D= " +  tConv + "*d[D0](t,z)/dz +" + tDiff + "*d2[D0](t,z)/dz2+" + reaction;

        //String c = "T= " + tConvT + "*d[T0](t,z)/dz+" + UTC + "*([T0](t,z)-" + T0 + ")-" + k + "*[A0](t,z)*[A0](t,z)" + "*(-" + deltah + ")";

        equations.add(a);
        equations.add(b);
        equations.add(c);

        return equations;
    }
}
