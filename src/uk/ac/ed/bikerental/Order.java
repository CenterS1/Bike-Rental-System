package uk.ac.ed.bikerental;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Order implements DeliveryService{

	private int orderNum = 00000000;
	private DateRange duration; 		// 同一order中book的所有bike必须有相同的duration，Ambiguity.
	private ArrayList<Bike> items = new ArrayList<Bike>();
	private HashMap<Bike, BigDecimal> priceDetails = new HashMap<Bike, BigDecimal>();
	private HashMap<Bike, BigDecimal> deposit = new HashMap<>();
	private HashMap<Bike, Boolean> returnStatus = new HashMap<>();
	private HashMap<Bike, Boolean> depositStatus = new HashMap<>();
	private HashMap<Bike, Boolean> depositReturnStatus = new HashMap<>();
	private BigDecimal totalPrice = new BigDecimal(0);
	private OrderStatus orderStatus;
	private boolean requireDelivery;
	
	public Order(ArrayList<Bike> items, DateRange duration, boolean requireDelivery) {
		
		this.orderNum ++;
		this.items = items;
		this.duration = duration;
		
		for(Bike b: items) {
			this.priceDetails.put(b, b.getBikeType().getDailyRentalPrice());
			this.deposit.put(b, b.getBikeType().getDepositRate().multiply(b.getBikeType().getReplacementValue()));
			this.returnStatus.put(b, false);	
			this.depositReturnStatus.put(b, false);
		}
		setTotalPrice();
		this.orderStatus = OrderStatus.OrderConfirmed;	
		this.requireDelivery = requireDelivery;	
		if(requireDelivery == true) {
			for(Bike b: items) {
				scheduleDelivery(b, b.getCustomer().getCusAddress(), b.getProvider().getShopAddress(), duration.getStart());
			}			
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////	
//-------------------------------------------Getter & Setter-------------------------------------------		
	public int getOrderNum() {
		return orderNum;
	}
	public DateRange getDuration() {
		return duration;
	}
	public ArrayList<Bike> getItems() {
		return items;
	}
	public HashMap<Bike, BigDecimal> getPriceDetails() {
		return priceDetails;
	}
	public HashMap<Bike, BigDecimal> getDeposit() {
		return deposit;
	}
	public HashMap<Bike, Boolean> getReturnStatus(){
		return returnStatus;
	}
	public HashMap<Bike, Boolean> getDepositStatus(){
		return depositStatus;
	}
	public HashMap<Bike, Boolean> getDepositReturnStatus(){
		return depositReturnStatus;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public boolean getRequireDelivery(){
		return requireDelivery;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public void setDuration(DateRange duration) {
		this.duration = duration;
	}
	public void setItems(ArrayList<Bike> items) {
		this.items = items;
	}
	public void setPriceDetails(Bike b, BigDecimal p) {
		this.priceDetails.put(b, p);
	}
	public void setDeposit(Bike b, BigDecimal d) {
		this.deposit.put(b, d);
	}
	public void setReturnStatus(Bike b, Boolean rs) {
		this.returnStatus.put(b, rs);
	}
	public void setDepositStatus(Bike b, Boolean ds) {
		this.depositStatus.put(b, ds);
	}
	public void setDepositReturnStatus(Bike b, Boolean drs) {
		this.depositReturnStatus.put(b, drs);
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setRequireDelivery(boolean requireDelivery) {
		this.requireDelivery = requireDelivery;
	}
//------------------------------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setTotalPrice() {						//We do not consider delivery fees
		ArrayList<Bike> copy = items;	
		for(int i = 0; i < items.size(); i++) {		
			ArrayList<Bike> list = new ArrayList<>();	
			for(int j = 0; j < copy.size(); j++) {
				
			
				Bike b = copy.get(j); 
				Provider b_provider = b.getProvider();
				
				Bike a_item = items.get(i);
				Provider a_item_provider = a_item.getProvider();
				if(b_provider == null) {
					continue;
				}
				if(b_provider.equals(a_item_provider)) {
					list.add(copy.get(j));
				 }
			}
			Provider a  = items.get(i).getProvider();
			this.totalPrice.add(a.calculatePrice(list, duration));		
		}
	}

	public void updateReturnStatus(Bike bike) {
		returnStatus.put(bike, true);
	}
	
	@Override
	public void scheduleDelivery(Deliverable deliverable, Location pickupLocation, Location dropoffLocation,
			LocalDate pickupDate) {}	//Implementation is not necessary	
	
	public boolean Equals(Order o) {
		boolean r = true;
		if(this.items.size()!= o.items.size()) {
			return false;
		}
		
		for(int i = 0; i < o.items.size(); i++) {
			if(this.items.get(i).getBikeType().getType().equals(o.items.get(i).getBikeType().getType())) {
				r = r && true;
			}
			else {
				r = r && false;
			}		
		}
		return r;
	}
}
