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

public class PointStyleToolbox extends Dialog {

	protected Object result;
	protected Shell shlTest;
	private Table tableScreenLineStyle;
	private Table tableScreenLineColor;
	private Table tablePrintLineStyle;
	private Table tablePrintLineColor;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PointStyleToolbox(Shell parent, int style) {
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
		shlTest.setText("Point Style");
		shlTest.setSize(209, 305);
		shlTest.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CTabFolder tabFolder = new CTabFolder(shlTest, SWT.BORDER);
		tabFolder.setBorderVisible(false);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmScreen = new CTabItem(tabFolder, SWT.NONE);
		tbtmScreen.setText("Screen");
		
		Composite compositeScreen = new Composite(tabFolder, SWT.NONE);
		tbtmScreen.setControl(compositeScreen);
		compositeScreen.setLayout(new GridLayout(2, false));
		
		CLabel lblScreenStyle = new CLabel(compositeScreen, SWT.NONE);
		lblScreenStyle.setText("Style");
		
		CLabel lblScreenStyleSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenStyleSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScreenStyleSelected.setText("(selected)");
		
		tableScreenLineStyle = new Table(compositeScreen, SWT.FULL_SELECTION);
		GridData gd_tableScreenLineStyle = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableScreenLineStyle.minimumHeight = 50;
		tableScreenLineStyle.setLayoutData(gd_tableScreenLineStyle);
		
		TableColumn tblclmnScreenLineStyle = new TableColumn(tableScreenLineStyle, SWT.NONE);
		tblclmnScreenLineStyle.setResizable(false);
		tblclmnScreenLineStyle.setWidth(193);
		tblclmnScreenLineStyle.setText("Line Style");
		
		TableItem tableItemScreenStyleSolid = new TableItem(tableScreenLineStyle, SWT.NONE);
		tableItemScreenStyleSolid.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyleSolid.setText("Solid");
		
		TableItem tableItemScreenStyleDashed = new TableItem(tableScreenLineStyle, SWT.NONE);
		tableItemScreenStyleDashed.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyleDashed.setText("Dashed");
		
		TableItem tableItemScreenStyleDotted = new TableItem(tableScreenLineStyle, SWT.NONE);
		tableItemScreenStyleDotted.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyleDotted.setText("Dotted");
		
		CLabel lblScreenColor = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColor.setText("Color");
		
		CLabel lblScreenColorSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScreenColorSelected.setText("(selected)");
		
		tableScreenLineColor = new Table(compositeScreen, SWT.FULL_SELECTION);
		GridData gd_tableScreenLineColor = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableScreenLineColor.minimumHeight = 50;
		tableScreenLineColor.setLayoutData(gd_tableScreenLineColor);
		
		TableColumn tblclmnScreenLineColor = new TableColumn(tableScreenLineColor, SWT.NONE);
		tblclmnScreenLineColor.setWidth(193);
		tblclmnScreenLineColor.setText("Line Color");
		tblclmnScreenLineColor.setResizable(false);
		
		TableItem tableItemScreenColor1 = new TableItem(tableScreenLineColor, SWT.NONE);
		tableItemScreenColor1.setText("Color 1");
		
		TableItem tableItemScreenColor2 = new TableItem(tableScreenLineColor, 0);
		tableItemScreenColor2.setText("Color 2");
		
		TableItem tableItemScreenColor3 = new TableItem(tableScreenLineColor, 0);
		tableItemScreenColor3.setText("Color 3");
		
		Button btnScreenBordered = new Button(compositeScreen, SWT.CHECK);
		btnScreenBordered.setText("Bordered");
		new Label(compositeScreen, SWT.NONE);
		
		CTabItem tbtmPrint = new CTabItem(tabFolder, SWT.NONE);
		tbtmPrint.setText("Print");
		
		Composite compositePrint = new Composite(tabFolder, SWT.NONE);
		tbtmPrint.setControl(compositePrint);
		compositePrint.setLayout(new GridLayout(2, false));
		
		CLabel lblPrintStyle = new CLabel(compositePrint, SWT.NONE);
		lblPrintStyle.setText("Style");
		
		CLabel lblPrintStyleSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintStyleSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrintStyleSelected.setText("(selected)");
		
		tablePrintLineStyle = new Table(compositePrint, SWT.FULL_SELECTION);
		GridData gd_tablePrintLineStyle = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tablePrintLineStyle.minimumHeight = 50;
		gd_tablePrintLineStyle.widthHint = 132;
		tablePrintLineStyle.setLayoutData(gd_tablePrintLineStyle);
		
		TableColumn tblclmnPrintLineStyle = new TableColumn(tablePrintLineStyle, SWT.NONE);
		tblclmnPrintLineStyle.setWidth(193);
		tblclmnPrintLineStyle.setText("Line Style");
		tblclmnPrintLineStyle.setResizable(false);
		
		TableItem tableItem = new TableItem(tablePrintLineStyle, 0);
		tableItem.setText("Solid");
		tableItem.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_1 = new TableItem(tablePrintLineStyle, 0);
		tableItem_1.setText("Dashed");
		tableItem_1.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_2 = new TableItem(tablePrintLineStyle, 0);
		tableItem_2.setText("Dotted");
		tableItem_2.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		CLabel lblPrintColor = new CLabel(compositePrint, SWT.NONE);
		lblPrintColor.setText("Color");
		
		CLabel lblPrintColorSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrintColorSelected.setText("(selected)");
		
		tablePrintLineColor = new Table(compositePrint, SWT.FULL_SELECTION);
		GridData gd_tablePrintLineColor = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tablePrintLineColor.minimumHeight = 50;
		tablePrintLineColor.setLayoutData(gd_tablePrintLineColor);
		
		TableColumn tblclmnPrintLineColor = new TableColumn(tablePrintLineColor, SWT.NONE);
		tblclmnPrintLineColor.setWidth(193);
		tblclmnPrintLineColor.setText("Line Color");
		tblclmnPrintLineColor.setResizable(false);
		
		TableItem tableItem_13 = new TableItem(tablePrintLineColor, 0);
		tableItem_13.setText("Color 1");
		
		TableItem tableItem_14 = new TableItem(tablePrintLineColor, 0);
		tableItem_14.setText("Color 2");
		
		TableItem tableItem_15 = new TableItem(tablePrintLineColor, 0);
		tableItem_15.setText("Color 3");
		
		Button btnPrintBordered = new Button(compositePrint, SWT.CHECK);
		btnPrintBordered.setText("Bordered");
		new Label(compositePrint, SWT.NONE);

	}
}
