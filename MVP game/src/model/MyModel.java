package model;
/**
 * @author Kobi Lemberg, Alon Abadi
 * @version 1.0
 * <h1> MyModel </h1>
 * MyModel class implements Model interface, 
 * class goal is to act as MVC Model and perform all business logic calculations.
 */

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.demo.Demo;
import algorithms.demo.SearchableMaze3d;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.Solution;
import presenter.Properties;


public class MyModel extends Observable implements Model{
	ExecutorService TP = Executors.newCachedThreadPool();
	Object data;
	int modelCompletedCommand=0;
	HashMap<String, Maze3d> maze3dMap = new HashMap<String, Maze3d>();
	HashMap<Maze3d, Solution<Position>> solutionMap = new HashMap<Maze3d, Solution<Position>>();
	HashMap<String, Thread> openThreads = new HashMap<String,Thread>();
	Properties properties;
	//Constructors
	/**
	* Instantiates a new  my own model.
	*/
	@SuppressWarnings("unchecked")
	public MyModel(Properties p)
	{
		super();
		this.properties = p;
		File map = new File("External files/solutionMap.txt");
		if(map.exists())
		{
			ObjectInputStream mapLoader;
			try {
				mapLoader = new ObjectInputStream(new GZIPInputStream(new FileInputStream(new File("External files/solutionMap.txt"))));
				solutionMap = (HashMap<Maze3d, Solution<Position>>) mapLoader.readObject();
				mapLoader.close();
				this.properties = read("External files/Properties.xml");
			} catch (FileNotFoundException e) {
				errorNoticeToControlelr("Problam with solution map file");
			} catch (IOException e) {
				errorNoticeToControlelr("Problam with solution map file(IO)");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				errorNoticeToControlelr("problam with class");
			} catch (Exception e) {
				errorNoticeToControlelr("Problem with xml");
				e.printStackTrace();
			}
		}
		else
			solutionMap = new HashMap<Maze3d, Solution<Position>>();
		
	
		
	}
	/**
	* Instantiates a new  my own model with given controller
	* @param controller Controller represent the controller layer to work with
	* @return new MyModel as instance
	*/
	
	
//Getters and setters
	/**
	* this method will set the controller to work with
	* @param controller Controller represent the controller layer to work with
	*/

//Functionality
	@Override
	public void dir(String dir) throws NullPointerException
	{
		if(dir!=null)
		{
			File folder = new File(dir);
			if((folder.exists()&&folder.isDirectory()))
			{
				
				//if(folder.isDirectory())
				{
					String strOfDir ="Files and Directories in: "+dir+"\n";
					for (String fileOrDirectory: folder.list())
					{
						strOfDir+=fileOrDirectory+"\n";
					}
				//	System.out.println("StrOfDir: "+strOfDir);
					//return strOfDir;
					this.data = strOfDir;
					this.modelCompletedCommand=1;
				//	System.out.println("Modell");
					this.setChanged();
					notifyObservers();
				}
			}
			else
				errorNoticeToControlelr("Illegal path");
		}
		else
			errorNoticeToControlelr("Illegal path");
		//return null;
	}
	@Override
	public void generateMazeWithName(String name, String generator, String floors, String lines, String columns) {
		
		if(floors.isEmpty()||lines.isEmpty()||columns.isEmpty())
		{
			errorNoticeToControlelr("Wrong parameters, Usage:generate 3d maze <name> <generator> <other params>");
		}
		
		Callable<Maze3d> generateMazeThread = new Callable<Maze3d>() 
		{

			@Override
			public Maze3d call() throws Exception 
			{
				Maze3dGenerator maze = new MyMaze3dGenerator();
				if(generator.equals("mymaze3dgenerator"))
					maze = new MyMaze3dGenerator();
				else if(generator.equals("simplemazegenerator"))
					maze = new SimpleMaze3dGenerator();
				else 
					maze = new MyMaze3dGenerator();
				
				if(!floors.isEmpty()&&!lines.isEmpty()&&!columns.isEmpty())
				{
					maze3dMap.put(name, maze.generate(new Integer(floors),new Integer(lines),new Integer(columns)));
					
					this.mazeIsReady(name);
					return maze3dMap.get(name);
				}
				else
				{	
					errorNoticeToControlelr("Wrong parameters, Usage:generate 3d maze <name> <generator> <other params>");
					return null;
				}
				
			}

			private void mazeIsReady(String name) 
			{
				setChanged();
				modelCompletedCommand=2;
				setData(name);
				notifyObservers();
				
			}
			
		};
		TP.submit(generateMazeThread);
		
		
	}
	
