package de.tudresden.inf.gsvgplott.ui;

/**
 * 
 * @author David Gollasch
 *
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;

import de.tudresden.inf.gsvgplott.data.Diagram;
import de.tudresden.inf.gsvgplott.data.Function;
import de.tudresden.inf.gsvgplott.data.Helpline;
import de.tudresden.inf.gsvgplott.data.Integral;
import de.tudresden.inf.gsvgplott.data.MarkedPointsList;
import de.tudresden.inf.gsvgplott.data.XAxis;
import de.tudresden.inf.gsvgplott.data.YAxis;
import de.tudresden.inf.gsvgplott.data.style.AreaStyle;
import de.tudresden.inf.gsvgplott.data.style.OverrideStyle;
import de.tudresden.inf.gsvgplott.data.style.TextStyle;
import de.tudresden.inf.gsvgplott.data.style.palettes.ColorPalette;

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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.w3c.dom.DOMException;

import tud.tangram.svgplot.SvgPlot;
import tud.tangram.svgplot.coordinatesystem.Range;
import tud.tangram.svgplot.xml.HtmlDocument;
import tud.tangram.svgplot.xml.SvgDocument;

public class MainWindow {
	
	public static final String APP_NAME = "gSVGPlott";

	protected Shell shlGsvgplott;
	
	/**
	 * The diagram object holds all data to be processed
	 */
	private Diagram diagram;
	
	/**
	 * Output object: SVG Graph Document
	 */
	private SvgDocument exportDoc, previewScreenDoc, previewPrintDoc;
	/**
	 * Output object: Description
	 */
	private HtmlDocument exportDesc, previewScreenDesc, previewPrintDesc;
	/**
	 * Output object: Legend
	 */
	private SvgDocument exportLegend, previewScreenLegend, previewPrintLegend;
	
	/**
	 * List of all functions as Group widgets
	 */
	private List<Group> functions;
	private int functionIDcounter;
	private Map<Group, Function> functionMap;
	
	/**
	 * List of all point lists as Group widgets
	 */
	private List<Group> pointlists;
	private int pointlistIDcounter;
	private Map<Group, MarkedPointsList> pointlistMap;
	
	/**
	 * This shell will only be used to prepare widgets to show them later on.
	 */
	private Shell temporaryContainer;
	
	private Composite compositeDataColumn;
	private ScrolledComposite scrolledCompositeDataColumn;
	private SashForm sashTopLevelColumns;
	private Text txtPoGeneralTitle;
	private Spinner spinnerPoGeneralWidth;
	private Button btnPoGeneralShowGrid;
	private Text txtPoXaxisTitle;
	private Spinner spinnerPoXaxisRangeFrom;
	private Spinner spinnerPoXaxisRangeTo;
	private Button btnPoXaxisPiDivisioning;
	private Text txtPoXaxisHelplines;
	private Text txtPoYaxisTitle;
	private Spinner spinnerPoYaxisRangeFrom;
	private Spinner spinnerPoYaxisRangeTo;
	private Text txtPoYaxisHelplines;
	private Text txtPoIntegralTitle;
	private Spinner spinnerPoIntegralRangeFrom;
	private Spinner spinnerPoIntegralRangeTo;
	private Combo comboPoIntegralBorderingFrom;
	private Combo comboPoIntegralBorderingTo;
	private Spinner spinnerPoGeneralHeight;

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
		// initialize object
		diagram = new Diagram(null, 0, 0, false, null, null);
		
		// initialize variable data objects
		functions = new ArrayList<Group>();
		functionMap = new HashMap<Group, Function>();
		functionIDcounter = 0;
		pointlists = new ArrayList<Group>();
		pointlistMap = new HashMap<Group, MarkedPointsList>();
		pointlistIDcounter = 0;
		
		// initialize container for holding invisible widgets
		temporaryContainer = new Shell();
		temporaryContainer.setLayout(new GridLayout(1, false));
		
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		
		createContents();
		
		// initialize data object and prepare view
		this.operateResetDiagram();
		
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
		grpPlotoptionsGeneralRow.setLayout(new GridLayout(6, false));
		
		CLabel lblPoGeneralTitle = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralTitle.setText("Title:");
		
		txtPoGeneralTitle = new Text(grpPlotoptionsGeneralRow, SWT.BORDER);
		txtPoGeneralTitle.setToolTipText("Enter title of diagram");
		txtPoGeneralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		Button btnPlotoptionsGeneralStyle = new Button(grpPlotoptionsGeneralRow, SWT.FLAT);
		btnPlotoptionsGeneralStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsGeneralStyleToolbox();
			}
		});
		btnPlotoptionsGeneralStyle.setToolTipText("Change style");
		btnPlotoptionsGeneralStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblPoGeneralSize = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSize.setText("Size:");
		
		spinnerPoGeneralWidth = new Spinner(grpPlotoptionsGeneralRow, SWT.BORDER);
		spinnerPoGeneralWidth.setToolTipText("Width in Millimeters");
		spinnerPoGeneralWidth.setIncrement(10);
		spinnerPoGeneralWidth.setMaximum(99999);
		spinnerPoGeneralWidth.setMinimum(0);
		spinnerPoGeneralWidth.setSelection(210);
		
		CLabel lblPoGeneralSizeMm = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSizeMm.setText("mm   x");
		
		spinnerPoGeneralHeight = new Spinner(grpPlotoptionsGeneralRow, SWT.BORDER);
		spinnerPoGeneralHeight.setToolTipText("Height in Millimeters");
		spinnerPoGeneralHeight.setMaximum(99999);
		spinnerPoGeneralHeight.setMinimum(0);
		spinnerPoGeneralHeight.setSelection(297);
		spinnerPoGeneralHeight.setIncrement(10);
		
		CLabel lblPoGeneralSizeMm2 = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSizeMm2.setText("mm");
		new Label(grpPlotoptionsGeneralRow, SWT.NONE);
		
		CLabel lblPoGeneralGrid = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralGrid.setText("Grid:");
		
		btnPoGeneralShowGrid = new Button(grpPlotoptionsGeneralRow, SWT.CHECK);
		btnPoGeneralShowGrid.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
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
		btnPlotoptionsXAxisStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsXaxisStyleToolbox();
			}
		});
		btnPlotoptionsXAxisStyle.setToolTipText("Change style");
		btnPlotoptionsXAxisStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblPoXaxisRange = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisRange.setText("Range:");
		
		spinnerPoXaxisRangeFrom = new Spinner(grpPlotoptionsXAxisRow, SWT.BORDER);
		spinnerPoXaxisRangeFrom.setMaximum(99999);
		spinnerPoXaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoXaxisRangeTo = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisRangeTo.setText("to");
		
		spinnerPoXaxisRangeTo = new Spinner(grpPlotoptionsXAxisRow, SWT.BORDER);
		spinnerPoXaxisRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoXaxisRangeTo.setMaximum(99999);
		spinnerPoXaxisRangeTo.setMinimum(-99999);
		
		CLabel lblPoXaxisDivisioning = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisDivisioning.setText("Divisioning:");
		
		btnPoXaxisPiDivisioning = new Button(grpPlotoptionsXAxisRow, SWT.CHECK);
		btnPoXaxisPiDivisioning.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		btnPoXaxisPiDivisioning.setText("Pi Divisioning");
		
		CLabel lblPoXaxisHelplines = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisHelplines.setText("Helplines:");
		
		txtPoXaxisHelplines = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisHelplines.setToolTipText("Enter y axis intersection points to define x axis helplines");
		txtPoXaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsXAxisHelplineStyle = new Button(grpPlotoptionsXAxisRow, SWT.FLAT);
		btnPlotoptionsXAxisHelplineStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		
		txtPoYaxisTitle = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisTitle.setToolTipText("Enter y axis title");
		txtPoYaxisTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsYAxisStyle = new Button(grpPlotoptionsYAxisRow, SWT.FLAT);
		btnPlotoptionsYAxisStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsYaxisStyleToolbox();
			}
		});
		btnPlotoptionsYAxisStyle.setToolTipText("Change style");
		btnPlotoptionsYAxisStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblPoYaxisRange = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisRange.setText("Range:");
		
		spinnerPoYaxisRangeFrom = new Spinner(grpPlotoptionsYAxisRow, SWT.BORDER);
		spinnerPoYaxisRangeFrom.setMaximum(99999);
		spinnerPoYaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoYaxisTo = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisTo.setText("to");
		
		spinnerPoYaxisRangeTo = new Spinner(grpPlotoptionsYAxisRow, SWT.BORDER);
		spinnerPoYaxisRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoYaxisRangeTo.setMaximum(99999);
		spinnerPoYaxisRangeTo.setMinimum(-99999);
		
		CLabel lblPoYaxisHelplines = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisHelplines.setText("Helplines:");
		
		txtPoYaxisHelplines = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisHelplines.setToolTipText("Enter x axis intersection points to define y axis helplines");
		txtPoYaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsYAxisHelplineStyle = new Button(grpPlotoptionsYAxisRow, SWT.FLAT);
		btnPlotoptionsYAxisHelplineStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		
		txtPoIntegralTitle = new Text(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		txtPoIntegralTitle.setToolTipText("Enter integral title");
		txtPoIntegralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsIntegralStyle = new Button(grpPlotoptionsIntegralAreaRow, SWT.FLAT);
		btnPlotoptionsIntegralStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsIntegralStyleToolbox();
			}
		});
		btnPlotoptionsIntegralStyle.setToolTipText("Change style");
		btnPlotoptionsIntegralStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblPoIntegralRange = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralRange.setText("Range:");
		
		spinnerPoIntegralRangeFrom = new Spinner(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		spinnerPoIntegralRangeFrom.setIncrement(1000);
		spinnerPoIntegralRangeFrom.setDigits(3);
		spinnerPoIntegralRangeFrom.setMaximum(99999);
		spinnerPoIntegralRangeFrom.setMinimum(-99999);
		
		CLabel lblPoIntegralRangeTo = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralRangeTo.setText("to");
		
		spinnerPoIntegralRangeTo = new Spinner(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		spinnerPoIntegralRangeTo.setIncrement(1000);
		spinnerPoIntegralRangeTo.setDigits(3);
		spinnerPoIntegralRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoIntegralRangeTo.setMaximum(99999);
		spinnerPoIntegralRangeTo.setMinimum(-99999);
		
		CLabel lblPoIntegralBordering = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralBordering.setText("Bordering:");
		
		comboPoIntegralBorderingFrom = new Combo(grpPlotoptionsIntegralAreaRow, SWT.READ_ONLY);
		comboPoIntegralBorderingFrom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		comboPoIntegralBorderingFrom.setItems(new String[] {});
		
		CLabel lblPoIntegralAnd = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralAnd.setText("&&");
		
		comboPoIntegralBorderingTo = new Combo(grpPlotoptionsIntegralAreaRow, SWT.READ_ONLY);
		comboPoIntegralBorderingTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		comboPoIntegralBorderingTo.setItems(new String[] {"x axis"});
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
		btnOutputExportExport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		// create new Function object
		Function f = new Function("", "");
		diagram.getFunctions().add(f);
		
		// create new according widgets
		this.operateAddFunction(f);
	}
	
	/**
	 * Add a new point list
	 */
	protected void triggerDataAddPointList() {
		// create new MarkedPointsList object
		MarkedPointsList p = new MarkedPointsList("");
		diagram.getMarkedPointLists().add(p);
		
		// create new according widgets
		this.operateAddPointList(p);
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
	 * Open tool box for point styling
	 * @param pointlist
	 */
	protected void triggerDataPointListStyleToolbox(Group pointlist) {
		PointStyleToolbox ps = new PointStyleToolbox(shlGsvgplott, 0);
		ps.setOpeningLocation(Display.getDefault().getCursorLocation());
		ps.open();
	}
	
	/**
	 * Open tool box for line styling
	 * @param function
	 */
	protected void triggerDataFunctionStyleToolbox(Group function) {
		LineStyleToolbox ls = new LineStyleToolbox(shlGsvgplott, 0);
		ls.setOpeningLocation(Display.getDefault().getCursorLocation());
		ls.open();
		
	}
	
	/**
	 * Adds a point to the intended point list table
	 * @param table
	 */
	protected void triggerDataPointListAddPoint(Table table) {
		operateDataPointListAddPoint(table);
	}
	
	/**
	 * Removes a point from the intended point list table
	 * @param table
	 */
	protected void triggerDataPointListRemovePoint(Table table) {
		operateDataPointListRemovePoint(table);
	}
	
	/**
	 * Open tool box for General styling
	 */
	protected void triggerOptionsGeneralStyleToolbox() {
		//TODO: bind to actual data
		TextStyle screenTS = new TextStyle("Courier", 15, ColorPalette.APPLE, true, false, false);
		TextStyle printTS = new TextStyle("Courier", 15, ColorPalette.BLUEBERRY, true, false, false);
		AreaStyle screenAS = new AreaStyle(ColorPalette.CITRUS);
		AreaStyle printAS = new AreaStyle(ColorPalette.ALUMINIUM);
		
		GeneralStyleToolbox gt = new GeneralStyleToolbox(shlGsvgplott, 0, screenTS, printTS, screenAS, printAS);
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
		//TODO: bind to actual data
		AreaStyleToolbox at = new AreaStyleToolbox(shlGsvgplott, 0, new AreaStyle(ColorPalette.APPLE), new AreaStyle(ColorPalette.BLUEBERRY));
		at.setOpeningLocation(Display.getDefault().getCursorLocation());
		at.open();
	}

	/**
	 * Export project
	 */
	protected void triggerExport() {
		/*FileDialog fd = new FileDialog(shlGsvgplott, SWT.SAVE);
        fd.setText("Export");
        //fd.setFilterPath("C:/");
        String[] filterExt = { "*.svg", "*.xml", "*.*" };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        
		//TODO: export file
        System.out.println(selected);*/
		
		this.operateFireSVGPlott(previewScreenDoc, previewScreenDesc, previewScreenLegend, RenderMode.UNISCREEN);
		
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
		//TODO: bind to actual data
		CssStyleOverrideDialog cd = new CssStyleOverrideDialog(shlGsvgplott, 0, new OverrideStyle(""));
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
	 * Re-Constructs the data column according to the given function data
	 */
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
		btnDataColumnFunctionsAddFunction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		btnDataColumnListsAddPointList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
	protected void operateAddFunction(Function function) {
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
		btnDRFStyle1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataFunctionStyleToolbox(grpDataRowFunction1);
			}
		});
		
		CLabel lblDRFfunc1 = new CLabel(grpDataRowFunction1, SWT.NONE);
		lblDRFfunc1.setSize(39, 19);
		lblDRFfunc1.setText("f(x) = ");
		
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
		btnDRFMoveUp1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataMoveUpFunction(grpDataRowFunction1);
			}
		});
		
		Button btnDRFMoveDown1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFMoveDown1.setToolTipText("Move item down");
		btnDRFMoveDown1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnDRFMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-16.png"));
		btnDRFMoveDown1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		btnDRFRemove1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataRemoveFunction(grpDataRowFunction1);
			}
		});
		
		// register group with function
		functions.add(grpDataRowFunction1);
		functionMap.put(grpDataRowFunction1, function);
		
		// fill data
		txtDRFTitle1.setText(function.getTitle());
		txtDRFfunc1.setText(function.getFunction());
		
		// update integral border selection
		this.operateUpdateIntegralBordering();
		
		
		this.operateRecreateDataColumn();
	}

	/**
	 * Adds a new point list and triggers redraw
	 */
	protected void operateAddPointList(MarkedPointsList pointlist) {
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
		btnDRMStyle1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataPointListStyleToolbox(grpDataRowMarkedpointsPointList);
			}
		});
		
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
		
