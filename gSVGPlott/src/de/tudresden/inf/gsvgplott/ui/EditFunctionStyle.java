package de.tudresden.inf.gsvgplott.ui;

import java.awt.Color;

import javax.swing.JFrame;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.explodingpixels.macwidgets.HudWindow;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.FillLayout;

public class EditFunctionStyle extends Dialog {

	protected Object result;
	protected Shell shlTest;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public EditFunctionStyle(Shell parent, int style) {
		super(parent, SWT.BORDER | SWT.CLOSE);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlTest.open();
		shlTest.layout();
		Display display = getParent().getDisplay();
		while (!shlTest.isDisposed()) {
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
		shlTest = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.TOOL);
		shlTest.setText("Line Style");
		shlTest.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		shlTest.setSize(343, 131);
		shlTest.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shlTest, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		GridLayout gl_composite = new GridLayout(4, false);
		gl_composite.marginBottom = 5;
		gl_composite.marginTop = 5;
		gl_composite.marginRight = 5;
		gl_composite.marginLeft = 5;
		gl_composite.horizontalSpacing = 10;
		gl_composite.verticalSpacing = 10;
		composite.setLayout(gl_composite);
		
		Button btnClose = new Button(composite, SWT.NONE);
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shlTest.close();
			}
		});
		btnClose.setText("Close");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		CLabel label = new CLabel(composite, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label.setText("Screen:");
		
		Combo combo = new Combo(composite, SWT.READ_ONLY);
		combo.setItems(new String[] {"----", "===="});
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo.select(0);
		
		Spinner spinner = new Spinner(composite, SWT.BORDER);
		spinner.setMinimum(1);
		spinner.setSelection(1);
		
		Combo combo_1 = new Combo(composite, SWT.READ_ONLY);
		combo_1.setItems(new String[] {"black", "gray", "green", "red", "blue", "yellow", "white"});
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo_1.select(0);
		
		CLabel label_1 = new CLabel(composite, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setText("Print:");
		
		Combo combo_2 = new Combo(composite, SWT.READ_ONLY);
		combo_2.setItems(new String[] {"----", "===="});
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo_2.select(0);
		
		Spinner spinner_1 = new Spinner(composite, SWT.BORDER);
		spinner_1.setMinimum(1);
		spinner_1.setSelection(1);
		
		Combo combo_3 = new Combo(composite, SWT.READ_ONLY);
		combo_3.setItems(new String[] {"black", "gray", "green", "red", "blue", "yellow", "white"});
		combo_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo_3.select(0);

	}
}
