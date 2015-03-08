package de.tudresden.inf.gsvgplott.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class CssStyleOverrideDialog extends Dialog {

	protected Object result;
	protected Shell shlCssStyleOverride;
	private StyledText styledText;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CssStyleOverrideDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlCssStyleOverride.open();
		shlCssStyleOverride.layout();
		Display display = getParent().getDisplay();
		while (!shlCssStyleOverride.isDisposed()) {
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
		shlCssStyleOverride = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
		shlCssStyleOverride.setSize(419, 293);
		shlCssStyleOverride.setText("CSS Style Override");
		shlCssStyleOverride.setLayout(new GridLayout(1, false));
		
		CLabel lblCustomCss = new CLabel(shlCssStyleOverride, SWT.NONE);
		lblCustomCss.setText("Custom CSS:");
		
		styledText = new StyledText(shlCssStyleOverride, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		styledText.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		styledText.setText("This is text.");
		
		Composite composite = new Composite(shlCssStyleOverride, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button btnOk = new Button(composite, SWT.NONE);
		btnOk.setText("OK");
		
		Button btnReset = new Button(composite, SWT.NONE);
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				styledText.setText("");
			}
		});
		btnReset.setText("Reset");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");

	}

}
