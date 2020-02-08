package uk.ac.ed.bikerental;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Customer extends User{
	private Location cusAddress;
	private String cusPhoneNum;
	private String paymentInfo;
	
	public Customer(String email, String name, Location cusAddress, String cusPhoneNum) {
		super(email, name);
		this.cusAddress = cusAddress;
		this.cusPhoneNum = cusPhoneNum;
	}	
///////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------------Getter & Setter-------------------------------------------	
	public String getCusPhoneNum() {
		return cusPhoneNum;
	}
	public Location getCusAddress(){
		return cusAddress;
	}
	public String getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(String pi) {
		this.paymentInfo = pi;
	}
	public void setCusAddress(Location l) {
		this.cusAddress = l;
	}
	public void setCusPhoneNum(String phoneNum) {
		this.cusPhoneNum = phoneNum;
	}
//------------------------------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////////
//--------------------------------------------Get Quote-------------------------------------------------
	public Quote getQuote(Database database, String type, Location loc, DateRange date, int amt){
			
		Quote q = new Quote();
		ArrayList<Bike> result = database.returnMatchedBike(type, loc, date, amt);
		// If there are no quotes satisfy all of the 4 requirements, then return nothing.
		if(result.isEmpty()) {
			System.out.println("No quotes found.");
		}
		else {
			q.setQuote(result);	
			System.out.println("Quotes found.");
		}
		return q;
	}	
//---------------------------------------------------------------------------------------------------------
///////////////////////////////////////////////////////////////////////////////////////////////////////////	
//----------------------------------------------Book Quote-------------------------------------------------
	public Order bookQuote(ArrayList<Bike> items, DateRange duration, boolean requireDelivery, String paymentInfo){
		for(Bike b: items) {
			b.setC(this);
			b.addBookedDates(duration);			
		}
		
		Order o = new Order(items, duration, requireDelivery);		// setOrder
		if(checkOverlap(items, duration)) {
			System.out.println("Some of your selected bikes may be unavailable at your selected dates, please selected another dates. Booking failed.");
			o.getItems().clear();
		}
		else if(!checkOut(o, paymentInfo)) {
			System.out.println("Booking failed.");
			o.getItems().clear();
		}
		else {		
			boolean b1 = true;
			Provider first = items.get(0).getProvider();
			for(int i = 0; i < items.size(); i++) {
				if(items.get(i).getProvider() != first) {
					b1 &= false;
				}
			}
			if(b1 == false) {
				System.out.println("You can only book quotes from one provider.");
				o.getItems().clear();
			}
			else {
				System.out.println("Booking succeed.");
				for(Bike b: items) {				
					b.setCurrOrder(o);
				}
			}							
		}
		return o;		
	}
	
	public boolean checkOut(Order order, String paymentInfo) {
		if(paymentInfo.equals(this.paymentInfo)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkOverlap(ArrayList<Bike> items, DateRange date) {
		
		boolean result = false;
		for(Bike b: items) {
			for(DateRange d: b.getBookedDates()) {
				if(d.overlaps(date)) {
					result = true;
				}
			}			
		}
		return result;
	}
//---------------------------------------------------------------------------------------------------------	
///////////////////////////////////////////////////////////////////////////////////////////////////////////	
//---------------------------------------------Return Bike-------------------------------------------------
	public boolean confirmReceivedBike(Order order) {
		for(Bike b:order.getItems()) {
			b.setCurrStatus(BikeStatus.Collected);
		}
		return true;
	}

	public boolean returnBikeToOriProvider(Order order){
		for(Bike b: order.getItems()) {
			b.setCurrStatus(BikeStatus.InShop);
			order.setReturnStatus(b, true);
			b.getProvider().receiveBikes(order);
			b.getProvider().returnDeposit(order);
		}
		return true;
	}
	
	public boolean returnBikeToParProvider(Order order, Provider pp) {
		for(Bike b: order.getItems()) {
			if(!(b.getProvider().getPartners().contains(pp)) &&  !(pp.getPartners().contains(b.getProvider()))) {
				System.out.println("You cannot return " + b.toString() + " to provider " + pp.toString() + ".");
				return false;
			}
			else {				
				pp.returnBikeToPar(order, b.getProvider());		//scheduleDelivery		
				b.setCurrStatus(BikeStatus.InShop);
				order.setReturnStatus(b, true);
				b.getProvider().receiveBikes(order);
				pp.returnDeposit(order);
				b.getProvider().returnDepositToPar(order, pp);	
				order.scheduleDelivery(b, pp.getShopAddress(), b.getProvider().getShopAddress(), order.getDuration().getEnd());
			}
		}
		return true;
	}
	
//---------------------------------------------------------------------------------------------------------	
///////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public void cancelOrder(Order o) {
		if(o.getOrderStatus() != OrderStatus.OrderConfirmed) {
			System.out.println("Your order cannot be cancelled at this stage.");
		}
		else {
			o.setOrderStatus(OrderStatus.OrderCancelled);
			DateRange duration = o.getDuration();
			
			for(Bike b: o.getItems()) {
				b.setCurrStatus(BikeStatus.InShop);
				b.removeBookedDates(duration);
			}
		}		
	}
	
	
}





