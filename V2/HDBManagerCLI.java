import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
            System.out.println("5. Toggle Project Visibility");
            System.out.println("6. Approve Officer");
            System.out.println("7. Reject Officer");
            System.out.println("8. Approve Applicant's Withdrawal");
            System.out.println("9. Generate Report");
            System.out.println("10. Change Password");
            System.out.println("11. Logout");

            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();

            switch(choice)
            {
                case "1" -> System.out.println(manager.viewListOfProjects());
                case "2" -> handleCreateProj(manager);

                case "3" -> handleEditProj(manager);
                case "4" -> handleDeleteProj(manager);
                case "5" -> handleToggleVisibility(manager);
                case "6" -> handleApproveOfficer(manager);
                case "7" -> handleRejectOfficer(manager);
                case "8" -> handleApproveWithdrawal(manager);
                case "9" -> {
                    System.out.println("Enter report filter criteria: ");
                    String filter = scanner.nextLine();
                    manager.genReport(filter);
                }
                case "10" -> {
                    System.out.println("Enter your new password: ");
                    String newPass = scanner.nextLine();
                    manager.changePassword(newPass);
                    loginManager.saveManagerToCSV(managerCSVPath);
                    System.out.println("You have sucessfully updated your password. Please login using your new password!");
                    return;
                }
                case "11" -> {
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
        saveProjectToCSV(newProj, twoRoom, twoRoomPrice, threeRoom, threeRoomPrice, maxOfficerSlots);
    
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

        manager.editProject(proj, newName, newHood, newOpen, newClose, newTwoRoom, newThreeRoom);
        saveProjectToCSV(proj, newTwoRoom, newTwoRoom, newThreeRoom, newThreeRoom, newThreeRoom);

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
        }
        
        else
        {
            System.out.println("Project not found.");
        }

        // here
        deleteProjectFromCSV(name);
        
        
    }

    private void handleToggleVisibility(HDBManager manager)
    {
        System.out.println("Enter the project name to toggle visibility: ");
        String name = scanner.nextLine();

        BTOProject proj = manager.getProjectByName(name);

        if(proj != null)
        {
            System.out.println("Enter visibility (true/false): ");
            boolean visible = Boolean.parseBoolean(scanner.nextLine());

            manager.toggleVisibility(proj, visible);
            System.out.println("Visibility updated.");
        }
        else
        {
            System.out.println("Project not found.");
        }
    }

    private void handleApproveOfficer(HDBManager manager)
    {
        System.out.println("Enter an officer's NRIC to approve: ");
        String nric = scanner.nextLine();

        HDBOfficer officer = loginManager.findOfficerByNRIC(nric);

        if(officer != null)
        {
            manager.approveOfficer(officer);
            System.out.println("Officer approved.");
        }
        else
        {
            System.out.println("Officer not found.");
        }
    }

    private void handleRejectOfficer(HDBManager manager)
    {
        System.out.println("Enter an officer's NRIC to reject: ");
        String nric = scanner.nextLine();

        HDBOfficer officer = loginManager.findOfficerByNRIC(nric);

        if(officer != null)
        {
            manager.approveOfficer(officer);
            System.out.println("Officer has been rejected.");
        }
        else
        {
            System.out.println("Officer not found.");
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


    private void saveProjectToCSV(BTOProject project, int twoRoomUnits, int twoRoomPrice, int threeRoomUnits, int threeRoomPrice, int maxOfficerSlots) {
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
                "" + // no manager assigned yet
                maxOfficerSlots + "," + // how many officers
                "" + "\n" // no officer assigned yet
            );
        } catch (IOException e) {
            System.out.println("Error saving project to CSV: " + e.getMessage());
        }
    }

    // help delete from csv file
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



