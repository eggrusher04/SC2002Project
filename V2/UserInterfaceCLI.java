import java.util.Scanner;

/**
 * Class UserInterfaceCLI - Auto-generated Javadoc documentation.
 */
public class UserInterfaceCLI {

	private Scanner scanner = new Scanner(System.in);
	private LoginManager loginManager;

	public UserInterfaceCLI()
	{
		loginManager =  new LoginManager();

		//Applicant tesApplicant = new Applicant("S1234567A", null, 25, true, "Pending", null, new Enquiry[0]);


		loginManager.loadApplicant("V2\\ApplicantList.csv"); //rmb to change filepath to your own one
		loginManager.loadOfficer("V2\\OfficerList.csv");
		loginManager.loadManager("V2\\ManagerList.csv");
	}

/**
 * Method displayMenu - auto-documented method.
 */
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
/**
 * Method getInput - auto-documented method.
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
/**
 * Method showOutput - auto-documented method.
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


	//Authentication of user happens here
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
				String path = "V2\\ApplicantList.csv"; //load the pathname of the CSV file
				ApplicantCLI applicantCLI = new ApplicantCLI(loginManager,path); //then pass it into Applicant CLI, including the loginManager object. So it wont need to duplicate the login logic or object.
				applicantCLI.launch((Applicant) user);
			}
			else if(user instanceof HDBOfficer)
			{
				String officerPath = "V2\\OfficerList.csv"; 
				OfficerCLI officerCLI = new OfficerCLI(loginManager,officerPath);
				officerCLI.launchOfficerCLI((HDBOfficer) user);
			}
			else if(user instanceof HDBManager)
			{
				String managerPath = "V2\\ManagerList.csv";
				HDBManagerCLI managerCLI = new HDBManagerCLI(loginManager, managerPath);
				managerCLI.launchManagerCLI((HDBManager) user);
			}
		}
		else
		{
			showOutput("Login failed. Invalid NRIC or password, please try again.");
		}
	}

/**
 * Method main - auto-documented method.
 */
	public static void main(String[] args) {
		UserInterfaceCLI cli = new UserInterfaceCLI();
		cli.launch();
	}

}