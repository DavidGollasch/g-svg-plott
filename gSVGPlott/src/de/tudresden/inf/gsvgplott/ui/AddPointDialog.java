package de.tudresden.inf.gsvgplott.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class AddPointDialog extends Dialog {

	protected Object result;
	protected Shell shlAddPoint;
	private Text txtX;
	private Text txtY;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AddPointDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAddPoint.open();
		shlAddPoint.layout();
		Display display = getParent().getDisplay();
		while (!shlAddPoint.isDisposed()) {
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
		shlAddPoint = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlAddPoint.setSize(274, 117);
		shlAddPoint.setText("Add Point");
		shlAddPoint.setLayout(new GridLayout(4, false));
		
		CLabel lblInsertAName = new CLabel(shlAddPoint, SWT.NONE);
		lblInsertAName.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblInsertAName.setText("Insert point coordinates and click OK.");
		
		CLabel lblX = new CLabel(shlAddPoint, SWT.NONE);
		lblX.setText("X:");
		
		txtX = new Text(shlAddPoint, SWT.BORDER);
		txtX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		CLabel lblY = new CLabel(shlAddPoint, SWT.NONE);
		lblY.setText("Y:");
		
		txtY = new Text(shlAddPoint, SWT.BORDER);
		txtY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite = new Composite(shlAddPoint, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));
		
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
