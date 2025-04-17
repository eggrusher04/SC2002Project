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
        while(true)
        {
            System.out.println("\n====== HDB Officer Dashboard ======");
            System.out.println("1. View Application Status");
            System.out.println("2. Apply for BTO Project");
            System.out.println("3. Request Withdrawal");
            System.out.println("4. View Enquiry");
            System.out.println("5. Reply Enquiry");
            System.out.println("6. View Project Details");
            System.out.println("7. Register for a project");
            System.out.println("8. Update Flat Availability");
            System.out.println("9. Retrieve Applicant Details");
            System.out.println("10. Update Applicant Status");
            System.out.println("11. Change Password");
            System.out.println("12. Logout");

            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();

            switch(choice)
            {
                case "1":
                    //System.out.println("Your Application Status: " + applicant.viewStatus());
                    break;
                case "2":
                    System.out.println("Enter the BTO Project name to apply: ");
                    String projectName = scanner.nextLine();

                    //add logic to check whether officer can apply the project as an applicant
                    break;
                case "3":
                    //add officer's method to request withdrawal
                    System.out.println("You have submitted a withdrawal request.");
                    break;
                case "4":
                    //officer.viewEnquiry()
                    break;
                case "5":
                    officer.replyEnquiry();
                    break;
                case "6":
                    //officer.viewProjDetails()
                    break;
                case "7":
                    officer.regProject(null);
                    break;
                case "8":
                    //officer.updateFlatAvail()
                    break;
                case "9":
                    //officer.retrieveApplicant()
                    break;
                case "10":
                    //officer.updateApplicantStatus()
                    break;
                case "11":
                    System.out.println("Enter your new password: ");
                    String newPass = scanner.nextLine();
                    officer.changePassword(newPass);
                    loginManager.saveOfficerToCSV(officerCSVPath);
                    System.out.println("You have sucessfully updated your password. Please login using your new password!");
                    return;
                case "12":
                    System.out.println("Logging out....");
                    return;
                default:
                    System.out.println("Invalid option. Try again!!");
            }
        }
    }
}
