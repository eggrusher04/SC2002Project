import java.util.ArrayList;
public class BTOProject {

    private String projName;
    private String neighbourhood;
    private String applicationOpenDate;
    private String applicationCloseDate;
    private boolean visibility;
    private ArrayList<Flat> availFlats;
    private ArrayList<HDBOfficer> assignedOfficers;
    private int maxOfficerSlots;

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

	
    public void addFlatType(String flatType, int units) {
            for (int i = 0; i < units; i++) {
            String unitNumber = flatType + "-" + (availFlats.size() + 1);
            availFlats.add(new Flat(flatType, unitNumber));
    }

}
    public int getAvailUnits(String flatType) {
        int count = 0;
        for (Flat flat : availFlats) {
            if (flat.getFlatType().equals(flatType) && !flat.getbookstatus()) {
                count++;
            }
        }
        return count;
    }
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
    public Flat[] assignedFlats() {
        return availFlats.toArray(new Flat[0]);
    }
    public void assignOfficer(HDBOfficer officer) {
        assignedOfficers.add(officer);
    }
    public ArrayList<Flat> getAvailFlats() {
        return availFlats;
    }

    public ArrayList<HDBOfficer> getAssignedOfficers() {
        return assignedOfficers;
    }

    public String getProjName() {
        return projName;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getApplicationOpenDate() {
        return applicationOpenDate;
    }
    public String getApplicationCloseDate() {
        return applicationCloseDate;
    }

    public boolean isVisible() {
        return visibility;
    }

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
    public String toString() {
        return "Project: " + projName + ", Neighbourhood: " + neighbourhood +
                ", Open: " + applicationOpenDate + ", Close: " + applicationCloseDate +
                ", Visible: " + visibility;
    }
}
