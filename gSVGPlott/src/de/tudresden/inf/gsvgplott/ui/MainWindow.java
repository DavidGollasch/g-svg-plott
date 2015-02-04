package de.tudresden.inf.gsvgplott.ui;

/**
 * 
 * @author David Gollasch
 *
 */

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
	private Text txtDRMlistControlsX1;
	private Text txtDRMlistControlsY1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
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
		shlGsvgplott.setSize(new Point(1024, 600));
		shlGsvgplott.setMinimumSize(new Point(500, 400));
		shlGsvgplott.setText(MainWindow.APP_NAME);
		shlGsvgplott.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashTopLevelColumns = new SashForm(shlGsvgplott, SWT.NONE);
		
		Group grpDataColumn = new Group(sashTopLevelColumns, SWT.NONE);
		grpDataColumn.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 15, SWT.BOLD));
		grpDataColumn.setText("Data");
		grpDataColumn.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ScrolledComposite scrolledCompositeDataColumn = new ScrolledComposite(grpDataColumn, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositeDataColumn.setExpandVertical(true);
		scrolledCompositeDataColumn.setExpandHorizontal(true);
		
		Composite compositeDataColumn = new Composite(scrolledCompositeDataColumn, SWT.NONE);
		compositeDataColumn.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashDataColumnRows = new SashForm(compositeDataColumn, SWT.BORDER | SWT.VERTICAL);
		
		Composite compositeDataRow = new Composite(sashDataColumnRows, SWT.BORDER);
		compositeDataRow.setLayout(new GridLayout(1, false));
		
		CLabel lblDataRowFunctions = new CLabel(compositeDataRow, SWT.NONE);
		lblDataRowFunctions.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		lblDataRowFunctions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblDataRowFunctions.setText("Functions");
		
		Label lblSepDataRowFunctions = new Label(compositeDataRow, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSepDataRowFunctions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblSepDataRowFunctions.setText("sep");
		
		Group grpDataRowFunction1 = new Group(compositeDataRow, SWT.NONE);
		grpDataRowFunction1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpDataRowFunction1.setText("Function 1");
		grpDataRowFunction1.setLayout(new GridLayout(3, false));
		
		CLabel lblDRFfunc1 = new CLabel(grpDataRowFunction1, SWT.NONE);
		lblDRFfunc1.setSize(39, 19);
		lblDRFfunc1.setText("y(x) = ");
		
		txtDRFfunc1 = new Text(grpDataRowFunction1, SWT.BORDER);
		txtDRFfunc1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDRFfunc1.setSize(194, 19);
		
		Composite compositeDRFControls1 = new Composite(grpDataRowFunction1, SWT.NONE);
		compositeDRFControls1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 2));
		compositeDRFControls1.setLayout(new GridLayout(1, false));
		
		Button btnDRFAdd1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFAdd1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDRFAdd1.setText("+");
		
		Button btnDRFRemove1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFRemove1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDRFRemove1.setText("-");
		
		ExpandBar expandBarDRF1Style = new ExpandBar(grpDataRowFunction1, SWT.NONE);
		expandBarDRF1Style.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		ExpandItem xpndtmDRFStyle1 = new ExpandItem(expandBarDRF1Style, SWT.NONE);
		xpndtmDRFStyle1.setExpanded(true);
		xpndtmDRFStyle1.setText("Style");
		
		Composite compositeDRFStyle1 = new Composite(expandBarDRF1Style, SWT.NONE);
		xpndtmDRFStyle1.setControl(compositeDRFStyle1);
		xpndtmDRFStyle1.setHeight(xpndtmDRFStyle1.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		compositeDRFStyle1.setLayout(new GridLayout(4, false));
		
		CLabel lblDRFStyleScreen1 = new CLabel(compositeDRFStyle1, SWT.NONE);
		lblDRFStyleScreen1.setText("Screen:");
		
		Combo comboDRFStyleScreenLType1 = new Combo(compositeDRFStyle1, SWT.READ_ONLY);
		comboDRFStyleScreenLType1.setItems(new String[] {"----", "===="});
		comboDRFStyleScreenLType1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboDRFStyleScreenLType1.select(0);
		
		Spinner spinnerDRFStyleScreenLWidth1 = new Spinner(compositeDRFStyle1, SWT.BORDER);
		spinnerDRFStyleScreenLWidth1.setMinimum(1);
		spinnerDRFStyleScreenLWidth1.setSelection(1);
		
		Combo comboDRFStyleScreenLColor1 = new Combo(compositeDRFStyle1, SWT.READ_ONLY);
		comboDRFStyleScreenLColor1.setItems(new String[] {"black", "gray", "green", "red", "blue", "yellow", "white"});
		comboDRFStyleScreenLColor1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboDRFStyleScreenLColor1.select(0);
		
		CLabel lblDRFStylePrint1 = new CLabel(compositeDRFStyle1, SWT.NONE);
		lblDRFStylePrint1.setText("Print:");
		
		Combo comboDRFStylePrintLType1 = new Combo(compositeDRFStyle1, SWT.READ_ONLY);
		comboDRFStylePrintLType1.setItems(new String[] {"----", "===="});
		comboDRFStylePrintLType1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboDRFStylePrintLType1.select(0);
		
		Spinner spinnerDRFStylePrintLWidth1 = new Spinner(compositeDRFStyle1, SWT.BORDER);
		spinnerDRFStylePrintLWidth1.setMinimum(1);
		spinnerDRFStylePrintLWidth1.setSelection(1);
		
		Combo comboDRFStylePrintLColor1 = new Combo(compositeDRFStyle1, SWT.READ_ONLY);
		comboDRFStylePrintLColor1.setItems(new String[] {"black", "gray", "green", "red", "blue", "yellow", "white"});
		comboDRFStylePrintLColor1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboDRFStylePrintLColor1.select(0);
		
		CLabel lblDataRowMarkedpoints = new CLabel(compositeDataRow, SWT.NONE);
		lblDataRowMarkedpoints.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		lblDataRowMarkedpoints.setText("Marked Points");
		
		Label lblSepMarkedpointsRow = new Label(compositeDataRow, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSepMarkedpointsRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Group grpDataRowMarkedpointsPointList = new Group(compositeDataRow, SWT.NONE);
		grpDataRowMarkedpointsPointList.setLayout(new GridLayout(3, false));
		grpDataRowMarkedpointsPointList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpDataRowMarkedpointsPointList.setText("Point List 1");
		
		CLabel lblDRMTitle1 = new CLabel(grpDataRowMarkedpointsPointList, SWT.NONE);
		lblDRMTitle1.setText("Title:");
		
		txtDRMtitle1 = new Text(grpDataRowMarkedpointsPointList, SWT.BORDER);
		txtDRMtitle1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite compositeDRMControls1 = new Composite(grpDataRowMarkedpointsPointList, SWT.NONE);
		compositeDRMControls1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));
		compositeDRMControls1.setLayout(new GridLayout(1, false));
		
		Button btnDRMAdd1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMAdd1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDRMAdd1.setText("+");
		
		Button btnDRMRemove1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMRemove1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDRMRemove1.setText("-");
		
		tableDRMlist1 = new Table(grpDataRowMarkedpointsPointList, SWT.BORDER | SWT.FULL_SELECTION);
		tableDRMlist1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tableDRMlist1.setLinesVisible(true);
		
		TableColumn tblclmnDRMXValue1 = new TableColumn(tableDRMlist1, SWT.NONE);
		tblclmnDRMXValue1.setWidth(100);
		tblclmnDRMXValue1.setText("X Value");
		
		TableColumn tblclmnDRMYValue1 = new TableColumn(tableDRMlist1, SWT.NONE);
		tblclmnDRMYValue1.setWidth(100);
		tblclmnDRMYValue1.setText("Y Value");
		
		TableItem tableItemDRMlist1point1 = new TableItem(tableDRMlist1, SWT.NONE);
		tableItemDRMlist1point1.setText(new String[] {"1.5", "2.5"});
		
		TableItem tableItemDRMlist1point2 = new TableItem(tableDRMlist1, SWT.NONE);
		tableItemDRMlist1point2.setText(new String[] {"3.5", "4.5"});
		
		Composite compositeDRMlistControls1 = new Composite(grpDataRowMarkedpointsPointList, SWT.NONE);
		compositeDRMlistControls1.setLayout(new GridLayout(6, false));
		compositeDRMlistControls1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		
		CLabel lblDRMlistControlsX1 = new CLabel(compositeDRMlistControls1, SWT.NONE);
		lblDRMlistControlsX1.setText("X:");
		
		txtDRMlistControlsX1 = new Text(compositeDRMlistControls1, SWT.BORDER);
		GridData gd_txtDRMlistControlsX1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtDRMlistControlsX1.widthHint = 50;
		txtDRMlistControlsX1.setLayoutData(gd_txtDRMlistControlsX1);
		
		CLabel lblDRMlistControlsY1 = new CLabel(compositeDRMlistControls1, SWT.NONE);
		lblDRMlistControlsY1.setText("Y:");
		
		txtDRMlistControlsY1 = new Text(compositeDRMlistControls1, SWT.BORDER);
		GridData gd_txtDRMlistControlsY1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtDRMlistControlsY1.widthHint = 50;
		txtDRMlistControlsY1.setLayoutData(gd_txtDRMlistControlsY1);
		
		Button btnDRMlistControlsAdd1 = new Button(compositeDRMlistControls1, SWT.FLAT);
		GridData gd_btnDRMlistControlsAdd1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDRMlistControlsAdd1.widthHint = 20;
		btnDRMlistControlsAdd1.setLayoutData(gd_btnDRMlistControlsAdd1);
		btnDRMlistControlsAdd1.setText("+");
		
		Button btnDRMlistControlsRemove1 = new Button(compositeDRMlistControls1, SWT.FLAT);
		GridData gd_btnDRMlistControlsRemove1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDRMlistControlsRemove1.widthHint = 20;
		btnDRMlistControlsRemove1.setLayoutData(gd_btnDRMlistControlsRemove1);
		btnDRMlistControlsRemove1.setText("-");
		new Label(grpDataRowMarkedpointsPointList, SWT.NONE);
		sashDataColumnRows.setWeights(new int[] {1});
		scrolledCompositeDataColumn.setContent(compositeDataColumn);
		scrolledCompositeDataColumn.setMinSize(compositeDataColumn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Group grpPlotoptionsColumn = new Group(sashTopLevelColumns, SWT.NONE);
		grpPlotoptionsColumn.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 15, SWT.BOLD));
		grpPlotoptionsColumn.setText("Plot Options");
		grpPlotoptionsColumn.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ScrolledComposite scrolledCompositePlotoptionsColumn = new ScrolledComposite(grpPlotoptionsColumn, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositePlotoptionsColumn.setExpandHorizontal(true);
		scrolledCompositePlotoptionsColumn.setExpandVertical(true);
		
		Composite compositePlotoptionsColumn = new Composite(scrolledCompositePlotoptionsColumn, SWT.NONE);
		compositePlotoptionsColumn.setLayout(new GridLayout(1, false));
		
		Group grpPlotoptionsGeneralRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsGeneralRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpPlotoptionsGeneralRow.setText("General");
		grpPlotoptionsGeneralRow.setLayout(new GridLayout(2, false));
		
		CLabel lblPoGeneralTitle = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralTitle.setText("Title:");
		
		txtPoGeneralTitle = new Text(grpPlotoptionsGeneralRow, SWT.BORDER);
		txtPoGeneralTitle.setToolTipText("Enter title of diagram");
		txtPoGeneralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		CLabel lblPoGeneralSize = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSize.setText("Size (Width):");
		
		Spinner spinnerPoGeneralSize = new Spinner(grpPlotoptionsGeneralRow, SWT.BORDER);
		spinnerPoGeneralSize.setIncrement(10);
		spinnerPoGeneralSize.setMaximum(99999);
		spinnerPoGeneralSize.setMinimum(0);
		spinnerPoGeneralSize.setSelection(217);
		
		CLabel lblPoGeneralGrid = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralGrid.setText("Grid:");
		
		Button btnPoGeneralShowGrid = new Button(grpPlotoptionsGeneralRow, SWT.CHECK);
		btnPoGeneralShowGrid.setText("Show Grid");
		
		Group grpPlotoptionsXAxisRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsXAxisRow.setLayout(new GridLayout(2, false));
		grpPlotoptionsXAxisRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpPlotoptionsXAxisRow.setText("X Axis");
		
		CLabel lblPoXaxisTitle = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisTitle.setText("Title:");
		
		txtPoXaxisTitle = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtPoXaxisTitle.setToolTipText("Set x axis title");
		
		CLabel lblPoXaxisRange = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisRange.setText("Range:");
		
		Composite compositePoXaxisRange = new Composite(grpPlotoptionsXAxisRow, SWT.BORDER);
		compositePoXaxisRange.setLayout(new GridLayout(3, false));
		
		Spinner spinnerPoXaxisRangeFrom = new Spinner(compositePoXaxisRange, SWT.BORDER);
		spinnerPoXaxisRangeFrom.setMaximum(99999);
		spinnerPoXaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoXaxisRangeTo = new CLabel(compositePoXaxisRange, SWT.NONE);
		lblPoXaxisRangeTo.setText("to");
		
		Spinner spinnerPoXaxisRangeTo = new Spinner(compositePoXaxisRange, SWT.BORDER);
		spinnerPoXaxisRangeTo.setMaximum(99999);
		spinnerPoXaxisRangeTo.setMinimum(-99999);
		
		CLabel lblPoXaxisDivisioning = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisDivisioning.setText("Divisioning:");
		
		Button btnPoXaxisPiDivisioning = new Button(grpPlotoptionsXAxisRow, SWT.CHECK);
		btnPoXaxisPiDivisioning.setText("Pi Divisioning");
		
		CLabel lblPoXaxisHelplines = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisHelplines.setText("Helplines:");
		
		txtPoXaxisHelplines = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisHelplines.setToolTipText("Enter y axis intersection points to define x axis helplines");
		txtPoXaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group grpPlotoptionsYAxisRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsYAxisRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpPlotoptionsYAxisRow.setText("Y Axis");
		grpPlotoptionsYAxisRow.setLayout(new GridLayout(2, false));
		
		CLabel lblPoYaxisTitle = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisTitle.setText("Title:");
		
		txtPoYaxisTitle = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisTitle.setToolTipText("Enter y axis title");
		txtPoYaxisTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		CLabel lblPoYaxisRange = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisRange.setText("Range:");
		
		Composite compositePoYaxisRange = new Composite(grpPlotoptionsYAxisRow, SWT.BORDER);
		compositePoYaxisRange.setLayout(new GridLayout(3, false));
		
		Spinner spinnerPoYaxisRangeFrom = new Spinner(compositePoYaxisRange, SWT.BORDER);
		spinnerPoYaxisRangeFrom.setMaximum(99999);
		spinnerPoYaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoYaxisTo = new CLabel(compositePoYaxisRange, SWT.NONE);
		lblPoYaxisTo.setText("to");
		
		Spinner spinnerPoYaxisTo = new Spinner(compositePoYaxisRange, SWT.BORDER);
		spinnerPoYaxisTo.setMaximum(99999);
		spinnerPoYaxisTo.setMinimum(-99999);
		
		CLabel lblPoYaxisHelplines = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisHelplines.setText("Helplines:");
		
		txtPoYaxisHelplines = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisHelplines.setToolTipText("Enter x axis intersection points to define y axis helplines");
		txtPoYaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group grpPlotoptionsIntegralAreaRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsIntegralAreaRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpPlotoptionsIntegralAreaRow.setText("Integral Area");
		grpPlotoptionsIntegralAreaRow.setLayout(new GridLayout(2, false));
		
		CLabel lblPoIntegralTitle = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralTitle.setText("Title:");
		
		txtPoIntegralTitle = new Text(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		txtPoIntegralTitle.setToolTipText("Enter integral title");
		txtPoIntegralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		CLabel lblPoIntegralRange = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralRange.setText("Range:");
		
		Composite compositePoIntegralRange = new Composite(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		compositePoIntegralRange.setLayout(new GridLayout(3, false));
		
		Spinner spinnerPoIntegralRangeFrom = new Spinner(compositePoIntegralRange, SWT.BORDER);
		spinnerPoIntegralRangeFrom.setMaximum(99999);
		spinnerPoIntegralRangeFrom.setMinimum(-99999);
		
		CLabel lblPoIntegralRangeTo = new CLabel(compositePoIntegralRange, SWT.NONE);
		lblPoIntegralRangeTo.setText("to");
		
		Spinner spinnerPoIntegralRangeTo = new Spinner(compositePoIntegralRange, SWT.BORDER);
		spinnerPoIntegralRangeTo.setMaximum(99999);
		spinnerPoIntegralRangeTo.setMinimum(-99999);
		
		CLabel lblPoIntegralBordering = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralBordering.setText("Bordering:");
		
		Composite compositePoIntegralBordering = new Composite(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		compositePoIntegralBordering.setLayout(new GridLayout(3, false));
		
		Combo comboPoIntegralBorderingFrom = new Combo(compositePoIntegralBordering, SWT.READ_ONLY);
		comboPoIntegralBorderingFrom.setItems(new String[] {"A", "B", "C", "D"});
		comboPoIntegralBorderingFrom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		CLabel lblPoIntegralAnd = new CLabel(compositePoIntegralBordering, SWT.NONE);
		lblPoIntegralAnd.setText("&&");
		
		Combo comboPoIntegralBorderingTo = new Combo(compositePoIntegralBordering, SWT.NONE);
		comboPoIntegralBorderingTo.setItems(new String[] {"x axis", "A", "B", "C", "D"});
		comboPoIntegralBorderingTo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboPoIntegralBorderingTo.select(0);
		scrolledCompositePlotoptionsColumn.setContent(compositePlotoptionsColumn);
		scrolledCompositePlotoptionsColumn.setMinSize(compositePlotoptionsColumn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Group grpPreviewoutputColumn = new Group(sashTopLevelColumns, SWT.NONE);
		grpPreviewoutputColumn.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpPreviewoutputColumn.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		grpPreviewoutputColumn.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 15, SWT.BOLD));
		grpPreviewoutputColumn.setText("Preview &&& Output");
		grpPreviewoutputColumn.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ScrolledComposite scrolledCompositePreviewoutputColumn = new ScrolledComposite(grpPreviewoutputColumn, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompositePreviewoutputColumn.setExpandHorizontal(true);
		scrolledCompositePreviewoutputColumn.setExpandVertical(true);
		
		Composite compositePreviewOutputColumn = new Composite(scrolledCompositePreviewoutputColumn, SWT.NONE);
		compositePreviewOutputColumn.setLayout(new GridLayout(1, false));
		
		TabFolder tabFolderPreview = new TabFolder(compositePreviewOutputColumn, SWT.NONE);
		GridData gd_tabFolderPreview = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
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
		compositeOutput.setLayout(new GridLayout(2, false));
		
		Group grpOutputStyle = new Group(compositeOutput, SWT.NONE);
		grpOutputStyle.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		grpOutputStyle.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		grpOutputStyle.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpOutputStyle.setText("Style");
		grpOutputStyle.setLayout(new GridLayout(1, false));
		
		Button btnOutputStyleLoad = new Button(grpOutputStyle, SWT.NONE);
		btnOutputStyleLoad.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnOutputStyleLoad.setText("Load...");
		
		Button btnOutputStyleModify = new Button(grpOutputStyle, SWT.NONE);
		btnOutputStyleModify.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnOutputStyleModify.setText("Modify...");
		
		Button btnOutputStyleStore = new Button(grpOutputStyle, SWT.NONE);
		btnOutputStyleStore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnOutputStyleStore.setText("Store...");
		
		Group grpExport = new Group(compositeOutput, SWT.NONE);
		grpExport.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		grpExport.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpExport.setLayout(new GridLayout(1, false));
		grpExport.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		grpExport.setText("Export");
		
		Button btnOutputExportExport = new Button(grpExport, SWT.NONE);
		btnOutputExportExport.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnOutputExportExport.setText("Export...");
		
		scrolledCompositePreviewoutputColumn.setContent(compositePreviewOutputColumn);
		scrolledCompositePreviewoutputColumn.setMinSize(compositePreviewOutputColumn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sashTopLevelColumns.setWeights(new int[] {2, 2, 3});
		
		Menu menu = new Menu(shlGsvgplott, SWT.BAR);
		shlGsvgplott.setMenuBar(menu);
		
		MenuItem mntmExtrasSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmExtrasSubmenu.setText("Extras");
		
		Menu extras_1 = new Menu(mntmExtrasSubmenu);
		mntmExtrasSubmenu.setMenu(extras_1);
		
		MenuItem mntmExtrasPreferences = new MenuItem(extras_1, SWT.NONE);
		mntmExtrasPreferences.setText("Preferences...");
		
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
