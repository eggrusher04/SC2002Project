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

	public void setReply(String response)
	{
		this.response = response;
	}

	public void setResponder(Employees responder)
	{
		this.responder = responder;
	}

	
	public void updateEnquiry(String newMessage) {
		this.message = newMessage;
	}

	public void delEnquiry() {
		this.message = null;
		this.response = null;
	}

	public void addEnquiry(Applicant sender, String message) {
		this.sender = sender;
		this.message = message;
	}

}