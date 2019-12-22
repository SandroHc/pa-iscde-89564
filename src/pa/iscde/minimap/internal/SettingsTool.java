package pa.iscde.minimap.internal;


import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class SettingsTool implements PidescoTool {

	@Override
	public void run(boolean selected) {
//		Activator.getContext();
//		ProjectBrowserActivator.getInstance().refreshWorkspace();

		MinimapView minimap = MinimapView.getInstance();

		SettingsDialog dialog = new SettingsDialog(minimap.getRoot().getShell());
		int returnCode = dialog.open();

		if (returnCode == IDialogConstants.OK_ID) {
			System.out.println("OK clicked");
		} else if (returnCode == IDialogConstants.CANCEL_ID) {
			System.out.println("CANCEL clicked");
		} else {
			System.out.println("UNK: " + returnCode);
		}
	}

}
