import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


public class HDBOfficer extends Employees implements View, ProjectManagement, ApplicantManagement{

	private BTOProject assignedProj;

	private String applicationStatus;
	private BTOProject appliedProject;
	private List<Enquiry> enquiries;
	private String regStatus;
	private dataLoader loader;
	

	// First Constructor to apply as an applicant
	public HDBOfficer(String nric, String name, String password, int age, String maritalStatus, int staffID, String role, String applicationStatus, BTOProject appliedProject, List<Enquiry> enquiries) {

    super(nric, password, age, maritalStatus, staffID, role, name);

    this.applicationStatus = applicationStatus;
    this.appliedProject = appliedProject;
    this.enquiries = enquiries;
	this.loader = new dataLoader();
}


	// Second constructor to manage a project
    public HDBOfficer(String nric, String name, String password, int age, String maritalStatus, int staffID, String role, BTOProject assignedProj) {

    super(nric, password, age, maritalStatus, staffID, role, name);
    this.assignedProj = assignedProj;
	this.loader = new dataLoader();
}
	//Third constructor to load login details and basic attributes when app is launched
	public HDBOfficer(String name, String nric, String password, int age, String maritalStatus)
	{
		super(nric, password, age, maritalStatus, 0, "Officer", name); // default staffID = 0, role = "Officer"
		this.assignedProj = null;
		this.applicationStatus = null;
		this.appliedProject = null;
		this.enquiries = null;
		this.regStatus = null;
		this.loader = new dataLoader();
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
		if(this.getNRIC().equals(nric) && this.getPassword().equals(pw))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void setPassword(String newPw)
	{
		this.changePassword(newPw);
	}

	public String viewListOfProjects()
	{
		// Read the listOfProjects from the csv ( erm im not really sure how to do this )
		String filePath = "V2\\ProjectList.csv";
    	StringBuilder result = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean isHeader = true;

			while ((line = br.readLine()) != null) {
				if (isHeader) {
					isHeader = false; // skip header line
					continue;
				}
				String[] fields = line.split(",");
				if (fields.length >= 1) {
					result.append("- ").append(fields[0]).append("\n"); // assuming the first column is project name
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
		Set<String> flatTypes = new HashSet<>();
		for (Flat flat : project.getAvailFlats())
		{
			flatTypes.add(flat.getFlatType());
		}

		return "Project Name: " + project.getProjName() +
               "\nLocation: " + project.getNeighbourhood() +
               "\nAvailable Flat Types: " + String.join(", ", flatTypes);
	}

	// ProjectManagement Implementation
	@Override
	public void regProject(BTOProject project)
	{
		// Register for project function
		this.assignedProj = project;
        System.out.println("Project " + project.getProjName() + " has been successfully registered to officer " + this.getName() + ".");
	}

	@Override
	public void updateFlatAvail(String flatType, int unitsLeft)
	{
		// Oficer to update flats available and save into the ProjectList CSV
		if (assignedProj == null) {
            System.out.println("No project assigned to officer.");
            throw new UnsupportedOperationException("Cannot update flat availability without an assigned project.");
        }
        assignedProj.updateFlatAvailability(flatType, unitsLeft);
        System.out.println("Updated " + flatType + " flats availability to " + unitsLeft + " units in project: " + assignedProj.getProjName());
	}

	// ApplicantManagement Implementation
	@Override
	public void retrieveApplicant(String nric) {
		List<Applicant> applicants = loader.loadApplicants("V2\\ApplicantList.csv", "V2\\Applications.csv");

		String trimmedNRIC = nric.trim();
		System.out.println("Searching for NRIC: '" + trimmedNRIC + "'");

		for (Applicant a : applicants) {
			String applicantNRIC = a.getNRIC().trim();

			if (applicantNRIC.equalsIgnoreCase(trimmedNRIC)) {
				System.out.println("\n=== Applicant Details ===");
				System.out.println("Name: " + a.getName());
				System.out.println("NRIC: " + a.getNRIC());
				System.out.println("Age: " + a.getAge());
				System.out.println("Marital Status: " + a.getMaritalStatus());
				System.out.println("Applied Project: " + (a.getAppliedProjectName() != null ? a.getAppliedProjectName() : "None"));
				System.out.println("Application Status: " + a.getApplicationStatus());
				System.out.println("==========================");
				return;
			}
		}

		System.out.println("Applicant with NRIC " + trimmedNRIC + " not found.");
	}


	public void updateApplicantStatus(String nric, String newStatus) {
		String csvPath = "V2\\Applications.csv";
	
		Application app = Application.getApplicationByNRIC(csvPath, nric);
	
	
		if (app != null) {
		
			app.setApplicationStatus(newStatus);
			
			app.updateCSV();
			
			System.out.println("Applicant status updated to: " + newStatus);
		} else {
			System.out.println("Application with NRIC " + nric + " not found.");
		}
	}
	
	
	@Override
	public boolean isEligibleForApplicant(Applicant applicant)
	{
		// Check whether the officer can be an applicant for a hdb project
		if(this.assignedProj != null && this.appliedProject != null)
		{
			return !this.assignedProj.getProjName().equals(this.appliedProject.getProjName());
		}
		return true;
	}

	// Getter and Setter for assignedProj
    public BTOProject getAssignedProj() {
        return assignedProj;
    }

    public void setAssignedProj(BTOProject assignedProj) {
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
    	System.out.println("Marital Status  : " + (applicant.getMaritalStatus()));

    	if (assignedProj != null) {
        	System.out.println("Project Name    : " + assignedProj.getProjName());
        	System.out.println("Project Location: " + assignedProj.getNeighbourhood()); // assumed method
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
            return "Officer is registered to handle project: " + assignedProj.getProjName();
        } else {
            return "No project assigned.";
        }
	}

	public void setRegStatus(String status)
	{
		this.regStatus = status;
	}

	public BTOProject loadProjectByName(String projectName) {
		String filePath = "V2\\ProjectList.csv";
	
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean isHeader = true;
	
			while ((line = br.readLine()) != null) {
				if (isHeader) {
					isHeader = false;
					continue;
				}
	
				String[] fields = line.split(",");
				if (fields.length >= 4) { 
					String name = fields[0].trim();
					String neighbourhood = fields[1].trim();
					String openDate = fields[2].trim();
					String closeDate = fields[3].trim();
	
					if (name.equalsIgnoreCase(projectName)) {
						BTOProject project = new BTOProject(name, neighbourhood, openDate, closeDate);
						return project;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading project file: " + e.getMessage());
		}
	
		return null;
	}
	
}