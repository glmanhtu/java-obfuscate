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
 * A bean with a parent bean reference
 * @author minsko
 *
 */
public class ChildBean extends OutputBean {

	
	
	public int getNum() {
		outputCalledMethod();
		return num;
	}
	public void setNum(int num) {
		outputCalledMethod();
		this.num = num;
	}
	public String getStr() {
		outputCalledMethod();
		return str;
	}
	public void setStr(String str) {
		outputCalledMethod();
		this.str = str;
	}
	public float getFlt() {
		outputCalledMethod();
		return flt;
	}
	public void setFlt(float flt) {
		outputCalledMethod();
		this.flt = flt;
	}

	@Override
	public void output() {
		super.output();
		int n = getNum();
		Output.println("num: " + n);
		float f = getFlt();
		Output.println("flt: " + f);
		String s = getStr();
		Output.println("str: " + s);		
	}
	
	private int num;
	private String str;
	private float flt;
}
