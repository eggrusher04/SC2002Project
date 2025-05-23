import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Applicant - Auto-generated Javadoc documentation.
 */
public class Applicant implements Users, View {
    private String name; // applicant's name
    private String nric; // applicant's nric
    private String password; // applicant's password
    private int age; // applicant's age
    private String maritalStatus; // marital status of the applicant
    private String applicationStatus; // tracks current application status
    private BTOProject appliedProject; // project applied by the applicant
    private String flatType; // flat type chosen by the applicant
    private ArrayList<Enquiry> enquiries; // list of enquiries made by the applicant
    private String flatTypeFilter; // filter for flat types when viewing projects
    private Application currentApplication; // tracks the current application object
    private String assignedOfficer;
    private String appliedProjName;

/**
 * Method Applicant - auto-documented method.
 */
    public Applicant(String name, String nric, String password, int age, String maritalStatus) {
        this.name = name;
        this.nric = nric;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.applicationStatus = "None"; // default status
        this.appliedProject = null; // no project applied initially
        this.flatType = null; // no flat type chosen initially
        this.currentApplication = null; // no application initially
        this.enquiries = new ArrayList<>(); // initialize empty list for enquiries
        this.flatTypeFilter = null; // no filter initially
        loadEnquiriesFromCSV(); // load existing enquiries from csv
        this.currentApplication = Application.loadFromCSV(this.nric);
        this.assignedOfficer = null;
    }

/**
 * Method getAppliedProjectName - auto-documented method.
 */
    public String getAppliedProjectName() {
        return appliedProjName;
    }

/**
 * Method setAppliedProjectName - auto-documented method.
 */
    public void setAppliedProjectName(String appliedProjectName) {
        this.appliedProjName = appliedProjectName;
    }

    // getters and setters for attributes
/**
 * Method setFlatTypeFilter - auto-documented method.
 */
    public void setFlatTypeFilter(String flatTypeFilter) {
        this.flatTypeFilter = flatTypeFilter;
    }

/**
 * Method getFlatTypeFilter - auto-documented method.
 */
    public String getFlatTypeFilter() {
        return flatTypeFilter;
    }

    @Override
/**
 * Method getName - auto-documented method.
 */
    public String getName() {
        return name;
    }

    @Override
/**
 * Method getNRIC - auto-documented method.
 */
    public String getNRIC() {
        return nric;
    }

    @Override
/**
 * Method getPassword - auto-documented method.
 */
    public String getPassword() {
        return password;
    }

    @Override
/**
 * Method getAge - performs the getAge operation.
 * Method getAge - auto-documented method.
 */
    public int getAge() {
        return age;
    }

    @Override
/**
 * Method getMaritalStatus - performs the getMaritalStatus operation.
 * Method getMaritalStatus - auto-documented method.
 */
    public String getMaritalStatus() {
        return maritalStatus;
    }

/**
 * Method setApplicationStatus - performs the setApplicationStatus operation.
 * Method setApplicationStatus - auto-documented method.
 */
    public void setApplicationStatus(String status) {
        this.applicationStatus = status;
    }

/**
 * Method getApplicationStatus - performs the getApplicationStatus operation.
 * Method getApplicationStatus - auto-documented method.
 */
    public String getApplicationStatus() {
        return applicationStatus;
    }

/**
 * Method setAppliedProject - performs the setAppliedProject operation.
 * Method setAppliedProject - auto-documented method.
 */
    public void setAppliedProject(BTOProject project) {
        this.appliedProject = project;
    }

/**
 * Method getAppliedProject - performs the getAppliedProject operation.
 * Method getAppliedProject - auto-documented method.
 */
    public BTOProject getAppliedProject() {
        return appliedProject;
    }

/**
 * Method setFlatType - performs the setFlatType operation.
 * Method setFlatType - auto-documented method.
 */
    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

/**
 * Method getFlatType - performs the getFlatType operation.
 * Method getFlatType - auto-documented method.
 */
    public String getFlatType() {
        return flatType;
    }

