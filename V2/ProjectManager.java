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
	void editProject(BTOProject project, String newName, String newHood, String newOpen, String newClose, int twoRoom, int threeRoom);


	/**
	 * 
	 * @param project
	 */
	void deleteProject(BTOProject project);


}