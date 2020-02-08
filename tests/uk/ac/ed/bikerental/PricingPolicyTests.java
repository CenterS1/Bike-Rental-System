package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.*;


public class PricingPolicyTests {
    // You can add attributes here
	
	private ArrayList<Bike> bikes1, bikes2;
	private Provider prov1, prov2;
	private DateRange d1, d2;

    @BeforeEach
    void setUp() throws Exception {
        // Put setup here
    	Location l1 = new Location ("EH8 9RD", "Salisbury Court");
		Location l2 = new Location ("EH8 9XQ", "Windmill Place");
		
		this.prov1 = new Provider("846260512@qq.com", "Edith", l1, "18611440569");
    	this.prov2 = new Provider("2249820164@qq.com", "Kim", l2, "13702883716");
    		  
    	
    	BikeType bp1 = new BikeType("Mountain bike", new BigDecimal(1000), new BigDecimal(0.5));
    	BikeType bp2 = new BikeType("Ebike", new BigDecimal(1500), new BigDecimal(0.4));
    	
    	BikeType bp3 = new BikeType("Road bike", new BigDecimal(800), new BigDecimal(0.8));
    	BikeType bp4 = new BikeType("Mountain bike", new BigDecimal(1500), new BigDecimal(0.2));
    	
		
    	prov1.setDailyRentalPrice(bp1, new BigDecimal(10));
    	prov1.setDailyRentalPrice(bp2, new BigDecimal(8));
    	
    	prov2.setDailyRentalPrice(bp3, new BigDecimal(15));
    	prov2.setDailyRentalPrice(bp4, new BigDecimal(10));
    	
		
		Bike b1 = new Bike(bp1, l1);
		Bike b2 = new Bike(bp2, l1);
		
		Bike b3 = new Bike(bp3, l2);		
		Bike b4 = new Bike(bp4, l2);
		
		
    	this.bikes1 = new ArrayList<>();
    	bikes1.add(b1);
    	bikes1.add(b2);
    	
    	this.bikes2 = new ArrayList<>();
    	bikes2.add(b3);
    	bikes2.add(b4);
    	  	
    	
    	this.d1 = new DateRange(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10));
        this.d2 = new DateRange(LocalDate.of(2019, 1, 5), LocalDate.of(2019, 1, 23));
 	
    }
    
    // TODO: Write tests for pricing policies
     @Test
     void testPricingPolicy() {
    	 assertEquals(51.3, prov1.calculatePrice(bikes1, d1).doubleValue());
    	 assertEquals(382.5, prov2.calculatePrice(bikes2, d2).doubleValue());
    	 
     }
    
}
