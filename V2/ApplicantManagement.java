public interface ApplicantManagement {

	/**
	 * 
	 * @param applicant
	 */
	void retrieveApplicant(Applicant applicant);

	/**
	 * 
	 * @param applicant
	 */
	void updateApplicantStatus(Applicant applicant);

	/**
	 * 
	 * @param applicant
	 */
	boolean isEligibleForApplicant(Applicant applicant);

}