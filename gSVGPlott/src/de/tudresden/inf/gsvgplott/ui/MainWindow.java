package de.tudresden.inf.gsvgplott.ui;

/**
 * 
 * @author David Gollasch
 *
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

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
import de.tudresden.inf.gsvgplott.data.style.DefaultStyles;
import de.tudresden.inf.gsvgplott.data.style.LineStyle;
import de.tudresden.inf.gsvgplott.data.style.OverrideStyle;
import de.tudresden.inf.gsvgplott.data.style.PointStyle;
import de.tudresden.inf.gsvgplott.data.style.TextStyle;
import de.tudresden.inf.gsvgplott.data.style.palettes.ColorPalette;
import de.tudresden.inf.gsvgplott.ui.util.RenderMode;

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
import org.eclipse.swt.widgets.MessageBox;
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
import org.eclipse.swt.events.SelectionListener;
import org.w3c.dom.DOMException;

import tud.tangram.svgplot.SvgPlot;
import tud.tangram.svgplot.coordinatesystem.PointListList;
import tud.tangram.svgplot.coordinatesystem.PointListList.PointList;
import tud.tangram.svgplot.coordinatesystem.Range;
import tud.tangram.svgplot.plotting.IntegralPlotSettings;
import tud.tangram.svgplot.xml.HtmlDocument;
import tud.tangram.svgplot.xml.SvgDocument;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

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
	private Browser browserPreviewScreenViewGraph;
	private Browser browserPreviewScreenViewLegend;
	private Browser browserPreviewScreenViewDescription;
	private Browser browserPreviewPrintViewGraph;
	private Browser browserPreviewPrintViewLegend;
	private Browser browserPreviewPrintViewDescription;

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
		txtPoGeneralTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsGeneralTitleChanged();
			}
		});
		
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
		spinnerPoGeneralWidth.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsGeneralSizeWidthChanged();
			}
		});
		spinnerPoGeneralWidth.setToolTipText("Width in Millimeters");
		spinnerPoGeneralWidth.setIncrement(10);
		spinnerPoGeneralWidth.setMaximum(99999);
		spinnerPoGeneralWidth.setMinimum(0);
		spinnerPoGeneralWidth.setSelection(210);
		
		CLabel lblPoGeneralSizeMm = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSizeMm.setText("mm   x");
		
		spinnerPoGeneralHeight = new Spinner(grpPlotoptionsGeneralRow, SWT.BORDER);
		spinnerPoGeneralHeight.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsGeneralSizeHeightChanged();
			}
		});
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
		btnPoGeneralShowGrid.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsGeneralShowGridChanged();
			}
		});
		btnPoGeneralShowGrid.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		btnPoGeneralShowGrid.setText("Show Grid");
		
		Group grpPlotoptionsXAxisRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsXAxisRow.setLayout(new GridLayout(5, false));
		grpPlotoptionsXAxisRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpPlotoptionsXAxisRow.setText("X Axis");
		
		CLabel lblPoXaxisTitle = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisTitle.setText("Title:");
		
		txtPoXaxisTitle = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsXaxisTitleChanged();
			}
		});
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
		spinnerPoXaxisRangeFrom.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsXaxisRangeFromChanged();
			}
		});
		spinnerPoXaxisRangeFrom.setMaximum(99999);
		spinnerPoXaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoXaxisRangeTo = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisRangeTo.setText("to");
		
		spinnerPoXaxisRangeTo = new Spinner(grpPlotoptionsXAxisRow, SWT.BORDER);
		spinnerPoXaxisRangeTo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsXaxisRangeToChanged();
			}
		});
		spinnerPoXaxisRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoXaxisRangeTo.setMaximum(99999);
		spinnerPoXaxisRangeTo.setMinimum(-99999);
		
		CLabel lblPoXaxisDivisioning = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisDivisioning.setText("Divisioning:");
		
		btnPoXaxisPiDivisioning = new Button(grpPlotoptionsXAxisRow, SWT.CHECK);
		btnPoXaxisPiDivisioning.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsXaxisPiDivisioningChanged();
			}
		});
		btnPoXaxisPiDivisioning.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		btnPoXaxisPiDivisioning.setText("Pi Divisioning");
		
		CLabel lblPoXaxisHelplines = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisHelplines.setText("Helplines:");
		
		txtPoXaxisHelplines = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisHelplines.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsXaxisHelplinesChanged();
			}
		});
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
		txtPoYaxisTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsYaxisTitleChanged();
			}
		});
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
		spinnerPoYaxisRangeFrom.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsYaxisRangeFromChanged();
			}
		});
		spinnerPoYaxisRangeFrom.setMaximum(99999);
		spinnerPoYaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoYaxisTo = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisTo.setText("to");
		
		spinnerPoYaxisRangeTo = new Spinner(grpPlotoptionsYAxisRow, SWT.BORDER);
		spinnerPoYaxisRangeTo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsYaxisRangeToChanged();
			}
		});
		spinnerPoYaxisRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoYaxisRangeTo.setMaximum(99999);
		spinnerPoYaxisRangeTo.setMinimum(-99999);
		
		CLabel lblPoYaxisHelplines = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisHelplines.setText("Helplines:");
		
		txtPoYaxisHelplines = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisHelplines.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsYaxisHelplinesChanged();
			}
		});
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
		txtPoIntegralTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralTitleChanged();
			}
		});
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
		spinnerPoIntegralRangeFrom.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralRangeFromChanged();
			}
		});
		spinnerPoIntegralRangeFrom.setIncrement(1000);
		spinnerPoIntegralRangeFrom.setDigits(3);
		spinnerPoIntegralRangeFrom.setMaximum(99999);
		spinnerPoIntegralRangeFrom.setMinimum(-99999);
		
		CLabel lblPoIntegralRangeTo = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralRangeTo.setText("to");
		
		spinnerPoIntegralRangeTo = new Spinner(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		spinnerPoIntegralRangeTo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralRangeToChanged();
			}
		});
		spinnerPoIntegralRangeTo.setIncrement(1000);
		spinnerPoIntegralRangeTo.setDigits(3);
		spinnerPoIntegralRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoIntegralRangeTo.setMaximum(99999);
		spinnerPoIntegralRangeTo.setMinimum(-99999);
		
		CLabel lblPoIntegralBordering = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralBordering.setText("Bordering:");
		
		comboPoIntegralBorderingFrom = new Combo(grpPlotoptionsIntegralAreaRow, SWT.READ_ONLY);
		comboPoIntegralBorderingFrom.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralBroderingFromChanged();
			}
		});
		comboPoIntegralBorderingFrom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		comboPoIntegralBorderingFrom.setItems(new String[] {});
		
		CLabel lblPoIntegralAnd = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralAnd.setText("&&");
		
		comboPoIntegralBorderingTo = new Combo(grpPlotoptionsIntegralAreaRow, SWT.READ_ONLY);
		comboPoIntegralBorderingTo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralBorderingToChanged();
			}
		});
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
		
		browserPreviewScreenViewGraph = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewGraph.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The graph's screen view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewScreenViewGraph.setControl(browserPreviewScreenViewGraph);
		
		TabItem tbtmPreviewScreenViewLegend = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewLegend.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/legend-16.png"));
		tbtmPreviewScreenViewLegend.setText("Legend");
		
		browserPreviewScreenViewLegend = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewLegend.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The legend's screen view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewScreenViewLegend.setControl(browserPreviewScreenViewLegend);
		
		TabItem tbtmPreviewScreenViewDescription = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewDescription.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/list-16.png"));
		tbtmPreviewScreenViewDescription.setText("Description");
		
		browserPreviewScreenViewDescription = new Browser(tabFolderPreviewScreenView, SWT.NONE);
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
		
		browserPreviewPrintViewGraph = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewGraph.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The graph's print view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewPrintViewGraph.setControl(browserPreviewPrintViewGraph);
		
		TabItem tbtmPreviewPrintViewLegend = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewLegend.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/legend-16.png"));
		tbtmPreviewPrintViewLegend.setText("Legend");
		
		browserPreviewPrintViewLegend = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewLegend.setText("<html>\n<body>\n<p style='font-family: sans-serif; font-size: 0.7em; color: gray;'>The legend's print view preview goes here.</p>\n</body>\n</html>");
		tbtmPreviewPrintViewLegend.setControl(browserPreviewPrintViewLegend);
		
		TabItem tbtmPreviewPrintViewDescription = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewDescription.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/list-16.png"));
		tbtmPreviewPrintViewDescription.setText("Description");
		
		browserPreviewPrintViewDescription = new Browser(tabFolderPreviewPrintView, SWT.NONE);
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
		MarkedPointsList p = pointlistMap.get(pointlist);
		PointStyle scrPS = p.getPointScreenStyle();
		if(scrPS == null) {
			scrPS = DefaultStyles.getDefaultScreenMarkedPointsListPointStyle();
		}
		PointStyle prtPS = p.getPointPrintStyle();
		if(prtPS == null) {
			prtPS = DefaultStyles.getDefaultPrintMarkedPointsListPointStyle();
		}
		
		PointStyleToolbox ps = new PointStyleToolbox(shlGsvgplott, 0, scrPS, prtPS);
		ps.setOpeningLocation(Display.getDefault().getCursorLocation());
		ps.open();
		
		Map<String, PointStyle> result = ps.getNewStyles();
		p.setPointScreenStyle(result.get("screen"));
		p.setPointPrintStyle(result.get("print"));
	}
	
	/**
	 * Open tool box for general/common point styling
	 */
	protected void triggerDataPointListCommonStyleToolbox() {
		if(diagram.getMarkedPointLists().isEmpty()) {
			triggerDataAddPointList();
		}
		
		MarkedPointsList p = pointlistMap.get(pointlists.get(0));
		PointStyle scrPS = p.getPointScreenStyle();
		if(scrPS == null) {
			scrPS = DefaultStyles.getDefaultScreenMarkedPointsListPointStyle();
		}
		PointStyle prtPS = p.getPointPrintStyle();
		if(prtPS == null) {
			prtPS = DefaultStyles.getDefaultPrintMarkedPointsListPointStyle();
		}
		
		PointStyleToolbox ps = new PointStyleToolbox(shlGsvgplott, 0, scrPS, prtPS);
		ps.setOpeningLocation(Display.getDefault().getCursorLocation());
		ps.open();
		
		Map<String, PointStyle> result = ps.getNewStyles();
		//p.setPointScreenStyle(result.get("screen")); DON'T SET THIS SPECIFICALLY BUT FOR EACH
		//p.setPointPrintStyle(result.get("print"));
		for(Entry<Group, MarkedPointsList> e : pointlistMap.entrySet()) {
			MarkedPointsList l = e.getValue();
			l.setPointScreenStyle(result.get("screen"));
			l.setPointPrintStyle(result.get("print"));
		}
	}
	
	/**
	 * Open tool box for line styling
	 * @param function
	 */
	protected void triggerDataFunctionStyleToolbox(Group function) {
		Function f = functionMap.get(function);
		LineStyle scrLS = f.getFunctionScreenStyle();
		if(scrLS == null) {
			scrLS = DefaultStyles.getDefaultScreenFunctionLineStyle();
		}
		LineStyle prtLS = f.getFunctionPrintStyle();
		if(prtLS == null) {
			prtLS = DefaultStyles.getDefaultPrintFunctionLineStyle();
		}
		
		LineStyleToolbox ls = new LineStyleToolbox(shlGsvgplott, 0, scrLS, prtLS);
		ls.setOpeningLocation(Display.getDefault().getCursorLocation());
		ls.open();
		
		Map<String, LineStyle> result = ls.getNewStyles();
		f.setFunctionScreenStyle(result.get("screen"));
		f.setFunctionPrintStyle(result.get("print"));
		
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
		AreaStyle scrAS = diagram.getBackgroundScreenStyle();
		if(scrAS == null) {
			scrAS = DefaultStyles.getDefaultScreenGeneralAreaStyle();
		}
		AreaStyle prtAS = diagram.getBackgroundPrintStyle();
		if(prtAS == null) {
			prtAS = DefaultStyles.getDefaultPrintGeneralAreaStyle();
		}
		TextStyle scrTS = diagram.getTextScreenStyle();
		if(scrTS == null) {
			scrTS = DefaultStyles.getDefaultScreenGeneralTextStyle();
		}
		TextStyle prtTS = diagram.getTextPrintStyle();
		if(prtTS == null) {
			prtTS = DefaultStyles.getDefaultPrintGeneralTextStyle();
		}
		
		GeneralStyleToolbox gt = new GeneralStyleToolbox(shlGsvgplott, 0, scrTS, prtTS, scrAS, prtAS);
		gt.setOpeningLocation(Display.getDefault().getCursorLocation());
		gt.open();
		
		Map<String, Map<String, Object>> result = gt.getNewStyles();
		scrAS = (AreaStyle)(result.get("area").get("screen"));
		prtAS = (AreaStyle)(result.get("area").get("print"));
		scrTS = (TextStyle)(result.get("text").get("screen"));
		prtTS = (TextStyle)(result.get("text").get("print"));
		diagram.setBackgroundScreenStyle(scrAS);
		diagram.setBackgroundPrintStyle(prtAS);
		diagram.setTextScreenStyle(scrTS);
		diagram.setTextPrintStyle(prtTS);
	}
	
	/**
	 * Open tool box for X Axis styling
	 */
	protected void triggerOptionsXaxisStyleToolbox() {
		LineStyle scrLS = diagram.getXaxis().getAxisScreenStyle();
		if(scrLS == null) {
			scrLS = DefaultStyles.getDefaultScreenXaxisLineStyle();
		}
		LineStyle prtLS = diagram.getXaxis().getAxisPrintStyle();
		if(prtLS == null) {
			prtLS = DefaultStyles.getDefaultPrintXaxisLineStyle();
		}
		
		LineStyleToolbox lt = new LineStyleToolbox(shlGsvgplott, 0, scrLS, prtLS);
		lt.setOpeningLocation(Display.getDefault().getCursorLocation());
		lt.open();
		
		Map<String, LineStyle> result = lt.getNewStyles();
		scrLS = result.get("screen");
		prtLS = result.get("print");
		diagram.getXaxis().setAxisScreenStyle(scrLS);
		diagram.getXaxis().setAxisPrintStyle(prtLS);
		
	}
	
	/**
	 * Open tool box for X Axis Helpline styling
	 */
	protected void triggerOptionsXaxisHelplineStyleToolbox() {
		LineStyle scrLS = diagram.getXaxis().getHelplineScreenStyle();
		if(scrLS == null) {
			scrLS = DefaultStyles.getDefaultScreenXaxisHelplinesLineStyle();
		}
		LineStyle prtLS = diagram.getXaxis().getHelplinePrintStyle();
		if(prtLS == null) {
			prtLS = DefaultStyles.getDefaultPrintXaxisHelplinesLineStyle();
		}
		
		LineStyleToolbox lt = new LineStyleToolbox(shlGsvgplott, 0, scrLS, prtLS);
		lt.setOpeningLocation(Display.getDefault().getCursorLocation());
		lt.open();
		
		Map<String, LineStyle> result = lt.getNewStyles();
		scrLS = result.get("screen");
		prtLS = result.get("print");
		diagram.getXaxis().setHelplineScreenStyle(scrLS);
		diagram.getXaxis().setHelplinePrintStyle(prtLS);
	}
	
	/**
	 * Open tool box for Y Axis styling
	 */
	protected void triggerOptionsYaxisStyleToolbox() {
		LineStyle scrLS = diagram.getYaxis().getAxisScreenStyle();
		if(scrLS == null) {
			scrLS = DefaultStyles.getDefaultScreenYaxisLineStyle();
		}
		LineStyle prtLS = diagram.getYaxis().getAxisPrintStyle();
		if(prtLS == null) {
			prtLS = DefaultStyles.getDefaultPrintYaxisLineStyle();
		}
		
		LineStyleToolbox lt = new LineStyleToolbox(shlGsvgplott, 0, scrLS, prtLS);
		lt.setOpeningLocation(Display.getDefault().getCursorLocation());
		lt.open();
		
		Map<String, LineStyle> result = lt.getNewStyles();
		scrLS = result.get("screen");
		prtLS = result.get("print");
		diagram.getYaxis().setAxisScreenStyle(scrLS);
		diagram.getYaxis().setAxisPrintStyle(prtLS);
	}
	
	/**
	 * Open tool box for Y Axis Helpline styling
	 */
	protected void triggerOptionsYaxisHelplineStyleToolbox() {
		LineStyle scrLS = diagram.getYaxis().getHelplineScreenStyle();
		if(scrLS == null) {
			scrLS = DefaultStyles.getDefaultScreenYaxisHelplinesLineStyle();
		}
		LineStyle prtLS = diagram.getYaxis().getHelplinePrintStyle();
		if(prtLS == null) {
			prtLS = DefaultStyles.getDefaultPrintYaxisHelplinesLineStyle();
		}
		
		LineStyleToolbox lt = new LineStyleToolbox(shlGsvgplott, 0, scrLS, prtLS);
		lt.setOpeningLocation(Display.getDefault().getCursorLocation());
		lt.open();
		
		Map<String, LineStyle> result = lt.getNewStyles();
		scrLS = result.get("screen");
		prtLS = result.get("print");
		diagram.getYaxis().setHelplineScreenStyle(scrLS);
		diagram.getYaxis().setHelplinePrintStyle(prtLS);
	}
	
	/**
	 * Open tool box for Integral styling
	 */
	protected void triggerOptionsIntegralStyleToolbox() {
		AreaStyle scrAS = null;
		AreaStyle prtAS = null;
		if(diagram.getIntegral() != null) {
			scrAS = diagram.getIntegral().getAreaScreenStyle();
			prtAS = diagram.getIntegral().getAreaPrintStyle();
		}
		if(scrAS == null) {
			scrAS = DefaultStyles.getDefaultScreenIntegralAreaStyle();
		}
		if(prtAS == null) {
			prtAS = DefaultStyles.getDefaultPrintIntegralAreaStyle();
		}
		
		AreaStyleToolbox at = new AreaStyleToolbox(shlGsvgplott, 0, scrAS, prtAS);
		at.setOpeningLocation(Display.getDefault().getCursorLocation());
		at.open();
		
		if(diagram.getIntegral() != null) {
			Map<String, AreaStyle> result = at.getNewStyles();
			scrAS = result.get("screen");
			prtAS = result.get("print");
			diagram.getIntegral().setAreaScreenStyle(scrAS);
			diagram.getIntegral().setAreaPrintStyle(prtAS);
		}
	}

	/**
	 * Update diagram integral
	 */
	protected void triggerOptionsIntegralBorderingToChanged() {
		operateOptionsIntegralChanged();
		
	}

	/**
	 * Update diagram integral
	 */
	protected void triggerOptionsIntegralBroderingFromChanged() {
		operateOptionsIntegralChanged();
		
	}

	/**
	 * Update diagram integral
	 */
	protected void triggerOptionsIntegralRangeToChanged() {
		operateOptionsIntegralChanged();
		
	}

	/**
	 * Update diagram integral
	 */
	protected void triggerOptionsIntegralRangeFromChanged() {
		operateOptionsIntegralChanged();
		
	}

	/**
	 * Update diagram integral
	 */
	protected void triggerOptionsIntegralTitleChanged() {
		operateOptionsIntegralChanged();
	}

	/**
	 * Update diagram y axis helplines
	 */
	protected void triggerOptionsYaxisHelplinesChanged() {
		// parse input
		String input = this.txtPoYaxisHelplines.getText();
		String[] values = input.split(" ");
		List<String> listValues = new ArrayList<String>();
		listValues.addAll(Arrays.asList(values));
	
		// add helplines
		ArrayList<Helpline> helplines = new ArrayList<Helpline>();
		for (String s : listValues) {
			try {
				double n = Double.parseDouble(s);
				Helpline h = new Helpline(n);
				helplines.add(h);
			} catch (Exception e) {
				// If parsing was not successful, stop here
			}
		}
		diagram.getYaxis().setHelplines(helplines);
		
	}

	/**
	 * Update diagram y axis range to
	 */
	protected void triggerOptionsYaxisRangeToChanged() {
		diagram.getYaxis().setRangeTo(this.spinnerPoYaxisRangeTo.getSelection());
		
	}

	/**
	 * Update diagram y axis range from
	 */
	protected void triggerOptionsYaxisRangeFromChanged() {
		diagram.getYaxis().setRangeFrom(this.spinnerPoYaxisRangeFrom.getSelection());
		
	}

	/**
	 * Update diagram y axis title
	 */
	protected void triggerOptionsYaxisTitleChanged() {
		diagram.getYaxis().setTitle(this.txtPoYaxisTitle.getText());
		
	}

	/**
	 * Update diagram x axis helplines
	 */
	protected void triggerOptionsXaxisHelplinesChanged() {
		//parse input
		String input = this.txtPoXaxisHelplines.getText();
		String[] values = input.split(" ");
		List<String> listValues = new ArrayList<String>();
		listValues.addAll(Arrays.asList(values));
		
		//add helplines
		ArrayList<Helpline> helplines = new ArrayList<Helpline>();
		for(String s : listValues) {
			try {
				double n = Double.parseDouble(s);
				Helpline h = new Helpline(n);
				helplines.add(h);
			} catch (Exception e) {
				// If parsing was not successful, stop here
			}
		}
		diagram.getXaxis().setHelplines(helplines);
		
	}

	/**
	 * Update diagram x axis pi divisioning
	 */
	protected void triggerOptionsXaxisPiDivisioningChanged() {
		diagram.getXaxis().setPiDivisioning(this.btnPoXaxisPiDivisioning.getSelection());
		
	}

	/**
	 * Update diagram x axis range to
	 */
	protected void triggerOptionsXaxisRangeToChanged() {
		diagram.getXaxis().setRangeTo(this.spinnerPoXaxisRangeTo.getSelection());
		
	}

	/**
	 * Update diagram x axis range from
	 */
	protected void triggerOptionsXaxisRangeFromChanged() {
		diagram.getXaxis().setRangeFrom(this.spinnerPoXaxisRangeFrom.getSelection());
		
	}

	/**
	 * Update diagram x axis title
	 */
	protected void triggerOptionsXaxisTitleChanged() {
		diagram.getXaxis().setTitle(this.txtPoXaxisTitle.getText());
		
	}

	/**
	 * Update diagram show grid option
	 */
	protected void triggerOptionsGeneralShowGridChanged() {
		diagram.setShowGrid(this.btnPoGeneralShowGrid.getSelection());
		
	}

	/**
	 * Update diagram size (height)
	 */
	protected void triggerOptionsGeneralSizeHeightChanged() {
		diagram.setSizeHeight(this.spinnerPoGeneralHeight.getSelection());
		
	}

	/**
	 * Update diagram size (width)
	 */
	protected void triggerOptionsGeneralSizeWidthChanged() {
		diagram.setSizeWidth(this.spinnerPoGeneralWidth.getSelection());
		
	}

	/**
	 * Update diagram title
	 */
	protected void triggerOptionsGeneralTitleChanged() {
		diagram.setTitle(this.txtPoGeneralTitle.getText());
		
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
		
		operateGeneratePreview();
		
	}

	/**
	 * Create new file
	 */
	protected void triggerMenuNew() {
		operateResetDiagram();
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
		diagram.setBackgroundScreenStyle(null);
		diagram.setBackgroundPrintStyle(null);
		diagram.setTextScreenStyle(null);
		diagram.setTextPrintStyle(null);
		
		diagram.setOverrideStyle(null);
		
		diagram.getXaxis().setAxisScreenStyle(null);
		diagram.getXaxis().setAxisPrintStyle(null);
		diagram.getXaxis().setHelplineScreenStyle(null);
		diagram.getXaxis().setHelplinePrintStyle(null);
		
		diagram.getYaxis().setAxisScreenStyle(null);
		diagram.getYaxis().setAxisPrintStyle(null);
		diagram.getYaxis().setHelplineScreenStyle(null);
		diagram.getYaxis().setHelplinePrintStyle(null);
		
		if(diagram.getIntegral() != null) {
			diagram.getIntegral().setAreaScreenStyle(null);
			diagram.getIntegral().setAreaPrintStyle(null);
		}
		
		for(Function f : diagram.getFunctions()) {
			f.setFunctionScreenStyle(null);
			f.setFunctionPrintStyle(null);
		}
		
		for(MarkedPointsList l : diagram.getMarkedPointLists()) {
			l.setPointScreenStyle(null);
			l.setPointPrintStyle(null);
		}
	}
	
	/**
	 * Open CSS Style Override Dialog
	 */
	protected void triggerMenuCssStyleOverride() {
		CssStyleOverrideDialog cd;
		if(diagram.getOverrideStyle() != null) {
			cd = new CssStyleOverrideDialog(shlGsvgplott, 0, diagram.getOverrideStyle());
		} else {
			cd = new CssStyleOverrideDialog(shlGsvgplott, 0, new OverrideStyle(""));
		}
		cd.open();
		OverrideStyle result = cd.getNewOverrideStyle();
		if (result.getCssStyle().isEmpty()) {
			diagram.setOverrideStyle(null);
		} else {
			diagram.setOverrideStyle(result);
		}
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
		
		Label lblSepDataRowFunctions = new Label(compositeDataColumn, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSepDataRowFunctions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
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
		
		Button btnDataColumnPointListStyle = new Button(compositeDataColumn, SWT.FLAT);
		btnDataColumnPointListStyle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnDataColumnPointListStyle.setToolTipText("Change style");
		btnDataColumnPointListStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		btnDataColumnPointListStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataPointListCommonStyleToolbox();
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
		txtDRFTitle1.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				Function f = functionMap.get(grpDataRowFunction1);
				if(f == null)
					return;
				
				String title = ((Text)arg0.getSource()).getText();
				f.setTitle(title);
			}
		});
		
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
		txtDRFfunc1.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				Function f = functionMap.get(grpDataRowFunction1);
				if(f == null)
					return;
				
				String func = ((Text)arg0.getSource()).getText();
				f.setFunction(func);
				
			}
		});
		
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
		txtDRMtitle1.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				MarkedPointsList list = pointlistMap.get(grpDataRowMarkedpointsPointList);
				if(list != null) {
					list.setTitle(txtDRMtitle1.getText());
				}
				
			}
		});
		
		/*Button btnDRMStyle1 = new Button(grpDataRowMarkedpointsPointList, SWT.FLAT);
		btnDRMStyle1.setToolTipText("Change style");
		btnDRMStyle1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		btnDRMStyle1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataPointListStyleToolbox(grpDataRowMarkedpointsPointList);
			}
		});*/
		
		Composite compositeDRMlist1 = new Composite(grpDataRowMarkedpointsPointList, SWT.NONE);
		compositeDRMlist1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1));
		compositeDRMlist1.setLayout(null);
		
		Table tableDRMlist1 = new Table(compositeDRMlist1, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
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
		AddPointDialog apd = new AddPointDialog(shlGsvgplott, 0);
		apd.open();
		de.tudresden.inf.gsvgplott.data.Point p = apd.getPoint();
		
		if(p != null) {
			Group grp = (Group)(table.getParent().getParent());
			MarkedPointsList list = pointlistMap.get(grp);
			
			for(de.tudresden.inf.gsvgplott.data.Point i : list.getPoints()) {
				if(i.getX() == p.getX() && i.getY() == p.getY()) {
					MessageBox b = new MessageBox(shlGsvgplott);
					b.setText("Information");
					b.setMessage("This point is already in the list.");
					b.open();
					return;
				}
			}
			
			list.getPoints().add(p);
			
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(new String[] {Double.toString(p.getX()), Double.toString(p.getY())});
		} 
	}
	
	/**
	 * Removes a point from the intended point list table
	 * @param table
	 */
	protected void operateDataPointListRemovePoint(Table table) {
		int[] selection = table.getSelectionIndices();
		
		TableItem[] items = table.getSelection();
		List<TableItem> list = new ArrayList<TableItem>();
		list.addAll(Arrays.asList(items));
		
		for(TableItem i : list) {
			double ix = Double.parseDouble(i.getText(0));
			double iy = Double.parseDouble(i.getText(1));
			
			Group grp = (Group)(table.getParent().getParent());
			MarkedPointsList pl = pointlistMap.get(grp);
			
			List<de.tudresden.inf.gsvgplott.data.Point> delete = new ArrayList<de.tudresden.inf.gsvgplott.data.Point>();
			for(de.tudresden.inf.gsvgplott.data.Point p : pl.getPoints()) {
				if(p.getX() == ix && p.getY() == iy) {
					delete.add(p);
				}
			}
			pl.getPoints().removeAll(delete);
		}
		
		table.remove(selection);
	}
	
	/**
	 * Sets or resets the integral based on the current integral options
	 */
	protected void operateOptionsIntegralChanged() {
		// avoid null pointer exceptions on application startup
		if(spinnerPoIntegralRangeFrom == null || spinnerPoIntegralRangeTo == null)
			return;
		
		//if ranges are equal, remove integral, else add one
		if(spinnerPoIntegralRangeFrom.getSelection() == spinnerPoIntegralRangeTo.getSelection()) {
			diagram.setIntegral(null);
			return;
		}
		
		String title = this.txtPoIntegralTitle.getText();
		double rangeFrom = (double)spinnerPoIntegralRangeFrom.getSelection() / Math.pow(10, spinnerPoIntegralRangeFrom.getDigits());
		double rangeTo = (double)spinnerPoIntegralRangeTo.getSelection() / Math.pow(10, spinnerPoIntegralRangeTo.getDigits());
		
		Function border1 = null;
		for(Group grp : this.functions) {
			if(grp.getText().equals(comboPoIntegralBorderingFrom.getText())) {
				border1 = functionMap.get(grp);
			}
		}
		if(border1 == null) {
			//function cannot be found but has to be set in order to create an integral!
			diagram.setIntegral(null);
			return;
		}
		
		Function border2 = null;
		for(Group grp : this.functions) {
			if(grp.getText().equals(comboPoIntegralBorderingTo.getText())) {
				border2 = functionMap.get(grp);
			}
		}
		// if border2 is still null, the x axis will be used. This is an intended case!
		
		Integral i = new Integral(title, rangeFrom, rangeTo, border1, border2);
		diagram.setIntegral(i);
	}

	/**
	 * This method invokes the creation of preview diagrams out of the current Diagram instance
	 */
	protected void operateGeneratePreview() {
		Map<String, String> previewScreen = operateFireSVGPlott(RenderMode.UNISCREEN, true);
		Map<String, String> previewPrint = operateFireSVGPlott(RenderMode.UNIPRINT, true);
		
		this.browserPreviewScreenViewGraph.setText(previewScreen.get("document"));
		this.browserPreviewScreenViewDescription.setText(previewScreen.get("description"));
		this.browserPreviewScreenViewLegend.setText(previewScreen.get("legend"));
		
		this.browserPreviewPrintViewGraph.setText(previewPrint.get("document"));
		this.browserPreviewPrintViewDescription.setText(previewPrint.get("description"));
		this.browserPreviewPrintViewLegend.setText(previewPrint.get("legend"));
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
	protected Map<String, String> operateFireSVGPlott(RenderMode mode, boolean surpressWarnings) {
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
		
		PointListList pl = new PointListList();
		for(MarkedPointsList l : diagram.getMarkedPointLists()) {
			List<de.tudresden.inf.gsvgplott.data.Point> points = l.getPoints();
			
			PointList list = pl.new PointList();
			list.name = l.getTitle();
			for(de.tudresden.inf.gsvgplott.data.Point p : points) {
				tud.tangram.svgplot.coordinatesystem.Point tp = 
						new tud.tangram.svgplot.coordinatesystem.Point(p.getX(), p.getY());
				list.add(tp);
			}
			pl.add(list);
		}
		plotter.setPoints(pl);
		
		plotter.setTitle(diagram.getTitle());
		
		plotter.setSize(
				new tud.tangram.svgplot.coordinatesystem.Point(
						(double)diagram.getSizeWidth(), 
						(double)diagram.getSizeHeight()));
		
		//FIXME: Function selection may be faulty
		if(diagram.getIntegral() != null) {
			Function f1 = diagram.getIntegral().getBorder1();
			int first = 0;
			if(f1 != null) {
				first = diagram.getFunctions().indexOf(f1);
			}
			Function f2 = diagram.getIntegral().getBorder2();
			int second = first;
			if(f2 != null) {
				second = diagram.getFunctions().indexOf(f2);
			}
			plotter.setIntegral(new IntegralPlotSettings(
					first, 
					second, 
					diagram.getIntegral().getTitle(), 
					new Range(
							diagram.getIntegral().getRangeFrom(), 
							diagram.getIntegral().getRangeTo())));
		}
		
		Range xrange = new Range(
				(double)diagram.getXaxis().getRangeFrom(), 
				(double)diagram.getXaxis().getRangeTo(), 
				diagram.getXaxis().getTitle());
		plotter.setxRange(xrange);
		
		plotter.setPi(diagram.getXaxis().isPiDivisioning());
		
		String strHx = "";
		List<Helpline> hx = diagram.getXaxis().getHelplines();
		for(Helpline h : hx) {
			strHx += String.valueOf(h.getIntersection()) + " ";
		}
		if(!strHx.isEmpty()) {
			plotter.setxLines(strHx);
		}
		
		Range yrange = new Range(
				(double)diagram.getYaxis().getRangeFrom(),
				(double)diagram.getYaxis().getRangeTo(),
				diagram.getYaxis().getTitle());
		plotter.setyRange(yrange);
		
		String strHy = "";
		List<Helpline> hy = diagram.getYaxis().getHelplines();
		for(Helpline h : hy) {
			strHy += String.valueOf(h.getIntersection()) + " ";
		}
		if(!strHy.isEmpty()) {
			plotter.setyLines(strHy);
		}
		
		try {
			plotter.create();
		} catch (DOMException | ParserConfigurationException | IOException
				| InterruptedException e) {
			return null; // iff there was an error
		} catch (NullPointerException e) {
			if(!surpressWarnings) {
				MessageBox b = new MessageBox(shlGsvgplott);
				b.setText("Gnuplot not available");
				b.setMessage("The plotter encountered a problem. Propably the required Gnuplot is not installed properly.");
				b.open();
			}
			return null; // iff gnuplott is unavailable
		}
		
		// return all artifacts
		SvgDocument doc = plotter.getDoc();
		HtmlDocument desc = plotter.getDesc();
		SvgDocument legend = plotter.getLegend();
		
		ByteArrayOutputStream baosDoc = new ByteArrayOutputStream();
		ByteArrayOutputStream baosDesc = new ByteArrayOutputStream();
		ByteArrayOutputStream baosLegend = new ByteArrayOutputStream();
		try {
			doc.writeTo(baosDoc);
			desc.writeTo(baosDesc);
			legend.writeTo(baosLegend);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		String strDoc = new String(baosDoc.toByteArray(),  java.nio.charset.StandardCharsets.UTF_8);
		String strDesc = new String(baosDesc.toByteArray(),  java.nio.charset.StandardCharsets.UTF_8);
		String strLegend = new String(baosLegend.toByteArray(),  java.nio.charset.StandardCharsets.UTF_8);
		
		Map<String, String> resultset = new HashMap<String, String>();
		resultset.put("document", strDoc);
		resultset.put("description", strDesc);
		resultset.put("legend", strLegend);
		return resultset;
	}
}
