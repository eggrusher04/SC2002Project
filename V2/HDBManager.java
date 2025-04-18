import java.util.Arrays;

public class HDBManager extends Employees implements ProjectManager, OfficerApproval, View {

	private BTOProject[] createdProj;
	private static final int INITIAL_CAPACITY = 10;


	public HDBManager(String nric, String password, int age, boolean maritalStatus, int staffID) {
        super(nric, password, age, maritalStatus, staffID, "HDB_MANAGER");
        this.createdProj = new BTOProject[INITIAL_CAPACITY];
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
		sb.append("Project Name: ").append(project.getProjectName()).append("\n");
		sb.append("Neighbourhood: ").append(project.getNeighbourhood()).append("\n");
		sb.append("Application Period: ").append(project.getApplicationOpenDate())
		  .append(" to ").append(project.getApplicationCloseDate()).append("\n");
		sb.append("Visibility: ").append(project.isVisibility() ? "Visible" : "Hidden").append("\n");
	
		// Flat availability
		sb.append("Available Flats:\n");
		sb.append(" - 2-Room: ").append(project.getAvailUnits("2-Room")).append(" units\n");
		sb.append(" - 3-Room: ").append(project.getAvailUnits("3-Room")).append(" units\n");
	
		// Officers
		sb.append("Officer Slots: ").append(project.getAssignedOfficers().length)
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
                sb.append(p.getProjectName())
                  .append(" (Visible: ")
                  .append(p.isVisibility() ? "ON" : "OFF")
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
	@Override
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
			project.updateFlatAvail("2-Room", twoRoom);
		}
		if (threeRoom >= 0) {
			project.updateFlatAvail("3-Room", threeRoom);
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
                report.append("Project: ").append(p.getProjectName()).append("\n");
            }
        }
        return report.toString();
    }

	private void ensureCapacity() {
        if (createdProj[createdProj.length - 1] != null) {
            createdProj = Arrays.copyOf(createdProj, createdProj.length * 2);
        }
    }
}

