import java.util.ArrayList;
/**
<<<<<<< HEAD
 * Class BTOProject - Represents a Build-To-Order project with flat types, availability, and project timeline.
=======
 * Class BTOProject - Auto-generated Javadoc documentation.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
public class BTOProject {

    private String projName;
    private String neighbourhood;
    private String applicationOpenDate;
    private String applicationCloseDate;
    private boolean visibility;
    private ArrayList<Flat> availFlats;
    private ArrayList<HDBOfficer> assignedOfficers;
    private int maxOfficerSlots;

/**
<<<<<<< HEAD
 * Method BTOProject - performs the BTOProject operation.
=======
 * Method BTOProject - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public BTOProject(String projName, String neighbourhood, String applicationOpenDate, String applicationCloseDate) {
        this.projName = projName;
        this.neighbourhood = neighbourhood;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.visibility = true;
        this.availFlats = new ArrayList<>();
        this.assignedOfficers = new ArrayList<>();
    }

    public int getMaxOfficerSlots()
    {
        return maxOfficerSlots;
    }

	
/**
<<<<<<< HEAD
 * Method addFlatType - performs the addFlatType operation.
=======
 * Method addFlatType - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public void addFlatType(String flatType, int units) {
            for (int i = 0; i < units; i++) {
            String unitNumber = flatType + "-" + (availFlats.size() + 1);
            availFlats.add(new Flat(flatType, unitNumber));
    }

}
/**
<<<<<<< HEAD
 * Method getAvailUnits - performs the getAvailUnits operation.
=======
 * Method getAvailUnits - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public int getAvailUnits(String flatType) {
        int count = 0;
        for (Flat flat : availFlats) {
            if (flat.getFlatType().equals(flatType) && !flat.getbookstatus()) {
                count++;
            }
        }
        return count;
    }
/**
<<<<<<< HEAD
 * Method updateFlatAvailability - performs the updateFlatAvailability operation.
=======
 * Method updateFlatAvailability - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public void updateFlatAvailability(String flatType, int newFlatCount) {
      int available = 0;
      for (Flat flat : availFlats) {
        if (flat.getFlatType().equals(flatType) && !flat.getbookstatus()) {
            available++;
        }
    }

      int toAdd = newFlatCount - available;

      if (toAdd > 0) {
        addFlatType(flatType, toAdd);
    }
}
/**
<<<<<<< HEAD
 * Method assignedFlats - performs the assignedFlats operation.
=======
 * Method assignedFlats - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public Flat[] assignedFlats() {
        return availFlats.toArray(new Flat[0]);
    }
/**
<<<<<<< HEAD
 * Method assignOfficer - performs the assignOfficer operation.
=======
 * Method assignOfficer - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public void assignOfficer(HDBOfficer officer) {
        assignedOfficers.add(officer);
    }
/**
<<<<<<< HEAD
 * Method getAvailFlats - performs the getAvailFlats operation.
=======
 * Method getAvailFlats - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public ArrayList<Flat> getAvailFlats() {
        return availFlats;
    }

/**
<<<<<<< HEAD
 * Method getAssignedOfficers - performs the getAssignedOfficers operation.
=======
 * Method getAssignedOfficers - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public ArrayList<HDBOfficer> getAssignedOfficers() {
        return assignedOfficers;
    }

/**
<<<<<<< HEAD
 * Method getProjName - performs the getProjName operation.
=======
 * Method getProjName - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public String getProjName() {
        return projName;
    }

/**
<<<<<<< HEAD
 * Method getNeighbourhood - performs the getNeighbourhood operation.
=======
 * Method getNeighbourhood - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public String getNeighbourhood() {
        return neighbourhood;
    }

/**
<<<<<<< HEAD
 * Method getApplicationOpenDate - performs the getApplicationOpenDate operation.
=======
 * Method getApplicationOpenDate - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public String getApplicationOpenDate() {
        return applicationOpenDate;
    }
/**
<<<<<<< HEAD
 * Method getApplicationCloseDate - performs the getApplicationCloseDate operation.
=======
 * Method getApplicationCloseDate - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public String getApplicationCloseDate() {
        return applicationCloseDate;
    }

/**
<<<<<<< HEAD
 * Method isVisible - performs the isVisible operation.
=======
 * Method isVisible - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public boolean isVisible() {
        return visibility;
    }

/**
<<<<<<< HEAD
 * Method setVisibility - performs the setVisibility operation.
=======
 * Method setVisibility - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setProjectName(String projName)
    {
        this.projName = projName;
    }

    public void setNeighbourhood(String neighbourhood)
    {
        this.neighbourhood = neighbourhood;
    }

    public void setApplicationOpenDate(String appOpenDate)
    {
        this.applicationOpenDate = appOpenDate;
    }

    public void setApplicationCloseDate(String appCloseDate)
    {
        this.applicationCloseDate = appCloseDate;
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
        return "Project: " + projName + ", Neighbourhood: " + neighbourhood +
                ", Open: " + applicationOpenDate + ", Close: " + applicationCloseDate +
                ", Visible: " + visibility;
    }

    
}
