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

package pt.iscte.pidesco.minimap.service;

import java.util.Collection;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Color;
import pt.iscte.pidesco.minimap.service.constants.Colors;
import pt.iscte.pidesco.minimap.service.constants.Styles;

public interface FileEvent<N extends ASTNode> {

	/**
	 * Defines the foreground color for this statement.
	 *
	 * @param color The color
	 *
	 * @see Colors for a list of some of the available colors
	 */
	void setForeground(Color color);

	/**
	 * Defines the background color for this statement.
	 *
	 * @param color The color
	 *
	 * @see Colors for a list of some of the available colors
	 */
	void setBackground(Color color);

	/**
	 * Defines the style (bold, italic, etc.) for this statement.
	 *
	 * @param style The style
	 *
	 * @see Styles for a list of some of the available styles
	 */
	void setStyle(int style);

	/**
	 * Defines an icon for this statement.
	 *
	 * @param iconResource The icon resource
	 */
	void setIcon(String iconResource);

	/**
	 * Add tooltip for this statement.
	 *
	 * @param tooltip The tooltip
	 */
	void addTooltip(String tooltip);

	/**
	 * Add tooltips for this statement.
	 *
	 * @param tooltips The tooltips
	 */
	void addTooltips(Iterable<String> tooltips);

	//<editor-fold desc="Getters" default-state="collaped">
	int getPosStart();
	int getPosEnd();
	int getLineStart();
	int getLineEnd();

	Color getForeground();
	Color getBackground();
	int getStyle();
	String getIcon();
	Collection<String> getTooltips();
	//</editor-fold>
}
