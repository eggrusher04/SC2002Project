import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Class HDBOfficer - Represents the hdbofficer in the system.
 */
public class HDBOfficer extends Employees implements View, ProjectManagement, ApplicantManagement{

	private BTOProject assignedProj;

	private String applicationStatus;
	private BTOProject appliedProject;
	private List<Enquiry> enquiries;
	private String regStatus;
	private dataLoader loader;

	// First Constructor to apply as an applicant
/**
 * Method HDBOfficer - performs the HDBOfficer operation.
 */
	public HDBOfficer(String nric, String name, String password, int age, String maritalStatus, int staffID, String role, String applicationStatus, BTOProject appliedProject, List<Enquiry> enquiries) {

    super(nric, password, age, maritalStatus, staffID, role, name);
	
    this.applicationStatus = applicationStatus;
    this.assignedProj = loadAssignedProject(name);
    this.enquiries = enquiries;
	this.loader = new dataLoader();
	
}


	// Second constructor to manage a project
/**
 * Method HDBOfficer - performs the HDBOfficer operation.
 */
    public HDBOfficer(String nric, String name, String password, int age, String maritalStatus, int staffID, String role, BTOProject assignedProj) {

    super(nric, password, age, maritalStatus, staffID, role, name);
    this.assignedProj = loadAssignedProject(name);
	this.loader = new dataLoader();

}
	//Third constructor to load login details and basic attributes when app is launched
	public HDBOfficer(String name, String nric, String password, int age, String maritalStatus)
	{
		super(nric, password, age, maritalStatus, 0, "Officer", name); // default staffID = 0, role = "Officer"
		this.assignedProj = loadAssignedProject(name);
		this.applicationStatus = null;
		this.appliedProject = null;
		this.enquiries = null;
		this.regStatus = null;
		this.loader = new dataLoader();
	}

	// Helper method to load the assigned project from OfficerList.csv
/**
 * Method loadAssignedProject - performs the loadAssignedProject operation.
 */
    private BTOProject loadAssignedProject(String officerName) {
        String filePath = "V2\\OfficerList.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip the header row
                }

                String[] fields = line.split(",");
                String name = fields[0].trim();
                String projectName = fields.length > 5 ? fields[5].trim() : ""; // Project Applied column
                String status = fields.length > 6 ? fields[6].trim() : ""; // Status column

                if (name.equalsIgnoreCase(officerName) && "Approved".equalsIgnoreCase(status)) {
                    // Create and return the BTOProject object for the assigned project
                    return createBTOProjectFromCSV(projectName);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading officer list: " + e.getMessage());
        }
        return null; // Return null if no project is found
    }

    // Method to create a BTOProject object from ProjectList.csv
/**
 * Method createBTOProjectFromCSV - performs the createBTOProjectFromCSV operation.
 */
    private BTOProject createBTOProjectFromCSV(String projectName) {
        String filePath = "V2\\ProjectList.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip the header row
                }

                String[] fields = line.split(",");
                String projName = fields[0].trim();
                String neighborhood = fields[1].trim();
                String openDate = fields[8].trim();
                String closeDate = fields[9].trim();

                if (projName.equalsIgnoreCase(projectName)) {
                    return new BTOProject(projName, neighborhood, openDate, closeDate);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading project list: " + e.getMessage());
        }
        return null; // Return null if the project is not found
    }

/**
 * Method getApplicationStatus - performs the getApplicationStatus operation.
 */
	public String getApplicationStatus() {
    	return applicationStatus;
	}

/**
 * Method getAppliedProject - performs the getAppliedProject operation.
 */
	public BTOProject getAppliedProject() {
    	return appliedProject;
	}

/**
 * Method getEnquiries - performs the getEnquiries operation.
 */
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

	@Override
