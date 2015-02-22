/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

import de.tudresden.inf.gsvgplott.data.style.LineStyle;

/**
 * @author David Gollasch
 *
 */
public class Function {
	private String title;
	private String function;
	
	private LineStyle functionScreenStyle;
	private LineStyle functionPrintStyle;
	
	/**
	 * @param title
	 * @param function
	 */
	public Function(String title, String function) {
		super();
		this.title = title;
		this.function = function;
		
		this.functionScreenStyle = null;
		this.functionPrintStyle = null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Function [title=" + title + ", function=" + function + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((function == null) ? 0 : function.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Function other = (Function) obj;
		if (function == null) {
			if (other.function != null)
				return false;
		} else if (!function.equals(other.function))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
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
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}
	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * @return the functionScreenStyle
	 */
	public LineStyle getFunctionScreenStyle() {
		return functionScreenStyle;
	}

	/**
	 * @param functionScreenStyle the functionScreenStyle to set
	 */
	public void setFunctionScreenStyle(LineStyle functionScreenStyle) {
		this.functionScreenStyle = functionScreenStyle;
	}

	/**
	 * @return the functionPrintStyle
	 */
	public LineStyle getFunctionPrintStyle() {
		return functionPrintStyle;
	}

	/**
	 * @param functionPrintStyle the functionPrintStyle to set
	 */
	public void setFunctionPrintStyle(LineStyle functionPrintStyle) {
		this.functionPrintStyle = functionPrintStyle;
	}
}
