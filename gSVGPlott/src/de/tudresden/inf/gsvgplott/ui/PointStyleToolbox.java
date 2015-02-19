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
	private Table table;
	private Table table_2;

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
		shlTest.setSize(209, 371);
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
		
		CLabel lblText = new CLabel(compositeScreen, SWT.NONE);
		lblText.setText("Font");
		new Label(compositeScreen, SWT.NONE);
		
		CLabel lblThisIsAn = new CLabel(compositeScreen, SWT.NONE);
		lblThisIsAn.setText("This is an example.");
		
		Button btnChange = new Button(compositeScreen, SWT.NONE);
		btnChange.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnChange.setText("Change");
		
		CTabItem tbtmPrint = new CTabItem(tabFolder, SWT.NONE);
		tbtmPrint.setText("Print");
		
		Composite compositePrint = new Composite(tabFolder, SWT.NONE);
		tbtmPrint.setControl(compositePrint);
		compositePrint.setLayout(new GridLayout(2, false));
		
		CLabel label = new CLabel(compositePrint, SWT.NONE);
		label.setText("Style");
		
		CLabel label_1 = new CLabel(compositePrint, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("(selected)");
		
		table = new Table(compositePrint, SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_table.minimumHeight = 50;
		gd_table.widthHint = 132;
		table.setLayoutData(gd_table);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(193);
		tableColumn.setText("Line Style");
		tableColumn.setResizable(false);
		
		TableItem tableItem = new TableItem(table, 0);
		tableItem.setText("Solid");
		tableItem.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_1 = new TableItem(table, 0);
		tableItem_1.setText("Dashed");
		tableItem_1.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_2 = new TableItem(table, 0);
		tableItem_2.setText("Dotted");
		tableItem_2.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		CLabel label_4 = new CLabel(compositePrint, SWT.NONE);
		label_4.setText("Color");
		
		CLabel label_5 = new CLabel(compositePrint, SWT.NONE);
		label_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_5.setText("(selected)");
		
		table_2 = new Table(compositePrint, SWT.FULL_SELECTION);
		GridData gd_table_2 = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_table_2.minimumHeight = 50;
		table_2.setLayoutData(gd_table_2);
		
		TableColumn tableColumn_4 = new TableColumn(table_2, SWT.NONE);
		tableColumn_4.setWidth(193);
		tableColumn_4.setText("Line Color");
		tableColumn_4.setResizable(false);
		
		TableItem tableItem_13 = new TableItem(table_2, 0);
		tableItem_13.setText("Color 1");
		
		TableItem tableItem_14 = new TableItem(table_2, 0);
		tableItem_14.setText("Color 2");
		
		TableItem tableItem_15 = new TableItem(table_2, 0);
		tableItem_15.setText("Color 3");
		
		CLabel label_6 = new CLabel(compositePrint, SWT.NONE);
		label_6.setText("Font");
		new Label(compositePrint, SWT.NONE);
		
		CLabel label_7 = new CLabel(compositePrint, SWT.NONE);
		label_7.setText("This is an example.");
		
		Button button = new Button(compositePrint, SWT.NONE);
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		button.setText("Change");

	}
}