    public void setAssignedOfficer(String officer)
    {
        this.assignedOfficer = officer;
    }

    // method to apply for a bto project
/**
 * Method applyProject - performs the applyProject operation.
 * Method applyProject - auto-documented method.
 */
    public void applyProject(BTOProject project) {
        if (this.currentApplication != null) { // check if already applied
            System.out.println("you have already applied for a project.");
            return;
        }
        if (project == null) { // check if project exists
            System.out.println("project not found. please enter a valid project name.");
            return;
        }
        if (!isEligible(project)) { // check eligibility
            System.out.println("you are not eligible to apply for this project.");
            return;
        }
        // create a new application and save to csv
        this.currentApplication = new Application(this.nric, project.getProjName(), flatType);
        this.currentApplication.saveToCSV();
        System.out.println("you have successfully applied for the project: " + project.getProjName());
    }

/**
 * Method getCurrentApplication - performs the getCurrentApplication operation.
 * Method getCurrentApplication - auto-documented method.
 */
    public Application getCurrentApplication() {
        return currentApplication;
    }

    // method to view application status
/**
 * Method viewStatus - performs the viewStatus operation.
 * Method viewStatus - auto-documented method.
 */
    public String viewStatus() {
        if (currentApplication == null) { // no active application
            return "no active application.";
        }
        return "your application status is: " + currentApplication.getApplicationStatus();
    }

    // method to request withdrawal of application
/**
 * Method reqWithdrawal - auto-documented method.
 */
    public void reqWithdrawal() {
        if (this.currentApplication == null) { // check if there is an active application
            System.out.println("you have no active application to withdraw.");
            return;
        }
        if (this.currentApplication.getApplicationStatus().equals("booked")) { // cannot withdraw if booked
            System.out.println("your application is already booked. please contact an hdb manager for withdrawal.");
            return;
        
        
        }
        this.withraw();
    }

/**
 * Method withraw - auto-documented method.
 */
    public void withraw(){
        // delete application from csv and reset current application
        if (this.currentApplication != null){
        this.currentApplication.deleteFromCSV();
        this.currentApplication = null;
        System.out.println("Your withdrawal request has been processed.");
    }
        else{
            String filePath = "V2\\Applications.csv";
            List<String> lines = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] fields = line.split(",");
                    if (!(fields[0].equals(this.nric))){
                        lines.add(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading applications from CSV: " + e.getMessage());
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    bw.write(line + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error writing applications to CSV: " + e.getMessage());
            }
            }
        
    }

    // method to create a bto project object from csv data
/**
 * Method createBTOProjectFromCSV - auto-documented method.
 */
    public void createBTOProjectFromCSV(String projectName) {
        String filePath = "V2\\ProjectList.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) { // skip header row
                    isHeader = false;
                    continue;
                }
                String[] fields = line.split(",");
                if (fields[0].equalsIgnoreCase(projectName)) { // find matching project
                    String projName = fields[0];
                    String neighborhood = fields[1];
                    String appOpenDate = fields[8];
                    String appCloseDate = fields[9];
                    BTOProject project = new BTOProject(projName, neighborhood, appOpenDate, appCloseDate);
                    String type1 = fields[2];
                    int unitsType1 = Integer.parseInt(fields[3]);
                    String type2 = fields[5];
                    int unitsType2 = Integer.parseInt(fields[6]);
                    project.addFlatType(type1, unitsType1); // add flat types
                    project.addFlatType(type2, unitsType2);
                    this.applyProject(project); // apply to the project
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("error reading project list: " + e.getMessage());
        }
        System.out.println("project not found. please enter a valid project name.");
    }

    // method to handle user login
    @Override
/**
 * Method login - auto-documented method.
 */
    public boolean login(String nric, String pw) {
        return this.nric.equals(nric) && this.password.equals(pw);
    }

