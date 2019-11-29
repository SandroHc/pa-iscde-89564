package pt.iscte.pidesco.minimap.internal;

import java.io.File;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener.Adapter;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.minimap.Activator;

public class MinimapView implements PidescoView {

	public static final String VIEW_ID = "pt.iscte.pidesco.minimap.minimap";

	/** the current instance for the plugin view */
	private static MinimapView instance;
	
	public MinimapView() {
		instance = this;

		setupListeners();
		loadFilters();
	}

	public static MinimapView getInstance() {
		return instance;
	}

	private void setupListeners() {
		BundleContext context = Activator.getContext();

		ServiceReference<JavaEditorServices> serviceReference = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices service = context.getService(serviceReference);

		service.addListener(new Adapter() {
			@Override
			public void fileOpened(File file) {
				System.out.println("FILE OPENED: " + file);
			}

			@Override
			public void fileClosed(File file) {
				System.out.println("FILE CLOSED: " + file);
			}
		});
	}
	
	private void loadFilters() {
//		IExtensionRegistry reg = Platform.getExtensionRegistry();
//		for(IExtension ext : reg.getExtensionPoint(EXT_POINT_FILTER).getExtensions()) {
//			
//			ProjectBrowserFilter f = null;
//			try {
//				f = (ProjectBrowserFilter) ext.getConfigurationElements()[0].createExecutableExtension("class");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if(f != null)
//				filtersMap.put(ext.getUniqueIdentifier(), new Filter(f));
//		}
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> images) {
		viewArea.setLayout(new RowLayout(SWT.VERTICAL));
//		BundleContext context = Activator.getContext();
//		service.addListener(new ProjectBrowserListener.Adapter() {
//			@Override
//			public void doubleClick(SourceElement element) {
//				new Label(viewArea, SWT.NONE).setText(element.getName());
//				viewArea.layout();
//			}
//		});
	}
	
	
}