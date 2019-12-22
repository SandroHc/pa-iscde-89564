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
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.service.InspectionContext;

/**
 * To be used as a dummy (no-operation) inspection
 */
public class NoopInspection implements MinimapInspection {

	public static final NoopInspection NOOP = new NoopInspection();

	private NoopInspection() {
		// Use the constant instead
	}

	@Override
	public <N extends ASTNode> void inspect(N node, InspectionContext context) {
		// NO-OP
	}
}
