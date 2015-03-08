package de.tudresden.inf.gsvgplott.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class StoreStyleDialog extends Dialog {

	protected Object result;
	protected Shell shlStoreStyle;
	private Text txtTitle;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public StoreStyleDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlStoreStyle.open();
		shlStoreStyle.layout();
		Display display = getParent().getDisplay();
		while (!shlStoreStyle.isDisposed()) {
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
		shlStoreStyle = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlStoreStyle.setSize(415, 117);
		shlStoreStyle.setText("Store Style");
		shlStoreStyle.setLayout(new GridLayout(2, false));
		
		CLabel lblInsertAName = new CLabel(shlStoreStyle, SWT.NONE);
		lblInsertAName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblInsertAName.setText("Insert a name for the style to be saved and click OK.");
		
		CLabel lblName = new CLabel(shlStoreStyle, SWT.NONE);
		lblName.setText("Name:");
		
		txtTitle = new Text(shlStoreStyle, SWT.BORDER);
		txtTitle.setText("Title");
		txtTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite = new Composite(shlStoreStyle, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		
		Button btnOK = new Button(composite, SWT.NONE);
		GridData gd_btnOK = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnOK.widthHint = 70;
		btnOK.setLayoutData(gd_btnOK);
		btnOK.setText("OK");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancel.widthHint = 70;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");

	}
}
