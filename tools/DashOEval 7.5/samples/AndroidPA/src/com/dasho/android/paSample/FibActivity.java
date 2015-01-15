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
package com.dasho.android.paSample;

import java.text.NumberFormat;
import java.util.Properties;

import com.preemptive.annotation.instrumentation.ReportUncaughtExceptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The Fibonacci calculator
 * @author Matt Insko
 *
 */
public class FibActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fib_main);
        findViewById(R.id.calcFibBtn).setOnClickListener(this);
        seqNum = (EditText) findViewById(R.id.fibSeqNum);
        fibNum = (TextView) findViewById(R.id.calcFibRes);
    }


	/**
	 * Processes the button click
	 */
    public void onClick(View v) {
		if (v.getId() == R.id.calcFibBtn) {
			processFibRequest();
		}
	}

	/**
	 * Processes the request 
	 * Add a @ReportUncaughtExceptions(ignore=true, sendMessage=true) virtual annotation.
	 */
    private void processFibRequest() {
    	if (getResources().getString(R.string.fibCalcProc).equals(fibNum.getText())) {
			Toast.makeText(getApplicationContext(), R.string.fibProc, Toast.LENGTH_SHORT).show();
			return;
    	}
		fibNum.setText(R.string.fibCalcProc);
		try {
			num = seqNum.getText().toString();
			int seq = Integer.parseInt(num);
			if (seq > 40) {
				num = "40";
				Toast.makeText(getApplicationContext(), R.string.fibTooLarge, Toast.LENGTH_SHORT).show();
				fibNum.setText("");
				throw new IllegalArgumentException("Too Large");
			} else if (seq < 1) {
				showNumberError();
				return;
			} else if (seq > 30) {
				Toast.makeText(getApplicationContext(), R.string.fibLarge, Toast.LENGTH_SHORT).show();
			}
			findFib();
		} catch (NumberFormatException e) {
			showNumberError();
		}
	}
    
    
    /**
     * Shows an error
     */
    private void showNumberError() {
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
		dlgAlert.setMessage(R.string.badNum);
		dlgAlert.setTitle(R.string.errTitle);
		dlgAlert.setPositiveButton(Resources.getSystem().getText(android.R.string.ok), null);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
		fibNum.setText("");
    }
	
	/**
	 * Finds the Fibonacci number at that sequence index
	 */
    private void findFib() {
		FibTask task = new FibTask();
		task.execute(Integer.valueOf(num));
	}
	

    
    /**
     * Saves the request
     */
    @Override
	protected void onPause() {
		super.onPause();
		SharedPreferences prefs = getSharedPreferences("FibPrefs", MODE_PRIVATE);
		Editor prefEditor = prefs.edit();
		prefEditor.putString("seq", num);
		prefEditor.apply();
	}

	/**
	 * Restores the last request
	 */
    @Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = getSharedPreferences("FibPrefs", MODE_PRIVATE);
		num = prefs.getString("seq", getString(R.string.fibSeqDef));
		seqNum.setText(num);
		seqNum.setSelection(num.length());
	}

	String num="";
    private TextView fibNum;
    private EditText seqNum;
    
    /**
     * An async task to calculate the number.
     * @author Matt Insko
     *
     */
    private class FibTask extends AsyncTask<Integer, Void, Integer> {

		private int seqNum;
    	
		/**
		 * Add a FeatureStart("Fibonacci") virtual annotation
		 */
		@Override
		protected Integer doInBackground(Integer... params) {
    		seqNum = params[0].intValue();
			return Integer.valueOf(getFib(seqNum));
		}
		
    	
    	@SuppressWarnings("unused")
		private Properties getFeatureInfo() {
    		Properties props = new Properties();
    		props.setProperty("requested_num", Integer.toString(seqNum));
    		return props;
    	}
		
		
	    /**
	     * Calculates the Fibonacci number at a certain location
	     * @param num The location
	     * @return The Fibonacci number at that location
	     */
		private int getFib(int num) {
	    	if (num < 3) {
	    		return 1;
	    	} else {
	    		return getFib(num -1) + getFib(num - 2);
	    	}
	    }

		/**
		 * Add a FeatureStop("Fibonacci") virtual annotation
		 */
		@Override
		protected void onPostExecute(Integer result) {
			fibNum.setText(nf.format(result));
		}

	    private NumberFormat nf = NumberFormat.getIntegerInstance();
    	
    }
}
