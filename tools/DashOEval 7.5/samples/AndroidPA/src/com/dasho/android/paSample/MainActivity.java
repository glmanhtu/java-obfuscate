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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.preemptive.annotation.instrumentation.Application;
import com.preemptive.annotation.instrumentation.ApplicationStart;
import com.preemptive.annotation.instrumentation.ApplicationStop;
import com.preemptive.annotation.instrumentation.Company;
import com.preemptive.annotation.instrumentation.ReportUncaughtExceptions;
import com.preemptive.annotation.instrumentation.SystemProfile;

/**
 * The main screen for this app
 *  @author Matt Insko
 */
@Company(id="11111111-aaaa-bbbb-cccc-555555555555", name="PreEmptive Solutions")
@Application(id="00000000-0000-cccc-0000-000000000000", name="Android Sample PA App", type="Sample", version="1.0")
public class MainActivity extends Activity implements OnClickListener {
	private boolean initialized=false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.GenActBtn).setOnClickListener(this);
        findViewById(R.id.FibActBtn).setOnClickListener(this);
        findViewById(R.id.SysInfoBtn).setOnClickListener(this);
        findViewById(R.id.SendErrorBtn).setOnClickListener(this);
		if (!initialized) {
			initialize();
		}
    }
    
    /**
     * Application start is attached here, since it is guaranteed to only happen once (unlike onCreate())
     */
    @ApplicationStart
	private void initialize() {
		initialized=true;
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    

    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId()==R.id.menu_about) {
			showDialog(R.string.abtTitle, R.string.abtMsg);
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * Handles the button clicks
	 * @param v The view clicked
	 */
    public void onClick(View v) {
		switch (v.getId()) {
		case R.id.GenActBtn:
			startActivity(new Intent(getApplicationContext(), RandomGenActivity.class));
			break;
		case R.id.FibActBtn:
			startActivity(new Intent(getApplicationContext(), FibActivity.class));
			break;
		case R.id.SysInfoBtn:
			sysProfile();
			break;
		case R.id.SendErrorBtn:
			throwError();
			break;
		}
	}
	
	
	/**
	 * Sends the system profile
	 */
    @SystemProfile
	private void sysProfile() {
		Toast.makeText(getApplicationContext(), R.string.profSent, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Throws an error (after a prompt)
	 */
    private void throwError() {
		final RuntimeException re =  new RuntimeException("Uncaught Error");
		DialogInterface.OnClickListener lstn = new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				throwIt(re);			
			}
		};
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
		dlgAlert.setTitle(R.string.errThrown);
		dlgAlert.setMessage(re.toString());
		dlgAlert.setPositiveButton(Resources.getSystem().getText(android.R.string.ok), lstn);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();	
	}	

	/**
	 * The exception will be caught, a message send and the exception class variable set to the exception
	 * @param e The exception being thrown
	 */
    @ReportUncaughtExceptions(ignore=true, sendMessage=true, action="catchIt()")
	private void throwIt(RuntimeException e) {
		throw e;
	}
    
    @Override
	protected void onDestroy() {
		if (isFinishing()) {
			appStop();
		}
		super.onDestroy();
	}
    
    @ApplicationStop
	private void appStop() {
	}

    private void catchIt(Throwable t) {
		Toast.makeText(getApplicationContext(), R.string.errCaught, Toast.LENGTH_SHORT).show();    	
    }
    
    /**
     * Shows a dialog
     * @param title The title id
     * @param message The message id
     */
    private void showDialog(int title, int message) {
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
		dlgAlert.setTitle(title);
		dlgAlert.setMessage(message);
		dlgAlert.setPositiveButton(Resources.getSystem().getText(android.R.string.ok), null);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();	
    }
}
