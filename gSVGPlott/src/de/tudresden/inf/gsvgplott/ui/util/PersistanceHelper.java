/**
 * 
 */
package de.tudresden.inf.gsvgplott.ui.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import de.tudresden.inf.gsvgplott.data.Diagram;
import de.tudresden.inf.gsvgplott.data.Function;
import de.tudresden.inf.gsvgplott.data.MarkedPointsList;

/**
 * @author David Gollasch
 *
 */
public class PersistanceHelper {
	/**
	 * Method to store a diagram state as xml file
	 * @param diagram
	 * @param filename
	 */
	public static void storeDiagramToFile(Diagram diagram, String filename) throws Exception {
		/*XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		encoder.writeObject(diagram);
		encoder.close();*/
		
		XStream stream = new XStream();
		String xml = stream.toXML(diagram);
		
		PrintWriter writer = new PrintWriter(filename);
		writer.println(xml);
		writer.flush();
		writer.close();
	}
	
	/**
	 * Method to store style as an xml file (store diagram without data)
	 * @param style
	 * @param filename
	 * @return
	 */
	public static void storeStyleToFile(Diagram style, String filename) throws Exception {
		//consider removing data here later on
		
		/*XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		encoder.writeObject(style);
		encoder.close();*/
		
		XStream stream = new XStream();
		String xml = stream.toXML(style);
		
		PrintWriter writer = new PrintWriter(filename);
		writer.println(xml);
		writer.flush();
		writer.close();
	}
	
	/**
	 * Loads diagram by file
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static Diagram loadDiagramFromFile(String filename) throws Exception {
		/*XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
	    Diagram o = (Diagram)decoder.readObject();
	    decoder.close();
	    return o;*/
		
		XStream stream = new XStream();
		Diagram o = (Diagram)stream.fromXML(new FileInputStream(filename));
		return o;
	}
	
	public static Diagram loadStyleFromFile(String filename) throws Exception {
		/*XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
	    Diagram o = (Diagram)decoder.readObject();
	    decoder.close();
	    return o;*/
	    
	    XStream stream = new XStream();
		Diagram o = (Diagram)stream.fromXML(new FileInputStream(filename));
		return o;
	}
	
	/**
	 * Apply styling information onto an data diagram object
	 * @param data Diagram object to apply styling on
	 * @param style Style information source
	 * @return Completely styled diagram object
	 */
	public static Diagram applyStyleToDiagram(Diagram data, Diagram style) {
		data.setBackgroundScreenStyle(style.getBackgroundScreenStyle());
		data.setBackgroundPrintStyle(style.getBackgroundPrintStyle());
		
		data.setGridScreenStyle(style.getGridScreenStyle());
		data.setGridPrintStyle(style.getGridPrintStyle());
		
		data.setTextScreenStyle(style.getTextScreenStyle());
		data.setTextPrintStyle(style.getTextPrintStyle());
		
		data.setOverrideStyle(style.getOverrideStyle());
		
		data.getXaxis().setAxisScreenStyle(style.getXaxis().getAxisScreenStyle());
		data.getXaxis().setAxisPrintStyle(style.getXaxis().getAxisPrintStyle());
		data.getXaxis().setHelplineScreenStyle(style.getXaxis().getHelplineScreenStyle());
		data.getXaxis().setHelplinePrintStyle(style.getXaxis().getHelplinePrintStyle());
		
		data.getYaxis().setAxisScreenStyle(style.getYaxis().getAxisScreenStyle());
		data.getYaxis().setAxisPrintStyle(style.getYaxis().getAxisPrintStyle());
		data.getYaxis().setHelplineScreenStyle(style.getYaxis().getHelplineScreenStyle());
		data.getYaxis().setHelplinePrintStyle(style.getYaxis().getHelplinePrintStyle());
		
		if(data.getIntegral() != null && style.getIntegral() != null) {
			data.getIntegral().setAreaScreenStyle(style.getIntegral().getAreaScreenStyle());
			data.getIntegral().setAreaPrintStyle(style.getIntegral().getAreaPrintStyle());
		}
		
		List<Function> dataFunctions = data.getFunctions();
		List<Function> styleFunctions = style.getFunctions();
		for(Function f : dataFunctions) {
			int index = dataFunctions.indexOf(f);
			if(index < styleFunctions.size()) {
				Function styleF = styleFunctions.get(index);
				f.setFunctionScreenStyle(styleF.getFunctionScreenStyle());
				f.setFunctionPrintStyle(styleF.getFunctionPrintStyle());
			}
		}
		
		List<MarkedPointsList> dataLists = data.getMarkedPointLists();
		List<MarkedPointsList> styleLists = style.getMarkedPointLists();
		for(MarkedPointsList l : dataLists) {
			int index = dataLists.indexOf(l);
			if(index < styleLists.size()) {
				MarkedPointsList styleL = styleLists.get(index);
				l.setPointScreenStyle(styleL.getPointScreenStyle());
				l.setPointPrintStyle(styleL.getPointPrintStyle());
			}
		}
		
		return data;
	}
}
