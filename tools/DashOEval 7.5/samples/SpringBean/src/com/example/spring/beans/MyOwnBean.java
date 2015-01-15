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
 * A bean to show life cycle. Methods print their name when called.
 * 
 */
public class MyOwnBean extends OutputBean {

	public MyOwnBean() {
		super();
	}

	public void my_init() {
		outputCalledMethod();
		p_my_init();
	}

	private void p_my_init() {
		outputCalledMethod();
	}

	public void my_destroy() {
		outputCalledMethod();
		p_my_destroy();
	}

	private void p_my_destroy() {
		outputCalledMethod();
	}

	public void def_init() {
		outputCalledMethod();
		p_def_init();
	}

	private void p_def_init() {
		outputCalledMethod();
	}

	public void def_destroy() {
		outputCalledMethod();
		p_def_destroy();
	}

	private void p_def_destroy() {
		outputCalledMethod();
	}

}
