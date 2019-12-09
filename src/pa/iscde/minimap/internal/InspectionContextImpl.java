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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.swt.graphics.Color;
import pa.iscde.minimap.internal.parser.MinimapFile;
import pa.iscde.minimap.internal.parser.MinimapLine;
import pa.iscde.minimap.service.InspectionContext;
import pa.iscde.minimap.utils.Colors;
import pa.iscde.minimap.utils.Styles;

public class InspectionContextImpl<N extends ASTNode> implements InspectionContext {

	public final N node;
	public final List<MinimapLine> lines;

	// Node info
	public final int posStart;
	public final int posEnd;

	public final int lineStart;
	public final int lineEnd;

	public InspectionContextImpl(N node, MinimapFile minimapFile) {
		this.node = node;

		this.posStart = node.getStartPosition();
		this.posEnd = this.posStart + node.getLength();

		CompilationUnit cu = (CompilationUnit) node.getRoot();
		this.lineStart = cu.getLineNumber(this.posStart);
		this.lineEnd   = cu.getLineNumber(this.posEnd);

		this.lines = Collections.unmodifiableList(minimapFile.getLines(this.lineStart, this.lineEnd));
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
	public void setForeground(final Color color) {
		this.lines.forEach(l -> l.foreground = color);
	}

	/**
	 * Defines the background color for this statement.
	 *
	 * @param color The color
	 *
	 * @see Colors for a list of some of the available colors
	 */
	@Override
	public void setBackground(final Color color) {
		this.lines.forEach(l -> l.background = color);
	}

	/**
	 * Defines the style (bold, italic, etc.) for this statement.
	 *
	 * @param style The style
	 *
	 * @see Styles for a list of some of the available styles
	 */
	@Override
	public void setStyle(final int style) {
		this.lines.forEach(l -> l.style = style);
	}

	/**
	 * Defines an icon for this statement.
	 *
	 * @param iconResource The icon resource
	 */
	@Override
	public void setIcon(final String iconResource) {
		this.lines.get(0).icon = iconResource;
	}

	/**
	 * Add tooltip for this statement.
	 *
	 * @param tooltip The tooltip
	 */
	@Override
	public void addTooltip(final String tooltip) {
		this.lines.get(0).tooltips.add(tooltip);
	}

	/**
	 * Add tooltips for this statement.
	 *
	 * @param tooltips The tooltips
	 */
	@Override
	public void addTooltips(final Iterable<String> tooltips) {
		Collection<String> lineTooltips = this.lines.get(0).tooltips;
		for (String tooltip : tooltips) {
			lineTooltips.add(tooltip);
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
	//</editor-fold>
}
