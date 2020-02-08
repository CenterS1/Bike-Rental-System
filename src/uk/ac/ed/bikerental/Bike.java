package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Bike implements Deliverable{
	
	private BikeType type;
	private Location location;
	private BigDecimal deposit;
	private Provider p;
	private Customer c;
	private ArrayList<DateRange> bookedDates;
	private BikeStatus currStatus;
	private Order currOrder;	
	
	public Bike(BikeType type, Location location) {
		this.type = type;
		this.location = location;
		this.deposit = type.getReplacementValue().multiply(type.getDepositRate());
		this.p = null;
		this.c = null;
		this.currStatus = BikeStatus.InShop;
		this.currOrder = null;	
		this.bookedDates = new ArrayList<>();
	}
	
	///////////////////////////////////////////////////////////////////////////
	//////////////////////////////Getter & Setter//////////////////////////////
	public BikeType getBikeType() {
		return type;
	}	
	public Location getLocation() {
		return location;
	}	
	public BigDecimal getDeposit() {
		return deposit;
	}	
	public Provider getProvider() {
		return p;
	}
	public Customer getCustomer() {
		return c;
	}
	public ArrayList<DateRange> getBookedDates(){
		return bookedDates;
	}
	public BikeStatus getCurrStatus() {
		return currStatus;
	}
	public Order getCurrOrder() {
		return currOrder;
	}
	public void setType(BikeType type) {
		this.type = type;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}
	public void setP(Provider p) {
		this.p = p;
	}
	public void setC(Customer c) {
		this.c = c;
	}
	public void addBookedDates(DateRange bookedDate) {
		this.bookedDates.add(bookedDate);
	}
	public void removeBookedDates(DateRange bookedDate) {
		this.bookedDates.remove(bookedDate);
	}
	public void setCurrStatus(BikeStatus currStatus) {
		this.currStatus = currStatus;
	}
	public void setCurrOrder(Order currOrder) {
		this.currOrder = currOrder;
	}
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Override
	public void onPickup() {
		currStatus = BikeStatus.Delivering;		
		currOrder.setOrderStatus(OrderStatus.BikeDelivering);
	}

	@Override
	public void onDropoff() {
		currStatus = BikeStatus.Delivered;		
		currOrder.setOrderStatus(OrderStatus.BikeDelivered);
	}
	
	public String toString() {		
		String result = this.type.toString();
		return result;
	}
}