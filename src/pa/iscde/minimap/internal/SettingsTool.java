package pa.iscde.minimap.internal;


import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IDialogConstants;
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
