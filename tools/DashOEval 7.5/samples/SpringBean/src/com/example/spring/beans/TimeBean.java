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
 * A bean which holds the time of construction and a number representing order
 * 
 */
public class TimeBean extends OutputBean{

	/**
	 * The contructor
	 */
	public TimeBean() {
		this.time = System.currentTimeMillis();
		this.counter = ++COUNT;
	}

	/**
	 * Gets the time the bean was created
	 * 
	 * @return The time
	 */
	public long getTime() {
		outputCalledMethod();
		return time;
	}

	/**
	 * Gets the order in which the bean was created
	 * 
	 * @return The order
	 */
	public int getCounter() {
		outputCalledMethod();
		return counter;
	}
	
	

	@Override
	public void output() {
		super.output();
		outputCalledMethod();
		Output.println("ID = "+getCounter());
		Output.println("Time = "+getTime());
	}



	private long time;
	private int counter = 0;
	private static int COUNT = 0;
}