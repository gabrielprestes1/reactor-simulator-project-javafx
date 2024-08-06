package OdeSolver;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class RKF45Storage {

	public PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public Double progress = 0.0;

	public List<List<Double>> Resolve(List<String> funcoes, List<String> listaVariaveis, List<Double> resultadoInicial,
									  Double finalT, Double hMin, Double hMax, Integer numberEquation) {

		Set<String> setVariaveis = new HashSet<String>(listaVariaveis);

		List<List<Double>> resultados = new ArrayList<List<Double>>();

		List<List<Double>> K = new ArrayList<List<Double>>();

		Double a = 0.0;
		Double b = finalT;
		Double tol = 0.9;
		Double hmin = hMin;
		Double hmax = hMax;
		Double h = hmin;

		for (int i = 0; i < 6; i++) {
			List<Double> listaK = new ArrayList<Double>();
			for (int j = 0; j < listaVariaveis.size() - 1; j++) {

				listaK.add(0.0);
			}
			K.add(listaK);
		}

		List<Expression> expressoes = new ArrayList<Expression>();

		for (int i = 0; i < funcoes.size(); i++) {

			expressoes.add(new ExpressionBuilder(funcoes.get(i)).variables(setVariaveis).build());
		}

		Double t = a;

		resultados.add(resultadoInicial);

		Integer i = 0;

		while (t < b) {

			setProperty(t / b);
			Double error = 1.0;

			while (error > tol || Double.isNaN(error)) {

				KCalculate(expressoes, K, resultados, listaVariaveis, t, h, i);

				error = 0.0;

				for (int f = resultadoInicial.size() / (listaVariaveis.size() - 1); f < listaVariaveis.size()
						- 1; f++) {

					Double resultadorkf45 = resultados.get(i).get(f) + ((16.0 / 135.0) * K.get(0).get(f)
							+ (6656.0 / 12825.0) * K.get(2).get(f) + (28561.0 / 56430.0) * K.get(3).get(f)
							- (9.0 / 50.0) * K.get(4).get(f) + (2.0 / 55.0) * K.get(5).get(f));

					Double resultadork4 = resultados.get(i).get(f) + (25.0 / 216.0) * K.get(0).get(f)
							+ (1408.0 / 2565.0) * K.get(2).get(f) + (2197.0 / 4104.0) * K.get(3).get(f)
							- K.get(4).get(f) / 5.0;
					error += Math.abs(resultadorkf45 - resultadork4);

				}

				if(h < 0.0000000000001) {
					return resultados;
				}
				if (error > tol || Double.isNaN(error)) {
					Double novoh = h / 10.0;
					h = novoh;

				}

			}

			List<Double> resultado = new ArrayList<Double>();

			// condicoes de contorno
			for (int j = 0; j < numberEquation; j++) {
				resultado.add(resultadoInicial.get(j));
			}


			if (resultadoInicial.size() / (listaVariaveis.size() - 1) == 5) {
				resultado.add(resultadoInicial.get(4));
			}

			for (int f = resultadoInicial.size() / (listaVariaveis.size() - 1); f < listaVariaveis.size() - 1; f++) {
				resultado.add(resultados.get(i).get(f) + ((16.0 / 135.0) * K.get(0).get(f)
						+ (6656.0 / 12825.0) * K.get(2).get(f) + (28561.0 / 56430.0) * K.get(3).get(f)
						- (9.0 / 50.0) * K.get(4).get(f) + (2.0 / 55.0) * K.get(5).get(f)));
			}

			i++;

			resultados.add(resultado);

			t = t + h;

			h = hmax;

		}

		return resultados;
	}

	private void KCalculate(List<Expression> expressoes, List<List<Double>> K, List<List<Double>> resultados,
							List<String> listaVariaveis, Double t, Double h, Integer i) {

		for (int k = 0; k < 6; k++) {

			if (k == 0) {

				for (int f = 0; f < expressoes.size(); f++) {

					expressoes.get(f).setVariable(listaVariaveis.get(0), t);

					for (int v = 1; v < listaVariaveis.size(); v++) {

						expressoes.get(f).setVariable(listaVariaveis.get(v), resultados.get(i).get(v - 1));

					}

					K.get(k).set(f, h * expressoes.get(f).evaluate());
				}

			} else if (k == 1) {
				for (int f = 0; f < expressoes.size(); f++) {
					expressoes.get(f).setVariable(listaVariaveis.get(0), t + (h / 4.0));
					for (int v = 1; v < listaVariaveis.size(); v++) {

						expressoes.get(f).setVariable(listaVariaveis.get(v),
								resultados.get(i).get(v - 1) + (1.0 / 4.0) * K.get(0).get(v - 1));
					}
					K.get(k).set(f, h * expressoes.get(f).evaluate());
				}

			} else if (k == 2) {
				for (int f = 0; f < expressoes.size(); f++) {
					expressoes.get(f).setVariable(listaVariaveis.get(0), t + 3.0 * h / 8.0);
					for (int v = 1; v < listaVariaveis.size(); v++) {

						expressoes.get(f).setVariable(listaVariaveis.get(v), resultados.get(i).get(v - 1)
								+ (3.0 / 32.0) * K.get(0).get(v - 1) + (9.0 / 32.0) * K.get(1).get(v - 1));
					}
					K.get(k).set(f, h * expressoes.get(f).evaluate());
				}

			} else if (k == 3) {
				for (int f = 0; f < expressoes.size(); f++) {
					expressoes.get(f).setVariable(listaVariaveis.get(0), t + (12 * h / 13));
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
					expressoes.get(f).setVariable(listaVariaveis.get(0), t + h);
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
					expressoes.get(f).setVariable(listaVariaveis.get(0), t + (h / 2));
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
	}

	public void addObserver(PropertyChangeListener l) {
		pcs.addPropertyChangeListener("theProperty", l);
	}

	public void setProperty(Double val) {
		Double old = progress;
		progress = val;
		pcs.firePropertyChange("theProperty", old, val);
	}

}
