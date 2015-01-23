/**
 * 
 */
package de.tudresden.inf.gsvgplott.data;

/**
 * @author David Gollasch
 *
 */
public class Integral {
	
	private String integralTitle;
	private double integralRangeFrom;
	private double integralRangeTo;
	private Function integralFunc1;
	private Function integralFunc2;
	
	public Integral() {
		
	}

	public String getIntegralTitle() {
		return integralTitle;
	}

	public void setIntegralTitle(String integralTitle) {
		this.integralTitle = integralTitle;
	}

	public double getIntegralRangeFrom() {
		return integralRangeFrom;
	}

	public void setIntegralRangeFrom(double integralRangeFrom) {
		this.integralRangeFrom = integralRangeFrom;
	}

	public double getIntegralRangeTo() {
		return integralRangeTo;
	}

	public void setIntegralRangeTo(double integralRangeTo) {
		this.integralRangeTo = integralRangeTo;
	}

	public Function getIntegralFunc1() {
		return integralFunc1;
	}

	public void setIntegralFunc1(Function integralFunc1) {
		this.integralFunc1 = integralFunc1;
	}

	public Function getIntegralFunc2() {
		return integralFunc2;
	}

	public void setIntegralFunc2(Function integralFunc2) {
		this.integralFunc2 = integralFunc2;
	}
}
