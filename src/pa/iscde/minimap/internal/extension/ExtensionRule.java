package pa.iscde.minimap.internal.extension;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import pa.iscde.minimap.extensibility.MinimapInspection;
import pa.iscde.minimap.internal.SettingsManager;

import static pa.iscde.minimap.extensibility.inspections.NoopInspection.NOOP;

public class ExtensionRule {

	private static final Logger LOGGER = Logger.getLogger(ExtensionRule.class);

	public final String id;
	public final String name;
	public final String description;
	private final MinimapInspection rule;

	private final boolean errored;
	private boolean enabled;

	public ExtensionRule(Extension ext, String id, String name, String description, MinimapInspection rule) {
		this.id = ext.id + ":" + id;
		this.name = name;
		this.description = description;
		this.rule = rule;

		this.errored = rule == NOOP;

		LOGGER.info("Loaded rule: " + this.id);
	}

	public ExtensionRule(Extension ext, IConfigurationElement conf) {
		this(ext, conf.getAttribute("id"), conf.getAttribute("name"),
				conf.getAttribute("description"), loadRule(ext, conf));
	}

	private static MinimapInspection loadRule(Extension ext, IConfigurationElement conf) {
		try {
			return (MinimapInspection) conf.createExecutableExtension("class");
		} catch (CoreException e) {
			String id = ext.id + ":" + conf.getAttribute("id");
			LOGGER.error("Error loading extension rule:" + id, e);
			return NOOP;
		}
	}

	public MinimapInspection getRule() {
		return isEnabled() ? rule : NOOP;
	}

	public boolean isErrored() {
		return errored;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExtensionRule that = (ExtensionRule) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "ExtensionRule[" + id + ']';
	}
}