    // method to change password
    @Override
/**
 * Method changePassword - auto-documented method.
 */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // method to check eligibility for a project
/**
 * Method isEligible - auto-documented method.
 */
    private boolean isEligible(BTOProject project) {
        if (maritalStatus.equalsIgnoreCase("married")) { // married applicants can apply for any flat
            return true;
        }
        else if(maritalStatus.equalsIgnoreCase("single")) { // singles above 35 can apply for 2-room
            return age >= 35 && flatType != null && flatType.equalsIgnoreCase("2-room");
        }
        return false; // ineligible otherwise
    }

    // enquiry management methods
/**
 * Method addEnquiry - auto-documented method.
 */
    public void addEnquiry(String message) {
        int enquiryID = enquiries.size() + 1; // generate unique id
        Enquiry newEnquiry = new Enquiry(enquiryID, this, message);
        enquiries.add(newEnquiry);
        saveEnquiryToCSV(newEnquiry); // save enquiry to csv
        System.out.println("enquiry added successfully.");
    }

/**
 * Method viewEnquiries - auto-documented method.
 */
    public void viewEnquiries() {
        if (enquiries.isEmpty()) { // no enquiries to display
            System.out.println("you have no enquiries.");
            return;
        }
        for (Enquiry enquiry : enquiries) { // display all enquiries
            System.out.println("enquiry id: " + enquiry.getEnquiryID() +
                               ", message: " + enquiry.getMessage() +
                               ", reply: " + (enquiry.getReply() != null ? enquiry.getReply() : "no reply yet"));
        }
    }

/**
 * Method editEnquiry - auto-documented method.
 */
    public void editEnquiry(int enquiryID, String newMessage) {
        for (Enquiry enquiry : enquiries) { // find and update enquiry
            if (enquiry.getEnquiryID() == enquiryID) {
                enquiry.updateEnquiry(newMessage);
                updateEnquiryInCSV(enquiry); // update in csv
                System.out.println("enquiry updated successfully.");
                return;
            }
        }
        System.out.println("enquiry not found.");
    }

/**
 * Method deleteEnquiry - auto-documented method.
 */
    public void deleteEnquiry(int enquiryID) {
        enquiries.removeIf(enquiry -> enquiry.getEnquiryID() == enquiryID); // remove enquiry
        saveAllEnquiriesToCSV(); // rewrite csv
        System.out.println("enquiry deleted successfully.");
    }

    // helper methods for csv handling
/**
 * Method loadEnquiriesFromCSV - auto-documented method.
 */
    private void loadEnquiriesFromCSV() {
        String filePath = "V2\\enquiries.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); //skip header

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",",-1);
                int id = Integer.parseInt(fields[0]);
                String sender = fields[1];
                String senderNRIC = fields[2];
                String responder = fields[3];
                String enquiryMessage = fields[4];
                String response = fields[5];
                if (sender.equals(this.nric)) { // load only relevant enquiries
                    Enquiry enquiry = new Enquiry(id, this, enquiryMessage);
                    enquiry.setReply(response);
                    enquiries.add(enquiry);
                }
            }
        } catch (IOException e) {
            System.out.println("no existing enquiries found. starting with an empty list.");
        }
    }

/**
 * Method saveEnquiryToCSV - auto-documented method.
 */
    private void saveEnquiryToCSV(Enquiry enquiry) {
        String filePath = "V2\\enquiries.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {

            //bw.write("enquiryID,senderNRIC,Name,responder,message,reply\n");

            bw.write(enquiry.getEnquiryID() + "," +
                     enquiry.getApplicant().getNRIC() + "," + enquiry.getApplicant().getName() + "," +
                     (enquiry.getResponder() != null ? enquiry.getResponder().getName() : "") + "," +
                     enquiry.getMessage() + "," +
                     (enquiry.getReply() != null ? enquiry.getReply() : "") + "\n");
        } catch (IOException e) {
            System.out.println("error saving enquiry to csv: " + e.getMessage());
        }
    }

/**
 * Method updateEnquiryInCSV - auto-documented method.
 */
    private void updateEnquiryInCSV(Enquiry enquiry) {
        saveAllEnquiriesToCSV(); // rewrite entire csv
    }

