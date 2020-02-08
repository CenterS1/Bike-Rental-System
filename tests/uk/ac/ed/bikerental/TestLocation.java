package uk.ac.ed.bikerental;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
	Location a,b,c,d;
	
    @BeforeEach
    void setUp() throws Exception {
        // TODO: setup some resources before each test
		a = new Location ("EH8 9RD","Salisbury Court");
		b = new Location ("EH8 9XQ","Windmill Place");
		c = new Location ("G12 8QQ","Glasgow University");
		d = new Location ("EH8 9LY","Edinburgh University");
    }
    
    @Test
    public void testIsNearTo() { 
    	assertEquals(true, a.isNearTo(b));
    	assertEquals(false, c.isNearTo(d));
    	assertEquals(false, a.isNearTo(c));
    }
    
    @Test
    public void testgetPostCode() {
    	assertEquals("EH8 9RD", a.getPostcode());
    }
    
    @Test
    public void testgetAddress() {
    	assertEquals("Salisbury Court", a.getAddress());
    }
}
