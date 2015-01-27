package com.jason;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class QueueService {
    private PriorityBlockingQueue<HotelChange> hotelChanges = new PriorityBlockingQueue<HotelChange>(3000, new Comparator<HotelChange>() {
        @Override
        public int compare(HotelChange o1, HotelChange o2) {
            return o1.getComparableKey().compareTo(o2.getComparableKey());
        }
    });

    public HotelChange takeHotelChange() {
        try {
            return hotelChanges.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addTask(HotelChange hotelChange) {
        hotelChanges.add(hotelChange);
    }
}
