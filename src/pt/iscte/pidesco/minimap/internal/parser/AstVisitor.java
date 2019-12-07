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

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ModuleDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import pt.iscte.pidesco.minimap.internal.Colors;
import pt.iscte.pidesco.minimap.service.FileEvent;
import pt.iscte.pidesco.minimap.service.FileEventImpl;

// TODO: um visitor por extensão (permite que a extensão termine os visitors)? ou um visitor partilhado por todas as extensões?
public class AstVisitor extends ASTVisitor {

	private final FileTest fileTest;

	public AstVisitor(FileTest fileTest) {
		this.fileTest = fileTest;
	}

	@Override
	public boolean visit(Block node) {
		FileEvent<Block> event = new FileEventImpl<>(node);
		event.addTooltip("Test");
		event.setBackground(Colors.PURPLE);

		fileTest.update(event);

		return super.visit(node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		return super.visit(node);
	}

	@Override
	public boolean visit(Javadoc node) {
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		return super.visit(node);
	}

	@Override
	public boolean visit(ModuleDeclaration node) {
		return super.visit(node);
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		return super.visit(node);
	}

	@Override
	public void endVisit(Block node) {
		super.endVisit(node);
	}

	@Override
	public void endVisit(FieldDeclaration node) {
		super.endVisit(node);
	}

	@Override
	public void endVisit(Javadoc node) {
		super.endVisit(node);
	}
}
