package pa.iscde.minimap.internal;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.graphics.Color;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.service.InspectionContext;
import pa.iscde.minimap.utils.Colors;

class DemoInspection implements MinimapInspection {

	private static final Color[] colors = new Color[] {
			Colors.BLUE, Colors.BROWN, Colors.GRAY, Colors.GREEN, Colors.ORANGE,
			Colors.PINK, Colors.PURPLE, Colors.YELLOW, Colors.GRAY
	};

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

		return colors[Math.abs(node.getClass().getName().hashCode()) % colors.length];
	}
}
