package de.tudresden.inf.gsvgplott.ui;

import java.awt.Color;
import java.util.Map;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import de.tudresden.inf.gsvgplott.data.style.palettes.ColorPalette;
import de.tudresden.inf.gsvgplott.data.style.palettes.PointTypePalette;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class PointStyleToolbox extends Dialog {

	protected Object result;
	protected Shell shlToolbox;
	private Table tableScreenPointStyle;
	private Table tableScreenPointColor;
	private Table tablePrintPointStyle;
	private Table tablePrintPointColor;
	
	/**
	 * Location the window should open
	 */
	private Point openingLocation = null;
	private CLabel lblScreenStyleSelected;
	private CLabel lblScreenColorSelected;
	private Button btnScreenBordered;
	private CLabel lblPrintStyleSelected;
	private CLabel lblPrintColorSelected;
	private Button btnPrintBordered;

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
		shlToolbox.open();
		shlToolbox.layout();
		Display display = getParent().getDisplay();
		if(openingLocation != null) {
			shlToolbox.setLocation(openingLocation.x, openingLocation.y);
		}
		while (!shlToolbox.isDisposed()) {
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
		shlToolbox = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.TOOL);
		shlToolbox.setText("Point Style");
		shlToolbox.setSize(209, 305);
		shlToolbox.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CTabFolder tabFolder = new CTabFolder(shlToolbox, SWT.BORDER);
		tabFolder.setBorderVisible(false);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmScreen = new CTabItem(tabFolder, SWT.NONE);
		tbtmScreen.setText("Screen");
		
		Composite compositeScreen = new Composite(tabFolder, SWT.NONE);
		tbtmScreen.setControl(compositeScreen);
		compositeScreen.setLayout(new GridLayout(2, false));
		
		CLabel lblScreenStyle = new CLabel(compositeScreen, SWT.NONE);
		lblScreenStyle.setText("Style");
		
		lblScreenStyleSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenStyleSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScreenStyleSelected.setText("(selected)");
		
		tableScreenPointStyle = new Table(compositeScreen, SWT.FULL_SELECTION);
		tableScreenPointStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tableScreenPointStyle.getSelection()[0];
				lblScreenStyleSelected.setText(selectedItem.getText());
			}
		});
		GridData gd_tableScreenPointStyle = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableScreenPointStyle.minimumHeight = 50;
		tableScreenPointStyle.setLayoutData(gd_tableScreenPointStyle);
		
		TableColumn tblclmnScreenPointStyle = new TableColumn(tableScreenPointStyle, SWT.NONE);
		tblclmnScreenPointStyle.setResizable(false);
		tblclmnScreenPointStyle.setWidth(193);
		tblclmnScreenPointStyle.setText("Point Style");
		
		TableItem tableItemScreenStyleSolid = new TableItem(tableScreenPointStyle, SWT.NONE);
		tableItemScreenStyleSolid.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tableItemScreenStyleSolid.setText("Example");
		
		CLabel lblScreenColor = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColor.setText("Color");
		
		lblScreenColorSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScreenColorSelected.setText("(selected)");
		
		tableScreenPointColor = new Table(compositeScreen, SWT.FULL_SELECTION);
		tableScreenPointColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tableScreenPointColor.getSelection()[0];
				lblScreenColorSelected.setText(selectedItem.getText());
				lblScreenColorSelected.setBackground(selectedItem.getImage());
			}
		});
		GridData gd_tableScreenPointColor = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tableScreenPointColor.minimumHeight = 50;
		tableScreenPointColor.setLayoutData(gd_tableScreenPointColor);
		
		TableColumn tblclmnScreenPointColor = new TableColumn(tableScreenPointColor, SWT.NONE);
		tblclmnScreenPointColor.setWidth(193);
		tblclmnScreenPointColor.setText("Line Color");
		tblclmnScreenPointColor.setResizable(false);
		
		TableItem tableItemScreenColor1 = new TableItem(tableScreenPointColor, SWT.NONE);
		tableItemScreenColor1.setText("Color 1");
		
		TableItem tableItemScreenColor2 = new TableItem(tableScreenPointColor, 0);
		tableItemScreenColor2.setText("Color 2");
		
		TableItem tableItemScreenColor3 = new TableItem(tableScreenPointColor, 0);
		tableItemScreenColor3.setText("Color 3");
		
		btnScreenBordered = new Button(compositeScreen, SWT.CHECK);
		btnScreenBordered.setText("Bordered");
		new Label(compositeScreen, SWT.NONE);
		
		CTabItem tbtmPrint = new CTabItem(tabFolder, SWT.NONE);
		tbtmPrint.setText("Print");
		
		Composite compositePrint = new Composite(tabFolder, SWT.NONE);
		tbtmPrint.setControl(compositePrint);
		compositePrint.setLayout(new GridLayout(2, false));
		
		CLabel lblPrintStyle = new CLabel(compositePrint, SWT.NONE);
		lblPrintStyle.setText("Style");
		
		lblPrintStyleSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintStyleSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrintStyleSelected.setText("(selected)");
		
		tablePrintPointStyle = new Table(compositePrint, SWT.FULL_SELECTION);
		tablePrintPointStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tablePrintPointStyle.getSelection()[0];
				lblPrintStyleSelected.setText(selectedItem.getText());
			}
		});
		GridData gd_tablePrintPointStyle = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tablePrintPointStyle.minimumHeight = 50;
		gd_tablePrintPointStyle.widthHint = 132;
		tablePrintPointStyle.setLayoutData(gd_tablePrintPointStyle);
		
		TableColumn tblclmnPrintPointStyle = new TableColumn(tablePrintPointStyle, SWT.NONE);
		tblclmnPrintPointStyle.setWidth(193);
		tblclmnPrintPointStyle.setText("Line Style");
		tblclmnPrintPointStyle.setResizable(false);
		
		TableItem tableItem = new TableItem(tablePrintPointStyle, 0);
		tableItem.setText("Example");
		tableItem.setImage(SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		
		CLabel lblPrintColor = new CLabel(compositePrint, SWT.NONE);
		lblPrintColor.setText("Color");
		
		lblPrintColorSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrintColorSelected.setText("(selected)");
		
		tablePrintPointColor = new Table(compositePrint, SWT.FULL_SELECTION);
		tablePrintPointColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tablePrintPointColor.getSelection()[0];
				lblPrintColorSelected.setText(selectedItem.getText());
				lblPrintColorSelected.setBackground(selectedItem.getImage());
			}
		});
		GridData gd_tablePrintPointColor = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tablePrintPointColor.minimumHeight = 50;
		tablePrintPointColor.setLayoutData(gd_tablePrintPointColor);
		
		TableColumn tblclmnPrintPointColor = new TableColumn(tablePrintPointColor, SWT.NONE);
		tblclmnPrintPointColor.setWidth(193);
		tblclmnPrintPointColor.setText("Line Color");
		tblclmnPrintPointColor.setResizable(false);
		
		TableItem tableItem_13 = new TableItem(tablePrintPointColor, 0);
		tableItem_13.setText("Color 1");
		
		TableItem tableItem_14 = new TableItem(tablePrintPointColor, 0);
		tableItem_14.setText("Color 2");
		
		TableItem tableItem_15 = new TableItem(tablePrintPointColor, 0);
		tableItem_15.setText("Color 3");
		
		btnPrintBordered = new Button(compositePrint, SWT.CHECK);
		btnPrintBordered.setText("Bordered");
		new Label(compositePrint, SWT.NONE);
		
		this.fillPointTypes();
		this.fillColors();

	}
	
	private void fillPointTypes() {		
		this.tableScreenPointStyle.removeAll();
		this.tablePrintPointStyle.removeAll();
		
		Map<String, String> palette = PointTypePalette.getPalette();
		for (Map.Entry<String, String> entry : palette.entrySet()) {
			TableItem item1 = new TableItem(tableScreenPointStyle, SWT.NONE);
			TableItem item2 = new TableItem(tablePrintPointStyle, SWT.NONE);
			Image icon = null;
			try {
				icon = SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/points/" + entry.getKey().toLowerCase() + ".png");
			} catch (Exception e) {
				// nothing happens. icon stays null
			}
			if(icon != null) {
				item1.setImage(icon);
				item2.setImage(icon);
			}
			
			String name = entry.getKey().toUpperCase().substring(0, 1);
			if(entry.getKey().length() > 1) {
				name = name + entry.getKey().toLowerCase().substring(1);
			}
			item1.setText(name);
			item2.setText(name);
		}
	}
	
	private void fillColors() {
		this.tableScreenPointColor.removeAll();
		this.tablePrintPointColor.removeAll();
		
		Map<String, Color> palette = ColorPalette.getPalette();
		for(Map.Entry<String, Color> entry : palette.entrySet()) {
			TableItem item1 = new TableItem(tableScreenPointColor, SWT.NONE);
			TableItem item2 = new TableItem(tablePrintPointColor, SWT.NONE);
			
			Image icon = new Image(getParent().getDisplay(), 16, 16);
			
			GC gc = new GC(icon);
			org.eclipse.swt.graphics.Color newcolor = new org.eclipse.swt.graphics.Color(this.getParent().getDisplay(), entry.getValue().getRed(), entry.getValue().getGreen(), entry.getValue().getBlue());
			gc.setBackground(newcolor);
			gc.setForeground(newcolor);
			gc.fillRectangle(0, 0, 16, 16);
			gc.dispose();
			
			item1.setImage(icon);
			item2.setImage(icon);
			
			String name = entry.getKey().toUpperCase().substring(0, 1);
			if(entry.getKey().length() > 1) {
				name = name + entry.getKey().toLowerCase().substring(1);
			}
			item1.setText(name);
			item2.setText(name);
		}
	}
}
