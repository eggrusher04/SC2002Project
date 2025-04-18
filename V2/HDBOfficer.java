public class HDBOfficer extends Employees implements View, ProjectManagement, ApplicantManagement {

	private BTOProject assignedProj;
	// Getter and Setter for assignedProj
    public BTOProject getAssignedProj() {
        return assignedProj;
    }

    public void setAssignedProj(BTOProject assignedProj) {
        this.assignedProj = assignedProj;
    }


	// Constructor
    public HDBOfficer(String staffID, String nric, String password, String name, BTOProject assignedProj) {
        super(staffID, nric, password, name);
        this.assignedProj = assignedProj; 
    }

	/**
	 * 
	 * @param applicant
	 */
	public void generateReceipt(Applicant applicant) {
		// TODO - implement HDBOfficer.generateReceipt
		System.out.println("Generating receipt for applicant ID: " + applicant.getApplicantID());
        System.out.println("Project: " + (assignedProj != null ? assignedProj.getProjectName() : "None"));
        System.out.println("Status: " + applicant.getApplicationStatus());
		throw new UnsupportedOperationException();
	}

	public String viewRegStatus() {
		// TODO - implement HDBOfficer.viewRegStatus
		if (assignedProj != null) {
            return "Officer is registered to handle project: " + assignedProj.getProjectName();
        } else {
            return "No project assigned.";
        }
		throw new UnsupportedOperationException();
	}

	

}