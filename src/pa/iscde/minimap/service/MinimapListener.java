/*
 * Copyright (c) 2020.
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
 * Listens for Minimap events.
 * @since 1.2.0
 */
public interface MinimapListener {

	/**
	 * Called when a new file is parsed,
	 *
	 * @param file the file that was parsed
	 * @since 1.2.0
	 */
	default void fileParsed(File file) { }

	/**
	 * Called when a line is selected.
	 *
	 * @param file            the active file being edited
	 * @param line            the line number in the file
	 * @param lineContent    the line content in the file
	 * @since 1.2.0
	 */
	default void lineSelected(File file, int line, String lineContent) { }

}
