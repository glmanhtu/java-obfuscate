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


/**
 * A bean using a lookup method and has a method replaced
 * 
 */
public class MyLookupReplacedBean extends OutputBean {

	/**
	 * Constructor
	 * 
	 * @param count
	 *            The number of Time Beans to request
	 */
	public MyLookupReplacedBean(int count) {
		this.count = count;
	}

	/**
	 * Creates a time bean. This method will be replaced by Spring (in on example)
	 * 
	 * @return a new time bean
	 */
	protected TimeBean newTimeBean() {
		outputCalledMethod();
		return new TimeBean();
	}

	/**
	 * Prints out the TimeBean information
	 */
	@Override
	public void output() {
		TimeBean tbs[] = new TimeBean[count];
		for (int i = 0; i < count; i++) {
			tbs[i] = newTimeBean();
		}
		for (TimeBean tb: tbs) {
			printTimeBean(tb);
		}
	}

	/**
	 * Prints out the information. This method will be replaced by Spring
	 * 
	 * @param tb
	 *            The bean to print
	 */
	protected void printTimeBean(TimeBean tb) {
		outputCalledMethod();
		tb.output();
	}

	private int count;
}