	/*
	Old generateMazeWithName with Runnable
	@Override
	public void generateMazeWithName(String name, String generator, String floors, String lines, String columns) {
		
		if(floors.isEmpty()||lines.isEmpty()||columns.isEmpty())
		{
			errorNoticeToControlelr("Wrong parameters, Usage:generate 3d maze <name> <generator> <other params>");
		}
		
		Thread generateMazeThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Maze3dGenerator maze = new MyMaze3dGenerator();
				if(generator.equals("mymaze3dgenerator"))
					maze = new MyMaze3dGenerator();
				else if(generator.equals("simplemazegenerator"))
					maze = new SimpleMaze3dGenerator();
				else 
					maze = new MyMaze3dGenerator();
				
				if(!floors.isEmpty()&&!lines.isEmpty()&&!columns.isEmpty())
				{
					maze3dMap.put(name, maze.generate(new Integer(floors),new Integer(lines),new Integer(columns)));
					
					this.mazeIsReady(name);
					
				}
				else
				{	
					errorNoticeToControlelr("Wrong parameters, Usage:generate 3d maze <name> <generator> <other params>");
				}
				
			}

			private void mazeIsReady(String name) {
				setChanged();
				modelCompletedCommand=2;
				setData(name);
				notifyObservers();
				
			}
		}, "generateMazeThread");
		openThreads.put(generateMazeThread.getName(), generateMazeThread);
		TP.submit(generateMazeThread);
		
		
	}*/
	
