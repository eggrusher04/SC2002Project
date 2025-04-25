/**
<<<<<<< HEAD
 * Class Flat - Represents the flat in the system.
=======
 * Class Flat - Auto-generated Javadoc documentation.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
public class Flat {
    private String flatType;
    private String unitNumber;
    private boolean isBooked;

/**
<<<<<<< HEAD
 * Method Flat - performs the Flat operation.
=======
 * Method Flat - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public Flat(String flatType, String unitNumber) {
        this.flatType = flatType;
        this.unitNumber = unitNumber;
        this.isBooked = false;
    }

/**
<<<<<<< HEAD
 * Method getFlatType - performs the getFlatType operation.
=======
 * Method getFlatType - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public String getFlatType() {
        return flatType;
    }

/**
<<<<<<< HEAD
 * Method getUnitNumber - performs the getUnitNumber operation.
=======
 * Method getUnitNumber - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public String getUnitNumber() {
        return unitNumber;
    }

/**
<<<<<<< HEAD
 * Method getbookstatus - performs the getbookstatus operation.
=======
 * Method getbookstatus - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public boolean getbookstatus() {
        return isBooked;
    }

/**
<<<<<<< HEAD
 * Method book - performs the book operation.
=======
 * Method book - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public void book() {
        if (!isBooked) {
            isBooked = true;
        }
    }

/**
<<<<<<< HEAD
 * Method cancelBooking - performs the cancelBooking operation.
=======
 * Method cancelBooking - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public void cancelBooking() {
        if (isBooked) {
            isBooked = false;
        }
    }

    @Override
/**
<<<<<<< HEAD
 * Method toString - performs the toString operation.
=======
 * Method toString - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public String toString() {
        return "Flat Type: " + flatType + ", Unit: " + unitNumber + ", Booked: " + isBooked;
    }
}
