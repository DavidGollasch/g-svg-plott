package de.tudresden.inf.gsvgplott.ui;

/**
 * 
 * @author David Gollasch
 *
 */

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import de.tudresden.inf.gsvgplott.data.Diagram;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWindow {
	
	public static final String APP_NAME = "gSVGPlott";

	protected Shell shlGsvgplott;
	
	/**
	 * The diagram object holds all data to be processed
	 */
	private Diagram diagram;
	
	/**
	 * List of all functions as Group widgets
	 */
	private List<Group> functions;
	private int functionIDcounter;
	
	/**
	 * List of all point lists as Group widgets
	 */
	private List<Group> pointlists;
	private int pointlistIDcounter;
	
	/**
	 * This shell will only be used to prepare widgets to show them later on.
	 */
	private Shell temporaryContainer;
	
	private Composite compositeDataColumn;
	private ScrolledComposite scrolledCompositeDataColumn;
	private SashForm sashTopLevelColumns;

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
		diagram = new Diagram(null, 0, false, null, null);
		
		functions = new ArrayList<Group>();
		functionIDcounter = 0;
		pointlists = new ArrayList<Group>();
		pointlistIDcounter = 0;
		
		temporaryContainer = new Shell();
		temporaryContainer.setLayout(new GridLayout(1, false));
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		
		createContents();
		operatePrepareView(); //invoked to prepare all view details before opening
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
		
		sashTopLevelColumns = new SashForm(shlGsvgplott, SWT.NONE);
		sashTopLevelColumns.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
		
		scrolledCompositeDataColumn = new ScrolledComposite(sashTopLevelColumns, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositeDataColumn.setExpandVertical(true);
		scrolledCompositeDataColumn.setExpandHorizontal(true);
		
		compositeDataColumn = new Composite(scrolledCompositeDataColumn, SWT.NONE);
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
		
		CLabel lblDRFTitle1 = new CLabel(grpDataRowFunction1, SWT.NONE);
		lblDRFTitle1.setText("Title:");
		
		Text txtDRFTitle1 = new Text(grpDataRowFunction1, SWT.BORDER);
		txtDRFTitle1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnDRFStyle1 = new Button(grpDataRowFunction1, SWT.FLAT);
		btnDRFStyle1.setToolTipText("Change style");
		btnDRFStyle1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblDRFfunc1 = new CLabel(grpDataRowFunction1, SWT.NONE);
		lblDRFfunc1.setSize(39, 19);
		lblDRFfunc1.setText("y(x) = ");
		
		Text txtDRFfunc1 = new Text(grpDataRowFunction1, SWT.BORDER);
		txtDRFfunc1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDRFfunc1.setSize(194, 19);
		new Label(grpDataRowFunction1, SWT.NONE);
		
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
		btnDRFMoveUp1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/up-16.png"));
		
		Button btnDRFMoveDown1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFMoveDown1.setToolTipText("Move item down");
		btnDRFMoveDown1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnDRFMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-16.png"));
		
		Button btnDRFRemove1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFRemove1.setToolTipText("Remove item");
		GridData gd_btnDRFRemove1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnDRFRemove1.horizontalIndent = 5;
		btnDRFRemove1.setLayoutData(gd_btnDRFRemove1);
		btnDRFRemove1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/remove-16.png"));
		btnDRFRemove1.setText("Remove");
		
		Button btnDataColumnFunctionsAddFunction = new Button(compositeDataColumn, SWT.FLAT);
		btnDataColumnFunctionsAddFunction.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/add-16.png"));
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
		
		Text txtDRMtitle1 = new Text(grpDataRowMarkedpointsPointList, SWT.BORDER);
		txtDRMtitle1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button btnDRMStyle1 = new Button(grpDataRowMarkedpointsPointList, SWT.FLAT);
		btnDRMStyle1.setToolTipText("Change style");
		btnDRMStyle1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		Composite compositeDRMlist1 = new Composite(grpDataRowMarkedpointsPointList, SWT.NONE);
		compositeDRMlist1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1));
		compositeDRMlist1.setLayout(null);
		
		Table tableDRMlist1 = new Table(compositeDRMlist1, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
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
		btnDRMMoveUp1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/up-16.png"));
		
		Button btnDRMMoveDown1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMMoveDown1.setToolTipText("Move item down");
		btnDRMMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-16.png"));
		
		Button btnDRMRemove1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMRemove1.setToolTipText("Remove item");
		GridData gd_btnDRMRemove1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDRMRemove1.horizontalIndent = 5;
		btnDRMRemove1.setLayoutData(gd_btnDRMRemove1);
		btnDRMRemove1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/remove-16.png"));
		btnDRMRemove1.setText("Remove");
		
		Button btnDataColumnListsAddPointList = new Button(compositeDataColumn, SWT.FLAT);
		btnDataColumnListsAddPointList.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/add-16.png"));
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
		grpPlotoptionsGeneralRow.setLayout(new GridLayout(4, false));
		
		CLabel lblPoGeneralTitle = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralTitle.setText("Title:");
		
		Text txtPoGeneralTitle = new Text(grpPlotoptionsGeneralRow, SWT.BORDER);
		txtPoGeneralTitle.setToolTipText("Enter title of diagram");
		txtPoGeneralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button btnPlotoptionsGeneralStyle = new Button(grpPlotoptionsGeneralRow, SWT.FLAT);
		btnPlotoptionsGeneralStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerOptionsGeneralStyleToolbox();
			}
		});
		btnPlotoptionsGeneralStyle.setToolTipText("Change style");
		btnPlotoptionsGeneralStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblPoGeneralSize = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSize.setText("Size (Width):");
		
		Spinner spinnerPoGeneralSize = new Spinner(grpPlotoptionsGeneralRow, SWT.BORDER);
		spinnerPoGeneralSize.setIncrement(10);
		spinnerPoGeneralSize.setMaximum(99999);
		spinnerPoGeneralSize.setMinimum(0);
		spinnerPoGeneralSize.setSelection(217);
		
		CLabel lblPoGeneralSizeMm = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSizeMm.setText("mm");
		new Label(grpPlotoptionsGeneralRow, SWT.NONE);
		
		CLabel lblPoGeneralGrid = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralGrid.setText("Grid:");
		
		Button btnPoGeneralShowGrid = new Button(grpPlotoptionsGeneralRow, SWT.CHECK);
		btnPoGeneralShowGrid.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		btnPoGeneralShowGrid.setText("Show Grid");
		
		Group grpPlotoptionsXAxisRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsXAxisRow.setLayout(new GridLayout(5, false));
		grpPlotoptionsXAxisRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpPlotoptionsXAxisRow.setText("X Axis");
		
		CLabel lblPoXaxisTitle = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisTitle.setText("Title:");
		
		Text txtPoXaxisTitle = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtPoXaxisTitle.setToolTipText("Set x axis title");
		
		Button btnPlotoptionsXAxisStyle = new Button(grpPlotoptionsXAxisRow, SWT.FLAT);
		btnPlotoptionsXAxisStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerOptionsXaxisStyleToolbox();
			}
		});
		btnPlotoptionsXAxisStyle.setToolTipText("Change style");
		btnPlotoptionsXAxisStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
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
		
		Text txtPoXaxisHelplines = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisHelplines.setToolTipText("Enter y axis intersection points to define x axis helplines");
		txtPoXaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsXAxisHelplineStyle = new Button(grpPlotoptionsXAxisRow, SWT.FLAT);
		btnPlotoptionsXAxisHelplineStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerOptionsXaxisHelplineStyleToolbox();
			}
		});
		btnPlotoptionsXAxisHelplineStyle.setToolTipText("Change style");
		btnPlotoptionsXAxisHelplineStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		Group grpPlotoptionsYAxisRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsYAxisRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpPlotoptionsYAxisRow.setText("Y Axis");
		grpPlotoptionsYAxisRow.setLayout(new GridLayout(5, false));
		
		CLabel lblPoYaxisTitle = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisTitle.setText("Title:");
		
		Text txtPoYaxisTitle = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisTitle.setToolTipText("Enter y axis title");
		txtPoYaxisTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsYAxisStyle = new Button(grpPlotoptionsYAxisRow, SWT.FLAT);
		btnPlotoptionsYAxisStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerOptionsYaxisStyleToolbox();
			}
		});
		btnPlotoptionsYAxisStyle.setToolTipText("Change style");
		btnPlotoptionsYAxisStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
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
		
		Text txtPoYaxisHelplines = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisHelplines.setToolTipText("Enter x axis intersection points to define y axis helplines");
		txtPoYaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsYAxisHelplineStyle = new Button(grpPlotoptionsYAxisRow, SWT.FLAT);
		btnPlotoptionsYAxisHelplineStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerOptionsYaxisHelplineStyleToolbox();
			}
		});
		btnPlotoptionsYAxisHelplineStyle.setToolTipText("Change style");
		btnPlotoptionsYAxisHelplineStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		Group grpPlotoptionsIntegralAreaRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsIntegralAreaRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpPlotoptionsIntegralAreaRow.setText("Integral Area");
		grpPlotoptionsIntegralAreaRow.setLayout(new GridLayout(5, false));
		
		CLabel lblPoIntegralTitle = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralTitle.setText("Title:");
		
		Text txtPoIntegralTitle = new Text(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		txtPoIntegralTitle.setToolTipText("Enter integral title");
		txtPoIntegralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsIntegralStyle = new Button(grpPlotoptionsIntegralAreaRow, SWT.FLAT);
		btnPlotoptionsIntegralStyle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerOptionsIntegralStyleToolbox();
			}
		});
		btnPlotoptionsIntegralStyle.setToolTipText("Change style");
		btnPlotoptionsIntegralStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
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
		tbtmPreviewScreenViewGraph.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tbtmPreviewScreenViewGraph.setText("Graph");
		
		Browser browserPreviewScreenViewGraph = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewGraph.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The graph's screen view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewScreenViewGraph.setControl(browserPreviewScreenViewGraph);
		
		TabItem tbtmPreviewScreenViewLegend = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewLegend.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/legend-16.png"));
		tbtmPreviewScreenViewLegend.setText("Legend");
		
		Browser browserPreviewScreenViewLegend = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewLegend.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The legend's screen view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewScreenViewLegend.setControl(browserPreviewScreenViewLegend);
		
		TabItem tbtmPreviewScreenViewDescription = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewDescription.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/list-16.png"));
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
		tbtmPreviewPrintViewGraph.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tbtmPreviewPrintViewGraph.setText("Graph");
		
		Browser browserPreviewPrintViewGraph = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewGraph.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The graph's print view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewPrintViewGraph.setControl(browserPreviewPrintViewGraph);
		
		TabItem tbtmPreviewPrintViewLegend = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewLegend.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/legend-16.png"));
		tbtmPreviewPrintViewLegend.setText("Legend");
		
		Browser browserPreviewPrintViewLegend = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewLegend.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The legend's print view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewPrintViewLegend.setControl(browserPreviewPrintViewLegend);
		
		TabItem tbtmPreviewPrintViewDescription = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewDescription.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/list-16.png"));
		tbtmPreviewPrintViewDescription.setText("Description");
		
		Browser browserPreviewPrintViewDescription = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewDescription.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The description's print view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewPrintViewDescription.setControl(browserPreviewPrintViewDescription);
		
		Composite compositeOutput = new Composite(compositePreviewOutputColumn, SWT.NONE);
		compositeOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		compositeOutput.setBackground(SWTResourceManager.getColor(51, 51, 51));
		compositeOutput.setLayout(new GridLayout(1, false));
		
		Button btnOutputExportExport = new Button(compositeOutput, SWT.FLAT);
		btnOutputExportExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerExport();
			}
		});
		btnOutputExportExport.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/share-16.png"));
		btnOutputExportExport.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
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
		mntmFileNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuNew();
			}
		});
		mntmFileNew.setText("New");
		
		MenuItem menuItemSeparator1 = new MenuItem(file_1, SWT.SEPARATOR);
		menuItemSeparator1.setText("sep");
		
		MenuItem mntmFileOpen = new MenuItem(file_1, SWT.NONE);
		mntmFileOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuOpen();
			}
		});
		mntmFileOpen.setText("Open...");
		
		MenuItem menuItemSeparator2 = new MenuItem(file_1, SWT.SEPARATOR);
		menuItemSeparator2.setText("sep");
		
		MenuItem mntmFileSave = new MenuItem(file_1, SWT.NONE);
		mntmFileSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuSave();
			}
		});
		mntmFileSave.setText("Save");
		
		MenuItem mntmSaveAs = new MenuItem(file_1, SWT.NONE);
		mntmSaveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuSaveAs();
			}
		});
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
		mntmExtrasStyleStyleManager.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuStyleManager();
			}
		});
		mntmExtrasStyleStyleManager.setText("Style Manager...");
		
		MenuItem mntmExtrasStyleStoreStyle = new MenuItem(menu_1, SWT.NONE);
		mntmExtrasStyleStoreStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuStoreStyle();
			}
		});
		mntmExtrasStyleStoreStyle.setText("Store Style");
		
		MenuItem mntmExtrasStyleResetAll = new MenuItem(menu_1, SWT.NONE);
		mntmExtrasStyleResetAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuResetStyle();
			}
		});
		mntmExtrasStyleResetAll.setText("Reset All");
		
		MenuItem mntmExtrasCssStyleOverride = new MenuItem(extras_1, SWT.NONE);
		mntmExtrasCssStyleOverride.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuCssStyleOverride();
			}
		});
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
		mntmExtrasPreferences.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuPreferences();
			}
		});
		mntmExtrasPreferences.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/settings-16.png"));
		mntmExtrasPreferences.setText("Preferences...");
		
		MenuItem mntmHelpSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmHelpSubmenu.setText("Help");
		
		Menu menu_3 = new Menu(mntmHelpSubmenu);
		mntmHelpSubmenu.setMenu(menu_3);
		
		MenuItem mntmHelpHelp = new MenuItem(menu_3, SWT.NONE);
		mntmHelpHelp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuHelp();
			}
		});
		mntmHelpHelp.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/help-16.png"));
		mntmHelpHelp.setText("Help...");

	}

	/**
	 * Add a new function
	 */
	protected void triggerDataAddFunction() {
		this.operateAddFunction();
	}
	
	/**
	 * Add a new point list
	 */
	protected void triggerDataAddPointList() {
		this.operateAddPointList();
	}
	
	/**
	 * Removes a desired function
	 * @param function
	 */
	protected void triggerDataRemoveFunction(Group function) {
		this.operateRemoveFunction(function);
	}
	
	/**
	 * Removes a desired point list
	 * @param pointlist
	 */
	protected void triggerDataRemovePointList(Group pointlist) {
		this.operateRemovePointList(pointlist);
	}
	
	/**
	 * Moves a desired function up
	 * @param function
	 */
	protected void triggerDataMoveUpFunction(Group function) {
		this.operateMoveUpFunction(function);
	}
	
	/**
	 * Moves a desired point list up
	 * @param pointlist
	 */
	protected void triggerDataMoveUpPointList(Group pointlist) {
		this.operateMoveUpPointList(pointlist);
	}
	
	/**
	 * Moves a desired function down
	 * @param function
	 */
	protected void triggerDataMoveDownFunction(Group function) {
		this.operateMoveDownFunction(function);
	}
	
	/**
	 * Moves a desired point list down
	 * @param pointlist
	 */
	protected void triggerDataMoveDownPointList(Group pointlist) {
		this.operateMoveDownPointList(pointlist);
	}
	
	/**
	 * Open tool box for General styling
	 */
	protected void triggerOptionsGeneralStyleToolbox() {
		GeneralStyleToolbox gt = new GeneralStyleToolbox(shlGsvgplott, 0);
		gt.setOpeningLocation(Display.getDefault().getCursorLocation());
		gt.open();
	}
	
	/**
	 * Open tool box for X Axis styling
	 */
	protected void triggerOptionsXaxisStyleToolbox() {
		LineStyleToolbox lt = new LineStyleToolbox(shlGsvgplott, 0);
		lt.setOpeningLocation(Display.getDefault().getCursorLocation());
		lt.open();
		
	}
	
	/**
	 * Open tool box for X Axis Helpline styling
	 */
	protected void triggerOptionsXaxisHelplineStyleToolbox() {
		LineStyleToolbox lt = new LineStyleToolbox(shlGsvgplott, 0);
		lt.setOpeningLocation(Display.getDefault().getCursorLocation());
		lt.open();
	}
	
	/**
	 * Open tool box for Y Axis styling
	 */
	protected void triggerOptionsYaxisStyleToolbox() {
		LineStyleToolbox lt = new LineStyleToolbox(shlGsvgplott, 0);
		lt.setOpeningLocation(Display.getDefault().getCursorLocation());
		lt.open();
	}
	
	/**
	 * Open tool box for Y Axis Helpline styling
	 */
	protected void triggerOptionsYaxisHelplineStyleToolbox() {
		LineStyleToolbox lt = new LineStyleToolbox(shlGsvgplott, 0);
		lt.setOpeningLocation(Display.getDefault().getCursorLocation());
		lt.open();
	}
	
	/**
	 * Open tool box for Integral styling
	 */
	protected void triggerOptionsIntegralStyleToolbox() {
		AreaStyleToolbox at = new AreaStyleToolbox(shlGsvgplott, 0);
		at.setOpeningLocation(Display.getDefault().getCursorLocation());
		at.open();
	}

	/**
	 * Export project
	 */
	protected void triggerExport() {
		FileDialog fd = new FileDialog(shlGsvgplott, SWT.SAVE);
        fd.setText("Export");
        //fd.setFilterPath("C:/");
        String[] filterExt = { "*.svg", "*.xml", "*.*" };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        
		//TODO: export file
        System.out.println(selected);
	}

	/**
	 * Create new file
	 */
	protected void triggerMenuNew() {
		//TODO: new file
	}
	
	/**
	 * Open file
	 */
	protected void triggerMenuOpen() {
		FileDialog fd = new FileDialog(shlGsvgplott, SWT.OPEN);
        fd.setText("Open");
        //fd.setFilterPath("C:/");
        String[] filterExt = { "*.gsp", "*.xml", "*.*" };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        
        //TODO: actually open the file, parse data, update view and diagram object
        System.out.println(selected);
	}
	
	/**
	 * Save file
	 */
	protected void triggerMenuSave() {
        
		//TODO: save file if file name already known
		
		triggerMenuSaveAs();
	}
	
	/**
	 * Save file as
	 */
	protected void triggerMenuSaveAs() {
		FileDialog fd = new FileDialog(shlGsvgplott, SWT.SAVE);
        fd.setText("Save");
        //fd.setFilterPath("C:/");
        String[] filterExt = { "*.gsp", "*.xml", "*.*" };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        
		//TODO: save file as
        System.out.println(selected);
	}
	
	/**
	 * Open Style Manager
	 */
	protected void triggerMenuStyleManager() {
		StyleManagerDialog sd = new StyleManagerDialog(shlGsvgplott, 0);
		sd.open();
	}
	
	/**
	 * Store current style
	 */
	protected void triggerMenuStoreStyle() {
		StoreStyleDialog sd = new StoreStyleDialog(shlGsvgplott, 0);
		sd.open();
	}
	
	/**
	 * Reset Style
	 */
	protected void triggerMenuResetStyle() {
		//TODO: Reset Style
	}
	
	/**
	 * Open CSS Style Override Dialog
	 */
	protected void triggerMenuCssStyleOverride() {
		//TODO: Open CSS Style Override Dialog
		CssStyleOverrideDialog cd = new CssStyleOverrideDialog(shlGsvgplott, 0);
		cd.open();
	}
	
	/**
	 * Open Preferences Dialog
	 */
	protected void triggerMenuPreferences() {
		PreferencesDialog pd = new PreferencesDialog(shlGsvgplott, 0);
		pd.open();
	}
	
	/**
	 * Open Help Dialog
	 */
	protected void triggerMenuHelp() {
		HelpDialog hd = new HelpDialog(shlGsvgplott, 0);
		hd.open();
	}
	
	/**
	 * This method will be invoked on startup to prepare all views and contents to start
	 * working (remove example values, fill default values, etc.)
	 */
	protected void operatePrepareView() {
		//TODO: to be implemented
		this.operateRecreateDataColumn();
	}
	
	protected void operateRecreateDataColumn() {
		// remove all controls first
		for (Control widget : this.compositeDataColumn.getChildren()) {
			if(widget instanceof Group) {
				widget.setParent(temporaryContainer);
			} else {
				widget.dispose();
			}
		}
		
		//create new controls then
		
		// a) fixed area (labels)
		
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
		
		
		// b) variable area (functions)
		
		for (Group function : functions) {
			function.setParent(compositeDataColumn);
		}
		
		// c) fixed area (add function button)
		
		Button btnDataColumnFunctionsAddFunction = new Button(compositeDataColumn, SWT.FLAT);
		btnDataColumnFunctionsAddFunction.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/add-16.png"));
		btnDataColumnFunctionsAddFunction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnDataColumnFunctionsAddFunction.setText("Add Function");
		btnDataColumnFunctionsAddFunction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerDataAddFunction();
			}
		});
		
		// d) fixed area (labels)
		
		CLabel lblDataRowMarkedpoints = new CLabel(compositeDataColumn, SWT.NONE);
		lblDataRowMarkedpoints.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDataRowMarkedpoints.setText("Marked Points");
		
		Label lblSepMarkedpointsRow = new Label(compositeDataColumn, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSepMarkedpointsRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		// e) variable area (point lists)
		
		for (Group pointlist : pointlists) {
			pointlist.setParent(compositeDataColumn);
		}
		
		// f) fixed area (add point list button)
		
		Button btnDataColumnListsAddPointList = new Button(compositeDataColumn, SWT.FLAT);
		btnDataColumnListsAddPointList.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/add-16.png"));
		btnDataColumnListsAddPointList.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnDataColumnListsAddPointList.setText("Add Point List");
		btnDataColumnListsAddPointList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerDataAddPointList();
			}
		});
		
		// update the view (resize and reposition all elements correctly)
		this.compositeDataColumn.layout();
		scrolledCompositeDataColumn.setMinSize(compositeDataColumn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
	}

	/**
	 * Adds a new function group and triggers an appropriate redraw
	 */
	protected void operateAddFunction() {
		functionIDcounter++;
		String name = "Function " + String.valueOf(functionIDcounter);
		
		Group grpDataRowFunction1 = new Group(temporaryContainer, SWT.NONE);
		grpDataRowFunction1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpDataRowFunction1.setText(name);
		grpDataRowFunction1.setLayout(new GridLayout(3, false));
		
		CLabel lblDRFTitle1 = new CLabel(grpDataRowFunction1, SWT.NONE);
		lblDRFTitle1.setText("Title:");
		
		Text txtDRFTitle1 = new Text(grpDataRowFunction1, SWT.BORDER);
		txtDRFTitle1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnDRFStyle1 = new Button(grpDataRowFunction1, SWT.FLAT);
		btnDRFStyle1.setToolTipText("Change style");
		btnDRFStyle1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblDRFfunc1 = new CLabel(grpDataRowFunction1, SWT.NONE);
		lblDRFfunc1.setSize(39, 19);
		lblDRFfunc1.setText("y(x) = ");
		
		Text txtDRFfunc1 = new Text(grpDataRowFunction1, SWT.BORDER);
		txtDRFfunc1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDRFfunc1.setSize(194, 19);
		new Label(grpDataRowFunction1, SWT.NONE);
		
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
		btnDRFMoveUp1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/up-16.png"));
		btnDRFMoveUp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerDataMoveUpFunction(grpDataRowFunction1);
			}
		});
		
		Button btnDRFMoveDown1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFMoveDown1.setToolTipText("Move item down");
		btnDRFMoveDown1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnDRFMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-16.png"));
		btnDRFMoveDown1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerDataMoveDownFunction(grpDataRowFunction1);
			}
		});
		
		Button btnDRFRemove1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFRemove1.setToolTipText("Remove item");
		GridData gd_btnDRFRemove1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnDRFRemove1.horizontalIndent = 5;
		btnDRFRemove1.setLayoutData(gd_btnDRFRemove1);
		btnDRFRemove1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/remove-16.png"));
		btnDRFRemove1.setText("Remove");
		btnDRFRemove1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerDataRemoveFunction(grpDataRowFunction1);
			}
		});
		
		functions.add(grpDataRowFunction1);
		this.operateRecreateDataColumn();
	}
	
	/**
	 * Adds a new point list and triggers redraw
	 */
	protected void operateAddPointList() {
		pointlistIDcounter++;
		String name = "Point List " + String.valueOf(pointlistIDcounter);
		
		Group grpDataRowMarkedpointsPointList = new Group(temporaryContainer, SWT.NONE);
		grpDataRowMarkedpointsPointList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpDataRowMarkedpointsPointList.setLayout(new GridLayout(4, false));
		grpDataRowMarkedpointsPointList.setText(name);
		
		CLabel lblDRMTitle1 = new CLabel(grpDataRowMarkedpointsPointList, SWT.NONE);
		lblDRMTitle1.setText("Title:");
		
		Text txtDRMtitle1 = new Text(grpDataRowMarkedpointsPointList, SWT.BORDER);
		txtDRMtitle1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button btnDRMStyle1 = new Button(grpDataRowMarkedpointsPointList, SWT.FLAT);
		btnDRMStyle1.setToolTipText("Change style");
		btnDRMStyle1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		Composite compositeDRMlist1 = new Composite(grpDataRowMarkedpointsPointList, SWT.NONE);
		compositeDRMlist1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1));
		compositeDRMlist1.setLayout(null);
		
		Table tableDRMlist1 = new Table(compositeDRMlist1, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
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
		btnDRMMoveUp1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/up-16.png"));
		btnDRMMoveUp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerDataMoveUpPointList(grpDataRowMarkedpointsPointList);
			}
		});
		
		Button btnDRMMoveDown1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMMoveDown1.setToolTipText("Move item down");
		btnDRMMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-16.png"));
		btnDRMMoveDown1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerDataMoveDownPointList(grpDataRowMarkedpointsPointList);
			}
		});
		
		Button btnDRMRemove1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMRemove1.setToolTipText("Remove item");
		GridData gd_btnDRMRemove1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDRMRemove1.horizontalIndent = 5;
		btnDRMRemove1.setLayoutData(gd_btnDRMRemove1);
		btnDRMRemove1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/remove-16.png"));
		btnDRMRemove1.setText("Remove");
		btnDRMRemove1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				triggerDataRemovePointList(grpDataRowMarkedpointsPointList);
			}
		});
		
		pointlists.add(grpDataRowMarkedpointsPointList);
		this.operateRecreateDataColumn();
	}
	
	/**
	 * Removes a desired function if existing
	 * @param function
	 */
	protected void operateRemoveFunction(Group function) {
		if(functions.contains(function)) {
			functions.remove(function);
			this.operateRecreateDataColumn();
		}
	}
	
	/**
	 * Removes a desired point list if existing
	 * @param pointlist
	 */
	protected void operateRemovePointList(Group pointlist) {
		if(pointlists.contains(pointlist)) {
			pointlists.remove(pointlist);
			this.operateRecreateDataColumn();
		}
	}
	
	/**
	 * Moves a function up in the list of functions if possible
	 * @param function
	 */
	protected void operateMoveUpFunction(Group function) {
		if(functions.contains(function)) {
			int index = functions.indexOf(function);
			if(index > 0) {
				functions.remove(index);
				functions.add(index-1, function);
			}
			
			this.operateRecreateDataColumn();
		}
	}
	
	/**
	 * Moves a point list up in the list of point lists if possible
	 * @param pointlist
	 */
	protected void operateMoveUpPointList(Group pointlist) {
		if(pointlists.contains(pointlist)) {
			int index = pointlists.indexOf(pointlist);
			if(index > 0) {
				pointlists.remove(index);
				pointlists.add(index-1, pointlist);
				
				this.operateRecreateDataColumn();
			}
		}
	}
	
	/**
	 * Moves a function down in the list of functions if possible
	 * @param function
	 */
	protected void operateMoveDownFunction(Group function) {
		if(functions.contains(function)) {
			int index = functions.indexOf(function);
			if(index < functions.size()-1) {
				functions.remove(index);
				functions.add(index+1, function);
				
				this.operateRecreateDataColumn();
			}
		}
	}
	
	/**
	 * Moves a point list down in the list of point lists if possible
	 * @param pointlist
	 */
	protected void operateMoveDownPointList(Group pointlist) {
		if(pointlists.contains(pointlist)) {
			int index = pointlists.indexOf(pointlist);
			if(index < pointlists.size()-1) {
				pointlists.remove(index);
				pointlists.add(index+1, pointlist);
				
				this.operateRecreateDataColumn();
			}
		}
	}
	
	/**
	 * This method invokes the creation of preview diagrams out of the current Diagram instance
	 */
	protected void operateGeneratePreview() {
		//TODO: to be implemented
	}
}
