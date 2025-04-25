import java.util.ArrayList;
/**
 * Class BTOProject - Auto-generated Javadoc documentation.
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
 * Method BTOProject - auto-documented method.
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
 * Method addFlatType - auto-documented method.
 */
    public void addFlatType(String flatType, int units) {
            for (int i = 0; i < units; i++) {
            String unitNumber = flatType + "-" + (availFlats.size() + 1);
            availFlats.add(new Flat(flatType, unitNumber));
    }

}
/**
 * Method getAvailUnits - auto-documented method.
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
 * Method updateFlatAvailability - auto-documented method.
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
 * Method assignedFlats - auto-documented method.
 */
    public Flat[] assignedFlats() {
        return availFlats.toArray(new Flat[0]);
    }
/**
 * Method assignOfficer - auto-documented method.
 */
    public void assignOfficer(HDBOfficer officer) {
        assignedOfficers.add(officer);
    }
/**
 * Method getAvailFlats - auto-documented method.
 */
    public ArrayList<Flat> getAvailFlats() {
        return availFlats;
    }

/**
 * Method getAssignedOfficers - auto-documented method.
 */
    public ArrayList<HDBOfficer> getAssignedOfficers() {
        return assignedOfficers;
    }

/**
 * Method getProjName - auto-documented method.
 */
    public String getProjName() {
        return projName;
    }

/**
 * Method getNeighbourhood - auto-documented method.
 */
    public String getNeighbourhood() {
        return neighbourhood;
    }

/**
 * Method getApplicationOpenDate - auto-documented method.
 */
    public String getApplicationOpenDate() {
        return applicationOpenDate;
    }
/**
 * Method getApplicationCloseDate - auto-documented method.
 */
    public String getApplicationCloseDate() {
        return applicationCloseDate;
    }

/**
 * Method isVisible - auto-documented method.
 */
    public boolean isVisible() {
        return visibility;
    }

/**
 * Method setVisibility - auto-documented method.
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
 * Method toString - auto-documented method.
 */
    public String toString() {
        return "Project: " + projName + ", Neighbourhood: " + neighbourhood +
                ", Open: " + applicationOpenDate + ", Close: " + applicationCloseDate +
                ", Visible: " + visibility;
    }

    
}
