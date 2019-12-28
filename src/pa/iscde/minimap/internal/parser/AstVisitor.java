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

package pa.iscde.minimap.internal.parser;

import java.util.Collection;

import org.apache.log4j.Logger;
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
import pa.iscde.minimap.internal.InspectionContextImpl;
import pa.iscde.minimap.internal.extension.ExtensionRule;

public class AstVisitor extends ASTVisitor {

	private static final Logger LOGGER = Logger.getLogger(AstVisitor.class);

	private final MinimapFile minimapFile;
	private final Collection<ExtensionRule> rules;

	public AstVisitor(MinimapFile minimapFile, Collection<ExtensionRule> rules) {
		this.minimapFile = minimapFile;
		this.rules = rules;
	}

	private void inspect(final ASTNode node) {
		final InspectionContextImpl<ASTNode> context;
		try {
			context = new InspectionContextImpl<>(node, minimapFile);
		} catch(Exception e) {
			LOGGER.error("Unable to create context for node: " + node.getClass().getSimpleName(), e);
			return;
		}

		for (ExtensionRule extRule : rules) {
			try {
				extRule.getRule().inspect(node, context);
			} catch (Exception e) {
				LOGGER.error("Error running inspection rule: " + extRule.id, e);
			}
		}
	}

	@Override
	public boolean visit(Block node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(Javadoc node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SingleVariableDeclaration node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayAccess node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayCreation node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayInitializer node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(BlockComment node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(LineComment node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(CreationReference node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ConditionalExpression node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchExpression node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchCase node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(LambdaExpression node) {
		inspect(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(ReturnStatement node) {
		inspect(node);
		return super.visit(node);
	}
}
