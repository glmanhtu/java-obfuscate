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
 * A bean to show life cycle. Methods print their name when called.
 * 
 */
public class MyFactoryBean extends OutputBean {
	public MyFactoryBean(String ID) {
		super();
	}

	public void my_init() {
		outputCalledMethod();
	}

	public void my_init2() {
		outputCalledMethod();
	}

	public void my_init3() {
		outputCalledMethod();
	}

	public void my_destroy() {
		outputCalledMethod();
	}

	public void my_destroy2() {
		outputCalledMethod();
	}

	public void my_destroy3() {
		outputCalledMethod();
	}

	public static MyFactoryBean my_factory() {
		Output.printCalledMethod(internalClassName);
		return new MyFactoryBean("1");
	}

	public static MyFactoryBean my_factory2() {
		Output.printCalledMethod(internalClassName);
		return new MyFactoryBean("2");
	}

	public static MyFactoryBean my_factory3() {
		Output.printCalledMethod(internalClassName);
		return new MyFactoryBean("3");
	}

	private static final String internalClassName;
	static {
		internalClassName = MyFactoryBean.class.getName();
	}

}
