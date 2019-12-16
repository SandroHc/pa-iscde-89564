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

package pa.iscde.minimap.internal;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.internal.parser.MinimapFile;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pa.iscde.minimap.Activator;
import pa.iscde.minimap.internal.listeners.ButtonClicked;
import pa.iscde.minimap.internal.parser.MinimapLine;

public class MinimapView implements PidescoView {

	private static final Logger LOGGER = Logger.getLogger(MinimapView.class);
	public static final String VIEW_ID = "pt.iscte.pidesco.minimap.minimap";
	public static final String EXT_POINT_INSPECTION = "pt.iscte.pidesco.minimap.inspection";

	public static final MinimapLine EMPTY_LINE = new MinimapLine(0, 1, "No file opened.");
	public static final List<MinimapLine> EMPTY_LINE_LIST = Collections.singletonList(EMPTY_LINE);

	/** the current instance for the plugin view */
	private static MinimapView instance;

	private final JavaEditorServices javaEditorServices;
	private final Map<String, MinimapInspection> rules;

	/* SWT Components */
	private Composite root;
	private ScrolledComposite scroll;


	public MinimapView() {
		instance = this;
		rules = new HashMap<>();

		BundleContext context = Activator.getContext();
		ServiceReference<JavaEditorServices> serviceReference = context.getServiceReference(JavaEditorServices.class);
		javaEditorServices = context.getService(serviceReference);

		loadInspections();
	}

	public static MinimapView getInstance() {
		return instance;
	}
	
	private void loadInspections() {
		rules.clear();

		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint(EXT_POINT_INSPECTION).getExtensions()) {
			try {
				loadInspection(ext);
			} catch (Exception e) {
				LOGGER.error("Error loading extension '" + ext.getSimpleIdentifier() + "'", e);
			}
		}

		LOGGER.info("Loaded " + rules.size() + " inspection rules");
	}

	private void loadInspection(IExtension ext) throws CoreException {
		String extId = ext.getSimpleIdentifier();
		String extName = ext.getLabel();

		LOGGER.info("Loading extension '" + extId + '\'');

		for (IConfigurationElement conf : ext.getConfigurationElements()) {
			String inspectionId = conf.getAttribute("id");
			String inspectionName = conf.getAttribute("name");
			String inspectionDescription = conf.getAttribute("description");

			LOGGER.info("Loading inspection '" + extId + '@' + inspectionId + '\'');

			MinimapInspection impl = (MinimapInspection) conf.createExecutableExtension("class");

			rules.put(ext.getUniqueIdentifier(), impl);
		}
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> images) {
		this.root = viewArea;

		Listener listener = new Listener();
		javaEditorServices.addListener(listener);

		File openedFile = javaEditorServices.getOpenedFile();
		if (openedFile != null) {
			listener.fileOpened(openedFile);
		} else {
			createScrollComponent(EMPTY_LINE_LIST);
		}
	}
	
	private void createScrollComponent(Collection<MinimapLine> lines) {
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

		// https://www.eclipse.org/articles/Article-Understanding-Layouts/Understanding-Layouts.htm
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.fill = true;
//		layout.spacing = 0;
//		layout.marginTop = 0;

		Group group = new Group(scroll, SWT.SHADOW_NONE);
		group.setLayout(layout);

		for (MinimapLine line : lines) {
			Label label = MinimapLine.createLabel(line, group);
			label.addMouseListener(
					new ButtonClicked(() -> {
						LOGGER.info("Navigating to line " + line.lineNumber + " on offset " + line.lineStartingOffset);
						javaEditorServices.selectText(javaEditorServices.getOpenedFile(), line.lineStartingOffset, 0);
					})
			);
		}

		group.pack();
		scroll.setContent(group);
	}

	private class Listener implements JavaEditorListener {

		@Override
		public void fileOpened(File file) {
			LOGGER.info("File opened: " + file);

			// TODO: do this in a worker thread
			// TODO: implement way to enable/disable rules
			Collection<MinimapLine> lines = new MinimapFile(file).parse(javaEditorServices, rules.values());

			createScrollComponent(lines);


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
	}

}