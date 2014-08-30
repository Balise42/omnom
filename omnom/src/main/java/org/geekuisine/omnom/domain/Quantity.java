package org.geekuisine.omnom.domain;

import java.math.BigDecimal;

/** Represent a quantity for ingredients */
public class Quantity {
	/** The unit (e.g. grams, dash, cups...) */
	String unit;
	/** The quantity, expressed in "unit"s */
	BigDecimal numberOfUnits;
	/** Whether the quantity needs to be scaled with the recipe (false) or not (true) */
	boolean fuzzy;
	
	public Quantity(){
	}
	
	public Quantity(String unit, BigDecimal numberOfUnits, boolean fuzzy){
		this.unit = unit;
		this.numberOfUnits = numberOfUnits;
		this.fuzzy = fuzzy;
	}
	
	public Quantity(String unit, BigDecimal numberOfUnits){
		this(unit, numberOfUnits, false);
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public BigDecimal getNumberOfUnits() {
		return numberOfUnits;
	}
	public void setNumberOfUnits(BigDecimal numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}
	public boolean isFuzzy() {
		return fuzzy;
	}
	public void setFuzzy(boolean fuzzy) {
		this.fuzzy = fuzzy;
	}
	
}
