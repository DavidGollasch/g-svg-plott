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

public class LineStyleToolbox extends Dialog {

	protected Object result;
	protected Shell shlTest;
	private Table tableScreenLineStyle;
	private Table tableScreenLineWidth;
	private Table tableScreenLineColor;
	private Table table;
	private Table table_1;
	private Table table_2;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public LineStyleToolbox(Shell parent, int style) {
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
		tableItemScreenStyleSolid.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyleSolid.setText("Solid");
		
		TableItem tableItemScreenStyleDashed = new TableItem(tableScreenLineStyle, SWT.NONE);
		tableItemScreenStyleDashed.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyleDashed.setText("Dashed");
		
		TableItem tableItemScreenStyleDotted = new TableItem(tableScreenLineStyle, SWT.NONE);
		tableItemScreenStyleDotted.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyleDotted.setText("Dotted");
		
		CLabel lblScreenWidth = new CLabel(compositeScreen, SWT.NONE);
		lblScreenWidth.setText("Width");
		
		CLabel lblScreenWidthSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenWidthSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScreenWidthSelected.setText("(selected)");
		
		tableScreenLineWidth = new Table(compositeScreen, SWT.FULL_SELECTION);
		GridData gd_tableScreenLineWidth = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableScreenLineWidth.minimumHeight = 50;
		tableScreenLineWidth.setLayoutData(gd_tableScreenLineWidth);
		
		TableColumn tblclmnScreenLineWidth = new TableColumn(tableScreenLineWidth, SWT.NONE);
		tblclmnScreenLineWidth.setWidth(193);
		tblclmnScreenLineWidth.setText("Line Width");
		tblclmnScreenLineWidth.setResizable(false);
		
		TableItem tableItemScreenWidth1 = new TableItem(tableScreenLineWidth, SWT.NONE);
		tableItemScreenWidth1.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth1.setText("1px");
		
		TableItem tableItemScreenWidth2 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth2.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth2.setText("2px");
		
		TableItem tableItemScreenWidth3 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth3.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth3.setText("3px");
		
		TableItem tableItemScreenWidth4 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth4.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth4.setText("4px");
		
		TableItem tableItemScreenWidth5 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth5.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth5.setText("5px");
		
		TableItem tableItemScreenWidth6 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth6.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth6.setText("6px");
		
		TableItem tableItemScreenWidth7 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth7.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth7.setText("7px");
		
		TableItem tableItemScreenWidth8 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth8.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth8.setText("8px");
		
		TableItem tableItemScreenWidth9 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth9.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth9.setText("9px");
		
		TableItem tableItemScreenWidth10 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth10.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth10.setText("10px");
		
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
		tableItem.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_1 = new TableItem(table, 0);
		tableItem_1.setText("Dashed");
		tableItem_1.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_2 = new TableItem(table, 0);
		tableItem_2.setText("Dotted");
		tableItem_2.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		CLabel label_2 = new CLabel(compositePrint, SWT.NONE);
		label_2.setText("Width");
		
		CLabel label_3 = new CLabel(compositePrint, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setText("(selected)");
		
		table_1 = new Table(compositePrint, SWT.FULL_SELECTION);
		GridData gd_table_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_table_1.minimumHeight = 50;
		table_1.setLayoutData(gd_table_1);
		
		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		tableColumn_2.setWidth(193);
		tableColumn_2.setText("Line Width");
		tableColumn_2.setResizable(false);
		
		TableItem tableItem_3 = new TableItem(table_1, 0);
		tableItem_3.setText("1px");
		tableItem_3.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_4 = new TableItem(table_1, 0);
		tableItem_4.setText("2px");
		tableItem_4.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_5 = new TableItem(table_1, 0);
		tableItem_5.setText("3px");
		tableItem_5.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_6 = new TableItem(table_1, 0);
		tableItem_6.setText("4px");
		tableItem_6.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_7 = new TableItem(table_1, 0);
		tableItem_7.setText("5px");
		tableItem_7.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_8 = new TableItem(table_1, 0);
		tableItem_8.setText("6px");
		tableItem_8.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_9 = new TableItem(table_1, 0);
		tableItem_9.setText("7px");
		tableItem_9.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_10 = new TableItem(table_1, 0);
		tableItem_10.setText("8px");
		tableItem_10.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_11 = new TableItem(table_1, 0);
		tableItem_11.setText("9px");
		tableItem_11.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_12 = new TableItem(table_1, 0);
		tableItem_12.setText("10px");
		tableItem_12.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
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
