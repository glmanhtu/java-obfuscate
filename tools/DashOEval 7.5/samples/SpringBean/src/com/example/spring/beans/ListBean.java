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

import java.util.List;

/**
 * A bean with a classnames in a list variable
 * 
 */
public class ListBean extends OutputBean {

	/**
	 * Constructs the bean
	 * 
	 * @param name
	 *            The classname list
	 */
	public ListBean(List name) {
		super();
		this.classNames = name;
	}

	/**
	 * Constructs the bean with a null classname list
	 */
	public ListBean() {
		this(null);
	}

	/**
	 * Gets the class names
	 * 
	 * @return The class names
	 */
	public List getClassNames() {
		return classNames;
	}

	/**
	 * Sets the class names
	 * 
	 * @param names
	 *            The class names
	 */
	public void setClassNames(List names) {
		this.classNames = names;
	}

	/**
	 * Instantiates the classes referenced by the list and calls the greeting
	 * methods.
	 */
	@Override
	public void output() {
		super.output();
		if (classNames != null) {
			for (Object o : classNames) {
				String classname = o.toString();
				try {
					IGreeting ig = (IGreeting) Class.forName(classname)
							.newInstance();
					Output.println("Instantiating class: '" + classname + "'");
					ig.greeting("ListBean");
				} catch (Exception e) {
					Output.println("Error finding class: " + classname);
				}
			}
		} else {
			Output.println("classNames is NULL");
		}
	}

	private List classNames;
}