import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private String applicantNRIC;
    private String projectName;
    private String flatType;
    private String assignedOfficer;

    public Application(String applicantNRIC, String projectName, String flatType) {
        this.applicantNRIC = applicantNRIC;
        this.projectName = projectName;
        this.flatType = flatType;
        saveToCSV(); // save application data when object is created
    }

    public String getApplicantNRIC() {
        return applicantNRIC;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getFlatType() {
        return flatType;
    }

    public String getAssignedOfficer() {
        return assignedOfficer;
    }

    public String getApplicationStatus() {
        String filePath = "Applications.csv";
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

    public void setAssignedOfficer(String assignedOfficer) {
        this.assignedOfficer = assignedOfficer;
        updateCSV(); // update file with new officer
    }

    private void saveToCSV() {
        String filePath = "Applications.csv";
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

    private void updateCSV() {
        String filePath = "Applications.csv";
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(applicantNRIC) && fields[1].equals(projectName) && fields[2].equals(flatType)) {
                    line = applicantNRIC + "," +
                           projectName + "," +
                           flatType + "," +
                           getApplicationStatus() + "," +
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

    public void deleteFromCSV() {
        String filePath = "Applications.csv";
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

    @Override
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
