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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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
	Label metaDataLabel;
	Label possibleKeysLabel;
	Label dummyLabel;
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
	boolean isSolving = true;
	String game;
	String mazeObjectName = "MyMaze"; 
	WelcomeDisplayer welcomeDisplayerCanvas;
	
	
	public MazeBasicWindow(String title, int width, int height,HashMap<String, Command> viewCommandMap) {
		super(title, width, height);
		this.viewCommandMap = viewCommandMap;
	}

	@Override
	void initWidgets() {

		/* General Grid Stuff */
		GridLayout grid = new GridLayout(2,false); 
		GridData stretchGridData = new GridData();
		stretchGridData.verticalAlignment = GridData.FILL; 
		stretchGridData.grabExcessVerticalSpace = true;
		GridData gd = new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1);
		gd.heightHint = 100;
		gd.widthHint = 110;
		//gd.heightHint = SWT.DEFAULT;
		
		GridData fitGridData = new GridData(); 
		fitGridData.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING; 
		fitGridData.grabExcessVerticalSpace = false; 
		
		/* General Shell Stuff */ 
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
	        MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
	        exitItem.setText("&Exit");
	        //shell.setMenuBar(menuBar); // Generate Menu.
	        
	        
	        /* Maze Menu Items: Load, Save Maze */  
	        Menu mazeMenu = new Menu(shell, SWT.DROP_DOWN);
	        cascadeMazeMenu.setMenu(mazeMenu);
	        MenuItem loadMaze = new MenuItem(mazeMenu, SWT.PUSH);
	        loadMaze.setText("Load maze from file");
	        MenuItem saveMaze = new MenuItem(mazeMenu, SWT.PUSH);
	        saveMaze.setText("Save Maze to file");
	        shell.setMenuBar(menuBar); // Generate Menu.
	        MenuItem GenerateItem = new MenuItem(mazeMenu, SWT.PUSH);
	        GenerateItem.setText("Generate Maze");


        /* Tab Folder */ 
        TabFolder MazeFolder = new TabFolder(shell, SWT.NULL);
        MazeFolder.setLayoutData(stretchGridData);

        /* "Play" Tab */ 
    	TabItem playTab = new TabItem(MazeFolder, SWT.NULL);
        playTab.setText("Play");
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
	        
	        /* MetaData Label */ 
			metaDataLabel = new Label(playForm, SWT.FILL);
			metaDataLabel.setLayoutData(gd);
			metaDataLabel.setLayoutData(gd);
			setMetaDataLabel(0, 0, 0, 0, 0, 0);
			
			/* Possible Keys Label */ 
			possibleKeysLabel = new Label(playForm, SWT.FILL); 
			possibleKeysLabel.setLayoutData(gd);
			
			/* Dummy Label */ 
			dummyLabel = new Label(playForm, SWT.FILL); 
			
	        int[] play_weights = {1,1,1,2,2,6};
	        playForm.setWeights(play_weights);
	        
        
	        /* "Options" Tab */ 
	        TabItem optionsTab = new TabItem(MazeFolder, SWT.NULL);
	        optionsTab.setText("Options");
	        SashForm optionsForm = new SashForm(MazeFolder, SWT.VERTICAL);
	        optionsForm.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 0, 0));
	        optionsTab.setControl(optionsForm);
	        optionsForm.setSize(10, 100);
	        
		        startButton=new Button(optionsForm, SWT.PUSH);

		        stopButton=new Button(optionsForm, SWT.PUSH);
		        stopButton.setText("Stop");
		        Button b = new Button(optionsForm, SWT.PUSH);

				/* Dummy Label */ 
		        dummyLabel = new Label(optionsForm, SWT.FILL);

		        int[] options_weights = {1,1,1,10000};
		        optionsForm.setWeights(options_weights);

        changeButton(startButton, true, "Start");

        /* Canvas Section */ 
        welcomeDisplayerCanvas = new  WelcomeDisplayer(shell, SWT.BORDER);
		welcomeDisplayerCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,1));

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
				openGenerateWindow();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

		/* What happens when a user clicks "File" > "Exit" */ 
		exitItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION| SWT.YES | SWT.NO);
				messageBox.setMessage("Are you sure you want to exit?");
				messageBox.setText("Exit Confirmation");
				int response = messageBox.open();
				if (response == SWT.YES)
					display.dispose();// dispose OS components
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				}
		});
		
		/* What happens when a user clicks "Maze" > "Save maze to file" */  
		saveMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				org.eclipse.swt.widgets.FileDialog fileDialog = new org.eclipse.swt.widgets.FileDialog(shell, SWT.SAVE);
				if(mazeObject!=null && mazeDisplayerCanvas!=null)
				{
					fileDialog.setText("Save Maze to file");
					fileDialog.setFilterPath("C:/");
					String[] fileTypes = {"*.Game"}; 
					fileDialog.setFilterExtensions(fileTypes);
					fileDialog.setFileName("Game.Game");
					String selectedFile = fileDialog.open();
					String selectedName = fileDialog.getFileName();
					String[] args = {mazeObjectName,fileDialog.getFilterPath()+"\\"+selectedName};
					viewCommandMap.get("save maze").doCommand(args);
				}
				else
					errorNoticeToUser("Play");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		

		/* What happens when a user clicks "Maze" > "Load maze from file" */  
		loadMaze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				org.eclipse.swt.widgets.FileDialog fileLoadDialog = new org.eclipse.swt.widgets.FileDialog(shell, SWT.OPEN);
				//if(mazeObject!=null && mazeDisplayerCanvas!=null)
				{
					fileLoadDialog.setText("Load maze from file");
					fileLoadDialog.setFilterPath("C:\\");
					String[] fileTypes = {"*.Game"}; 
					fileLoadDialog.setFilterExtensions(fileTypes);
					fileLoadDialog.setFileName("Game.Game");
					String selectedFileToLoad = fileLoadDialog.open();
					String selectedName = fileLoadDialog.getFileName();
					String[] args = {fileLoadDialog.getFilterPath()+"\\"+selectedName,mazeObjectName};
					viewCommandMap.get("load maze").doCommand(args);
				if(mazeObject!=null && mazeDisplayerCanvas!=null)
				{
					changeButton(startButton, false, "Start");
					started=true;
				}
				}
				//else
				//	errorNoticeToUser("Play");
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
				startGame();
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


		/* What happens when a user clicks "[Generate]". */ 
		generateNewMazeButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openGenerateWindow();
				
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
					String[] params = {mazeObjectName,currentFloor+"",mazeDisplayerCanvas.getCharacterX()+"",mazeDisplayerCanvas.getCharacterY()+""};
					notifyObservers(params);
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
					String[] params = {mazeObjectName,currentFloor+"",mazeDisplayerCanvas.getCharacterX()+"",mazeDisplayerCanvas.getCharacterY()+""};
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

	public void startGame(){
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
//		/shell.layout();
	}
	public String[] openGenerateWindow(){
		
		/* Generate New Maze open Pop-up window */ 
		Shell child = new Shell(shell);
		child.setLayout(new GridLayout(2,false));
	    child.setSize(148, 188);
	    child.setText("Generate Maze");
	    child.open();
	    
	    /* Generate new maze form: */ 
		    /* Maze Name: */ 
		    Label mazeNameLabel = new Label(child,SWT.FILL);
		    mazeNameLabel.setText("Name:");
		    Text mazeNameInput = new Text(child, SWT.BORDER);
		    mazeNameInput.setText("NewMaze");
		    Label dummy = new Label(child, SWT.FILL); 
		    
		    /* Maze Floors: */ 
		    dummy.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 2, 1));
		    Label xLabel = new Label(child,SWT.FILL);
		    xLabel.setText("Floors:");
		    Text xInput = new Text(child, SWT.BORDER);
		    
		    /* Maze Columns: */ 
		    Label yLabel = new Label(child,SWT.FILL);
		    yLabel.setText("Rows:");
		    Text yInput = new Text(child, SWT.BORDER);
		    
		    /* Maze Columns */ 
		    Label zLabel = new Label(child,SWT.FILL);
		    zLabel.setText("Columns:");
		    Text zInput = new Text(child, SWT.BORDER);

		    Button send = new Button(child, SWT.NULL);
		    send.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 2, 1));
		    send.setText("Generate");
		    child.layout();
		    
		    send.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					mazeObjectName = mazeNameInput.getText(); 
					String[] params = {mazeNameInput.getText(),"default",xInput.getText(),yInput.getText(),zInput.getText()};
					started = true; 
					game = "mazeGame";
					viewCommandMap.get("generate 3d maze").doCommand(params);
					//mazeDisplayerCanvas.setFocus();
					startButton.setEnabled(false);
					stopButton.setEnabled(true);
					child.dispose();
					shell.layout();
					mazeDisplayerCanvas.forceFocus();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		return null; 
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
						case SWT.ARROW_DOWN:	
							mazeDisplayerCanvas.moveBackward();
							break;
						case SWT.ARROW_UP:		
							mazeDisplayerCanvas.moveForward();
							break;
						case SWT.ARROW_LEFT:	
							mazeDisplayerCanvas.moveLeft();
							break;
						case SWT.ARROW_RIGHT:	
							mazeDisplayerCanvas.moveRight();
							break;
						case SWT.PAGE_DOWN:
							if(pageDownKey){
								mazeDisplayerCanvas.moveDown();
								pageDownKey=false;
							}
							break;
						case SWT.PAGE_UP:
							if(PageUp){
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
                         case SWT.PAGE_DOWN:	
                        	 pageDownKey=false;
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
					switch (e.keyCode) 
					{
                        case SWT.PAGE_DOWN:	
                        	pageDownKey=getFloorArr("DOWN");
                        	break;
                        case SWT.PAGE_UP:	
                        	PageUp=getFloorArr("UP");
                        	break;
					}
				}
			}
		});
	}
	
	private void initMazeDisplayerAndMazeCurrentFloorCrossedArr() 
	{
		String[] mazeArgs =  {mazeObjectName,"default","2","10","18"};
		this.viewCommandMap.get("generate 3d maze").doCommand(mazeArgs);
	}
	
	private void initGame()
	{
		welcomeDisplayerCanvas = new  WelcomeDisplayer(shell, SWT.BORDER);
		welcomeDisplayerCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,1));
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
		String[] mazeName={name,};
		out.println("View: Maze "+name+" is Ready, you can take it!");
		out.flush();
		
		this.viewCommandMap.get("display").doCommand(mazeName);
		//this.notifyObservers();
	}
	
	@Override
	public void printMazeToUser(Maze3d mazeWithName,String name) {
		if(mazeDisplayerCanvas==null|| !welcomeDisplayerCanvas.isDisposed())
		{
			mazeDisplayerCanvas=new Maze3dDisplayer(shell, SWT.BORDER);
	        mazeDisplayerCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,1)); 
	        //maze.forceFocus();
	        //maze.setLayoutData(new GridData(horizontalAlignment, verticalAlignment, grabExcessHorizontalSpace, grabExcessVerticalSpace, horizontalSpan, verticalSpan));
			((Maze3dDisplayer)mazeDisplayerCanvas).setMazeBasicWindow(this);
			initKeyListeners();
			//System.out.println("mazeWithName: "+mazeWithName);
			//crossedArr = mazeData.getCrossSectionByX(mazeData.getStartPosition().getXPosition());
		}
		mazeObject = mazeWithName;
		String[] args={"x",mazeObject.getStartPosition().getXPosition()+"","for",name};
		viewCommandMap.get("display cross section by").doCommand(args);
		this.mazeDisplayerCanvas.setExitX(mazeWithName.getGoalPosition().getYposition());
		this.mazeDisplayerCanvas.setExitY(mazeWithName.getGoalPosition().getZposition());
		this.mazeDisplayerCanvas.setExitFloor(mazeWithName.getGoalPosition().getXPosition()); 
		((Maze3dDisplayer) mazeDisplayerCanvas).setCurrentFloor(currentFloor);
		mazeDisplayerCanvas.setCharacterPosition(mazeWithName.getStartPosition().getYposition(), mazeWithName.getStartPosition().getZposition());
	}
	
	
	public boolean getFloorArr(String direction) 
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
					this.currentFloorCrossedArr = crossedArrToCheck;
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
		mazeDisplayerCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,1)); 
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
		
		metaDataLabel.setText("hello");
	}
	
	
	public void setMetaDataLabel(int x,int y,int z,int goalX,int goalY,int goalZ)
	{
		String metaData =   "You:\t"+x+": {"+y+","+z+"}\nGoal:\t"+goalX+": {"+goalY+","+goalZ+"}";
		
		if (mazeDisplayerCanvas != null && mazeObject != null){
			if (x == goalX && y == goalY && z == goalZ)
			{
				metaData += "\nYou Won!";
			}
			metaDataLabel.setText(metaData);
			setPossibleKeysLabel(x, y, z);
		}
	}
	
	public void setPossibleKeysLabel(int x, int y, int z){
		String possibleKeysText = "    "; 
		if (mazeDisplayerCanvas != null && mazeObject != null){
			String[] possibleMoves = mazeObject.getPossibleMoves(new Position(x, y, z));
			String result = ""; 
			for (int i = 0; i < possibleMoves.length; i++) {
				result += possibleMoves[i]+"";
			}

			if (result.contains("Up")){
				possibleKeysText += "\u21E7";
			}
			if (result.contains("Down"))
			{
				possibleKeysText += "\u21E9";
			}
			
			FontData[] fontData = metaDataLabel.getFont().getFontData();
			for(int i = 0; i < fontData.length; ++i)
			    fontData[i].setHeight(30);

			final Font newFont = new Font(display, fontData);
			possibleKeysLabel.setFont(newFont);

			possibleKeysLabel.setText(possibleKeysText);


		}

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
		String[] mazeNameArr={mazeName,};
		this.viewCommandMap.get("display").doCommand(mazeNameArr);
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
		
		String[] mazeNameArr = {mazeObjectName,"A*"};
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
				if(isSolving){solutionToDisplay=solution.getSolution();}
				else{solutionToDisplay.add(solution.getSolution().get(0));}
				for (State<Position> p: solutionToDisplay)
				{
					
					if(shell.isDisposed()){Thread.currentThread().interrupt();}
					else if(p.getCameFromAction().equals("Down"))
					{
						getFloorArr("DOWN");
						mazeDisplayerCanvas.moveDown();
					}
					else if(p.getCameFromAction().equals("Up"))
					{
						getFloorArr("UP");
						mazeDisplayerCanvas.moveUp();
					}	
					else if(p.getCameFromAction().equals("Backward")){mazeDisplayerCanvas.moveForward();}
					else if(p.getCameFromAction().equals("Forward")){mazeDisplayerCanvas.moveBackward();}
					else if(p.getCameFromAction().equals("Left")){mazeDisplayerCanvas.moveLeft();}
					else if(p.getCameFromAction().equals("Right")){mazeDisplayerCanvas.moveRight();}
					
					try {Thread.sleep(250);} 
					catch (InterruptedException e) 
					{
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
	public void errorNoticeToUser(String s) 
	{
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
		    	MessageBox messageBox = new MessageBox(new Shell(display),SWT.ICON_INFORMATION|SWT.OK);
				messageBox.setMessage(s);
				messageBox.setText("Notification");
				messageBox.open();
				if(mazeDisplayerCanvas!=null){mazeDisplayerCanvas.setFocus();}
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
	public CLI getCli() {return cli;}
	/**
	 * @param cli the cli to set
	 */
	public void setCli(CLI cli) {this.cli = cli;}
	
	/**
	 * @return the viewCommandMap
	 */
	public HashMap<String, Command> getViewCommandMap() {return viewCommandMap;}

	/**
	 * @return the cliMenu
	 */
	public String getCliMenu() {return cliMenu;}
	/**
	 * @param cliMenu the cliMenu to set
	 */
	public void setCliMenu(String cliMenu) {this.cliMenu = cliMenu;}
	/**
	 * @return the in
	 */
	public BufferedReader getIn() {return in;}
	/**
	 * @param in the in to set
	 */
	public void setIn(BufferedReader in) {this.in = in;}
	/**
	 * @return the out
	 */
	public PrintWriter getOut() {return out;}
	/**
	 * @param out the out to set
	 */
	public void setOut(PrintWriter out) {this.out = out;}
	
	/**
	 * @return the won
	 */
	public boolean isWon() {return won;}

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
			if (task != null || timer != null)
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
}
