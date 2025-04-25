import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class HDBManager - Auto-generated Javadoc documentation.
 */
public class HDBManager extends Employees implements ProjectManager, OfficerApproval, View {

	private BTOProject[] createdProj;
	private static final int INITIAL_CAPACITY = 10;
    private String name;


/**
 * Method HDBManager - auto-documented method.
 */
	public HDBManager(String nric, String password, int age, String maritalStatus, int staffID, String name) {
        super(nric, password, age, maritalStatus, staffID, "HDB_MANAGER", name);
        this.createdProj = new BTOProject[INITIAL_CAPACITY];
 
    }

/**
 * Method HDBManager - auto-documented method.
 */
    public HDBManager(String name, String nric, String password, int age, String maritalStatus, int staffID) {
        super(nric, password, age, maritalStatus, staffID, "HDB_MANAGER", name);
        this.createdProj = new BTOProject[INITIAL_CAPACITY];  // initialize it here
        
    }
    
    
	@Override
/**
 * Method viewEnquiry - auto-documented method.
 */
	public String viewEnquiry(String message) {
		return "Enquiry: " + message;
	}
	
	@Override
/**
 * Method replyEnquiry - auto-documented method.
 */
	public void replyEnquiry() {
		System.out.println("Replying to enquiry... (HDBManager specific logic)");
	}
	
	@Override
/**
 * Method viewProjDetails - auto-documented method.
 */
	public String viewProjDetails(BTOProject project) {
		if (project == null) return "No project provided.";
	
		StringBuilder sb = new StringBuilder();
		sb.append("=== Project Details ===\n");
		sb.append("Project Name: ").append(project.getProjName()).append("\n");
		sb.append("Neighbourhood: ").append(project.getNeighbourhood()).append("\n");
		sb.append("Application Period: ").append(project.getApplicationOpenDate())
		  .append(" to ").append(project.getApplicationCloseDate()).append("\n");
		sb.append("Visibility: ").append(project.isVisible() ? "Visible" : "Hidden").append("\n");
	
		// Flat availability
		sb.append("Available Flats:\n");
		sb.append(" - 2-Room: ").append(project.getAvailUnits("2-Room")).append(" units\n");
		sb.append(" - 3-Room: ").append(project.getAvailUnits("3-Room")).append(" units\n");
	
		// Officers
		sb.append("Officer Slots: ").append(project.getAssignedOfficers().size())
		  .append(" / ").append(project.getMaxOfficerSlots()).append("\n");
	
		sb.append("Assigned Officers:\n");
		for (HDBOfficer o : project.getAssignedOfficers()) {
			sb.append(" - ").append(o.getName()).append(" (").append(o.getNRIC()).append(")\n");
		}
	
		return sb.toString();
	}
	



	@Override
/**
 * Method login - auto-documented method.
 */
	public boolean login(String nric, String pw) {
        return getNRIC().equals(nric) && getPassword().equals(pw);
	}



    // pulls from projectlist csv and prints out all the exisitng projects (changed)
	@Override
/**
 * Method viewListOfProjects - auto-documented method.
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
                result.append("Application Dates: ").append(openDate).append(" to ").append(closeDate).append("\n");
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
    
       




	@Override
/**
 * Method createProject - auto-documented method.
 */
    public void createProject(BTOProject project) {
        ensureCapacity(); // Ensure there is enough space in the array
        for (int i = 0; i < createdProj.length; i++) {
            if (createdProj[i] == null) {
                createdProj[i] = project;
                System.out.println("Project '" + project.getProjName() + "' has been added to the system.");
                return;
            }
        }
        System.out.println("Failed to add project: No available slots in the createdProj array.");
            }


// This method only updates data - no printing or input
/**
 * Method editProject - auto-documented method.
 */
	public void editProject(BTOProject project, String newName, String newHood, String newOpen, String newClose, int twoRoom, int threeRoom) {
		if (newName != null && !newName.isBlank()) {
			project.setProjectName(newName);
		}
		if (newHood != null && !newHood.isBlank()) {
			project.setNeighbourhood(newHood);
		}
		if (newOpen != null && !newOpen.isBlank()) {
			project.setApplicationOpenDate(newOpen);
		}
		if (newClose != null && !newClose.isBlank()) {
			project.setApplicationCloseDate(newClose);
		}
		if (twoRoom >= 0) {
			project.updateFlatAvailability("2-Room", twoRoom);
		}
		if (threeRoom >= 0) {
			project.updateFlatAvailability("3-Room", threeRoom);
		}
	}


    @Override
/**
 * Method deleteProject - auto-documented method.
 */
    public void deleteProject(BTOProject project) {
        for (int i = 0; i < createdProj.length; i++) {
            if (createdProj[i] != null && createdProj[i].equals(project)) {
                createdProj[i] = null;
                return;
            }
        }
    }




	@Override
/**
 * Method approveOfficer - auto-documented method.
 */
	public void approveOfficer(HDBOfficer officer) {
        if (officer != null) {
            officer.setRegStatus("Approved"); // Assume setter exists
        }
    }

    @Override
/**
 * Method rejectOfficer - auto-documented method.
 */
    public void rejectOfficer(HDBOfficer officer) {
        if (officer != null) {
            officer.setRegStatus("Rejected");
        }
    }



