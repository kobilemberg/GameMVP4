package presenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Model;
import view.View;

public class Presenter implements Observer {
	View view;
	Model model;
	HashMap<String, Command> viewCommandMap;
	
	
	/**
	* Instantiates a new  my own controller with given view and model.
	* @param view View represent the view layer
	* @param model Model represent the model layer
	* @return new MyController as instance
	* */
		public Presenter(View view, Model model) {
			super();
			this.view = view;
			this.model = model;
			
			HashMap<String, Command> viewCommandMap = new HashMap<String, Command>();
			viewCommandMap.put("dir",new Command() 
			{	
				@Override
				public void doCommand(String[] args) 
				{
					try {
						view.setUserCommand(1);
						((Observable)view).notifyObservers(args);
					} catch (NullPointerException e) {e.printStackTrace();}	
				}
			});
			
			viewCommandMap.put("generate 3d maze",new Command() 
			{
				@Override
				public void doCommand(String[] args) 
				{
					try {
						view.setUserCommand(2);
						((Observable)view).notifyObservers(args);
						//model.generateMazeWithName(args[0],args[1], args[2], args[3],args[4]);
					} catch (Exception e) {
						view.errorNoticeToUser("Wrong parameters, Usage:generate 3d maze <name> <generator> <other params>");
						e.printStackTrace();
					}
					 
				}
			});
			
			viewCommandMap.put("display",new Command() 
			{
				@Override
				public void doCommand(String[] args) 
				{
					//args[1] = name
					try {
						view.setUserCommand(3);
						((Observable)view).notifyObservers(args);
					} catch (NullPointerException e) {view.errorNoticeToUser("Exception: there is no maze named "+args[0]);}
				}
			});
			
			viewCommandMap.put("display cross section by",new Command() 
			{
				@Override
				public void doCommand(String[] args) 
				{
					try {
						view.setUserCommand(4);
						((Observable)view).notifyObservers(args);
					} catch (Exception e) {
						e.printStackTrace();
						view.errorNoticeToUser("Exception: problem with args");}
				}
			});
			
			viewCommandMap.put("save maze",new Command() 
			{
				@Override
				//args[1] = name, args[2] = filename
				public void doCommand(String[] args) {
					
					try {
							if(!(args[0].isEmpty()||args[1].isEmpty()))
							{
								view.setUserCommand(5);
								((Observable)view).notifyObservers(args);
							}
							else{view.errorNoticeToUser("problem with args");}
					} catch (Exception e) {view.errorNoticeToUser("Exception: problem with args");}
				}
			});
			
			viewCommandMap.put("load maze",new Command() 
			{
				@Override
				public void doCommand(String[] args) {
					try {
						view.setUserCommand(6);
						((Observable)view).notifyObservers(args);
					} catch (Exception e) {view.errorNoticeToUser("Exception: problem with args");}
				}
			});
			
			viewCommandMap.put("maze size",new Command() 
			{
				@Override
				//args[1] = mazeName
				public void doCommand(String[] args) {
					try {
						view.setUserCommand(7);
						((Observable)view).notifyObservers(args);
					}catch (Exception e) {
						e.printStackTrace();
						view.errorNoticeToUser("Exception: problem with args");
					}
				}
			});
			
			viewCommandMap.put("file size",new Command() 
			{
				@Override
				//args[1] = Filename
				public void doCommand(String[] args) {
					try {
						view.setUserCommand(8);
						((Observable)view).notifyObservers(args);
					} catch (Exception e) {
						e.printStackTrace();
						view.errorNoticeToUser("Exception: problem with args");
					}	
				}
			});
			
			viewCommandMap.put("solve",new Command() 
			{
				@Override
				public void doCommand(String[] args) {
						try {
							view.setUserCommand(9);
							((Observable)view).notifyObservers(args);
						} catch (Exception e) {
							e.printStackTrace();
							view.errorNoticeToUser("Exception: problem with args");
						}	
				}
			});
			
			viewCommandMap.put("display solution",new Command() 
			{
				@Override
				//args[1] = mazeName
				public void doCommand(String[] args) {
						try {
							view.setUserCommand(10);
							((Observable)view).notifyObservers(args);
						} catch (NullPointerException e) {
							e.printStackTrace();
							view.errorNoticeToUser("Exception: unexisted solution");
						}
				}
			});
			
			viewCommandMap.put("exit",new Command() 
			{
				@Override
				public void doCommand(String[] args) {
					view.setUserCommand(11);
					((Observable)view).notifyObservers(args);
					model.exit();}
			});
			String cliMenu=new String();
			cliMenu+= "1:	dir <path>\n";
			cliMenu+= "2:	generate 3d maze <Maze name> <MyMaze3dGenerator\\SimpleMaze3dGenerator> <X> <Y> <Z>\n";
			cliMenu+= "3:	display <Maze name>\n";
			cliMenu+= "4:	display cross section by {X,Y,Z} <index> for <Maze name>\n";
			cliMenu+= "5:	save maze <Maze name> <File name>\n";
			cliMenu+= "6:	load maze <File name> <Maze name>\n";
			cliMenu+= "7:	maze size <Maze name>\n";
			cliMenu+= "8:	file size <File name>\n";
			cliMenu+= "9:	solve <Maze name> <A*\\BFS>\n";
			cliMenu+= "10:	display solution <Maze name>\n";
			cliMenu+= "11:	exit\n";
			this.viewCommandMap = viewCommandMap;
			view.setCommands(viewCommandMap);
			view.setCommandsMenu(cliMenu);
			
		}

