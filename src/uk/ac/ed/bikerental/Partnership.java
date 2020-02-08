package uk.ac.ed.bikerental;
import java.util.ArrayList;

public class Partnership {
	
	private ArrayList<Provider> members;	//members = members' emails, we only record emails.
	
	public ArrayList<Provider> getMembers(){
		return members;
	}

	public Partnership() {
		this.members = new ArrayList<>();
	}
	
	public void addMember(Provider p) {
		members.add(p);
	}
	
	public void removeMmeber(Provider p) {
		members.remove(p);
	}

}
