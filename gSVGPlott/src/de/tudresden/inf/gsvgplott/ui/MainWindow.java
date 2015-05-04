package de.tudresden.inf.gsvgplott.ui;

/**
 * 
 * @author David Gollasch
 *
 */

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import de.tudresden.inf.gsvgplott.ui.util.PersistanceHelper;
import de.tudresden.inf.gsvgplott.ui.util.RenderMode;
import de.tudresden.inf.gsvgplott.ui.util.SvgPlotHelper;

import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.accessibility.AccessibleListener;
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

import java.util.ResourceBundle;

public class MainWindow {
	private ResourceBundle DICT = ResourceBundle.getBundle("de.tudresden.inf.gsvgplott.ui.util.messages"); //$NON-NLS-1$
	private static Locale LOC = Locale.GERMAN;
	
	public static final String APP_NAME = "gSVGPlott";

	protected Shell shlGsvgplott;
	
	/**
	 * The diagram object holds all data to be processed
	 */
	private Diagram diagram;
	private String filename;
	
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
		
		//TODO: Remove when finished
		Locale.setDefault(LOC);
		ResourceBundle.clearCache();
		
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
		
		// timer to update preview regularly
		Runnable previewtimer = new Runnable() {
			
			@Override
			public void run() {
				operateGeneratePreview();
				display.timerExec(3000, this);
			}
		};
		display.timerExec(3000, previewtimer);
		
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
		shlGsvgplott.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/gSVGPlottIcon.png"));
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
		lblPlotoptionsColumn.setText(DICT.getString("MainWindow.lblPlotoptionsColumn.text")); //$NON-NLS-1$
		
		Group grpPlotoptionsGeneralRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsGeneralRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpPlotoptionsGeneralRow.setText(DICT.getString("MainWindow.grpPlotoptionsGeneralRow.text")); //$NON-NLS-1$
		grpPlotoptionsGeneralRow.setLayout(new GridLayout(6, false));
		
		CLabel lblPoGeneralTitle = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralTitle.setText(DICT.getString("MainWindow.lblPoGeneralTitle.text")); //$NON-NLS-1$
		
