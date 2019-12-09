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

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchExpression;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.swt.graphics.Color;
import pa.iscde.minimap.service.FileEvent;
import pa.iscde.minimap.service.FileEventImpl;
import pa.iscde.minimap.service.constants.Colors;

// TODO: um visitor por extensão (permite que a extensão termine os visitors)? ou um visitor partilhado por todas as extensões?
public class AstVisitor extends ASTVisitor {

	private final MinimapFile minimapFile;

	public AstVisitor(MinimapFile minimapFile) {
		this.minimapFile = minimapFile;
	}

	private static <N extends ASTNode> FileEvent<N> createEvent(N node, String tooltip, Color background) {
		FileEventImpl<N> event = new FileEventImpl<>(node);
		event.addTooltip(tooltip);
		event.setBackground(background);
		return event;
	}

	@Override
	public boolean visit(Block node) {
		FileEvent<Block> event = createEvent(node, "BLOCK", Colors.PURPLE);
		minimapFile.update(event);
		return super.visit(node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		minimapFile.update(createEvent(node, "FIELD DECLARATION", Colors.RED));
		return super.visit(node);
	}

	@Override
	public boolean visit(Javadoc node) {
		minimapFile.update(createEvent(node, "JAVADOC", Colors.GREEN));
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		minimapFile.update(createEvent(node, "METHOD DECLARATION", Colors.ORANGE));
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		minimapFile.update(createEvent(node, "METHOD INVOCATION", Colors.ORANGE));
		return super.visit(node);
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		minimapFile.update(createEvent(node, "SINGLE VARIABLE DECLARATION", Colors.YELLOW));
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		minimapFile.update(createEvent(node, "TYPE DECLARATION", Colors.WHITE));
		return super.visit(node);
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		minimapFile.update(createEvent(node, "ANOOTATION TYPE DECLARATION", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		minimapFile.update(createEvent(node, "ANONYMOUS CLASSE DECLARATION", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayAccess node) {
		minimapFile.update(createEvent(node, "ARRAY ACCESS", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayCreation node) {
		minimapFile.update(createEvent(node, "ARRAY CREATION", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayInitializer node) {
		minimapFile.update(createEvent(node, "ARRAY INITIALIZER", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(BlockComment node) {
		minimapFile.update(createEvent(node, "BLOCK COMMENT", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(LineComment node) {
		minimapFile.update(createEvent(node, "LINE COMMENT", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(CreationReference node) {
		minimapFile.update(createEvent(node, "CREATION REFERENCE", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		minimapFile.update(createEvent(node, "ENUM CONSTANT DECLARATION", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		minimapFile.update(createEvent(node, "ENUM DECLARATION", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(ConditionalExpression node) {
		minimapFile.update(createEvent(node, "CONDITIONAL EXPRESSION", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		minimapFile.update(createEvent(node, "IF STATEMENT", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchExpression node) {
		minimapFile.update(createEvent(node, "SWITCH EXPRESSION", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchCase node) {
		minimapFile.update(createEvent(node, "SWITCH CASE", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		minimapFile.update(createEvent(node, "FOR STATEMENT", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		minimapFile.update(createEvent(node, "ENHANCED FOR STATEMENT", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		minimapFile.update(createEvent(node, "WHILE STATEMENT", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		minimapFile.update(createEvent(node, "IMPORT DECLARATION", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(LambdaExpression node) {
		minimapFile.update(createEvent(node, "LAMBDA EXPRESSION", Colors.GRAY));
		return super.visit(node);
	}

	@Override
	public boolean visit(ReturnStatement node) {
		minimapFile.update(createEvent(node, "RETURN STATEMENT", Colors.GRAY));
		return super.visit(node);
	}
}
