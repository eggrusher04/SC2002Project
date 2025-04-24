import java.util.Scanner;


// This class is what the OFFICER will see upon successful login


public class OfficerCLI {
    private Scanner scanner = new Scanner(System.in);
    private LoginManager loginManager;
    private String officerCSVPath;

    public OfficerCLI(LoginManager loginManager, String officerCSVPath)
    {
        this.loginManager = loginManager;
        this.officerCSVPath = officerCSVPath;
    }

    public void launchOfficerCLI(HDBOfficer officer)
    {
        Applicant officerApplicant =  new Applicant(
            officer.getNRIC(),
            officer.getName(),
            officer.getPassword(),
            officer.getAge(),
            officer.getMaritalStatus()
        );
        while(true)
        {
            System.out.println("\n====== HDB Officer Dashboard ======");
            System.out.println("1. View Application Status");
            System.out.println("2. Apply for BTO Project");
            System.out.println("3. Request Withdrawal");
            System.out.println("4. View Enquiry");
            System.out.println("5. Reply Enquiry");
            System.out.println("6. View Project Details");
            System.out.println("7. Register for a project to manage");
            System.out.println("8. Update Flat Availability");
            System.out.println("9. Retrieve Applicant Details");
            System.out.println("10. Update Applicant Status");
            System.out.println("11. Change Password");
            System.err.println("12. View Project Lists");
            System.out.println("13. Logout");

            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();

            switch(choice)
            {
                case "1":
                    System.out.println("Your Application Status: " + officerApplicant.viewStatus());
                    break;
                case "2":
                    //add logic to check whether officer can apply the project as an applicant
                    if(officer.isEligibleForApplicant(officerApplicant))
                    {
                        System.out.println("Filter by flat type? (eg: 2-Room, 3-Room or leave blank)");
                        String flatTypeFilter = scanner.nextLine().trim();
                        officerApplicant.setFlatTypeFilter(flatTypeFilter);
                        System.out.println(officerApplicant.viewListOfProjects());

                        System.out.println("Enter the BTO Project name to apply: ");
                        String projectName = scanner.nextLine();

                        System.out.println("Enter flat type: ");
                        String flatType =  scanner.nextLine();
                        officerApplicant.setFlatType(flatType);
                        officerApplicant.createBTOProjectFromCSV(projectName);

                    }
                    else
                    {
                        System.out.println("You are not eligible to apply as you are managing his project!");
                    }
                    break;
                case "3":
                    officerApplicant.reqWithdrawal();
                    System.out.println("You have submitted a withdrawal request.");
                    break;
                case "4":
                    if(officer.getEnquiries() != null && !officer.getEnquiries().isEmpty())
                    {
                        for(Enquiry e : officer.getEnquiries())
                        {
                            System.out.println(officer.viewEnquiry(e.getMessage()));
                        }
                    }
                    else
                    {
                        System.out.println("No enquiries found.");
                    }
                    break;
                case "5":
                    officer.replyEnquiry();
                    break;
                case "6":
                    BTOProject assigned = officer.getAssignedProj();
                    if(assigned != null)
                    {
                        System.out.println(officer.viewProjDetails(assigned));
                    }
                    else
                    {
                        System.out.println("You have yet to register to manage a project.");
                    }
                    break;
                case "7":
                    System.out.println(officer.viewListOfProjects());
                    System.out.println("Enter project name you want to manage: ");
                    String regProjName = scanner.nextLine();

                    BTOProject manageProject = officer.loadProjectByName(regProjName);
                    if (manageProject != null) {
                        officer.regProject(manageProject);
                    } else {
                        System.out.println("Project not found.");
                    }
                    break;
                case "8":
                    System.out.println("Enter flat type to update: ");
                    String flatType = scanner.nextLine();

                    System.out.println("Enter units left: ");
                    int unitsLeft = Integer.parseInt(scanner.nextLine());
                    officer.updateFlatAvail(flatType, unitsLeft);

                    break;
                case "9":
                    System.out.println("Enter applicant's nric: ");
                    String applicantNRIC = scanner.nextLine();
                    officer.retrieveApplicant(applicantNRIC);
                    break;
                case "10":
                    System.out.println("Enter applicant's NRIC: ");
                    String appliNRIC = scanner.nextLine();
            
                    Application application = Application.getApplicationByNRIC("V2\\Applications.csv", appliNRIC); // Pass file path and NRIC
                    
                    if (application != null) {
                        System.out.println("Application found: " + application.toString());
                        
                        System.out.println("Enter the new status for the applicant (e.g., Approved, Pending, Rejected): ");
                        String newStatus = scanner.nextLine().trim();
                        
                        if (newStatus.isEmpty()) {
                            System.out.println("Invalid status. Status not updated.");
                            return;
                        }
    
                        application.setApplicationStatus(newStatus);
                        
                        officer.updateApplicantStatus(appliNRIC,newStatus);
                        
                        System.out.println("Application status has been updated to: " + newStatus);
                    } else {
                        System.out.println("No application found with NRIC: " + appliNRIC);
                    }
                    break;
                case "11":
                    System.out.println("Enter your new password: ");
                    String newPass = scanner.nextLine();
                    officer.changePassword(newPass);
                    loginManager.saveOfficerToCSV(officerCSVPath);
                    System.out.println("You have sucessfully updated your password. Please login using your new password!");
                    return;
                case "12":
                    System.out.println(officer.viewListOfProjects());
                    break;
                case "13":
                    System.out.println("Logging out....");
                    return;
                default:
                    System.out.println("Invalid option. Try again!!");
            }
        }
    }
}
