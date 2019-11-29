package pt.iscte.pidesco.minimap;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	/** the context for the current instance of the plugin */
	private static BundleContext context;

	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("STARTED");
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("STOPPED");
		Activator.context = null;
	}

}
