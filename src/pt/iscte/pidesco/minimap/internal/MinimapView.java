/*
 * Copyright (c) 2019.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Authors:
 *  - Sandro Marques <https://sandrohc.net>
 */

package pt.iscte.pidesco.minimap.internal;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.minimap.Activator;
import pt.iscte.pidesco.minimap.internal.parser.FileTest;
import pt.iscte.pidesco.minimap.internal.parser.LineTest;

public class MinimapView implements PidescoView {

	private static final Logger LOGGER = Logger.getLogger(MinimapView.class);
	public static final String VIEW_ID = "pt.iscte.pidesco.minimap.minimap";

	/** the current instance for the plugin view */
	private static MinimapView instance;

	// SWT Components
	private Composite root;
	private ScrolledComposite scroll;


	public MinimapView() {
		instance = this;

		loadFilters();
	}

	public static MinimapView getInstance() {
		return instance;
	}
	
	private void loadFilters() {
//		LOGGER.info("Filters loaded");
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
		this.root = viewArea;

		BundleContext context = Activator.getContext();

		ServiceReference<JavaEditorServices> serviceReference = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices service = context.getService(serviceReference);

		JavaEditorListener listener = new JavaEditorListener() {
			@Override
			public void fileOpened(File file) {
				LOGGER.debug("File opened: " + file);

				// TODO: do this in a worker thread
				Collection<LineTest> lines = new FileTest(file).parse(service);

				String linesStr = lines.stream()
						.map(line -> line.lineContent)
						.collect(Collectors.joining(System.lineSeparator()));

				createScrollComponent(linesStr);


//				StyledText text = new StyledText(scroll, SWT.BORDER);
//				text.setText("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//
//				FontData data = text.getFont().getFontData()[0];
//				Font font1 = new Font(scroll.getDisplay(), data.getName(), data.getHeight(), data.getStyle());
//
//				StyleRange style1 = new StyleRange();
//				style1.start = 0;
//				style1.length = 10;
//				style1.fontStyle = SWT.BOLD;
//				style1.font = font1;
//				style1.background = Colors.PURPLE;
//				text.setStyleRange(style1);


//				Label label = new Label(scroll, SWT.NONE);
//				label.setText(linesStr);
//				// label.setBackground(scroll.getDisplay().getSystemColor( SWT.COLOR_DARK_GREEN ) );
//				// label.pack();
//
//				scroll.setContent(label);
			}

			@Override
			public void fileClosed(File file) {
				LOGGER.debug("File closed: " + file);
			}
			
			@Override
			public void fileSaved(File file) {
				LOGGER.debug("File saved: " + file);
				// TODO reload/reparse file
			}
		};
		
		service.addListener(listener);


		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < 200; i++)
			temp.append(StringUtils.leftPad("" + i, 3)).append(". Very very very very very very very very very very very very very very very long line\n");

		createScrollComponent(temp.toString());
		
		File openedFile = service.getOpenedFile();
		if (openedFile != null) {
			listener.fileOpened(openedFile);
		}
	}
	
	private void createScrollComponent(String content) {
		// https://www.codeaffine.com/2016/03/01/swt-scrolledcomposite

		if (this.scroll == null || this.scroll.isDisposed()) {
			// Create a new ScrolledComposite
			LOGGER.debug("Creating new ScrolledComposite");

			ScrolledComposite scroll = new ScrolledComposite(this.root, SWT.V_SCROLL);
			scroll.setBackground(this.root.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
			scroll.setExpandHorizontal(true);
			this.scroll = scroll;
		} else if (this.scroll.getContent() != null && !this.scroll.getContent().isDisposed()) {
			// Remove the ScrolledComposite children
			LOGGER.debug("Removing ScrolledComposite content");

			this.scroll.getContent().dispose();
		}

		Label label = new Label(scroll, SWT.NONE);
		label.setText(content);
		label.setBackground(this.root.getDisplay().getSystemColor(SWT.COLOR_GREEN));
		label.pack();
		scroll.setContent(label);
	}

}