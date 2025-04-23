import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class HDBOfficer extends Employees implements View, ProjectManagement, ApplicantManagement {

	private BTOProject assignedProj;
	private String name;
	private String nric;
	private String password;
	private int age;
	private boolean maritalStatus;

	private String applicationStatus;
	private BTOProject appliedProject;
	private List<Enquiry> enquiries = new ArrayList<>();

	// First Constructor
	public HDBOfficer(String name,String nric, String pw, int age, boolean maritalStatus, String applicationStatus, BTOProject appliedProject, List<Enquiry> enquiries)
	{
		this.name = name;
		this.nric = nric;
		this.password = pw;
		this.age = age;
		this.maritalStatus = maritalStatus;
		this.applicationStatus = applicationStatus;
		this.appliedProject = appliedProject;
		this.enquiries = (enquiries != null) ? enquiries : new ArrayList<>();

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

	public String getApplicationStatus() {
    	return applicationStatus;
	}

	public BTOProject getAppliedProject() {
    	return appliedProject;
	}

	public List<Enquiry> getEnquiries() {
		return enquiries;
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
		// Read the listOfProjects from the csv 
		String filePath = "ProjectList.csv";
    	StringBuilder result = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean isHeader = true;

			while ((line = br.readLine()) != null) {
				if (isHeader) {
					isHeader = false; // Skip header line
					continue;
				}
				String[] fields = line.split(",");
				if (fields.length >= 1) {
					result.append("- ").append(fields[0]).append("\n"); // Assuming the first column is project name
				}
			}

			if (result.length() == 0) {
				return "No projects found in the file.";
			}

			return "Available Projects:\n" + result.toString();

		} catch (IOException e) {
			return "Error reading project list: " + e.getMessage();
		}
	}

	public String viewEnquiry(String message)
	{
		// View enquiries 
		if (enquiries == null || enquiries.isEmpty()) {
        return "No enquiries available.";
    }
		
		for (Enquiry e : enquiries) {
            if (e.getMessage().equalsIgnoreCase(message)) {
                return "Enquiry from " + e.getApplicant().getName() + ": " + e.getMessage();
            }
        }
        return "Enquiry not found.";
	}

	public void replyEnquiry()
	{
		// Simulate replying to the first enquiry in the list
        if (enquiries != null && !enquiries.isEmpty()) {
            Enquiry enquiry = enquiries.get(0);
            enquiry.setReply("Thank you for your enquiry. We will get back to you shortly.");
            System.out.println("Reply sent to " + enquiry.getApplicant().getName() + ": " + enquiry.getReply());
        } else {
            System.out.println("No enquiries to reply to.");
        }
	}

	public String viewProjDetails(BTOProject project)
	{
		// Method to view project details
		return "Project Name: " + project.getProjectName() +
               "\nLocation: " + project.getLocation() +
               "\nAvailable Flat Types: " + String.join(", ", project.getFlatTypes());
	}

	// ProjectManagement Implementation
	@Override
	public void regProject(BTOProject project)
	{
		// Register for project function
		this.appliedProject = project;
    	this.applicationStatus = "Pending";
        System.out.println("Officer has registered for project: " + project.getProjectName());
	}

	@Override
	public void updateFlatAvail(String flatType, int unitsLeft)
	{
		// Oficer to update flats available and save into the ProjectList CSV
		if (assignedProj == null) {
            System.out.println("No project assigned to officer.");
            throw new UnsupportedOperationException("Cannot update flat availability without an assigned project.");
        }
        assignedProj.updateFlatAvailability(flatType, "Available", unitsLeft);
        System.out.println("Updated " + flatType + " flats availability to " + unitsLeft + " units in project: " + assignedProj.getProjectName());
	}

	// ApplicantManagement Implementation
	@Override
	public void retrieveApplicant(Applicant applicant)
	{
		// Retrieve applicant details 
		System.out.println("=== Applicant Details ===");
		System.out.println("Name: " + applicant.getName());
		System.out.println("NRIC: " + applicant.getNRIC());
		System.out.println("Age: " + applicant.getAge());
		System.out.println("Marital Status: " + (applicant.getMaritalStatus() ? "Married" : "Single"));
		System.out.println("Applied Project: " + (applicant.getAppliedProject() != null ? applicant.getAppliedProject().getProjectName() : "None"));
		System.out.println("Application Status: " + applicant.getApplicationStatus());
		System.out.println("==========================");
	}

	@Override
	public void updateApplicantStatus(Applicant applicant)
	{
		// Change the status of an applicant from pending to booked or otherwise
		String currentStatus = applicant.getApplicationStatus();
		if ("Pending".equalsIgnoreCase(currentStatus)) {
			applicant.setApplicationStatus("Booked");
			System.out.println("Applicant status updated to: Booked");
		} else {
			System.out.println("No status change applied. Current status: " + currentStatus);
		}
	}
	@Override
	public boolean isEligibleForApplicant(Applicant applicant)
	{
		// Check whether the officer can be an applicant for a hdb project
		boolean ageEligible = applicant.getAge() >= 21;
		boolean maritalEligible = applicant.getMaritalStatus();
		return ageEligible && maritalEligible;
	}

	// Getter and Setter for assignedProj
    public BTOProject getAssignedProj() {
        return assignedProj;
    }

    public void setAssignedProj(BTOProject assignedProj) {
        this.assignedProj = assignedProj;
    }

	// Second constructor
    public HDBOfficer(String staffID, String nric, String password, String name, BTOProject assignedProj) {
        super(staffID, nric, password, name);
        this.assignedProj = assignedProj; 
    }

	/**
	 * 
	 * @param applicant
	 */
	public void generateReceipt(Applicant applicant) {
		// HDBOfficer.generateReceipt
		System.out.println("===== HDB Application Receipt =====");
    	System.out.println("Applicant ID    : " + applicant.getNRIC());
    	System.out.println("Name            : " + applicant.getName());
    	System.out.println("Age             : " + applicant.getAge());
    	System.out.println("Marital Status  : " + (applicant.getMaritalStatus() ? "Married" : "Single"));

    	if (assignedProj != null) {
        	System.out.println("Project Name    : " + assignedProj.getProjectName());
        	System.out.println("Project Location: " + assignedProj.getLocation()); // assumed method
        	System.out.println("Flat Type       : " + applicant.getFlatType());   // assumed method
        	System.out.println("Application Status: " + applicant.getApplicationStatus()); // assumed method
    	} else {
        	System.out.println("Project         : None assigned");
    }

    	System.out.println("===================================");
	}

	public String viewRegStatus() {
		// HDBOfficer.viewRegStatus
		if (assignedProj != null) {
            return "Officer is registered to handle project: " + assignedProj.getProjectName();
        } else {
            return "No project assigned.";
        }
	}
}