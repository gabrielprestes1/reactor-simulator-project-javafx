package gui.funcoesauxiliares;

public class ConversionModule {



	// private Double R1 = 8.3144621; // J/molK
	// private Double R2 = 0.0820574587; // L atm // mol K
	// private Double R3 = 0.0000820574587; // m3 atm / mol K

	// all flow must be converted to m3/s
	// all temperatures must be converted to Kelvin
	// all sizes must be converted to meter
	// all height must be converted to kg

	public Double flowConverter(Double flowIn, Integer subChoice) {

		Double flowOut = flowIn;
		// Temp must be in Kelvin
		// m3/h -> m3/s - 0
		// m3/min -> m3/s - 1
		// m3/s -> m3/s - 2
		// mL/h -> m3/s - 3
		// ml/min -> m3/s - 4
		// ml/s -> m3/s - 5
		// Adotando gás ideal pV = nRT

		if (subChoice == 0) {
			flowOut = flowIn;
		} else if (subChoice == 1) {
			flowOut = flowIn / 60.0;
		} else if (subChoice == 2) {
			flowOut = flowIn / 3600.0;
		} else if (subChoice == 3) {
			flowOut = flowIn / 3600000000.0;
		} else if (subChoice == 4) {
			flowOut = flowIn / 60000000.0;
		} else if (subChoice == 5) {
			flowOut = flowIn / 1000000.0;
		}

		return flowOut;
	}

	public Double temperatureConverter(Double inletTemp, Integer subChoice) {

		Double outTemp = inletTemp;

		// C to K -> 1
		// F to K -> 2

		if (subChoice == 1) {
			outTemp = inletTemp + 273.15;
		} else if (subChoice == 2) {
			outTemp = (inletTemp - 32.0) * (5.0 / 9.0) + 273.15;
		}

		return outTemp;

	}

	public Double lenghtConversion(Double lenghtIn, Integer subChoice) {

		Double lenghtOut = lenghtIn;

		// cm to m -> 1
		// mm to m -> 2

		if (subChoice == 1) {
			lenghtOut = lenghtIn / 100.0;
		} else if (subChoice == 2) {
			lenghtOut = lenghtIn / 1000.0;
		}

		return lenghtOut;
	}

}
