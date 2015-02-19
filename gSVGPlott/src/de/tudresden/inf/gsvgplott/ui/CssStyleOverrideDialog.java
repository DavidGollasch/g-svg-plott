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

public class CssStyleOverrideDialog extends Dialog {

	protected Object result;
	protected Shell shlCssStyleOverride;

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
		shlCssStyleOverride = new Shell(getParent(), getStyle());
		shlCssStyleOverride.setSize(419, 293);
		shlCssStyleOverride.setText("CSS Style Override");
		RowLayout rl_shlCssStyleOverride = new RowLayout(SWT.VERTICAL);
		rl_shlCssStyleOverride.fill = true;
		shlCssStyleOverride.setLayout(rl_shlCssStyleOverride);
		
		CLabel lblCustomCss = new CLabel(shlCssStyleOverride, SWT.NONE);
		lblCustomCss.setText("Custom CSS:");
		
		StyledText styledText = new StyledText(shlCssStyleOverride, SWT.BORDER);
		styledText.setLayoutData(new RowData(406, 202));
		styledText.setText("This is text.");
		
		Composite composite = new Composite(shlCssStyleOverride, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button btnOk = new Button(composite, SWT.NONE);
		btnOk.setText("OK");
		
		Button btnReset = new Button(composite, SWT.NONE);
		btnReset.setText("Reset");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");

	}

}
