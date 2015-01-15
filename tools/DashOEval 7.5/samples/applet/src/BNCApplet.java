/* 
	BNCApplet.java

	Author:			Arnold Reinhold
	Date:  2000-12-15
	Description:
	
	Copyright © 2000 Arnold G. Reinhold, Cambridge. MA USA
	reinhold@world.std.com	

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version, with 
two additional restrictions: 

1. You may not redistribute versions modified to create "malware," 
such as versions that deliberately produce inaccurate or misleading 
results or that surreptitiously capture data entered by the user or 
that produce random values that are more predictable or guessable.

2. You may not redistribute this program in ways that violate the 
export control laws of the United States.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You can find a copy of the GNU General Public License at 
http://www.gnu.org/copyleft/gpl.html , or write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

*/


import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class BNCApplet extends Applet 
{ 


	boolean isStandalone = false;

// Variables to hold applet parameters
	String foregroundColor;
	String backgroundColor;
	public final static int PRIMECERTDEFLAULT = 100;     // prob(is a prime) = 1 - 2**-primeCert
    public final static int MAXFACTORIALDEFLAULT = 10000;        
	int primeCert = PRIMECERTDEFLAULT;   
    int maxFactorial = MAXFACTORIALDEFLAULT;        
	BigNumCalc mycalc = null;

// member declarations
	public BNCApplet() 
	{
	}

	// Retrieve the value of an applet parameter
	public String getParameter(String key, String def) 
	{
		return isStandalone ? System.getProperty(key, def) :
			(getParameter(key) != null ? getParameter(key) : def);
	}

	// Get info on the applet parameters
	public String[][] getParameterInfo() 
	{
		String info[][] =
		{
			{"maxFactorial", "int", "Maximum integer for which factorial will be computed."},
			{"primeCert", "int", "Probability(is a prime) = 1 - 2**-primeCert"}
//			{"foregroundColor", "String", ""},
//			{"backgroundColor", "String", ""}
		};

		return info;
	}

	// Get applet information
	public String getAppletInfo() 
	{
		return mycalc.aboutString;
	}

	// Initialize the applet
	public void init() 
	{
		try { maxFactorial = Integer.parseInt(this.getParameter("maxFactorial",String.valueOf(MAXFACTORIALDEFLAULT))); } catch (Exception e) { /*e.printStackTrace();*/  }
		try { primeCert = Integer.parseInt(this.getParameter("primeCert",String.valueOf(PRIMECERTDEFLAULT))); } catch (Exception e) { }
//		try { foregroundColor = this.getParameter("foregroundColor",""); } catch (Exception e) {  }
//		try { backgroundColor = this.getParameter("backgroundColor",""); } catch (Exception e) {  } 
		try {
			initComponents();
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void initComponents() throws Exception
	{
		mycalc = new BigNumCalc(this.primeCert, this.maxFactorial, this);
		// the following code sets the frame's initial state
/*		Dimension mySize = new java.awt.Dimension(400, 400);
		setLocation(new java.awt.Point(0, 0));
		setLayout(null);
		setSize(mySize);
		mycalc.setSize(mySize);  */

		add(mycalc);
		mycalc.addKeyListener(mycalc);
		mycalc.requestFocus();  
	}
	

	// Standard method to start the applet
	public void start() 
	{
	this.validate();
	}

	// Standard method to stop the applet
	public void stop() 
	{
 	   if (mycalc.buttonCalc != null) {
 	      mycalc.buttonCalc.stop(); 
 	      mycalc.buttonCalc = null;}
 	}

	// Standard method to destroy the applet
	public void destroy() 
	{
	}

	// Main entry point when running standalone
	//FIX -- not yet tested!!!
	public static void main(String[] args) 
	{
	    BigNumCalc mycalc = 
	       new BigNumCalc(PRIMECERTDEFLAULT, MAXFACTORIALDEFLAULT, null); //primeCert, maxFactorial
		BNCApplet applet = new BNCApplet();
		applet.isStandalone = true;
		Frame frame = new Frame();
		frame.setTitle("Big Number Calculator");
		frame.add( applet, BorderLayout.CENTER );
		applet.init();
		applet.start();
		frame.setSize( 400, 400 );
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation( (d.width - frame.getSize().width) / 2,
			(d.height - frame.getSize().height) / 2);
			
		frame.add(mycalc);
		frame.resize(400, 400);
		frame.show();

		frame.setVisible( true );
		

/*		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});  */

	} 


/*	// Close the window when the close box is clicked
	void thisWindowClosing(java.awt.event.WindowEvent e)
	{
		setVisible(false);
//		dispose();
		System.exit(0);
	}   */
	
}