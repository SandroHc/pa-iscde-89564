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

package pa.iscde.minimap.extensibility.inspections;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.graphics.Color;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.service.InspectionContext;
import pa.iscde.minimap.utils.Colors;

import static pa.iscde.minimap.utils.Colors.ALL_COLORS;

public class BaseInspection implements MinimapInspection {

	@Override
	public <N extends ASTNode> void inspect(N node, InspectionContext context) {
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