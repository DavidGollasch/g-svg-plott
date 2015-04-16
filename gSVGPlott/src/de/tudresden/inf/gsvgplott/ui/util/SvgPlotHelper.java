/**
 * 
 */
package de.tudresden.inf.gsvgplott.ui.util;

import java.util.Map;
import java.util.Map.Entry;

import de.tudresden.inf.gsvgplott.data.Diagram;
import de.tudresden.inf.gsvgplott.data.Function;
import de.tudresden.inf.gsvgplott.data.MarkedPointsList;
import de.tudresden.inf.gsvgplott.data.style.AreaStyle;
import de.tudresden.inf.gsvgplott.data.style.LineStyle;
import de.tudresden.inf.gsvgplott.data.style.OverrideStyle;
import de.tudresden.inf.gsvgplott.data.style.PointStyle;
import de.tudresden.inf.gsvgplott.data.style.TextStyle;

/**
 * @author David Gollasch
 *
 */
public class SvgPlotHelper {
	
	/**
	 * Builds appropriate CSS formatted style information according to a diagram object.
	 * @param diagram
	 * @return
	 */
	public static String generateCss(Diagram diagram) {
		String result = "";
		
		// general css (text style, background)
		AreaStyle sGas = diagram.getBackgroundScreenStyle();
		AreaStyle pGas = diagram.getBackgroundPrintStyle();
		TextStyle sGts = diagram.getTextScreenStyle();
		TextStyle pGts = diagram.getTextPrintStyle();
		//TODO: formulate general styling
		
		// x axis style
		LineStyle sXls = diagram.getXaxis().getAxisScreenStyle();
		LineStyle pXls = diagram.getXaxis().getAxisPrintStyle();
		//TODO: formulate x axis styling
		
		// y axis style
		LineStyle sYls = diagram.getYaxis().getAxisScreenStyle();
		LineStyle pYls = diagram.getYaxis().getAxisPrintStyle();
		//TODO: formulate y axis styling
		
		// x-helplines style
		LineStyle sXHls = diagram.getXaxis().getHelplineScreenStyle();
		LineStyle pXHls = diagram.getXaxis().getHelplinePrintStyle();
		//TODO: formulate x helplines styling
		
		// y-helplines style
		LineStyle sYHls = diagram.getYaxis().getHelplineScreenStyle();
		LineStyle pYHls = diagram.getYaxis().getHelplinePrintStyle();
		//TODO: formulate y helplines styling
		
		// integral style
		if(diagram.getIntegral() != null) {
			AreaStyle sIas = diagram.getIntegral().getAreaScreenStyle();
			AreaStyle pIas = diagram.getIntegral().getAreaPrintStyle();
			//TODO: formulate integral styling
		}
		
		// functions style
		for(Function f : diagram.getFunctions()) {
			LineStyle sFls = f.getFunctionScreenStyle();
			LineStyle pFls = f.getFunctionPrintStyle();
			//TODO: formulate function styling
		}
		
		// point lists style
		for(MarkedPointsList l : diagram.getMarkedPointLists()) {
			PointStyle sLps = l.getPointScreenStyle();
			PointStyle pLps = l.getPointPrintStyle();
			//TODO: formulate point list styling
		}
		// style override
		OverrideStyle Os = diagram.getOverrideStyle();
		//TODO: formulate override styling
		
		return result;
	}
	
	private static String formatBlock(String modifier, Map<String,String> values) {
		/*
		 * modifier {
		 *   key: value;
		 *   key: value;
		 * }
		 */
		
		String result = "";
		
		result += modifier;
		result += " {";
		
		for(Entry<String, String> e : values.entrySet()) {
			String key = e.getKey();
			String value = e.getValue();
			
			result += key;
			result += ": ";
			result += value;
			result += "; ";
		}
		
		result += "} ";
		return result;
	}
}
