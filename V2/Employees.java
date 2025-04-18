public abstract class Employees implements Users {

	

    private String nric;
    private String password;
    private int age;
    private boolean maritalStatus;

    private int staffID;
    private String role;

	public Employees( String nric, String password, int age, boolean maritalStatus, int staffID, String role) {
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.staffID = staffID;
        this.role = role;
    }

	@Override public String getName()            { return name; }
    @Override public String getNRIC()            { return nric; }
    @Override public String getPassword()        { return password; }
    @Override public int getAge()                { return age; }
    @Override public boolean getMaritalStatus()  { return maritalStatus; }

    @Override
    public boolean login(String nric, String pw) {
        return this.nric.equals(nric) && this.password.equals(pw);
    }

    @Override
    public void changePassword(String newPw) {
        this.password = newPw;
    }

	/**
	 * 
	 * @param message
	 */
	public String viewEnquiry(String message) {
		// TODO - implement Employees.viewEnquiry
		throw new UnsupportedOperationException();
	}

	public void replyEnquiry() {
		// TODO - implement Employees.replyEnquiry
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param project
	 */
	public String viewProjDetails(BTOProject project) {
		// TODO - implement Employees.viewProjDetails
		throw new UnsupportedOperationException();
	}

}