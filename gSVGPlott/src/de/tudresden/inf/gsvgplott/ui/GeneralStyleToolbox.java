package de.tudresden.inf.gsvgplott.ui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;

import de.tudresden.inf.gsvgplott.data.style.AreaStyle;
import de.tudresden.inf.gsvgplott.data.style.TextStyle;
import de.tudresden.inf.gsvgplott.data.style.palettes.ColorPalette;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class GeneralStyleToolbox extends Dialog {

	protected Object result;
	protected Shell shlToolbox;
	private Table tableScreenBgColor;
	private Table tablePrintBgColor;
	
	/**
	 * Location the window should open
	 */
	private Point openingLocation = null;
	private CLabel lblPrintFontSelected;
	private CLabel lblPrintColorSelected;
	private CLabel lblScreenFontSelected;
	private CLabel lblScreenColorSelected;
	
	/**
	 * Data Exchange Objects
	 */
	private TextStyle screenTextStyle, printTextStyle;
	private AreaStyle screenAreaStyle, printAreaStyle;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GeneralStyleToolbox(Shell parent, int style, 
			TextStyle oldScreenTextStyle, 
			TextStyle oldPrintTextStyle, 
			AreaStyle oldScreenAreaStyle, 
			AreaStyle oldPrintAreaStyle) {
		super(parent, SWT.BORDER | SWT.CLOSE);
		setText("SWT Dialog");
		
		// set references
		screenTextStyle = oldScreenTextStyle;
		printTextStyle = oldPrintTextStyle;
		screenAreaStyle = oldScreenAreaStyle;
		printAreaStyle = oldPrintAreaStyle;
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
		shlToolbox = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.TOOL);
		shlToolbox.setText("General Style");
		shlToolbox.setSize(209, 255);
		shlToolbox.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		CTabFolder tabFolder = new CTabFolder(shlToolbox, SWT.BORDER);
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
		btnScreenFont.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FontDialog fd = new FontDialog(shlToolbox, SWT.NONE);
		        
				fd.setText("Select Font");
		        fd.setRGB(lblScreenFontSelected.getForeground().getRGB());
		        
		        Font currentF = lblScreenFontSelected.getFont();
		        FontData[] currentFd = currentF.getFontData();
		        
		        fd.setFontList(currentFd);
		        
		        FontData newFont = fd.open();
		        if (newFont == null)
		          return;
		        
		        lblScreenFontSelected.setFont(new Font(getParent().getDisplay(), newFont));
		        lblScreenFontSelected.setForeground(new org.eclipse.swt.graphics.Color(getParent().getDisplay(), fd.getRGB()));
		        lblScreenFontSelected.setText(newFont.getName() + ", " + newFont.getHeight());
		        
		        //translate data to exchange objects
		        screenTextStyle.setFont(newFont.getName());
		        screenTextStyle.setSize(newFont.getHeight());
		        if(newFont.getStyle() == SWT.NORMAL) {
		        	screenTextStyle.setBold(false);
		        	screenTextStyle.setItalic(false);
		        } else if (newFont.getStyle() == SWT.BOLD) {
		        	screenTextStyle.setBold(true);
		        	screenTextStyle.setItalic(false);
		        } else if (newFont.getStyle() == SWT.ITALIC) {
		        	screenTextStyle.setBold(false);
		        	screenTextStyle.setItalic(true);
		        } else if (newFont.getStyle() == (SWT.BOLD | SWT.ITALIC)) {
		        	screenTextStyle.setBold(true);
		        	screenTextStyle.setItalic(true);
		        }
		        screenTextStyle.setColor(new Color(fd.getRGB().red, fd.getRGB().green, fd.getRGB().blue));
			}
		});
		btnScreenFont.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnScreenFont.setText("...");
		
		lblScreenFontSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenFontSelected.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lblScreenFontSelected.setText("(selected)");
		
		CLabel lblScreenColor = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColor.setText("Background Color");
		
		lblScreenColorSelected = new CLabel(compositeScreen, SWT.NONE);
		lblScreenColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScreenColorSelected.setText("(selected)");
		
		tableScreenBgColor = new Table(compositeScreen, SWT.FULL_SELECTION);
		tableScreenBgColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tableScreenBgColor.getSelection()[0];
				lblScreenColorSelected.setText(selectedItem.getText());
				lblScreenColorSelected.setBackground(selectedItem.getImage());
				
				Color newColor = new Color(selectedItem.getImage().getImageData().getPixel(1, 1));
				screenAreaStyle.setColor(newColor);
			}
		});
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
		btnPrintFont.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FontDialog fd = new FontDialog(shlToolbox, SWT.NONE);
		        
				fd.setText("Select Font");
		        fd.setRGB(lblPrintFontSelected.getForeground().getRGB());
		        
		        Font currentF = lblPrintFontSelected.getFont();
		        FontData[] currentFd = currentF.getFontData();
		        
		        fd.setFontList(currentFd);
		        
		        FontData newFont = fd.open();
		        if (newFont == null)
		          return;
		        
		        lblPrintFontSelected.setFont(new Font(getParent().getDisplay(), newFont));
		        lblPrintFontSelected.setForeground(new org.eclipse.swt.graphics.Color(getParent().getDisplay(), fd.getRGB()));
		        lblPrintFontSelected.setText(newFont.getName() + ", " + newFont.getHeight());
		        
		        //translate data to exchange objects
		        printTextStyle.setFont(newFont.getName());
		        printTextStyle.setSize(newFont.getHeight());
		        if(newFont.getStyle() == SWT.NORMAL) {
		        	printTextStyle.setBold(false);
		        	printTextStyle.setItalic(false);
		        } else if (newFont.getStyle() == SWT.BOLD) {
		        	printTextStyle.setBold(true);
		        	printTextStyle.setItalic(false);
		        } else if (newFont.getStyle() == SWT.ITALIC) {
		        	printTextStyle.setBold(false);
		        	printTextStyle.setItalic(true);
		        } else if (newFont.getStyle() == (SWT.BOLD | SWT.ITALIC)) {
		        	printTextStyle.setBold(true);
		        	printTextStyle.setItalic(true);
		        }
		        printTextStyle.setColor(new Color(fd.getRGB().red, fd.getRGB().green, fd.getRGB().blue));
			}
		});
		btnPrintFont.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnPrintFont.setText("...");
		
		lblPrintFontSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintFontSelected.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblPrintFontSelected.setText("(selected)");
		
		CLabel lblPrintColor = new CLabel(compositePrint, SWT.NONE);
		lblPrintColor.setText("Background Color");
		
		lblPrintColorSelected = new CLabel(compositePrint, SWT.NONE);
		lblPrintColorSelected.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrintColorSelected.setText("(selected)");
		
		tablePrintBgColor = new Table(compositePrint, SWT.FULL_SELECTION);
		tablePrintBgColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem selectedItem = tablePrintBgColor.getSelection()[0];
				lblPrintColorSelected.setText(selectedItem.getText());
				lblPrintColorSelected.setBackground(selectedItem.getImage());
				
				Color newColor = new Color(selectedItem.getImage().getImageData().getPixel(1, 1));
				printAreaStyle.setColor(newColor);
			}
		});
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
		
		fillColors();
		initiateStyle();

	}
	
	private void fillColors() {
		this.tableScreenBgColor.removeAll();
		this.tablePrintBgColor.removeAll();
		
		Map<String, Color> palette = ColorPalette.getPalette();
		for(String key : ColorPalette.getOrderedPaletteKeys()) {
			Color c = palette.get(key);
			
			TableItem item1 = new TableItem(tableScreenBgColor, SWT.NONE);
			TableItem item2 = new TableItem(tablePrintBgColor, SWT.NONE);
			
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
		
		FontData screenFd = new FontData();
		screenFd.setName(screenTextStyle.getFont());
		screenFd.setHeight(screenTextStyle.getSize());
		if(!screenTextStyle.isBold() && !screenTextStyle.isItalic()) {
			//normal
			screenFd.setStyle(SWT.NONE);
		} else if(screenTextStyle.isBold() && !screenTextStyle.isItalic()) {
			//bold
			screenFd.setStyle(SWT.BOLD);
		} else if(!screenTextStyle.isBold() && screenTextStyle.isItalic()) {
			//italic
			screenFd.setStyle(SWT.ITALIC);
		} else if(screenTextStyle.isBold() && screenTextStyle.isItalic()) {
			//bold+italic
			screenFd.setStyle(SWT.BOLD | SWT.ITALIC);
		}
		
		Font screenF = new Font(getParent().getDisplay(), screenFd);
		this.lblScreenFontSelected.setFont(screenF);
		this.lblScreenFontSelected.setForeground(new org.eclipse.swt.graphics.Color(
				getParent().getDisplay(), 
				screenTextStyle.getColor().getRed(), 
				screenTextStyle.getColor().getGreen(), 
				screenTextStyle.getColor().getBlue()));
		this.lblScreenFontSelected.setText(screenFd.getName() + ", " + screenFd.getHeight());
		
		
		
		FontData printFd = new FontData();
		printFd.setName(printTextStyle.getFont());
		printFd.setHeight(printTextStyle.getSize());
		if(!printTextStyle.isBold() && !printTextStyle.isItalic()) {
			//normal
			printFd.setStyle(SWT.NORMAL);
		} else if(printTextStyle.isBold() && !printTextStyle.isItalic()) {
			//bold
			printFd.setStyle(SWT.BOLD);
		} else if(!printTextStyle.isBold() && printTextStyle.isItalic()) {
			//italic
			printFd.setStyle(SWT.ITALIC);
		} else if(printTextStyle.isBold() && printTextStyle.isItalic()) {
			//bold+italic
			printFd.setStyle(SWT.BOLD | SWT.ITALIC);
		}
		
		Font printF = new Font(getParent().getDisplay(), printFd);
		this.lblPrintFontSelected.setFont(printF);
		this.lblPrintFontSelected.setForeground(new org.eclipse.swt.graphics.Color(
				getParent().getDisplay(), 
				printTextStyle.getColor().getRed(), 
				printTextStyle.getColor().getGreen(), 
				printTextStyle.getColor().getBlue()));
		this.lblPrintFontSelected.setText(printFd.getName() + ", " + printFd.getHeight());
		
		
		
		Color screenC = screenAreaStyle.getColor();
		Image screenIcon = new Image(getParent().getDisplay(), 16, 16);
		GC screenGc = new GC(screenIcon);
		org.eclipse.swt.graphics.Color screenNewcolor = new org.eclipse.swt.graphics.Color(this.getParent().getDisplay(), screenC.getRed(), screenC.getGreen(), screenC.getBlue());
		screenGc.setBackground(screenNewcolor);
		screenGc.setForeground(screenNewcolor);
		screenGc.fillRectangle(0, 0, 16, 16);
		screenGc.dispose();
		this.lblScreenColorSelected.setBackground(screenIcon);
		
		
		
		Color printC = printAreaStyle.getColor();
		Image printIcon = new Image(getParent().getDisplay(), 16, 16);
		GC printGc = new GC(printIcon);
		org.eclipse.swt.graphics.Color printNewcolor = new org.eclipse.swt.graphics.Color(this.getParent().getDisplay(), printC.getRed(), printC.getGreen(), printC.getBlue());
		printGc.setBackground(printNewcolor);
		printGc.setForeground(printNewcolor);
		printGc.fillRectangle(0, 0, 16, 16);
		printGc.dispose();
		this.lblPrintColorSelected.setBackground(printIcon);
	}
	
	/**
	 * Returns a two-folded map with the following structure:
	 * <ul>
	 * 	<li>Key <code>text</code> > Map with text styling </li>
	 * 	<ul>
	 * 		<li>Key <code>screen</code> > Screen text style</li>
	 * 		<li>Key <code>print</code> > Print text style</li>
	 * 	</ul>
	 * 	<li>Key <code>area</code> > Map with area styling</li>
	 * 	<ul>
	 * 		<li>Key <code>screen</code> > Screen area style</li>
	 * 		<li>Key <code>print</code> > Print area style</li>
	 * 	</ul>
	 * </ul>
	 * @return
	 */
	public Map<String,Map<String,Object>> getNewStyles() {
		Map<String,Object> text = new HashMap<String, Object>();
		text.put("screen", screenTextStyle);
		text.put("print", printTextStyle);
		
		Map<String,Object> area = new HashMap<String, Object>();
		area.put("screen", screenAreaStyle);
		area.put("print", printAreaStyle);
		
		Map<String,Map<String,Object>> result = new HashMap<String, Map<String,Object>>();
		result.put("text", text);
		result.put("area", area);
		
		return result;
	}
}
