package gui.funcoesauxiliares;


import gui.PFRFormController;
import model.entities.Reactor;

public class KineticsBuilder {

	public String kineticsBuilder(Reactor reactor) {

		PFRFormController controller = new PFRFormController();

		String equations = "";
		equations = BuilderIrreversibleAndIsothermal(reactor, controller);

		return equations;

	}

	private String BuilderIrreversibleAndIsothermal(Reactor reactor, PFRFormController controller) {

		String kinetics = "";

        String reaction = controller.getKinetics();
		Double k0 = reactor.getK0();

		kinetics = k0 + "*" + reaction;

		return kinetics;
	}





}
