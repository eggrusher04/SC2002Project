public class HDBOfficer extends Employees implements View, ProjectManagement, ApplicantManagement {

	private BTOProject assignedProj;
	private String name;
	private String nric;
	private String password;
	private int age;
	private boolean maritalStatus;

	//Constructor
	public HDBOfficer(String name,String nric, String pw, int age, boolean maritalStatus)
	{
		this.name = name;
		this.nric = nric;
		this.password = Users.password;
		this.age = age;
		this.maritalStatus = maritalStatus;

		/*this.applicationStatus = appStats;
		this.appliedProject = appliedProj;
		this.enquiries = enquiry;*/

		//As for the commented out code above^, still thinking how to do its constructor considering it can be an applicant
	}


	public String getName()
	{
		return name;
	}

	public String getNRIC()
	{
		return nric;
	}

	public String getPassword()
	{
		return password;
	}

	public int getAge()
	{
		return age;
	}

	public boolean getMaritalStatus()
	{
		return maritalStatus;
	}

	public boolean login(String nric, String pw)
	{
		if(this.nric.equals(nric) && this.password.equals(pw))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void changePassword(String newPw)
	{
		this.password = newPw;
	}

	public String viewListOfProjects()
	{
		//Read the listOfProjects from the csv
		return "blablabla";
	}
	

	public String viewEnquiry(String message)
	{
		//implement viewEnquiry here
		return "blablabla";
	}

	public void replyEnquiry()
	{
		//Implement replyEnquiry
	}

	public String viewProjDetails(BTOProject project)
	{
		//implement a method to view project details
		return "blablabal";
	}

	public void regProject(BTOProject project)
	{
		//register for project function
	}

	public void updateFlatAvail(String flatType, int unitsLeft)
	{
		//Oficer to update flats available and save into the ProjectList CSV
	}

	public void retrieveApplicant(Applicant applicant)
	{
		//retrieve applicant details here
	}

	public void updateApplicantStatus(Applicant applicant)
	{
		//Change the status of an applicant from pending to booked or otherwise
	}

	public boolean isEligibleForApplicant(Applicant applicant)
	{
		//check whether the officer can be an applicant for a hdb project
		return false; //temporary
	}
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
		System.out.println("Generating receipt for applicant ID: " + applicant.getNRIC() + ", Name: " + applicant.getName()); //updated the receipt to include NRIC and Name
        System.out.println("Age: " + applicant.getAge());
		System.out.println("Marital Status: " + applicant.getMaritalStatus());
		//System.out.println("Project: " + (assignedProj != null ? assignedProj.getProjectName() : "None"));
		//add the flat type and other project details
		throw new UnsupportedOperationException();
	}

	public String viewRegStatus() {
		// TODO - implement HDBOfficer.viewRegStatus
		/*if (assignedProj != null) {
            return "Officer is registered to handle project: " + assignedProj.getProjectName();
        } else {
            return "No project assigned.";
        }*/
		throw new UnsupportedOperationException();
	}

	

}