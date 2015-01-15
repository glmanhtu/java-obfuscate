/*Copyright (c) 1997-2014 PreEmptive Solutions, LLC. All Rights Reserved.
 *
 * This software is property of PreEmptive Solutions, Inc. Duplication,
 * Reverse Engineering, and/or Decompilation is strictly prohibited.
 *
 * PREEMPTIVE SOLUTIONS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. PREEMPTIVE SOLUTIONS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.example.spring.beans;

import com.example.spring.other.IGreeting;
import com.example.spring.util.Output;

/**
 * A bean with a classname as a variable
 * 
 */
public class ClassBean extends OutputBean {

	/**
	 * Constructs the bean
	 * 
	 * @param name
	 *            The class name
	 */
	public ClassBean(String name) {
		super();
		this.classname = name;
	}

	/**
	 * Constructs the bean with a null class name
	 */
	public ClassBean() {
		this(null);
	}

	/**
	 * Gets the class name
	 * 
	 * @return The class name
	 */
	public String getClassName() {
		return classname;
	}

	/**
	 * Sets the class name
	 * 
	 * @param name
	 *            The class name
	 */
	public void setClassName(String name) {
		this.classname = name;
	}

	/**
	 * Instantiates the class referenced by class name and calls the greeting
	 * method.
	 */
	@Override
	public void output() {
		super.output();
		try {
			IGreeting ih = (IGreeting) Class.forName(classname).newInstance();
			Output.println("Instantiating class: '" + classname + "'");
			ih.greeting("World!");
		} catch (Exception e) {
			Output.println("Error finding class: " + classname);
		}
	}

	private String classname;
}