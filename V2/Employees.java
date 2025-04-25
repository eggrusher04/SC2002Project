import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Employees implements Users {

	

    private String nric;
    private String name;
    private String password;
    private int age;
    private String maritalStatus;

    private int staffID;
    private String role;

	public Employees( String nric, String password, int age, String maritalStatus, int staffID, String role, String name) {
        this.nric = nric;
        this.name = name;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.staffID = staffID;
        this.role = role;
    }

	@Override public String getName()            { return name; }
    @Override public String getNRIC()            { return nric; }
    @Override public String getPassword()        { return password; }
    @Override public int getAge()                { return age; }
    @Override public String getMaritalStatus()  { return maritalStatus; }
    public int getStaffID()
    {
        return staffID;
    }

    @Override
    public boolean login(String nric, String pw) {
        return this.nric.equals(nric) && this.password.equals(pw);
    }

    @Override
    public void changePassword(String newPw) {
        this.password = newPw;
    }

	/**
	 * 
	 * @param message
	 */
	public String viewEnquiry(String message) {

        
        String filePath = "V2\\enquiries.csv";

        // see if the employee wants replied, unreplied or all enquiries
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 'unreplied', 'replied', or 'all' to view enquiries:");
        String userChoice = scanner.nextLine().trim().toLowerCase();

        // reading the csv files
        List<String[]> allEnquiries = readCSV(filePath);
        StringBuilder result = new StringBuilder();

        for (String[] enquiry : allEnquiries) {
            String enquiryID = enquiry[0];
            String sender = enquiry[1];
            String responder = enquiry[2];
            String enquiryMessage = enquiry[3];
            String response = enquiry[4];

            // filtering the options
            boolean isUnreplied = responder.isEmpty() && response.isEmpty();
            boolean isReplied = !responder.isEmpty() && !response.isEmpty();

            if ("unreplied".equals(userChoice) && isUnreplied) {
                result.append(formatEnquiry(enquiryID, sender, responder, enquiryMessage, response));
            } else if ("replied".equals(userChoice) && isReplied) {
                result.append(formatEnquiry(enquiryID, sender, responder, enquiryMessage, response));
            } else if ("all".equals(userChoice)) {
                result.append(formatEnquiry(enquiryID, sender, responder, enquiryMessage, response));
            }
        }

        
        scanner.close();

        // return the string result asked by user
        return result.toString();
    }

    // private method to read csv file
    private List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",", -1); // keep empty fields
                data.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return data;
    }

    // writing back to the csv
    private void writeCSV(String filePath, List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // formatting the csv file
    private String formatEnquiry(String enquiryID, String sender, String responder, String message, String response) {
        return String.format(
            "Enquiry ID: %s\nSender: %s\nResponder: %s\nMessage: %s\nResponse: %s\n\n",
            enquiryID, sender, responder, message, response
        );
    }

    public void replyEnquiry() {
        
        String filePath = "V2\\enquiries.csv";

        // users input for the enquiry to reply to
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the enquiry ID you want to reply to:");
        String enquiryID = scanner.nextLine().trim();

        System.out.println("Enter your response:");
        String response = scanner.nextLine().trim();

        
        List<String[]> allEnquiries = readCSV(filePath);

        
        boolean enquiryFound = false;

        // find and update / reply to the enquiry
        for (String[] enquiry : allEnquiries) {
            if (enquiry[0].equals(enquiryID)) {
                if (enquiry[2].isEmpty() && enquiry[4].isEmpty()) { 
                    enquiry[2] = this.name;
                    enquiry[4] = response;  
                    enquiryFound = true;
                    System.out.println("Reply successfully submitted.");
                } else {
                    System.out.println("This enquiry has already been replied to.");
                    enquiryFound = true;
                }
                break;
            }
        }

        if (!enquiryFound) {
            System.out.println("No enquiry found with the given ID.");
        } else {

            writeCSV(filePath, allEnquiries);
        }

        
        scanner.close();
    }

   

	/**
	 * 
	 * @param project
	 */
    public String viewProjDetails(BTOProject project) {
        
        String filePath = "V2\\ProjectList.csv";

        // check if project is null
        if (project == null) {
            return "Invalid project. Please provide a valid BTOProject object.";
        }

        // get name
        String projectName = project.getProjName();

        
        List<String[]> allProjects = readCSV(filePath);

        // output results of all that matches
        StringBuilder result = new StringBuilder();

        
        boolean projectFound = false;

     
        for (String[] projectData : allProjects) {

            String csvProjectName = projectData[0]; // project Name
            String neighborhood = projectData[1];   // neighborhood
            String type1 = projectData[2];          // type 1
            String unitsType1 = projectData[3];     // number of units for Type 1
            String priceType1 = projectData[4];     // selling price for Type 1
            String type2 = projectData[5];          // type 2
            String unitsType2 = projectData[6];     // number of units for Type 2
            String priceType2 = projectData[7];     // selling price for Type 2
            String appOpenDate = projectData[8];    // application opening date
            String appCloseDate = projectData[9];   // application closing date
            String manager = projectData[10];       // manager
            String officerSlot = projectData[11];   // officer Slot
            String officer = projectData[12];       // officer

            // check if name match
            if (csvProjectName.equals(projectName)) {
                projectFound = true;

                // add any that matches to the results
                result.append(String.format("""
                                            Project Details:
                                            Project Name: %s
                                            Neighborhood: %s
                                            Type 1: %s (%s units, $%s)
                                            Type 2: %s (%s units, $%s)
                                            Application Opening Date: %s
                                            Application Closing Date: %s
                                            Manager: %s
                                            Officer Slot: %s
                                            Officer: %s
                                            
                                            """,
                    csvProjectName, neighborhood,
                    type1, unitsType1, priceType1,
                    type2, unitsType2, priceType2,
                    appOpenDate, appCloseDate,
                    manager, officerSlot, officer
                ));
            }
        }

        // project do not exist in the file
        if (!projectFound) {
            return "No projects found with the name: " + projectName;
        }

        // return 
        return result.toString();
    }

   

}