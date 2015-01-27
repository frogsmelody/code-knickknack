package com.jason;
import java.util.Date;

public class PushService {
    public void push(HotelChange hotelChange) {
        System.out.println(String.format("%s: push begin,%s", hotelChange.getHotelCode(), LocalDateTimes.format(new Date())));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
