/**
 * Class Flat - Auto-generated Javadoc documentation.
 */
public class Flat {
    private String flatType;
    private String unitNumber;
    private boolean isBooked;

/**
 * Method Flat - auto-documented method.
 */
    public Flat(String flatType, String unitNumber) {
        this.flatType = flatType;
        this.unitNumber = unitNumber;
        this.isBooked = false;
    }

/**
 * Method getFlatType - auto-documented method.
 */
    public String getFlatType() {
        return flatType;
    }

/**
 * Method getUnitNumber - auto-documented method.
 */
    public String getUnitNumber() {
        return unitNumber;
    }

/**
 * Method getbookstatus - auto-documented method.
 */
    public boolean getbookstatus() {
        return isBooked;
    }

/**
 * Method book - auto-documented method.
 */
    public void book() {
        if (!isBooked) {
            isBooked = true;
        }
    }

/**
 * Method cancelBooking - auto-documented method.
 */
    public void cancelBooking() {
        if (isBooked) {
            isBooked = false;
        }
    }

    @Override
/**
 * Method toString - auto-documented method.
 */
    public String toString() {
        return "Flat Type: " + flatType + ", Unit: " + unitNumber + ", Booked: " + isBooked;
    }
}
