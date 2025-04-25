import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class HDBManagerCLI - Auto-generated Javadoc documentation.
 */
public class HDBManagerCLI {
    private LoginManager loginManager;
    private String managerCSVPath;
    private Scanner scanner;

    public HDBManagerCLI (LoginManager loginManager, String managerCSVPath)
    {
       this.loginManager = loginManager;
       this.managerCSVPath = managerCSVPath;
       this.scanner = new Scanner(System.in); 
    }

    public void launchManagerCLI(HDBManager manager)
    {

        while(true)
        {
            System.out.println("\n====== HDB Manager Dashboard ======");
            System.out.println("1. View List of Projects");
            System.out.println("2. Create a Project");
            System.out.println("3. Edit a Project");
            System.out.println("4. Delete a Project");
            System.out.println("5. Approve Officer");
            System.out.println("6. Reject Officer");
            System.out.println("7. Approve Applicant's Withdrawal");
            System.out.println("8. Generate Report");
            System.out.println("9. Change Password");
            System.out.println("10. Logout");

            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();

            switch(choice)
            {
                case "1" -> System.out.println(manager.viewListOfProjects());
                case "2" -> handleCreateProj(manager);

                case "3" -> handleEditProj(manager);
                case "4" -> handleDeleteProj(manager);
                case "5" -> handleApproveOfficer(manager);
                case "6" -> handleRejectOfficer(manager);
                case "7" -> handleApproveWithdrawal(manager);
                case "8" -> {
                    System.out.println("Enter report filter criteria (flat type, project name, age, marital status)(leave the option blank if any): ");
                    String filter = scanner.nextLine();
                    System.out.println(manager.genReport(filter));;
                }
                case "9" -> {
                    System.out.println("Enter your new password: ");
                    String newPass = scanner.nextLine();
                    manager.changePassword(newPass);
                    loginManager.saveManagerToCSV(managerCSVPath);
                    System.out.println("You have sucessfully updated your password. Please login using your new password!");
                    return;
                }
                case "10" -> {
                    System.out.println("Logging out....");
                    return;
                }
                default -> System.out.println("Invalid option. Try again!!");
            }
        }
    }

    private void handleCreateProj(HDBManager manager)
    {
        System.out.println("Enter project name: ");
        String projectName = scanner.nextLine();
    
        System.out.println("Enter neighbourhood: ");
        String hood = scanner.nextLine();
    
        System.out.println("Enter application OPEN date: ");
        String openDate = scanner.nextLine();
    
        System.out.println("Enter application CLOSE date: ");
        String closeDate = scanner.nextLine();
    
        System.out.println("Enter number of 2-Room flats: ");
        int twoRoom = Integer.parseInt(scanner.nextLine());
    
        System.out.println("Enter selling price for 2-Room flats: ");
        int twoRoomPrice = Integer.parseInt(scanner.nextLine());
    
        System.out.println("Enter number of 3-Room flats: ");
        int threeRoom = Integer.parseInt(scanner.nextLine());
    
        System.out.println("Enter selling price for 3-Room flats: ");
        int threeRoomPrice = Integer.parseInt(scanner.nextLine());
    
        System.out.println("Enter maximum officer slots (e.g., 10): ");
        int maxOfficerSlots = Integer.parseInt(scanner.nextLine());
    
        BTOProject newProj = new BTOProject(projectName, hood, openDate, closeDate);
        newProj.addFlatType("2-Room", twoRoom);
        newProj.addFlatType("3-Room", threeRoom);
    
        // Save to memory and file
        manager.createProject(newProj);
        saveProjectToCSV(newProj, twoRoom, twoRoomPrice, threeRoom, threeRoomPrice, maxOfficerSlots, manager.getName(), "No");
    
        System.out.println("Project created and saved successfully!");
    }
    

