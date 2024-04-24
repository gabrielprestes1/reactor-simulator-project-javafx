package OdeSolver;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RK4SistemasEdos {

	public List<List<Double>> Resolve(
			List<String> funcoes, 
			List<String> listaVariaveis, 
			List<Double> resultadoInicial) {
		
		Set<String> setVariaveis = new HashSet<String>(listaVariaveis);

		List<List<Double>> resultados = new ArrayList<List<Double>>();

		List<Double> X = new ArrayList<Double>();

		List<List<Double>> K = new ArrayList<List<Double>>();

		Double a = 0.0;
		Double b = 1.0;
		Double h = 0.0001;

		for (int i = 0; i < 6; i++) {
			List<Double> listaK = new ArrayList<Double>();
			for (int j = 0; j < listaVariaveis.size() - 1; j++) { // +1 pois são n+1 pontos na discretização para n= ?													// intervalos
				listaK.add(0.0);
			}
			K.add(listaK);
		}

		Integer numeroIntervalos = (int) Math.ceil((b - a) / h);

		Integer numeroPontos = numeroIntervalos + 1;

		for (int i = 0; i < numeroPontos; i++) {
			X.add(a + i * h);
		}

		resultados.add(resultadoInicial);

		List<Expression> expressoes = new ArrayList<Expression>();

		for (int i = 0; i < funcoes.size(); i++) {

			expressoes.add(new ExpressionBuilder(funcoes.get(i)).variables(setVariaveis).build());
		}

		for (int i = 0; i < numeroPontos; i++) {

			for (int k = 0; k < 6; k++) {

				if (k == 0) {
					for (int f = 0; f < expressoes.size(); f++) {

						expressoes.get(f).setVariable(listaVariaveis.get(0), X.get(i));

						for (int v = 1; v < listaVariaveis.size(); v++) {

							expressoes.get(f).setVariable(listaVariaveis.get(v), resultados.get(i).get(v - 1));

						}

						K.get(k).set(f, h * expressoes.get(f).evaluate());
					}

				} else if (k == 1) {
					for (int f = 0; f < expressoes.size(); f++) {
						expressoes.get(f).setVariable(listaVariaveis.get(0), X.get(i) + h / 4.0);
						for (int v = 1; v < listaVariaveis.size(); v++) {

							expressoes.get(f).setVariable(listaVariaveis.get(v),
									resultados.get(i).get(v - 1) + (1.0 / 4.0) * K.get(0).get(v - 1));
						}
						K.get(k).set(f, h * expressoes.get(f).evaluate());
					}

				} else if (k == 2) {
					for (int f = 0; f < expressoes.size(); f++) {
						expressoes.get(f).setVariable(listaVariaveis.get(0), X.get(i) + 3.0 * h / 8.0);
						for (int v = 1; v < listaVariaveis.size(); v++) {

							expressoes.get(f).setVariable(listaVariaveis.get(v), resultados.get(i).get(v - 1)
									+ (3.0 / 32.0) * K.get(0).get(v - 1) + (9.0 / 32.0) * K.get(1).get(v - 1));
						}
						K.get(k).set(f, h * expressoes.get(f).evaluate());
					}

				} else if (k == 3) {
					for (int f = 0; f < expressoes.size(); f++) {
						expressoes.get(f).setVariable(listaVariaveis.get(0), X.get(i) + 12 * h / 13);
						for (int v = 1; v < listaVariaveis.size(); v++) {

							expressoes.get(f).setVariable(listaVariaveis.get(v),
									resultados.get(i).get(v - 1) + (1932.0 / 2197.0) * K.get(0).get(v - 1)
											- (7200.0 / 2197.0) * K.get(1).get(v - 1)
											+ (7296.0 / 2197.0) * K.get(2).get(v - 1));
						}
						K.get(k).set(f, h * expressoes.get(f).evaluate());
					}

				} else if (k == 4) {
					for (int f = 0; f < expressoes.size(); f++) {
						expressoes.get(f).setVariable(listaVariaveis.get(0), X.get(i) + h);
						for (int v = 1; v < listaVariaveis.size(); v++) {

							expressoes.get(f).setVariable(listaVariaveis.get(v),
									resultados.get(i).get(v - 1) + (439.0 / 216.0) * K.get(0).get(v - 1)
											- (8.0) * K.get(1).get(v - 1) + (3680.0 / 513.0) * K.get(2).get(v - 1)
											- (845.0 / 4104.0) * K.get(3).get(v - 1));
						}
						K.get(k).set(f, h * expressoes.get(f).evaluate());
					}

				} else if (k == 5) {
					for (int f = 0; f < expressoes.size(); f++) {
						expressoes.get(f).setVariable(listaVariaveis.get(0), X.get(i) + h / 2);
						for (int v = 1; v < listaVariaveis.size(); v++) {

							expressoes.get(f).setVariable(listaVariaveis.get(v),
									resultados.get(i).get(v - 1) - (8.0 / 27.0) * K.get(0).get(v - 1)
											+ (2.0) * K.get(1).get(v - 1) - (3544.0 / 2565.0) * K.get(2).get(v - 1)
											+ (1859.0 / 4104.0) * K.get(3).get(v - 1)
											- (11.0 / 40.0) * K.get(4).get(v - 1));
						}
						K.get(k).set(f, h * expressoes.get(f).evaluate());
					}

				}

			}

			List<Double> resultado = new ArrayList<Double>();

			// condicoes de contorno
			resultado.add(resultadoInicial.get(0));
			resultado.add(resultadoInicial.get(1));
			resultado.add(resultadoInicial.get(2));
			resultado.add(resultadoInicial.get(3));
			if(resultadoInicial.size()/(listaVariaveis.size()-1) == 5) {
				resultado.add(resultadoInicial.get(4));
			}
			

			/*for (int f = resultadoInicial.size()/(listaVariaveis.size()-1); f < listaVariaveis.size() - 1; f++) {

				resultado.add(resultados.get(i).get(f) + ((16.0 / 135.0) * K.get(0).get(f)
						+ (6656.0 / 12825.0) * K.get(2).get(f) + (28561.0 / 56430.0) * K.get(3).get(f)
						- (9.0 / 50.0) * K.get(4).get(f) + (2.0 / 55.0) * K.get(5).get(f)));

			}*/

			Double error = 0.0;
			
			for (int f = resultadoInicial.size()/(listaVariaveis.size()-1); f < listaVariaveis.size() - 1; f++) {
				
				Double resultadorkf45 = resultados.get(i).get(f) + ((16.0 / 135.0) * K.get(0).get(f)
						+ (6656.0 / 12825.0) * K.get(2).get(f) + (28561.0 / 56430.0) * K.get(3).get(f)
						- (9.0 / 50.0) * K.get(4).get(f) + (2.0 / 55.0) * K.get(5).get(f));
				resultado.add(resultadorkf45);
		
				 try {
				       Thread.sleep(50);
				    } catch(InterruptedException e) {
				    }
				Double resultadork4 = resultados.get(i).get(f)+ (25.0/16.0)*K.get(0).get(f)+(1408.0/2565.0)*K.get(2).get(f)+(2197.0/4104.0)*K.get(3).get(f)-K.get(4).get(f)/5.0;
				error +=  resultadorkf45- resultadork4;
	
			}
			
			resultados.add(resultado);

			
			
			

		}

	

		
		return resultados;
	}
	
	

}
