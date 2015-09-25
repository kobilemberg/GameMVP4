package presenter;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> Controller </h1>
 * Controller interface represent a generally controller layer of MVC
 */

import java.util.HashMap;


import algorithms.mazeGenerators.Maze3d;

public interface Controller {
	HashMap<String, Maze3d> maze3dMap = new HashMap<String, Maze3d>();

	/**
	 * The method will update the view layer that maze is ready
	 *@param name String represent the name of the maze          
	 */
	public void mazeIsReady(String name);
	
	/**
	 * The method will update the view layer that solution for maze is ready
	 *@param mazeName String represent the name of the maze          
	 */
	public void solutionIsReady(String mazeName);
	
	/**
	 * The method will notice the view layer about error messages
	 *@param s String represent the messege to view         
	 */
	public void errorNoticeToViewr(String s);



}
