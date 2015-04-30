package de.tudresden.inf.gsvgplott.ui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import de.tudresden.inf.gsvgplott.data.style.AreaStyle;
import de.tudresden.inf.gsvgplott.data.style.LineStyle;
import de.tudresden.inf.gsvgplott.data.style.TextStyle;
import de.tudresden.inf.gsvgplott.data.style.palettes.ColorPalette;
import de.tudresden.inf.gsvgplott.data.style.palettes.LineTypePalette;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import java.util.ResourceBundle;

public class LineStyleToolbox extends Dialog {
	private ResourceBundle DICT = ResourceBundle.getBundle("de.tudresden.inf.gsvgplott.ui.util.messages"); //$NON-NLS-1$

	protected Object result;
	protected Shell shlTest;
	private Table tableScreenLineStyle;
	private Table tableScreenLineWidth;
	private Table tableScreenLineColor;
	private Table tablePrintLineStyle;
	private Table tablePrintLineWidth;
	private Table tablePrintLineColor;
	
	/**
	 * Location the window should open
	 */
	private Point openingLocation = null;
	private CLabel lblScreenStyleSelected;
	private CLabel lblScreenWidthSelected;
	private CLabel lblScreenColorSelected;
	private CLabel lblPrintStyleSelected;
	private CLabel lblPrintWidthSelected;
	private CLabel lblPrintColorSelected;
	
