package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas{
	
	// just as a stub...
	int[][] mazeData={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
			{0,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
			{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
			{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
			{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
			{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
			{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,0,1,1},
		};

	
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}

	public void setMazeData(int[][] mazeData){
		this.mazeData=mazeData;
	}
	
	public abstract  void setCharacterPosition(int row,int col);

	public abstract void moveForward();

	public abstract  void moveBackward();

	public abstract  void moveLeft();

	public  abstract void moveRight();
	
	public  abstract void moveDown();
	
	public  abstract void moveUp();
	
	
	/**
	 * @return the characterX
	 */
	public abstract int getCharacterX();
	/**
	 * @param characterX the characterX to set
	 */
	public abstract void setCharacterX(int characterX);
	/**
	 * @return the characterY
	 */
	public abstract int getCharacterY();
	/**
	 * @param characterY the characterY to set
	 */
	public abstract void setCharacterY(int characterY);
	/**
	 * @return the exitX
	 */
	public abstract int getExitX();
	/**
	 * @param exitX the exitX to set
	 */
	public abstract void setExitX(int exitX);
	/**
	 * @return the exitY
	 */
	public abstract  int getExitY();
	/**
	 * @param exitY the exitY to set
	 */
	public abstract void setExitY(int exitY);

}