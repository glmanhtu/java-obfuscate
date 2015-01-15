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
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.preemptive.annotation.instrumentation.FeatureTick;

/**
 * The Random Number Generator
 * @author Matt Insko
 *
 */
public class RandomGenActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_main);
        findViewById(R.id.genBtn).setOnClickListener(this);
        minNum = (EditText) findViewById(R.id.rndMinNum);
        maxNum = (EditText) findViewById(R.id.rndMaxNum);
        genNum = (TextView) findViewById(R.id.genNumber);
    }

    /**
     * Processes the button click
     */
    public void onClick(View v) {
		if (v.getId() == R.id.genBtn) {
			genNum.setText("");
			try {
				minStr = minNum.getText().toString();
				int minInt = Integer.parseInt(minStr);
				maxStr = maxNum.getText().toString();
				int maxInt = Integer.parseInt(maxStr);
				if (maxInt <= minInt) {
					Toast.makeText(getApplicationContext(), R.string.minMaxErr,
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (minInt < 0 || maxInt < 0) {
					Toast.makeText(getApplicationContext(), R.string.badNum,
							Toast.LENGTH_SHORT).show();
				}
				genNum.setText(nf.format(findRnd(minInt, maxInt)));
			} catch (NumberFormatException e) {
				AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
				dlgAlert.setMessage(R.string.badNum);
				dlgAlert.setTitle(R.string.errTitle);
				dlgAlert.setPositiveButton(Resources.getSystem().getText(android.R.string.ok), null);
				dlgAlert.setCancelable(true);
				dlgAlert.create().show();			
			}
		}
    }
    
    
    /**
     * Saves the request
     */
    @Override
	protected void onPause() {
		super.onPause();
		SharedPreferences prefs = getSharedPreferences("RndPrefs", MODE_PRIVATE);
		Editor prefEditor = prefs.edit();
		prefEditor.putString("min", minStr);
		prefEditor.putString("max", maxStr);
		prefEditor.apply();
	}

	/**
	 * Restores the last request
	 */
	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences prefs = getSharedPreferences("RndPrefs", MODE_PRIVATE);
		minStr = prefs.getString("min", "1");
		maxStr = prefs.getString("max", "100");
		minNum.setText(minStr);
		minNum.setSelection(minStr.length());
		maxNum.setText(maxStr);
		maxNum.setSelection(maxStr.length());
	}

	/**
	 * Finds the random number
	 * @param min The minimum
	 * @param max The maximum
	 * @return The random number
	 */
	@FeatureTick("FindRandom")
    private long findRnd(int min, int max) {
		int range = max-min+1;
		return ((int) (rnd.nextDouble() * range)) + min;
    }
    
	private String minStr, maxStr;
    private NumberFormat nf = NumberFormat.getIntegerInstance();
    private EditText minNum, maxNum;
    private TextView genNum;
    private Random rnd = new Random(System.currentTimeMillis());
}