	/**
	 * Data Exchange Objects
	 */
	private LineStyle screenLineStyle, printLineStyle;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public LineStyleToolbox(Shell parent, int style, LineStyle oldScreenLineStyle, LineStyle oldPrintLineStyle) {
		super(parent, SWT.BORDER | SWT.CLOSE);
		setText(DICT.getString("LineStyleToolbox.this.text")); //$NON-NLS-1$
		
		screenLineStyle = oldScreenLineStyle;
		printLineStyle = oldPrintLineStyle;
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
		if(openingLocation != null) {
			shlTest.setLocation(openingLocation.x, openingLocation.y);
		}
		while (!shlTest.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
	
	/**
	 * Specify the location the dialog should open
	 * @param pt new location
	 */
	public void setOpeningLocation(Point pt) {
		openingLocation = pt;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlTest = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.TOOL | SWT.APPLICATION_MODAL);
		shlTest.setText(DICT.getString("LineStyleToolbox.shlTest.text")); //$NON-NLS-1$
		shlTest.setSize(215, 310);
		shlTest.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CTabFolder tabFolder = new CTabFolder(shlTest, SWT.BORDER);
		tabFolder.setBorderVisible(false);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmScreen = new CTabItem(tabFolder, SWT.NONE);
		tbtmScreen.setText(DICT.getString("LineStyleToolbox.tbtmScreen.text")); //$NON-NLS-1$
		
		Composite compositeScreen = new Composite(tabFolder, SWT.NONE);
		tbtmScreen.setControl(compositeScreen);
		compositeScreen.setLayout(new GridLayout(2, false));
		
		CLabel lblScreenStyle = new CLabel(compositeScreen, SWT.NONE);
		lblScreenStyle.setText(DICT.getString("LineStyleToolbox.lblScreenStyle.text")); //$NON-NLS-1$
		
		lblScreenStyleSelected = new CLabel(compositeScreen, SWT.NONE);
		GridData gd_lblScreenStyleSelected = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblScreenStyleSelected.widthHint = 100;
		lblScreenStyleSelected.setLayoutData(gd_lblScreenStyleSelected);
		lblScreenStyleSelected.setText(DICT.getString("LineStyleToolbox.lblScreenStyleSelected.text")); //$NON-NLS-1$
		
		tableScreenLineStyle = new Table(compositeScreen, SWT.FULL_SELECTION);
		tableScreenLineStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tableScreenLineStyle.getSelection()[0];
				//lblScreenStyleSelected.setText(selectedItem.getText());
				lblScreenStyleSelected.setText("");
				lblScreenStyleSelected.setBackground(selectedItem.getImage());
				
				String styleName = selectedItem.getText();
				screenLineStyle.setStyle(LineTypePalette.getPalette().get(styleName.toUpperCase()));
			}
		});
		GridData gd_tableScreenLineStyle = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableScreenLineStyle.minimumHeight = 50;
		tableScreenLineStyle.setLayoutData(gd_tableScreenLineStyle);
		
		TableColumn tblclmnScreenLineStyle = new TableColumn(tableScreenLineStyle, SWT.NONE);
		tblclmnScreenLineStyle.setResizable(false);
		tblclmnScreenLineStyle.setWidth(193);
		tblclmnScreenLineStyle.setText(DICT.getString("LineStyleToolbox.tblclmnScreenLineStyle.text")); //$NON-NLS-1$
		
		TableItem tableItemScreenStyle1 = new TableItem(tableScreenLineStyle, SWT.NONE);
		tableItemScreenStyle1.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyle1.setText(DICT.getString("LineStyleToolbox.tableItemScreenStyle1.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenStyle2 = new TableItem(tableScreenLineStyle, SWT.NONE);
		tableItemScreenStyle2.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyle2.setText(DICT.getString("LineStyleToolbox.tableItemScreenStyle2.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenStyle3 = new TableItem(tableScreenLineStyle, SWT.NONE);
		tableItemScreenStyle3.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyle3.setText(DICT.getString("LineStyleToolbox.tableItemScreenStyle3.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenStyle4 = new TableItem(tableScreenLineStyle, 0);
		tableItemScreenStyle4.setText(DICT.getString("LineStyleToolbox.tableItemScreenStyle4.text(java.lang.String)")); //$NON-NLS-1$
		tableItemScreenStyle4.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItemScreenStyle5 = new TableItem(tableScreenLineStyle, 0);
		tableItemScreenStyle5.setText(DICT.getString("LineStyleToolbox.tableItemScreenStyle5.text(java.lang.String)")); //$NON-NLS-1$
		tableItemScreenStyle5.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItemScreenStyle6 = new TableItem(tableScreenLineStyle, 0);
		tableItemScreenStyle6.setText(DICT.getString("LineStyleToolbox.tableItemScreenStyle6.text(java.lang.String)")); //$NON-NLS-1$
		tableItemScreenStyle6.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		CLabel lblScreenWidth = new CLabel(compositeScreen, SWT.NONE);
		lblScreenWidth.setText(DICT.getString("LineStyleToolbox.lblScreenWidth.text")); //$NON-NLS-1$
		
		lblScreenWidthSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenWidthSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScreenWidthSelected.setText(DICT.getString("LineStyleToolbox.lblScreenWidthSelected.text")); //$NON-NLS-1$
		
		tableScreenLineWidth = new Table(compositeScreen, SWT.FULL_SELECTION);
		tableScreenLineWidth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tableScreenLineWidth.getSelection()[0];
				lblScreenWidthSelected.setText(selectedItem.getText());
				//lblScreenWidthSelected.setBackground(selectedItem.getImage());
				
				String newWidth = selectedItem.getText();
				newWidth = newWidth.replace("px", "");
				screenLineStyle.setWidth(Integer.parseInt(newWidth));
			}
		});
		GridData gd_tableScreenLineWidth = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableScreenLineWidth.minimumHeight = 50;
		tableScreenLineWidth.setLayoutData(gd_tableScreenLineWidth);
		
		TableColumn tblclmnScreenLineWidth = new TableColumn(tableScreenLineWidth, SWT.NONE);
		tblclmnScreenLineWidth.setWidth(193);
		tblclmnScreenLineWidth.setText(DICT.getString("LineStyleToolbox.tblclmnScreenLineWidth.text")); //$NON-NLS-1$
		tblclmnScreenLineWidth.setResizable(false);
		
		TableItem tableItemScreenWidth1 = new TableItem(tableScreenLineWidth, SWT.NONE);
		tableItemScreenWidth1.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth1.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth1.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenWidth2 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth2.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth2.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth2.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenWidth3 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth3.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth3.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth3.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenWidth4 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth4.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth4.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth4.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenWidth5 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth5.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth5.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth5.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenWidth6 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth6.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth6.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth6.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenWidth7 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth7.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth7.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth7.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenWidth8 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth8.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth8.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth8.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenWidth9 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth9.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth9.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth9.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenWidth10 = new TableItem(tableScreenLineWidth, 0);
		tableItemScreenWidth10.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenWidth10.setText(DICT.getString("LineStyleToolbox.tableItemScreenWidth10.text(java.lang.String)")); //$NON-NLS-1$
		
		CLabel lblScreenColor = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColor.setText(DICT.getString("LineStyleToolbox.lblScreenColor.text")); //$NON-NLS-1$
		
		lblScreenColorSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScreenColorSelected.setText(DICT.getString("LineStyleToolbox.lblScreenColorSelected.text")); //$NON-NLS-1$
		
		tableScreenLineColor = new Table(compositeScreen, SWT.FULL_SELECTION);
		tableScreenLineColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tableScreenLineColor.getSelection()[0];
				lblScreenColorSelected.setText(selectedItem.getText());
				lblScreenColorSelected.setBackground(selectedItem.getImage());
				
				Color newColor = new Color(selectedItem.getImage().getImageData().getPixel(1, 1));
				screenLineStyle.setColor(newColor);
			}
		});
		GridData gd_tableScreenLineColor = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableScreenLineColor.minimumHeight = 50;
		tableScreenLineColor.setLayoutData(gd_tableScreenLineColor);
		
		TableColumn tblclmnScreenLineColor = new TableColumn(tableScreenLineColor, SWT.NONE);
		tblclmnScreenLineColor.setWidth(193);
		tblclmnScreenLineColor.setText(DICT.getString("LineStyleToolbox.tblclmnScreenLineColor.text")); //$NON-NLS-1$
		tblclmnScreenLineColor.setResizable(false);
		
		TableItem tableItemScreenColor1 = new TableItem(tableScreenLineColor, SWT.NONE);
		tableItemScreenColor1.setText(DICT.getString("LineStyleToolbox.tableItemScreenColor1.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenColor2 = new TableItem(tableScreenLineColor, 0);
		tableItemScreenColor2.setText(DICT.getString("LineStyleToolbox.tableItemScreenColor2.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItemScreenColor3 = new TableItem(tableScreenLineColor, 0);
		tableItemScreenColor3.setText(DICT.getString("LineStyleToolbox.tableItemScreenColor3.text(java.lang.String)")); //$NON-NLS-1$
		
		CTabItem tbtmPrint = new CTabItem(tabFolder, SWT.NONE);
		tbtmPrint.setText(DICT.getString("LineStyleToolbox.tbtmPrint.text")); //$NON-NLS-1$
		
		Composite compositePrint = new Composite(tabFolder, SWT.NONE);
		tbtmPrint.setControl(compositePrint);
		compositePrint.setLayout(new GridLayout(2, false));
		
		CLabel lblPrintStyle = new CLabel(compositePrint, SWT.NONE);
		lblPrintStyle.setText(DICT.getString("LineStyleToolbox.lblPrintStyle.text")); //$NON-NLS-1$
		
		lblPrintStyleSelected = new CLabel(compositePrint, SWT.NONE);
		GridData gd_lblPrintStyleSelected = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblPrintStyleSelected.widthHint = 100;
		lblPrintStyleSelected.setLayoutData(gd_lblPrintStyleSelected);
		lblPrintStyleSelected.setText(DICT.getString("LineStyleToolbox.lblPrintStyleSelected.text")); //$NON-NLS-1$
		
		tablePrintLineStyle = new Table(compositePrint, SWT.FULL_SELECTION);
		tablePrintLineStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tablePrintLineStyle.getSelection()[0];
				//lblPrintStyleSelected.setText(selectedItem.getText());
				lblPrintStyleSelected.setText("");
				lblPrintStyleSelected.setBackground(selectedItem.getImage());
				
				String styleName = selectedItem.getText();
				printLineStyle.setStyle(LineTypePalette.getPalette().get(styleName.toUpperCase()));
			}
		});
		GridData gd_tablePrintLineStyle = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tablePrintLineStyle.minimumHeight = 50;
		gd_tablePrintLineStyle.widthHint = 132;
		tablePrintLineStyle.setLayoutData(gd_tablePrintLineStyle);
		
		TableColumn tblclmnPrintLineStyle = new TableColumn(tablePrintLineStyle, SWT.NONE);
		tblclmnPrintLineStyle.setWidth(193);
		tblclmnPrintLineStyle.setText(DICT.getString("LineStyleToolbox.tblclmnPrintLineStyle.text")); //$NON-NLS-1$
		tblclmnPrintLineStyle.setResizable(false);
		
		TableItem tableItem = new TableItem(tablePrintLineStyle, 0);
		tableItem.setText(DICT.getString("LineStyleToolbox.tableItem.text(java.lang.String)")); //$NON-NLS-1$
		tableItem.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_1 = new TableItem(tablePrintLineStyle, 0);
		tableItem_1.setText(DICT.getString("LineStyleToolbox.tableItem_1.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_1.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_2 = new TableItem(tablePrintLineStyle, 0);
		tableItem_2.setText(DICT.getString("LineStyleToolbox.tableItem_2.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_2.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		CLabel lblPrintWidth = new CLabel(compositePrint, SWT.NONE);
		lblPrintWidth.setText(DICT.getString("LineStyleToolbox.lblPrintWidth.text")); //$NON-NLS-1$
		
		lblPrintWidthSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintWidthSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrintWidthSelected.setText(DICT.getString("LineStyleToolbox.lblPrintWidthSelected.text")); //$NON-NLS-1$
		
		tablePrintLineWidth = new Table(compositePrint, SWT.FULL_SELECTION);
		tablePrintLineWidth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tablePrintLineWidth.getSelection()[0];
				lblPrintWidthSelected.setText(selectedItem.getText());
				//lblPrintWidthSelected.setBackground(selectedItem.getImage());
				
				String newWidth = selectedItem.getText();
				newWidth = newWidth.replace("px", "");
				printLineStyle.setWidth(Integer.parseInt(newWidth));
			}
		});
		GridData gd_tablePrintLineWidth = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tablePrintLineWidth.minimumHeight = 50;
		tablePrintLineWidth.setLayoutData(gd_tablePrintLineWidth);
		
		TableColumn tblclmnPrintLineWidth = new TableColumn(tablePrintLineWidth, SWT.NONE);
		tblclmnPrintLineWidth.setWidth(193);
		tblclmnPrintLineWidth.setText(DICT.getString("LineStyleToolbox.tblclmnPrintLineWidth.text")); //$NON-NLS-1$
		tblclmnPrintLineWidth.setResizable(false);
		
		TableItem tableItem_3 = new TableItem(tablePrintLineWidth, 0);
		tableItem_3.setText(DICT.getString("LineStyleToolbox.tableItem_3.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_3.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_4 = new TableItem(tablePrintLineWidth, 0);
		tableItem_4.setText(DICT.getString("LineStyleToolbox.tableItem_4.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_4.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_5 = new TableItem(tablePrintLineWidth, 0);
		tableItem_5.setText(DICT.getString("LineStyleToolbox.tableItem_5.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_5.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_6 = new TableItem(tablePrintLineWidth, 0);
		tableItem_6.setText(DICT.getString("LineStyleToolbox.tableItem_6.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_6.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_7 = new TableItem(tablePrintLineWidth, 0);
		tableItem_7.setText(DICT.getString("LineStyleToolbox.tableItem_7.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_7.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_8 = new TableItem(tablePrintLineWidth, 0);
		tableItem_8.setText(DICT.getString("LineStyleToolbox.tableItem_8.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_8.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_9 = new TableItem(tablePrintLineWidth, 0);
		tableItem_9.setText(DICT.getString("LineStyleToolbox.tableItem_9.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_9.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_10 = new TableItem(tablePrintLineWidth, 0);
		tableItem_10.setText(DICT.getString("LineStyleToolbox.tableItem_10.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_10.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_11 = new TableItem(tablePrintLineWidth, 0);
		tableItem_11.setText(DICT.getString("LineStyleToolbox.tableItem_11.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_11.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		TableItem tableItem_12 = new TableItem(tablePrintLineWidth, 0);
		tableItem_12.setText(DICT.getString("LineStyleToolbox.tableItem_12.text(java.lang.String)")); //$NON-NLS-1$
		tableItem_12.setImage(SWTResourceManager.getImage(LineStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		CLabel lblPrintColor = new CLabel(compositePrint, SWT.NONE);
		lblPrintColor.setText(DICT.getString("LineStyleToolbox.lblPrintColor.text")); //$NON-NLS-1$
		
		lblPrintColorSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrintColorSelected.setText(DICT.getString("LineStyleToolbox.lblPrintColorSelected.text")); //$NON-NLS-1$
		
		tablePrintLineColor = new Table(compositePrint, SWT.FULL_SELECTION);
		tablePrintLineColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tablePrintLineColor.getSelection()[0];
				lblPrintColorSelected.setText(selectedItem.getText());
				lblPrintColorSelected.setBackground(selectedItem.getImage());
				
				Color newColor = new Color(selectedItem.getImage().getImageData().getPixel(1, 1));
				printLineStyle.setColor(newColor);
			}
		});
		GridData gd_tablePrintLineColor = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tablePrintLineColor.minimumHeight = 50;
		tablePrintLineColor.setLayoutData(gd_tablePrintLineColor);
		
		TableColumn tblclmnPrintLineColor = new TableColumn(tablePrintLineColor, SWT.NONE);
		tblclmnPrintLineColor.setWidth(193);
		tblclmnPrintLineColor.setText(DICT.getString("LineStyleToolbox.tblclmnPrintLineColor.text")); //$NON-NLS-1$
		tblclmnPrintLineColor.setResizable(false);
		
		TableItem tableItem_13 = new TableItem(tablePrintLineColor, 0);
		tableItem_13.setText(DICT.getString("LineStyleToolbox.tableItem_13.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItem_14 = new TableItem(tablePrintLineColor, 0);
		tableItem_14.setText(DICT.getString("LineStyleToolbox.tableItem_14.text(java.lang.String)")); //$NON-NLS-1$
		
		TableItem tableItem_15 = new TableItem(tablePrintLineColor, 0);
		tableItem_15.setText(DICT.getString("LineStyleToolbox.tableItem_15.text(java.lang.String)")); //$NON-NLS-1$
		
		fillLineStyles();
		fillLineWidths();
		fillColors();
		initiateStyle();

	}
	
	private void fillLineStyles() {		
		this.tableScreenLineStyle.removeAll();
		this.tablePrintLineStyle.removeAll();
		
		Map<String, String> palette = LineTypePalette.getPalette();
		for (String key : LineTypePalette.getOrderedPaletteKeys()) {
			String value = palette.get(key);
			
			TableItem item1 = new TableItem(tableScreenLineStyle, SWT.NONE);
			TableItem item2 = new TableItem(tablePrintLineStyle, SWT.NONE);
			Image icon = null;
			try {
				if(value.isEmpty()) {
					icon = SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/lines/" + "solid" + ".png");
				} else {
					icon = SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/lines/" + value.toLowerCase() + ".png");
				}
			} catch (Exception e) {
				// nothing happens. icon stays null
			}
			if(icon != null) {
				item1.setImage(icon);
				item2.setImage(icon);
			}
			
			String name = key.toUpperCase().substring(0, 1);
			if(key.length() > 1) {
				name = name + key.toLowerCase().substring(1);
			}
			item1.setText(name);
			item2.setText(name);
		}
	}
	
	private void fillLineWidths() {
		this.tableScreenLineWidth.removeAll();
		this.tablePrintLineWidth.removeAll();
		
		String[] widths = {"1px", "2px", "3px", "4px", "5px", "6px", "7px", "8px", "9px", "10px"};
		for(String s : widths) {
			TableItem item1 = new TableItem(tableScreenLineWidth, SWT.NONE);
			TableItem item2 = new TableItem(tablePrintLineWidth, SWT.NONE);
			
			Image icon = null;
			try {
				icon = SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/linewidths/" + s + ".png");
			} catch (Exception e) {
				// nothing happens. icon stays null
			}
			
			if(icon != null) {
				item1.setImage(icon);
				item2.setImage(icon);
			}
			
			item1.setText(s);
			item2.setText(s);
		}
	}
	
	private void fillColors() {
		this.tableScreenLineColor.removeAll();
		this.tablePrintLineColor.removeAll();
		
		Map<String, Color> palette = ColorPalette.getPalette();
		for(String key : ColorPalette.getOrderedPaletteKeys()) {
			Color c = palette.get(key);
			
			TableItem item1 = new TableItem(tableScreenLineColor, SWT.NONE);
			TableItem item2 = new TableItem(tablePrintLineColor, SWT.NONE);
			
			Image icon = new Image(getParent().getDisplay(), 16, 16);
			
			GC gc = new GC(icon);
			org.eclipse.swt.graphics.Color newcolor = new org.eclipse.swt.graphics.Color(this.getParent().getDisplay(), c.getRed(), c.getGreen(), c.getBlue());
			gc.setBackground(newcolor);
			gc.setForeground(newcolor);
			gc.fillRectangle(0, 0, 16, 16);
			gc.dispose();
			
			item1.setImage(icon);
			item2.setImage(icon);
			
			String name = key.toUpperCase().substring(0, 1);
			if(key.length() > 1) {
				name = name + key.toLowerCase().substring(1);
			}
			item1.setText(name);
			item2.setText(name);
		}
	}
	
	private void initiateStyle() {
		String sStyle = screenLineStyle.getStyle();
		lblScreenStyleSelected.setText(sStyle); //TODO: could be improved to show correct selection (image and name)
		
		Color sColor = screenLineStyle.getColor();
		Color screenC = sColor;
		Image screenIcon = new Image(getParent().getDisplay(), 16, 16);
		GC screenGc = new GC(screenIcon);
		org.eclipse.swt.graphics.Color screenNewcolor = new org.eclipse.swt.graphics.Color(this.getParent().getDisplay(), screenC.getRed(), screenC.getGreen(), screenC.getBlue());
		screenGc.setBackground(screenNewcolor);
		screenGc.setForeground(screenNewcolor);
		screenGc.fillRectangle(0, 0, 16, 16);
		screenGc.dispose();
		this.lblScreenColorSelected.setBackground(screenIcon);
		
		float sWidth = screenLineStyle.getWidth();
		lblScreenWidthSelected.setText(sWidth + "px");
		
		String pStyle = printLineStyle.getStyle();
		lblPrintStyleSelected.setText(pStyle);
		
		Color pColor = printLineStyle.getColor();
		Color printC = pColor;
		Image printIcon = new Image(getParent().getDisplay(), 16, 16);
		GC printGc = new GC(printIcon);
		org.eclipse.swt.graphics.Color printNewcolor = new org.eclipse.swt.graphics.Color(this.getParent().getDisplay(), printC.getRed(), printC.getGreen(), printC.getBlue());
		printGc.setBackground(printNewcolor);
		printGc.setForeground(printNewcolor);
		printGc.fillRectangle(0, 0, 16, 16);
		printGc.dispose();
		this.lblPrintColorSelected.setBackground(printIcon);
		
		float pWidth = printLineStyle.getWidth();
		lblPrintWidthSelected.setText(pWidth + "px");
	}
	
	/**
	 * Returns Line Styles with keys "screen" and "print"
	 * @return
	 */
	public Map<String, LineStyle> getNewStyles() {
		Map<String, LineStyle> result = new HashMap<String, LineStyle>();
		result.put("screen", screenLineStyle);
		result.put("print", printLineStyle);
		return result;
	}
}