    private void handleEditProj(HDBManager manager)
    {
        System.out.println("Enter the name of the project: ");
        String name = scanner.nextLine();
        BTOProject proj = manager.getProjectByName(name);

        if(proj == null)
        {
            System.out.println("Project not found.");
            return;
        }

        System.out.println("Edit project name: ");
        String newName = scanner.nextLine();

        System.out.println("Edit neighbourhood: ");
        String newHood = scanner.nextLine();

        System.out.println("Edit the OPENING date: ");
        String newOpen = scanner.nextLine();

        System.out.println("Edit the CLOSING date: ");
        String newClose = scanner.nextLine();

        System.out.println("Edit no. of 2-Room flats: ");
        int newTwoRoom = Integer.parseInt(scanner.nextLine());

        System.out.println("Edit no. of 3-Room flats: ");
        int newThreeRoom = Integer.parseInt(scanner.nextLine());

        System.out.println("Edit the visibility (Yes/No): ");
        String newVisibility = scanner.nextLine();

        manager.editProject(proj, newName, newHood, newOpen, newClose, newTwoRoom, newThreeRoom);
        deleteProjectFromCSV(name);
        saveProjectToCSV(proj, newTwoRoom, newTwoRoom, newThreeRoom, newThreeRoom, newThreeRoom, manager.getName(), newVisibility);

        System.out.println("Project updated sucessfully.");
    }

    // changed a bit
    private void handleDeleteProj(HDBManager manager)
    {
        
        System.out.println("Enter the project name you want to delete: ");
        String name = scanner.nextLine();

        BTOProject proj = manager.getProjectByName(name);

        if(proj != null)
        {
            manager.deleteProject(proj);
            System.out.println("Project has been deleted.");
            deleteProjectFromCSV(name);
        }
        
        else
        {
            System.out.println("Project not found.");
        }
        
        
        
    }

/**
 * Method handleApproveOfficer - auto-documented method.
 */
    private void handleApproveOfficer(HDBManager manager) {
        System.out.println("Enter an officer's NRIC to approve: ");
        String nric = scanner.nextLine();
    
        // Step 1: Find the officer in OfficerList.csv
        String filePath = "V2\\OfficerList.csv";
        boolean officerFound = false;
        String projectName = null;
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            ArrayList<String> updatedLines = new ArrayList<>();
            String line;
            boolean isHeader = true;
    
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    updatedLines.add(line); // Retain the header row
                    isHeader = false;
                    continue;
                }
    
                String[] fields = line.split(",");
                String currentNRIC = fields[1].trim();
                String currentProjectApplied = fields.length > 5 ? fields[5].trim() : "";
                String currentStatus = fields.length > 6 ? fields[6].trim() : "";
    
                if (currentNRIC.equalsIgnoreCase(nric)) {
                    officerFound = true;
    
                    // Check if the officer has applied to a project
                    if (currentProjectApplied == null || currentProjectApplied.isEmpty()) {
                        System.out.println("Officer has not applied to any project.");
                        return;
                    }
    
                    projectName = currentProjectApplied;
    
                    // Step 2: Check if the project has available slots
                    String projectFilePath = "V2\\ProjectList.csv";
                    boolean isProjectUpdated = false;
    
                    try (BufferedReader projectBr = new BufferedReader(new FileReader(projectFilePath))) {
                        ArrayList<String> updatedProjectLines = new ArrayList<>();
                        String projectLine;
                        boolean isProjectHeader = true;
    
                        while ((projectLine = projectBr.readLine()) != null) {
                            if (isProjectHeader) {
                                updatedProjectLines.add(projectLine); // Retain the header row
                                isProjectHeader = false;
                                continue;
                            }
    
                            String[] projectFields = projectLine.split(",");
                            String currentProjectName = projectFields[0].trim();
                            int maxOfficerSlots = Integer.parseInt(projectFields[11].trim());
                            String officerColumn = projectFields.length > 12 ? projectFields[12].trim() : "";
                            String[] assignedOfficers = officerColumn.split("\\|");
                            int assignedCount = assignedOfficers.length;
    
                            if (currentProjectName.equalsIgnoreCase(projectName)) {
                                if (assignedCount >= maxOfficerSlots) {
                                    // Slots are full, reject the officer
                                    fields[6] = "Rejected"; // Update status to "Rejected"
                                    System.out.println("Project slots are full. Officer rejected.");
                                } else {
                                    // Slots available, add the officer
                                    String updatedOfficerColumn = officerColumn.isEmpty()
                                            ? fields[0].trim() // Officer's name
                                            : officerColumn + "|" + fields[0].trim(); // Append officer's name
    
                                    projectFields[12] = updatedOfficerColumn;
                                    fields[6] = "Approved"; // Update status to "Approved"
                                    isProjectUpdated = true;
                                    System.out.println("Officer approved and assigned to the project.");
                                }
                            }
    
                            updatedProjectLines.add(String.join(",", projectFields));
                        }
    
                        // Write updated lines back to ProjectList.csv
                        if (isProjectUpdated) {
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(projectFilePath))) {
                                for (String updatedLine : updatedProjectLines) {
                                    bw.write(updatedLine);
                                    bw.newLine();
                                }
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error updating project list: " + e.getMessage());
                        return;
                    }
                }
    
