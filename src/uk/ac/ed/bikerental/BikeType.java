package uk.ac.ed.bikerental;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class BikeType {
	
	private String type;
	private BigDecimal replacementValue;
	private BigDecimal dailyRentalPrice;
	private BigDecimal depositRate;
	
	public BikeType(String type, BigDecimal replacementValue, BigDecimal depositRate) {
		if(type == "Mountain bike" || type == "Hybrid bike" || type == "Ebike" || type == "Road bike") {
			this.type = type;
		}
		this.replacementValue = replacementValue;
		this.depositRate = depositRate;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////Getter & Setter///////////////////////////////////
	public String getType() {
		return type;
	}
	public BigDecimal getReplacementValue() {
		return replacementValue;
	}
	public BigDecimal getDailyRentalPrice() {
		return dailyRentalPrice;
	}
	public BigDecimal getDepositRate() {
		return depositRate;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setReplacementValue(BigDecimal rv) {
		this.replacementValue = rv;
	}
	public void setDailyRentalPrice(BigDecimal drp) {
		this.dailyRentalPrice = drp;
	}
	public void setDepositRate(BigDecimal dr) {
		this.depositRate  = dr;
	}
	/////////////////////////////////////////////////////////////////////////////////////
	
	public String toString() {
		return type;
	}
	
}
