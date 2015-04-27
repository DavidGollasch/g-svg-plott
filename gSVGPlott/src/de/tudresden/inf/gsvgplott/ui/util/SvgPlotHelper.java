/**
 * 
 */
package de.tudresden.inf.gsvgplott.ui.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import de.tudresden.inf.gsvgplott.data.style.palettes.ColorPalette;

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
		String sGasMV = "";
		if(sGas != null) {
			String sGasM = "#background";
			Map<String, String> sGasV = new HashMap<String, String>();
			sGasV.put("fill", formatColor(sGas.getColor()));
			sGasMV = formatBlock(sGasM, sGasV);
		} else {
			sGas = new AreaStyle(ColorPalette.SNOW);
		}
		
		AreaStyle pGas = diagram.getBackgroundPrintStyle();
		String pGasMV = "";
		if(pGas != null) {
			String pGasM = "#background";
			Map<String, String> pGasV = new HashMap<String, String>();
			pGasV.put("fill", formatColor(pGas.getColor()));
			pGasMV = formatBlock(pGasM, pGasV);
		} else {
			pGas = new AreaStyle(ColorPalette.SNOW);
		}
		
		TextStyle sGts = diagram.getTextScreenStyle();
		String sGtsMV = "";
		if(sGts != null) {
			String sGtsM = "text";
			Map<String, String> sGtsV = new HashMap<String, String>();
			sGtsV.put("font-family", sGts.getFont() + ", sans-serif");
			sGtsV.put("font-size", String.valueOf(sGts.getSize()));
			sGtsV.put("fill", formatColor(sGts.getColor()));
			sGtsV.put("stroke", "none");
			if(sGts.isItalic()) {
				sGtsV.put("font-style", "italic");
			}
			if(sGts.isBold()) {
				sGtsV.put("font-weight", "bold");
			}
			sGtsMV = formatBlock(sGtsM, sGtsV);
		}
		
		TextStyle pGts = diagram.getTextPrintStyle();
		String pGtsMV = "";
		if(pGts != null) {
			String pGtsM = "text";
			Map<String, String> pGtsV = new HashMap<String, String>();
			pGtsV.put("font-family", pGts.getFont() + ", sans-serif");
			pGtsV.put("font-size", String.valueOf(pGts.getSize()));
			pGtsV.put("fill", formatColor(pGts.getColor()));
			pGtsV.put("stroke", "none");
			if(pGts.isItalic()) {
				pGtsV.put("font-style", "italic");
			}
			if(pGts.isBold()) {
				pGtsV.put("font-weight", "bold");
			}
			pGtsMV = formatBlock(pGtsM, pGtsV);
		}
		
		String sGgsMV = "";
		String pGgsMV = "";
		if(diagram.isShowGrid()) {
			String GgsM = "#grid";
			Map<String, String> GgsV = new HashMap<String, String>();
			GgsV.put("stroke", "#777777");
			sGgsMV = formatBlock(GgsM, GgsV);
			pGgsMV = sGgsMV;
		} else {
			String GgsM = "#grid";
			Map<String, String> sGgsV = new HashMap<String, String>();
			sGgsV.put("stroke", formatColor(sGas.getColor()));
			sGgsMV = formatBlock(GgsM, sGgsV);
			Map<String, String> pGgsV = new HashMap<String, String>();
			pGgsV.put("stroke", formatColor(pGas.getColor()));
			pGgsMV = formatBlock(GgsM, pGgsV);
		}
		
		
		// x axis style
		LineStyle sXls = diagram.getXaxis().getAxisScreenStyle();
		String sXlsMV = "";
		if(sXls != null) {
			String sXlsM = "#x-axis, #x-axis-arrow, #x-tics";
			Map<String, String> sXlsV = new HashMap<String, String>();
			sXlsV.put("stroke", formatColor(sXls.getColor()));
			sXlsV.put("fill", "transparent");
			sXlsV.put("stroke-width", String.valueOf(sXls.getWidth()));
			if(sXls.getStyle().isEmpty()) {
				sXlsV.put("stroke-dasharray", "none");
			} else {
				sXlsV.put("stroke-dasharray", sXls.getStyle());
			}
			sXlsMV = formatBlock(sXlsM, sXlsV);
		}
		
		LineStyle pXls = diagram.getXaxis().getAxisPrintStyle();
		String pXlsMV = "";
		if(pXls != null) {
			String pXlsM = "#x-axis, #x-axis-arrow, #x-tics";
			Map<String, String> pXlsV = new HashMap<String, String>();
			pXlsV.put("stroke", formatColor(sXls.getColor()));
			pXlsV.put("fill", "transparent");
			pXlsV.put("stroke-width", String.valueOf(pXls.getWidth()));
			if(pXls.getStyle().isEmpty()) {
				pXlsV.put("stroke-dasharray", "none");
			} else {
				pXlsV.put("stroke-dasharray", pXls.getStyle());
			}
			pXlsMV = formatBlock(pXlsM, pXlsV);
		}
		
		
		// y axis style
		LineStyle sYls = diagram.getYaxis().getAxisScreenStyle();
		String sYlsMV = "";
		if(sYls != null) {
			String sYlsM = "#y-axis, #y-axis-arrow, #y-tics";
			Map<String, String> sYlsV = new HashMap<String, String>();
			sYlsV.put("stroke", formatColor(sYls.getColor()));
			sYlsV.put("fill", "transparent");
			sYlsV.put("stroke-width", String.valueOf(sYls.getWidth()));
			if(sYls.getStyle().isEmpty()) {
				sYlsV.put("stroke-dasharray", "none");
			} else {
				sYlsV.put("stroke-dasharray", sYls.getStyle());
			}
			sYlsMV = formatBlock(sYlsM, sYlsV);
		}
		
		LineStyle pYls = diagram.getYaxis().getAxisPrintStyle();
		String pYlsMV = "";
		if(pYls != null) {
			String pYlsM = "#y-axis, #y-axis-arrow, #y-tics";
			Map<String, String> pYlsV = new HashMap<String, String>();
			pYlsV.put("stroke", formatColor(pYls.getColor()));
			pYlsV.put("fill", "transparent");
			pYlsV.put("stroke-width", String.valueOf(pYls.getWidth()));
			if(pYls.getStyle().isEmpty()) {
				pYlsV.put("stroke-dasharray", "none");
			} else {
				pYlsV.put("stroke-dasharray", pYls.getStyle());
			}
			pYlsMV = formatBlock(pYlsM, pYlsV);
		}
		
		
		// x-helplines style
		LineStyle sXHls = diagram.getXaxis().getHelplineScreenStyle();
		String sXHlsMV = "";
		if(sXHls != null) {
			String sXHlsM = "#x-reference-lines";
			Map<String, String> sXHlsV = new HashMap<String, String>();
			sXHlsV.put("stroke", formatColor(sXHls.getColor()));
			sXHlsV.put("fill", "transparent");
			sXHlsV.put("stroke-width", String.valueOf(sXHls.getWidth()));
			if(sXHls.getStyle().isEmpty()) {
				sXHlsV.put("stroke-dasharray", "none");
			} else {
				sXHlsV.put("stroke-dasharray", sXHls.getStyle());
			}
			sXHlsMV = formatBlock(sXHlsM, sXHlsV);
		}
		
		LineStyle pXHls = diagram.getXaxis().getHelplinePrintStyle();
		String pXHlsMV = "";
		if(pXHls != null) {
			String pXHlsM = "#x-reference-lines";
			Map<String, String> pXHlsV = new HashMap<String, String>();
			pXHlsV.put("stroke", formatColor(pXHls.getColor()));
			pXHlsV.put("fill", "transparent");
			pXHlsV.put("stroke-width", String.valueOf(pXHls.getWidth()));
			if(pXHls.getStyle().isEmpty()) {
				pXHlsV.put("stroke-dasharray", "none");
			} else {
				pXHlsV.put("stroke-dasharray", pXHls.getStyle());
			}
			pXHlsMV = formatBlock(pXHlsM, pXHlsV);
		}
		
		
		// y-helplines style
		LineStyle sYHls = diagram.getYaxis().getHelplineScreenStyle();
		String sYHlsMV = "";
		if(sYHls != null) {
			String sYHlsM = "#y-reference-lines";
			Map<String, String> sYHlsV = new HashMap<String, String>();
			sYHlsV.put("stroke", formatColor(sYHls.getColor()));
			sYHlsV.put("fill", "transparent");
			sYHlsV.put("stroke-width", String.valueOf(sYHls.getWidth()));
			if(sYHls.getStyle().isEmpty()) {
				sYHlsV.put("stroke-dasharray", "none");
			} else {
				sYHlsV.put("stroke-dasharray", sYHls.getStyle());
			}
			sYHlsMV = formatBlock(sYHlsM, sYHlsV);
		}
		
		LineStyle pYHls = diagram.getYaxis().getHelplinePrintStyle();
		String pYHlsMV = "";
		if(pYHls != null) {
			String pYHlsM = "#y-reference-lines";
			Map<String, String> pYHlsV = new HashMap<String, String>();
			pYHlsV.put("stroke", formatColor(pYHls.getColor()));
			pYHlsV.put("fill", "transparent");
			pYHlsV.put("stroke-width", String.valueOf(pYHls.getWidth()));
			if(pYHls.getStyle().isEmpty()) {
				pYHlsV.put("stroke-dasharray", "none");
			} else {
				pYHlsV.put("stroke-dasharray", pYHls.getStyle());
			}
			pYHlsMV = formatBlock(pYHlsM, pYHlsV);
		}
		
		// integral style
		String sIasMV = "";
		String pIasMV = "";
		if(diagram.getIntegral() != null) {
			AreaStyle sIas = diagram.getIntegral().getAreaScreenStyle();
			if(sIas != null) {
				String sIasM = ".integral-1";
				Map<String, String> sIasV = new HashMap<String, String>();
				sIasV.put("fill", formatColor(sIas.getColor()));
				sIasMV = formatBlock(sIasM, sIasV);
			}
			
			AreaStyle pIas = diagram.getIntegral().getAreaPrintStyle();
			if(pIas != null) {
				String pIasM = ".integral-1";
				Map<String, String> pIasV = new HashMap<String, String>();
				pIasV.put("fill", formatColor(pIas.getColor()));
				pIasMV = formatBlock(pIasM, pIasV);
			}
		}
		
		// functions style
		List<String> sFlsMVs = new ArrayList<String>();
		List<String> pFlsMVs = new ArrayList<String>();
		for(Function f : diagram.getFunctions()) {
			LineStyle sFls = f.getFunctionScreenStyle();
			if(sFls != null) {
				String sFlsM = "#plot-" + String.valueOf(diagram.getFunctions().indexOf(f) + 1);
				Map<String, String> sFlsV = new HashMap<String, String>();
				sFlsV.put("stroke", formatColor(sFls.getColor()));
				sFlsV.put("fill", "transparent");
				sFlsV.put("stroke-width", String.valueOf(sFls.getWidth()));
				if(sFls.getStyle().isEmpty()) {
					sFlsV.put("stroke-dasharray", "none");
				} else {
					sFlsV.put("stroke-dasharray", sFls.getStyle());
				}
				String sFlsMV = formatBlock(sFlsM, sFlsV);
				sFlsMVs.add(sFlsMV);
			}
			
			LineStyle pFls = f.getFunctionPrintStyle();
			if(pFls != null) {
				String pFlsM = "#plot-" + String.valueOf(diagram.getFunctions().indexOf(f) + 1);
				Map<String, String> pFlsV = new HashMap<String, String>();
				pFlsV.put("stroke", formatColor(pFls.getColor()));
				pFlsV.put("fill", "transparent");
				pFlsV.put("stroke-width", String.valueOf(pFls.getWidth()));
				if(pFls.getStyle().isEmpty()) {
					pFlsV.put("stroke-dasharray", "none");
				} else {
					pFlsV.put("stroke-dasharray", pFls.getStyle());
				}
				String pFlsMV = formatBlock(pFlsM, pFlsV);
				pFlsMVs.add(pFlsMV);
			}
		}
		
		// point lists style
		/*for(MarkedPointsList l : diagram.getMarkedPointLists()) {
			PointStyle sLps = l.getPointScreenStyle();
			PointStyle pLps = l.getPointPrintStyle();
		}*/
		String sLpsMV = "";
		String pLpsMV = "";
		if(!diagram.getMarkedPointLists().isEmpty()) {
			MarkedPointsList l = diagram.getMarkedPointLists().get(0);
			PointStyle sLps = l.getPointScreenStyle();
			if(sLps != null) {
				//TODO: Respect symbol size
				String sLpsM = ".poi_symbol";
				Map<String, String> sLpsV = new HashMap<String, String>();
				sLpsV.put("stroke", formatColor(sLps.getColor()));
				sLpsV.put("fill", formatColor(sLps.getColor()));
				sLpsMV = formatBlock(sLpsM, sLpsV);
				
				String sLpsM2 = ".poi_symbol_bg";
				Map<String, String> sLpsV2 = new HashMap<String, String>();
				if(sLps.isShowBorder()) {
					sLpsV2.put("stroke", formatColor(sLps.getColor()));
				} else {
					sLpsV2.put("stroke", formatColor(sGas.getColor()));
				}
				sLpsMV += formatBlock(sLpsM2, sLpsV2);
			}
			
			PointStyle pLps = l.getPointPrintStyle();
			if(pLps != null) {
				String pLpsM = ".poi_symbol";
				Map<String, String> pLpsV = new HashMap<String, String>();
				pLpsV.put("stroke", formatColor(pLps.getColor()));
				pLpsV.put("fill", formatColor(pLps.getColor()));
				pLpsMV = formatBlock(pLpsM, pLpsV);
				
				String pLpsM2 = ".poi_symbol_bg";
				Map<String, String> pLpsV2 = new HashMap<String, String>();
				if(pLps.isShowBorder()) {
					pLpsV2.put("stroke", formatColor(pLps.getColor()));
				} else {
					pLpsV2.put("stroke", formatColor(pGas.getColor()));
				}
				pLpsMV += formatBlock(pLpsM2, pLpsV2);
			}
		}
		// style override
		OverrideStyle Os = diagram.getOverrideStyle();
		String OsMV = "";
		if(Os != null) {
			OsMV = Os.getCssStyle();
		}
		
		
		// compose result
		result += sGasMV;
		result += sGtsMV;
		result += sGgsMV;
		result += sXlsMV;
		result += sYlsMV;
		result += sXHlsMV;
		result += sYHlsMV;
		result += sIasMV;
		for(String s : sFlsMVs) {
			result += s;
		}
		result += sLpsMV;
		
		result += "@media print{ ";
		
		result += pGasMV;
		result += pGtsMV;
		result += pGgsMV;
		result += pXlsMV;
		result += pYlsMV;
		result += pXHlsMV;
		result += pYHlsMV;
		result += pIasMV;
		for(String s : pFlsMVs) {
			result += s;
		}
		result += pLpsMV;
		
		result += "} ";
		
		result += OsMV;
		
		return result;
	}
	
	public static String formatBlock(String modifier, Map<String,String> values) {
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
	
	public static String formatColor(Color color) {
		String result = "#";
		String red = Integer.toHexString(color.getRed());
		String green = Integer.toHexString(color.getGreen());
		String blue = Integer.toHexString(color.getBlue());
		
		if(red.length() == 1) {
			red = "0" + red;
		}
		if(green.length() == 1) {
			green = "0" + green;
		}
		if(blue.length() == 1) {
			blue = "0" + blue;
		}
		
		result += red;
		result += green;
		result += blue;
		
		if(result.length() != 7) {
			return "#000000";
		}
		
		return result;
	}
}
