package dasho.samples;

import static com.preemptive.annotation.instrumentation.InjectionPoint.Beginning;
import static com.preemptive.annotation.instrumentation.InjectionPoint.BeginningAndEnd;
import static com.preemptive.annotation.instrumentation.InjectionPoint.End;

import java.util.HashMap;

import com.preemptive.annotation.instrumentation.Application;
import com.preemptive.annotation.instrumentation.ApplicationInstanceIdSource;
import com.preemptive.annotation.instrumentation.ApplicationStart;
import com.preemptive.annotation.instrumentation.ApplicationStop;
import com.preemptive.annotation.instrumentation.Company;
import com.preemptive.annotation.instrumentation.FeatureStart;
import com.preemptive.annotation.instrumentation.FeatureStop;
import com.preemptive.annotation.instrumentation.FeatureTick;
import com.preemptive.annotation.instrumentation.PerformanceProbe;

/**
 * This sample shows the use of attributes that have several parameters.
 */
@Company(id = "11111111-aaaa-bbbb-cccc-555555555555", name = "PreEmptive Solutions")
@Application(id = "00000000-0000-bbbb-0000-000000000000", name = "Test two", type = "command line", version = "1.0")
public class Instrumentation2 {
	private long serialNumber;

	public static void main(String[] args) {
		try {
			final Instrumentation2 instance = new Instrumentation2();
			instance.setup();
			instance.run();
			instance.teardown();
		}
		catch(Throwable t){
			t.printStackTrace();
			System.exit(1);
		}
	}

	private Instrumentation2() {
		System.out.println("<init>()");
	}

	@ApplicationStart(where = End)
	@ApplicationInstanceIdSource("getSerialNumber()")
	public void setup() {
		System.out.println("setup()");
		serialNumber = 27682934176L;
	}

	@ApplicationStop(where = Beginning)
	public void teardown() {
		System.out.println("teardown()");
		serialNumber = 0L;
	}

	public void run() {
		System.out.println("run()");
		startFeature();
		stopFeature();
		performanceMethod();
		for(int i = 1; i >= -1; i--){
			try{
				multiReturns(i);
				returnInsideExceptionHandler(i);
				returnInsideFinally(i);
				longReturn(i);
				doubleReturn(i);
				exceptionReturn(i);
			}
			catch(IllegalArgumentException e){
				System.out.println("run() - i=" + i + "; " + e.toString());
				// ignored
			}
		}
	}

	public String getSerialNumber() {
		final String rc = serialNumber == 0L ? null : Long.toHexString(serialNumber);
		System.out.println("getSerialNumber() returning " + rc);
		return rc;
	}

	@FeatureStart(value = "myFeature", where = End)
	public void startFeature() {
		System.out.println("startFeature() called");
	}

	@FeatureStop(value = "myFeature", where = Beginning)
	public void stopFeature() {
		System.out.println("stopFeature() called");
	}

	@FeatureTick(value = "returnFeature", where = End)
	public int multiReturns(int x) {
		if(x == 0){
			System.out.println("multiReturns(" + x + ") returning 0");
			return 0;
		}
		if(x < 0){
			System.out.println("multiReturns(" + x + ") returning -1");
			return -1;
		}
		System.out.println("multiReturns(" + x + ") returning 1");
		return 1;
	}

	@FeatureTick(value = "exceptionFeature", where = End)
	public int exceptionReturn(int x) {
		if(x == 0){
			System.out.println("exceptionReturn(" + x + ") throwing IllegalArgumentException()");
			throw new IllegalArgumentException("exceptionReturn(" + x + ") : doesnt like zero");
		}
		if(x <= 0){
			System.out.println("exceptionReturn(" + x + ") returning -1");
			return -1;
		}
		System.out.println("exceptionReturn(" + x + ") returning 1");
		return 1;
	}

	@FeatureStart(value = "${METHOD_NAME}")
	@FeatureStop(value = "${METHOD_NAME}")
	public int returnInsideExceptionHandler(int x) {
		try {
			int rc = exceptionReturn(x); 
			System.out.println("returnInsideExceptionHandler(" + x + ") returning " + rc);
			return rc;
		}
		catch(IllegalArgumentException e){
			System.out.println("returnInsideExceptionHandler(" + x + ") returning " + 5);
			return 5;
		}
	}

	@FeatureStart(value = "${METHOD_NAME}")
	@FeatureStop(value = "${METHOD_NAME}")
	public int returnInsideFinally(int x) {
		int rc = Integer.MIN_VALUE;
		try {
			rc = exceptionReturn(x); 
		}
		catch(IllegalArgumentException e){
			rc = 5;
		}
		finally {
			System.out.println("returnInsideFinally(" + x + ") returning " + rc);
			return rc;
		}
	}
	
	@PerformanceProbe(where = BeginningAndEnd)
	public HashMap performanceMethod() {
		System.out.println("performanceMethod() building bigMap");
		final HashMap bigMap = new HashMap();
		for(int j = 0; j < 500; j++){
			final HashMap map = new HashMap();
			for(int i = 0; i < 500; i++){
				map.put("K" + i, "V" + (i * i));
			}
			bigMap.put("K" + j, map);
		}
		System.out.println("performanceMethod() returning bigMap");
		return bigMap;
	}
	
	@FeatureTick(value = "longReturn", where = End)
	public long longReturn(int x) {
		final long rc = (long)x + 1L;
		System.out.println("longReturn(" + x + ") returning " + rc);
		return rc;
	}
	
	@FeatureTick(value = "doubleReturn", where = End)
	public double doubleReturn(int x) {
		double rc = (double)x * 3.5d;
		System.out.println("doubleReturn(" + x + ") returning " + rc);
		return rc;
	}
}
