
		/*
        * Classname   BigNumCalc
        * 
        * Version 0.9 alpha 
        *
        * Date 2000-12-15
        *
        * created 1999-2-1, 2000-10-17, Applet ver 2000-11-20 
        * Copyright © Arnold G. Reinhold  2000
        
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
        
/** Big Number Calculator Applet  */
        

//package com.diceware.BigNumClac;

import java.security.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;

public class BigNumCalc 
    extends Container implements ActionListener, KeyListener
{
/* ========================CONSTANTS================================*/
  static final boolean debugOn = false;  //debug flag: true/false
  int MODDISPLAYLENGTH = 8;         	 // used in showCalcinfo
  int TAGLENGTH = 6;
  double TAGMIN = 1.0e9;
  int metaKeyCode = 2;	// Control key on PC and Mac. FIX May be a problem in Unix.

  public final static String aboutString = 
//   ruler   0000000001111111111222222222233333333334444X4444455555555556    
//           123456789012345678901234567890123456789012345678901234567890    
  		    "Big Number Calculator Applet by Arnold Reinhold\n" 
          + "Rev 0.9 beta,  Copyright © MM, 2001-1-5 \n"
          + "ABSOLUTELY NO WARRANTY comes with this\n" 
          + "program; it is free software, and you are\n"
		  + "welcome to redistribute it under certain\n"
		  + "conditions. For details see:\n"
          + "http://world.std.com/~reinhold/BigNumCalc.html or\n"
		  + "http://www.gnu.org/copyleft/gpl.html\n"
          + "\"In memory of Prof. Bennington P. Gill.\""
		  ;


//    The button layout is determined by the following table, "keyLabel." 
//    o You can rearange buttons in the table or delete buttons safely. 
//    o You can add buttons to the table if you add the code to handle those buttons. 
//    o The button labels are used in the code. If you want to change any lablels, 
//      do a global search-and-replace to change all occurances the same way
//      (in four places, typically) e.g. s/"or"/"oder"/.
//    o Each line in the keyLabel table must have the same number of elements.
//
  final static String keyLabel[][] = {
  	{"About",	"Store",	"Recall",	"Clear",	"AC"},
    {	"!",	"xCy",		"x<->y",	"SetMod",	"SetBase"},
    {"log2",	"ln",		"log10",	"GetMod",	"SHA1"},
    {"+/-",		"and",		"or",		"xor",		"not"},
    {"SetBit",	"ClearBit","Next p",	"Rand p",	"Random"},
    {"D", "E", "F",   "(", ")"},
    {"A", "B", "C",  "x**y", "*"},
    {"7", "8", "9", "1/x", "/"},
    {"4", "5", "6", "mod", "+"},
    {"1", "2", "3", "gcd", "-"},
    {"",  "0",  "", "del", "="}          
   };
      
              
/* ========================HTML PARAMETERS (see Applet)======================*/
  private int primeCert;       // prob(is a prime) = 1 - 2**-primeCert
  private int maxFactorial;    // largest n for which n! or nCm will be computed  

/* ========================GUI==================================*/
  private Applet myApplet;     // needed to access Applet stuff, like showStatus
  private TextArea xDisplay;
  private TextArea yDisplay;
  private TextArea status;  // was Label
  protected GridBagLayout layout = new GridBagLayout();
  protected GridBagConstraints cons = new GridBagConstraints();
  private Panel panel1 = new Panel();
  private Panel panel2 = new Panel();
//  private Panel panel4 = new Panel();
  
/* ========================STATE================================*/
  public DoButton buttonCalc = null; //the button calculation thread
  private BigInteger x;
  private BigInteger y;
  private BigInteger modulus;
  private BigInteger memory;
  private int base = 10;
  private boolean XdoubleSpecialFlag = false;
  private boolean noDigits = true;
  private String cmd;   // the button command
  private String opMemory = "";
  private Stack parenStack;

/* ========================OTHER VARIABLES================================*/
  private double xDouble;
  private double modDouble;
  private SecureRandom secRandom = null;
  private MessageDigest myHash =  null;
  
    
/* ========================METHODS================================*/
  public BigNumCalc(int primeCertParam, int maxFactorialParam, Applet appletContext)
  {
    primeCert = primeCertParam;     
    maxFactorial = maxFactorialParam;
    myApplet = appletContext;
    try {if (myHash == null) {myHash = myHash.getInstance("SHA");}}
	   catch (NoSuchAlgorithmException e) {message ("Can't find SHA1.");}
    setLayout(new BorderLayout());
    debug ( "start BigNumCalc!" );          //debug
    x = new BigInteger ("0");
    y = new BigInteger ("0");
    modulus = new BigInteger ("0");
//  xDisplay = new TextArea("x=0", 1, 33, TextArea.SCROLLBARS_HORIZONTAL_ONLY); //this works, but I don't like it...
    xDisplay = new TextArea("x=0", 2, 33); // rows, cols 2 rows for Win 98 vert scroll bars
    yDisplay = new TextArea("y=0", 2, 33);  
    status = new TextArea("", 2, 33);
    parenStack = new Stack();
    panel1.setLayout(new GridLayout(3,1));   // Displays
    panel2.setLayout(new GridLayout(keyLabel.length,keyLabel[1].length));   // Buttons (11,5)
    Button tempButton=null;
    int i,j;
    
    for(i=0; i<keyLabel.length; i++)
    {
	for(j=0; j<keyLabel[i].length; j++)
	  {
	    
	    tempButton = new Button(keyLabel[i][j]);
		tempButton.addActionListener(this);
	    panel2.add(tempButton);
	  }
    }
    
    cons.gridx = 0;
    cons.gridy = 0;
    cons.weightx = 1;
    cons.weighty = 1;
    cons.insets = new Insets(10,10,10,10);
   
    GridBagConstraints cons = this.cons;
    cons.insets = new Insets(0,0,0,0);
    cons.gridx = GridBagConstraints.RELATIVE; 
    
    panel1.add(xDisplay);  //was WEST
    panel1.add(yDisplay);
	panel1.add(status);

    add(panel1, BorderLayout.NORTH);
    add(panel2, BorderLayout.SOUTH);
    allClear(); 
    panel2.requestFocus();  //"this" worked better on MRJ 2.1
  }

  public void actionPerformed(ActionEvent e) 
  {
/*  From Sun's secRandom.setSeed documentation: 
			"The given seed supplements, rather than replaces, the existing seed. 
			 Thus, repeated calls are guaranteed never to reduce randomness."  */
 	if (secRandom != null) secRandom.setSeed(new Date().getTime()); 
 	cmd = e.getActionCommand();
 	this.requestFocus(); 	
 	this.validate();	// FIX should it be here?
 	if (buttonCalc ==null) {
 	   buttonCalc = new DoButton(); 
 	   buttonCalc.start();}  // do it!
 	else if (cmd.equals("AC") || cmd.equals("Clear")) {
 	   buttonCalc.stop(); 
 	   buttonCalc = null; 
 	   if (cmd.equals("AC")) {allClear();}
 	   else {clear();} 
 	   waitCursor(false);
 	   debug("buttonCalc.stop");
 	 }
  }
  
  public void allClear()
  {
    xDisplay.setText("x= 0"); 
    x = x.xor(x);  //set to zero
    xDouble = 0.;
    clear();
    try {myApplet.showStatus("Big Number Calculator");} 
    catch (NullPointerException e) { }
//    this.requestFocus();
//    panel2.requestFocus();
  }
  
  public void beep()
  {
    if (myApplet != null) myApplet.showStatus("beeeep");
    debug("beeeep");
  }
  public String bigToFPString(BigInteger z)
  {
    if (z.signum()==0) return ("0.0");
    double log10z = logBig(z.abs())/Math.log(10);
    int exp = (int) Math.floor(log10z);
    double mant = Math.pow(10.0, (log10z - Math.floor(log10z)));
    return ((z.signum()<0 ? "-" : "") 
        + new Double(mant).toString().substring(0,13) + "E" + exp); //13 is experimental limit of acccuracy on e.g. 2**2000
  }
  
  public void clear()
  {
    opMemory = "";
    yDisplay.setText("y=0");
//   y = BigInteger.ZERO  //doesn't work?
    y = y.valueOf(0);  //set to zero
	noDigits = true;
	showCalcInfo(false);
  }
  
  public void debug (String msg)
  {
  	if (debugOn) System.out.println(msg);
  }
  
  public BigInteger doLog (BigInteger target, double divisor)
  {
  	  XdoubleSpecialFlag = true;
  	  xDouble = logBig(target.abs())/divisor;
  	  if (xDouble == 0.0) {beep();}
  	  return BigInteger.valueOf((long)(xDouble));
  }
  
  public void finishY() {
    String stemp;
    try {
    debug("finishY ");
    int yL = yDisplay.getText().length();
    if (yDisplay.getText().substring(0,Math.min(2,yL)).equals("y=")) {
       y = new BigInteger(yDisplay.getText().substring(2).trim(), base);
    }
    else {
       y = new BigInteger(yDisplay.getText().trim(), base);
    }
    if (!yDisplay.getText().substring(0,Math.min(3,yL)).equals("y=0")) {noDigits=false;}
    yDisplay.setText("y=0");
	debug( "finishY1 " + y.toString(base) );  //debug
	}
	catch (NumberFormatException e) {
	message("NumberFormatException");
	}
	catch (IndexOutOfBoundsException e) {
	message("IndexOutOfBoundsException");
	}
  }
  
  public SecureRandom getSecRandom()
  {
  String stemp;
  if (secRandom == null) {
  	stemp = xDisplay.getText();
  	message("This may take a while.");
  	secRandom = new SecureRandom();
  	message("y=0");
  	xDisplay.setText(stemp);
  	}
  return secRandom;
  }
  
  public String getTag(BigInteger z) {
       String tagTemp = new BigInteger(1, myHash.digest(z.toByteArray())).toString(32);
       tagTemp = tagTemp.substring(0,TAGLENGTH/2) + " " 
                 + tagTemp.substring(TAGLENGTH/2,TAGLENGTH);
       return (tagTemp.replace('l','L'));
  }
  
  public void infixCmd(String cm)
  {
    int i, k, n;
  	finishY();
  	String prevcmd = opMemory;
  	debug ("infixCmd0 " + cm + " prevcmd" + prevcmd);  //debug
  	
	debug( "infixCmd1 " + x.toString(base) );  //debug
	
  	if  (prevcmd.equals("") && !noDigits) 
  		{ x = y; debug("infixcmd x=y");}   
  	else if (cm.equals("("))  { }	//do nothing
  	else if (prevcmd.equals("+")) x = x.add(y); 
  	else if (prevcmd.equals("-")) x = x.subtract(y); 
  	else if (prevcmd.equals("*")) x = x.multiply(y); 
  	else if (prevcmd.equals("/") && modulus.signum() == 0) {
  	    if (y.signum() != 0) {
  	  	   if (x.doubleValue() == Double.POSITIVE_INFINITY 
  	  	         || x.doubleValue() == Double.NEGATIVE_INFINITY) {
  	  	       xDouble =  Math.exp(logBig(x) - logBig(y));
  	  	       }
  	  	   else {xDouble = x.doubleValue()/y.doubleValue();}
  	       x = x.divide(y);
  	       XdoubleSpecialFlag = true;
		} 
  	    else  {message("Can't divide by zero."); beep();}
  	   } 
  	else if (prevcmd.equals("/") && modulus.signum() == 1)    {  
       	try {x = x.multiply(y.modInverse(modulus));}
		catch (ArithmeticException e) {message ("Can't divide by this number."); beep();}
		}
	else if (prevcmd.equals("x**y") && modulus.signum() == 0) {
	    if (y.intValue() <= 15*maxFactorial) {x = x.pow(y.intValue());} // 15 is sort of arbitrary, aprox. = log2(10000)
	    else {message("Exponent too big."); beep();} 
	    }
	else if (prevcmd.equals("x**y") && modulus.signum() == 1) x = x.modPow(y,modulus); 
	else if (prevcmd.equals("mod")) x = x.mod(y); 
	else if (prevcmd.equals("gcd")) x = x.gcd(y); 
	else if (prevcmd.equals("and")) x = x.and(y); 
	else if (prevcmd.equals("or")) x = x.or(y); 
	else if (prevcmd.equals("xor")) x = x.xor(y); 
    else if (prevcmd.equals("ClearBit")) {x = x.clearBit(y.intValue());}
    else if (prevcmd.equals("SetBit")) {x = x.setBit(y.intValue()); debug("SetBit");}
    else if (prevcmd.equals("xCy")) {            // Binomial coefficient
    			n = Math.abs(x.intValue()); 
    			k = Math.abs(y.intValue()); 
  				if (n>maxFactorial || n<=0 || k>n || k<0) {
  				   x=BigInteger.valueOf(0); 
  				   message("Argument out of range."); beep();
  				} 
  				else {
  				   /* begin at n-1 because x starts out as n */
  				   for(i=n-1;i>=n-k+1;i--){x=x.multiply(BigInteger.valueOf(i));} 
  				   for(i=2;i<=k;i++){x=x.divide(BigInteger.valueOf(i));} 
  				}
  			}
			
	if (modulus.signum() == 1) x = x.mod(modulus);
	debug( "infixCmd2 " + x.toString(base) + opMemory);  //debug
  	noDigits = true;
  	if (cm.equals("=")) opMemory = "";
  	else if (cm.equals("(")) { 
 	   parenStack.push(x); 
//     parenStack.push(x.shiftLeft(0));  //FIX Hack to get a new BigInteger--don't need?
  	   parenStack.push(opMemory); 
	   debug("'(' " + x + opMemory);
  	   x = x.xor(x);  //set to zero
  	   opMemory = "";
    }
	else if (cm.equals(")")) { 
	   if (!parenStack.empty()) {
	      opMemory = (String) parenStack.pop(); 
	      y=x;
	      noDigits=false;
	      x=(BigInteger)parenStack.pop();
	      showY();
		  debug("')' " + x + opMemory);
	   }
	   else {opMemory = "";}
	} 
	else {opMemory = cm;}
  	showX(opMemory.equals("")); //check primality only at end of calculation
  }
  
  public void keyPressed(KeyEvent e) {
   }
  
  public void keyReleased(KeyEvent e) {
        String OKKEYS = "()+-*/=!";
 	    //See comment at actionPerformed:
 	    if (secRandom != null) secRandom.setSeed(new Date().getTime()); 
        char c = e.getKeyChar();
        String keyCmd = String.valueOf(c);
        int keyCode = e.getKeyCode();
        int modif = e.getModifiers();  
        if (debugOn && myApplet!=null) myApplet.showStatus("keyPressed: "+ String.valueOf(c) 
          + " modif " + modif + " Code " + keyCode);  /* DEBUG temp to check key codes */
        debug("keyReleased: "+ String.valueOf(c) 
               + " modif " + modif + " Code " + keyCode);
        boolean metaKey = ((modif&metaKeyCode) == metaKeyCode);       
        if (keyCode==8) {cmd = "del";}  			//Delete key (Mac), Bksp key (PC)
        else if (keyCode==10) {cmd = "=";}   		//Enter or Return key
        else if (keyCode==27) {cmd = "AC";}  		//Esc key
        else if (keyCode==12) {cmd = "Clear";}     	//Clear key (Mac)

		else if (metaKey && keyCode==65) { cmd = "About"; }  	// "a" 
		else if (metaKey && keyCode==83) { cmd = "Store"; }  	// "s"
		else if (metaKey && keyCode==82) { cmd = "Recall"; }  	// "r"
		else if (metaKey && keyCode==87) { cmd = "log2"; }  	// "w"
		else if (metaKey && keyCode==69) { cmd = "ln"; }  		// "e"
		else if (metaKey && keyCode==84) { cmd = "log10"; }  	// "t"
		else if (metaKey && keyCode==71) { cmd = "GetMod"; }  	// "g"
		else if (metaKey && keyCode==72) { cmd = "SHA1"; }  	// "h"
		else if (metaKey && keyCode==67) { cmd = "xCy"; }   	// "c"
		else if (metaKey && keyCode==88) { cmd = "x<->y"; }  	// "x"
		else if (metaKey && keyCode==89) { cmd = "Clear"; }  	// "y"
		else if (metaKey && keyCode==77) { cmd = "SetMod"; }  	// "m"
		else if (metaKey && keyCode==66) { cmd = "SetBase"; }  	// "b"
		else if (metaKey && keyCode==78) { cmd = "Next p"; }  	// "n"
		else if (metaKey && keyCode==80) { cmd = "Rand p"; }   	// "p"
		else if (metaKey && keyCode==68) { cmd = "gcd"; }  		// "d"
		else if (metaKey && keyCode==73) { cmd = "1/x"; }  		// "i"

		else if (keyCmd.equals("&")) { cmd = "and"; }  
		else if (keyCmd.equals("|")) { cmd = "or"; }  
		else if (keyCmd.equals("^")) { cmd = "xor"; }  
		else if (keyCmd.equals("~")) { cmd = "not"; }  
		else if (keyCmd.equals("%")) { cmd = "mod"; } 
		else if (keyCmd.equals(":")) { cmd = "SetBit"; }  
		else if (keyCmd.equals(";")) { cmd = "ClearBit"; }  
		else if (keyCmd.equals("#")) { cmd = "Random"; }  
		else if (keyCmd.equals("@")) { cmd = "x**y"; }  
		else if (keyCmd.equals("\\")){ cmd = "+/-"; }  		    // "\"
		else if (keyCmd.equals("[")) { cmd = "("; }  
		else if (keyCmd.equals("]")) { cmd = ")"; }  
		else if (keyCmd.equals("{")) { cmd = "("; }  
		else if (keyCmd.equals("}")) { cmd = ")"; } 

		else if (validDigit(keyCmd)) {cmd = keyCmd;}
        else if (OKKEYS.indexOf(c)>=0) {cmd = keyCmd;}
		else {cmd = ""; return;} 

        if (buttonCalc ==null) {
 	       buttonCalc = new DoButton(); 
 	       buttonCalc.start(); // do it!
 	    }  

//		this.validate();
  }
    
  public void keyTyped(KeyEvent e) {
  }  
  
  public void message(String msg)
  {
//   applet.showStatus(msg);
   yDisplay.setText(msg);
   debug( "Message: " + msg );  //agr debug
   return;
  } 
  
  public double logBig(BigInteger z) { 
//   Needed because Math.log(z.doubleValue()) returns infinite for z bigger than MAX DOUBLE
     BigInteger b;
     int temp = z.bitLength() - 1000;
     if (temp > 0) {
//      b=z.divide(BigInteger.valueOf(2).pow(temp)); //replaced with below
        b=z.shiftRight(temp); 
        return (Math.log(b.doubleValue()) + temp*Math.log(2));
     }
     else {return (Math.log(z.doubleValue()));}
  }
  
  public void numericCmd(String cm)
  {
	if (Integer.parseInt(cm, Character.MAX_RADIX) < base) {
		debug( "cmd " + cm );  //agr debug
		if (noDigits) {
			yDisplay.setText("y=" + cm);
			noDigits = false;
			}
		else yDisplay.setText(yDisplay.getText() + cm);
	}
	else {beep();}	
  }
  
  public void showCalcInfo(boolean testPrimality){			
	debug( "showCalcInfo " );  //debug
	String floatString = "";
	String bitsString = x.bitLength() + " bits,";
	String primeString = "";
	String tag = "";
	
	if (modulus.signum()==0 && testPrimality) {
	   	primeString = (x.isProbablePrime(primeCert) ? " a prime," : " non-prime,"); 
	   	}                

	String baseString = " base " + new Integer(base).toString();

	String modString = "";
	if (modulus.signum()==1) {
	   modString = " " + modulus.toString();
	   if (modString.length() > MODDISPLAYLENGTH) 
	      {modString = ":tag=" + getTag(modulus);}
	   modString = ", modulo" + modString;
	}

	String parenString = (parenStack.size()>0 ? ", (" + parenStack.size()/2 : "");
	String opString = " " + opMemory;
	
	if (modulus.signum()==0) {
	   if (xDouble == Double.POSITIVE_INFINITY || xDouble == Double.NEGATIVE_INFINITY) {
	       floatString = "x= " + bigToFPString(x) + " Decimal";
	       }
	   else {floatString = "x= " + new Double(xDouble).toString() + " decimal";}
	}
//  String memString = Runtime.getRuntime().freeMemory() + " bytes free, "; /* not meaningful on IE 5 */

    if (myHash != null && Math.abs(xDouble) > TAGMIN) {
        tag = " tag=" + getTag(x);
       }
	
	status.setText( bitsString + primeString 
	                 + baseString + modString + parenString + opString 
	                 + "\n" + floatString + tag);
  }
  
  public void showX(boolean testPrimality){			
	debug( "showX " + x.toString(base) );  //debug
	xDisplay.setText("x=" + x.toString(base));
	if (!XdoubleSpecialFlag) xDouble=x.doubleValue();
	XdoubleSpecialFlag = false;
	showCalcInfo(testPrimality);
	waitCursor(false);	
  }
  
  public void showY(){			
	debug( "showY " + y.toString(base) );  //debug
	yDisplay.setText("y=" + y.toString(base));
  }
  
  public void unaryCmd(String cm)
  {
  int i, itemp;
  BigInteger target;
  debug ("unaryCmd " + cm);   //debug
  if (noDigits) target = x;
  else {finishY(); target = y;}
  if (cm.equals("+/-")) {target = target.negate();}
  else if (cm.equals("not")) {target = target.not();}
  else if (cm.equals("Next p")) {
        target=target.add(BigInteger.valueOf(1));
        target = target.setBit(0);  //Set odd
        while (!target.isProbablePrime(primeCert)) {
            target = target.add(BigInteger.valueOf(2)); debug(target.toString());
            }
        }
  else if (cm.equals("SetBase")) {
  		itemp = Math.abs(target.intValue()); 
  		target=x;
  		if (itemp==0 || itemp==base) base = 10;  //If the user typed "10", set decimal
  		else if (itemp>=2 && itemp<=Character.MAX_RADIX) base=itemp; 
  		else beep();
  		clear(); 
  		}
  else if (cm.equals("SetMod")) {
  	if (target.compareTo(BigInteger.valueOf(0)) >= 0 
  	      && target.compareTo(BigInteger.valueOf(1)) != 0) 
  	      {modulus = target; target=x; clear();} 
  	else message("Invalid modulus."); beep();}  
  else if (cm.equals("GetMod")) {target = modulus; clear();}
  else if (cm.equals("!")) {itemp = Math.abs(target.intValue()); 
  				if (itemp>maxFactorial || itemp<=0) {
  				   itemp=1; message("Factorial out of range"); beep();
  				   } 
  				/* terminate at itemp-1 because target starts as itemp */
  				for(i=2;i<itemp;i++){target=target.multiply(BigInteger.valueOf(i));} 
 				} 
  else if (cm.equals("1/x")) {
     if (modulus.signum()==0) {
       	 if (target.signum() != 0) {
  	  	   xDouble = 1.0/target.doubleValue();
  	       target = BigInteger.valueOf(1).divide(target);
  	       XdoubleSpecialFlag = true;
  	       }
     }
     else {  
       	try {target = target.modInverse(modulus);}
		catch (ArithmeticException e) {message ("There is no inverse."); beep();}
		}
  }
  else if (cm.equals("log2")) {target = doLog(target, Math.log((double)2.0));}
  else if (cm.equals("ln")) {target = doLog(target, (double)1.0);}
  else if (cm.equals("log10")) {target = doLog(target, Math.log((double)10.0));}
  else if (cm.equals("Random")) {
        if (y.intValue()>1) {target = new BigInteger (y.intValue(), getSecRandom());} 
        else {message("How many bits?");}
        }
  else if (cm.equals("Rand p")) {
        if (y.intValue()>1) {target = new BigInteger (y.intValue(), primeCert, getSecRandom());} 
        else {message("How many bits?");}
        }
  else if (cm.equals("SHA1")) {
   			if (myHash != null) target = new BigInteger(1, myHash.digest(target.toByteArray()));  //forces positive number
 			debug("SHA1 " + myHash.digest(target.toByteArray()).toString()); //debug
		    }
		    
  if (noDigits || opMemory.equals("")) {x = target; showX(true); noDigits=true;}  
  else {y = target; showY();}  
  }
  
 
  public void swapXY()
  {
     BigInteger temp;
     finishY();
     temp = x; x = y; y = temp;
     showX(false); showY();
  }
  
  public boolean validDigit(String cmd) {
     if (cmd.length() == 1) {
        char c = cmd.charAt(0);
 	    if (('0' <= c && c <= '9') || ('a' <= c && c <= 'z')|| ('A' <= c && c <= 'Z')) {return(true);}
 	 }
 	 return(false);
  }
  	
  public void waitCursor (boolean on) 
  {
     if (on) {
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));  //FIX try applet.setCursor
        xDisplay.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        yDisplay.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        status.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        }
	 else {
	    this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    xDisplay.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    yDisplay.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    status.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    }
  }

