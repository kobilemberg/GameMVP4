package boot; 

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import presenter.Properties;
import view.MazeBasicWindow;
import view.MyView;


public class Run {

	public static void main(String[] args) 
	{
		
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream("External files/properties.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File External files/properties.xml not found");
		}
		Properties properties=(Properties)decoder.readObject();
		System.out.println(properties);
		
		MyView view = new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		
			
			//view = new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out),);
		
		
		
		//MyView view = new MyView(System.in, System.out);
		MyModel model = new MyModel(properties);
	
	//	MyController controller = new MyController(view, model);
		Presenter presenter = new Presenter(view, model);
		view.addObserver(presenter);
		model.addObserver(presenter);
		//model.setController(controller);
		//view.setController(controller);
		
		if(properties.getUI().equals("GUI"))
		{
			MazeBasicWindow win=new MazeBasicWindow("3D Maze Game", 500, 300);
			view.setMazeWindow(win);
			view.start("GUI");
		}
		else
			view.start("CLI");

	}

}
