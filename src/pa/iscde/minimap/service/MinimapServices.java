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

package pa.iscde.minimap.service;

import java.io.File;

/**
 * Services offered by the Minimap component.
 */
public interface MinimapServices {

	String MINIMAP_VIEW_ID = "pt.iscte.pidesco.minimap.minimap";
	String EXT_POINT_INSPECTION_ID = "pt.iscte.pidesco.minimap.inspection";

	/**
	 * Add a new Minimap listener.
	 *
	 * @param listener the listener
	 * @since 1.2.0
	 */
	void addListener(MinimapListener listener);

	/**
	 * Remove a new Minimap listener.
	 *
	 * @param listener the listener
	 * @since 1.2.0
	 */
	void removeListener(MinimapListener listener);

	/**
	 * Enables the given inspection, and parses the active file.
	 *
	 * @param extensionId  the extension identifier
	 * @param inspectionId the inspection identifier
	 * @since 1.2.0
	 */
	void enableInspection(String extensionId, String inspectionId);

	/**
	 * Disable the given inspection, and parses the active file.
	 *
	 * @param extensionId  the extension identifier
	 * @param inspectionId the inspection identifier
	 * @since 1.2.0
	 */
	void disableInspection(String extensionId, String inspectionId);

	/**
	 * Get the file that is opened in the minimap view.
	 *
	 * @return a reference to the opened file, or null if no file is opened
	 * @since 1.0.0
	 */
	File getOpenedFile();

	/**
	 * Opens a file in the minimap view.
	 *
	 * @param file (non-null) file to open
	 * @since 1.0.0
	 */
	void openFile(File file);

}