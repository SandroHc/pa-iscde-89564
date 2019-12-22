package pa.iscde.minimap.internal;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import pa.iscde.minimap.internal.extension.Extension;
import pa.iscde.minimap.internal.extension.ExtensionRule;

public class SettingsDialog extends TitleAreaDialog {

	private static final Logger LOGGER = Logger.getLogger(SettingsDialog.class);

	private Tree tree;


	/**
	 * Instantiate a new title area dialog.
	 *
	 * @param parentShell the parent SWT shell
	 */
	public SettingsDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Settings");
		setMessage("Enable your desired inspection rules.", IMessageProvider.NONE);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(new GridLayout(1, false));

		tree = new Tree(container, SWT.CHECK | SWT.BORDER);
		tree.setLayoutData(container.getLayoutData());
		fillTree(tree);

		return area;
	}

	/**
	 * Helper method to fill a tree with data
	 *
	 * @param tree the tree to fill
	 */
	private void fillTree(Tree tree) {
		// Turn off drawing to avoid flicker
		tree.setRedraw(false);

		tree.addListener(SWT.Selection, event -> {
			TreeItem item = (TreeItem) event.item;
			LOGGER.debug((event.detail == SWT.CHECK ? "CHECK" : "SELECT") + " EVENT: " + item);

			if (event.detail != SWT.CHECK) {
				return;
			}

			// Set checked state for himself and all it's children
			setCheckedState(item, item.getChecked());

			// Refresh checked state for it's parent
			if (item.getParentItem() != null) {
				item.getParentItem().setChecked(getCheckedState(item.getParentItem()));
			}
		});

		for (Extension ext : MinimapView.getInstance().getExtensions()) {
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText(ext.name + " [" + ext.id + ']');
			item.setData(ext);
			item.setExpanded(true);

			for (ExtensionRule rule : ext.rules) {
				TreeItem child = new TreeItem(item, SWT.NONE);
				child.setText(rule.name + " [" + rule.id + ']');
				child.setData(rule);

				if (rule.isErrored()) {
					child.setGrayed(true);
					child.setChecked(false);
				} else {
					child.setChecked(SettingsManager.isEnabled(rule));
				}
			}

			item.setChecked(getCheckedState(item));
		}

		tree.setRedraw(true);
	}

	private void setCheckedState(TreeItem item, boolean checked) {
		item.setChecked(checked);
		for (TreeItem child : item.getItems()) {
			setCheckedState(child, checked);
		}
	}

	/**
	 * Find the checked state based on it's children's state.
	 *
	 * @param item The item to find the checked state
	 * @return {@code true} if at least one of it's children is checked, {@code false} otherwise
	 */
	private boolean getCheckedState(TreeItem item) {
		if (item.getItems().length < 1) {
			return item.getChecked();
		}

		for (TreeItem child : item.getItems()) {
			if (getCheckedState(child)) {
				return true;
			}
		}

		return false;
	}

	private void save(TreeItem item) {
		if (item.getData() instanceof ExtensionRule) {
			ExtensionRule rule = (ExtensionRule) item.getData();
			rule.setEnabled(item.getChecked());
			SettingsManager.setEnabled(rule);
		}

		for (TreeItem child : item.getItems()) {
			save(child);
		}
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		for (TreeItem item : tree.getItems()) {
			save(item);
		}

		boolean changed = SettingsManager.save();

		if (changed) {
			MinimapView view = MinimapView.getInstance();
			view.parseFile(view.getActiveFile());
		}

		super.okPressed();
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
}
