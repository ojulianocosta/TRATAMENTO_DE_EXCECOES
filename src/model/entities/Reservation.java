package model.entities;

import model.exceptions.DomainException;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Reservation {
    private Integer roomNumber;
    private Date checkIn;
    private Date checkOut;
    private static SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy");

    public Reservation(Integer roomNumber, Date checkIn, Date checkOut) {
        if (!checkOut.after(checkIn)){
            throw new DomainException("Check-out date must be after check-in date");
        }
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public long duration(){
        long difference = checkOut.getTime() - checkIn.getTime();
        return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
    }

    public void updateDates(Date checkIn, Date checkOut){
        Date now = new Date();
        if (checkIn.before(now) || checkOut.before(now)) {
            throw new DomainException("Error in reservation: reservation dates for update must be future dates");
        }
        if (!checkOut.after(checkIn)) {
            throw new DomainException("Error in reservation: Check-out date must be after check-in date");
        }
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString(){
        return "Room "
                + roomNumber
                + ", check-in: "
                + formattedDate.format(checkIn)
                + ", check-out:  "
                + formattedDate.format(checkOut)
                + ", "
                + duration()
                + " nights";
    }
}
