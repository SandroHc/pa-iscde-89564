package pa.iscde.minimap.extensibility;

import org.eclipse.jdt.core.dom.ASTNode;
import pa.iscde.minimap.service.InspectionContext;

/**
 * Adds inspection rules that can be activated/deactivated in the Minimap view.
 */
public interface MinimapInspection {

	/**
	 * Inspect an AST Node.
	 */
	<N extends ASTNode> void inspect(N node, InspectionContext context);

}
