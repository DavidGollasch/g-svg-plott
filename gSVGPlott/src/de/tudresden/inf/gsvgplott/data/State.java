package de.tudresden.inf.gsvgplott.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author David Gollasch
 *
 */

public class State {
	private String title;
	private int size;
	private boolean showGrid;
	private XAxis xaxis;
	private YAxis yaxis;
	private List<Function> functions;
	private List<MarkedPointList> markedPointLists;
	private Integral integral;
	
	/**
	 * @param title
	 * @param size
	 * @param showGrid
	 * @param xaxis
	 * @param yaxis
	 */
	public State(String title, int size, boolean showGrid, XAxis xaxis,
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
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "State [title=" + title + ", size=" + size + ", showGrid="
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
		State other = (State) obj;
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
}
