public class Flat {
    private String flatType;
    private String unitNumber;
    private boolean isBooked;

    public Flat(String flatType, String unitNumber) {
        this.flatType = flatType;
        this.unitNumber = unitNumber;
        this.isBooked = false;
    }

    public String getFlatType() {
        return flatType;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public boolean getbookstatus() {
        return isBooked;
    }

    public void book() {
        if (!isBooked) {
            isBooked = true;
        }
    }

    public void cancelBooking() {
        if (isBooked) {
            isBooked = false;
        }
    }

    @Override
    public String toString() {
        return "Flat Type: " + flatType + ", Unit: " + unitNumber + ", Booked: " + isBooked;
    }
}
