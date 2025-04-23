import java.util.Arrays;

public class HDBManager extends Employees implements ProjectManager, OfficerApproval, View {

	private BTOProject[] createdProj;
	private static final int INITIAL_CAPACITY = 10;
    private String name;


	public HDBManager(String nric, String password, int age, String maritalStatus, int staffID, String name) {
        super(nric, password, age, maritalStatus, staffID, "HDB_MANAGER", name);
        this.createdProj = new BTOProject[INITIAL_CAPACITY];
    }

    public HDBManager(String name, String nric, String password, int age, String maritalStatus, int staffID)
    {
        super(nric, password, age, maritalStatus, staffID, "HDB_MANAGER", name);
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




	@Override
    public String viewListOfProjects() {
        StringBuilder sb = new StringBuilder();
        for (BTOProject p : createdProj) {
            if (p != null) {
                sb.append(p.getProjName())
                  .append(" (Visible: ")
                  .append(p.isVisible() ? "ON" : "OFF")
                  .append(")\n");
            }
        }
        return sb.toString();
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




    public void approveWithdrawal(Applicant applicant) {
        if (applicant != null) {
            applicant.reqWithdrawal(); // Assume method exists in Applicant
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

