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

import org.springframework.beans.factory.support.MethodReplacer;

import com.example.spring.util.Output;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;

/**
 * The Method Replacer
 * 
 */
public class Replacer extends OutputBean implements MethodReplacer {

	/**
	 * This is the method replacing calls to printTimeBean.
	 */
	public Object reimplement(Object o, Method m, Object[] a) throws Throwable {
		outputCalledMethod();
		TimeBean tb = (TimeBean) (a[0]);
		if (tb != null) {
			int id = tb.getCounter();
			long time = tb.getTime();
			Output.println("TimeBean ID #"+id+" was created at: "+df.format(new Date(time)));
		} else {
			Output.println("Bean was null");
		}
		return null;
	}
	
	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
}