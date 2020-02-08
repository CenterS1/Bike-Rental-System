package uk.ac.ed.bikerental;
import java.util.ArrayList;

public class Quote {
	
	private ArrayList<Bike> quote = new ArrayList<>();
	
	public ArrayList<Bike> getQuote(){
		return quote;
	}
	
	public void setQuote(ArrayList<Bike> bikes) {
		this.quote = bikes;
	}
	public void addQuote(Bike b) {
		this.quote.add(b);
	}
	public void removeQuote(Bike b) {
		this.quote.remove(b);
	}
	
	public String toString() {
		String result = "";
		for(int i = 0; i < quote.size(); i++) {
			result = result + quote.get(i).toString() + "\n";
		}
		return result;		
	}
	
	public boolean Equals(Quote q) {
		boolean r = true;
		if(this.quote.size()!= q.quote.size()) {
			return false;
		}
		
		for(int i = 0; i < q.quote.size(); i++) {
			if(this.quote.get(i).getBikeType().getType().equals(q.quote.get(i).getBikeType().getType())) {
				r = r && true;
			}
			else {
				r = r && false;
			}		
		}
		return r;
	}
}
