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

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final Logger LOGGER = Logger.getLogger(Activator.class);

	/** the context for the current instance of the plugin */
	private static BundleContext context;

	
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
	
	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) {
		LOGGER.info("Service started");
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) {
		LOGGER.info("Service stopped");
		Activator.context = null;
	}

}
