package Tests;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import algorithms.demo.SearchableMaze3d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.search.AStarCommonSearcher;
import algorithms.search.ManhatenDistance;
import algorithms.search.Maze3dSolution;
import algorithms.search.Solution;
import junit.framework.TestCase;

/**
 * @author Kobi
 *
 */
public class AstarTests{
	
	

	SearchableMaze3d searchableMazeToTest;
	SearchableMaze3d randomSearchableMazeToTest;
	AStarCommonSearcher aStar;
	
	SearchableMaze3d noneSearchableMaze;
	SearchableMaze3d oneCellWallSearchableMaze;
	SearchableMaze3d oneCellnotWallSearchableMaze; 
	SearchableMaze3d startEqualsToGoalSearchableMaze;
	
	
	@Before
	public void setUp(){
		MyMaze3dGenerator mg = new MyMaze3dGenerator();
		aStar = new AStarCommonSearcher(new ManhatenDistance());
		Maze3dSolution solution = new Maze3dSolution();
		aStar.setSolution(solution);
		
		Maze3d mazeToTest = mg.generate(3, 3, 3);
		Maze3d randomMaze = mg.generate(10, 10,10);
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
		searchableMazeToTest = new SearchableMaze3d(mazeToTest);
		randomSearchableMazeToTest = new SearchableMaze3d(randomMaze);
	}
	
	@Test
	public void testRandomMazeAstarSolutionIsNotNull() {
	//	System.out.println(aStar.search(randomSearchableMazeToTest).toString());
		Assert.assertNotEquals(aStar.search(randomSearchableMazeToTest).getSolution().size(),0 );
	}
	
	@Test
	public void testMazeAstarSolutionIsNotNull() {
		Assert.assertNotEquals(aStar.search(searchableMazeToTest).toString(),"" );
	}
	
	@Test
	public void testNegetiveAstarMazeSolutionAmountOfSteps() {
		aStar.search(searchableMazeToTest);
		Assert.assertNotEquals(aStar.getSolution().getSolution().size(),6);
		Assert.assertNotEquals(aStar.getSolution().getSolution().size(),-1);
	}
	
	@Test
	public void testMazeAstarEvaluatedNodesAmountOfSteps() {

		Assert.assertNotEquals(aStar.getNumberOfNodesEvaluated(),-1);
		Assert.assertNotEquals(aStar.getNumberOfNodesEvaluated(),6);
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
		System.out.println(aStar.search(oneCellWallSearchableMaze));
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
