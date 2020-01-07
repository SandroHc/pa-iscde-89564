package pt.iscte.pidesco.minimap.ext.metrics;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import pa.iscde.metrics.MetricEnum;
import pa.iscde.minimap.extensibility.InspectionContext;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;

public class MetricsInspection implements MinimapInspection {

	@Override
	public void inspect(ASTNode node, InspectionContext context) {
		// Display metrics over the class declaration
		if (node instanceof TypeDeclaration) {
			ClassElement classElement = new ClassElement(null, context.getFile().toFile());
			
			for (MetricEnum metric : MetricEnum.values()) {
				float value = Activator.getMetricsServices().getMetric(metric.name(), classElement);
				context.addTooltip("Metric " + metric.name() + ": " + value);
			}
		}
	}

}