	@Override
	public void getMazeWithName(String nameOfMaze) {
		
		if(maze3dMap.containsKey(nameOfMaze))
		{
			this.modelCompletedCommand=3;
			Object[] dataToSet = new Object[2];
			dataToSet[0]=maze3dMap.get(nameOfMaze);
			dataToSet[1]=nameOfMaze;
			data= dataToSet;
			setChanged();
			notifyObservers();
		}
	}
	@Override
	public void getCrossSectionByAxe(String axe, String index, String mazeName) {
		int[][] arrToRet = null;
		if(maze3dMap.containsKey(mazeName))
		{
			Maze3d maze = maze3dMap.get(mazeName);
			//Floors
			if(axe.equals("x"))
			{
				if(  (new Integer(index)) >=0 && (new Integer(index)) < maze.getMaze().length)
				{
					arrToRet = maze.getCrossSectionByX(new Integer(index));
				}
				else{errorNoticeToControlelr("illegal index, llegal indexes are:0-"+maze.getMaze().length);}
			}
			//Lines
			else if(axe.equals("y"))
			{
				if(  (new Integer(index)) >=0 && (new Integer(index)) < maze.getMaze()[0].length)
				{
					arrToRet = maze.getCrossSectionByY(new Integer(index));
				}
				else{errorNoticeToControlelr("illegal index, llegal indexes are:0-"+maze.getMaze()[0].length);}
			}
			//Columns
			else if(axe.equals("z"))
			{
				if(  (new Integer(index)) >=0 && (new Integer(index)) < maze.getMaze()[0][0].length)
				{
					arrToRet = maze.getCrossSectionByZ(new Integer(index));
				}
				else{errorNoticeToControlelr("illegal index, llegal indexes are:0-"+maze.getMaze()[0][0].length);}
			}
			
			else{errorNoticeToControlelr("incorrect axe, the options are: X,Y,Z");}
		}
		else if(!maze3dMap.containsKey(mazeName))
		{
			errorNoticeToControlelr("problem with args");
		}
		if(arrToRet!=null)
		{
			Object[] argsToRet= new Object[4];
			argsToRet[0] = arrToRet;
			argsToRet[1] = axe;
			argsToRet[2] = index;
			argsToRet[3] = mazeName;
			
			
			this.setData(argsToRet );
			this.modelCompletedCommand=4;
			setChanged();
			notifyObservers();
		}
		else
		{
			errorNoticeToControlelr("problem with args");
		}
		
	}
	@Override
	public void saveCompressedMazeToFile(String mazeName, String fileName) throws IOException {
		
		if(fileName.isEmpty()||mazeName.isEmpty())
		{
			errorNoticeToControlelr("Cannot resolve filename\\maze name");
		}
		else
		{
			if(maze3dMap.containsKey(mazeName))
			{
				File fileCreator = new File(fileName);
				if(fileCreator.exists())
				{
					OutputStream out=new MyCompressorOutputStream(new FileOutputStream(fileName));
					out.write(maze3dMap.get(mazeName).toByteArray());
					out.flush();
					out.close();
					String[] datatToSet = new String[2];
					datatToSet[0] = mazeName;
					datatToSet[1] = fileName;
					data = datatToSet;
					this.modelCompletedCommand=5;
					this.setChanged();
					this.notifyObservers();
				}
				else if(!fileCreator.exists())
				{
					if(fileCreator.createNewFile())
					{
						OutputStream out=new MyCompressorOutputStream(new FileOutputStream(fileName));
						out.write(maze3dMap.get(mazeName).toByteArray());
						out.flush();
						out.close();
						String[] datatToSet = new String[2];
						datatToSet[0] = mazeName;
						datatToSet[1] = fileName;
						data = datatToSet;
						this.modelCompletedCommand=5;
						this.setChanged();
						this.notifyObservers();
					}
					else{errorNoticeToControlelr("It seems that file exists/Cannot create file.");}
						
				}
			
			}
			else
			{
				
				errorNoticeToControlelr("The name is incorrect");
				throw new NullPointerException("There is no maze " +mazeName);
			}
		}
	}
	@Override
	public void loadAndDeCompressedMazeToFile(String fileName, String mazeName) throws IOException {
		if(fileName.isEmpty()||mazeName.isEmpty())
		{
			errorNoticeToControlelr("File not found\\Cannot resolve maze name");
		}
		else
		{
			File file = new File(fileName);
			if(file.exists())
			{
				
				@SuppressWarnings("resource")
				FileInputStream fileIn = new FileInputStream(fileName);//Opening the file
				byte[] dimensionsArr = new byte[12];//array of diemensions
				fileIn.read(dimensionsArr, 0, 12);//reading from file to the array
				int xLength,yLength,zLength;//setting parameters
				byte[] copyArr = new byte[4];//extracting int
				//xLength
				for (int i = 0; i <4; i++) {copyArr[i] = dimensionsArr[i];}
				xLength = ByteBuffer.wrap(copyArr).getInt();
				
				//yLength
				for (int i = 0; i <4; i++) {copyArr[i] = dimensionsArr[i+4];}
				yLength = ByteBuffer.wrap(copyArr).getInt();
				
				//zLength
				for (int i = 0; i <4; i++) {copyArr[i] = dimensionsArr[i+8];}
				zLength = ByteBuffer.wrap(copyArr).getInt();
				
				
				byte[] mazeMatrix = new byte[xLength*yLength*zLength + 36];
				
				InputStream in=new MyDecompressorInputStream(new FileInputStream(fileName));
				in.read(mazeMatrix);
				in.close();
				Maze3d mazeToSave = new Maze3d(mazeMatrix);
				maze3dMap.put(mazeName, mazeToSave);
				String[] datatToSet = new String[2];
				datatToSet[1] = mazeName;
				datatToSet[0] = fileName;
				data = datatToSet;
				this.modelCompletedCommand=6;
				this.setChanged();
				this.notifyObservers();
				
			}
			else
			{

				errorNoticeToControlelr("File not found");
				throw new FileNotFoundException("File not found");
			}
		}
		
	}
	@Override
	public void getSizeOfMazeInRam(String mazeName) {
		if(maze3dMap.containsKey(mazeName))
		{
			Object[] dataToSet = new Object[2];
			dataToSet[0]=mazeName;
			dataToSet[1]=new Double(maze3dMap.get(mazeName).toByteArray().length);
			this.modelCompletedCommand=7;
			this.setChanged();
			this.setData(dataToSet);
			notifyObservers();
		}
		else	
			errorNoticeToControlelr("There is no maze named: "+mazeName);
	}
	@Override
	public void getSizeOfMazeInFile(String fileName) {
		File f = new File(fileName);
		if(!f.exists())
		{
			errorNoticeToControlelr("File "+fileName+" doesnt exists");
		}
		else
		{
			Object[] dataToSet = new Object[2];
			dataToSet[0]=fileName;
			dataToSet[1]=new Double(f.length());
			this.modelCompletedCommand=8;
			this.setChanged();
			this.setData(dataToSet);
			notifyObservers();
		}
	}
	@Override
	public void solveMaze(String mazeName, String algorithm) 
	{
		if(maze3dMap.containsKey(mazeName))
		{
			if(solutionMap.containsKey(maze3dMap.get(mazeName)))
			{
				System.out.println("Model notification: I have this solution, i won't calculate it again!");
				modelCompletedCommand=9;
				setData(mazeName);
				setChanged();
				notifyObservers();
			}
			else
			{
				Callable<Solution<Position>> mazeSolver =new Callable<Solution<Position>>() 
				{
					@Override
					public Solution<Position> call() throws Exception 
					{
						if(maze3dMap.containsKey(mazeName))
						{
							Demo d = new Demo();
							SearchableMaze3d searchableMaze = new SearchableMaze3d(maze3dMap.get(mazeName));
							if(algorithm.equals("bfs"))
							{		
								Solution<Position> solutionToAdd = d.solveSearchableMazeWithBFS(searchableMaze);
								solutionMap.put(maze3dMap.get(mazeName), solutionToAdd);
								modelCompletedCommand=9;
								setData(mazeName);
								setChanged();
								notifyObservers();
								return solutionMap.get(maze3dMap.get(mazeName));
							}
							else if(algorithm.equals("a*"))
							{
								Solution<Position> solutionToAdd = d.solveSearchableMazeWithAstarByManhatenDistance(searchableMaze);
								solutionMap.put(maze3dMap.get(mazeName), solutionToAdd);
								modelCompletedCommand=9;
								setChanged();
								setData(mazeName);
								notifyObservers();
								return solutionMap.get(maze3dMap.get(mazeName));
							}
							else{errorNoticeToControlelr("Algorithm: "+algorithm+" is not valid!");}
						}
						return null;
					}
				};
				TP.submit(mazeSolver);
			}
		}
		else{errorNoticeToControlelr("There is no maze named: "+mazeName);}
	}
		
		
	
	
/*
  Old solveMaze with runnable
	@Override
	public void solveMaze(String mazeName, String algorithm) 
	{
		Thread mazeSolver = new Thread((new Runnable() {		
			@Override
			public void run() 
			{
				if(maze3dMap.containsKey(mazeName))
				{
					Demo d = new Demo();
					SearchableMaze3d searchableMaze = new SearchableMaze3d(maze3dMap.get(mazeName));
					if(solutionMap.containsKey(mazeName))
					{
						modelCompletedCommand=9;
						setData(mazeName);
						setChanged();
						notifyObservers();
						//controller.solutionIsReady(mazeName);
						
					}
						
					else
					{
						if(algorithm.equals("bfs"))
						{		
							Solution<Position> solutionToAdd = d.solveSearchableMazeWithBFS(searchableMaze);
							solutionMap.put(mazeName, solutionToAdd);
							modelCompletedCommand=9;
							setData(mazeName);
							setChanged();
							notifyObservers();
							//controller.solutionIsReady(mazeName);
						}
						else if(algorithm.equals("a*"))
						{
							Solution<Position> solutionToAdd = d.solveSearchableMazeWithAstarByManhatenDistance(searchableMaze);
							solutionMap.put(mazeName, solutionToAdd);
							modelCompletedCommand=9;
							setChanged();
							setData(mazeName);
							notifyObservers();
							//controller.solutionIsReady(mazeName);
						}
						else
						{errorNoticeToControlelr("Algorithm: "+algorithm+" is not valid!");}
					}
				}
				else
				{errorNoticeToControlelr("There is no maze named: "+mazeName);}
			}
		}),"MazeSolverThread");
		openThreads.put(mazeSolver.getName(), mazeSolver);
		TP.submit(mazeSolver);
	}
 */
	
	
	@Override
	public void getSolutionOfMaze(String mazeName) {
		
		if(maze3dMap.containsKey(mazeName))
		{
			if(solutionMap.containsKey(maze3dMap.get(mazeName)))
			{
				modelCompletedCommand = 10;
				Object[] dataToSet = new Object[2];
				dataToSet[0] = mazeName;
				dataToSet[1] = solutionMap.get(maze3dMap.get(mazeName));
				setChanged();
				this.data = dataToSet;
				notifyObservers();
			}
			else{errorNoticeToControlelr("maze wasn't solve!");}
		}
		else{errorNoticeToControlelr("maze wasn't solve!");}
	}
	@Override
	public void exit() {
		try {
			
			
			modelCompletedCommand =11;
			TP.shutdownNow();
			ObjectOutputStream mapSave = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(new File("External files/solutionMap.txt"))));
			mapSave.writeObject(this.solutionMap);
			mapSave.flush();
			mapSave.close();
		} catch (Exception e) {
			errorNoticeToControlelr("ThreadPool exit error");
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	* Ihis method will notice to controller an error messege
	* @param s String represent the error to notice
	*/
	public void errorNoticeToControlelr(String s)
	{
		modelCompletedCommand=-1;
		data = s;
		this.setChanged();
		notifyObservers();
	}
	@Override
	public boolean isLoaded(String mazeName) {
		return maze3dMap.containsKey(mazeName);
	}
	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return data;
	}
	@Override
	public void setData(Object o) {
		this.data = o;
	}
	
	public int getModelCompletedCommand(){return modelCompletedCommand;}
	
	public void setModelCommandCommand(int commandNum){modelCompletedCommand=commandNum;}
	
	
	public static Properties read(String filename) throws Exception {
        XMLDecoder decoder =
            new XMLDecoder(new BufferedInputStream(
                new FileInputStream(filename)));
        Properties properties = (Properties)decoder.readObject();
        decoder.close();
        return properties;
    }
}
