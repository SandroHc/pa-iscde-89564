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

package pa.iscde.minimap.internal.listeners;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public class ButtonClicked extends MouseAdapter {

	public static final int LEFT_BUTTON = 1;

	private final Runnable action;
	private boolean armed = false;

	public ButtonClicked(Runnable action) {
		this.action = action;
	}

	@Override
	public void mouseDown(MouseEvent event) {
		if (event.button == LEFT_BUTTON) {
			armed = true;
		}
	}

	@Override
	public void mouseUp(MouseEvent event) {
		if (armed && inRange(event)) {
			armed = false;
			action.run();
		}
	}

	static boolean inRange(MouseEvent event) {
		Point size = ((Control) event.widget).getSize();
		return event.x >= 0
				&& event.x <= size.x
				&& event.y >= 0
				&& event.y <= size.y;
	}
}
