package de.tudresden.inf.gsvgplott.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpDialog extends Dialog {
	private ResourceBundle DICT = ResourceBundle.getBundle("de.tudresden.inf.gsvgplott.ui.util.messages"); //$NON-NLS-1$

	protected Object result;
	protected Shell shlHelp;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public HelpDialog(Shell parent, int style) {
		super(parent, style);
		setText(DICT.getString("HelpDialog.this.text")); //$NON-NLS-1$
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlHelp.open();
		shlHelp.layout();
		Display display = getParent().getDisplay();
		while (!shlHelp.isDisposed()) {
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
		shlHelp = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
		shlHelp.setSize(600, 383);
		shlHelp.setText(DICT.getString("HelpDialog.shlHelp.text")); //$NON-NLS-1$
		shlHelp.setLayout(new GridLayout(1, false));
		
		Browser browser = new Browser(shlHelp, SWT.NONE);
		
		URL url = HelpDialog.class.getResource("/de/tudresden/inf/gsvgplott/docs/Help_de.htm");
		if(url != null)
			browser.setUrl(url.toString());
		else
			browser.setText("<html><body><h1>No Help Document Found.</h1></body></html>");
		
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Button btnClose = new Button(shlHelp, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlHelp.close();
			}
		});
		btnClose.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnClose.setText(DICT.getString("HelpDialog.btnClose.text")); //$NON-NLS-1$
	}

}
