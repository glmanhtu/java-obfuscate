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
package dasho.samples;

import java.util.Properties;
import com.preemptive.instrumentation.*;

/**
 * 
 * A basic example using the API
 *
 */
public class InstrumentationApi {

	public static void main(String args[]) {
		System.out.println("Instrumentation API Sample");		
		RuntimeIntelligence
				.setCompanyId("11111111-aaaa-bbbb-cccc-555555555555");
		RuntimeIntelligence.setCompanyName("PreEmptive Solutions");
		RuntimeIntelligence
				.setApplicationId("00000000-0000-eeee-0000-000000000000");
		RuntimeIntelligence.setApplicationName("InstrumentationApi");
		RuntimeIntelligence.setApplicationVersion("1.0.0.0");
		RuntimeIntelligence.startApplication();
		InstrumentationApi app = new InstrumentationApi();
		app.featureOne(100);
		app.featureOne(1000);
		app.featureTwo();
		app.profile();
		app.errorMessageThrown();
		app.errorMessageCaught();
		RuntimeIntelligence.stopApplication();
	}

	private InstrumentationApi() {
	}

	private void featureOne(long time) {
		RuntimeIntelligence.featureStart("Feature One");
		System.out.println("Feature One...");
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			// Ignore
		}
		RuntimeIntelligence.featureStop("Feature One");
	}

	private void featureTwo() {
		RuntimeIntelligence.featureTick("Feature Two");
		System.out.println("Feature Two...");
	}

	private void profile() {
		RuntimeIntelligence.systemProfile(getProps());
		System.out.println("System Profile...");
	}

	private Properties getProps() {
		Properties props = new Properties();
		props.put("key1", "value1");
		props.put("key2", new Integer(123));
		return props;
	}
	
	private void errorMessageThrown() {
		try {
			Exception e =  new Exception("My Exception 1");
			RuntimeIntelligence.reportException(e, RuntimeIntelligence.EXCEPTION_THROWN);
			System.out.println("Exception Thrown...");
			throw e;
		} catch (Exception e) {
			//Ignored
		}
	}
	
	private void errorMessageCaught() {
		try {
			throw  new Exception("My Exception 2");
		} catch (Exception e) {
			RuntimeIntelligence.reportException(e, RuntimeIntelligence.EXCEPTION_CAUGHT);
			System.out.println("Exception Caught...");
		}
	}

}