//		TableItem tableItemDRMlist1point2 = new TableItem(tableDRMlist1, SWT.NONE);
//		tableItemDRMlist1point2.setText(new String[] {"3.5", "4.5"});
//		
//		TableItem tableItemDRMlist1point1 = new TableItem(tableDRMlist1, SWT.NONE);
//		tableItemDRMlist1point1.setText(new String[] {"1.5", "2.5"});
		
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
		btnDRMlistAdd1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataPointListAddPoint(tableDRMlist1);
			}
		});
		
		Button btnDRMlistRemove1 = new Button(compositeDRMlistControls1, SWT.NONE);
		btnDRMlistRemove1.setText("Remove");
		btnDRMlistRemove1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataPointListRemovePoint(tableDRMlist1);
			}
		});
		
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
		btnDRMMoveUp1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataMoveUpPointList(grpDataRowMarkedpointsPointList);
			}
		});
		
		Button btnDRMMoveDown1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMMoveDown1.setToolTipText("Move item down");
		btnDRMMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-16.png"));
		btnDRMMoveDown1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
		btnDRMRemove1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataRemovePointList(grpDataRowMarkedpointsPointList);
			}
		});
		
		// register group and points list
		pointlists.add(grpDataRowMarkedpointsPointList);
		pointlistMap.put(grpDataRowMarkedpointsPointList, pointlist);
		
		// fill data
		txtDRMtitle1.setText(pointlist.getTitle());
		List<de.tudresden.inf.gsvgplott.data.Point> points = pointlist.getPoints();
		for(de.tudresden.inf.gsvgplott.data.Point p : points) {
			double x = p.getX();
			double y = p.getY();
			
			String[] tableEntryText = {Double.toString(x), Double.toString(y)};
			
			TableItem ti = new TableItem(tableDRMlist1, 0);
			ti.setText(tableEntryText);
		}
		
		this.operateRecreateDataColumn();
	}
	
	/**
	 * Removes a desired function if existing
	 * @param function
	 */
	protected void operateRemoveFunction(Group function) {
		if(functions.contains(function)) {
			functions.remove(function);
			
			// remove function in diagram as well
			Function f = functionMap.get(function);
			if(f != null) {
				functionMap.remove(function);
				diagram.getFunctions().remove(f);
			}
			
			this.operateUpdateIntegralBordering();
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
			
			// remove data as well
			MarkedPointsList p = pointlistMap.get(pointlist);
			if(p != null) {
				pointlistMap.remove(pointlist);
				diagram.getMarkedPointLists().remove(p);
			}
			
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
	 * Adds a point to the intended point list table
	 * @param table
	 */
	protected void operateDataPointListAddPoint(Table table) {
		//TODO: implement adding a point, bind to actual data
		AddPointDialog apd = new AddPointDialog(shlGsvgplott, 0, new de.tudresden.inf.gsvgplott.data.Point(0.0, 0.0));
		apd.open();
	}
	
	/**
	 * Removes a point from the intended point list table
	 * @param table
	 */
	protected void operateDataPointListRemovePoint(Table table) {
		int[] selection = table.getSelectionIndices();
		table.remove(selection);
	}
	
	/**
	 * This method invokes the creation of preview diagrams out of the current Diagram instance
	 */
	protected void operateGeneratePreview() {
		//TODO: to be implemented
	}
	
	/**
	 * Resets all data by crating a new diagram object and resetting all views
	 */
	protected void operateResetDiagram() {
		// initialize object
		XAxis xaxis = new XAxis("", -8, 8, false);
		YAxis yaxis = new YAxis("", -8, 8);
		diagram = new Diagram("", 210, 297, false, xaxis, yaxis);
		
		// reset view
		operateUpdateByDiagramObject(diagram);
	}
	
	protected void operateUpdateByDiagramObject(Diagram dia) {
		// set dia as data object
		diagram = dia;
		
		//
		// TASK) reset functions and point lists
		//
		
		// re-initialize variable data objects
		functions = new ArrayList<Group>();
		functionMap = new HashMap<Group, Function>();
		functionIDcounter = 0;
		pointlists = new ArrayList<Group>();
		pointlistMap = new HashMap<Group, MarkedPointsList>();
		pointlistIDcounter = 0;

		// re-initialize container for holding invisible widgets
		temporaryContainer = new Shell();
		temporaryContainer.setLayout(new GridLayout(1, false));
		
		// create data column
		this.operateRecreateDataColumn();
		
		//
		// TASK) set functions
		//
		for(Function f : diagram.getFunctions()) {
			this.operateAddFunction(f);
		}
		
		//
		// TASK) set point lists
		//
		for(MarkedPointsList p : diagram.getMarkedPointLists()) {
			this.operateAddPointList(p);
		}
		
		//
		// TASK) set options pane
		//
		
		// GENERAL
		this.txtPoGeneralTitle.setText(diagram.getTitle());
		this.spinnerPoGeneralWidth.setSelection(diagram.getSizeWidth());
		this.spinnerPoGeneralHeight.setSelection(diagram.getSizeHeight());
		this.btnPoGeneralShowGrid.setSelection(diagram.isShowGrid());
		
		// XAXIS
		this.txtPoXaxisTitle.setText(diagram.getXaxis().getTitle());
		this.spinnerPoXaxisRangeFrom.setSelection(diagram.getXaxis().getRangeFrom());
		this.spinnerPoXaxisRangeTo.setSelection(diagram.getXaxis().getRangeTo());
		this.btnPoXaxisPiDivisioning.setSelection(diagram.getXaxis().isPiDivisioning());
		List<Helpline> xhelps = diagram.getXaxis().getHelplines();
		if(xhelps != null) {
			String helplines = "";
			for(Helpline h : xhelps) {
				helplines += " " + Double.toString(h.getIntersection()) + ";";
			}
			this.txtPoXaxisHelplines.setText(helplines);
		}
		
		// YAXIS
		this.txtPoYaxisTitle.setText(diagram.getYaxis().getTitle());
		this.spinnerPoYaxisRangeFrom.setSelection(diagram.getYaxis().getRangeFrom());
		this.spinnerPoYaxisRangeTo.setSelection(diagram.getYaxis().getRangeTo());
		List<Helpline> yhelps = diagram.getYaxis().getHelplines();
		if(yhelps != null) {
			String helplines = "";
			for(Helpline h : yhelps) {
				helplines += " " + Double.toString(h.getIntersection()) + ";";
			}
			this.txtPoYaxisHelplines.setText(helplines);
		}
		
		// INTEGRAL
		if(diagram.getIntegral() != null) {
			this.txtPoIntegralTitle.setText(diagram.getIntegral().getTitle());
			
			this.spinnerPoIntegralRangeFrom.setSelection((int)Math.round(diagram.getIntegral().getRangeFrom() * 1000.0));
			this.spinnerPoIntegralRangeTo.setSelection((int)Math.round(diagram.getIntegral().getRangeTo() * 1000.0));
			
			// in case the diagram object consists of messy numbers, tidy 'em up
			diagram.getIntegral().setRangeFrom((double)this.spinnerPoIntegralRangeFrom.getSelection() / 1000.0);
			diagram.getIntegral().setRangeTo((double)this.spinnerPoIntegralRangeTo.getSelection() / 1000.0);
			
			Function b1 = diagram.getIntegral().getBorder1();
			Function b2 = diagram.getIntegral().getBorder2();
			String b1selection = "";
			String b2selection = "";
			if(b1 != null) {
				for(Group g : functionMap.keySet()) {
					if(functionMap.get(g) == b1) {
						b1selection = g.getText();
						break;
					}
				}
			}
			if(b2 != null) {
				for(Group g : functionMap.keySet()) {
					if(functionMap.get(g) == b2) {
						b2selection = g.getText();
						break;
					}
				}
			} else {
				b2selection = "X axis";
			}
			
			// try to select in combo boxes
			try {
				int i = this.comboPoIntegralBorderingFrom.indexOf(b1selection);
				this.comboPoIntegralBorderingFrom.select(i);
			} catch (Exception e) {
				// do nothing if this doesn't work
			}
			
			try {
				int i = this.comboPoIntegralBorderingTo.indexOf(b2selection);
				this.comboPoIntegralBorderingTo.select(i);
			} catch (Exception e) {
				// do nothing if this doesn't work
			}
		}
	}
	
	/**
	 * Recreate lists of functions for bordering selection of integrals
	 */
	protected void operateUpdateIntegralBordering() {
		// temporary store current selection
		String fromCurrent = this.comboPoIntegralBorderingFrom.getText();
		String toCurrent = this.comboPoIntegralBorderingTo.getText();
		
		// regenerate items
		this.comboPoIntegralBorderingFrom.removeAll();
		this.comboPoIntegralBorderingTo.removeAll();
		
		this.comboPoIntegralBorderingTo.add("X axis");
		for(Group grp : functions) {
			this.comboPoIntegralBorderingFrom.add(grp.getText());
			this.comboPoIntegralBorderingTo.add(grp.getText());
		}
		
		// try to restore former selection
		try {
			int i = this.comboPoIntegralBorderingFrom.indexOf(fromCurrent);
			this.comboPoIntegralBorderingFrom.select(i);
		} catch (Exception e) {
			// do nothing if this doesn't work
		}
		
		try {
			int i = this.comboPoIntegralBorderingTo.indexOf(toCurrent);
			this.comboPoIntegralBorderingTo.select(i);
		} catch (Exception e) {
			// do nothing if this doesn't work
		}
		
	}
	
	/**
	 * Fire up the SVG Plotting
	 * @param doc Graph document to write to
	 * @param desc Description document to write to
	 * @param legend Legend document to write to
	 * @param mode Select style rendering mode for output
	 */
	protected void operateFireSVGPlott(SvgDocument doc, HtmlDocument desc, SvgDocument legend, RenderMode mode) {
		SvgPlot plotter = new SvgPlot(); 
		
		// Set all variables
		
		//TODO: generate CSS for SVGPlott
		String css = "";
		switch (mode) {
		case DEFAULT:
			break;
		case UNIPRINT:
			break;
		case UNISCREEN:
			break;
		default:
			break;
		}
		plotter.setCss(css);
		
		List<tud.tangram.svgplot.plotting.Function> functions = 
				new ArrayList<tud.tangram.svgplot.plotting.Function>();
		for(Function f : diagram.getFunctions()) {
			tud.tangram.svgplot.plotting.Function func = 
					new tud.tangram.svgplot.plotting.Function(f.getTitle(), f.getFunction());
			functions.add(func);
		}
		plotter.setFunctions(functions);
		//plotter.setIntegral(null);
		plotter.setPi(diagram.getXaxis().isPiDivisioning());
		//plotter.setPoints(null);
			
		plotter.setSize(
				new tud.tangram.svgplot.coordinatesystem.Point(
						(double)diagram.getSizeWidth(), 
						(double)diagram.getSizeHeight()));
		plotter.setTitle(diagram.getTitle());
		//plotter.setxLines(null);
		Range xrange = new Range(
				(double)diagram.getXaxis().getRangeFrom(), 
				(double)diagram.getXaxis().getRangeTo(), 
				diagram.getXaxis().getTitle());
		plotter.setxRange(xrange);
		//plotter.setyLines(null);
		Range yrange = new Range(
				(double)diagram.getYaxis().getRangeFrom(),
				(double)diagram.getYaxis().getRangeTo(),
				diagram.getYaxis().getTitle());
		plotter.setyRange(yrange);
		
		try {
			plotter.create();
		} catch (DOMException | ParserConfigurationException | IOException
				| InterruptedException e) {
			return; // iff there was an error
		}
		
		// return all artifacts
		doc = plotter.getDoc();
		desc = plotter.getDesc();
		legend = plotter.getLegend();
	}
}
