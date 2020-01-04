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

package pa.iscde.minimap.service;

import java.nio.file.Path;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import pa.iscde.minimap.utils.Colors;
import pa.iscde.minimap.utils.Styles;

public interface InspectionContext {

	/**
	 * Defines the foreground color for the node being inspected.
	 * Any {@linkplain org.eclipse.swt.graphics.Color} can be used.</p>
	 *
	 * <p>You can use a list of preset colors found in {@linkplain Colors}:
	 * {@linkplain Colors#WHITE WHITE}, {@linkplain Colors#RED RED}, {@linkplain Colors#ORANGE ORANGE},
	 * {@linkplain Colors#BROWN BROWN}, {@linkplain Colors#YELLOW YELLOW}, {@linkplain Colors#GREEN GREEN},
	 * {@linkplain Colors#BLUE BLUE}, {@linkplain Colors#PURPLE PURPLE}, {@linkplain Colors#PINK PINK},
	 * {@linkplain Colors#GRAY GRAY}, {@linkplain Colors#BLACK BLACK}.</p>
	 *
	 * @param color The color
	 *
	 * @see Colors Colors - for a list of some of the available colors
	 */
	void setForeground(final Color color);

	/**
	 * <p>Defines the background color for the node being inspected.
	 * Any {@linkplain org.eclipse.swt.graphics.Color} can be used.</p>
	 *
	 * <p>You can use a list of preset colors found in {@linkplain Colors}:
	 * {@linkplain Colors#WHITE WHITE}, {@linkplain Colors#RED RED}, {@linkplain Colors#ORANGE ORANGE},
	 * {@linkplain Colors#BROWN BROWN}, {@linkplain Colors#YELLOW YELLOW}, {@linkplain Colors#GREEN GREEN},
	 * {@linkplain Colors#BLUE BLUE}, {@linkplain Colors#PURPLE PURPLE}, {@linkplain Colors#PINK PINK},
	 * {@linkplain Colors#GRAY GRAY}, {@linkplain Colors#BLACK BLACK}.</p>
	 *
	 * @param color The color
	 *
	 * @see Colors Colors - for a list of some of the available colors
	 */
	void setBackground(final Color color);

	/**
	 * <p>Defines the style (bold, italic, etc.) for the node being inspected.
	 * You can pass any {@linkplain SWT} constant here.</p>
	 *
	 * <p>To use more than one style, you have to concatenate them with "{@code |}".
	 * If you want to use bold and italic, you should do: {@code setStyle(SWT.BOLD | SWT.ITALIC)} (or use the helper in {@linkplain Styles#multi(int...)})</p>
	 *
	 * <p>For a list of valid text styles, you can use the ones present in {@linkplain Styles}:
	 * <ul>
	 *     <li> {@linkplain Styles#NORMAL NORMAL}</li>
	 *     <li> {@linkplain Styles#BOLD BOLD}</li>
	 *     <li> {@linkplain Styles#ITALIC ITALIC}</li>
	 *     <li> {@linkplain Styles#STRIKETHROUGH STRIKETHROUGH}</li>
	 *     <li> {@linkplain Styles#UNDERLINE UNDERLINE}</li>
	 *     <li> {@linkplain Styles#multi(int...) multi(BOLD, ITALIC)}</li>
	 * </ul>
	 * </p>
	 *
	 * @param style The style
	 *
	 * @see Styles Styles - for a list of some of the available styles
	 */
	void setStyle(final int style);

	/**
	 * Defines an icon for the node being inspected.
	 *
	 * @param iconResource The icon resource
	 */
	void setIcon(final String iconResource);

	/**
	 * Add tooltip for the node being inspected.
	 *
	 * @param tooltip The tooltip
	 */
	void addTooltip(final String tooltip);

	/**
	 * Add tooltips for the node being inspected.
	 *
	 * @param tooltips The tooltips
	 */
	void addTooltips(final Iterable<String> tooltips);



	/* -- INFO ABOUT THE FILE BEING PARSED -- */

	/**
	 * The file being parsed.
	 *
	 * @return the file
	 */
	Path getFile();

	/**
	 * The character position in the file where this node starts.
	 *
	 * @return the starting position
	 */
	int getPosStart();

	/**
	 * The character position in the file where this node ends.
	 *
	 * @return the ending position
	 */
	int getPosEnd();

	/**
	 * The line in the file where this node starts.
	 *
	 * @return the starting line
	 */
	int getLineStart();

	/**
	 * The line in the file where this node ends.
	 *
	 * @return the ending line
	 */
	int getLineEnd();

	/**
	 * The character position inside of line in the file where this node starts.
	 *
	 * @return the starting line
	 */
	int getColumnStart();

	/**
	 * The character position inside of line in the file where this node ends.
	 *
	 * @return the ending line
	 */
	int getColumnEnd();
}
