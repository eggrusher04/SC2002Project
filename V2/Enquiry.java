/**
<<<<<<< HEAD
 * Class Enquiry - Represents the enquiry in the system.
=======
 * Class Enquiry - Auto-generated Javadoc documentation.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
public class Enquiry {

	private int enquiryID;
	private Applicant sender;
	private Employees responder;
	private String message;
	private String response;

	public Enquiry(int enquiryID, Applicant sender, String message)
	{
		this.enquiryID = enquiryID;
		this.sender = sender;
		this.message = message;
		this.response = null;
	}

	public int getEnquiryID()
	{
		return enquiryID;
	}

	public Applicant getApplicant()
	{
		return sender;
	}

	public String getMessage()
	{
		return message;
	}

	public String getReply()
	{
		return response;
	}

	public Employees getResponder()
	{
		return responder;
	}

	public void setReply(String response)
	{
		this.response = response;
	}

	public void setResponder(Employees responder)
	{
		this.responder = responder;
	}

	
/**
<<<<<<< HEAD
 * Method updateEnquiry - performs the updateEnquiry operation.
=======
 * Method updateEnquiry - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
	public void updateEnquiry(String newMessage) {
		this.message = newMessage;
	}

/**
<<<<<<< HEAD
 * Method delEnquiry - performs the delEnquiry operation.
=======
 * Method delEnquiry - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
	public void delEnquiry() {
		this.message = null;
		this.response = null;
	}

/**
<<<<<<< HEAD
 * Method addEnquiry - performs the addEnquiry operation.
=======
 * Method addEnquiry - auto-documented method.
>>>>>>> 9b0546170238e4f3197a17806f2a839da3d77f05
 */
	public void addEnquiry(Applicant sender, String message) {
		this.sender = sender;
		this.message = message;
	}

}