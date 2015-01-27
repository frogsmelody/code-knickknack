package com.jason;

import java.util.Date;

public class HotelChange {
    private Status status = Status.PENDING;
    private String hotelCode;
    private long timeStamp = new Date().getTime();

    public HotelChange(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public void reFetch() {
        this.status = Status.RE_FETCH;
    }

    public void cancel() {
        this.status = Status.CANCELLED;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Status getStatus() {
        return status;
    }

    public String getComparableKey() {
        return String.format("%d:%d", status.getScore(), getTimeStamp());
    }
}
