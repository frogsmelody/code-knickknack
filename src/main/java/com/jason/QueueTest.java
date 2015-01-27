package com.jason;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class QueueTest {
    private PriorityBlockingQueue<HotelChange> createQueue() {
        PriorityBlockingQueue<HotelChange> hotelChanges = new PriorityBlockingQueue<HotelChange>(3000, new Comparator<HotelChange>() {
            @Override
            public int compare(HotelChange o1, HotelChange o2) {
                return o1.getComparableKey().compareTo(o2.getComparableKey());
            }
        });
        hotelChanges.put(createHotelChange("Hotel-01", null, LocalDateTimes.of("2015-02-01 08:21:22").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-02", null, LocalDateTimes.of("2015-02-01 08:21:22").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-03", null, LocalDateTimes.of("2015-02-01 09:21:22").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-04-1", Status.CANCELLED, LocalDateTimes.of("2015-02-01 08:21:22").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-04-2", Status.CANCELLED, LocalDateTimes.of("2015-02-01 08:21:25").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-05", null, LocalDateTimes.of("2015-02-01 08:21:20").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-06-1", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:21:20").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-06-2", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:21:22").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-06-3", Status.RE_FETCH, LocalDateTimes.of("2015-02-01 08:21:02").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-07", null, LocalDateTimes.of("2015-02-01 08:21:20").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-08", null, LocalDateTimes.of("2015-02-01 08:21:05").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-09", null, LocalDateTimes.of("2015-02-01 08:21:10").toDate().getTime()));
        hotelChanges.put(createHotelChange("Hotel-10", null, LocalDateTimes.of("2015-02-01 08:21:25").toDate().getTime()));
        return hotelChanges;
    }

    public void testName() throws Exception {
        PriorityBlockingQueue<HotelChange> hotelChanges = createQueue();
        while (hotelChanges.iterator().hasNext()) {
            System.out.println(hotelChanges.poll().getHotelCode());
        }
    }

    private HotelChange createHotelChange(String hotelCode, Status status, long timeStamp) {
        HotelChange hotelChange = new HotelChange(hotelCode);
        if (status != null) {
            if (status == Status.CANCELLED) {
                hotelChange.cancel();
            }
            if (status == Status.RE_FETCH) {
                hotelChange.reFetch();
            }
        }
        hotelChange.setTimeStamp(timeStamp);
        return hotelChange;
    }
}
