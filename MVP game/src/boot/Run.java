package boot; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import model.MyModel;
import presenter.Presenter;
import view.MyView;


public class Run {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		MyView view = new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		//MyView view = new MyView(System.in, System.out);
		MyModel model = new MyModel();
	//	MyController controller = new MyController(view, model);
		Presenter presenter = new Presenter(view, model);
		view.addObserver(presenter);
		model.addObserver(presenter);
		//model.setController(controller);
		//view.setController(controller);
		view.start();

	}

}
