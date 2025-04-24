public interface ApplicantManagement {

	/**
	 * 
	 * @param applicant
	 */
	void retrieveApplicant(String nric);

	/**
	 * 
	 * @param applicant
	 */
	void updateApplicantStatus(String nric, String newStatus);

	/**
	 * 
	 * @param applicant
	 */
	boolean isEligibleForApplicant(Applicant applicant);

}