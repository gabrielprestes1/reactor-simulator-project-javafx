package OdeSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiferencasFinitas {

	public List<String> diferencasFinitas(String string, Integer nParticoes, Double z0, Double zf, String inicio) {

		Double h = (zf - z0) / Double.valueOf(nParticoes);

		List<String> formulas = new ArrayList<String>();

		List<String> variaveisI = new ArrayList<String>();

		List<String> variaveis = new ArrayList<String>();

		String derivada2 = "d2\\[[A-Z][0-9]\\]\\([a-z],[a-z]\\)/dz2";
		Pattern pattern2 = Pattern.compile(derivada2);
		Matcher matcher2 = pattern2.matcher(string);

		String derivada1 = "d\\[[A-Z][0-9]\\]\\([a-z],[a-z]\\)/dz";
		Pattern pattern1 = Pattern.compile(derivada1);
		Matcher matcher1 = pattern1.matcher(string);

		String funcao = "\\[[A-Z][0-9]\\]\\([a-z],[a-z]\\)";
		Pattern patternFuncao = Pattern.compile(funcao);
		Matcher matcherFucao = patternFuncao.matcher(string);

		String derivadat = "d\\[[A-Z][0-9]\\]\\([a-z],[a-z]\\)/dt";
		Pattern patternderivadat = Pattern.compile(derivadat);
		Matcher matcherDerivadat = patternderivadat.matcher(string);

		String derivadaAtrasada = new String(string);

		String derivadaAdiantada = new String(string);

		while (matcherDerivadat.find()) {

			String novaExpressao = "d" + matcherDerivadat.group().toString().substring(2, 3) + "[i]t";
			string = string.replace(matcherDerivadat.group().toString(), novaExpressao);
			derivadaAtrasada = derivadaAtrasada.replace(matcherDerivadat.group().toString(), novaExpressao);
			derivadaAdiantada = derivadaAdiantada.replace(matcherDerivadat.group().toString(), novaExpressao);
			if (!variaveisI.contains(matcherDerivadat.group().toString())) {
				variaveisI.add(novaExpressao);
			}
		}

		while (matcher2.find()) {
			if (!variaveisI.contains(matcher2.group().toString().substring(3, 4) + "[i]")) {
				variaveisI.add(matcher2.group().toString().substring(3, 4) + "[i]");

			}

			String novaExpressao1 = "(2*" + matcher2.group().toString().substring(3, 4) + "[i]-5*"
					+ matcher2.group().toString().substring(3, 4) + "[i+1]+4*"
					+ matcher2.group().toString().substring(3, 4) + "[i+2]-"
					+ matcher2.group().toString().substring(3, 4) + "[i+3])/(H^3)";

			// inicio = matcher2.group().toString().substring(3, 4)+"0";

			derivadaAdiantada = derivadaAdiantada.replace(matcher2.group().toString(), novaExpressao1);

			// fim = matcher2.group().toString().substring(3,
			// 4)+"[i]-"+matcher2.group().toString().substring(3, 4)+"[i-1]";

			String novaExpressao2 = "(2*" + matcher2.group().toString().substring(3, 4) + "[i]-5*"
					+ matcher2.group().toString().substring(3, 4) + "[i-1]+4*"
					+ matcher2.group().toString().substring(3, 4) + "[i-2]-"
					+ matcher2.group().toString().substring(3, 4) + "[i-3])/(H^3)";

			derivadaAtrasada = derivadaAtrasada.replace(matcher2.group().toString(), novaExpressao2);

			String novaExpressao = "(" + matcher2.group().toString().substring(3, 4) + "[i+1]+"
					+ matcher2.group().toString().substring(3, 4) + "[i-1]-2*"
					+ matcher2.group().toString().substring(3, 4) + "[i])/(H^2)";
			string = string.replace(matcher2.group().toString(), novaExpressao);

		}

		while (matcher1.find()) {
			if (!variaveisI.contains(matcher1.group().toString().substring(2, 3) + "[i]")) {
				variaveisI.add(matcher1.group().toString().substring(2, 3) + "[i]");

			}
			String novaExpressao = "(" + matcher1.group().toString().substring(2, 3) + "[i+1]-"
					+ matcher1.group().toString().substring(2, 3) + "[i-1])/(2*H)";
			/*
			 * "(" + matcher1.group().toString().substring(2, 3) + "[i]-" +
			 * matcher1.group().toString().substring(2, 3) + "[i-1])/(H)";
			 */
			string = string.replace(matcher1.group().toString(), novaExpressao);

			String novaExpressaoAdiantada = "(" + matcher1.group().toString().substring(2, 3) + "[i+1]-"
					+ matcher1.group().toString().substring(2, 3) + "[i])/(H)";

			String novaExpressaoAtrasada = "(" + matcher1.group().toString().substring(2, 3) + "[i]-"
					+ matcher1.group().toString().substring(2, 3) + "[i-1])/(H)";

			derivadaAtrasada = derivadaAtrasada.replace(matcher1.group().toString(), novaExpressaoAtrasada);
			derivadaAdiantada = derivadaAdiantada.replace(matcher1.group().toString(), novaExpressaoAdiantada);

		}

		while (matcherFucao.find()) {
			if (!variaveisI.contains(matcherFucao.group().toString().substring(1, 2) + "[i]")) {
				variaveisI.add(matcherFucao.group().toString().substring(1, 2) + "[i]");
			}
			String novaExpressao = matcherFucao.group().toString().substring(1, 2) + "[i]";
			string = string.replace(matcherFucao.group().toString(), novaExpressao);
			derivadaAtrasada = derivadaAtrasada.replace(matcherFucao.group().toString(), novaExpressao);
			derivadaAdiantada = derivadaAdiantada.replace(matcherFucao.group().toString(), novaExpressao);

		}

		for (int i = 0; i <= nParticoes; i++) {
			if (i == 0) {

				formulas.add(inicio);
			} else if (i == nParticoes) {

				formulas.add(formulas.get(nParticoes - 1));
			} else {
				formulas.add(string.replace("[i+1]", String.valueOf(i + 1)).replace("[i-1]", String.valueOf(i - 1))
						.replace("[i]", String.valueOf(i)).replace("H", String.valueOf(h)));
			}

			for (String variavel : variaveisI) {

				variaveis.add(variavel.replace("[i]", String.valueOf(i)));

			}

		}

		return formulas;

	}

}
