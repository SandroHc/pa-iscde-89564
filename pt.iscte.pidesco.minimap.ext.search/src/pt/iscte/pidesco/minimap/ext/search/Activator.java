package pt.iscte.pidesco.minimap.ext.search;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pa.iscde.minimap.service.MinimapListener;
import pa.iscde.minimap.service.MinimapServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.search.service.SearchServices;

public class Activator implements BundleActivator {

	private static Activator instance;
	private static SearchServices searchServices;
	private static MinimapServices minimapServices;
	
	private MinimapListener minimapListener;
	
	public String searchKeyword = null;
	public int[] searchResults = new int[0];

	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		searchServices = bundleContext.getService(bundleContext.getServiceReference(SearchServices.class));
		minimapServices = bundleContext.getService(bundleContext.getServiceReference(MinimapServices.class));

		minimapListener = new SearchListener();
		minimapServices.addListener(minimapListener);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
		
		minimapServices.removeListener(minimapListener);
	}
	
	public static Activator getInstance() {
		return instance;
	}
	
	public static SearchServices getSearchServices() {
		return searchServices;
	}

	public static MinimapServices getMinimapServices() {
		return minimapServices;
	}
	
	
	public static class SearchListener implements MinimapListener {
		@Override
		public void fileParsed(File file) {
		}

		@Override
		public void lineSelected(File file, int line, String lineContent) {
			PackageElement packageElement = new PackageElement(null, "root", file);
			String word = lineContent.trim();
			String packageItem = file.getPath().substring(file.getPath().lastIndexOf("pt"), file.getPath().lastIndexOf("\\")).replace("\\", ".");

			// Do search
			searchServices.searchAllFileEquals(packageElement, word, packageItem);
			
			// Store the results
			Activator.instance.searchKeyword = word;
			Activator.instance.searchResults = new int[] { 1 }; // lines with results

		}
	}

}
