package uk.ac.ed.bikerental;
import java.time.LocalDate;

/**
 * This DateRange class is for representing a collection a successive days.
 * DateRange class encapsulates information needed for a duration of time  
 * @author s1820742
 * @author s1810646
 */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class DateRange {
	
	/**
	 * The start and end days of a period
	 */
    private LocalDate start, end;
    
      
    /**
     * 
     * Constructor of DateRange
     * Uses the start date and end date to get a lock on specific duration.
     * <p>
     * {@link LocalDate} it is a class encapsulates the year-month-day-time 
     * for a day at user's current time zone.
     * 
     * @param start is a date of type LocalDate represent the first day of a period
     * @param end is a date of type LocalDate represent day next to the last day of a period
     */
    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
    
    
    /**
     * get and set the first day of the duration
     * @return starting day
     */
    public LocalDate getStart() {
        return this.start;
    }
    public void setStart(LocalDate s) {
    	this.start = s;
    }
    
    /**
     * get and set the day after the last day of the duration
     * @return the day after the last day
     */
    public LocalDate getEnd() {
        return this.end;
    }
    public void setEnd(LocalDate e) {
    	this.end = e;
    }
    
    
    /**
     * calculate the years gap between the start date and end date
     * by using {@link ChronoUnits} which is a standard unit
     * for date period
     * @return number of years between the start and end of this duration
     */
    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }
    
    
    /**
     * calculate the days difference between the start date and end date
     * by using {@link ChronoUnits} which is a standard unit
     * for date period
     * @return number of days between the start and end of this duration
     */
    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }
    
    
    /**
     * check whether two durations of days overlap or not
     * if one duration contains the day(s) in another duration
     * then return true
     * if two duration are independent then return false
     * @param other is the duration needed to be compared with.
     * @return true or false whether two durations overlap
     */
    public boolean overlaps(DateRange other) {
    	
    	if((other.getStart().isBefore(this.getEnd())) && (other.getEnd().isAfter(this.getEnd())) ||
    	   (other.getEnd().isAfter(this.getStart())) && (other.getStart().isBefore(this.getStart())) ||
    	   (other.getStart().isAfter(this.getStart())) && (other.getEnd().isBefore(this.getEnd())) ||
    	   (this.getStart().isBefore(other.getEnd())) && (this.getEnd().isAfter(other.getEnd())) ||
    	   (this.getEnd().isAfter(other.getStart())) && (this.getStart().isBefore(other.getStart())) ||
    	   (this.getStart().isAfter(other.getStart())) && (this.getEnd().isBefore(other.getEnd()))) {
    		
    		return true;
    	}
    	else {
    		return false;
    	}
        
    }

    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    @Override
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }
    
    // You can add your own methods here
    /**
     * calculate the number of days in these period
     * by using {@link ChronoUnit} method.
     * @return the days difference
     */
    public long countLength() {
    	long d = ChronoUnit.DAYS.between(start, end); 	
    	return d;
    }
}
