import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Application - Represents the application in the system.
 */
public class Application {
    private String applicantNRIC;
    private String projectName;
    private String flatType;
    private String assignedOfficer;
    private String applicationStatus;

/**
 * Method Application - performs the Application operation.
 */
    public Application(String applicantNRIC, String projectName, String flatType) {
        this.applicantNRIC = applicantNRIC;
        this.projectName = projectName;
        this.flatType = flatType;
        this.applicationStatus = "Pending";
        this.assignedOfficer = "Unassigned";
    }

/**
 * Method Application - performs the Application operation.
 */
    public Application(String applicantNRIC, String projectName, String flatType, String applicationStatus, String assignedOfficer) {
        this.applicantNRIC = applicantNRIC;
        this.projectName = projectName;
        this.flatType = flatType;
        this.applicationStatus = applicationStatus;
        this.assignedOfficer = assignedOfficer;
    }
    

/**
 * Method getApplicantNRIC - performs the getApplicantNRIC operation.
 */
    public String getApplicantNRIC() {
        return applicantNRIC;
    }

/**
 * Method getProjectName - performs the getProjectName operation.
 */
    public String getProjectName() {
        return projectName;
    }

/**
 * Method getFlatType - performs the getFlatType operation.
 */
    public String getFlatType() {
        return flatType;
    }

/**
 * Method getAssignedOfficer - performs the getAssignedOfficer operation.
 */
    public String getAssignedOfficer() {
        return assignedOfficer;
    }

/**
 * Method getApplicationStatus - performs the getApplicationStatus operation.
 */
    public String getApplicationStatus() {
        String filePath = "V2\\Applications.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(applicantNRIC) && fields[1].equals(projectName) && fields[2].equals(flatType)) {
                    return fields[3]; // return status from csv
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading application status from CSV: " + e.getMessage());
        }
        return "Status not found";
    }

/**
 * Method setApplicationStatus - performs the setApplicationStatus operation.
 */
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
        updateCSV();
    }
    

/**
 * Method setAssignedOfficer - performs the setAssignedOfficer operation.
 */
    public void setAssignedOfficer(String assignedOfficer) {
        this.assignedOfficer = assignedOfficer;
        updateCSV(); // update file with new officer
    }

/**
 * Method saveToCSV - performs the saveToCSV operation.
 */
    public void saveToCSV() {
        String filePath = "V2\\Applications.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(applicantNRIC + "," +
                     projectName + "," +
                     flatType + "," +
                     "Pending" + "," + // default status
                     (assignedOfficer != null ? assignedOfficer : "") + "\n");
        } catch (IOException e) {
            System.out.println("Error saving application to CSV: " + e.getMessage());
        }
    }

/**
 * Method loadFromCSV - performs the loadFromCSV operation.
 */
    public static Application loadFromCSV(String nric) {
        String filePath = "V2\\Applications.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 5 && fields[0].equals(nric)) {
                    Application app = new Application(fields[0], fields[1], fields[2]);
                    app.applicationStatus = fields[3];
                    app.assignedOfficer = fields[4];
                    return app;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading application: " + e.getMessage());
        }
        return null; // no application found
    }

/**
 * Method updateCSV - performs the updateCSV operation.
 */
    public void updateCSV() {
        String filePath = "V2\\Applications.csv";
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(applicantNRIC) && fields[1].equals(projectName) && fields[2].equals(flatType)) {
                    line = applicantNRIC + "," +
                           projectName + "," +
                           flatType + "," +
                           getApplicationStatus()+ "," +
                           (assignedOfficer != null ? assignedOfficer : "");
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading applications from CSV: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : lines) {
                bw.write(updatedLine + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing applications to CSV: " + e.getMessage());
        }
    }

/**
 * Method deleteFromCSV - performs the deleteFromCSV operation.
 */
    public void deleteFromCSV() {
        String filePath = "V2\\Applications.csv";
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (!(fields[0].equals(applicantNRIC) && fields[1].equals(projectName) && fields[2].equals(flatType))) {
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

/**
 * Method getApplicationByNRIC - performs the getApplicationByNRIC operation.
 */
    public static Application getApplicationByNRIC(String csvPath, String nric) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[0].equalsIgnoreCase(nric)) {
                    return new Application(parts[0], parts[1], parts[2], parts[3], parts[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    @Override
/**
 * Method toString - performs the toString operation.
 */
    public String toString() {
        return "Application{" +
               "Applicant NRIC='" + applicantNRIC + '\'' +
               ", Project Name='" + projectName + '\'' +
               ", Flat Type='" + flatType + '\'' +
               ", Status='" + getApplicationStatus() + '\'' +
               ", Assigned Officer='" + assignedOfficer + '\'' +
               '}';
    }

    
}
