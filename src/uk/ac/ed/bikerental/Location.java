package uk.ac.ed.bikerental;
/**
 * This Location class due to exactly allocate the location of each object which has this attributes.
 * A Location object encapsulate the information need to allocate a object on the Earth.
 * @author s1820742
 * @author s1801646
 */

public class Location {
	
    private String postcode;
    private String address;
    
    
    /**
     * Constructor of Location
     * Since only address is not precise to represent a specific location 
     * and only postcode is not obvious for users to read
     * so we both need postcode and address to initialize a location.
     * @param postcode should be a 6 digit/character long String 
     * @param address is a readable String include the specific block/street/building/flatNo.
     */
    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode;
        this.address = address;
    }
    
    
    /**
     * compares two Locations whether they are close 
     * by comparing the first two characters of the postcode of those Location.
     * if two lacations are not close to each other return false.
     * if two locations are close to each other then return true.
     * @param other is the location being compared.
     * @return whether the Location which calls this method is near to the Location(other)
     */
    public boolean isNearTo(Location other) {
        String thisFirst2 = this.postcode.substring(0, 1);
        String otherFirst2 = other.postcode.substring(0, 1);
        thisFirst2.toUpperCase();
        otherFirst2.toUpperCase();
        
        if(thisFirst2.equals(otherFirst2)) {
        	return true;
        }
        else {
        	return false;
        }
    }
    
    
    /**
     * Gets the postcode of the location
     * @return postcode
     */
    public String getPostcode() {
        return postcode;
    }
    
    
    /** 
     * gets the address of the location
     * @return address
     */
    public String getAddress() {
        return address;
    }
    
    public String toString() {
    	return address + " " + postcode;
    }
    
    // You can add your own methods here
}
