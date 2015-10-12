package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import presenter.Command;

public class MazeBasicWindow extends BasicWindow implements View{

	Timer timer;
	TimerTask task;
	Button startButton; 
	Button stopButton; 
	HashMap<String, Command> viewCommandMap;
	private String cliMenu;
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out=new PrintWriter(System.out);
	private CLI cli;
	Maze3d mazeObject;
	int userCommand=0;
	int[][] currentFloorCrossedArr;
	int currentFloor;
	MazeDisplayer mazeDisplayerCanvas;
	boolean started =false;
	boolean won=false;
	String game; 
	WelcomeDisplayer welcomeDisplayerCanvas;
	boolean isSolving = true;
	
	
	public MazeBasicWindow(String title, int width, int height,HashMap<String, Command> viewCommandMap) {
		super(title, width, height);
		this.viewCommandMap = viewCommandMap;
	}

	@Override
	void initWidgets() {

		
		
		
		
		GridLayout grid = new GridLayout(2,false); 
		shell.setLayout(grid);
        //shell.setMinimumSize(menuBar.get, height);
		
        /* Main Bar Menu Items: File, Maze */
		Menu menuBar = new Menu(shell, SWT.BAR);
		MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        MenuItem cascadeMazeMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeMazeMenu.setText("Maze");
        
        /* File Menu Items: Open Properties, Exit */  
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);
        MenuItem openProperties = new MenuItem(fileMenu, SWT.PUSH);
        openProperties.setText("Open Properties");
        MenuItem GenerateItem = new MenuItem(fileMenu, SWT.PUSH);
        GenerateItem.setText("Generate Maze");
        MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
        exitItem.setText("&Exit");
        shell.setMenuBar(menuBar); // Generate Menu.
        
        TabFolder MazeFolder = new TabFolder(shell, SWT.NULL);

        TabItem playTab = new TabItem(MazeFolder, SWT.NULL);
        playTab.setText("Maze");
        SashForm playForm = new SashForm(MazeFolder, SWT.VERTICAL);
        playTab.setControl(playForm);
        
        Button generateNewMazeButton = new Button(playForm, SWT.PUSH);
        generateNewMazeButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
        generateNewMazeButton.setText("Generate");
        
        Button solveButton = new Button(playForm, SWT.PUSH);
        solveButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
        solveButton.setText("Solve");
        
        Button hintButton = new Button(playForm, SWT.PUSH);
        hintButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
        hintButton.setText("Hint");

        /* Options form */ 
        TabItem optionsTab = new TabItem(MazeFolder, SWT.NULL);
        optionsTab.setText("Options");
        SashForm optionsForm = new SashForm(MazeFolder, SWT.VERTICAL);
        optionsTab.setControl(optionsForm);
        optionsForm.setSize(10, 100);

        startButton=new Button(optionsForm, SWT.PUSH);
        stopButton=new Button(optionsForm, SWT.PUSH);
        changeButton(startButton, true, "Start");
        stopButton.setText("Stop");
        
        Button testButton = new Button(optionsForm, SWT.PUSH);
        testButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
        testButton.setText("Hint");
        
        
        
        welcomeDisplayerCanvas = new  WelcomeDisplayer(shell, SWT.BORDER);
		welcomeDisplayerCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
        
        
        
		/* What happens when a user clicks "File" > "Open Properties" */  
		openProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				org.eclipse.swt.widgets.FileDialog fileDialog = new org.eclipse.swt.widgets.FileDialog(shell, SWT.OPEN);
				fileDialog.setText("Open Properties");
				fileDialog.setFilterPath("C:/");
				String[] fileTypes = {"*.xml"}; 
				fileDialog.setFilterExtensions(fileTypes);
				String selectedFile = fileDialog.open();
				String[] args = {selectedFile};
				setUserCommand(12);
				notifyObservers(args);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/* What happens when a user clicks "File" > "Generate Maze" */ 
		GenerateItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//TODO 
				Dialog dialog = new Dialog(shell, SWT.CENTER) {
					Text floors =new Text(shell, SWT.BORDER);
					Text lines =new Text(shell, SWT.BORDER);
					Text columns =new Text(shell, SWT.BORDER);
					
				};
								
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		/* What happens when a user clicks "File" > "Exit" */ 
		exitItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				display.dispose(); // dispose OS components
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		/* What happens when a user clicks "[Start]". */ 
		startButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				started=true;
				game = "mazeGame";
				if (game.equals("mazeGame"))
				{
					if(mazeDisplayerCanvas==null)
					{
						initMazeDisplayerAndMazeCurrentFloorCrossedArr();	
						//initKeyListeners();
					}
					else
					{
						
						mazeDisplayerCanvas.setFocus();
					}
						
					
					/* UI Grid */ 

				}
				else
				{
					initGame(); 
				}

