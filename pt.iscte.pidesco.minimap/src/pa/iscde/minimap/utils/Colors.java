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

package pa.iscde.minimap.utils;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

@SuppressWarnings("unused")
public class Colors {

	public static final Color WHITE       = fromRGB(255,   255,   255,  1);
	public static final Color RED         = fromHSB(  0, 0.30F, 1.00F, 1F);
	public static final Color ORANGE      = fromHSB( 25, 0.30F, 1.00F, 1F);
	public static final Color BROWN       = fromHSB( 34, 0.30F, 1.00F, 1F);
	public static final Color YELLOW      = fromHSB( 52, 0.30F, 1.00F, 1F);
	public static final Color GREEN       = fromHSB(128, 0.30F, 1.00F, 1F);
	public static final Color BLUE        = fromHSB(236, 0.30F, 1.00F, 1F);
	public static final Color PURPLE      = fromHSB(287, 0.30F, 1.00F, 1F);
	public static final Color PINK        = fromHSB(330, 0.30F, 1.00F, 1F);
	public static final Color GRAY        = fromHSB(  0, 0.00F, 0.96F, 1F);
	public static final Color BLACK       = fromHSB(265, 0.83F, 0.20F, 1F);
	public static final Color TRANSPARENT = fromRGB(  0,     0,     0,  0);

	public static final Color[] ALL_COLORS = {
			Colors.BLUE, Colors.BROWN, Colors.GRAY, Colors.GREEN, Colors.ORANGE,
			Colors.PINK, Colors.PURPLE, Colors.YELLOW, Colors.GRAY
	};


	private Colors() { }

	public static Color fromRGB(int r, int g, int b, int a) {
		return new Color(Display.getDefault(), r, g, b, a);
	}

	public static Color fromHSB(float h, float s, float b, float a) {
		java.awt.Color color = java.awt.Color.getHSBColor(h / 360F, s, b);
		return fromRGB(color.getRed(), color.getGreen(), color.getBlue(), Math.round(a * 255));
	}
}
