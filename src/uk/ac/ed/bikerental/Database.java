package uk.ac.ed.bikerental;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	
	private HashMap<String, ArrayList<Bike>> bikeGarage;
	
	public Database() {
		this.bikeGarage = new HashMap<String, ArrayList<Bike>>();
	}
	
	/////////////////////Getter & Setter/////////////////////
	public HashMap<String, ArrayList<Bike>> getBikeGarage(){
		return bikeGarage;
	}
	public void setBikeGarage(String email, ArrayList<Bike> bikes) {
		this.bikeGarage.put(email, bikes);
	}
	////////////////////////////////////////////////////////
	
	public ArrayList<Bike> getProvBike(String provEmail) {
		ArrayList<Bike> bikeOwned = new ArrayList<>();
		bikeOwned = bikeGarage.get(provEmail);
		return bikeOwned;
	}

	public ArrayList<Bike> getBikeGarageList(){
		ArrayList<Bike> TotalBike= new ArrayList<Bike>();
		
		for(String s: bikeGarage.keySet()) {
			for(Bike bike: bikeGarage.get(s)){
				TotalBike.add(bike); 
			}
		}
		return TotalBike;
	}
	
	public ArrayList<Bike> returnMatchedBike(String type, Location location, DateRange dates, int quantity){	
		// return the bikes that satisfies all requirements.
		
		ArrayList<Bike> EmptyList = new ArrayList<>();
		ArrayList<Bike> MatchedBike = new ArrayList<>();
		ArrayList<Bike> MB = new ArrayList<>();
		
		MatchedBike = this.getBikeGarageList();
		
		if(MatchedBike.size() == 0) {
			System.out.println("The Bike Garage is empty!");
		}
		
		for(int i = 0; i < MatchedBike.size(); i++) {
			Bike b = MatchedBike.get(i);
		
			if(b.getBikeType().getType() != type) {
				MatchedBike.remove(b);
				i -= 1;
			}
			else if(!b.getLocation().isNearTo(location)) {
				MatchedBike.remove(b);
				i -= 1;
			}
			for(int j = 0; j < b.getBookedDates().size(); j++) {
				if(b.getBookedDates().get(j).overlaps(dates)) {
					MatchedBike.remove(b);
					i -= 1;
				}
			}
		}
		
		for(Bike b: MatchedBike) {
			MB.add(b);
		}
		
		if(quantity > MB.size()) {
			return EmptyList;
		}
		else return MB;
	}
	
	public String toString() {
		String r = "";		
		for(String s: bikeGarage.keySet()) {
			r = r + s + " -> ";
			for(Bike bike: bikeGarage.get(s)){
				r = r + bike.toString() + ", ";
			}
			r += "\n";
		}
		return r;
	}
	
}
