package uk.ac.ed.bikerental;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public interface PricingPolicy {

    public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice);
    public BigDecimal calculatePrice(ArrayList<Bike> bikes, DateRange duration);
}
