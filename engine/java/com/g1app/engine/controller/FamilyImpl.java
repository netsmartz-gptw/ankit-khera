package com.g1app.engine.controller;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;


public class FamilyImpl {
    /**
     * This method is use to calculate the Age.
     * @param dob
     * @return
     */
    public static boolean isAgeAboveEighteen(long dob) {
        Timestamp ts = new Timestamp(dob);
        LocalDate localDate = ts.toLocalDateTime().toLocalDate();
        LocalDate dob1 = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth());
        LocalDate curDate = LocalDate.now();
        Period period = Period.between(dob1, curDate);
        if(period.getYears() >= 18 ){
            return true;
        }else{
            return false;
        }
    }
}
