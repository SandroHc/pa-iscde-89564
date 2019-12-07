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

package pt.iscte.pidesco.minimap.internal;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

@SuppressWarnings("unused")
public class Colors {

	public static final Color WHITE       = fromRGB(255, 255, 255, 255);
	public static final Color YELLOW      = fromRGB(253, 230,  75, 255);
	public static final Color ORANGE      = fromRGB(237, 112,  20, 255);
	public static final Color RED         = fromRGB(208,  49,  45, 255);
	public static final Color PINK        = fromRGB(250, 134, 197, 255);
	public static final Color PURPLE      = fromRGB(227, 159, 246, 255);
	public static final Color BLUE        = fromRGB( 58,  67, 186, 255);
	public static final Color GREEN       = fromRGB( 59, 177,  67, 255);
	public static final Color BROWN       = fromRGB( 75,  55,  28, 255);
	public static final Color GRAY        = fromRGB(108,  98, 109, 255);
	public static final Color BLACK       = fromRGB(  3,   1,   6, 255);
	public static final Color TRANSPARENT = fromRGB(  0,   0,   0,   0);


	private Colors() { }

	public static Color fromRGB(int r, int g, int b, int a) {
		return new Color(Display.getDefault(), r, g, b, a);
	}

}
