package pt.iscte.pidesco.minimap.internal;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.minimap.Activator;

public class MinimapView implements PidescoView {

	public static final String VIEW_ID = "pt.iscte.pidesco.minimap.minimap";

	/** the current instance for the plugin view */
	private static MinimapView instance;
	
	private ScrolledComposite composite;
	
	public MinimapView() {
		instance = this;

		loadFilters();
	}

	public static MinimapView getInstance() {
		return instance;
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
		
		
		BundleContext context = Activator.getContext();

		ServiceReference<JavaEditorServices> serviceReference = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices service = context.getService(serviceReference);

		JavaEditorListener listener = new JavaEditorListener() {
			@Override
			public void fileOpened(File file) {
				System.out.println("FILE OPENED: " + file);
				ScrolledComposite scroll = MinimapView.this.composite;
				
				return;/*				
				String contents;
				try {
					contents = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
				} catch (IOException e) {
					contents = "Error loading file: " + file;
					e.printStackTrace();
				}

				// contents.split("\n")
				
				
				Label label = new Label(MinimapView.this.composite, SWT.NONE);
				label.setText(contents);
				// label.setBackground( this.composite.getDisplay().getSystemColor( SWT.COLOR_DARK_GREEN ) );
				// label.pack();
				
				scroll.setContent(label);
				scroll.setMinSize(label.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
				scroll.layout();*/
			}

			@Override
			public void fileClosed(File file) {
				System.out.println("FILE CLOSED: " + file);
			}
			
			@Override
			public void fileSaved(File file) {
				// TODO reload file
			}
		};
		
		service.addListener(listener);
		
		

		Button button = new Button(viewArea, SWT.PUSH);
		button.setText("Refresh");
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("SELECTED: " + e);
				
				initScrolledComposite(viewArea);
				
				// listener.fileOpened(service.getOpenedFile());
				
				viewArea.layout();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) { /* NO-OP */ }
		});
		
		initScrolledComposite(viewArea);
		
		File openedFile = service.getOpenedFile();
		if (openedFile != null) {
			listener.fileOpened(openedFile);
		}
	}
	
	private void initScrolledComposite(Composite root) {
		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < 200; i++) {
			temp.append("Very very very very very very very very very very very very very very very very very very very very very very very very long line\n");
		}

		// Removes the previous composite, if any
		if (this.composite != null) {
			this.composite.dispose();
		}

		ScrolledComposite scroll = new ScrolledComposite(root, SWT.V_SCROLL);
		scroll.setLayout(new RowLayout(SWT.VERTICAL));
		scroll.setExpandHorizontal(true);

		Label label = new Label(scroll, SWT.NONE);
		label.setText(temp.toString());
//		label.setBackground( this.composite.getDisplay().getSystemColor( SWT.COLOR_DARK_GREEN ) );
		label.pack();
		
		scroll.setContent(label);
		
		Point p = label.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		scroll.setMinHeight(p.y);
//		scroll.setMinSize(label.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		this.composite = scroll;
	}
	
	
}