/**
 * Method viewListOfProjects - performs the viewListOfProjects operation.
 */
    public String viewListOfProjects() {
        String filePath = "V2\\ProjectList.csv";
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] fields = line.split(",");
    
                String projectName = fields[0];
                String neighborhood = fields[1];
                String type1 = fields[2];
                int unitsType1 = Integer.parseInt(fields[3]);
                String priceType1 = fields[4];
                String type2 = fields[5];
                int unitsType2 = Integer.parseInt(fields[6]);
                String priceType2 = fields[7];
                String openDate = fields[8];
                String closeDate = fields[9];
                String manager = fields.length > 10 && !fields[10].isBlank() ? fields[10] : "N/A";
                String officerSlot = fields.length > 11 ? fields[11] : "N/A";
                String officer = fields.length > 12 ? fields[12] : "None assigned";
    
                result.append("--------------------------------------------------\n");
                result.append("Project Name     : ").append(projectName).append("\n");
                result.append("Neighborhood     : ").append(neighborhood).append("\n");
                result.append("Flat Types       : ").append(type1).append(" (").append(unitsType1).append(" units, $").append(priceType1).append("), ")
                      .append(type2).append(" (").append(unitsType2).append(" units, $").append(priceType2).append(")\n");
                result.append("Application Dates: ").append(openDate).append(" → ").append(closeDate).append("\n");
                result.append("Manager          : ").append(manager).append("\n");
                result.append("Officer Slot     : ").append(officerSlot).append("\n");
                result.append("Assigned Officer : ").append(officer).append("\n");
                result.append("--------------------------------------------------\n\n");
            }
    
            if (result.length() == 0) {
                return "No projects found.";
            }
            return "Available Projects:\n\n" + result.toString();
        } catch (IOException | NumberFormatException e) {
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

/**
 * Method updateFlatAvail - performs the updateFlatAvail operation.
 */
	public void updateFlatAvail(String flatType, int unitsLeft) {
    // Step 1: Validate input
    if (assignedProj == null) {
        System.out.println("No project assigned to officer.");
        throw new UnsupportedOperationException("Cannot update flat availability without an assigned project.");
    }

    String projectName = assignedProj.getProjName();
    String filePath = "V2\\ProjectList.csv";
    ArrayList<String> updatedLines = new ArrayList<>();
    boolean projectFound = false;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean isHeader = true;

        while ((line = br.readLine()) != null) {
            if (isHeader) {
                updatedLines.add(line); // Retain the header row
                isHeader = false;
                continue;
            }

            String[] fields = line.split(",");
            String currentProjectName = fields[0].trim();

            // Step 2: Find the project row
            if (currentProjectName.equalsIgnoreCase(projectName)) {
                projectFound = true;

                // Step 3: Update the flat type availability
                boolean flatTypeUpdated = false;
                for (int i = 2; i < fields.length; i += 3) {
                    if (fields[i].trim().equalsIgnoreCase(flatType)) {
                        fields[i + 1] = String.valueOf(unitsLeft); // Update the number of units
                        flatTypeUpdated = true;
                        break;
                    }
                }

                if (!flatTypeUpdated) {
                    System.out.println("Flat type '" + flatType + "' not found in project '" + projectName + "'.");
                    return;
                }

                // Add the updated line to the list
                updatedLines.add(String.join(",", fields));
            } else {
                // Retain other projects unchanged
                updatedLines.add(line);
            }
        }

        // Step 4: Write updated lines back to the file
        if (projectFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String updatedLine : updatedLines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }
            System.out.println("Updated " + flatType + " flats availability to " + unitsLeft + " units in project: " + projectName);
        } else {
            System.out.println("Project '" + projectName + "' not found in the CSV file.");
        }
    } catch (IOException e) {
        System.out.println("Error updating project list: " + e.getMessage());
    }
}

	// ApplicantManagement Implementation
	@Override
/**
 * Method retrieveApplicant - performs the retrieveApplicant operation.
 */
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


/**
 * Method updateApplicantStatus - performs the updateApplicantStatus operation.
 */
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
/**
 * Method getAssignedProj - performs the getAssignedProj operation.
 */
    public BTOProject getAssignedProj() {
        return assignedProj;
    }

/**
 * Method setAssignedProj - performs the setAssignedProj operation.
 */
    public void setAssignedProj(BTOProject assignedProj) {
        this.assignedProj = assignedProj;
    }

	/**
	 * 
	 * @param applicant
	 */
/**
 * Method generateReceipt - performs the generateReceipt operation.
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

/**
 * Method viewRegStatus - performs the viewRegStatus operation.
 */
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

/**
 * Method loadProjectByName - performs the loadProjectByName operation.
 */
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