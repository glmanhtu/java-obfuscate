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

import com.example.spring.util.Output;

/**
 * A Bean which can output data
 *
 */
public class OutputBean {

	/**
	 * Constructs the bean.
	 */
	public OutputBean() {
		this.name = getClass().getName();
	}

	/**
	 * Output data about the bean
	 */
	public void output() {
		Output.println("Name: " + name);
	}

	/**
	 * Output the calling method.
	 */
	protected void outputCalledMethod() {
		Output.println(name + "." + Output.getCallingMethodName() + "() called.");
	}

	protected final String name;
}
