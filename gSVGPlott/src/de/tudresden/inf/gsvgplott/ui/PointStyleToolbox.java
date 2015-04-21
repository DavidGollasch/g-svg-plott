package de.tudresden.inf.gsvgplott.ui;

import java.awt.Color;
import java.util.HashMap;
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

import de.tudresden.inf.gsvgplott.data.style.LineStyle;
import de.tudresden.inf.gsvgplott.data.style.PointStyle;
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
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

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
	 * Data Exchange Objects
	 */
	private PointStyle screenPointStyle, printPointStyle;
	private Spinner spinnerScreenSize;
	private Spinner spinnerPrintSize;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PointStyleToolbox(Shell parent, int style, PointStyle oldScreenPointStyle, PointStyle oldPrintPointStyle) {
		super(parent, SWT.BORDER | SWT.CLOSE);
		setText("SWT Dialog");
		
		screenPointStyle = oldScreenPointStyle;
		printPointStyle = oldPrintPointStyle;
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
		shlToolbox.setSize(236, 305);
		shlToolbox.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CTabFolder tabFolder = new CTabFolder(shlToolbox, SWT.BORDER);
		tabFolder.setBorderVisible(false);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmScreen = new CTabItem(tabFolder, SWT.NONE);
		tbtmScreen.setText("Screen");
		
		Composite compositeScreen = new Composite(tabFolder, SWT.NONE);
		tbtmScreen.setControl(compositeScreen);
		compositeScreen.setLayout(new GridLayout(4, false));
		
		CLabel lblScreenStyle = new CLabel(compositeScreen, SWT.NONE);
		lblScreenStyle.setText("Style");
		
		lblScreenStyleSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenStyleSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1));
		lblScreenStyleSelected.setText("(selected)");
		
		tableScreenPointStyle = new Table(compositeScreen, SWT.FULL_SELECTION);
		tableScreenPointStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tableScreenPointStyle.getSelection()[0];
				lblScreenStyleSelected.setText(selectedItem.getText());
				
				String newValue = PointTypePalette.getPalette().get(selectedItem.getText().toUpperCase());
				screenPointStyle.setStyle(newValue);
			}
		});
		GridData gd_tableScreenPointStyle = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
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
		lblScreenColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1));
		lblScreenColorSelected.setText("(selected)");
		
		tableScreenPointColor = new Table(compositeScreen, SWT.FULL_SELECTION);
		tableScreenPointColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tableScreenPointColor.getSelection()[0];
				lblScreenColorSelected.setText(selectedItem.getText());
				lblScreenColorSelected.setBackground(selectedItem.getImage());
				
				Color newColor = new Color(selectedItem.getImage().getImageData().getPixel(1, 1));
				screenPointStyle.setColor(newColor);
			}
		});
		GridData gd_tableScreenPointColor = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
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
		btnScreenBordered.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				screenPointStyle.setShowBorder(btnScreenBordered.getSelection());
			}
		});
		btnScreenBordered.setText("Bordered");
		
		CLabel lblScreenSize = new CLabel(compositeScreen, SWT.NONE);
		lblScreenSize.setText("Size:");
		
		spinnerScreenSize = new Spinner(compositeScreen, SWT.BORDER);
		spinnerScreenSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				screenPointStyle.setSize(spinnerScreenSize.getSelection());
			}
		});
		spinnerScreenSize.setMaximum(999);
		spinnerScreenSize.setMinimum(1);
		spinnerScreenSize.setSelection(15);
		
		CLabel lblScreenPx = new CLabel(compositeScreen, SWT.NONE);
		lblScreenPx.setText("px");
		
		CTabItem tbtmPrint = new CTabItem(tabFolder, SWT.NONE);
		tbtmPrint.setText("Print");
		
		Composite compositePrint = new Composite(tabFolder, SWT.NONE);
		tbtmPrint.setControl(compositePrint);
		compositePrint.setLayout(new GridLayout(4, false));
		
		CLabel lblPrintStyle = new CLabel(compositePrint, SWT.NONE);
		lblPrintStyle.setText("Style");
		
		lblPrintStyleSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintStyleSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1));
		lblPrintStyleSelected.setText("(selected)");
		
		tablePrintPointStyle = new Table(compositePrint, SWT.FULL_SELECTION);
		tablePrintPointStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tablePrintPointStyle.getSelection()[0];
				lblPrintStyleSelected.setText(selectedItem.getText());
				
				String newValue = PointTypePalette.getPalette().get(selectedItem.getText().toUpperCase());
				printPointStyle.setStyle(newValue);
			}
		});
		GridData gd_tablePrintPointStyle = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
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
		lblPrintColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1));
		lblPrintColorSelected.setText("(selected)");
		
		tablePrintPointColor = new Table(compositePrint, SWT.FULL_SELECTION);
		tablePrintPointColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tablePrintPointColor.getSelection()[0];
				lblPrintColorSelected.setText(selectedItem.getText());
				lblPrintColorSelected.setBackground(selectedItem.getImage());
				
				Color newColor = new Color(selectedItem.getImage().getImageData().getPixel(1, 1));
				printPointStyle.setColor(newColor);
			}
		});
		GridData gd_tablePrintPointColor = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
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
		btnPrintBordered.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				printPointStyle.setShowBorder(btnPrintBordered.getSelection());
			}
		});
		btnPrintBordered.setText("Bordered");
		
		CLabel lblPrintSize = new CLabel(compositePrint, SWT.NONE);
		lblPrintSize.setText("Size:");
		
		spinnerPrintSize = new Spinner(compositePrint, SWT.BORDER);
		spinnerPrintSize.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				printPointStyle.setSize(spinnerPrintSize.getSelection());
			}
		});
		spinnerPrintSize.setMaximum(999);
		spinnerPrintSize.setMinimum(1);
		spinnerPrintSize.setSelection(15);
		
		CLabel lblPrintPx = new CLabel(compositePrint, SWT.NONE);
		lblPrintPx.setText("px");
		
		fillPointTypes();
		fillColors();
		initiateStyle();
		
		// make some controls invisible
		Shell invisibleShell = new Shell();
		lblScreenStyle.setParent(invisibleShell);
		lblScreenStyleSelected.setParent(invisibleShell);
		tableScreenPointStyle.setParent(invisibleShell);
		lblPrintStyle.setParent(invisibleShell);
		lblPrintStyleSelected.setParent(invisibleShell);
		tablePrintPointStyle.setParent(invisibleShell);

	}
	
	private void fillPointTypes() {		
		this.tableScreenPointStyle.removeAll();
		this.tablePrintPointStyle.removeAll();
		
		Map<String, String> palette = PointTypePalette.getPalette();
		for (String key : PointTypePalette.getOrderedPaletteKeys()) {
			//String value = palette.get(key);
			
			TableItem item1 = new TableItem(tableScreenPointStyle, SWT.NONE);
			TableItem item2 = new TableItem(tablePrintPointStyle, SWT.NONE);
			Image icon = null;
			try {
				icon = SWTResourceManager.getImage(PointStyleToolbox.class, "/de/tudresden/inf/gsvgplott/ui/icons/points/" + key.toLowerCase() + ".png");
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
	
	private void fillColors() {
		this.tableScreenPointColor.removeAll();
		this.tablePrintPointColor.removeAll();
		
		Map<String, Color> palette = ColorPalette.getPalette();
		for(String key : ColorPalette.getOrderedPaletteKeys()) {
			Color c = palette.get(key);
			
			TableItem item1 = new TableItem(tableScreenPointColor, SWT.NONE);
			TableItem item2 = new TableItem(tablePrintPointColor, SWT.NONE);
			
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
		String sStyle = screenPointStyle.getStyle();
		lblScreenStyleSelected.setText(sStyle);
		
		Color sColor = screenPointStyle.getColor();
		Color screenC = sColor;
		Image screenIcon = new Image(getParent().getDisplay(), 16, 16);
		GC screenGc = new GC(screenIcon);
		org.eclipse.swt.graphics.Color screenNewcolor = new org.eclipse.swt.graphics.Color(this.getParent().getDisplay(), screenC.getRed(), screenC.getGreen(), screenC.getBlue());
		screenGc.setBackground(screenNewcolor);
		screenGc.setForeground(screenNewcolor);
		screenGc.fillRectangle(0, 0, 16, 16);
		screenGc.dispose();
		this.lblScreenColorSelected.setBackground(screenIcon);
		
		int sSize = screenPointStyle.getSize();
		spinnerScreenSize.setSelection(sSize);
		
		boolean sBordering = screenPointStyle.isShowBorder();
		btnScreenBordered.setSelection(sBordering);
		
		String pStyle = printPointStyle.getStyle();
		lblPrintStyleSelected.setText(pStyle);
		
		Color pColor = printPointStyle.getColor();
		Color printC = pColor;
		Image printIcon = new Image(getParent().getDisplay(), 16, 16);
		GC printGc = new GC(printIcon);
		org.eclipse.swt.graphics.Color printNewcolor = new org.eclipse.swt.graphics.Color(this.getParent().getDisplay(), printC.getRed(), printC.getGreen(), printC.getBlue());
		printGc.setBackground(printNewcolor);
		printGc.setForeground(printNewcolor);
		printGc.fillRectangle(0, 0, 16, 16);
		printGc.dispose();
		this.lblPrintColorSelected.setBackground(printIcon);
		
		int pSize = printPointStyle.getSize();
		spinnerPrintSize.setSelection(pSize);
		
		boolean pBordering = printPointStyle.isShowBorder();
		btnPrintBordered.setSelection(pBordering);
	}
	
	/**
	 * Returns Point Styles with keys "screen" and "print"
	 * @return
	 */
	public Map<String, PointStyle> getNewStyles() {
		Map<String, PointStyle> result = new HashMap<String, PointStyle>();
		result.put("screen", screenPointStyle);
		result.put("print", printPointStyle);
		return result;
	}
}
