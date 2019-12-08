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

package pt.iscte.pidesco.minimap.internal.parser;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import pt.iscte.pidesco.minimap.service.constants.Styles;

public class MinimapLine {

	public final int lineNumber;
	public final String lineContent;

	public Color foreground	= null;
	public Color background	= null;
	public int style		= Styles.NORMAL;
	public String icon		= null;
	public final Collection<String> tooltips = new ArrayList<>(0);

	public MinimapLine(int lineNumber, String lineContent) {
		this.lineNumber = lineNumber;
		this.lineContent = lineContent;
	}

	public static Label createLabel(MinimapLine line, Composite parent) {
		Label label = new Label(parent, line.style);
		label.setText(line.lineContent);

		if (line.foreground != null) {
			label.setForeground(line.foreground);
		}

		if (line.background != null) {
			label.setBackground(line.background);
		}

		if (!line.tooltips.isEmpty()) {
			String tooltips = String.join("\n", line.tooltips);
			label.setToolTipText(tooltips);
		}

		return label;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MinimapLine minimapLine = (MinimapLine) o;

		return lineNumber == minimapLine.lineNumber;
	}

	@Override
	public int hashCode() {
		return lineNumber;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("line", lineNumber)
				.append("content", StringUtils.abbreviate(lineContent.trim(), 30));
		if (foreground != null)			builder.append("foreground", foreground);
		if (background != null)			builder.append("background", background);
		if (style != Styles.NORMAL)		builder.append("style", style);
		if (icon != null)				builder.append("icon", icon);
		if (!tooltips.isEmpty())		builder.append("tooltips", tooltips);
		return builder.toString();
	}
}
