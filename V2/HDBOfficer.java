public class HDBOfficer extends Employees implements View, ProjectManagement, ApplicantManagement {

	private BTOProject assignedProj;
	

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