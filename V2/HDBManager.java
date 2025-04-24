import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class HDBManager extends Employees implements ProjectManager, OfficerApproval, View {

	private BTOProject[] createdProj;
	private static final int INITIAL_CAPACITY = 10;
    private String name;


	public HDBManager(String nric, String password, int age, String maritalStatus, int staffID, String name) {
        super(nric, password, age, maritalStatus, staffID, "HDB_MANAGER", name);
        this.createdProj = new BTOProject[INITIAL_CAPACITY];
    }

    public HDBManager(String name, String nric, String password, int age, String maritalStatus, int staffID) {
        super(nric, password, age, maritalStatus, staffID, "HDB_MANAGER", name);
        this.createdProj = new BTOProject[INITIAL_CAPACITY];  // ✅ initialize it here
    }
    

	@Override
	public String viewEnquiry(String message) {
		return "Enquiry: " + message;
	}
	
	@Override
	public void replyEnquiry() {
		System.out.println("Replying to enquiry... (HDBManager specific logic)");
	}
	
	@Override
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
	public boolean login(String nric, String pw) {
        return getNRIC().equals(nric) && getPassword().equals(pw);
	}



    // pulls from projectlist csv and prints out all the exisitng projects (changed)
	@Override
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
    
       




	@Override
    public void createProject(BTOProject project) {
        ensureCapacity();
        for (int i = 0; i < createdProj.length; i++) {
            if (createdProj[i] == null) {
                createdProj[i] = project;
                return;
            }
        }
    }

// This method only updates data - no printing or input
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
    public void deleteProject(BTOProject project) {
        for (int i = 0; i < createdProj.length; i++) {
            if (createdProj[i] != null && createdProj[i].equals(project)) {
                createdProj[i] = null;
                return;
            }
        }
    }

    @Override
    public void toggleVisibility(BTOProject project, boolean visibility) {
        if (project != null) {
            project.setVisibility(visibility); // Make sure this method exists in BTOProject
        }
    }





	@Override
	public void approveOfficer(HDBOfficer officer) {
        if (officer != null) {
            officer.setRegStatus("Approved"); // Assume setter exists
        }
    }

    @Override
    public void rejectOfficer(HDBOfficer officer) {
        if (officer != null) {
            officer.setRegStatus("Rejected");
        }
    }



    // cahnged a bit
    public void approveWithdrawal(Applicant applicant) {
        if (applicant != null) {
            applicant.withraw(); // deletes the application from application.csv
        }
    }

    public String genReport(String filterCriteria) {
        StringBuilder report = new StringBuilder("=== Manager Report ===\n");
        for (BTOProject p : createdProj) {
            if (p != null) {
                report.append("Project: ").append(p.getProjName()).append("\n");
            }
        }
        return report.toString();
    }

	private void ensureCapacity() {
        if (createdProj[createdProj.length - 1] != null) {
            createdProj = Arrays.copyOf(createdProj, createdProj.length * 2);
        }
    }

    public BTOProject getProjectByName(String name) {
        for (BTOProject project : createdProj) {
            if (project != null && project.getProjName().equalsIgnoreCase(name)) {
                return project;
            }
        }
        return null;
    }


    
}

