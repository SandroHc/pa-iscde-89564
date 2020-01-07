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

package pa.iscde.minimap.extensibility;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * Adds inspection rules that can be activated/deactivated in the Minimap view.
 */
public interface MinimapInspection {

	/**
	 * Inspect an AST Node.
	 * The {@code node} represents the current AST node being inspected.
	 * The {@code context} provides a way to change the source code style
	 *
	 * @param node		the AST node
	 * @param context	the context
	 * @since 1.0.0
	 */
	void inspect(ASTNode node, InspectionContext context);
}
