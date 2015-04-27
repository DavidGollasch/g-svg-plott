package de.tudresden.inf.gsvgplott.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.tudresden.inf.gsvgplott.data.style.OverrideStyle;
import java.util.ResourceBundle;
import org.eclipse.swt.layout.RowData;

public class CssStyleOverrideDialog extends Dialog {
	private ResourceBundle DICT = ResourceBundle.getBundle("de.tudresden.inf.gsvgplott.ui.util.messages"); //$NON-NLS-1$

	protected Object result;
	protected Shell shlCssStyleOverride;
	private StyledText styledText;
	
	/**
	 * Data Exchange Object
	 */
	private OverrideStyle overrideStyle;
	private Button btnOk;
	private Button btnReset;
	private Button btnCancel;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CssStyleOverrideDialog(Shell parent, int style, OverrideStyle oldOverrideStyle) {
		super(parent, style);
		setText(DICT.getString("CssStyleOverrideDialog.this.text")); //$NON-NLS-1$
		
		// set object reference
		overrideStyle = oldOverrideStyle;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		
		//initialize data
		setInitialData();
		
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
		shlCssStyleOverride.setText(DICT.getString("CssStyleOverrideDialog.shlCssStyleOverride.text")); //$NON-NLS-1$
		shlCssStyleOverride.setLayout(new GridLayout(1, false));
		
		CLabel lblCustomCss = new CLabel(shlCssStyleOverride, SWT.NONE);
		lblCustomCss.setText(DICT.getString("CssStyleOverrideDialog.lblCustomCss.text")); //$NON-NLS-1$
		
		styledText = new StyledText(shlCssStyleOverride, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		styledText.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		styledText.setText(DICT.getString("CssStyleOverrideDialog.styledText.text")); //$NON-NLS-1$
		
		Composite composite = new Composite(shlCssStyleOverride, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		btnOk = new Button(composite, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setNewData();
				shlCssStyleOverride.close();
			}
		});
		btnOk.setText(DICT.getString("CssStyleOverrideDialog.btnOk.text")); //$NON-NLS-1$
		
		btnReset = new Button(composite, SWT.NONE);
		btnReset.setLayoutData(new RowData(123, SWT.DEFAULT));
		btnReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.setText("");
			}
		});
		btnReset.setText(DICT.getString("CssStyleOverrideDialog.btnReset.text")); //$NON-NLS-1$
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new RowData(113, SWT.DEFAULT));
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlCssStyleOverride.close();
			}
		});
		btnCancel.setText(DICT.getString("CssStyleOverrideDialog.btnCancel.text")); //$NON-NLS-1$
		
		shlCssStyleOverride.setDefaultButton(btnOk);

	}
	
	/**
	 * Load override style data and set form controls accordingly
	 */
	private void setInitialData() {
		styledText.setText(overrideStyle.getCssStyle());
	}
	
	/**
	 * Store newly typed css override
	 */
	private void setNewData() {
		overrideStyle.setCssStyle(styledText.getText());
	}
	
	/**
	 * For getting the new current css override styling
	 * @return
	 */
	public OverrideStyle getNewOverrideStyle() {
		return overrideStyle;
	}

}
