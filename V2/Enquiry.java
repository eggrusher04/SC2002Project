/**
 * Class Enquiry - Represents the enquiry in the system.
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
 * Method updateEnquiry - performs the updateEnquiry operation.
 */
	public void updateEnquiry(String newMessage) {
		this.message = newMessage;
	}

/**
 * Method delEnquiry - performs the delEnquiry operation.
 */
	public void delEnquiry() {
		this.message = null;
		this.response = null;
	}

/**
 * Method addEnquiry - performs the addEnquiry operation.
 */
	public void addEnquiry(Applicant sender, String message) {
		this.sender = sender;
		this.message = message;
	}

}