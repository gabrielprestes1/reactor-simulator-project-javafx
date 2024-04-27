package gui.funcoesauxiliares;


import model.entities.Reactor;

public class KineticsBuilder {

	public String kineticsBuilder(Reactor reactor) {
		String equations = "";
		equations = PLBuilderIrreversible(reactor);
		return equations;

	}

	private String PLBuilderIrreversible(Reactor reactor) {

		String kinetics = "";

		Double K0 = reactor.getK0();
		Double E = reactor.getE();

			kinetics = K0 + "*exp(-" + E +"/ ([T0](t,z)))" + "*[A0](t,z)*[A0](t,z)";

		return kinetics;
	}





}
