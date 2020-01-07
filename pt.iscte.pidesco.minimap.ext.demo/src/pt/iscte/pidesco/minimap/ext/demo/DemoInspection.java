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

package pt.iscte.pidesco.minimap.ext.demo;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.graphics.Color;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.extensibility.InspectionContext;
import pa.iscde.minimap.utils.Colors;

import static pa.iscde.minimap.utils.Colors.ALL_COLORS;

public class DemoInspection implements MinimapInspection {

	@Override
	public void inspect(ASTNode node, InspectionContext context) {
		String name = node.getClass().getSimpleName();

		context.addTooltip("L" + context.getLineStart() + ": " + name);
		context.setBackground(getColor(node));
	}

	private Color getColor(ASTNode node) {
		if (node instanceof TypeDeclaration) {
			return Colors.WHITE;
		}

		return ALL_COLORS[Math.abs(node.getClass().getName().hashCode()) % ALL_COLORS.length];
	}
}
