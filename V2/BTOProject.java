import java.util.ArrayList;
/**
 * Class BTOProject - Represents a Build-To-Order project with flat types, availability, and project timeline.
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
 * Method BTOProject - performs the BTOProject operation.
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
 * Method addFlatType - performs the addFlatType operation.
 */
    public void addFlatType(String flatType, int units) {
            for (int i = 0; i < units; i++) {
            String unitNumber = flatType + "-" + (availFlats.size() + 1);
            availFlats.add(new Flat(flatType, unitNumber));
    }

}
/**
 * Method getAvailUnits - performs the getAvailUnits operation.
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
 * Method updateFlatAvailability - performs the updateFlatAvailability operation.
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
 * Method assignedFlats - performs the assignedFlats operation.
 */
    public Flat[] assignedFlats() {
        return availFlats.toArray(new Flat[0]);
    }
/**
 * Method assignOfficer - performs the assignOfficer operation.
 */
    public void assignOfficer(HDBOfficer officer) {
        assignedOfficers.add(officer);
    }
/**
 * Method getAvailFlats - performs the getAvailFlats operation.
 */
    public ArrayList<Flat> getAvailFlats() {
        return availFlats;
    }

/**
 * Method getAssignedOfficers - performs the getAssignedOfficers operation.
 */
    public ArrayList<HDBOfficer> getAssignedOfficers() {
        return assignedOfficers;
    }

/**
 * Method getProjName - performs the getProjName operation.
 */
    public String getProjName() {
        return projName;
    }

/**
 * Method getNeighbourhood - performs the getNeighbourhood operation.
 */
    public String getNeighbourhood() {
        return neighbourhood;
    }

/**
 * Method getApplicationOpenDate - performs the getApplicationOpenDate operation.
 */
    public String getApplicationOpenDate() {
        return applicationOpenDate;
    }
/**
 * Method getApplicationCloseDate - performs the getApplicationCloseDate operation.
 */
    public String getApplicationCloseDate() {
        return applicationCloseDate;
    }

/**
 * Method isVisible - performs the isVisible operation.
 */
    public boolean isVisible() {
        return visibility;
    }

/**
 * Method setVisibility - performs the setVisibility operation.
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
 * Method toString - performs the toString operation.
 */
    public String toString() {
        return "Project: " + projName + ", Neighbourhood: " + neighbourhood +
                ", Open: " + applicationOpenDate + ", Close: " + applicationCloseDate +
                ", Visible: " + visibility;
    }

    
}
