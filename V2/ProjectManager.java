public interface ProjectManager {

	/**
	 * 
	 * @param project
	 */
	void createProject(BTOProject project);

	/**
	 * 
	 * @param project
	 */
	void editProject(BTOProject project);

	/**
	 * 
	 * @param project
	 */
	void deleteProject(BTOProject project);

	/**
	 * 
	 * @param project
	 * @param visibility
	 */
	void toggleVisibility(BTOProject project, boolean visibility);

}