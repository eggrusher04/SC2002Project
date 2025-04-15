public interface ProjectManagement {

	/**
	 * 
	 * @param project
	 */
	void regProject(BTOProject project);

	/**
	 * 
	 * @param flatType
	 * @param unitsLeft
	 */
	void updateFlatAvail(String flatType, int unitsLeft);

}