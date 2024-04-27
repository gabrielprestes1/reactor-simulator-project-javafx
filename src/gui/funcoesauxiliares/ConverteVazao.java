package gui.funcoesauxiliares;

import java.util.ArrayList;
import java.util.List;

public class ConverteVazao {

		public List<Double> converteVazao(List<Double> vazaoInicial, Integer uInicial, Integer uFinal, Integer coluna){
			
			List<Double> vazaoFinal = new ArrayList<Double>();
			
			List<Double> massaMolar = new ArrayList<Double>();
			
			massaMolar.add(0.016040); // kg/mol de CH4
			massaMolar.add(0.044010); // kg/mol de CO2
			massaMolar.add(0.028010); // kg/mol de CO
			massaMolar.add(0.002016); // kg/mol de H2
			
			if(uInicial == 0) {
				if(uFinal == 1) {
					
				}else if(uFinal == 2) {
					
				}
			}else if(uInicial == 1) {
				
			}else if(uInicial == 2) {
				
			}else if(uInicial == 3) {
				
			}else if(uInicial == 4) {
				
			}else if(uInicial == 5) {
				
			}
			
			
			return vazaoFinal;
			
		}
	
}
