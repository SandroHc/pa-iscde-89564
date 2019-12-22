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

import org.eclipse.swt.graphics.Color;
import pa.iscde.minimap.utils.Colors;
import pa.iscde.minimap.utils.Styles;

public interface InspectionContext {

	/**
	 * Defines the foreground color for this statement.
	 *
	 * @param color The color
	 *
	 * @see Colors for a list of some of the available colors
	 */
	void setForeground(final Color color);

	/**
	 * Defines the background color for this statement.
	 *
	 * @param color The color
	 *
	 * @see Colors for a list of some of the available colors
	 */
	void setBackground(final Color color);

	/**
	 * Defines the style (bold, italic, etc.) for this statement.
	 *
	 * @param style The style
	 *
	 * @see Styles for a list of some of the available styles
	 */
	void setStyle(final int style);

	/**
	 * Defines an icon for this statement.
	 *
	 * @param iconResource The icon resource
	 */
	void setIcon(final String iconResource);

	/**
	 * Add tooltip for this statement.
	 *
	 * @param tooltip The tooltip
	 */
	void addTooltip(final String tooltip);

	/**
	 * Add tooltips for this statement.
	 *
	 * @param tooltips The tooltips
	 */
	void addTooltips(final Iterable<String> tooltips);

	//<editor-fold desc="Getters" default-state="collaped">
	int getPosStart();
	int getPosEnd();
	int getLineStart();
	int getLineEnd();
	//</editor-fold>
}
