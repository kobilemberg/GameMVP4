/**
 * 
 */
package Tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//import static junit.framework.Assert.assertTrue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import algorithms.demo.SearchableMaze3d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import junit.framework.TestCase;

/**
 * @author Kobi
 *
 */
public class AstarTests{

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println("Test");
		MyMaze3dGenerator mg  = new MyMaze3dGenerator();
		Maze3d oneOnOne = mg.generate(1, 1, 1);
		Maze3d oneOnTwo = mg.generate(1, 1, 2);
		Maze3d emptyMaze = new Maze3d(2,2, 2);
		Maze3d nollMaze = new Maze3d(0,0,0);
		
		SearchableMaze3d realMazeToCheck = new SearchableMaze3d(mg.generate(3, 3, 3));
		SearchableMaze3d oneOnOneToCheck = new SearchableMaze3d(oneOnOne); 
		SearchableMaze3d oneOnTwoToCheck = new SearchableMaze3d(oneOnTwo); 
		SearchableMaze3d emptyMazeToCheck = new SearchableMaze3d(emptyMaze); 
		SearchableMaze3d nollMazeToCheck = new SearchableMaze3d(nollMaze); 
		assert(1==0);
		//assertTrue("aaa",1==0);
		
	//	fail("Not yet implemented");
	}

	
}
