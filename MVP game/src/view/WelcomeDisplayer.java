package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class WelcomeDisplayer extends Canvas {
	
	String Welcome = "Welcome";

	WelcomeDisplayer(Composite parent, int style) {
		 
			super(parent, style);
			
			final Color white=new Color(null, 255, 255, 255);
			final Color black=new Color(null, 150,150,150);
			Image bGImage = new Image(getDisplay(), "Resources/wood-floor-texture.jpg");
			setBackgroundImage(bGImage);
			//setBackground(white);
			addPaintListener(new PaintListener() 
			{
				@Override
				public void paintControl(PaintEvent e) 
				{
					  // e.gc.setForeground(new Color(null,0,0,0));
					  // e.gc.setBackground(new Color(null,0,0,0));
					   int width=getSize().x;
					   int height=getSize().y;
					   
					   int mx=width/2;

					   double w=(double)width/Welcome.length();
					   double h=(double)height/Welcome.length();
					  
					   
					   Image welcomeImage = new Image(getDisplay(), "Resources/welcome_mat.jpg");
					   //e.gc.drawImage(welcomeImage, 0, 0,welcomeImage.getBounds().width,welcomeImage.getBounds().height,0,0, (int) (getShell().getBounds().width*0.90), (int) (getShell().getBounds().height*0.90));
					   //e.gc.
					   Point textSize = e.gc.stringExtent(Welcome);
					   //e.gc.drawText(Welcome, (getSize().x - textSize.x)/2, (getSize().y - textSize.y)/2);
					   e.gc.drawString(Welcome,  (getSize().x - textSize.x)/2, (getSize().y - textSize.y)/2);
					   
				}
					          
					          
					          
					          
			});
					      
					  
				getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					redraw();
				}
			});		   
					
		}
	

	public String getWelcome() {
		return Welcome;
	}

	public void setWelcome(String welcome) {
		Welcome = welcome;
	}
	
	

}
