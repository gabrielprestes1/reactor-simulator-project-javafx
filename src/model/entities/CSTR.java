package model.entities;

import java.util.ArrayList;
import java.util.List;


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




        String a = "A= " + "d[A0](t,z)/dt+" + tConv + "*( ([A0](t,z))-" + CA0 + ")" + "";
        String b = "B= ";


        equations.add(a);
        equations.add(b);





        return equations;

    }












}
