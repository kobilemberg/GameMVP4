package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

import presenter.Command;
import presenter.Controller;

public class MazeBasicWindow extends BasicWindow{

	Timer timer;
	TimerTask task;
	
	HashMap<String, Command> viewCommandMap;
	private String cliMenu;
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out=new PrintWriter(System.out);
	int userCommand=0;
	private CLI cli;
	private Controller controller;
	Maze3d mazeData;
	int[][] crossedArr;
	MazeDisplayer maze;
	boolean started =false;
	int currentFloor;
	
	
	public MazeBasicWindow(String title, int width, int height,HashMap<String, Command> viewCommandMap) {
		super(title, width, height);
		this.viewCommandMap = viewCommandMap;
	}

	private void randomWalk(MazeDisplayer maze){
	//	Random r=new Random();
	//	boolean b1,b2;
	//	b1=r.nextBoolean();
	//	b2=r.nextBoolean();
	//	if(b1&&b2)
	//		maze.moveUp();
	//	if(b1&&!b2)
	//		maze.moveDown();
	//	if(!b1&&b2)
	//		maze.moveRight();
	//	if(!b1&&!b2)
	//		maze.moveLeft();
		
	//	maze.redraw();
		
		
		
	}
	
	@Override
	void initWidgets() {
		
		InitalMaze();
		shell.setLayout(new GridLayout(2,false));
		initialKeyListeners();
		Button startButton=new Button(shell, SWT.PUSH);
		startButton.setText("Start");
		startButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
		Button stopButton=new Button(shell, SWT.PUSH);
		stopButton.setText("Stop");
		stopButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
		stopButton.setEnabled(false);
		startButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				timer=new Timer();
				task=new TimerTask() {
					@Override
					public void run() {
						display.syncExec(new Runnable() {
							@Override
							public void run() {
								//randomWalk(maze);
								started=true;
								
							}
						});
					}
				};				
				timer.scheduleAtFixedRate(task, 0, 100);				
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		stopButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				task.cancel();
				timer.cancel();
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				started=false;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
	}
	
	public void initialKeyListeners()

	{
		maze.addKeyListener(new KeyListener() 
		{
			boolean UpPresses=false;
            boolean DownPresses=false;
            boolean RightPresses=false;
            boolean LeftPresses=false;
            boolean PageDown = false;
            boolean PageUp = false;

            
			@Override
			public void keyReleased(KeyEvent e) 
			{
					if(started == true)
					{
						switch (e.keyCode) 
						{
			                  case SWT.ARROW_DOWN:
			                  	maze.moveBackward();
			                      DownPresses=false;
			                       break;
			                  case SWT.ARROW_UP:
			                	  maze.moveForward();
			                  //	setUserCommand(SWT.ARROW_UP);
			                      UpPresses=false;
			                      break;
			                   case SWT.ARROW_LEFT:
			                	   maze.moveLeft();
			                  	 //setUserCommand(SWT.ARROW_LEFT);
			                      LeftPresses=false;
			                      break;
			                  case SWT.ARROW_RIGHT:
			                	  maze.moveRight();
			                  //	setUserCommand(SWT.ARROW_RIGHT);
			                      RightPresses=false;
			                      break;
			                      
			                  case SWT.PAGE_DOWN:
			                	 if(PageDown)
			                	 {
			                		 maze.moveDown();
					                  //	setUserCommand(SWT.ARROW_RIGHT);
					                	  PageDown=false;
			                	 }
			                		  
			                      break;
			                      
			                  case SWT.PAGE_UP:
			                	  if(PageUp)
			                	  {
			                		  maze.moveUp();
					                  //	setUserCommand(SWT.ARROW_RIGHT);
					                	  PageUp=false;
			                	  }
			                		  
			                      break;
			              }
				     // setChanged();
					//  notifyObservers();
					}
			        else
	                {    
			        	switch (e.keyCode) 
	                    {
	                         case SWT.ARROW_DOWN:
	                             DownPresses=false;
	                             break;
	                         case SWT.ARROW_UP:
	                              UpPresses=false;
	                             break;
	                         case SWT.ARROW_LEFT:
	                             LeftPresses=false;
	                              break;
	                         case SWT.ARROW_RIGHT:
	                             RightPresses=false;
	                              break;
	                         case SWT.PAGE_DOWN:
			                	  PageDown=false;
			                      break;
			                  case SWT.PAGE_UP:
			                	  PageUp=false;
			                      break;
	                    }
	                 }
				
			}
		
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(started)
				{
					System.out.println("keyListener");
					switch (e.keyCode) 
	                   {
	                       case SWT.ARROW_DOWN:
	                           DownPresses=true;
	                           break;
	                        case SWT.ARROW_UP:
	                           UpPresses=true;
	                           break;
	                       case SWT.ARROW_LEFT:
	                           LeftPresses=true;
	                            break;
	                       case SWT.ARROW_RIGHT:
	                           RightPresses=true;
	                           break;
	                           
	                           
	                       case SWT.PAGE_DOWN:
			                	  PageDown=getFloorUpCrossedArr("DOWN");
			                      break;
			                      
			                  case SWT.PAGE_UP:
			                	  PageUp=getFloorUpCrossedArr("UP");
			                      break;
	                   }
				
				}
			}
			
			
		
			
		});
		

	}
	
	private void InitalMaze() 
	{
		maze=new Maze3dDisplayer(shell, SWT.BORDER);
		
		String[] mazeArgs =  {"test","default","2","10","18"};
		this.viewCommandMap.get("generate 3d maze").doCommand(mazeArgs);
		
	}

