package pa.iscde.minimap.internal;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class SettingsTool implements PidescoTool {

	@Override
	public void run(boolean selected) {
//		Activator.getContext();
//		ProjectBrowserActivator.getInstance().refreshWorkspace();

		MinimapView minimap = MinimapView.getInstance();

		// create a dialog with ok and cancel buttons and a question icon
		MessageBox dialog = new MessageBox(minimap.getRoot().getShell(), SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
		dialog.setText("My info");
		dialog.setMessage("Do you really want to do this?");

		// open dialog and await user selection
		int returnCode = dialog.open();
	}

}