                // Add the updated officer line to the list
                updatedLines.add(String.join(",", fields));
            }
    
            // Write updated lines back to OfficerList.csv
            if (officerFound) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                    for (String updatedLine : updatedLines) {
                        bw.write(updatedLine);
                        bw.newLine();
                    }
                }
            } else {
                System.out.println("Officer not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading officer list: " + e.getMessage());
        }
    }

/**
 * Method handleRejectOfficer - auto-documented method.
 */
    private void handleRejectOfficer(HDBManager manager) {
        System.out.println("Enter an officer's NRIC to reject: ");
        String nric = scanner.nextLine();
        HDBOfficer officer = loginManager.findOfficerByNRIC(nric);
    
        if (officer == null) {
            System.out.println("Officer not found.");
            return;
        }
    
        // Update the officer's status in OfficerList.csv
        updateOfficerStatusInCSV(officer.getNRIC(), "Rejected");
        System.out.println("Officer has been rejected.");
    }
    
    
/**
 * Method updateOfficerStatusInCSV - auto-documented method.
 */
    private void updateOfficerStatusInCSV(String nric, String status) {
        String filePath = "V2\\OfficerList.csv";
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            ArrayList<String> updatedLines = new ArrayList<>();
            String line;
            boolean isHeader = true;
    
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    updatedLines.add(line);
                    isHeader = false;
                    continue;
                }
    
                String[] fields = line.split(",");
                String currentNRIC = fields[1];
    
                if (currentNRIC.equalsIgnoreCase(nric)) {
                    fields[6] = status; // Update the Status column
                    updatedLines.add(String.join(",", fields));
                } else {
                    updatedLines.add(line);
                }
            }
    
            // Write updated lines back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String updatedLine : updatedLines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating officer list: " + e.getMessage());
        }
    }

    private void handleApproveWithdrawal(HDBManager manager)
    {
        System.out.println("Enter applicant's NRIC to approve withdrawal: ");
        String nric = scanner.nextLine();

        Applicant applicant = loginManager.findApplicantByNRIC(nric);

        if(applicant != null)
        {
            manager.approveWithdrawal(applicant);
            System.out.println("Withdrawal successful.");
        }
        else
        {
            System.out.println("Applicant not found.");
        }
    }


/**
 * Method saveProjectToCSV - auto-documented method.
 */
    private void saveProjectToCSV(BTOProject project, int twoRoomUnits, int twoRoomPrice, int threeRoomUnits, int threeRoomPrice, int maxOfficerSlots, String managerName, String visibility) {
        String filePath = "V2\\ProjectList.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            // append to the next blank row
            bw.write(
                project.getProjName() + "," +
                project.getNeighbourhood() + "," +
                "2-Room," + twoRoomUnits + "," + twoRoomPrice + "," +
                "3-Room," + threeRoomUnits + "," + threeRoomPrice + "," +
                project.getApplicationOpenDate() + "," +
                project.getApplicationCloseDate() + "," + 
                managerName + "," + // no manager assigned yet
                maxOfficerSlots + "," + // how many officers
                "" + "," +// no officer assigned yet
                visibility +"\n"
            );
        } catch (IOException e) {
            System.out.println("Error saving project to CSV: " + e.getMessage());
        }
    }

    // help delete from csv file
/**
 * Method deleteProjectFromCSV - auto-documented method.
 */
    private void deleteProjectFromCSV(String projectName) {
        String filePath = "V2\\ProjectList.csv";
        ArrayList<String> updatedLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            boolean projectFound = false;

            // read file
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    updatedLines.add(line);
                    isHeader = false;
                    continue;
                }

                // split 
                String[] fields = line.split(",");
                String currentProjectName = fields[0];

                // check if match
                if (currentProjectName.equalsIgnoreCase(projectName)) {
                    projectFound = true; // project found
                    System.out.println("Project '" + projectName + "' has been deleted from the CSV file.");
                } else {
                    // retain line if not projectName
                    updatedLines.add(line);
                }
            }

            // if not found
            if (!projectFound) {
                System.out.println("Project '" + projectName + "' not found in the CSV file.");
                return;
            }

            // write everything back
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String updatedLine : updatedLines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error deleting project from CSV: " + e.getMessage());
        }
    }

}



