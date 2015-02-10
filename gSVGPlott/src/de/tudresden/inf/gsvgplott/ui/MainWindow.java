package de.tudresden.inf.gsvgplott.ui;

/**
 * 
 * @author David Gollasch
 *
 */

import javax.swing.JFrame;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import de.tudresden.inf.gsvgplott.data.Plotter;

import org.eclipse.swt.browser.Browser;

import swing2swt.layout.BorderLayout;
import swing2swt.layout.BoxLayout;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.explodingpixels.macwidgets.HudWindow;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.StackLayout;

public class MainWindow {
	
	public static final String APP_NAME = "gSVGPlott";

	protected Shell shlGsvgplott;
	
	private Plotter plotter;
	private Text txtPoGeneralTitle;
	private Text txtPoXaxisTitle;
	private Text txtPoXaxisHelplines;
	private Text txtPoYaxisTitle;
	private Text txtPoYaxisHelplines;
	private Text txtPoIntegralTitle;
	private Text txtDRFfunc1;
	private Text txtDRMtitle1;
	private Table tableDRMlist1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "gSVGPlott");
		} catch (Exception e) {
			// nothing... doesn't matter anyway
		}
		
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor to initialize a new gSVGPlott Instance.
	 */
	public MainWindow() {
		plotter = new Plotter();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		
		createContents();
		shlGsvgplott.open();
		shlGsvgplott.layout();
		while (!shlGsvgplott.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlGsvgplott = new Shell();
		shlGsvgplott.setSize(new Point(1131, 650));
		shlGsvgplott.setMinimumSize(new Point(500, 400));
		shlGsvgplott.setText(MainWindow.APP_NAME);
		shlGsvgplott.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashTopLevelColumns = new SashForm(shlGsvgplott, SWT.NONE);
		sashTopLevelColumns.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
		
		ScrolledComposite scrolledCompositeDataColumn = new ScrolledComposite(sashTopLevelColumns, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositeDataColumn.setExpandVertical(true);
		scrolledCompositeDataColumn.setExpandHorizontal(true);
		
		Composite compositeDataColumn = new Composite(scrolledCompositeDataColumn, SWT.NONE);
		compositeDataColumn.setLayout(new GridLayout(1, false));
		
		CLabel lblDataColumn = new CLabel(compositeDataColumn, SWT.NONE);
		lblDataColumn.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 15, SWT.BOLD));
		lblDataColumn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDataColumn.setText("Data");
		
		CLabel lblDataRowFunctions = new CLabel(compositeDataColumn, SWT.NONE);
		lblDataRowFunctions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDataRowFunctions.setText("Functions");
		
		Label lblSepDataRowFunctions = new Label(compositeDataColumn, SWT.SEPARATOR | SWT.HORIZONTAL | SWT.SHADOW_NONE);
		lblSepDataRowFunctions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblSepDataRowFunctions.setText("sep");
		
		Group grpDataRowFunction1 = new Group(compositeDataColumn, SWT.NONE);
		grpDataRowFunction1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpDataRowFunction1.setText("Function 1");
		grpDataRowFunction1.setLayout(new GridLayout(3, false));
		
		CLabel lblDRFfunc1 = new CLabel(grpDataRowFunction1, SWT.NONE);
		lblDRFfunc1.setSize(39, 19);
		lblDRFfunc1.setText("y(x) = ");
		
		txtDRFfunc1 = new Text(grpDataRowFunction1, SWT.BORDER);
		txtDRFfunc1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDRFfunc1.setSize(194, 19);
		
		Button btnDRFStyle1 = new Button(grpDataRowFunction1, SWT.FLAT);
		btnDRFStyle1.setToolTipText("Change style");
		btnDRFStyle1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-20.png"));
		
		Composite compositeDRFControls1 = new Composite(grpDataRowFunction1, SWT.NONE);
		GridLayout gl_compositeDRFControls1 = new GridLayout(3, false);
		gl_compositeDRFControls1.verticalSpacing = 0;
		gl_compositeDRFControls1.marginWidth = 0;
		gl_compositeDRFControls1.marginHeight = 0;
		gl_compositeDRFControls1.horizontalSpacing = 0;
		compositeDRFControls1.setLayout(gl_compositeDRFControls1);
		compositeDRFControls1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		
		Button btnDRFMoveUp1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFMoveUp1.setToolTipText("Move item up");
		btnDRFMoveUp1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnDRFMoveUp1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/up-20.png"));
		
		Button btnDRFMoveDown1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFMoveDown1.setToolTipText("Move item down");
		btnDRFMoveDown1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnDRFMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-20.png"));
		
		Button btnDRFRemove1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFRemove1.setToolTipText("Remove item");
		GridData gd_btnDRFRemove1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnDRFRemove1.horizontalIndent = 5;
		btnDRFRemove1.setLayoutData(gd_btnDRFRemove1);
		btnDRFRemove1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/delete-20.png"));
		btnDRFRemove1.setText("Remove");
		
		Button btnDataColumnFunctionsAddFunction = new Button(compositeDataColumn, SWT.FLAT);
		btnDataColumnFunctionsAddFunction.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/add-20.png"));
		btnDataColumnFunctionsAddFunction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnDataColumnFunctionsAddFunction.setText("Add Function");
		
		CLabel lblDataRowMarkedpoints = new CLabel(compositeDataColumn, SWT.NONE);
		lblDataRowMarkedpoints.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDataRowMarkedpoints.setText("Marked Points");
		
		Label lblSepMarkedpointsRow = new Label(compositeDataColumn, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSepMarkedpointsRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group grpDataRowMarkedpointsPointList = new Group(compositeDataColumn, SWT.NONE);
		grpDataRowMarkedpointsPointList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpDataRowMarkedpointsPointList.setLayout(new GridLayout(4, false));
		grpDataRowMarkedpointsPointList.setText("Point List 1");
		
		CLabel lblDRMTitle1 = new CLabel(grpDataRowMarkedpointsPointList, SWT.NONE);
		lblDRMTitle1.setText("Title:");
		
		txtDRMtitle1 = new Text(grpDataRowMarkedpointsPointList, SWT.BORDER);
		txtDRMtitle1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button btnDRMStyle1 = new Button(grpDataRowMarkedpointsPointList, SWT.FLAT);
		btnDRMStyle1.setToolTipText("Change style");
		btnDRMStyle1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-20.png"));
		
		Composite compositeDRMlist1 = new Composite(grpDataRowMarkedpointsPointList, SWT.NONE);
		compositeDRMlist1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1));
		compositeDRMlist1.setLayout(null);
		
		tableDRMlist1 = new Table(compositeDRMlist1, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		tableDRMlist1.setBounds(0, 0, 185, 64);
		tableDRMlist1.setLinesVisible(true);
		tableDRMlist1.setHeaderVisible(true);
		
		TableColumn tblclmnDRMXValue1 = new TableColumn(tableDRMlist1, SWT.NONE);
		tblclmnDRMXValue1.setWidth(75);
		tblclmnDRMXValue1.setText("X Value");
		
		TableColumn tblclmnDRMYValue1 = new TableColumn(tableDRMlist1, SWT.NONE);
		tblclmnDRMYValue1.setWidth(75);
		tblclmnDRMYValue1.setText("Y Value");
		
		TableItem tableItemDRMlist1point2 = new TableItem(tableDRMlist1, SWT.NONE);
		tableItemDRMlist1point2.setText(new String[] {"3.5", "4.5"});
		
		TableItem tableItemDRMlist1point1 = new TableItem(tableDRMlist1, SWT.NONE);
		tableItemDRMlist1point1.setText(new String[] {"1.5", "2.5"});
		
		Composite compositeDRMlistControls1 = new Composite(grpDataRowMarkedpointsPointList, SWT.NONE);
		compositeDRMlistControls1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1));
		GridLayout gl_compositeDRMlistControls1 = new GridLayout(1, false);
		gl_compositeDRMlistControls1.horizontalSpacing = 0;
		gl_compositeDRMlistControls1.marginWidth = 0;
		gl_compositeDRMlistControls1.marginHeight = 0;
		gl_compositeDRMlistControls1.verticalSpacing = 0;
		compositeDRMlistControls1.setLayout(gl_compositeDRMlistControls1);
		
		Button btnDRMlistAdd1 = new Button(compositeDRMlistControls1, SWT.NONE);
		btnDRMlistAdd1.setText("Add...");
		
		Button btnDRMlistRemove1 = new Button(compositeDRMlistControls1, SWT.NONE);
		btnDRMlistRemove1.setEnabled(false);
		btnDRMlistRemove1.setText("Remove");
		
		Composite compositeDRMControls1 = new Composite(grpDataRowMarkedpointsPointList, SWT.NONE);
		compositeDRMControls1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		GridLayout gl_compositeDRMControls1 = new GridLayout(3, false);
		gl_compositeDRMControls1.verticalSpacing = 0;
		gl_compositeDRMControls1.marginWidth = 0;
		gl_compositeDRMControls1.marginHeight = 0;
		gl_compositeDRMControls1.horizontalSpacing = 0;
		compositeDRMControls1.setLayout(gl_compositeDRMControls1);
		
		Button btnDRMMoveUp1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMMoveUp1.setToolTipText("Move item up");
		btnDRMMoveUp1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/up-20.png"));
		
		Button btnDRMMoveDown1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMMoveDown1.setToolTipText("Move item down");
		btnDRMMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-20.png"));
		
		Button btnDRMRemove1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMRemove1.setToolTipText("Remove item");
		GridData gd_btnDRMRemove1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDRMRemove1.horizontalIndent = 5;
		btnDRMRemove1.setLayoutData(gd_btnDRMRemove1);
		btnDRMRemove1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/delete-20.png"));
		btnDRMRemove1.setText("Remove");
		
		Button btnDataColumnListsAddPointList = new Button(compositeDataColumn, SWT.FLAT);
		btnDataColumnListsAddPointList.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/add-20.png"));
		btnDataColumnListsAddPointList.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnDataColumnListsAddPointList.setText("Add Point List");
		scrolledCompositeDataColumn.setContent(compositeDataColumn);
		scrolledCompositeDataColumn.setMinSize(compositeDataColumn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		ScrolledComposite scrolledCompositePlotoptionsColumn = new ScrolledComposite(sashTopLevelColumns, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositePlotoptionsColumn.setExpandHorizontal(true);
		scrolledCompositePlotoptionsColumn.setExpandVertical(true);
		
		Composite compositePlotoptionsColumn = new Composite(scrolledCompositePlotoptionsColumn, SWT.NONE);
		compositePlotoptionsColumn.setLayout(new GridLayout(1, false));
		
		CLabel lblPlotoptionsColumn = new CLabel(compositePlotoptionsColumn, SWT.NONE);
		lblPlotoptionsColumn.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 15, SWT.BOLD));
		lblPlotoptionsColumn.setText("Plot Options");
		
		Group grpPlotoptionsGeneralRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsGeneralRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpPlotoptionsGeneralRow.setText("General");
		grpPlotoptionsGeneralRow.setLayout(new GridLayout(3, false));
		
		CLabel lblPoGeneralTitle = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralTitle.setText("Title:");
		
		txtPoGeneralTitle = new Text(grpPlotoptionsGeneralRow, SWT.BORDER);
		txtPoGeneralTitle.setToolTipText("Enter title of diagram");
		txtPoGeneralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnPlotoptionsGeneralStyle = new Button(grpPlotoptionsGeneralRow, SWT.FLAT);
		btnPlotoptionsGeneralStyle.setToolTipText("Change style");
		btnPlotoptionsGeneralStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-20.png"));
		
		CLabel lblPoGeneralSize = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSize.setText("Size (Width):");
		
		Spinner spinnerPoGeneralSize = new Spinner(grpPlotoptionsGeneralRow, SWT.BORDER);
		spinnerPoGeneralSize.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoGeneralSize.setIncrement(10);
		spinnerPoGeneralSize.setMaximum(99999);
		spinnerPoGeneralSize.setMinimum(0);
		spinnerPoGeneralSize.setSelection(217);
		
		CLabel lblPoGeneralGrid = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralGrid.setText("Grid:");
		
		Button btnPoGeneralShowGrid = new Button(grpPlotoptionsGeneralRow, SWT.CHECK);
		btnPoGeneralShowGrid.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnPoGeneralShowGrid.setText("Show Grid");
		
		Group grpPlotoptionsXAxisRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsXAxisRow.setLayout(new GridLayout(5, false));
		grpPlotoptionsXAxisRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpPlotoptionsXAxisRow.setText("X Axis");
		
		CLabel lblPoXaxisTitle = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisTitle.setText("Title:");
		
		txtPoXaxisTitle = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtPoXaxisTitle.setToolTipText("Set x axis title");
		
		Button btnPlotoptionsXAxisStyle = new Button(grpPlotoptionsXAxisRow, SWT.FLAT);
		btnPlotoptionsXAxisStyle.setToolTipText("Change style");
		btnPlotoptionsXAxisStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-20.png"));
		
		CLabel lblPoXaxisRange = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisRange.setText("Range:");
		
		Spinner spinnerPoXaxisRangeFrom = new Spinner(grpPlotoptionsXAxisRow, SWT.BORDER);
		spinnerPoXaxisRangeFrom.setMaximum(99999);
		spinnerPoXaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoXaxisRangeTo = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisRangeTo.setText("to");
		
		Spinner spinnerPoXaxisRangeTo = new Spinner(grpPlotoptionsXAxisRow, SWT.BORDER);
		spinnerPoXaxisRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoXaxisRangeTo.setMaximum(99999);
		spinnerPoXaxisRangeTo.setMinimum(-99999);
		
		CLabel lblPoXaxisDivisioning = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisDivisioning.setText("Divisioning:");
		
		Button btnPoXaxisPiDivisioning = new Button(grpPlotoptionsXAxisRow, SWT.CHECK);
		btnPoXaxisPiDivisioning.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		btnPoXaxisPiDivisioning.setText("Pi Divisioning");
		
		CLabel lblPoXaxisHelplines = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisHelplines.setText("Helplines:");
		
		txtPoXaxisHelplines = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisHelplines.setToolTipText("Enter y axis intersection points to define x axis helplines");
		txtPoXaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(grpPlotoptionsXAxisRow, SWT.NONE);
		
		Group grpPlotoptionsYAxisRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsYAxisRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpPlotoptionsYAxisRow.setText("Y Axis");
		grpPlotoptionsYAxisRow.setLayout(new GridLayout(5, false));
		
		CLabel lblPoYaxisTitle = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisTitle.setText("Title:");
		
		txtPoYaxisTitle = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisTitle.setToolTipText("Enter y axis title");
		txtPoYaxisTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsYAxisStyle = new Button(grpPlotoptionsYAxisRow, SWT.FLAT);
		btnPlotoptionsYAxisStyle.setToolTipText("Change style");
		btnPlotoptionsYAxisStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-20.png"));
		
		CLabel lblPoYaxisRange = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisRange.setText("Range:");
		
		Spinner spinnerPoYaxisRangeFrom = new Spinner(grpPlotoptionsYAxisRow, SWT.BORDER);
		spinnerPoYaxisRangeFrom.setMaximum(99999);
		spinnerPoYaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoYaxisTo = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisTo.setText("to");
		
		Spinner spinnerPoYaxisTo = new Spinner(grpPlotoptionsYAxisRow, SWT.BORDER);
		spinnerPoYaxisTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoYaxisTo.setMaximum(99999);
		spinnerPoYaxisTo.setMinimum(-99999);
		
		CLabel lblPoYaxisHelplines = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisHelplines.setText("Helplines:");
		
		txtPoYaxisHelplines = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisHelplines.setToolTipText("Enter x axis intersection points to define y axis helplines");
		txtPoYaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(grpPlotoptionsYAxisRow, SWT.NONE);
		
		Group grpPlotoptionsIntegralAreaRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsIntegralAreaRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpPlotoptionsIntegralAreaRow.setText("Integral Area");
		grpPlotoptionsIntegralAreaRow.setLayout(new GridLayout(5, false));
		
		CLabel lblPoIntegralTitle = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralTitle.setText("Title:");
		
		txtPoIntegralTitle = new Text(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		txtPoIntegralTitle.setToolTipText("Enter integral title");
		txtPoIntegralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsIntegralStyle = new Button(grpPlotoptionsIntegralAreaRow, SWT.FLAT);
		btnPlotoptionsIntegralStyle.setToolTipText("Change style");
		btnPlotoptionsIntegralStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-20.png"));
		
		CLabel lblPoIntegralRange = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralRange.setText("Range:");
		
		Spinner spinnerPoIntegralRangeFrom = new Spinner(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		spinnerPoIntegralRangeFrom.setMaximum(99999);
		spinnerPoIntegralRangeFrom.setMinimum(-99999);
		
		CLabel lblPoIntegralRangeTo = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralRangeTo.setText("to");
		
		Spinner spinnerPoIntegralRangeTo = new Spinner(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		spinnerPoIntegralRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoIntegralRangeTo.setMaximum(99999);
		spinnerPoIntegralRangeTo.setMinimum(-99999);
		
		CLabel lblPoIntegralBordering = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralBordering.setText("Bordering:");
		
		Combo comboPoIntegralBorderingFrom = new Combo(grpPlotoptionsIntegralAreaRow, SWT.READ_ONLY);
		comboPoIntegralBorderingFrom.setItems(new String[] {"A", "B", "C", "D"});
		
		CLabel lblPoIntegralAnd = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralAnd.setText("&&");
		
		Combo comboPoIntegralBorderingTo = new Combo(grpPlotoptionsIntegralAreaRow, SWT.READ_ONLY);
		comboPoIntegralBorderingTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		comboPoIntegralBorderingTo.setItems(new String[] {"x axis", "A", "B", "C", "D"});
		comboPoIntegralBorderingTo.select(0);
		scrolledCompositePlotoptionsColumn.setContent(compositePlotoptionsColumn);
		scrolledCompositePlotoptionsColumn.setMinSize(compositePlotoptionsColumn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		ScrolledComposite scrolledCompositePreviewoutputColumn = new ScrolledComposite(sashTopLevelColumns, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositePreviewoutputColumn.setExpandHorizontal(true);
		scrolledCompositePreviewoutputColumn.setExpandVertical(true);
		
		Composite compositePreviewOutputColumn = new Composite(scrolledCompositePreviewoutputColumn, SWT.NONE);
		compositePreviewOutputColumn.setBackground(SWTResourceManager.getColor(51, 51, 51));
		compositePreviewOutputColumn.setLayout(new GridLayout(1, false));
		
		CLabel lblPreviewOutputColumn = new CLabel(compositePreviewOutputColumn, SWT.NONE);
		lblPreviewOutputColumn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblPreviewOutputColumn.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 15, SWT.BOLD));
		lblPreviewOutputColumn.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblPreviewOutputColumn.setBackground(SWTResourceManager.getColor(51, 51, 51));
		lblPreviewOutputColumn.setText("Preview && Output");
		
		TabFolder tabFolderPreview = new TabFolder(compositePreviewOutputColumn, SWT.NONE);
		GridData gd_tabFolderPreview = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tabFolderPreview.heightHint = 350;
		tabFolderPreview.setLayoutData(gd_tabFolderPreview);
		
		TabItem tbtmPreviewScreenView = new TabItem(tabFolderPreview, SWT.NONE);
		tbtmPreviewScreenView.setText("Screen View");
		
		Composite compositePreviewScreenView = new Composite(tabFolderPreview, SWT.NONE);
		tbtmPreviewScreenView.setControl(compositePreviewScreenView);
		compositePreviewScreenView.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolderPreviewScreenView = new TabFolder(compositePreviewScreenView, SWT.NONE);
		
		TabItem tbtmPreviewScreenViewGraph = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewGraph.setText("Graph");
		
		Browser browserPreviewScreenViewGraph = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewGraph.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The graph's screen view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewScreenViewGraph.setControl(browserPreviewScreenViewGraph);
		
		TabItem tbtmPreviewScreenViewLegend = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewLegend.setText("Legend");
		
		Browser browserPreviewScreenViewLegend = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewLegend.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The legend's screen view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewScreenViewLegend.setControl(browserPreviewScreenViewLegend);
		
		TabItem tbtmPreviewScreenViewDescription = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewDescription.setText("Description");
		
		Browser browserPreviewScreenViewDescription = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewDescription.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The description's screen view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewScreenViewDescription.setControl(browserPreviewScreenViewDescription);
		
		TabItem tbtmPreviewPrintView = new TabItem(tabFolderPreview, SWT.NONE);
		tbtmPreviewPrintView.setText("Print View");
		
		Composite compositePreviewPrintView = new Composite(tabFolderPreview, SWT.NONE);
		tbtmPreviewPrintView.setControl(compositePreviewPrintView);
		compositePreviewPrintView.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolderPreviewPrintView = new TabFolder(compositePreviewPrintView, SWT.NONE);
		
		TabItem tbtmPreviewPrintViewGraph = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewGraph.setText("Graph");
		
		Browser browserPreviewPrintViewGraph = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewGraph.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The graph's print view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewPrintViewGraph.setControl(browserPreviewPrintViewGraph);
		
		TabItem tbtmPreviewPrintViewLegend = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewLegend.setText("Legend");
		
		Browser browserPreviewPrintViewLegend = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewLegend.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The legend's print view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewPrintViewLegend.setControl(browserPreviewPrintViewLegend);
		
		TabItem tbtmPreviewPrintViewDescription = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewDescription.setText("Description");
		
		Browser browserPreviewPrintViewDescription = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewDescription.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The description's print view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewPrintViewDescription.setControl(browserPreviewPrintViewDescription);
		
		Composite compositeOutput = new Composite(compositePreviewOutputColumn, SWT.NONE);
		compositeOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		compositeOutput.setBackground(SWTResourceManager.getColor(51, 51, 51));
		compositeOutput.setLayout(new GridLayout(1, false));
		
		Button btnOutputExportExport = new Button(compositeOutput, SWT.FLAT);
		btnOutputExportExport.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/finish-20.png"));
		btnOutputExportExport.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		btnOutputExportExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				EditFunctionStyle efs = new EditFunctionStyle(shlGsvgplott, 0);
				efs.open();
			}
		});
		btnOutputExportExport.setText("Export...");
		
		scrolledCompositePreviewoutputColumn.setContent(compositePreviewOutputColumn);
		scrolledCompositePreviewoutputColumn.setMinSize(compositePreviewOutputColumn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sashTopLevelColumns.setWeights(new int[] {2, 2, 3});
		
		Menu menu = new Menu(shlGsvgplott, SWT.BAR);
		shlGsvgplott.setMenuBar(menu);
		
		MenuItem mntmFileSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmFileSubmenu.setText("File");
		
		Menu file_1 = new Menu(mntmFileSubmenu);
		mntmFileSubmenu.setMenu(file_1);
		
		MenuItem mntmFileNew = new MenuItem(file_1, SWT.NONE);
		mntmFileNew.setText("New");
		
		MenuItem menuItemSeparator1 = new MenuItem(file_1, SWT.SEPARATOR);
		menuItemSeparator1.setText("sep");
		
		MenuItem mntmFileOpen = new MenuItem(file_1, SWT.NONE);
		mntmFileOpen.setText("Open...");
		
		MenuItem menuItemSeparator2 = new MenuItem(file_1, SWT.SEPARATOR);
		menuItemSeparator2.setText("sep");
		
		MenuItem mntmFileSave = new MenuItem(file_1, SWT.NONE);
		mntmFileSave.setText("Save");
		
		MenuItem mntmSaveAs = new MenuItem(file_1, SWT.NONE);
		mntmSaveAs.setText("Save As...");
		
		MenuItem mntmExtrasSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmExtrasSubmenu.setText("Extras");
		
		Menu extras_1 = new Menu(mntmExtrasSubmenu);
		mntmExtrasSubmenu.setMenu(extras_1);
		
		MenuItem mntmExtrasStyle = new MenuItem(extras_1, SWT.CASCADE);
		mntmExtrasStyle.setText("Style");
		
		Menu menu_1 = new Menu(mntmExtrasStyle);
		mntmExtrasStyle.setMenu(menu_1);
		
		MenuItem mntmExtrasStyleStyleManager = new MenuItem(menu_1, SWT.NONE);
		mntmExtrasStyleStyleManager.setText("Style Manager...");
		
		MenuItem mntmExtrasStyleStoreStyle = new MenuItem(menu_1, SWT.NONE);
		mntmExtrasStyleStoreStyle.setText("Store Style");
		
		MenuItem mntmExtrasStyleResetAll = new MenuItem(menu_1, SWT.NONE);
		mntmExtrasStyleResetAll.setText("Reset All");
		
		MenuItem mntmExtrasCssStyleOverride = new MenuItem(extras_1, SWT.NONE);
		mntmExtrasCssStyleOverride.setText("CSS Style Override...");
		
		MenuItem menuItemSeparator3 = new MenuItem(extras_1, SWT.SEPARATOR);
		menuItemSeparator3.setText("sep");
		
		MenuItem mntmExtrasLanguage = new MenuItem(extras_1, SWT.CASCADE);
		mntmExtrasLanguage.setText("Language");
		
		Menu menu_2 = new Menu(mntmExtrasLanguage);
		mntmExtrasLanguage.setMenu(menu_2);
		
		MenuItem mntmExtrasLanguageEnglish = new MenuItem(menu_2, SWT.RADIO);
		mntmExtrasLanguageEnglish.setSelection(true);
		mntmExtrasLanguageEnglish.setText("English (Default)");
		
		MenuItem mntmExtrasPreferences = new MenuItem(extras_1, SWT.NONE);
		mntmExtrasPreferences.setText("Preferences...");
		
		MenuItem mntmHelpSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmHelpSubmenu.setText("Help");
		
		Menu menu_3 = new Menu(mntmHelpSubmenu);
		mntmHelpSubmenu.setMenu(menu_3);
		
		MenuItem mntmHelpHelp = new MenuItem(menu_3, SWT.NONE);
		mntmHelpHelp.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/help-16.png"));
		mntmHelpHelp.setText("Help...");
		
		/*
		Button btnClose = new Button(shlGsvgplott, SWT.NONE);
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				MessageBox messageBox = new MessageBox(shlGsvgplott, SWT.ICON_QUESTION |SWT.YES | SWT.NO);
			    messageBox.setMessage("Are you sure that you want to close this application?");
			    int rc = messageBox.open();
			    
			    switch (rc) {
				case SWT.YES:
					shlGsvgplott.close();
					break;
				case SWT.NO:
					break;
				default: //do nothing
					break;
				}
			}
		});
		btnClose.setBounds(10, 10, 95, 28);
		btnClose.setText("Close");
		*/

	}
}
