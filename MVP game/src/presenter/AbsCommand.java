package presenter;

import model.Model;

public abstract class AbsCommand implements Command {
	
	
	protected Model model;
	Controller controller;
	
	
	
	public AbsCommand(Model model, Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
	}



	@Override
	public abstract void doCommand(String[] args);

}
