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

package pa.iscde.minimap.service;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.swt.graphics.Color;
import pa.iscde.minimap.service.constants.Colors;
import pa.iscde.minimap.service.constants.Styles;

public class FileEventImpl<N extends ASTNode> implements FileEvent<N> {

	public final N node;

	// Node info
	public final int posStart;
	public final int posEnd;

	public final int lineStart;
	public final int lineEnd;

	// Styles
	private Color foreground = null;
	private Color background = null;
	private int style        = Styles.NORMAL;
	private String icon      = null;
	private Collection<String> tooltips = new ArrayList<>(0);

	public FileEventImpl(N node) {
		this.node = node;

		this.posStart = node.getStartPosition();
		this.posEnd = this.posStart + node.getLength();

		CompilationUnit cu = (CompilationUnit) node.getRoot();
		this.lineStart = cu.getLineNumber(this.posStart);
		this.lineEnd   = cu.getLineNumber(this.posEnd);
	}

	//<editor-fold desc="Setters" default-state="collaped">
	/**
	 * Defines the foreground color for this statement.
	 *
	 * @param color The color
	 *
	 * @see Colors for a list of some of the available colors
	 */
	@Override
	public void setForeground(Color color) {
		this.foreground = color;
	}

	/**
	 * Defines the background color for this statement.
	 *
	 * @param color The color
	 *
	 * @see Colors for a list of some of the available colors
	 */
	@Override
	public void setBackground(Color color) {
		this.background = color;
	}

	/**
	 * Defines the style (bold, italic, etc.) for this statement.
	 *
	 * @param style The style
	 *
	 * @see Styles for a list of some of the available styles
	 */
	@Override
	public void setStyle(int style) {
		this.style = style;
	}

	/**
	 * Defines an icon for this statement.
	 *
	 * @param iconResource
	 */
	@Override
	public void setIcon(String iconResource) {
		this.icon = iconResource;
	}

	/**
	 * Add tooltip for this statement.
	 *
	 * @param tooltip The tooltip
	 */
	@Override
	public void addTooltip(String tooltip) {
		this.tooltips.add(tooltip);
	}

	/**
	 * Add tooltips for this statement.
	 *
	 * @param tooltips The tooltips
	 */
	@Override
	public void addTooltips(Iterable<String> tooltips) {
		for (String tooltip : tooltips) {
			this.tooltips.add(tooltip);
		}
	}
	//</editor-fold>

	//<editor-fold desc="Getters" default-state="collaped">
	@Override
	public int getPosStart() {
		return posStart;
	}

	@Override
	public int getPosEnd() {
		return posEnd;
	}

	@Override
	public int getLineStart() {
		return lineStart;
	}

	@Override
	public int getLineEnd() {
		return lineEnd;
	}

	@Override
	public Color getForeground() {
		return foreground;
	}

	@Override
	public Color getBackground() {
		return background;
	}

	@Override
	public int getStyle() {
		return style;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public Collection<String> getTooltips() {
		return tooltips;
	}
	//</editor-fold>
}
