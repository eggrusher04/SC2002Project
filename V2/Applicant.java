public class Applicant implements Users {

	private String name;
	private String nric;
	private String password;
	private int age;
	private boolean maritalStatus;

	private String applicationStatus;
	private BTOProject appliedProject;
	private Enquiry[] enquiries;

	public Applicant(String name,String nric, String pw, int age, boolean maritalStatus, String appStats, BTOProject appliedProj, Enquiry[] enquiry)
	{
		this.name = name;
		this.nric = nric;
		this.password = Users.password;
		this.age = age;
		this.maritalStatus = maritalStatus;

		this.applicationStatus = appStats;
		this.appliedProject = appliedProj;
		this.enquiries = enquiry;
	}

	public String getName()
	{
		return name;
	}

	public String getNRIC()
	{
		return nric;
	}

	public String getPassword()
	{
		return password;
	}

	public int getAge()
	{
		return age;
	}

	public boolean getMaritalStatus()
	{
		return maritalStatus;
	}

	/**
	 * 
	 * @param project
	 */
	public void applyProject(BTOProject project) {
		// TODO - implement Applicant.applyProject
		//throw new UnsupportedOperationException();
	}

	public String viewStatus() {
		// TODO - implement Applicant.viewStatus
		return applicationStatus;
	}

	public void reqWithdrawal() {
		// TODO - implement Applicant.reqWithdrawal
		//throw new UnsupportedOperationException();
	}

	public boolean login(String nric, String pw)
	{
		if(this.nric.equals(nric) && this.password.equals(pw))
		{
			//System.out.println("Comparing login: input NRIC = " + nric + ", input password = " + pw);
			//System.out.println("Actual NRIC = " + this.nric + ", Actual password = " + this.password);
			return true;
		}
		else
		{
			return false;
		}
	}

	public void changePassword(String newPw)
	{
		this.password = newPw;
	}

}