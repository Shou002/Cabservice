package com.projectSer.projectServices.DTO;

import com.projectSer.projectServices.enums.BookingStatus;
import java.util.Date;
import lombok.Data;

@Data
public class BookingResponse {
    private int booId;
    private String vehicleName;
    private String driverName;
    private String pickupLoc;
    private String destination;
    private Date date;
    private int rentalTime;
    private double distance;
    private double finalPrice;
    private BookingStatus status;

    public BookingResponse(int booId, String vehicleName, String driverName, String pickupLoc,
                           String destination, Date date, int rentalTime, double distance,
                           double finalPrice, BookingStatus status) {
        this.booId = booId;
        this.vehicleName = vehicleName;
        this.driverName = driverName;
        this.pickupLoc = pickupLoc;
        this.destination = destination;
        this.date = date;
        this.rentalTime = rentalTime;
        this.distance = distance;
        this.finalPrice = finalPrice;
        this.status = status;
    }
}
