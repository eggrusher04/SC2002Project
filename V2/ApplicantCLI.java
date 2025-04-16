import java.util.Scanner;


//This class is what the Applicant will see upon successful login

public class ApplicantCLI {
    private Scanner scanner = new Scanner(System.in);

    public void launch(Applicant applicant)
    {
        while(true)
        {
            System.out.println("\n====== Applicant Dashboard ======");
            System.out.println("1. View Application Status");
            System.out.println("2. Apply for BTO Project");
            System.out.println("3. Request Withdrawal");
            System.out.println("4. Change Password");
            System.out.println("5. Logout");

            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();

            switch(choice)
            {
                case "1":
                    System.out.println("Your Application Status: " + applicant.viewStatus());
                    break;
                case "2":
                    System.out.println("Enter BTO Project Name: ");
                    //to be added more
                    break;
                case "3":
                    applicant.reqWithdrawal();
                    System.out.println("You have submitted a withdrawal request.");
                    break;
                case "4":
                    System.out.println("Enter your new password: ");
                    String newPw = scanner.nextLine();
                    applicant.changePassword(newPw);
                    System.out.println("You have successfully updates your password.");
                    break;
                case "5":
                    System.out.println("Logging out... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again!!");
            }

        }
    }
}
