import java.util.Scanner;

public class ApplicantCLI {
    private Scanner scanner = new Scanner(System.in); // for user input
    private LoginManager loginManager; // manages login functionality
    private String applicantCSVPath; // path to applicant CSV file

    public ApplicantCLI(LoginManager loginManager, String applicantCSVPath) {
        this.loginManager = loginManager;
        this.applicantCSVPath = applicantCSVPath;
    }

    public void launch(Applicant applicant) { // main dashboard loop
        while (true) {
            System.out.println("\n====== Applicant Dashboard ======");
            System.out.println("1. View Application Status");
            System.out.println("2. Apply for BTO Project");
            System.out.println("3. Request Withdrawal");
            System.out.println("4. Manage Enquiries");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");

            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> System.out.println(applicant.viewStatus()); // display status
                case "2" -> { // apply for project
                    System.out.println("Filter by flat type? (e.g., '2-Room', '3-Room', or leave blank):");
                    String flatTypeFilter = scanner.nextLine().trim();
                    applicant.setFlatTypeFilter(flatTypeFilter);
                    System.out.println(applicant.viewListOfProjects());

                    System.out.println("Enter project name:");
                    String projectName = scanner.nextLine();

                    System.out.println("Enter flat type:");
                    String flatType = scanner.nextLine();
                    applicant.setFlatType(flatType);
                    applicant.createBTOProjectFromCSV(projectName);
                }
                case "3" -> applicant.reqWithdrawal(); // request withdrawal
                case "4" -> manageEnquiries(applicant); // manage enquiries
                case "5" -> { // change password
                    System.out.println("Enter new password:");
                    String newPw = scanner.nextLine();
                    applicant.changePassword(newPw);
                    loginManager.saveApplicantToCSV(applicantCSVPath);
                    System.out.println("Password updated successfully.");
                }
                case "6" -> { // logout
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again!");
            }
        }
    }

    private void manageEnquiries(Applicant applicant) { // submenu for enquiries
        while (true) {
            System.out.println("\n===== Enquiry Management =====");
            System.out.println("1. View Enquiries");
            System.out.println("2. Add Enquiry");
            System.out.println("3. Edit Enquiry");
            System.out.println("4. Delete Enquiry");
            System.out.println("5. Back to Dashboard");

            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> applicant.viewEnquiries(); // view enquiries
                case "2" -> { // add enquiry
                    System.out.println("Enter your enquiry message:");
                    String message = scanner.nextLine();
                    applicant.addEnquiry(message);
                }
                case "3" -> { // edit enquiry
                    System.out.println("Enter Enquiry ID to edit:");
                    int editID = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new message:");
                    String newMessage = scanner.nextLine();
                    applicant.editEnquiry(editID, newMessage);
                }
                case "4" -> { // delete enquiry
                    System.out.println("Enter Enquiry ID to delete:");
                    int deleteID = Integer.parseInt(scanner.nextLine());
                    applicant.deleteEnquiry(deleteID);
                }
                case "5" -> { // back to dashboard
                    return;
                }
                default -> System.out.println("Invalid option. Try again!");
            }
        }
    }
}