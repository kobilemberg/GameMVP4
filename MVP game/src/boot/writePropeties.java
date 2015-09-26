package boot;

import java.beans.DefaultPersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.*;

import presenter.Properties;


public class writePropeties {

	public static void main(String[] args) {
		Properties p = new Properties(3, "MyMaze3dGenerator", "A*");
		System.out.println(p.toString());
		try {
			
			
			FileOutputStream os = new FileOutputStream("properties.xml");
			//XMLEncoder encoder = new XMLEncoder(os);
			//write(p, );
			java.beans.XMLEncoder encoder = new java.beans.XMLEncoder(os);
			encoder.writeObject(p);
			encoder.flush();
			encoder.close();
			
			
			
		} catch (Exception e) {
			System.out.println("problem with writing XML");
		}
		
		
		
	}
	public static void write(Properties f, String filename) throws Exception{
	        
		
		
		//Properties p = new Properties(3, "MyMaze3dGenerator", "A*");
	//	encoder.setPersistenceDelegate(f.getClass(),
		//		new DefaultPersistenceDelegate(new String[] { "defaultSolver","defaultAlgorith"}));
													
		
		
		
		
		/*
		FileOutputStream fos1 = new FileOutputStream(filename);
		java.beans.XMLEncoder xe1 = new java.beans.XMLEncoder(fos1);
		xe1.writeObject(f);
		xe1.flush();
		xe1.close();
		
		XMLEncoder xmlWriter =new XMLEncoder(new FileOutputStream("External files/Properties.xml"));
		
		xmlWriter.writeObject(f);
		xmlWriter.flush();
		xmlWriter.close();
		*/
		/*XMLEncoder encoder =new XMLEncoder(
	              new BufferedOutputStream(
	                new FileOutputStream(filename)));
	        encoder.writeObject(f);
	        encoder.flush();
	        encoder.close();
	        */
	        
	        
	     

	}

}
