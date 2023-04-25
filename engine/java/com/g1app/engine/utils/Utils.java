package com.g1app.engine.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;

public class Utils {

    public static int YYMMPrefix() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int yearTwoLastDigit = (year % 100);
        Formatter fmt1 = new Formatter();
        fmt1.format("%tm", date);
        int prefix = Integer.valueOf(String.valueOf(yearTwoLastDigit) + String.valueOf(fmt1));
        return prefix;
    }

    public static String OrderId(){
        String contain  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(12);
        for(int i=0; i<12; i++)
        {
            sb.append(contain.charAt(rnd.nextInt(contain.length())));
        }
        return sb.toString();
    }
}