	//Getters and setters
		
		/**
	 * @return the viewCommandMap
	 */
	public HashMap<String, Command> getViewCommandMap() {
		return viewCommandMap;
	}

	/**
	 * @param viewCommandMap the viewCommandMap to set
	 */
	public void setViewCommandMap(HashMap<String, Command> viewCommandMap) {
		this.viewCommandMap = viewCommandMap;
	}

		/**
		 * This method will set controller view layer
		 * @param view View represent the view layer
		 */
		public void setView(View view){this.view = view;}
		/**
		* This method will set controller model layer
		* @param model Model represent the model layer
		*/
		public void setModel(Model model){this.model = model;}
		/**
		* This method will return the controller view layer
		* @return View instance represent the view layer of the controller
		*/
		public View getView(){return view;}
		/**
		* This method will return the controller model layer
		* @return Model instance represent the Model layer of the controller
		*/
		public Model getModel(){return model;}
		
		public void errorNoticeToViewr(String s) {view.errorNoticeToUser(s);}
	
		
		
		
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object args) {
		if(o==view)
		{
			String[] argArr = ((String[])args).clone();
			
			int input = view.getUserCommand();
			switch (input) {
			case 1:
				try {model.dir(argArr[0]);} catch (Exception e) {view.errorNoticeToUser("Exception: Illegal path");}
				break;
			
			case 2:
				try {model.generateMazeWithName(argArr[0], argArr[1],  argArr[2],  argArr[3],  argArr[4]);}
				catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Exception: Wrong parameters, Usage:generate 3d maze <name> <generator> <other params>");
				}
				break;

			case 3:
				model.getMazeWithName(argArr[0]);
				break;

			case 4:
				try {model.getCrossSectionByAxe(argArr[0], argArr[1], argArr[3]);} catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Exception: problem with args");}
				break;

			case 5:
				try {model.saveCompressedMazeToFile(argArr[0], argArr[1]);} catch (IOException e) {view.errorNoticeToUser("Exception: problem with args");}
				break;
				
			case 6:
				try {model.loadAndDeCompressedMazeToFile(argArr[0], argArr[1]);} catch (IOException e) {view.errorNoticeToUser("Exception: problem with args");}
				break;
				
			case 7:
				try {model.getSizeOfMazeInRam(argArr[0]);} 
				catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Exception: problem with args");
				}
				break;
				
			case 8:
				try {model.getSizeOfMazeInFile(argArr[0]);} 
				catch (Exception e) {
					e.printStackTrace();
					view.errorNoticeToUser("Exception: problem with args");
				}
				
				break;
			case 9:
				try {model.solveMaze(argArr[0], argArr[1]);} catch (Exception e) {view.errorNoticeToUser("Exception: problem with args");}
				break;
				
			case 10:
				try {
					model.getSolutionOfMaze(argArr[0]);} catch (Exception e) {view.errorNoticeToUser("Exception: problem with args");}
				break;
				
			case 11:
				model.exit();
				break;

			default:
				break;
			}
		}
		
		
		else if(o==model)
		{
			int modelCompletedNum = model.getModelCompletedCommand();
			Object[] dataToPassToView;
			switch (modelCompletedNum) 
			{
				case 1:
					//dir path;
					view.displayData(model.getData());
					break;
					
				case 2:
					//maze is ready;
					view.tellTheUserMazeIsReady((String)model.getData());
					break;
					
				case 3:
					//display maze
					dataToPassToView = (Object[]) model.getData();
					view.printMazeToUser((Maze3d)dataToPassToView[0], (String)dataToPassToView[1]);
					break;
					
				case 4:
					//display cross section by {X,Y,Z} <index> for <Maze name>
					dataToPassToView = (Object[]) model.getData();
					view.printToUserCrossedArray((int[][])dataToPassToView[0], (String)dataToPassToView[1],  (String)dataToPassToView[2], (String)dataToPassToView[3]);
					break;
				
				case 5:
					//save maze <Maze name> <File name>
					dataToPassToView = (Object[]) model.getData();
					view.tellTheUserTheMazeIsSaved((String)dataToPassToView[0], (String)dataToPassToView[1]);
					break;	
				
				case 6:
					//load maze <File name> <Maze name>
					dataToPassToView = (Object[]) model.getData();
					view.tellTheUserTheMazeIsLoaded((String)dataToPassToView[0], (String)dataToPassToView[1]);
					break;
				
				case 7:
					//maze size <Maze name>
					dataToPassToView = (Object[]) model.getData();
					view.tellTheUsersizeOfMazeInRam((String)dataToPassToView[0], (double)dataToPassToView[1]);
					break;
				
				case 8:
					//file size <File name>
					dataToPassToView = (Object[]) model.getData();
					view.tellTheUsersizeOfMazeInFile((String)dataToPassToView[0], (double)dataToPassToView[1]);
					break;
							
				case 9:
					//solve <Maze name> <A*\BFS>
					view.tellTheUserSolutionIsReady((String)(model.getData()));
					break;
					
				case 10:
					//display solution <Maze name>   //Solution<Position>
					dataToPassToView = (Object[]) model.getData();
					view.printSolutionToUser((String)(dataToPassToView[0]), (Solution<Position>)(dataToPassToView[1]));
					break;
					
				case -1:
					//Error
					view.errorNoticeToUser((String) model.getData());
					break;
					
				default:
					break;
			}
		}
	}
}


