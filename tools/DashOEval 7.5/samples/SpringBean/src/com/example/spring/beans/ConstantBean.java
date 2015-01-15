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

import com.example.spring.other.MyConstants;
//import static com.example.spring.other.MyConstants.QUESTION_2;
//import static com.example.spring.other.MyConstants.ANSWER_3;
import com.example.spring.util.Output;


/**
 * A bean used initialized with constants from the MyConstants interface.
 */
public class ConstantBean extends OutputBean implements MyConstants {
	
	public ConstantBean() {
		this(QUESTION_2, ANSWER_3);
	}
	
	public ConstantBean(String question, int answer) {
		super();
		this.question = question;
		this.answer = answer;
	}

	/**
	 * Outputs the values of each property
	 */
	@Override
	public void output() {
		super.output();
		String ques = getQuestion();
		Output.println("Question: " + ques);
		int ans = getAnswer();
		Output.println("Answer: " + ans);
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public int getAnswer() {
		return answer;
	}
	
	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public String question;	
	private int answer;


}
