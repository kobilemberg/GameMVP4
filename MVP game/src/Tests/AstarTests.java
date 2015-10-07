/**
 * 
 */
package Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algorithms.demo.SearchableMaze3d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.search.AStarCommonSearcher;
import algorithms.search.ManhatenDistance;
import algorithms.search.Maze3dSolution;
import junit.framework.TestCase;

/**
 * @author Kobi
 *
 */
public class AstarTests{
	
	
	SearchableMaze3d noneSearchableMaze;
	SearchableMaze3d oneCellWallSearchableMaze;
	SearchableMaze3d oneCellnotWallSearchableMaze; 
	SearchableMaze3d startEqualsToGoalSearchableMaze; 
	AStarCommonSearcher aStar;
	
	
	@Before
	public void setUp(){
		MyMaze3dGenerator mg = new MyMaze3dGenerator();
	
		Maze3d noneMaze = new Maze3d(1, 1, 1);
		Maze3d oneCellWallMaze = new Maze3d(1,1,1);
		Maze3d oneCellnotWallMaze = new Maze3d(1,1,1);
		
		Maze3d startEqualsToGoal = mg.generate(2, 2, 2);
		startEqualsToGoal.setGoalPosition(startEqualsToGoal.getStartPosition());
		
		
		
		
		
		
		int[][][] oneCellWallMazeArr = {{{1}}};
		int[][][] oneCellNotWallMazeArr = {{{1}}};
		
		oneCellWallMaze.setMaze(oneCellWallMazeArr);
		oneCellnotWallMaze.setMaze(oneCellNotWallMazeArr);
		
		
		
		noneSearchableMaze = new SearchableMaze3d(noneMaze);
		oneCellWallSearchableMaze = new SearchableMaze3d(oneCellWallMaze );
		oneCellnotWallSearchableMaze = new SearchableMaze3d(oneCellnotWallMaze);
		startEqualsToGoalSearchableMaze = new SearchableMaze3d(startEqualsToGoal);

		
		
		aStar = new AStarCommonSearcher(new ManhatenDistance());
		Maze3dSolution solution = new Maze3dSolution();
		aStar.setSolution(solution);
		//solution= (Maze3dSolution)(aStar.search(searchableMaze));
		
		
	}
	
	
	@Test
	public void testNoneMazeSolutionIsNotNull() {
		Assert.assertNotEquals(aStar.search(noneSearchableMaze).toString(),null );
	}
	
	@Test
	public void testNoneMazeSolutionIsEmpty() {
		Assert.assertEquals(aStar.search(noneSearchableMaze).toString(),"Solution:{}");
	}
	
	
	
	
	
	@Test
	public void oneCellWallMazeSolutionIsNotNull() {
		Assert.assertNotEquals(aStar.search(oneCellWallSearchableMaze).toString(), null);
	}
	
	
	@Test
	public void oneCellWallMazeSolutionIsEmpty() {
		Assert.assertEquals(aStar.search(oneCellWallSearchableMaze).toString(), "Solution:{}");
	}
	
	
	
	
	@Test
	public void startEqualsToGoalMazeSolutionIsNotNull() {
		Assert.assertNotEquals(aStar.search(startEqualsToGoalSearchableMaze).toString(), null);
	}
	
	
	@Test
	public void startEqualsToGoalMazeSolutionIsEmpty() {
		Assert.assertEquals(aStar.search(startEqualsToGoalSearchableMaze).toString(), "Solution:{}");
	}
	
}
