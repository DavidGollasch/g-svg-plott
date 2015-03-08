package de.tudresden.inf.gsvgplott.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StyleManagerDialog extends Dialog {

	protected Object result;
	protected Shell shlStyleManager;
	private List list;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public StyleManagerDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlStyleManager.open();
		shlStyleManager.layout();
		Display display = getParent().getDisplay();
		while (!shlStyleManager.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlStyleManager = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlStyleManager.setSize(314, 277);
		shlStyleManager.setText("Style Manager");
		
		CLabel lblStyles = new CLabel(shlStyleManager, SWT.NONE);
		lblStyles.setBounds(10, 10, 62, 19);
		lblStyles.setText("Styles:");
		
		list = new List(shlStyleManager, SWT.BORDER);
		list.setItems(new String[] {"One", "Two", "Three"});
		list.setBounds(10, 35, 169, 180);
		
		Button btnRemove = new Button(shlStyleManager, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int[] selection = list.getSelectionIndices();
				list.remove(selection);
				
				//TODO remove styles completely
			}
		});
		btnRemove.setBounds(185, 35, 119, 28);
		btnRemove.setText("Remove");
		
		Button btnRename = new Button(shlStyleManager, SWT.NONE);
		btnRename.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO
			}
		});
		btnRename.setBounds(185, 69, 119, 28);
		btnRename.setText("Rename");
		
		Button btnApply = new Button(shlStyleManager, SWT.NONE);
		btnApply.setBounds(10, 221, 95, 28);
		btnApply.setText("Apply");
		
		Button btnClose = new Button(shlStyleManager, SWT.NONE);
		btnClose.setBounds(111, 221, 95, 28);
		btnClose.setText("Close");

		sortList();
	}
	
	/**
	 * Sorts the displayed styles list alphabetically
	 */
	private void sortList() {
		String[] arr_items = list.getItems();
		java.util.List<String> items = java.util.Arrays.asList(arr_items);
		java.util.Collections.sort(items);
		list.setItems((String[])items.toArray());
	}
}
