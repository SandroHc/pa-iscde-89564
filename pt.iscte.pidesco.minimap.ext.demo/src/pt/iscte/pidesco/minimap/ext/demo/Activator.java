package pt.iscte.pidesco.minimap.ext.demo;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import pa.iscde.minimap.service.MinimapListener;
import pa.iscde.minimap.service.MinimapServices;

public class Activator implements BundleActivator {

	private MinimapServices minimapServices;
	private MinimapListener minimapListener;

	public void start(BundleContext bundleContext) {
		ServiceReference<MinimapServices> ref = bundleContext.getServiceReference(MinimapServices.class);
		minimapServices = bundleContext.getService(ref);

		minimapListener = new DemoListener();
		minimapServices.addListener(minimapListener);
	}

	public void stop(BundleContext bundleContext) {
		minimapServices.removeListener(minimapListener);
	}

}
