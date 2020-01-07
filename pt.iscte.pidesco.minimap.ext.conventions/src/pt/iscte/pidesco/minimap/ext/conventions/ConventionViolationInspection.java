package pt.iscte.pidesco.minimap.ext.conventions;

import java.util.function.Predicate;

import org.eclipse.jdt.core.dom.ASTNode;

import pa.iscde.minimap.extensibility.InspectionContext;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.utils.Colors;
import pt.iscte.pidesco.conventions.problems.Problem;

public class ConventionViolationInspection implements MinimapInspection {

	@Override
	public void inspect(ASTNode node, InspectionContext context) {
		final String filePath = context.getFile().toAbsolutePath().toString();
		
		Predicate<Problem> isCurrentLine = p -> context.getLineStart() <= p.getStartingLine() && context.getLineEnd() >= p.getStartingLine();
		
		Activator.getConventionsCheckerServices().getFileProblems(filePath).stream()
				.filter(isCurrentLine)
				.forEach(p -> {
					context.addTooltip("Violation: " + p.getProblemType().getProperName());
					context.setBackground(Colors.BLUE);
				});
	}

}
