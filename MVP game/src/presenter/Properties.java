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
	protected  StringBuilder defaultAlgorith;
	protected  StringBuilder defaultSolver;
	
	public Properties() {
		//super();
		this.numOfThreads = 2;
		this.defaultAlgorith = new StringBuilder("MyMaze3dGenerator");
		this.defaultSolver = new StringBuilder("A*");
	}
	
	
	
	public Properties(int numOfThreads,final String defaultAlgorith1, final String defaultSolver1) {
		super();
		this.numOfThreads = numOfThreads;
		this.defaultAlgorith = new StringBuilder(defaultAlgorith1);
		this.defaultSolver = new StringBuilder(defaultSolver1);
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
	public final StringBuilder getDefaultAlgorith() {
		return defaultAlgorith;
	}




	/**
	 * @param defaultAlgorith the defaultAlgorith to set
	 */
	public final void setDefaultAlgorith(final StringBuilder defaultAlgorith) {
		this.defaultAlgorith = defaultAlgorith;
	}




	/**
	 * @return the defaultSolver
	 */
	public StringBuilder getDefaultSolver() {
		return defaultSolver;
	}




	/**
	 * @param defaultSolver the defaultSolver to set
	 */
	public void setDefaultSolver(StringBuilder defaultSolver) {
		this.defaultSolver = defaultSolver;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
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
