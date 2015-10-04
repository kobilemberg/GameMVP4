package view;

/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyView </h1>
 * MyView class implements View interface, 
 * class goal is to act as MVC View layer and to display applications to end-user.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;
import presenter.Controller;
import java.util.Observable;

public class MyView extends Observable implements View {
	Controller controller;
	CLI cli;
	HashMap<String, Command> viewCommandMap;
	private String cliMenu;
	BufferedReader in;
	PrintWriter out;
	int userCommand=0;
	MazeBasicWindow mazeWindow;
	
	
	/**
	 * @return the mazeWindow
	 */
	public MazeBasicWindow getMazeWindow() {
		return mazeWindow;
	}
	/**
	 * @param mazeWindow the mazeWindow to set
	 */
	public void setMazeWindow(MazeBasicWindow mazeWindow) {
		this.mazeWindow = mazeWindow;
	}
	//Constructors
	/**
	 * Instantiates a new  my own maze3d generator.
	 */
	public MyView()
	{
		super();
	}
	/**
	 * Instantiates a new  my own maze3d generator with given: BufferedReader in, PrintWriter out
	 * @param in BufferedReader represent the input source
	 * @param out PrintWriter represent the output source
	 * @return new MyView as instance with BufferedReader in and PrintWriter out
	 */
	public MyView(BufferedReader in, PrintWriter out)
	{
		super();
		this.in = in;
		this.out=out;
	}
	/**
	 * Instantiates a new  my own maze3d generator with given controller layer as instance
	 * @param controller Controller represent the controller layer as instance
	 * @return new MyView as instance with controller layer
	 */
	public MyView(Controller controller)
	{
		super();
		this.controller = controller;
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintWriter(System.out);
	}
	/**
	 * Instantiates a new  my own maze3d generator with given: controller layer as instance, BufferedReader in and PrintWriter out
	 * @param controller Controller represent the controller layer as instance
	 * @param in BufferedReader represent the input source
	 * @param out PrintWriter represent the output source
	 * @param viewCommandMap HashMap<String, Command> represent a commands to run
	 * @return new MyView as instance with controller layer, BufferedReader in, PrintWriter out and commands 
	 */
	public MyView(Controller controller, BufferedReader in, PrintWriter out,HashMap<String, Command> viewCommandMap)
	{
		super();
		this.controller = controller;
		cli = new CLI(in, out,viewCommandMap);
	}
	/**
	 * Instantiates a new  my own maze3d generator with given controller layer as instance
	 * @param controller Controller represent the controller layer as instance
	 * @param in BufferedReader represent the input source
	 * @param out PrintWriter represent the output source
	 * @return new MyView as instance with controller layer BufferedReader in and PrintWriter out
	 */
	public MyView(Controller controller, BufferedReader in, PrintWriter out)
	{
		super();
		this.controller = controller;
		this.in = in;
		this.out = out;
	}
	/**
	* this method will set the controller to work with
	* @param controller Controller represent the controller layer to work with
	*/
	public void setController(Controller controller){this.controller = controller;}
	
	@Override
	/**
	* this method will start to run the view layer
	*/
	public void start() {cli.start();}
	public void start(String display) {
		if(display.equals("CLI"))
			cli.start();
		else
			mazeWindow.run();
		}

	/**
	* this method will print int[][] array
	*/
	public void printArr(int[][] arr)
	{
		String strOfMazeMatrix="";
		for (int i=0;i<arr.length;i++)
		{
			strOfMazeMatrix+="{";
			for(int j=0;j<arr[0].length;j++){strOfMazeMatrix+=arr[i][j];}
			strOfMazeMatrix+="}\n";
		}
		out.println(strOfMazeMatrix);
		out.flush();
	}

	@Override
	public void printFilesAndDirectories(String filesAndDirOfPath) {out.println(filesAndDirOfPath);}
	
	@Override
	public void tellTheUserMazeIsReady(String name) {
		out.println("View: Maze "+name+" is Ready, you can take it!");
		out.flush();
	}
	
	@Override
	public void printMazeToUser(Maze3d mazeWithName,String name) {
		out.println("Maze: "+name+"\n"+mazeWithName.toString());
		out.flush();
	}
	
	@Override
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name) {
		out.println("Crossed maze: "+name+ " by axe: "+axe+" with index: "+index);
		out.flush();
		printArr(crossedArr);
	}
	
	@Override
	public void tellTheUserTheMazeIsSaved(String mazeName, String filename) {
		out.println("Maze: "+mazeName+ " saved to:"+ filename);
		out.flush();
	}
	
	@Override
	public void tellTheUserTheMazeIsLoaded(String fileName, String mazeName) {
		out.println("Maze: "+mazeName+ " has been loaded from:"+ fileName);
		out.flush();
	}
	
	@Override
	public void tellTheUsersizeOfMazeInRam(String mazeName,Double size) {
		out.println("The size of maze: "+mazeName+" in ram memory is:" +size+"b");
		out.flush();
	}
	
	@Override
	public void tellTheUsersizeOfMazeInFile(String fileName, double sizeOfFile) {
		out.println("The size of file: "+fileName+" is: "+sizeOfFile+"b");	
		out.flush();
	}
	
	@Override
	public void tellTheUserSolutionIsReady(String mazeName) {
		out.println("Solution for "+mazeName+" is Ready, you can take it!");
		out.flush();
	}
	
	@Override
	public void printSolutionToUser(String mazeName,Solution<Position> solution) {
		out.println("Solution of: "+mazeName+"\n");
		out.flush();
		for (State<Position> p: solution.getSolution()){
			out.println(p.getCameFromAction() + " To: "+p.toString());
			out.flush();
			}
	}
	
	@Override
	public void setCommands(HashMap<String, Command> viewCommandMap) 
	{
		this.viewCommandMap = viewCommandMap;
		cli = new CLI(in, out,viewCommandMap);
		if(cliMenu!=null)
			cli.cliMenu = cliMenu;
	}
	
	@Override
	public void setCommandsMenu(String cliMenu) {
		this.cliMenu = cliMenu;
		if(cli!=null){cli.setCLIMenu(cliMenu);}	
	}
	
	@Override
	public void errorNoticeToUser(String s) {
		out.println("Notification:\n"+s);
		out.flush();	
	}
	
	@Override
	public int getUserCommand() {return this.userCommand;}
	
	@Override
	public void setUserCommand(int commandID) 
	{
		this.setChanged();
		this.userCommand = commandID;
	}
	
	@Override
	public void displayData(Object data) {
		out.println(data);
		out.flush();		
	}
}
