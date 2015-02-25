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
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class GeneralStyleToolbox extends Dialog {

	protected Object result;
	protected Shell shlTest;
	private Table tableScreenBgColor;
	private Table tablePrintBgColor;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GeneralStyleToolbox(Shell parent, int style) {
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
		shlTest.setText("General Style");
		shlTest.setSize(209, 193);
		shlTest.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CTabFolder tabFolder = new CTabFolder(shlTest, SWT.BORDER);
		tabFolder.setBorderVisible(false);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmScreen = new CTabItem(tabFolder, SWT.NONE);
		tbtmScreen.setText("Screen");
		
		Composite compositeScreen = new Composite(tabFolder, SWT.NONE);
		tbtmScreen.setControl(compositeScreen);
		compositeScreen.setLayout(new GridLayout(2, false));
		
		CLabel lblScreenFont = new CLabel(compositeScreen, SWT.NONE);
		lblScreenFont.setText("Font");
		
		Button btnScreenFont = new Button(compositeScreen, SWT.NONE);
		btnScreenFont.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnScreenFont.setText("...");
		
		CLabel lblScreenFontSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenFontSelected.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblScreenFontSelected.setText("(selected)");
		
		CLabel lblScreenColor = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColor.setText("Background Color");
		
		CLabel lblScreenColorSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScreenColorSelected.setText("(selected)");
		
		tableScreenBgColor = new Table(compositeScreen, SWT.FULL_SELECTION);
		GridData gd_tableScreenBgColor = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableScreenBgColor.minimumHeight = 50;
		tableScreenBgColor.setLayoutData(gd_tableScreenBgColor);
		
		TableColumn tblclmnScreenBgColor = new TableColumn(tableScreenBgColor, SWT.NONE);
		tblclmnScreenBgColor.setWidth(193);
		tblclmnScreenBgColor.setText("Line Color");
		tblclmnScreenBgColor.setResizable(false);
		
		TableItem tableItemScreenColor1 = new TableItem(tableScreenBgColor, SWT.NONE);
		tableItemScreenColor1.setText("Color 1");
		
		TableItem tableItemScreenColor2 = new TableItem(tableScreenBgColor, 0);
		tableItemScreenColor2.setText("Color 2");
		
		TableItem tableItemScreenColor3 = new TableItem(tableScreenBgColor, 0);
		tableItemScreenColor3.setText("Color 3");
		
		CTabItem tbtmPrint = new CTabItem(tabFolder, SWT.NONE);
		tbtmPrint.setText("Print");
		
		Composite compositePrint = new Composite(tabFolder, SWT.NONE);
		tbtmPrint.setControl(compositePrint);
		compositePrint.setLayout(new GridLayout(2, false));
		
		CLabel lblPrintFont = new CLabel(compositePrint, SWT.NONE);
		lblPrintFont.setText("Font");
		
		Button btnPrintFont = new Button(compositePrint, SWT.NONE);
		btnPrintFont.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnPrintFont.setText("...");
		
		CLabel lblPrintFontSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintFontSelected.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblPrintFontSelected.setText("(selected)");
		
		CLabel lblPrintColor = new CLabel(compositePrint, SWT.NONE);
		lblPrintColor.setText("Background Color");
		
		CLabel lblPrintColorSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrintColorSelected.setText("(selected)");
		
		tablePrintBgColor = new Table(compositePrint, SWT.FULL_SELECTION);
		tablePrintBgColor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		TableColumn tblclmnPrintBgColor = new TableColumn(tablePrintBgColor, SWT.NONE);
		tblclmnPrintBgColor.setWidth(193);
		tblclmnPrintBgColor.setText("Line Color");
		tblclmnPrintBgColor.setResizable(false);
		
		TableItem tableItem = new TableItem(tablePrintBgColor, 0);
		tableItem.setText("Color 1");
		
		TableItem tableItem_1 = new TableItem(tablePrintBgColor, 0);
		tableItem_1.setText("Color 2");
		
		TableItem tableItem_2 = new TableItem(tablePrintBgColor, 0);
		tableItem_2.setText("Color 3");

	}
}
