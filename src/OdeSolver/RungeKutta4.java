package OdeSolver;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RungeKutta4 {

	
	
	public static void main(String[] args) {
		
		List<String> listaVariaveis = new ArrayList<String>();
		
		Set<String> variaveis = new HashSet<String>();
		
		List<Double> K = new ArrayList<Double>();
		
		for(int i = 0; i < 4; i++) {
			K.add(0.0);
		}
		
		listaVariaveis.add("x");
		listaVariaveis.add("y");
		variaveis.addAll(listaVariaveis);
		
		Double a = 1.0;
		Double b = 2.5; 
		Double h = 0.000001;
		
		
		List<Double> Y = new ArrayList<Double>();
		Y.add(2.0);
		
		List<Double> X = new ArrayList<Double>();
		Integer numeroIntervalos = (int) Math.ceil((b-a)/h);
		Integer numeroPontos = numeroIntervalos + 1;
		for(int i = 0; i < numeroPontos; i++) {
			X.add(a+i*h);
		}
		System.out.println(X);
	
		
		String g = "3*x^2*y";
		
		Expression expressaoG = new ExpressionBuilder(g).variables(variaveis).build();
		
		for(int j = 0; j < numeroIntervalos; j++) {
		for(int i = 0; i <4; i++) {
			
			if(i == 0) {
				expressaoG.setVariable(listaVariaveis.get(0), X.get(j));
				expressaoG.setVariable(listaVariaveis.get(1), Y.get(j));
			} else if(i == 3) {
				expressaoG.setVariable(listaVariaveis.get(0), X.get(j)+h);
				expressaoG.setVariable(listaVariaveis.get(1), Y.get(j)+K.get(2)* h);
			} else {
				expressaoG.setVariable(listaVariaveis.get(0), X.get(j)+h/2);
				expressaoG.setVariable(listaVariaveis.get(1), Y.get(j)+K.get(i-1)* h/2);
			}

			K.set(i,expressaoG.evaluate());
		}
		
		Y.add(Y.get(j)+h*(K.get(0)+2*K.get(1)+2*K.get(2)+K.get(3))/6);
		}
		System.out.println();
		System.out.println(Y.get(Y.size()-1));
		listaVariaveis.add("z");
		variaveis.addAll(listaVariaveis);
	
		//Expression edo = new ExpressionBuilder(expressao).variables(variaveis).build();
	}
	
}