/**
 * Method saveAllEnquiriesToCSV - auto-documented method.
 */
    private void saveAllEnquiriesToCSV() {
        String filePath = "V2\\enquiries.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            bw.write("enquiryID,senderNRIC,Name,responder,message,reply\n");


            for (Enquiry enquiry : enquiries) { // write all enquiries to csv
                bw.write(enquiry.getEnquiryID() + "," +
                         enquiry.getApplicant().getNRIC() + "," + enquiry.getApplicant().getName() + "," +
                         (enquiry.getResponder() != null ? enquiry.getResponder().getName() : "") + "," +
                         enquiry.getMessage() + "," +
                         (enquiry.getReply() != null ? enquiry.getReply() : "") + "\n");
            }
        } catch (IOException e) {
            System.out.println("error saving enquiries to csv: " + e.getMessage());
        }
    }

    // method to view filtered list of projects
    @Override
/**
 * Method viewListOfProjects - auto-documented method.
 */
    public String viewListOfProjects() {
        String filePath = "V2\\ProjectList.csv";
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) { // skip header row
                    isHeader = false;
                    continue;
                }
                String[] fields = line.split(",",-1);
                String projectName = fields[0];
                String neighborhood = fields[1];
                String type1 = fields[2];
                int unitsType1 = Integer.parseInt(fields[3]);
                String type2 = fields[5];
                int unitsType2 = Integer.parseInt(fields[6]);
                String visible = fields[13];

                // ensure singles above 35 only see 2-room flats
                if (maritalStatus.equalsIgnoreCase("single") && age >= 35) {
                    flatTypeFilter = "2-room";
                }

                // apply flat type filter
                else if (flatTypeFilter != null && !flatTypeFilter.isEmpty()) {
                    if (!type1.equalsIgnoreCase(flatTypeFilter) && !type2.equalsIgnoreCase(flatTypeFilter)) {
                        continue; // skip projects that don't match filter
                    }
                }
                if ("No".equals(visible)){
                    continue; // skip non visible projects
                }

                // append project details to result
                if (flatTypeFilter.equalsIgnoreCase("2-Room")){
                    result.append("- project name: ").append(projectName).append("\n");
                    result.append("  neighborhood: ").append(neighborhood).append("\n");
                    result.append("  flat types: ").append(type1).append(" (").append(unitsType1).append(" units)\n");
                    result.append("\n");
                }
                else if (flatTypeFilter.equalsIgnoreCase("3-Room")){
                    result.append("- project name: ").append(projectName).append("\n");
                    result.append("  neighborhood: ").append(neighborhood).append("\n");
                    result.append(type2).append(" (").append(unitsType2).append(" units)\n");
                    result.append("\n");
                }
                else{
                    result.append("- project name: ").append(projectName).append("\n");
                    result.append("  neighborhood: ").append(neighborhood).append("\n");
                    result.append("  flat types: ").append(type1).append(" (").append(unitsType1).append(" units), ")
                        .append(type2).append(" (").append(unitsType2).append(" units)\n");
                    result.append("\n");
                }
            }
            if (result.length() == 0) { // no projects match the filter
                return "no projects found matching the filter.";
            }
            return "available projects:\n" + result.toString();
        } catch (IOException e) {
            return "error reading project list: " + e.getMessage(); 
        }
    }

/**
 * Method loadCurrentApplicationFromCSV - auto-documented method.
 */
    private void loadCurrentApplicationFromCSV() {
        String filePath = "V2\\Applications.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length >= 5 && fields[0].equals(this.nric)) {
                    String projectName = fields[1];
                    String flatType = fields[2];
                    String status = fields[3];
                    String officer = fields[4];
    
                    this.currentApplication = new Application(this.nric, projectName, flatType);
                    this.currentApplication.setApplicationStatus(status);
                    this.currentApplication.setAssignedOfficer(officer);
                    this.applicationStatus = status;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading application from CSV: " + e.getMessage());
        }
    }
    

    
}