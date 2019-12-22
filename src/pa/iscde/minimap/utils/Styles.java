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

import org.eclipse.swt.SWT;

public class Styles {

	public static final int NORMAL        = SWT.NORMAL;
	public static final int BOLD          = SWT.BOLD;
	public static final int ITALIC        = SWT.ITALIC;
	public static final int STRIKETHROUGH = 536870912;
	public static final int UNDERLINE     = 1073741824;


	private Styles() { }

	public static int multi(int... styles) {
		int style = 0;
		for (int i : styles) {
			style |= i;
		}
		return style;
	}
}
