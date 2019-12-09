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

package pa.iscde.minimap.internal.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.eclipse.jdt.core.dom.ASTNode;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pa.iscde.minimap.service.constants.Styles;
import pa.iscde.minimap.service.FileEvent;

public class MinimapFile {

	public final File file;
	public final List<MinimapLine> lines;

	public MinimapFile(File file) {
		this.file = file;
		this.lines = loadLines(this.file.toPath());
	}

	private static List<MinimapLine> loadLines(Path path) {
		String contents;
		try {
			byte[] bytes = Files.readAllBytes(path);
			contents = new String(bytes, StandardCharsets.UTF_8);
		} catch (IOException e) {
			contents = "Error loading file: " + path;
			e.printStackTrace();
		}

		String[] lines = contents.split(System.lineSeparator());
		List<MinimapLine> list = new ArrayList<>(lines.length);

		final int newlineOffset = System.lineSeparator().length();
		int offset = 0;
		for (int num = 1; num <= lines.length; num++) {
			String content = lines[num-1];
			MinimapLine line = new MinimapLine(offset, num, content);

			offset += content.length() + newlineOffset;
			list.add(line);
		}

		return list;
	}

	public Collection<MinimapLine> parse(JavaEditorServices services) {
		services.parseFile(file, new AstVisitor(this));
		return this.lines;
	}

	/**
	 * Returns a range of lines. Note that the lines must be 1-base indexes, meaning they start with the value 1.
	 *
	 * @param from	The first line (inclusive)
	 * @param to	The last line (inclusive)
	 * @return range of lines
	 */
	public List<MinimapLine> getLines(int from, int to) {
		// Ajust values to 0-based indexes
		from -= 1;
		to   -= 1;

		// Verify array index bounds
		Validate.validIndex(this.lines, from);
		Validate.validIndex(this.lines, to);

		return lines.subList(from, to + 1);
	}

	public <N extends ASTNode> void update(FileEvent<N> event) {
		List<MinimapLine> lines = getLines(event.getLineStart(), event.getLineEnd());

		boolean isFirstLine = true;
		for (MinimapLine line : lines) {
			if (event.getForeground() != null) {
				line.foreground = event.getForeground();
			}

			if (event.getBackground() != null) {
				line.background = event.getBackground();
			}

			if (event.getStyle() != Styles.NORMAL) {
				line.style = event.getStyle();
			}

			// Apply these properties only to the first line
			if (isFirstLine) {
				if (event.getIcon() != null) {
					line.icon = event.getIcon();
				}

				if (!event.getTooltips().isEmpty()) {
					line.tooltips.addAll(event.getTooltips());
				}

				isFirstLine = false;
			}
		}
	}
}