class DoButton extends Thread {   
   public void run() {    
     debug("DoButton.run");
 	 waitCursor(true);
	 if (validDigit(cmd)) { numericCmd(cmd); }
		else if (cmd.equals("(")) { infixCmd(cmd); }
		else if (cmd.equals(")")) { infixCmd(cmd);} 
		else if (cmd.equals("*")) { infixCmd(cmd); }
		else if (cmd.equals("x**y")) { infixCmd(cmd); }
		else if (cmd.equals("+")) { infixCmd(cmd); }
		else if (cmd.equals("+/-")) { unaryCmd(cmd); }
		else if (cmd.equals("!")) { unaryCmd(cmd); }
		else if (cmd.equals("-")) { infixCmd(cmd); }
		else if (cmd.equals("/")) { infixCmd(cmd); }
		else if (cmd.equals("=")) { infixCmd(cmd); }
		else if (cmd.equals("AC")) { allClear(); }
		else if (cmd.equals("and")) { infixCmd(cmd); }
		else if (cmd.equals("Clear")) { clear(); }
		else if (cmd.equals("ClearBit")) { infixCmd(cmd); }
		else if (cmd.equals("del")) 
		   {if (!noDigits && yDisplay.getText().length()>0) 
		          yDisplay.setText(yDisplay.getText().substring
			              (0,yDisplay.getText().length() - 1)); 
			  if (yDisplay.getText().equals("y=")) {noDigits=true; yDisplay.setText("y=0");}
			  }
		else if (cmd.equals("gcd")) { infixCmd(cmd); }
		else if (cmd.equals("GetMod")) { unaryCmd(cmd);}
		else if (cmd.equals("1/x")) { unaryCmd(cmd); }
		else if (cmd.equals("ln")) { unaryCmd(cmd); }
		else if (cmd.equals("log10")) { unaryCmd(cmd); }
		else if (cmd.equals("log2")) { unaryCmd(cmd); }
		else if (cmd.equals("mod")) { infixCmd(cmd); }
		else if (cmd.equals("Next p")) { unaryCmd(cmd); }
		else if (cmd.equals("not")) { unaryCmd(cmd); }
		else if (cmd.equals("or")) { infixCmd(cmd); }
		else if (cmd.equals("xCy")) { infixCmd(cmd); }
		else if (cmd.equals("SHA1")) { unaryCmd(cmd); }
		else if (cmd.equals("Rand p")) { unaryCmd(cmd); }
		else if (cmd.equals("Random")) { unaryCmd(cmd); }
		else if (cmd.equals("Recall")) {
			// maybe should use copy method, but works because computations create new x 
			if (memory != null) {
		  		if (opMemory.equals("")) {x=memory; showX(false);}  
		  		else {y=memory; showY();}
		  		}
		   }
		else if (cmd.equals("About")) { clear();  yDisplay.setText(aboutString); }
		else if (cmd.equals("SetBit")) { infixCmd(cmd); }
		else if (cmd.equals("SetBase")) { unaryCmd(cmd); }
		else if (cmd.equals("SetMod")) { unaryCmd(cmd); }
		else if (cmd.equals("Store")) { 
		  if (noDigits) {memory = x;}
		  else {finishY(); memory = y; showY();}
		 }
		else if (cmd.equals("x<->y")) { swapXY(); }
		else if (cmd.equals("xor")) { infixCmd(cmd); }
		
	    waitCursor(false);
	    buttonCalc = null; 
	   } 
   }   
 
}


