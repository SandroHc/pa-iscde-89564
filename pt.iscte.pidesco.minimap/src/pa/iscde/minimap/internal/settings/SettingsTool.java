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

package pa.iscde.minimap.internal.settings;


import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IDialogConstants;
import pa.iscde.minimap.internal.MinimapView;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class SettingsTool implements PidescoTool {

	private static final Logger LOGGER = Logger.getLogger(SettingsTool.class);

	@Override
	public void run(boolean selected) {
		MinimapView minimap = MinimapView.getInstance();

		SettingsDialog dialog = new SettingsDialog(minimap.getRoot().getShell());
		int returnCode = dialog.open();

		if (returnCode == IDialogConstants.OK_ID) {
			LOGGER.debug("OK clicked");
		} else if (returnCode == IDialogConstants.CANCEL_ID) {
			LOGGER.debug("CANCEL clicked");
		}
	}

}
