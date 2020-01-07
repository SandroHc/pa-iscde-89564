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

package pa.iscde.minimap;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import pa.iscde.minimap.internal.MinimapServicesImpl;
import pa.iscde.minimap.service.MinimapListener;
import pa.iscde.minimap.service.MinimapServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class MinimapActivator implements BundleActivator {

	private static final Logger LOGGER = Logger.getLogger(MinimapActivator.class);

	/** the current instance of the plugin */
	private static MinimapActivator instance;

	private Set<MinimapListener> listeners;
	private ServiceRegistration<MinimapServices> service;

	private JavaEditorServices javaEditorServices;

	
	static {
		ConsoleAppender console = new ConsoleAppender();
		console.setName("ConsoleLogger");
		console.setLayout(new PatternLayout("%d{HH:mm:ss} %-5p [%c{1}:%L] (%t) %m%n"));
		console.setThreshold(Level.DEBUG);
		console.activateOptions();

		FileAppender file = new FileAppender();
		file.setName("FileLogger");
		file.setFile("minimap.log");
		file.setLayout(new PatternLayout("%d{HH:mm:ss} %-5p [%c{1}:%L] (%t) %m%n")); // More verbose: "%d %-5p [%c:%L] (%t) %m%n"
		file.setThreshold(Level.DEBUG);
		file.setAppend(true);
		file.activateOptions();

		Logger rootLogger = Logger.getRootLogger();
		rootLogger.addAppender(console);
		rootLogger.addAppender(file);
	}
	
	public void start(BundleContext bundleContext) {
		LOGGER.info("Service started");

		instance = this;
		this.listeners = new HashSet<>();
		this.service = bundleContext.registerService(MinimapServices.class, new MinimapServicesImpl(), null);

		ServiceReference<JavaEditorServices> ref = bundleContext.getServiceReference(JavaEditorServices.class);
		javaEditorServices = bundleContext.getService(ref);
	}

	public void stop(BundleContext bundleContext) {
		LOGGER.info("Service stopped");

		instance = null;
		this.listeners.clear();
		this.service.unregister();
	}

	public static MinimapActivator getInstance() {
		return instance;
	}

	public Set<MinimapListener> getListeners() {
		return listeners;
	}

	public void addListener(MinimapListener listener) {
		listeners.add(listener);
	}

	public void removeListener(MinimapListener listener) {
		listeners.remove(listener);
	}

	public JavaEditorServices getJavaEditorServices() {
		return javaEditorServices;
	}
}
