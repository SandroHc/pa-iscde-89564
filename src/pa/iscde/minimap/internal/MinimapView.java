/*
 * Copyright (c) 2019.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Authors:
 *  - Sandro Marques <https://sandrohc.net>
 */

package pa.iscde.minimap.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import pa.iscde.minimap.MinimapActivator;
import pa.iscde.minimap.internal.extension.Extension;
import pa.iscde.minimap.internal.extension.ExtensionRule;
import pa.iscde.minimap.internal.listeners.ButtonClicked;
import pa.iscde.minimap.internal.parser.MinimapFile;
import pa.iscde.minimap.internal.parser.MinimapLine;
import pa.iscde.minimap.internal.settings.SettingsManager;
import pa.iscde.minimap.service.MinimapServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class MinimapView implements PidescoView {

	private static final Logger LOGGER = Logger.getLogger(MinimapView.class);

	public static final MinimapLine EMPTY_LINE = new MinimapLine(0, 1, "No file opened.");
	public static final List<MinimapLine> EMPTY_LINE_LIST = Collections.singletonList(EMPTY_LINE);

	/** the current instance for the plugin view */
	private static MinimapView instance;

	private final Collection<Extension> extensions;
	private final Collection<ExtensionRule> activeRules;

	private File activeFile = null;

	/* SWT Components */
	private Composite root;
	private ScrolledComposite scroll;


	public MinimapView() {
		instance = this;
		extensions = new ArrayList<>(10);
		activeRules = new ArrayList<>(10);

		loadInspections();
	}

	public static MinimapView getInstance() {
		return instance;
	}

	private void loadInspections() {
		SettingsManager.load();
		extensions.clear();
		activeRules.clear();

		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint(MinimapServices.EXT_POINT_INSPECTION_ID).getExtensions()) {
			try {
				LOGGER.info("Loading extension '" + ext.getSimpleIdentifier());

				Extension extension = new Extension(ext);
				this.extensions.add(extension);
				this.activeRules.addAll(extension.rules);

				// Load rule stat
				for (ExtensionRule rule : extension.rules) {
					if (rule.isErrored()) {
						rule.setEnabled(false);
						SettingsManager.update(rule);
					} else {
						rule.setEnabled(SettingsManager.isEnabled(rule));
					}
				}
			} catch (Exception e) {
				LOGGER.error("Error loading extension '" + ext.getSimpleIdentifier() + "'", e);
			}
		}

		LOGGER.info("Loaded " + activeRules.size() + " inspection rules");
		SettingsManager.save();
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> images) {
		this.root = viewArea;

		Listener listener = new Listener();

		JavaEditorServices javaEditorServices = MinimapActivator.getInstance().getJavaEditorServices();
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
//			scroll.setBackground(this.root.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
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
						final JavaEditorServices javaEditorServices = MinimapActivator.getInstance().getJavaEditorServices();
						final File file = javaEditorServices.getOpenedFile();

						LOGGER.info("Navigating to line " + line.lineNumber + " on offset " + line.lineStartingOffset);
						javaEditorServices.selectText(file, line.lineStartingOffset, 0);

						// Notify the listeners
						MinimapActivator.getInstance().getListeners().forEach(l -> l.lineSelected(file, line.lineNumber, line.lineContent));
					})
			);

			// TODO: use a more flexible style
//			StyledText text = new StyledText(scroll, SWT.BORDER);
//			text.setText("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//
//			FontData data = text.getFont().getFontData()[0];
//			Font font1 = new Font(scroll.getDisplay(), data.getName(), data.getHeight(), data.getStyle());
//
//			StyleRange style1 = new StyleRange();
//			style1.start = 0;
//			style1.length = 10;
//			style1.fontStyle = SWT.BOLD;
//			style1.font = font1;
//			style1.background = Colors.PURPLE;
//			text.setStyleRange(style1);
		}

		group.pack();
		scroll.setContent(group);
	}

	public Composite getRoot() {
		return root;
	}

	public Collection<Extension> getExtensions() {
		return extensions;
	}

	public File getActiveFile() {
		return activeFile;
	}

	public void parseFile(File file) {
		this.activeFile = file;

		if (file == null) {
			this.scroll.getContent().dispose();
			return;
		}

		// TODO: do this in a worker thread
		JavaEditorServices javaEditorServices = MinimapActivator.getInstance().getJavaEditorServices();
		Collection<MinimapLine> lines = new MinimapFile(file).parse(javaEditorServices, activeRules);

		// Notify the listeners
		MinimapActivator.getInstance().getListeners().forEach(l -> l.fileParsed(file));

		createScrollComponent(lines);
	}

	private class Listener implements JavaEditorListener {

		@Override
		public void fileOpened(File file) {
			LOGGER.info("File opened: " + file);
			parseFile(file);
		}

		@Override
		public void fileClosed(File file) {
			LOGGER.debug("File closed: " + file);
			parseFile(null);
		}

		@Override
		public void fileSaved(File file) {
			LOGGER.debug("File saved: " + file);
			parseFile(file);
		}
	}

}