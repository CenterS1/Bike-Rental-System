package uk.ac.ed.bikerental;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SystemTests {
	/////////////////////////////////////////////////////////////////////////////
    ////////////////////////////getQuote Attributes////////////////////////////
	Database database = new Database();
	
	Provider p1, p2, p3;
	
	BigDecimal reVal1, reVal2, reVal3, reVal4,
			   reVal5, reVal6, reVal7,
			   reVal8, reVal9; 
	
	BigDecimal depoRate1, depoRate2, depoRate3, depoRate4,
			   depoRate5, depoRate6, depoRate7,
			   depoRate8, depoRate9;	
	
	BikeType bt1, bt2, bt3, bt4,
			 bt5, bt6, bt7,
			 bt8, bt9;	
	
	Location l1, 
			 l2,
			 l3;
	
	Location cusL1, cusL2, cusL3;
	
	Bike bike1, bike2, bike3, bike4,
		 bike5, bike6, bike7,
		 bike8, bike9;
	
	ArrayList<Bike> bikes1,
					bikes2,
					bikes3;
	
	Customer c1, c2, c3;
	
	Quote q1, q2;
	
	DateRange d1, d2, d3;
	
	Partnership ps;
	
    @BeforeEach
    void setUp1() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        ///////Customer #1 #2
        cusL1 = new Location("EJ9 3LN", "South district");
        cusL2 = new Location("GC3 9GG", "North district");
        
        c1 = new Customer("ilovetwy@126.com", "Xukun Cai", cusL1, "18611440569");
        c2 = new Customer("junjungkook@163.com", "Jung kook", cusL2, "13701727364");
      
        //////////////////////////////////////////////////////////
        ///////Provider #1
        
        l1 = new Location("EH8 9RD", "Salisbury Court");
        
        p1 = new Provider("846260512@qq.com", "Jane", l1, "13801229385");
        
        reVal1 = new BigDecimal(999.99);	//Mountain bike
        reVal2 = new BigDecimal(399.99);	//Road bike
        reVal3 = new BigDecimal(1999.99);	//Ebike
        reVal4 = new BigDecimal(199.99);	//Hybrid bike
        
        depoRate1 = new BigDecimal(0.35);	//Mountain bike
        depoRate2 = new BigDecimal(0.5);	//Road bike
        depoRate3 = new BigDecimal(0.4);	//Ebike
        depoRate4 = new BigDecimal(0.1);	//Hybrid bike
        
        bt1 = new BikeType("Mountain bike", reVal1, depoRate1);
        bt2 = new BikeType("Road bike", reVal2, depoRate2);
        bt3 = new BikeType("Ebike", reVal3, depoRate3);
        bt4 = new BikeType("Hybrid bike", reVal4, depoRate4);
        
        bike1 = new Bike(bt1, l1);
        bike2 = new Bike(bt2, l1);   
        bike3 = new Bike(bt3, l1);
        bike4 = new Bike(bt4, l1);
        
        bikes1 = new ArrayList<>();
        bikes1.add(bike1);
        bikes1.add(bike2);
        bikes1.add(bike3);
        bikes1.add(bike4);
        
        database.setBikeGarage(p1.email, bikes1); 
             
        //////////////////////////////////////////////////////////
        ///////Provider #2
        
        l2 = new Location("EH3 9FE", "Chalmers Street");
        
        p2 = new Provider("2249820164@qq.com", "Chloe", l2, "13801229385");
        
        reVal5 = new BigDecimal(899.99);	//Mountain bike
        reVal6 = new BigDecimal(299.99);	//Road bike
        reVal7 = new BigDecimal(1599.99);	//Ebike
        
        depoRate5 = new BigDecimal(0.1);	//Mountain bike
        depoRate6 = new BigDecimal(0.2);	//Road bike
        depoRate7 = new BigDecimal(0.15);	//Ebike
        
        bt5 = new BikeType("Mountain bike", reVal5, depoRate5);
        bt6 = new BikeType("Road bike", reVal6, depoRate6);
        bt7 = new BikeType("Ebike", reVal7, depoRate7);       
        
        bike5 = new Bike(bt5, l2);
        bike6 = new Bike(bt6, l2);   
        bike7 = new Bike(bt7, l2);
      
        bikes2 = new ArrayList<>();
        bikes2.add(bike5);
        bikes2.add(bike6);
        bikes2.add(bike7);
        
        database.setBikeGarage(p2.email, bikes2);      
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////Test Get Quote//////////////////////////////////////////
    	
    @Test
    void testGetQuote() {
    	//setting customers' booking dates
    	d1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
    	d2 = new DateRange(LocalDate.of(2019, 1, 5),
                LocalDate.of(2019, 1, 8));
    	
    	//setting correct answer
    	Quote result1 = new Quote();
    	result1.setQuote(new ArrayList<Bike>());
    	result1.addQuote(bike1);
    	result1.addQuote(bike5);  	
    	result1.addQuote(bike8);
    	//get actual answer
    	q1 = c1.getQuote(database, "Mountain bike", cusL1, d1, 1);
    	
    	//setting correct answer
       	Quote result2 = new Quote();
    	result2.setQuote(new ArrayList<Bike>());
    	result2.addQuote(bike3);
    	result2.addQuote(bike7); 
    	//get actual answer
    	q2 = c2.getQuote(database, "Ebike", cusL2, d2, 2);

    	boolean b1 = result1.Equals(q1);
    	boolean b2 = result2.Equals(q2);
    	assertTrue(b1);
    	assertFalse(b2);
    }
    
    
//////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Book Quote Attributes//////////////////////////////////////
    
    @BeforeEach
    void setUp2() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        ///////Customer #1 #2 #3
        cusL1 = new Location("EJ9 3LN", "South district");
        cusL2 = new Location("GC3 9GG", "North district");
        
        c1 = new Customer("ilovetwy@126.com", "Xukun Cai", cusL1, "18611440569");
        c2 = new Customer("junjungkook@163.com", "Jung kook", cusL2, "13701727364");
      
        //////////////////////////////////////////////////////////
        ///////Provider #1
        
        l1 = new Location("EH8 9RD", "Salisbury Court");
        
        p1 = new Provider("846260512@qq.com", "Jane", l1, "13801229385");
        
        reVal1 = new BigDecimal(999.99);	//Mountain bike
        reVal2 = new BigDecimal(399.99);	//Road bike
        reVal3 = new BigDecimal(1999.99);	//Ebike
        reVal4 = new BigDecimal(199.99);	//Hybrid bike
        
        depoRate1 = new BigDecimal(0.35);	//Mountain bike
        depoRate2 = new BigDecimal(0.5);	//Road bike
        depoRate3 = new BigDecimal(0.4);	//Ebike
        depoRate4 = new BigDecimal(0.1);	//Hybrid bike
        
        bt1 = new BikeType("Mountain bike", reVal1, depoRate1);
        bt2 = new BikeType("Road bike", reVal2, depoRate2);
        bt3 = new BikeType("Ebike", reVal3, depoRate3);
        bt4 = new BikeType("Hybrid bike", reVal4, depoRate4);
        
        bike1 = new Bike(bt1, l1);
        bike2 = new Bike(bt2, l1);   
        bike3 = new Bike(bt3, l1);
        bike4 = new Bike(bt4, l1);
        
        bikes1 = new ArrayList<>();
        bikes1.add(bike1);
        bikes1.add(bike2);
        bikes1.add(bike3);
        bikes1.add(bike4);
       
        database.setBikeGarage(p1.email, bikes1);
             
        //////////////////////////////////////////////////////////
        ///////Provider #2
        
        l2 = new Location("EH3 9FE", "Chalmers Street");
        
        p2 = new Provider("2249820164@qq.com", "Chloe", l2, "13801229385");
        
        reVal5 = new BigDecimal(899.99);	//Mountain bike
        reVal6 = new BigDecimal(299.99);	//Road bike
        reVal7 = new BigDecimal(1599.99);	//Ebike
        
        depoRate5 = new BigDecimal(0.1);	//Mountain bike
        depoRate6 = new BigDecimal(0.2);	//Road bike
        depoRate7 = new BigDecimal(0.15);	//Ebike
        
        bt5 = new BikeType("Mountain bike", reVal5, depoRate5);
        bt6 = new BikeType("Road bike", reVal6, depoRate6);
        bt7 = new BikeType("Ebike", reVal7, depoRate7);       
        
        bike5 = new Bike(bt5, l2);
        bike6 = new Bike(bt6, l2);   
        bike7 = new Bike(bt7, l2);
      
        bikes2 = new ArrayList<>();
        bikes2.add(bike5);
        bikes2.add(bike6);
        bikes2.add(bike7);       

        database.setBikeGarage(p2.email, bikes2);   
    }
 
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////Test Book Quote/////////////////////////////////////////
    
    @Test
    void testBookQuote() {
    	ArrayList<Bike> items1 = new ArrayList<>();
    	items1.add(bike1);
    	items1.add(bike3);
    	items1.add(bike4);
    	
    	ArrayList<Bike> items2 = new ArrayList<>();
    	bike5.addBookedDates(new DateRange(LocalDate.of(2018, 8, 23),
                						   LocalDate.of(2018, 10, 1)));
    	items2.add(bike5);
    	items2.add(bike7);
    	items2.add(bike2);
    	
    	p1.addBike(database, bike1);
    	p1.addBike(database, bike2);
    	p1.addBike(database, bike3);
    	p1.addBike(database, bike4);
    	
    	p1.setDailyRentalPrice(bt1, new BigDecimal(10));	//Mountain
    	p1.setDailyRentalPrice(bt2, new BigDecimal(15));	//r
    	p1.setDailyRentalPrice(bt3, new BigDecimal(8));		//e
    	p1.setDailyRentalPrice(bt4, new BigDecimal(20));	//h
    	
    	p2.addBike(database, bike5);
    	p2.addBike(database, bike6);
    	p2.addBike(database, bike7);
    	
    	p2.setDailyRentalPrice(bt5, new BigDecimal(15));	//Mountain
    	p2.setDailyRentalPrice(bt6, new BigDecimal(12));	//r
    	p2.setDailyRentalPrice(bt7, new BigDecimal(9));		//e
    	
    	DateRange dr1 = new DateRange(LocalDate.of(2019, 5, 26),
                LocalDate.of(2019, 8, 23));
    	DateRange dr2 = new DateRange(LocalDate.of(2018, 8, 23),
                LocalDate.of(2019, 1, 8));
    	    		
    	String pi1 = "zfb";
    	c1.setPaymentInfo(pi1);
    	Order o1 = c1.bookQuote(items1, dr1, true, pi1);
    	Order result3 = new Order(items1, dr1, true);
    	
    	String pi2 = "wx";
    	c2.setPaymentInfo(pi2);
    	Order result4 = new Order(items2, dr2, false);
    	
    	ArrayList<Bike> items3 = new ArrayList<>(items2);
    	Order o2 = c2.bookQuote(items3, dr2, false, pi2);
    	boolean b3 = (o1.Equals(result3));	
    	assertTrue(b3);
    	boolean b4 = (o2.Equals(result4));
    	assertFalse(b4);
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Return Bike Attributes/////////////////////////////////////
    @BeforeEach
    void setUp3() throws Exception {
    	  // Setup mock delivery service before each tests
          DeliveryServiceFactory.setupMockDeliveryService();
        
        
          ///////Customer #1 #2 #3
	      cusL1 = new Location("EJ9 3LN", "South district");
	      cusL2 = new Location("GC3 9GG", "North district");
	      cusL3 = new Location("PP2 1SS", "East district");
	      
	      c1 = new Customer("ilovetwy@126.com", "Xukun Cai", cusL1, "18611440569");
	      c2 = new Customer("junjungkook@163.com", "Jung kook", cusL2, "13701727364");
	      c3 = new Customer("parkjimin@qq.com", "Jimin", cusL3, "18273644372");
	    
	      //////////////////////////////////////////////////////////
	      ///////Provider #1
	      l1 = new Location("EH8 9RD", "Salisbury Court");
	      
	      p1 = new Provider("846260512@qq.com", "Jane", l1, "13801229385");
	      
	      reVal1 = new BigDecimal(999.99);	//Mountain bike
	      reVal2 = new BigDecimal(399.99);	//Road bike
	      reVal3 = new BigDecimal(1999.99);	//Ebike
	      reVal4 = new BigDecimal(199.99);	//Hybrid bike
	      
	      depoRate1 = new BigDecimal(0.35);	//Mountain bike
	      depoRate2 = new BigDecimal(0.5);	//Road bike
	      depoRate3 = new BigDecimal(0.4);	//Ebike
	      depoRate4 = new BigDecimal(0.1);	//Hybrid bike
	      
	      bt1 = new BikeType("Mountain bike", reVal1, depoRate1);
	      bt2 = new BikeType("Road bike", reVal2, depoRate2);
	      bt3 = new BikeType("Ebike", reVal3, depoRate3);
	      bt4 = new BikeType("Hybrid bike", reVal4, depoRate4);
	      
	      bike1 = new Bike(bt1, l1);
	      bike2 = new Bike(bt2, l1);   
	      bike3 = new Bike(bt3, l1);
	      bike4 = new Bike(bt4, l1);
	      
	      bikes1 = new ArrayList<>();
	      bikes1.add(bike1);
	      bikes1.add(bike2);
	      bikes1.add(bike3);
	      bikes1.add(bike4);
	     
	      database.setBikeGarage(p1.email, bikes1);
	      
	      //////////////////////////////////////////////////////////
	      ///////Provider #2
		
	      l2 = new Location("EH3 9FE", "Chalmers Street");
		
	      p2 = new Provider("2249820164@qq.com", "Chloe", l2, "13801229385");
	      
	      reVal5 = new BigDecimal(899.99);	//Mountain bike
	      reVal6 = new BigDecimal(299.99);	//Road bike
	      reVal7 = new BigDecimal(1599.99);	//Ebike	
		
	      depoRate5 = new BigDecimal(0.1);	//m	
	      depoRate6 = new BigDecimal(0.2);	//r
	      depoRate7 = new BigDecimal(0.15);	//e
		
	      bt5 = new BikeType("Mountain bike", reVal5, depoRate5);
	      bt6 = new BikeType("Road bike", reVal6, depoRate6);
	      bt7 = new BikeType("Ebike", reVal7, depoRate7);       
		
	      bike5 = new Bike(bt5, l2);
	      bike6 = new Bike(bt6, l2);   
	      bike7 = new Bike(bt7, l2);
		
	      bikes2 = new ArrayList<>();
	      bikes2.add(bike5);
	      bikes2.add(bike6);
	      bikes2.add(bike7);		
		
	      database.setBikeGarage(p2.email, bikes2);
	      
	      //////////////////////////////////////////////////////////
	      ///////Provider #3
	      
	      l3 = new Location("EJ3 9NN", "MY PALACE");
	      
	      p3 = new Provider("852509904@qq.com", "Edith", l3, "13801229385");
	      
	      reVal8 = new BigDecimal(1299.99);	//Mountain bike
	      reVal9 = new BigDecimal(899.99);	//Road bike
	      
	      depoRate8 = new BigDecimal(0.25);	//m
	      depoRate9 = new BigDecimal(0.2);	//r
	      
	      bt8 = new BikeType("Mountain bike", reVal8, depoRate8);
	      bt9 = new BikeType("Road bike", reVal9, depoRate9);     
	      
	      bike8 = new Bike(bt8, l3);
	      bike9 = new Bike(bt9, l3);    
	      
	      bikes3 = new ArrayList<>();
	      bikes3.add(bike8);
	      bikes3.add(bike9);
	      
	      database.setBikeGarage(p3.email, bikes3); 
    }
    
    @Test
    void testReturnBikeToOriProv() {
    	
    	//all three bikes are from the same provider p1
    	ArrayList<Bike> items1 = new ArrayList<>();
    	items1.add(bike1);
    	items1.add(bike4);
    	items1.add(bike7);
    	//all three bikes are from the same provider p2
    	ArrayList<Bike> items2 = new ArrayList<>();
    	items2.add(bike2);
    	items2.add(bike5);
    	items2.add(bike8);
    	//all three bikes are from the same provider p3
    	ArrayList<Bike> items3 = new ArrayList<>();
    	items3.add(bike3);
    	items3.add(bike6);
    	items3.add(bike9);
    	
    	DateRange dr1 = new DateRange(LocalDate.of(2019, 5, 26),
    								  LocalDate.of(2019, 8, 23));
    	DateRange dr2 = new DateRange(LocalDate.of(2018, 8, 23),
                					  LocalDate.of(2019, 1, 8));
    	DateRange dr3 = new DateRange(LocalDate.of(2018, 9, 23),
                					  LocalDate.of(2019, 1, 8));
    	
    	//setting paymentInfo for 3 customers
    	String pi1 = "zfb";
    	c1.setPaymentInfo(pi1);
    	
    	String pi2 = "wx";
    	c2.setPaymentInfo(pi2);
    	
    	String pi3 = "bank";
    	c3.setPaymentInfo(pi3);
    	
    	//setting the database of provider#1
    	p1.addBike(database, bike1);
    	p1.addBike(database, bike4);
    	p1.addBike(database, bike7);
    	   	
    	p1.setDailyRentalPrice(bt1, new BigDecimal(10));	//Mountain
    	p1.setDailyRentalPrice(bt2, new BigDecimal(15));	//r
    	p1.setDailyRentalPrice(bt3, new BigDecimal(8));		//e
    	p1.setDailyRentalPrice(bt4, new BigDecimal(20));	//h
    	
    	p1.setDailyRentalPrice(bt1, new BigDecimal(10));	//Mountain
    	p1.setDailyRentalPrice(bt7, new BigDecimal(15));	//r
    	p1.setDailyRentalPrice(bt4, new BigDecimal(20));	//h
    	
    	//setting the database of provider#2
    	p2.addBike(database, bike2);
    	p2.addBike(database, bike5);
    	p2.addBike(database, bike8);
    	
    	p2.setDailyRentalPrice(bt5, new BigDecimal(15));	//Mountain
    	p2.setDailyRentalPrice(bt2, new BigDecimal(12));	//r
    	p2.setDailyRentalPrice(bt8, new BigDecimal(9));		//e
    	
    	//setting the database of provider#3
    	p3.addBike(database, bike3);
    	p3.addBike(database, bike6);
    	p3.addBike(database, bike9);
    	
    	p3.setDailyRentalPrice(bt6, new BigDecimal(10));	//Mountain
    	p3.setDailyRentalPrice(bt9, new BigDecimal(12));	//r
    	p3.setDailyRentalPrice(bt3, new BigDecimal(12));
    	
    	//setting order#1 #2 #3
    	Order o1 = c1.bookQuote(items1, dr1, true, pi1); 
    	Order o2 = c2.bookQuote(items2, dr2, false, pi2);
    	Order o3 = c3.bookQuote(items3, dr3, false, pi3);
    	c1.confirmReceivedBike(o1);
    	c2.confirmReceivedBike(o2);
    	c3.confirmReceivedBike(o3);    	
    	
    	boolean b1 = c1.returnBikeToOriProvider(o1);

    	assertTrue(b1);
    	
    	
    	//////// RETURN TO PARTNER
    	p1.setPartners(new ArrayList<>());
    	p2.setPartners(new ArrayList<>());
    	p3.setPartners(new ArrayList<>());
    	
    	p1.joinPartnership(p2);
    	p3.joinPartnership(p2);
    	
    	//test p1 partnership
    	
    	//o1 contains three bikes from p1: Jane which is a partner of p2
    	
    	boolean b2 = c2.returnBikeToParProvider(o1, p2);
    	assertTrue(b2);
    	
    	//o3 contains three bikes form p3: Edith which is not a partner of p1

    	boolean b3 = c3.returnBikeToParProvider(o3, p1);
    	assertFalse(b3);  	
    	
    }
}
