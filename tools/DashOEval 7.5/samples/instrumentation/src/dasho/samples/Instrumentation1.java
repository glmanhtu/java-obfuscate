package dasho.samples;

import java.util.Date;
import java.util.Properties;

import com.preemptive.annotation.instrumentation.ApplicationId;
import com.preemptive.annotation.instrumentation.ApplicationName;
import com.preemptive.annotation.instrumentation.ApplicationStart;
import com.preemptive.annotation.instrumentation.ApplicationStop;
import com.preemptive.annotation.instrumentation.ApplicationType;
import com.preemptive.annotation.instrumentation.ApplicationVersion;
import com.preemptive.annotation.instrumentation.CompanyId;
import com.preemptive.annotation.instrumentation.CompanyName;
import com.preemptive.annotation.instrumentation.FeatureStart;
import com.preemptive.annotation.instrumentation.FeatureStop;
import com.preemptive.annotation.instrumentation.FeatureTick;
import com.preemptive.annotation.instrumentation.FlowControl;
import com.preemptive.annotation.instrumentation.PerformanceProbe;
import com.preemptive.annotation.instrumentation.PropertySource;
import com.preemptive.annotation.instrumentation.SystemProfile;

/**
 * This sample shows the annotations that use a single parameter.
 */
@CompanyId("11111111-aaaa-bbbb-cccc-555555555555")
@CompanyName("PreEmptive Solutions")
@ApplicationId("00000000-0000-aaaa-0000-000000000000")
@ApplicationName("Test one")
@ApplicationType("command line")
@ApplicationVersion("1.0")
@FlowControl(low=10, high=20, dropAt=100)
public class Instrumentation1 {
	/**
	 * @see #cleanUp()
	 * @see #main(String[])
	 */
	private static Properties staticProps = new Properties();
	private Properties instanceProps = new Properties();
	private int propertyGetCount = 0;

	/**
	 * We have to use a static member as the source for properties in a static method.
	 *
	 * @param args command line arguments
	 */
	@ApplicationStart
	@ApplicationStop
	@PropertySource("staticProps")
	public static void main(String[] args) {
		final Instrumentation1 instance = new Instrumentation1();
		staticProps.setProperty("app.run", "true");
		instance.run();
		staticProps.setProperty("app.run", "false");
		cleanUp();
	}

	/**
	 * Here we use an instance variable as the source of our properties. When the feature start is sent we won't have any
	 * instance properties. When the feature stop is sent we will have one.
	 */
	@FeatureStart("<init>")
	@FeatureStop("<init>")
	@PropertySource("instanceProps")
	private Instrumentation1() {
		instanceProps.putAll(staticProps);
		instanceProps.setProperty("instance.start", new Date().toString());
	}

	@SystemProfile
	public void run() {
		startFeature();
		bracketedFeature();
		stopFeature();
		performanceMethod();
		featureWithProperties();
	}

	@FeatureStart("bracketedFeature")
	@FeatureStop("bracketedFeature")
	public void bracketedFeature() {
		System.out.println("bracketedFeature()");
	}

	@FeatureStart("myFeature")
	public void startFeature() {
		System.out.println("startFeature()");
	}

	@FeatureStop("myFeature")
	public void stopFeature() {
		System.out.println("stopFeature()");
	}

	@FeatureStart("featureWithProperties")
	@FeatureStop("featureWithProperties")
	@PropertySource("getMyProperties()")
	public void featureWithProperties() {
		System.out.println("featureWithProperties()");
	}

	@PerformanceProbe
	@PropertySource("@getStaticProperties()")
	public void performanceMethod() {
		System.out.println("performanceMethod()");
	}

	/**
	 * This is required because of our use of the PropertySource annotation. If the annotation is used on a static method,
	 * this would need to be static. Since it was on an instance method it is not static.
	 *
	 * @return the properties, may be null or empty.
	 */
	public Properties getMyProperties() {
		final Properties p = new Properties();
		p.putAll(instanceProps);
		propertyGetCount++;
		p.setProperty("propertyGetCount", Integer.toString(propertyGetCount));
		return p;
	}

	/**
	 * This is required because of our use of the PropertySource annotation in static methods.
	 *
	 * @return the properties, may be null or empty.
	 */
	public static Properties getStaticProperties() {
		return staticProps;
	}

	/**
	 * A static method that uses a static source for its properties.
	 */
	@FeatureTick("cleanUp")
	@PropertySource("getStaticProperties()")
	public static void cleanUp() {
		System.out.println("cleanUp()");
	}
}
