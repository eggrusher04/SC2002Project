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
                case "1":
                    manager.viewListOfProjects();
                    break;
                case "2":
                    handleCreateProj(manager);
                    break;

                case "3":
                    handleEditProj(manager);
                    break;
                case "4":
                    handleDeleteProj(manager);
                    break;
                case "5":
                    handleToggleVisibility(manager);
                    break;
                case "6":
                    handleApproveOfficer(manager);
                    break;
                case "7":
                    handleRejectOfficer(manager);
                    break;
                case "8":
                    handleApproveWithdrawal(manager);
                    break;
                case "9":
                    System.out.println("Enter report filter criteria: ");
                    String filter = scanner.nextLine();
                    manager.genReport(filter);
                    break;
                case "10":
                    System.out.println("Enter your new password: ");
                    String newPass = scanner.nextLine();
                    manager.changePassword(newPass);
                    loginManager.saveManagerToCSV(managerCSVPath);
                    System.out.println("You have sucessfully updated your password. Please login using your new password!");
                    return;
                case "11":
                    System.out.println("Logging out....");
                    return;
                default:
                    System.out.println("Invalid option. Try again!!");
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

        System.out.println("Enter number of 3-Room flats: ");
        int threeRoom = Integer.parseInt(scanner.nextLine());

        BTOProject newProj = new BTOProject(projectName, hood, openDate, closeDate);
        newProj.addFlatType("2-Room", twoRoom);
        newProj.addFlatType("3-Room", threeRoom);
        manager.createProject(newProj);

        System.out.println("Project created successfully!!");
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

        System.out.println("Project updated sucessfully.");
    }

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
        System.out.println("Eneter applicant's NRIC to approve withdrawal: ");
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


}
