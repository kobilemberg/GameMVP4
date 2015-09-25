package view;

/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> View </h1>
 * View interface represent a generally view layer of MVC
 */
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;


public interface View {
	/**
	 * Starting the View layer
	 */
	public void start();

	/**
	 * Displays the directory/file path. 
	 * @param filesAndDirOfPath file location
	 */
	public void printFilesAndDirectories(String filesAndDirOfPath);

	/**
	 * Notify when the maze has finished building. 
	 * @param name Maze name. 
	 */
	public void tellTheUserMazeIsReady(String name);

	/**
	 * Display the maze to the user. 
	 * @param mazeWithName - maze3d maze
	 * @param name - maze name 
	 */
	public void printMazeToUser(Maze3d mazeWithName,String name);
	/**
	 * Get an Axe and an Index, and print the 2d matrix of the axe. 
	 * @param crossedArr - the array to print
	 * @param axe - the axe to print (x/y/z)
	 * @param index - the index to print
	 * @param name - maze name
	 */
	//args[1] = Axe,args[2] = index ,args[3] = Name
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name);
	/**
	 * Notify the user when the maze finishes saving.
	 * @param mazeName - maze name
	 * @param filename - file name
	 */
	//args[1] = name, args[2] = filename
	public void tellTheUserTheMazeIsSaved(String mazeName, String filename);
	/**
	 * Notify the user the maze has finished loading. 
	 * @param fileName - file name
	 * @param mazeName - maze name  
	 */
	public void tellTheUserTheMazeIsLoaded(String fileName, String mazeName);
	
	/**
	 * Receive the size and display it to the user. 
	 * @param mazeName - the name of the maze
	 * @param size - the size of the maze
	 */
	public void tellTheUsersizeOfMazeInRam(String mazeName,Double size);
	/**
	 * Receive the file name and file size, and display them to the user.
	 * @param fileName - file name 
	 * @param sizeOfFile - file size
	 */
	public void tellTheUsersizeOfMazeInFile(String fileName, double sizeOfFile);
	/**
	 * Notify the user when the solution is ready. 
	 * @param mazeName - maze name. 
	 */
	public void tellTheUserSolutionIsReady(String mazeName);
	/**
	 * Receive maze solution and print it to the user. 
	 * @param mazeName - maze name
	 * @param solution - maze solution
	 */
	public void printSolutionToUser(String mazeName,Solution<Position> solution);
	/**
	 * Set the commands hash map and cli.menu
	 * @param viewCommandMap - the command map as created in MyController
	 */
	public void setCommands(HashMap<String, Command> viewCommandMap);
	/**
	 * Set the cliMenu to be the menu itself. 
	 * @param cliMenu - The menu to be put in cliMenu
	 */
	public void setCommandsMenu(String cliMenu);
	/**
	 * Display a generic error message to the user 
	 * @param s - the error to display (String). 
	 */
	public void errorNoticeToUser(String s);

	public int getUserCommand();
	
	public void setUserCommand(int commandID);
	
	public void notifyObservers();
	
	//public void notifyObservers(Object o);

	public void displayData(Object data);
	
}