//	public static void main(String[] args) {
		//MazeBasicWindow win=new MazeBasicWindow("maze example", 500, 300);
		//win.run();
	//}



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
		String[] mazeName={"test"};
		out.println("View: Maze "+name+" is Ready, you can take it!");
		out.flush();
		this.viewCommandMap.get("display").doCommand(mazeName);
	}
	
	@Override
	public void printMazeToUser(Maze3d mazeWithName,String name) {
		this.mazeData = mazeWithName;
		this.crossedArr = this.mazeData.getCrossSectionByX(mazeData.getStartPosition().getXPosition());
		maze.mazeData = crossedArr;
		maze.setCharacterPosition(mazeWithName.getStartPosition().getYposition(), mazeWithName.getStartPosition().getZposition());
		this.currentFloor = mazeData.getStartPosition().getXPosition();
		out.println("Maze: "+name+"\n"+mazeWithName.toString());
		out.flush();
	}
	
	
	public boolean getFloorUpCrossedArr(String direction) {
		
		
		if(direction.equals("UP"))
		{
			if(currentFloor>=0&&currentFloor<(this.mazeData.getMaze().length-1))
			{
				System.out.println("Prepering to go up from: "+currentFloor+" to: "+(currentFloor+1));
				int[][] crossedArrToCheck = this.mazeData.getCrossSectionByX(currentFloor+1);
				System.out.println("Next floor:");
				printArr(crossedArrToCheck);
				System.out.println("Charecter position: "+maze.getCharacterX()+","+maze.getCharacterY());
				System.out.println("The cell is: "+crossedArrToCheck[maze.getCharacterX()][maze.getCharacterY()]);
				//this.crossedArr = this.mazeData.getCrossSectionByX(currentFloor+1);
				if(crossedArrToCheck[maze.getCharacterX()][maze.getCharacterY()]==0)
				{
					this.crossedArr = crossedArrToCheck;
					currentFloor++;
					maze.mazeData = crossedArr;
					return true;
				}
				else
				{
					System.out.println("The cell is 1!!!");
					return false;
				}
				
			}
			else
			{
				System.out.println("Illegal UP");
				return false;
			}
		}
		
		else if(direction.equals("DOWN"))
		{
			if(currentFloor>=1&&currentFloor<=(this.mazeData.getMaze().length-1))
			{
				System.out.println("Prepering to go down from: "+currentFloor+" to: "+(currentFloor-1));
				int[][] crossedArrToCheck = this.mazeData.getCrossSectionByX(currentFloor-1);
				System.out.println("Previous floor:");
				printArr(crossedArrToCheck);
				System.out.println("Charecter position: "+maze.getCharacterX()+","+maze.getCharacterY());
				System.out.println("The cell is: "+crossedArrToCheck[maze.getCharacterX()][maze.getCharacterY()]);
				if(crossedArrToCheck[maze.getCharacterX()][maze.getCharacterY()]==0)
				{
					this.crossedArr =crossedArrToCheck;
					currentFloor--;
					maze.mazeData = crossedArr;
					return true;
				}
				else
				{
					System.out.println("The cell is 1");
					return false;
				}	
			}
			else
			{
				System.out.println("Illegal DOWN");
				return false;
			}
		}
		
		System.out.println("No change");
		return false;
	}
	
	
	
	
	
	@Override
	public void printToUserCrossedArray(int[][] crossedArr, String axe, String index, String name) {
		System.out.println("Crossed Arr!!!");
		this.crossedArr = crossedArr;
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
		System.out.println("Setted Command map");
		this.viewCommandMap = viewCommandMap;
		//cli = new CLI(in, out,viewCommandMap);
		//if(cliMenu!=null)
			//cli.cliMenu = cliMenu;
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
	
	
	

	/**
	 * @return the cli
	 */
	public CLI getCli() {
		return cli;
	}
	/**
	 * @param cli the cli to set
	 */
	public void setCli(CLI cli) {
		this.cli = cli;
	}
	/**
	 * @return the viewCommandMap
	 */
	public HashMap<String, Command> getViewCommandMap() {
		return viewCommandMap;
	}

	/**
	 * @return the cliMenu
	 */
	public String getCliMenu() {
		return cliMenu;
	}
	/**
	 * @param cliMenu the cliMenu to set
	 */
	public void setCliMenu(String cliMenu) {
		this.cliMenu = cliMenu;
	}
	/**
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}
	/**
	 * @param in the in to set
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	/**
	 * @return the out
	 */
	public PrintWriter getOut() {
		return out;
	}
	/**
	 * @param out the out to set
	 */
	public void setOut(PrintWriter out) {
		this.out = out;
	}
	/**
	 * @return the controller
	 */
	public Controller getController() {
		return controller;
	}

	@Override
	public void start() {
		this.run();
		
	}
	
	
	
	
	
	

}
