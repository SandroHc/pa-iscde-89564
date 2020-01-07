package pt.iscte.pidesco.minimap.ext.search;

import org.eclipse.jdt.core.dom.ASTNode;
import pa.iscde.minimap.extensibility.InspectionContext;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.utils.Colors;
import pa.iscde.minimap.utils.Styles;

public class SearchInspection implements MinimapInspection {

	@Override
	public void inspect(ASTNode node, InspectionContext context) {
		Activator activator = Activator.getInstance();
		
		// Check for any search
		if (activator.searchKeyword == null)
			return;
		
		// Check if any of the search results is present in this node
		boolean match = false;
		for (int line : activator.searchResults) {
			if (context.getLineStart() <= line && context.getLineEnd() >= line) {
				match = true;
				break;
			}
		}
		
		if (match) {
			context.addTooltip("Search result for '" + activator.searchKeyword + "' on lines: " + activator.searchResults);
			context.setBackground(Colors.BLUE);
			context.setStyle(Styles.UNDERLINE);
		}
	}

}
