package presenter;

import java.io.Serializable;


public class Properties implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//protected int defaultAlgorith;
	//protected int defaultSolver;
	protected int numOfThreads;
	protected  String defaultAlgorith;
	protected  String defaultSolver;
	
	public Properties() {
		//super();
		numOfThreads = 2;
		defaultAlgorith = new String("MyMaze3dGenerator");
		defaultSolver = new String("A*");
	}
	
	
	
	public Properties(int numOfThreads,String defaultAlgorith,String defaultSolver) {
		super();
		this.numOfThreads = numOfThreads;
		this.defaultAlgorith = defaultAlgorith;
		this.defaultSolver = defaultSolver;
	}
	
	

	




	/**
	 * @return the numOfThreads
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}



	/**
	 * @param numOfThreads the numOfThreads to set
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}



	/**
	 * @return the defaultAlgorith
	 */
	public String getDefaultAlgorith() {
		return defaultAlgorith;
	}



	/**
	 * @param defaultAlgorith the defaultAlgorith to set
	 */
	public void setDefaultAlgorith(String defaultAlgorith) {
		this.defaultAlgorith = defaultAlgorith;
	}



	/**
	 * @return the defaultSolver
	 */
	public String getDefaultSolver() {
		return defaultSolver;
	}



	/**
	 * @param defaultSolver the defaultSolver to set
	 */
	public void setDefaultSolver(String defaultSolver) {
		this.defaultSolver = defaultSolver;
	}



	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Properties [numOfThreads=" + numOfThreads + ", defaultAlgorith=" + defaultAlgorith + ", defaultSolver="
				+ defaultSolver + "]";
	}
	
	
	
	
	
	
	
	
}
