import java.util.Scanner;

public class UserInterfaceCLI {

	private Scanner scanner = new Scanner(System.in);
	private LoginManager loginManager;

	public UserInterfaceCLI()
	{
		loginManager =  new LoginManager();

		//Applicant tesApplicant = new Applicant("S1234567A", null, 25, true, "Pending", null, new Enquiry[0]);
		loginManager.loadApplicant("C:\\Users\\rusha\\OneDrive\\Documents\\GitHub\\SC2002Project\\V2\\ApplicantList.csv");
	}

	public void displayMenu() {
		// TODO - implement UserInterfaceCLI.displayMenu
		System.out.println("====== Welcome to HDB Login System =====");
		System.out.println("1. Login");
		System.out.println("2. Exit");
		//throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param prompt
	 */
	public String getInput(String prompt) {
		// TODO - implement UserInterfaceCLI.getInput
		System.out.println(prompt);
		return scanner.nextLine();
		//throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param output
	 */
	public void showOutput(String output) {
		// TODO - implement UserInterfaceCLI.showOutput
		System.out.println(output);
		//throw new UnsupportedOperationException();
	}

	public void launch()
	{
		while(true)
		{
			displayMenu();
			String choice = getInput("Select an option: ");
			switch(choice)
			{
				case "1":
					handleLogin();
					break;
				case "2":
					showOutput("Goodbye!");
					System.exit(0);
					break;
				default:
					showOutput("Invalid option. Please try again.");
			}
		}
	}

	private void handleLogin()
	{
		String nric = getInput("Enter NRIC: ");
		String password = getInput("Enter Password: ");

		Users user = loginManager.authenticateUser(nric, password);

		if(user != null)
		{
			showOutput("Login successful! Welcome, " + user.getName());
			if(user instanceof Applicant)
			{
				ApplicantCLI applicantCLI = new ApplicantCLI();
				applicantCLI.launch((Applicant) user);
			}
		}
		else
		{
			showOutput("Login failed. Invalid NRIC or password.");
		}
	}

	public static void main(String[] args) {
		UserInterfaceCLI cli = new UserInterfaceCLI();
		cli.launch();
	}

}