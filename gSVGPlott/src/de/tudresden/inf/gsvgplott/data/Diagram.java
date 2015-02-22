package de.tudresden.inf.gsvgplott.data;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.gsvgplott.data.style.AreaStyle;
import de.tudresden.inf.gsvgplott.data.style.LineStyle;
import de.tudresden.inf.gsvgplott.data.style.OverrideStyle;
import de.tudresden.inf.gsvgplott.data.style.TextStyle;

/**
 * 
 * @author David Gollasch
 *
 */

public class Diagram {
	private String title;
	private int size;
	private boolean showGrid;
	private XAxis xaxis;
	private YAxis yaxis;
	private List<Function> functions;
	private List<MarkedPointList> markedPointLists;
	private Integral integral;
	
	private LineStyle gridScreenStyle;
	private AreaStyle backgroundScreenStyle;
	private TextStyle textScreenStyle;
	
	private LineStyle gridPrintStyle;
	private AreaStyle backgroundPrintStyle;
	private TextStyle textPrintStyle;
	
	private OverrideStyle overrideStyle;
	
	/**
	 * @param title
	 * @param size
	 * @param showGrid
	 * @param xaxis
	 * @param yaxis
	 */
	public Diagram(String title, int size, boolean showGrid, XAxis xaxis,
			YAxis yaxis) {
		super();
		this.title = title;
		this.size = size;
		this.showGrid = showGrid;
		this.xaxis = xaxis;
		this.yaxis = yaxis;
		this.functions = new ArrayList<Function>();
		this.markedPointLists = new ArrayList<MarkedPointList>();
		this.integral = null;
		
		this.gridScreenStyle = null;
		this.backgroundScreenStyle = null;
		this.textScreenStyle = null;
		
		this.gridPrintStyle = null;
		this.backgroundPrintStyle = null;
		this.textPrintStyle = null;
		
		this.overrideStyle = null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Diagram [title=" + title + ", size=" + size + ", showGrid="
				+ showGrid + ", xaxis=" + xaxis + ", yaxis=" + yaxis
				+ ", functions=" + functions + ", markedPointLists="
				+ markedPointLists + ", integral=" + integral + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((functions == null) ? 0 : functions.hashCode());
		result = prime * result
				+ ((integral == null) ? 0 : integral.hashCode());
		result = prime
				* result
				+ ((markedPointLists == null) ? 0 : markedPointLists.hashCode());
		result = prime * result + (showGrid ? 1231 : 1237);
		result = prime * result + size;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((xaxis == null) ? 0 : xaxis.hashCode());
		result = prime * result + ((yaxis == null) ? 0 : yaxis.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Diagram other = (Diagram) obj;
		if (functions == null) {
			if (other.functions != null)
				return false;
		} else if (!functions.equals(other.functions))
			return false;
		if (integral == null) {
			if (other.integral != null)
				return false;
		} else if (!integral.equals(other.integral))
			return false;
		if (markedPointLists == null) {
			if (other.markedPointLists != null)
				return false;
		} else if (!markedPointLists.equals(other.markedPointLists))
			return false;
		if (showGrid != other.showGrid)
			return false;
		if (size != other.size)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (xaxis == null) {
			if (other.xaxis != null)
				return false;
		} else if (!xaxis.equals(other.xaxis))
			return false;
		if (yaxis == null) {
			if (other.yaxis != null)
				return false;
		} else if (!yaxis.equals(other.yaxis))
			return false;
		return true;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the showGrid
	 */
	public boolean isShowGrid() {
		return showGrid;
	}
	/**
	 * @param showGrid the showGrid to set
	 */
	public void setShowGrid(boolean showGrid) {
		this.showGrid = showGrid;
	}
	/**
	 * @return the xaxis
	 */
	public XAxis getXaxis() {
		return xaxis;
	}
	/**
	 * @param xaxis the xaxis to set
	 */
	public void setXaxis(XAxis xaxis) {
		this.xaxis = xaxis;
	}
	/**
	 * @return the yaxis
	 */
	public YAxis getYaxis() {
		return yaxis;
	}
	/**
	 * @param yaxis the yaxis to set
	 */
	public void setYaxis(YAxis yaxis) {
		this.yaxis = yaxis;
	}
	/**
	 * @return the functions
	 */
	public List<Function> getFunctions() {
		return functions;
	}
	/**
	 * @param functions the functions to set
	 */
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	/**
	 * @return the markedPointLists
	 */
	public List<MarkedPointList> getMarkedPointLists() {
		return markedPointLists;
	}
	/**
	 * @param markedPointLists the markedPointLists to set
	 */
	public void setMarkedPointLists(List<MarkedPointList> markedPointLists) {
		this.markedPointLists = markedPointLists;
	}
	/**
	 * @return the integral
	 */
	public Integral getIntegral() {
		return integral;
	}
	/**
	 * @param integral the integral to set
	 */
	public void setIntegral(Integral integral) {
		this.integral = integral;
	}

	/**
	 * @return the gridScreenStyle
	 */
	public LineStyle getGridScreenStyle() {
		return gridScreenStyle;
	}

	/**
	 * @param gridScreenStyle the gridScreenStyle to set
	 */
	public void setGridScreenStyle(LineStyle gridScreenStyle) {
		this.gridScreenStyle = gridScreenStyle;
	}

	/**
	 * @return the backgroundScreenStyle
	 */
	public AreaStyle getBackgroundScreenStyle() {
		return backgroundScreenStyle;
	}

	/**
	 * @param backgroundScreenStyle the backgroundScreenStyle to set
	 */
	public void setBackgroundScreenStyle(AreaStyle backgroundScreenStyle) {
		this.backgroundScreenStyle = backgroundScreenStyle;
	}

	/**
	 * @return the textScreenStyle
	 */
	public TextStyle getTextScreenStyle() {
		return textScreenStyle;
	}

	/**
	 * @param textScreenStyle the textScreenStyle to set
	 */
	public void setTextScreenStyle(TextStyle textScreenStyle) {
		this.textScreenStyle = textScreenStyle;
	}

	/**
	 * @return the gridPrintStyle
	 */
	public LineStyle getGridPrintStyle() {
		return gridPrintStyle;
	}

	/**
	 * @param gridPrintStyle the gridPrintStyle to set
	 */
	public void setGridPrintStyle(LineStyle gridPrintStyle) {
		this.gridPrintStyle = gridPrintStyle;
	}

	/**
	 * @return the backgroundPrintStyle
	 */
	public AreaStyle getBackgroundPrintStyle() {
		return backgroundPrintStyle;
	}

	/**
	 * @param backgroundPrintStyle the backgroundPrintStyle to set
	 */
	public void setBackgroundPrintStyle(AreaStyle backgroundPrintStyle) {
		this.backgroundPrintStyle = backgroundPrintStyle;
	}

	/**
	 * @return the textPrintStyle
	 */
	public TextStyle getTextPrintStyle() {
		return textPrintStyle;
	}

	/**
	 * @param textPrintStyle the textPrintStyle to set
	 */
	public void setTextPrintStyle(TextStyle textPrintStyle) {
		this.textPrintStyle = textPrintStyle;
	}

	/**
	 * @return the overrideStyle
	 */
	public OverrideStyle getOverrideStyle() {
		return overrideStyle;
	}

	/**
	 * @param overrideStyle the overrideStyle to set
	 */
	public void setOverrideStyle(OverrideStyle overrideStyle) {
		this.overrideStyle = overrideStyle;
	}
}
