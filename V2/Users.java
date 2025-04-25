/**
 * Interface Users - Auto-generated Javadoc documentation.
 */
public interface Users {

	//String nric = "SXXXXXXD";
	String password = "password";

	String getName();
	String getNRIC();
	String getPassword();
	int getAge();
	String getMaritalStatus();

	/**
	 * 
	 * @param nric
	 * @param password
	 */
	boolean login(String nric, String password);

	/**
	 * 
	 * @param newPassword
	 */
	void changePassword(String newPassword);
	

}