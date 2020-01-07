package pt.iscte.pidesco.minimap.ext.metrics;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pa.iscde.metrics.MetricServiceAPI;

public class Activator implements BundleActivator {

	private static MetricServiceAPI metricsServices;

	public void start(BundleContext bundleContext) throws Exception {
		metricsServices = bundleContext.getService(bundleContext.getServiceReference(MetricServiceAPI.class));
	}

	public void stop(BundleContext bundleContext) throws Exception {
	}
	
	public static MetricServiceAPI getMetricsServices() {
		return metricsServices;
	}

}
