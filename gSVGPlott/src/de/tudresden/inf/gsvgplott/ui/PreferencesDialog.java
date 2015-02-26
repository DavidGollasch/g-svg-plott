package de.tudresden.inf.gsvgplott.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;

public class PreferencesDialog extends Dialog {

	protected Object result;
	protected Shell shlPreferences;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PreferencesDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlPreferences.open();
		shlPreferences.layout();
		Display display = getParent().getDisplay();
		while (!shlPreferences.isDisposed()) {
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
		shlPreferences = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlPreferences.setSize(405, 204);
		shlPreferences.setText("Preferences");
		
		Button btnOk = new Button(shlPreferences, SWT.NONE);
		btnOk.setBounds(89, 144, 95, 28);
		btnOk.setText("OK");
		
		Button btnResetDefault = new Button(shlPreferences, SWT.NONE);
		btnResetDefault.setBounds(190, 144, 104, 28);
		btnResetDefault.setText("Reset Default");
		
		Button btnCancel = new Button(shlPreferences, SWT.NONE);
		btnCancel.setBounds(300, 144, 95, 28);
		btnCancel.setText("Cancel");
		
		CLabel lblNoSettingsAvailable = new CLabel(shlPreferences, SWT.NONE);
		lblNoSettingsAvailable.setBounds(129, 10, 165, 19);
		lblNoSettingsAvailable.setText("no settings available yet");

	}

}
