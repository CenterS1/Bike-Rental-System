package uk.ac.ed.bikerental;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDateRange {
    private DateRange dateRange1, dateRange2, dateRange3;

    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5),
                LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7),
                LocalDate.of(2018, 1, 10));
    }

    // Sample JUnit tests checking toYears works
    @Test
    void testToYears1() {
        assertEquals(0, this.dateRange1.toYears());
    }

    @Test
    void testToYears3() {
        assertEquals(3, this.dateRange3.toYears());
    }

    @Test
    void testOverlapsTrue() {
    	dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
        dateRange2 = new DateRange(LocalDate.of(2019, 1, 5),
                LocalDate.of(2019, 1, 8));
        dateRange3 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 2, 8));
        
        boolean b1 = dateRange1.overlaps(dateRange2);
        boolean b2 = dateRange1.overlaps(dateRange3);
        boolean b3 = dateRange2.overlaps(dateRange3);
        
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
        
        assertEquals(b1, true); 
        assertEquals(b2, true); 
        assertEquals(b3, true); 
    }

    @Test
    void testOverlapsFalse() {
    	this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2018, 12, 5),
                LocalDate.of(2019, 1, 1));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 12),
                LocalDate.of(2018, 1, 23));
        
        boolean b1 = dateRange1.overlaps(dateRange2);
        boolean b2 = dateRange1.overlaps(dateRange3);
        boolean b3 = dateRange2.overlaps(dateRange3);
        
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
        
        assertEquals(b1, false);
        assertEquals(b2, false);
        assertEquals(b3, false);
    }

    // TODO: put some of your own unit tests here
}
