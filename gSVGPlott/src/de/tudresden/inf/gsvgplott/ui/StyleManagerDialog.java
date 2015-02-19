package de.tudresden.inf.gsvgplott.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;

public class StyleManagerDialog extends Dialog {

	protected Object result;
	protected Shell shlStyleManager;

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
		shlStyleManager = new Shell(getParent(), getStyle());
		shlStyleManager.setSize(289, 277);
		shlStyleManager.setText("Style Manager");
		
		CLabel lblStyles = new CLabel(shlStyleManager, SWT.NONE);
		lblStyles.setBounds(10, 10, 62, 19);
		lblStyles.setText("Styles:");
		
		List list = new List(shlStyleManager, SWT.BORDER);
		list.setItems(new String[] {"One", "Two", "Three"});
		list.setBounds(10, 35, 169, 180);
		
		Button btnMoveUp = new Button(shlStyleManager, SWT.NONE);
		btnMoveUp.setBounds(185, 35, 95, 28);
		btnMoveUp.setText("Move Up");
		
		Button btnMoveDowb = new Button(shlStyleManager, SWT.NONE);
		btnMoveDowb.setBounds(185, 69, 95, 28);
		btnMoveDowb.setText("Move Down");
		
		Button btnRemove = new Button(shlStyleManager, SWT.NONE);
		btnRemove.setBounds(185, 103, 95, 28);
		btnRemove.setText("Remove");
		
		Button btnRename = new Button(shlStyleManager, SWT.NONE);
		btnRename.setBounds(185, 137, 95, 28);
		btnRename.setText("Rename");
		
		Button btnApply = new Button(shlStyleManager, SWT.NONE);
		btnApply.setBounds(10, 221, 95, 28);
		btnApply.setText("Apply");
		
		Button btnCancel = new Button(shlStyleManager, SWT.NONE);
		btnCancel.setBounds(111, 221, 95, 28);
		btnCancel.setText("Cancel");

	}
}
