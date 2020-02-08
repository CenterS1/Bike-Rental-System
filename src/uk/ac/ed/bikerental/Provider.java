package uk.ac.ed.bikerental;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Provider extends User implements PricingPolicy,DeliveryService{
	private Location shopAddress;
	private String provPhoneNum;
	private ArrayList<Provider> partners;
	
	
	public Provider(String email, String name, Location shopAddress, 
					String provPhoneNum) {
		super(email, name);
		this.shopAddress = shopAddress;
		this.provPhoneNum = provPhoneNum;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////	
//------------------------------------------Getter & Setter------------------------------------------------	
	public Location getShopAddress() {
		return shopAddress;
	}
	public String getProvPhoneNum() {
		return provPhoneNum;
	}
	public ArrayList<Provider> getPartners(){
		return partners;
	}	
	public void setShopAddress(Location l) {
		this.shopAddress = l;
	}
	public void setProvPhoneNum(String num) {
		this.provPhoneNum = num;
	}
	public void setPartners(ArrayList<Provider> p) {
		this.partners = p;
	}
	public void addPartners(Provider p) {
		this.partners.add(p);
	}
	public void removePartners(Provider p) {
		this.partners.remove(p);
	}
	
//------------------------------------------------------------------------	
//////////////////////////////////////////////////////////////////////////
//------------------------------Partnership-------------------------------	
	public void joinPartnership(Provider ori) {
		ori.partners.add(this);
		partners.add(ori);
	}
	
	public void quitPartnership(Provider ori) {
		if(ori.partners.contains(this)) {
			ori.partners.remove(this);
			partners.remove(ori);
		}
		else {
			partners.remove(this);
			System.out.printf("You are not in a partnership with %d.", ori.email);
		}
	}
//---------------------------------------------------------------------------------------	
/////////////////////////////////////////////////////////////////////////////////////////	
	public ArrayList<Bike> getBikeOwned(Database database){	
		return database.getProvBike(email);
	}
	
	public void addBike(Database database, Bike bike) {	
		database.getBikeGarage().get(this.email).add(bike);
		bike.setP(this); 
	}
	
	public void removeBike(Database database, Bike bike) {
		database.getBikeGarage().get(this.email).remove(bike);
	}
	
	public void setBikeType(BikeType bt, String type, BigDecimal rv, BigDecimal dr) {	
		//rv = replacementValue, dr = DepositRate
		bt.setType(type); 
		bt.setReplacementValue(rv);
		bt.setDepositRate(dr);
	}
	
	public void receiveBikes(Order order) {					// customer只能当面还车，不能delivery	
		for(Bike b: order.getItems()) {
			order.setReturnStatus(b, true);
			order.setOrderStatus(OrderStatus.BikeReturned);
			b.setCurrStatus(BikeStatus.InShop);
		}
	}
	
	public void returnBikeToPar(Order order, Provider par) {
		for(Bike b: order.getItems()) {
			order.scheduleDelivery(b, this.shopAddress, par.shopAddress, order.getDuration().getEnd());
		}
		for(Bike bike:order.getItems()) {
			bike.setCurrStatus(BikeStatus.InShop);
		}
	}
	
	public void returnDeposit(Order order) {			
		for(Bike b: order.getItems()) {
			order.setOrderStatus(OrderStatus.Finished);
			order.getDepositReturnStatus().replace(b, true);
			b.setCurrOrder(null);
		}	
	}
	
	public void returnDepositToPar(Order order, Provider par) {
		for(Bike b: order.getItems()) {
			order.setOrderStatus(OrderStatus.Finished);
			order.getDepositReturnStatus().replace(b, true);
			b.setCurrOrder(null);
		}
	}

	@Override
	public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
		bikeType.setDailyRentalPrice(dailyPrice); 
	}

	@Override
	public BigDecimal calculatePrice(ArrayList<Bike> bikes, DateRange duration) {				// Submodule 1
		if(duration.getStart().isAfter(duration.getEnd())) {
			System.out.println("Invalid duration.");
		}
		
		BigDecimal length = new BigDecimal(duration.countLength());
		
		long d = duration.countLength();
		ArrayList<BigDecimal> bikePrice = new ArrayList<BigDecimal>();
		
		int i = 0;
		for(Bike b : bikes) {
			if (1 <= d && d <= 2) {
				bikePrice.add(i, b.getBikeType().getDailyRentalPrice());
				i++;
			}
			else if(3 <= d && d <= 6) {
				BigDecimal dis = new BigDecimal(0.95);
				bikePrice.add(i, b.getBikeType().getDailyRentalPrice().multiply(dis));
				i++;
			}
			else if(7 <= d && d <= 13) {
				BigDecimal dis = new BigDecimal(0.9);
				bikePrice.add(i, b.getBikeType().getDailyRentalPrice().multiply(dis));
				i++;
			}
			else {
				BigDecimal dis = new BigDecimal(0.85);
				
				bikePrice.add(i, b.getBikeType().getDailyRentalPrice().multiply(dis));
				i++;
			}		
		}
		
		BigDecimal r = new BigDecimal(0);
		for(int j = 0; j < bikePrice.size(); j++) {
			r = r.add(bikePrice.get(j));
		}
			
		return r.multiply(length);
	}

	@Override
	public void scheduleDelivery(Deliverable deliverable, Location pickupLocation, Location dropoffLocation,
			LocalDate pickupDate) {}
	
	public String toString() {
		return email;
	}
}

