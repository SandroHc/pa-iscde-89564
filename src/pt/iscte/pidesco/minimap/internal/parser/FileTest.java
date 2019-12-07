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

package pt.iscte.pidesco.minimap.internal.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.eclipse.jdt.core.dom.Block;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.minimap.internal.Styles;
import pt.iscte.pidesco.minimap.service.FileEvent;

public class FileTest {

	public final File file;
	public final List<LineTest> lines;

	public FileTest(File file) {
		this.file = file;
		this.lines = loadLines(this.file.toPath());
	}

	private static List<LineTest> loadLines(Path path) {
		String contents;
		try {
			byte[] bytes = Files.readAllBytes(path);
			contents = new String(bytes, StandardCharsets.UTF_8);
		} catch (IOException e) {
			contents = "Error loading file: " + path;
			e.printStackTrace();
		}

		String[] lines = contents.split(System.lineSeparator());
		List<LineTest> list = new ArrayList<>(lines.length);

		for (int num = 1; num <= lines.length; num++) {
			String content = lines[num-1];
			LineTest line = new LineTest(num, content);

			list.add(line);
		}

		return list;
	}

	public Collection<LineTest> parse(JavaEditorServices services) {
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
	public List<LineTest> getLines(int from, int to) {
		// Ajust values to 0-based indexes
		from -= 1;
		to   -= 1;

		// Verify array index bounds
		Validate.validIndex(this.lines, from);
		Validate.validIndex(this.lines, to);

		return lines.subList(from, to + 1);
	}

	public void update(FileEvent<Block> event) {
		List<LineTest> lines = getLines(event.getLineStart(), event.getLineEnd());

		boolean isFirstLine = true;
		for (LineTest line : lines) {
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
