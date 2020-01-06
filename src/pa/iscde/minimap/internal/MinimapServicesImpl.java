/*
 * Copyright (c) 2020.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Authors:
 *  - Sandro Marques <https://sandrohc.net>
 */

package pa.iscde.minimap.internal;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import pa.iscde.minimap.MinimapActivator;
import pa.iscde.minimap.internal.extension.ExtensionRule;
import pa.iscde.minimap.internal.settings.SettingsManager;
import pa.iscde.minimap.service.MinimapListener;
import pa.iscde.minimap.service.MinimapServices;

public class MinimapServicesImpl implements MinimapServices {

	@Override
	public void addListener(MinimapListener listener) {
		MinimapActivator.getInstance().addListener(listener);
	}

	@Override
	public void removeListener(MinimapListener listener) {
		MinimapActivator.getInstance().removeListener(listener);
	}

	@Override
	public void enableInspection(String extensionId, String inspectionId) {
		setRuleState(extensionId, inspectionId, true);
	}

	@Override
	public void disableInspection(String extensionId, String inspectionId) {
		setRuleState(extensionId, inspectionId, false);
	}

	private void setRuleState(String extensionId, String inspectionId, boolean state) {
		getRule(extensionId, inspectionId).ifPresent(rule -> {
			rule.setEnabled(state);
			SettingsManager.update(rule);
			SettingsManager.save();
		});
	}

	private Optional<ExtensionRule> getRule(String extensionId, String inspectionId) {
		return MinimapView.getInstance().getExtensions().stream()
				.filter(ext -> ext.id.equals(extensionId))
				.map(ext -> ext.rules)
				.flatMap(Collection::stream)
				.filter(rule -> rule.id.equals(inspectionId))
				.findAny();
	}

	@Override
	public File getOpenedFile() {
		return MinimapView.getInstance().getActiveFile();
	}

	@Override
	public void openFile(File file) {
		MinimapView.getInstance().parseFile(file);
	}
}
