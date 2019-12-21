package pa.iscde.minimap.extensibility.inspections;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.graphics.Color;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.service.InspectionContext;
import pa.iscde.minimap.utils.Colors;

import static pa.iscde.minimap.utils.Colors.ALL_COLORS;

public class BaseInspection implements MinimapInspection {

	@Override
	public <N extends ASTNode> void inspect(N node, InspectionContext context) {
		String name = node.getClass().getSimpleName();

		context.addTooltip("L" + context.getLineStart() + ": " + name);
		context.setBackground(getColor(node));
	}

	private Color getColor(ASTNode node) {
		if (node instanceof TypeDeclaration) {
			return Colors.WHITE;
		}

		return ALL_COLORS[Math.abs(node.getClass().getName().hashCode()) % ALL_COLORS.length];
	}
}