    // cahnged a bit
/**
 * Method approveWithdrawal - auto-documented method.
 */
    public void approveWithdrawal(Applicant applicant) {
        if (applicant != null) {
            applicant.withraw(); // deletes the application from application.csv
        }
    }


/**
 * Method genReport - auto-documented method.
 */
    public String genReport(String filterCriteria) {
        // Parse the filter criteria into individual components
        String[] filters = new String[4]; // Default to blank filters
        String[] inputFilters = filterCriteria.split(",");
        for (int i = 0; i < inputFilters.length && i < 4; i++) {
            filters[i] = inputFilters[i].trim();
        }
    
        String filterFlatType = filters[0];
        String filterProjectName = filters[1];
        String filterAge = filters[2];
        String filterMaritalStatus = filters[3];
    
        // Prepare the report header
        StringBuilder report = new StringBuilder("=== Manager Report ===\n");
        report.append(String.format("%-15s | %-15s | %-10s | %-15s | %-15s\n", 
            "Applicant NRIC", "Project Name", "Flat Type", "Age", "Marital Status"));
        report.append("-------------------------------------------------------------\n");
    
        // Read applicant details from Applications.csv
        String applicationsFilePath = "V2\\Applications.csv";
        try (BufferedReader appBr = new BufferedReader(new FileReader(applicationsFilePath))) {
            String line;
            boolean isHeader = true;
    
            while ((line = appBr.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip the header row
                }
    
                String[] fields = line.split(",");
                String applicantNRIC = fields[0].trim();
                String projectName = fields[1].trim();
                String flatType = fields[2].trim();
                String applicationStatus = fields[3].trim(); // Optional: Can be used for filtering later
    
                // Retrieve age and marital status from ApplicantList.csv
                String applicantDetails = getApplicantDetails(applicantNRIC);
                if (applicantDetails == null) {
                    continue; // Skip if applicant details are not found
                }
    
                String[] applicantFields = applicantDetails.split(",");
                int age = Integer.parseInt(applicantFields[2].trim());
                String maritalStatus = applicantFields[3].trim();
    
                // Apply filters
                boolean matchesFlatType = filterFlatType.isEmpty() || flatType.equalsIgnoreCase(filterFlatType);
                boolean matchesProjectName = filterProjectName.isEmpty() || projectName.equalsIgnoreCase(filterProjectName);
                boolean matchesAge = filterAge.isEmpty() || age == Integer.parseInt(filterAge);
                boolean matchesMaritalStatus = filterMaritalStatus.isEmpty() || maritalStatus.equalsIgnoreCase(filterMaritalStatus);
    
                if (matchesFlatType && matchesProjectName && matchesAge && matchesMaritalStatus) {
                    // Add matching applicant to the report
                    report.append(String.format("%-15s | %-15s | %-10s | %-15d | %-15s\n", 
                        applicantNRIC, projectName, flatType, age, maritalStatus));
                }
            }
        } catch (IOException e) {
            return "Error reading applications file ('" + applicationsFilePath + "'): " + e.getMessage();
        }
    
        // Return the generated report
        if (report.toString().contains("Applicant NRIC")) {
            return report.toString();
        } else {
            return "No matching applicants found.";
        }
    }

    // Helper method to retrieve applicant details from ApplicantList.csv
/**
 * Method getApplicantDetails - auto-documented method.
 */
    private String getApplicantDetails(String nric) {
        String applicantFilePath = "V2\\ApplicantList.csv";
        try (BufferedReader appBr = new BufferedReader(new FileReader(applicantFilePath))) {
            String line;
            boolean isHeader = true;

            while ((line = appBr.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip the header row
                }

                String[] fields = line.split(",");
                String currentNRIC = fields[1].trim();

                if (currentNRIC.equalsIgnoreCase(nric)) {
                    return line; // Return the entire line for the matching NRIC
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading applicant list: " + e.getMessage());
        }
        return null; // Return null if applicant not found
    }

/**
 * Method ensureCapacity - auto-documented method.
 */
	private void ensureCapacity() {
        if (createdProj[createdProj.length - 1] != null) {
            createdProj = Arrays.copyOf(createdProj, createdProj.length * 2);
        }
    }

/**
 * Method getProjectByName - auto-documented method.
 */
    public BTOProject getProjectByName(String name) {
        BTOProject project = createBTOProjectFromCSV(name);
        if (project != null){
            return project;
        }
        
        return null;
    }


    // method to create a bto project object from csv data
/**
 * Method createBTOProjectFromCSV - auto-documented method.
 */
    public BTOProject createBTOProjectFromCSV(String projectName) {
        String filePath = "V2\\ProjectList.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) { // skip header row
                    isHeader = false;
                    continue;
                }
                String[] fields = line.split(",");
                if ((fields[0].trim().equalsIgnoreCase(projectName)) && 
                    (!fields[10].trim().isEmpty() && fields[10].trim().equalsIgnoreCase(this.getName().trim()))) { // find matching project
                    String projName = fields[0];
                    String neighborhood = fields[1];
                    String appOpenDate = fields[8];
                    String appCloseDate = fields[9];
                    BTOProject project = new BTOProject(projName, neighborhood, appOpenDate, appCloseDate);
                    String type1 = fields[2];
                    int unitsType1 = Integer.parseInt(fields[3]);
                    String type2 = fields[5];
                    int unitsType2 = Integer.parseInt(fields[6]);
                    project.addFlatType(type1, unitsType1); // add flat types
                    project.addFlatType(type2, unitsType2);
                    return project;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading project list: " + e.getMessage());
        }
        System.out.println("Project not found. Please enter a valid project name.");
        return null;
    }


    
}

