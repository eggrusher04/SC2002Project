/**
 * Class Flat - Represents the flat in the system.
 */
public class Flat {
    private String flatType;
    private String unitNumber;
    private boolean isBooked;

/**
 * Method Flat - performs the Flat operation.
 */
    public Flat(String flatType, String unitNumber) {
        this.flatType = flatType;
        this.unitNumber = unitNumber;
        this.isBooked = false;
    }

/**
 * Method getFlatType - performs the getFlatType operation.
 */
    public String getFlatType() {
        return flatType;
    }

/**
 * Method getUnitNumber - performs the getUnitNumber operation.
 */
    public String getUnitNumber() {
        return unitNumber;
    }

/**
 * Method getbookstatus - performs the getbookstatus operation.
 */
    public boolean getbookstatus() {
        return isBooked;
    }

/**
 * Method book - performs the book operation.
 */
    public void book() {
        if (!isBooked) {
            isBooked = true;
        }
    }

/**
 * Method cancelBooking - performs the cancelBooking operation.
 */
    public void cancelBooking() {
        if (isBooked) {
            isBooked = false;
        }
    }

    @Override
/**
 * Method toString - performs the toString operation.
 */
    public String toString() {
        return "Flat Type: " + flatType + ", Unit: " + unitNumber + ", Booked: " + isBooked;
    }
}