		txtPoGeneralTitle = new Text(grpPlotoptionsGeneralRow, SWT.BORDER);
		txtPoGeneralTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsGeneralTitleChanged();
			}
		});
		txtPoGeneralTitle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.txtPoGeneralTitle.access");
			}
		});
		
		txtPoGeneralTitle.setToolTipText(DICT.getString("MainWindow.txtPoGeneralTitle.toolTipText")); //$NON-NLS-1$
		txtPoGeneralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		Button btnPlotoptionsGeneralStyle = new Button(grpPlotoptionsGeneralRow, SWT.FLAT);
		btnPlotoptionsGeneralStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsGeneralStyleToolbox();
			}
		});
		btnPlotoptionsGeneralStyle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnPlotoptionsGeneralStyle.access");
			}
		});
		btnPlotoptionsGeneralStyle.setToolTipText(DICT.getString("MainWindow.btnPlotoptionsGeneralStyle.toolTipText")); //$NON-NLS-1$
		btnPlotoptionsGeneralStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblPoGeneralSize = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSize.setText(DICT.getString("MainWindow.lblPoGeneralSize.text")); //$NON-NLS-1$
		
		spinnerPoGeneralWidth = new Spinner(grpPlotoptionsGeneralRow, SWT.BORDER);
		spinnerPoGeneralWidth.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsGeneralSizeWidthChanged();
			}
		});
		spinnerPoGeneralWidth.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.spinnerPoGeneralWidth.access");
			}
		});
		spinnerPoGeneralWidth.setToolTipText(DICT.getString("MainWindow.spinnerPoGeneralWidth.toolTipText")); //$NON-NLS-1$
		spinnerPoGeneralWidth.setIncrement(10);
		spinnerPoGeneralWidth.setMaximum(99999);
		spinnerPoGeneralWidth.setMinimum(0);
		spinnerPoGeneralWidth.setSelection(210);
		
		CLabel lblPoGeneralSizeMm = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSizeMm.setText(DICT.getString("MainWindow.lblPoGeneralSizeMm.text")); //$NON-NLS-1$
		
		spinnerPoGeneralHeight = new Spinner(grpPlotoptionsGeneralRow, SWT.BORDER);
		spinnerPoGeneralHeight.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsGeneralSizeHeightChanged();
			}
		});
		spinnerPoGeneralHeight.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.spinnerPoGeneralHeight.access");
			}
		});
		spinnerPoGeneralHeight.setToolTipText(DICT.getString("MainWindow.spinnerPoGeneralHeight.toolTipText")); //$NON-NLS-1$
		spinnerPoGeneralHeight.setMaximum(99999);
		spinnerPoGeneralHeight.setMinimum(0);
		spinnerPoGeneralHeight.setSelection(297);
		spinnerPoGeneralHeight.setIncrement(10);
		
		CLabel lblPoGeneralSizeMm2 = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralSizeMm2.setText(DICT.getString("MainWindow.lblPoGeneralSizeMm2.text")); //$NON-NLS-1$
		new Label(grpPlotoptionsGeneralRow, SWT.NONE);
		
		CLabel lblPoGeneralGrid = new CLabel(grpPlotoptionsGeneralRow, SWT.NONE);
		lblPoGeneralGrid.setText(DICT.getString("MainWindow.lblPoGeneralGrid.text")); //$NON-NLS-1$
		
		btnPoGeneralShowGrid = new Button(grpPlotoptionsGeneralRow, SWT.CHECK);
		btnPoGeneralShowGrid.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsGeneralShowGridChanged();
			}
		});
		btnPoGeneralShowGrid.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		btnPoGeneralShowGrid.setText(DICT.getString("MainWindow.btnPoGeneralShowGrid.text")); //$NON-NLS-1$
		
		Group grpPlotoptionsXAxisRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsXAxisRow.setLayout(new GridLayout(5, false));
		grpPlotoptionsXAxisRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpPlotoptionsXAxisRow.setText(DICT.getString("MainWindow.grpPlotoptionsXAxisRow.text")); //$NON-NLS-1$
		
		CLabel lblPoXaxisTitle = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisTitle.setText(DICT.getString("MainWindow.lblPoXaxisTitle.text")); //$NON-NLS-1$
		
		txtPoXaxisTitle = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsXaxisTitleChanged();
			}
		});
		txtPoXaxisTitle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.txtPoXaxisTitle.access");
			}
		});
		txtPoXaxisTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtPoXaxisTitle.setToolTipText(DICT.getString("MainWindow.txtPoXaxisTitle.toolTipText")); //$NON-NLS-1$
		
		Button btnPlotoptionsXAxisStyle = new Button(grpPlotoptionsXAxisRow, SWT.FLAT);
		btnPlotoptionsXAxisStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsXaxisStyleToolbox();
			}
		});
		btnPlotoptionsXAxisStyle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnPlotoptionsXAxisStyle.access");
			}
		});
		btnPlotoptionsXAxisStyle.setToolTipText(DICT.getString("MainWindow.btnPlotoptionsXAxisStyle.toolTipText")); //$NON-NLS-1$
		btnPlotoptionsXAxisStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblPoXaxisRange = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisRange.setText(DICT.getString("MainWindow.lblPoXaxisRange.text")); //$NON-NLS-1$
		
		spinnerPoXaxisRangeFrom = new Spinner(grpPlotoptionsXAxisRow, SWT.BORDER);
		spinnerPoXaxisRangeFrom.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsXaxisRangeFromChanged();
			}
		});
		spinnerPoXaxisRangeFrom.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.spinnerPoXaxisRangeFrom.access");
			}
		});
		spinnerPoXaxisRangeFrom.setMaximum(99999);
		spinnerPoXaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoXaxisRangeTo = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisRangeTo.setText(DICT.getString("MainWindow.lblPoXaxisRangeTo.text")); //$NON-NLS-1$
		
		spinnerPoXaxisRangeTo = new Spinner(grpPlotoptionsXAxisRow, SWT.BORDER);
		spinnerPoXaxisRangeTo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsXaxisRangeToChanged();
			}
		});
		spinnerPoXaxisRangeTo.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.spinnerPoXaxisRangeTo.access");
			}
		});
		spinnerPoXaxisRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoXaxisRangeTo.setMaximum(99999);
		spinnerPoXaxisRangeTo.setMinimum(-99999);
		
		CLabel lblPoXaxisDivisioning = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisDivisioning.setText(DICT.getString("MainWindow.lblPoXaxisDivisioning.text")); //$NON-NLS-1$
		
		btnPoXaxisPiDivisioning = new Button(grpPlotoptionsXAxisRow, SWT.CHECK);
		btnPoXaxisPiDivisioning.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsXaxisPiDivisioningChanged();
			}
		});
		btnPoXaxisPiDivisioning.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		btnPoXaxisPiDivisioning.setText(DICT.getString("MainWindow.btnPoXaxisPiDivisioning.text")); //$NON-NLS-1$
		
		CLabel lblPoXaxisHelplines = new CLabel(grpPlotoptionsXAxisRow, SWT.NONE);
		lblPoXaxisHelplines.setText(DICT.getString("MainWindow.lblPoXaxisHelplines.text")); //$NON-NLS-1$
		
		txtPoXaxisHelplines = new Text(grpPlotoptionsXAxisRow, SWT.BORDER);
		txtPoXaxisHelplines.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsXaxisHelplinesChanged();
			}
		});
		txtPoXaxisHelplines.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.txtPoXaxisHelplines.access");
			}
		});
		txtPoXaxisHelplines.setToolTipText(DICT.getString("MainWindow.txtPoXaxisHelplines.toolTipText")); //$NON-NLS-1$
		txtPoXaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsXAxisHelplineStyle = new Button(grpPlotoptionsXAxisRow, SWT.FLAT);
		btnPlotoptionsXAxisHelplineStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsXaxisHelplineStyleToolbox();
			}
		});
		btnPlotoptionsXAxisHelplineStyle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnPlotoptionsXAxisHelplineStyle.access");
			}
		});
		btnPlotoptionsXAxisHelplineStyle.setToolTipText(DICT.getString("MainWindow.btnPlotoptionsXAxisHelplineStyle.toolTipText")); //$NON-NLS-1$
		btnPlotoptionsXAxisHelplineStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		Group grpPlotoptionsYAxisRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsYAxisRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpPlotoptionsYAxisRow.setText(DICT.getString("MainWindow.grpPlotoptionsYAxisRow.text")); //$NON-NLS-1$
		grpPlotoptionsYAxisRow.setLayout(new GridLayout(5, false));
		
		CLabel lblPoYaxisTitle = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisTitle.setText(DICT.getString("MainWindow.lblPoYaxisTitle.text")); //$NON-NLS-1$
		
		txtPoYaxisTitle = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsYaxisTitleChanged();
			}
		});
		txtPoYaxisTitle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.txtPoYaxisTitle.access");
			}
		});
		txtPoYaxisTitle.setToolTipText(DICT.getString("MainWindow.txtPoYaxisTitle.toolTipText")); //$NON-NLS-1$
		txtPoYaxisTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsYAxisStyle = new Button(grpPlotoptionsYAxisRow, SWT.FLAT);
		btnPlotoptionsYAxisStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsYaxisStyleToolbox();
			}
		});
		btnPlotoptionsYAxisStyle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnPlotoptionsYAxisStyle.access");
			}
		});
		btnPlotoptionsYAxisStyle.setToolTipText(DICT.getString("MainWindow.btnPlotoptionsYAxisStyle.toolTipText")); //$NON-NLS-1$
		btnPlotoptionsYAxisStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblPoYaxisRange = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisRange.setText(DICT.getString("MainWindow.lblPoYaxisRange.text")); //$NON-NLS-1$
		
		spinnerPoYaxisRangeFrom = new Spinner(grpPlotoptionsYAxisRow, SWT.BORDER);
		spinnerPoYaxisRangeFrom.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsYaxisRangeFromChanged();
			}
		});
		spinnerPoYaxisRangeFrom.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.spinnerPoYaxisRangeFrom.access");
			}
		});
		spinnerPoYaxisRangeFrom.setMaximum(99999);
		spinnerPoYaxisRangeFrom.setMinimum(-99999);
		
		CLabel lblPoYaxisTo = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisTo.setText(DICT.getString("MainWindow.lblPoYaxisTo.text")); //$NON-NLS-1$
		
		spinnerPoYaxisRangeTo = new Spinner(grpPlotoptionsYAxisRow, SWT.BORDER);
		spinnerPoYaxisRangeTo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsYaxisRangeToChanged();
			}
		});
		spinnerPoYaxisRangeTo.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.spinnerPoYaxisRangeTo.access");
			}
		});
		spinnerPoYaxisRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoYaxisRangeTo.setMaximum(99999);
		spinnerPoYaxisRangeTo.setMinimum(-99999);
		
		CLabel lblPoYaxisHelplines = new CLabel(grpPlotoptionsYAxisRow, SWT.NONE);
		lblPoYaxisHelplines.setText(DICT.getString("MainWindow.lblPoYaxisHelplines.text")); //$NON-NLS-1$
		
		txtPoYaxisHelplines = new Text(grpPlotoptionsYAxisRow, SWT.BORDER);
		txtPoYaxisHelplines.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsYaxisHelplinesChanged();
			}
		});
		txtPoYaxisHelplines.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.txtPoYaxisHelplines.access");
			}
		});
		txtPoYaxisHelplines.setToolTipText(DICT.getString("MainWindow.txtPoYaxisHelplines.toolTipText")); //$NON-NLS-1$
		txtPoYaxisHelplines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsYAxisHelplineStyle = new Button(grpPlotoptionsYAxisRow, SWT.FLAT);
		btnPlotoptionsYAxisHelplineStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsYaxisHelplineStyleToolbox();
			}
		});
		btnPlotoptionsYAxisHelplineStyle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnPlotoptionsYAxisHelplineStyle.access");
			}
		});
		btnPlotoptionsYAxisHelplineStyle.setToolTipText(DICT.getString("MainWindow.btnPlotoptionsYAxisHelplineStyle.toolTipText")); //$NON-NLS-1$
		btnPlotoptionsYAxisHelplineStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		Group grpPlotoptionsIntegralAreaRow = new Group(compositePlotoptionsColumn, SWT.NONE);
		grpPlotoptionsIntegralAreaRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpPlotoptionsIntegralAreaRow.setText(DICT.getString("MainWindow.grpPlotoptionsIntegralAreaRow.text")); //$NON-NLS-1$
		grpPlotoptionsIntegralAreaRow.setLayout(new GridLayout(5, false));
		
		CLabel lblPoIntegralInfo = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralInfo.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.BOLD));
		lblPoIntegralInfo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblPoIntegralInfo.setText(DICT.getString("MainWindow.lblWarning.text"));
		new Label(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		
		CLabel lblPoIntegralBordering = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralBordering.setText(DICT.getString("MainWindow.lblPoIntegralBordering.text"));
		
		comboPoIntegralBorderingFrom = new Combo(grpPlotoptionsIntegralAreaRow, SWT.READ_ONLY);
		comboPoIntegralBorderingFrom.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralBroderingFromChanged();
			}
		});
		comboPoIntegralBorderingFrom.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.comboPoIntegralBorderingFrom.access");
			}
		});
		comboPoIntegralBorderingFrom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		comboPoIntegralBorderingFrom.setItems(new String[] {});
		
		CLabel lblPoIntegralAnd = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralAnd.setText(DICT.getString("MainWindow.lblPoIntegralAnd.text"));
		
		comboPoIntegralBorderingTo = new Combo(grpPlotoptionsIntegralAreaRow, SWT.READ_ONLY);
		comboPoIntegralBorderingTo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralBorderingToChanged();
			}
		});
		comboPoIntegralBorderingTo.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWondow.comboPoIntegralBorderingTo.access");
			}
		});
		comboPoIntegralBorderingTo.setItems(new String[] {"x axis"});
		comboPoIntegralBorderingTo.select(0);
		new Label(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		
		Label lblSep = new Label(grpPlotoptionsIntegralAreaRow, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));
		lblSep.setText(DICT.getString("MainWindow.lblSep.text"));
		
		CLabel lblPoIntegralTitle = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralTitle.setText(DICT.getString("MainWindow.lblPoIntegralTitle.text")); //$NON-NLS-1$
		
		txtPoIntegralTitle = new Text(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		txtPoIntegralTitle.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralTitleChanged();
			}
		});
		txtPoIntegralTitle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.txtPoIntegralTitle.access");
			}
		});
		txtPoIntegralTitle.setToolTipText(DICT.getString("MainWindow.txtPoIntegralTitle.toolTipText")); //$NON-NLS-1$
		txtPoIntegralTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Button btnPlotoptionsIntegralStyle = new Button(grpPlotoptionsIntegralAreaRow, SWT.FLAT);
		btnPlotoptionsIntegralStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerOptionsIntegralStyleToolbox();
			}
		});
		btnPlotoptionsIntegralStyle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnPlotoptionsIntegralStyle.access");
			}
		});
		btnPlotoptionsIntegralStyle.setToolTipText(DICT.getString("MainWindow.btnPlotoptionsIntegralStyle.toolTipText")); //$NON-NLS-1$
		btnPlotoptionsIntegralStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		
		CLabel lblPoIntegralRange = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralRange.setText(DICT.getString("MainWindow.lblPoIntegralRange.text")); //$NON-NLS-1$
		
		spinnerPoIntegralRangeFrom = new Spinner(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		spinnerPoIntegralRangeFrom.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralRangeFromChanged();
			}
		});
		spinnerPoIntegralRangeFrom.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.spinnerPoIntegralRangeFrom.access");
			}
		});
		spinnerPoIntegralRangeFrom.setIncrement(1000);
		spinnerPoIntegralRangeFrom.setDigits(3);
		spinnerPoIntegralRangeFrom.setMaximum(99999);
		spinnerPoIntegralRangeFrom.setMinimum(-99999);
		
		CLabel lblPoIntegralRangeTo = new CLabel(grpPlotoptionsIntegralAreaRow, SWT.NONE);
		lblPoIntegralRangeTo.setText(DICT.getString("MainWindow.lblPoIntegralRangeTo.text")); //$NON-NLS-1$
		
		spinnerPoIntegralRangeTo = new Spinner(grpPlotoptionsIntegralAreaRow, SWT.BORDER);
		spinnerPoIntegralRangeTo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				triggerOptionsIntegralRangeToChanged();
			}
		});
		spinnerPoIntegralRangeTo.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWondow.spinnerPoIntegralRangeTo.access");
			}
		});
		spinnerPoIntegralRangeTo.setIncrement(1000);
		spinnerPoIntegralRangeTo.setDigits(3);
		spinnerPoIntegralRangeTo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		spinnerPoIntegralRangeTo.setMaximum(99999);
		spinnerPoIntegralRangeTo.setMinimum(-99999);
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
		lblPreviewOutputColumn.setText(DICT.getString("MainWindow.lblPreviewOutputColumn.text")); //$NON-NLS-1$
		
		TabFolder tabFolderPreview = new TabFolder(compositePreviewOutputColumn, SWT.NONE);
		GridData gd_tabFolderPreview = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tabFolderPreview.heightHint = 350;
		tabFolderPreview.setLayoutData(gd_tabFolderPreview);
		
		TabItem tbtmPreviewScreenView = new TabItem(tabFolderPreview, SWT.NONE);
		tbtmPreviewScreenView.setText(DICT.getString("MainWindow.tbtmPreviewScreenView.text")); //$NON-NLS-1$
		
		Composite compositePreviewScreenView = new Composite(tabFolderPreview, SWT.NONE);
		tbtmPreviewScreenView.setControl(compositePreviewScreenView);
		compositePreviewScreenView.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolderPreviewScreenView = new TabFolder(compositePreviewScreenView, SWT.NONE);
		
		TabItem tbtmPreviewScreenViewGraph = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewGraph.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tbtmPreviewScreenViewGraph.setText(DICT.getString("MainWindow.tbtmPreviewScreenViewGraph.text")); //$NON-NLS-1$
		
		browserPreviewScreenViewGraph = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewGraph.setText(DICT.getString("MainWindow.browserPreviewScreenViewGraph.text")); //$NON-NLS-1$
		tbtmPreviewScreenViewGraph.setControl(browserPreviewScreenViewGraph);
		
		TabItem tbtmPreviewScreenViewLegend = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewLegend.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/legend-16.png"));
		tbtmPreviewScreenViewLegend.setText(DICT.getString("MainWindow.tbtmPreviewScreenViewLegend.text")); //$NON-NLS-1$
		
		browserPreviewScreenViewLegend = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewLegend.setText(DICT.getString("MainWindow.browserPreviewScreenViewLegend.text")); //$NON-NLS-1$
		tbtmPreviewScreenViewLegend.setControl(browserPreviewScreenViewLegend);
		
		TabItem tbtmPreviewScreenViewDescription = new TabItem(tabFolderPreviewScreenView, SWT.NONE);
		tbtmPreviewScreenViewDescription.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/list-16.png"));
		tbtmPreviewScreenViewDescription.setText(DICT.getString("MainWindow.tbtmPreviewScreenViewDescription.text")); //$NON-NLS-1$
		
		browserPreviewScreenViewDescription = new Browser(tabFolderPreviewScreenView, SWT.NONE);
		browserPreviewScreenViewDescription.setText(DICT.getString("MainWindow.browserPreviewScreenViewDescription.text")); //$NON-NLS-1$
		tbtmPreviewScreenViewDescription.setControl(browserPreviewScreenViewDescription);
		
		TabItem tbtmPreviewPrintView = new TabItem(tabFolderPreview, SWT.NONE);
		tbtmPreviewPrintView.setText(DICT.getString("MainWindow.tbtmPreviewPrintView.text")); //$NON-NLS-1$
		
		Composite compositePreviewPrintView = new Composite(tabFolderPreview, SWT.NONE);
		tbtmPreviewPrintView.setControl(compositePreviewPrintView);
		compositePreviewPrintView.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolderPreviewPrintView = new TabFolder(compositePreviewPrintView, SWT.NONE);
		
		TabItem tbtmPreviewPrintViewGraph = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewGraph.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/graphic-16.png"));
		tbtmPreviewPrintViewGraph.setText(DICT.getString("MainWindow.tbtmPreviewPrintViewGraph.text")); //$NON-NLS-1$
		
		browserPreviewPrintViewGraph = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewGraph.setText(DICT.getString("MainWindow.browserPreviewPrintViewGraph.text")); //$NON-NLS-1$
		tbtmPreviewPrintViewGraph.setControl(browserPreviewPrintViewGraph);
		
		TabItem tbtmPreviewPrintViewLegend = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewLegend.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/legend-16.png"));
		tbtmPreviewPrintViewLegend.setText(DICT.getString("MainWindow.tbtmPreviewPrintViewLegend.text")); //$NON-NLS-1$
		
		browserPreviewPrintViewLegend = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewLegend.setText(DICT.getString("MainWindow.browserPreviewPrintViewLegend.text")); //$NON-NLS-1$
		tbtmPreviewPrintViewLegend.setControl(browserPreviewPrintViewLegend);
		
		TabItem tbtmPreviewPrintViewDescription = new TabItem(tabFolderPreviewPrintView, SWT.NONE);
		tbtmPreviewPrintViewDescription.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/list-16.png"));
		tbtmPreviewPrintViewDescription.setText(DICT.getString("MainWindow.tbtmPreviewPrintViewDescription.text")); //$NON-NLS-1$
		
		browserPreviewPrintViewDescription = new Browser(tabFolderPreviewPrintView, SWT.NONE);
		browserPreviewPrintViewDescription.setText(DICT.getString("MainWindow.browserPreviewPrintViewDescription.text")); //$NON-NLS-1$
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
		btnOutputExportExport.setText(DICT.getString("MainWindow.btnOutputExportExport.text")); //$NON-NLS-1$
		
		scrolledCompositePreviewoutputColumn.setContent(compositePreviewOutputColumn);
		scrolledCompositePreviewoutputColumn.setMinSize(compositePreviewOutputColumn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sashTopLevelColumns.setWeights(new int[] {9, 10, 12});
		
		Menu menu = new Menu(shlGsvgplott, SWT.BAR);
		shlGsvgplott.setMenuBar(menu);
		
		MenuItem mntmFileSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmFileSubmenu.setText(DICT.getString("MainWindow.mntmFileSubmenu.text")); //$NON-NLS-1$
		
		Menu file_1 = new Menu(mntmFileSubmenu);
		mntmFileSubmenu.setMenu(file_1);
		
		MenuItem mntmFileNew = new MenuItem(file_1, SWT.NONE);
		mntmFileNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuNew();
			}
		});
		mntmFileNew.setText(DICT.getString("MainWindow.mntmFileNew.text")); //$NON-NLS-1$
		
		MenuItem menuItemSeparator1 = new MenuItem(file_1, SWT.SEPARATOR);
		menuItemSeparator1.setText(DICT.getString("MainWindow.menuItemSeparator1.text")); //$NON-NLS-1$
		
		MenuItem mntmFileOpen = new MenuItem(file_1, SWT.NONE);
		mntmFileOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuOpen();
			}
		});
		mntmFileOpen.setText(DICT.getString("MainWindow.mntmFileOpen.text")); //$NON-NLS-1$
		
		MenuItem menuItemSeparator2 = new MenuItem(file_1, SWT.SEPARATOR);
		menuItemSeparator2.setText(DICT.getString("MainWindow.menuItemSeparator2.text")); //$NON-NLS-1$
		
		MenuItem mntmFileSave = new MenuItem(file_1, SWT.NONE);
		mntmFileSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuSave();
			}
		});
		mntmFileSave.setText(DICT.getString("MainWindow.mntmFileSave.text")); //$NON-NLS-1$
		
		MenuItem mntmSaveAs = new MenuItem(file_1, SWT.NONE);
		mntmSaveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuSaveAs();
			}
		});
		mntmSaveAs.setText(DICT.getString("MainWindow.mntmSaveAs.text")); //$NON-NLS-1$
		
		MenuItem mntmExtrasSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmExtrasSubmenu.setText(DICT.getString("MainWindow.mntmExtrasSubmenu.text")); //$NON-NLS-1$
		
		Menu extras_1 = new Menu(mntmExtrasSubmenu);
		mntmExtrasSubmenu.setMenu(extras_1);
		
		MenuItem mntmExtrasStyle = new MenuItem(extras_1, SWT.CASCADE);
		mntmExtrasStyle.setText(DICT.getString("MainWindow.mntmExtrasStyle.text")); //$NON-NLS-1$
		
		Menu menu_1 = new Menu(mntmExtrasStyle);
		mntmExtrasStyle.setMenu(menu_1);
		
		MenuItem mntmExtrasStyleStoreStyle = new MenuItem(menu_1, SWT.NONE);
		mntmExtrasStyleStoreStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuStoreStyle();
			}
		});
		mntmExtrasStyleStoreStyle.setText(DICT.getString("MainWindow.mntmExtrasStyleStoreStyle.text")); //$NON-NLS-1$
		
		MenuItem mntmExtrasStyleLoadStyle = new MenuItem(menu_1, SWT.NONE);
		mntmExtrasStyleLoadStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuLoadStyle();
			}
		});
		mntmExtrasStyleLoadStyle.setText(DICT.getString("MainWindow.mntmExtrasStyleLoadStyle.text")); //$NON-NLS-1$
		
		MenuItem mntmExtrasStyleResetAll = new MenuItem(menu_1, SWT.NONE);
		mntmExtrasStyleResetAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuResetStyle();
			}
		});
		mntmExtrasStyleResetAll.setText(DICT.getString("MainWindow.mntmExtrasStyleResetAll.text")); //$NON-NLS-1$
		
		MenuItem mntmExtrasCssStyleOverride = new MenuItem(extras_1, SWT.NONE);
		mntmExtrasCssStyleOverride.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerMenuCssStyleOverride();
			}
		});
		mntmExtrasCssStyleOverride.setText(DICT.getString("MainWindow.mntmExtrasCssStyleOverride.text"));
		
		MenuItem mntmHelpSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmHelpSubmenu.setText(DICT.getString("MainWindow.mntmHelpSubmenu.text")); //$NON-NLS-1$
		
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
		mntmHelpHelp.setText(DICT.getString("MainWindow.mntmHelpHelp.text")); //$NON-NLS-1$

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
		for(Entry<Group, MarkedPointsList> e : pointlistMap.entrySet()) {
			MarkedPointsList l = e.getValue();
			l.setPointScreenStyle(scrPS);
			l.setPointPrintStyle(prtPS);
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
		f.setFunctionScreenStyle(scrLS);
		f.setFunctionPrintStyle(prtLS);
		
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
		diagram.setBackgroundScreenStyle(scrAS);
		diagram.setBackgroundPrintStyle(prtAS);
		diagram.setTextScreenStyle(scrTS);
		diagram.setTextPrintStyle(prtTS);
		
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
		diagram.getXaxis().setAxisScreenStyle(scrLS);
		diagram.getXaxis().setAxisPrintStyle(prtLS);
		
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
		diagram.getXaxis().setHelplineScreenStyle(scrLS);
		diagram.getXaxis().setHelplinePrintStyle(prtLS);
		
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
		diagram.getYaxis().setAxisScreenStyle(scrLS);
		diagram.getYaxis().setAxisPrintStyle(prtLS);
		
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
		diagram.getYaxis().setHelplineScreenStyle(scrLS);
		diagram.getYaxis().setHelplinePrintStyle(prtLS);
		
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
		if(diagram.getIntegral() != null) {
			diagram.getIntegral().setAreaScreenStyle(scrAS);
			diagram.getIntegral().setAreaPrintStyle(prtAS);
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
			String s1 = s.replaceAll(",", ".");
			try {
				double n = Double.parseDouble(s1);
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
			String s1 = s.replaceAll(",", ".");
			try {
				double n = Double.parseDouble(s1);
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
		FileDialog fd = new FileDialog(shlGsvgplott, SWT.SAVE);
        fd.setText("Export");
        //fd.setFilterPath("C:/");
        String[] filterExt = { "*.*" };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        
        if(selected == null || selected.isEmpty())
        	return;
        
        String graph, legend, description;
        Map<String, String> output = operateFireSVGPlott(RenderMode.DEFAULT, false);
        graph = output.get("document");
        legend = output.get("legend");
        description = output.get("description");
        
		try {
			PrintWriter outGraph = new PrintWriter(selected + "_graph.svg");
			PrintWriter outLegend = new PrintWriter(selected + "_legend.svg");
			PrintWriter outDescription = new PrintWriter(selected + "_description.htm");
			
			outGraph.println(graph);
			outLegend.println(legend);
			outDescription.println(description);
			
			outGraph.flush();
			outGraph.close();
			outLegend.flush();
			outLegend.close();
			outDescription.flush();
			outDescription.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
        //System.out.println(selected);
		//operateGeneratePreview();
		
	}

	/**
	 * Create new file
	 */
	protected void triggerMenuNew() {
		filename = "";
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
        
        if(selected == null || selected.isEmpty()) {
        	return;
        }
        
        try {
			Diagram opened = PersistanceHelper.loadDiagramFromFile(selected);
			diagram = opened;
			operateUpdateByDiagramObject(diagram);
			filename = selected;
		} catch (Exception e) {
			MessageBox b = new MessageBox(shlGsvgplott);
			b.setText("Opening Error");
			b.setMessage("The selected file cannot be opened as a diagram.");
			b.open();
		}
	}
	
	/**
	 * Save file
	 */
	protected void triggerMenuSave() {
		if(filename == null || filename.isEmpty()) {
			triggerMenuSaveAs();
		}
		
		try {
			PersistanceHelper.storeDiagramToFile(diagram, filename);
		} catch (Exception e) {
			MessageBox b = new MessageBox(shlGsvgplott);
			b.setText("Saving Error");
			b.setMessage("The diagram cannot be stored correctly.");
			b.open();
		}
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
        
		if(selected == null || selected.isEmpty())
			return;
		
		try {
			PersistanceHelper.storeDiagramToFile(diagram, selected);
			filename = selected;
		} catch (Exception e) {
			MessageBox b = new MessageBox(shlGsvgplott);
			b.setText("Saving Error");
			b.setMessage("The diagram cannot be stored correctly.");
			b.open();
		}
		
	}
	
	/**
	 * Store current style
	 */
	protected void triggerMenuStoreStyle() {
		/*StoreStyleDialog sd = new StoreStyleDialog(shlGsvgplott, 0);
		sd.open();*/
		
		FileDialog fd = new FileDialog(shlGsvgplott, SWT.SAVE);
        fd.setText("Store Style");
        //fd.setFilterPath("C:/");
        String[] filterExt = { "*.gss", "*.xml", "*.*" };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        
		if(selected == null || selected.isEmpty())
			return;
		
		try {
			PersistanceHelper.storeStyleToFile(diagram, selected);
		} catch (Exception e) {
			MessageBox b = new MessageBox(shlGsvgplott);
			b.setText("Saving Error");
			b.setMessage("The style cannot be stored correctly.");
			b.open();
		}
	}
	
	/**
	 * Load Style
	 */
	protected void triggerMenuLoadStyle() {
		/*StyleManagerDialog sd = new StyleManagerDialog(shlGsvgplott, 0);
		sd.open();*/
		
		FileDialog fd = new FileDialog(shlGsvgplott, SWT.OPEN);
        fd.setText("Load Style");
        //fd.setFilterPath("C:/");
        String[] filterExt = { "*.gss", "*.xml", "*.*" };
        fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        
        if(selected == null || selected.isEmpty()) {
        	return;
        }
        
        try {
			Diagram opened = PersistanceHelper.loadStyleFromFile(selected);
			PersistanceHelper.applyStyleToDiagram(diagram, opened);
		} catch (Exception e) {
			MessageBox b = new MessageBox(shlGsvgplott);
			b.setText("Opening Error");
			b.setMessage("The selected file cannot be opened as a style setting.");
			b.open();
		}
	}

	/**
	 * Reset Style
	 */
	protected void triggerMenuResetStyle() {
		diagram.setBackgroundScreenStyle(null);
		diagram.setBackgroundPrintStyle(null);
		diagram.setTextScreenStyle(null);
		diagram.setTextPrintStyle(null);
		diagram.setGridScreenStyle(null);
		diagram.setGridPrintStyle(null);
		
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
		lblDataColumn.setText(DICT.getString("MainWindow.lblDataColumn.text")); //$NON-NLS-1$
		
		CLabel lblDataRowFunctions = new CLabel(compositeDataColumn, SWT.NONE);
		lblDataRowFunctions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDataRowFunctions.setText(DICT.getString("MainWindow.lblDataRowFunctions.text")); //$NON-NLS-1$
		
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
		btnDataColumnFunctionsAddFunction.setText(DICT.getString("MainWindow.btnDataColumnFunctionsAddFunction.text")); //$NON-NLS-1$
		btnDataColumnFunctionsAddFunction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataAddFunction();
			}
		});
		
		// d) fixed area (labels)
		
		CLabel lblDataRowMarkedpoints = new CLabel(compositeDataColumn, SWT.NONE);
		lblDataRowMarkedpoints.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblDataRowMarkedpoints.setText(DICT.getString("MainWindow.lblDataRowMarkedpoints.text")); //$NON-NLS-1$
		
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
		btnDataColumnListsAddPointList.setText(DICT.getString("MainWindow.btnDataColumnListsAddPointList.text")); //$NON-NLS-1$
		btnDataColumnListsAddPointList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataAddPointList();
			}
		});
		
		Button btnDataColumnPointListStyle = new Button(compositeDataColumn, SWT.FLAT);
		btnDataColumnPointListStyle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnDataColumnPointListStyle.setToolTipText(DICT.getString("MainWindow.btnDataColumnPointListStyle.toolTipText")); //$NON-NLS-1$
		btnDataColumnPointListStyle.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		btnDataColumnPointListStyle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataPointListCommonStyleToolbox();
			}
			
		});
		btnDataColumnPointListStyle.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDataColumnPointListStyle.access");
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
		String name = DICT.getString("MainWindow.grpDataRowFunction1.text") + String.valueOf(functionIDcounter);
		
		Group grpDataRowFunction1 = new Group(temporaryContainer, SWT.NONE);
		grpDataRowFunction1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpDataRowFunction1.setText(name);
		grpDataRowFunction1.setLayout(new GridLayout(3, false));
		
		CLabel lblDRFTitle1 = new CLabel(grpDataRowFunction1, SWT.NONE);
		lblDRFTitle1.setText(DICT.getString("MainWindow.lblDRFTitle1.text"));
		
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
		txtDRFTitle1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.txtDRFTitle1.access") + " (" + name + ")";
			}
		});
		
		Button btnDRFStyle1 = new Button(grpDataRowFunction1, SWT.FLAT);
		btnDRFStyle1.setToolTipText(DICT.getString("MainWindow.btnDRFStyle1.toolTipText"));
		btnDRFStyle1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/edit-16.png"));
		btnDRFStyle1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataFunctionStyleToolbox(grpDataRowFunction1);
			}
		});
		btnDRFStyle1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDRFStyle1.access") + " (" + name + ")";
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
				func = func.replaceAll(",", ".");
				f.setFunction(func);
				
			}
		});
		txtDRFfunc1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.txtDRFfunc1.access") + " (" + name + ")";
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
		btnDRFMoveUp1.setToolTipText(DICT.getString("MainWindow.btnDRFMoveUp1.toolTipText"));
		btnDRFMoveUp1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnDRFMoveUp1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/up-16.png"));
		btnDRFMoveUp1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataMoveUpFunction(grpDataRowFunction1);
			}
		});
		btnDRFMoveUp1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDRFMoveUp1.access") + " (" + name + ")";
			}
		});
		
		Button btnDRFMoveDown1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFMoveDown1.setToolTipText(DICT.getString("MainWindow.btnDRFMoveDown1.toolTipText"));
		btnDRFMoveDown1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnDRFMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-16.png"));
		btnDRFMoveDown1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataMoveDownFunction(grpDataRowFunction1);
			}
		});
		btnDRFMoveDown1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDRFMoveDown1.access") + " (" + name + ")";
			}
		});
		
		Button btnDRFRemove1 = new Button(compositeDRFControls1, SWT.FLAT);
		btnDRFRemove1.setToolTipText(DICT.getString("MainWindow.btnDRFRemove1.toolTipText"));
		GridData gd_btnDRFRemove1 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_btnDRFRemove1.horizontalIndent = 5;
		btnDRFRemove1.setLayoutData(gd_btnDRFRemove1);
		btnDRFRemove1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/remove-16.png"));
		btnDRFRemove1.setText(DICT.getString("MainWindow.btnDRFRemove1.text"));
		btnDRFRemove1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataRemoveFunction(grpDataRowFunction1);
			}
		});
		btnDRFRemove1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDRFRemove1.access") + " (" + name + ")";
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
		String name = DICT.getString("MainWindow.grpDataRowMarkedpointsPointList.text") + String.valueOf(pointlistIDcounter);
		
		Group grpDataRowMarkedpointsPointList = new Group(temporaryContainer, SWT.NONE);
		grpDataRowMarkedpointsPointList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpDataRowMarkedpointsPointList.setLayout(new GridLayout(4, false));
		grpDataRowMarkedpointsPointList.setText(name);
		
		CLabel lblDRMTitle1 = new CLabel(grpDataRowMarkedpointsPointList, SWT.NONE);
		lblDRMTitle1.setText(DICT.getString("MainWindow.lblDRMTitle1.text"));
		
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
		txtDRMtitle1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.txtDRMtitle1.access") + " (" + name + ")";
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
		tableDRMlist1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.tableDRMlist1.access") + " (" + name + ")";
			}
		});
		
		TableColumn tblclmnDRMXValue1 = new TableColumn(tableDRMlist1, SWT.NONE);
		tblclmnDRMXValue1.setWidth(75);
		tblclmnDRMXValue1.setText(DICT.getString("MainWindow.tblclmnDRMXValue1.text"));
		
		TableColumn tblclmnDRMYValue1 = new TableColumn(tableDRMlist1, SWT.NONE);
		tblclmnDRMYValue1.setWidth(75);
		tblclmnDRMYValue1.setText(DICT.getString("MainWindow.tblclmnDRMYValue1.text"));
		
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
		btnDRMlistAdd1.setText(DICT.getString("MainWindow.btnDRMlistAdd1.text"));
		btnDRMlistAdd1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataPointListAddPoint(tableDRMlist1);
			}
		});
		btnDRMlistAdd1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDRMlistAdd1.access") + " (" + name + ")";
			}
		});
		
		Button btnDRMlistRemove1 = new Button(compositeDRMlistControls1, SWT.NONE);
		btnDRMlistRemove1.setText(DICT.getString("MainWindow.btnDRMlistRemove1.text"));
		btnDRMlistRemove1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataPointListRemovePoint(tableDRMlist1);
			}
		});
		btnDRMlistRemove1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDRMlistRemove1.access") + " (" + name + ")";
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
		btnDRMMoveUp1.setToolTipText(DICT.getString("MainWindow.btnDRMMoveUp1.toolTipText"));
		btnDRMMoveUp1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/up-16.png"));
		btnDRMMoveUp1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataMoveUpPointList(grpDataRowMarkedpointsPointList);
			}
		});
		btnDRMMoveUp1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDRMMoveUp1.access") + " (" + name + ")";
			}
		});
		
		Button btnDRMMoveDown1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMMoveDown1.setToolTipText(DICT.getString("MainWindow.btnDRMMoveDown1.toolTipText"));
		btnDRMMoveDown1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/down-16.png"));
		btnDRMMoveDown1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataMoveDownPointList(grpDataRowMarkedpointsPointList);
			}
		});
		btnDRMMoveDown1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDRMMoveDown1.access") + " (" + name + ")";
			}
		});
		
		Button btnDRMRemove1 = new Button(compositeDRMControls1, SWT.FLAT);
		btnDRMRemove1.setToolTipText(DICT.getString("MainWindow.btnDRMRemove1.toolTipText"));
		GridData gd_btnDRMRemove1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDRMRemove1.horizontalIndent = 5;
		btnDRMRemove1.setLayoutData(gd_btnDRMRemove1);
		btnDRMRemove1.setImage(SWTResourceManager.getImage(MainWindow.class, "/de/tudresden/inf/gsvgplott/ui/icons/remove-16.png"));
		btnDRMRemove1.setText(DICT.getString("MainWindow.btnDRMRemove1.text"));
		btnDRMRemove1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				triggerDataRemovePointList(grpDataRowMarkedpointsPointList);
			}
		});
		btnDRMRemove1.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = DICT.getString("MainWindow.btnDRMRemove1.access") + " (" + name + ")";
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
		
		if(!browserPreviewScreenViewGraph.getText().equals(previewScreen.get("document")))
			this.browserPreviewScreenViewGraph.setText(previewScreen.get("document"));
		if(!browserPreviewScreenViewDescription.getText().equals(previewScreen.get("description")))
			this.browserPreviewScreenViewDescription.setText(previewScreen.get("description"));
		if(!browserPreviewScreenViewLegend.getText().equals(previewScreen.get("legend")))
			this.browserPreviewScreenViewLegend.setText(previewScreen.get("legend"));
		
		if(!browserPreviewPrintViewGraph.getText().equals(previewPrint.get("document")))
			this.browserPreviewPrintViewGraph.setText(previewPrint.get("document"));
		if(!browserPreviewPrintViewDescription.getText().equals(previewPrint.get("description")))
			this.browserPreviewPrintViewDescription.setText(previewPrint.get("description"));
		if(!browserPreviewPrintViewLegend.getText().equals(previewPrint.get("legend")))
			this.browserPreviewPrintViewLegend.setText(previewPrint.get("legend"));
		
		//for testing purposes only:
		//System.out.println(previewPrint.get("document"));
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
				helplines += Double.toString(h.getIntersection()) + " ";
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
				helplines += Double.toString(h.getIntersection()) + " ";
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
	
	protected void operateSetAccessabilityTexts() {
		
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
		
		String css = SvgPlotHelper.generateCss(diagram);
		
		String modifierM = "svg";
		Map<String, String> modifierV = new HashMap<String, String>();
		modifierV.put("-ms-transform-origin","0 0");
		modifierV.put("-moz-transform-origin","0 0");
		modifierV.put("-webkit-transform-origin","0 0");
		modifierV.put("-o-transform-origin","0 0");
		modifierV.put("transform-origin","0 0");
		modifierV.put("-ms-transform","scale(0.5)");
		modifierV.put("-moz-transform","scale(0.5)");
		modifierV.put("-webkit-transform","scale(0.5)");
		modifierV.put("-o-transform","scale(0.5)");
		modifierV.put("transform","scale(0.5)");
		String mofivierMV = SvgPlotHelper.formatBlock(modifierM, modifierV);
		switch (mode) {
		case DEFAULT:
			// do nothing special here
			break;
		case UNIPRINT:
			// create preview for print view
			css = css.replace("@media print", "@media screen");
			css = mofivierMV + css;
			break;
		case UNISCREEN:
			// create preview for screen view
			css = mofivierMV + css;
			break;
		default:
			break;
		}
		plotter.setCss(css);
		
		List<tud.tangram.svgplot.plotting.Function> functions = 
				new ArrayList<tud.tangram.svgplot.plotting.Function>();
		for(Function f : diagram.getFunctions()) {
			tud.tangram.svgplot.plotting.Function func = 
					new tud.tangram.svgplot.plotting.Function(f.getTitle(), f.getFunction().replaceAll(",", "."));
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
		
		//FIXME: Function selection might by faulty
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