				startButton.setEnabled(false);
				stopButton.setEnabled(true);
//				/shell.layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		/* What happens when a user clicks "[Stop]". */
		stopButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				started=false;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		
		
		/* What happens when a user clicks "[Solve]". */
		solveButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplayerCanvas!=null)
				{
					isSolving=true;
					//Updating the model about current place in maze
					setUserCommand(13);
					String[] params = {"test",currentFloor+"",mazeDisplayerCanvas.getCharacterX()+"",mazeDisplayerCanvas.getCharacterY()+""};
					notifyObservers(params);
					
					//Position positionToStart = new Position(currentFloor, mazeDisplayerCanvas.getCharacterX(), mazeDisplayerCanvas.getCharacterY());
					//String[] mazeNameArr = {"test","A*",currentFloor+"",mazeDisplayerCanvas.getCharacterX()+"",mazeDisplayerCanvas.getCharacterY()+""};
					//viewCommandMap.get("solve").doCommand(mazeNameArr);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		
		
		/* What happens when a user clicks "[Hint]". */
		hintButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplayerCanvas!=null)
				{
					isSolving=false;
					//Updating the model about current place in maze
					setUserCommand(13);
					String[] params = {"test",currentFloor+"",mazeDisplayerCanvas.getCharacterX()+"",mazeDisplayerCanvas.getCharacterY()+""};
					notifyObservers(params);
					
					//Position positionToStart = new Position(currentFloor, mazeDisplayerCanvas.getCharacterX(), mazeDisplayerCanvas.getCharacterY());
					//String[] mazeNameArr = {"test","A*",currentFloor+"",mazeDisplayerCanvas.getCharacterX()+"",mazeDisplayerCanvas.getCharacterY()+""};
					//viewCommandMap.get("solve").doCommand(mazeNameArr);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		
	}


	public Button getStartButton() {
		return startButton;
	}

	public void setStartButton(Button startButton) {
		this.startButton = startButton;
		
	}

	public boolean changeButton(Button b, boolean enabled, String text) {
		if(b == startButton) {
			/* Buttons : Start, Stop */ 
			startButton.setText(text);
			startButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
			startButton.setEnabled(enabled);
			stopButton.setEnabled(!(enabled));
			stopButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));

		}
		else if (b == stopButton)
		{
			stopButton.setText(text);
			stopButton.setLayoutData(new GridData(SWT.None, SWT.None, false, false, 1, 1));
			stopButton.setEnabled(enabled);
			startButton.setEnabled(!(enabled));
			startButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		}
		else
		{
			return false; 
		}
		return true; 
	}
	
	public Button getStopButton() {
		return stopButton;
	}

	public void setStopButton(Button stopButton) {
		this.stopButton = stopButton;
	}

	public void initKeyListeners()
	{
		mazeDisplayerCanvas.addKeyListener(new KeyListener() 
		{
            boolean pageDownKey = false;
            boolean PageUp = false;
            
			@Override
			public void keyReleased(KeyEvent e) 
			{
				if(started == true)
				{
					switch (e.keyCode) 
					{
						case SWT.ARROW_DOWN:	mazeDisplayerCanvas.moveBackward();
							break;
						case SWT.ARROW_UP:		mazeDisplayerCanvas.moveForward();
							break;
						case SWT.ARROW_LEFT:	mazeDisplayerCanvas.moveLeft();
							break;
						case SWT.ARROW_RIGHT:	mazeDisplayerCanvas.moveRight();
							break;
						case SWT.PAGE_DOWN:
							if(pageDownKey)
							{
								mazeDisplayerCanvas.moveDown();
								pageDownKey=false;
							}
							break;
						case SWT.PAGE_UP:
							if(PageUp)
							{
								mazeDisplayerCanvas.moveUp();
								PageUp=false;
							}
							break;
		              }
				}
		        else
                {    
		        	switch (e.keyCode) 
                    {
                         case SWT.PAGE_DOWN:	pageDownKey=false;
                        	 break;
                         case SWT.PAGE_UP:		PageUp=false;
                        	 break;
                    }
                }
			}
		
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(started)
				{
					switch (e.keyCode) 
					{
                        case SWT.PAGE_DOWN:	pageDownKey=getFloorUpCrossedArr("DOWN");
                        	break;
                        case SWT.PAGE_UP:	PageUp=getFloorUpCrossedArr("UP");
                        	break;
					}
				}
			}
		});
	}
	
	private void initMazeDisplayerAndMazeCurrentFloorCrossedArr() 
	{
		String[] mazeArgs =  {"test","default","2","10","18"};
		this.viewCommandMap.get("generate 3d maze").doCommand(mazeArgs);
		
		
		

	}
	
	private void initGame()
	{
		welcomeDisplayerCanvas = new  WelcomeDisplayer(shell, SWT.BORDER);
		welcomeDisplayerCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));
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
		String[] mazeName={name,};
		out.println("View: Maze "+name+" is Ready, you can take it!");
		out.flush();
		
		this.viewCommandMap.get("display").doCommand(mazeName);
		//this.notifyObservers();
	}
	
	@Override
	public void printMazeToUser(Maze3d mazeWithName,String name) {
		mazeDisplayerCanvas=new Maze3dDisplayer(shell, SWT.BORDER);
        mazeDisplayerCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2)); 
       
        //maze.forceFocus();
        //maze.setLayoutData(new GridData(horizontalAlignment, verticalAlignment, grabExcessHorizontalSpace, grabExcessVerticalSpace, horizontalSpan, verticalSpan));
		((Maze3dDisplayer)mazeDisplayerCanvas).setMazeBasicWindow(this);
		initKeyListeners();
		mazeObject = mazeWithName;
		//System.out.println("mazeWithName: "+mazeWithName);
		//crossedArr = mazeData.getCrossSectionByX(mazeData.getStartPosition().getXPosition());
		String[] args={"x",mazeObject.getStartPosition().getXPosition()+"","for",name};
		viewCommandMap.get("display cross section by").doCommand(args);
		
		
		
		this.mazeDisplayerCanvas.setExitX(mazeWithName.getGoalPosition().getYposition());
		this.mazeDisplayerCanvas.setExitY(mazeWithName.getGoalPosition().getZposition());
		this.mazeDisplayerCanvas.setExitFloor(mazeWithName.getGoalPosition().getXPosition()); 
		
		mazeDisplayerCanvas.setCharacterPosition(mazeWithName.getStartPosition().getYposition(), mazeWithName.getStartPosition().getZposition());
	}
	
	
	public boolean getFloorUpCrossedArr(String direction) 
	{
		if(direction.toUpperCase().equals("UP"))
		{
			if(currentFloor>=0&&currentFloor<(this.mazeObject.getMaze().length-1))
			{
				System.out.println("Prepering to go up from: "+currentFloor+" to: "+(currentFloor+1));
				int[][] crossedArrToCheck = this.mazeObject.getCrossSectionByX(currentFloor+1);
				System.out.println("Next floor:");
				printArr(crossedArrToCheck);
				System.out.println("Charecter position: "+mazeDisplayerCanvas.getCharacterX()+","+mazeDisplayerCanvas.getCharacterY());
				System.out.println("The cell is: "+crossedArrToCheck[mazeDisplayerCanvas.getCharacterX()][mazeDisplayerCanvas.getCharacterY()]);
				//this.crossedArr = this.mazeData.getCrossSectionByX(currentFloor+1);
				if(crossedArrToCheck[mazeDisplayerCanvas.getCharacterX()][mazeDisplayerCanvas.getCharacterY()]==0)
				{
					this.currentFloorCrossedArr = crossedArrToCheck;
					currentFloor++;
					((Maze3dDisplayer) mazeDisplayerCanvas).setCurrentFloor(currentFloor);
					mazeDisplayerCanvas.mazeData = currentFloorCrossedArr;
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
		
		else if(direction.toUpperCase().equals("DOWN"))
		{
			if(currentFloor>=1&&currentFloor<=(this.mazeObject.getMaze().length-1))
			{
				System.out.println("Prepering to go down from: "+currentFloor+" to: "+(currentFloor-1));
				int[][] crossedArrToCheck = this.mazeObject.getCrossSectionByX(currentFloor-1);
				System.out.println("Previous floor:");
				printArr(crossedArrToCheck);
				System.out.println("Charecter position: "+mazeDisplayerCanvas.getCharacterX()+","+mazeDisplayerCanvas.getCharacterY());
				System.out.println("The cell is: "+crossedArrToCheck[mazeDisplayerCanvas.getCharacterX()][mazeDisplayerCanvas.getCharacterY()]);
				if(crossedArrToCheck[mazeDisplayerCanvas.getCharacterX()][mazeDisplayerCanvas.getCharacterY()]==0)
				{
					this.currentFloorCrossedArr =crossedArrToCheck;
					currentFloor--;
					((Maze3dDisplayer) mazeDisplayerCanvas).setCurrentFloor(currentFloor);
					mazeDisplayerCanvas.mazeData = currentFloorCrossedArr;
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
		
		
		
		//crossedArr = mazeData.getCrossSectionByX(mazeData.getStartPosition().getXPosition());

				mazeDisplayerCanvas.mazeData = crossedArr;
				
				this.currentFloor = mazeObject.getStartPosition().getXPosition();
				//out.println("Maze: "+name+"\n"+mazeWithName.toString());
				//out.flush();
				//welcomeDisplayer.dispose();
				//System.out.println("Test");
				//maze.set
				mazeDisplayerCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2)); 
				//maze.setBounds(welcomeDisplayer.getBounds());
				welcomeDisplayerCanvas.dispose();
				//maze.handle;
				shell.layout();
				mazeDisplayerCanvas.forceFocus();
				
				//shell.pack();
		
		
		
		System.out.println("Crossed Arr!!!");
		this.currentFloorCrossedArr = crossedArr;
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
		
		String[] mazeNameArr = {"test","A*"};
		viewCommandMap.get("display solution").doCommand(mazeNameArr);
	}
	
	@Override
	public void printSolutionToUser(String mazeName,Solution<Position> solution) {
		out.println("Solution of: "+mazeName+"\n");
		out.flush();
		for (State<Position> p: solution.getSolution()){
			out.println(p.getCameFromAction() + " To: "+p.toString());
			out.flush();
			}
			
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ArrayList<State<Position>> solutionToDisplay = new ArrayList<State<Position>>();
				if(isSolving)
				{
					solutionToDisplay=solution.getSolution();
				}

				else
					solutionToDisplay.add(solution.getSolution().get(0));
				
				
				
				
				
				for (State<Position> p: solutionToDisplay){
					
					if(shell.isDisposed())
					{
						Thread.currentThread().interrupt();
					}
					else if(p.getCameFromAction().equals("Down"))
					{
						getFloorUpCrossedArr("DOWN");
						mazeDisplayerCanvas.moveDown();
					}
					else if(p.getCameFromAction().equals("Up"))
					{
						getFloorUpCrossedArr("UP");
						mazeDisplayerCanvas.moveUp();
					}	
					else if(p.getCameFromAction().equals("Backward"))
					{
						mazeDisplayerCanvas.moveForward();
					}
					else if(p.getCameFromAction().equals("Forward"))
					{
						mazeDisplayerCanvas.moveBackward();
					}
					else if(p.getCameFromAction().equals("Left"))
					{
						mazeDisplayerCanvas.moveLeft();
					}
					else if(p.getCameFromAction().equals("Right"))
					{
						mazeDisplayerCanvas.moveRight();
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					}
				
			}
		});
		
		t.start();
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
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	MessageBox messageBox = new MessageBox(new Shell(display),SWT.ICON_INFORMATION|SWT.OK);
				messageBox.setMessage(s);
				messageBox.setText("Notification");
				messageBox.open();
				if(mazeDisplayerCanvas!=null)
				{
					mazeDisplayerCanvas.setFocus();
				}
				
		    }
		});
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
	 * @return the won
	 */
	public boolean isWon() {
		return won;
	}

	/**
	 * @param won the won to set
	 */
	public void setWon(boolean won) {this.won = won;}

	@Override
	public void start() {this.run();}
	
	
	@Override
	public void exit(){
		if (started)
		{
			if (task != null && timer != null)
			{
				task.cancel();
				timer.cancel();
			}
						
		}
		
		display.dispose(); // dispose OS components
		setUserCommand(11);
		String[] args= {"Exit"};
		System.out.println("Exiting now");
		notifyObservers(args);
		
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
}
