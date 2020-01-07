package pt.iscte.pidesco.minimap.ext.conventions;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pt.iscte.pidesco.conventions.service.ConventionsCheckerServices;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static ConventionsCheckerServices conventionsCheckerServices;

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Activator.conventionsCheckerServices = context.getService(context.getServiceReference(ConventionsCheckerServices.class));
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	public static BundleContext getContext() {
		return context;
	}
	
	public static ConventionsCheckerServices getConventionsCheckerServices() {
		return conventionsCheckerServices;
	}

}
