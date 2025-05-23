import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class dataLoader - Auto-generated Javadoc documentation.
 */
public class dataLoader {

/**
 * Method dataLoader - auto-documented method.
 */
    public dataLoader() {
        
    }

/**
 * Method loadPersonalDetails - auto-documented method.
 */
    public Map<String, Applicant> loadPersonalDetails(String personalCSV) {
        Map<String, Applicant> applicantMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(personalCSV))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length >= 5) {
                    String name = fields[0].trim();
                    String nric = fields[1].trim();
                    int age = Integer.parseInt(fields[2].trim());
                    String maritalStatus = fields[3].trim();
                    String password = fields[4].trim();

                    Applicant applicant = new Applicant(name,nric, password, age, maritalStatus);
                    applicantMap.put(nric, applicant);

                }
            }
        } catch (IOException e) {
            System.out.println("Error reading personal CSV: " + e.getMessage());
        }

        return applicantMap;
    }

/**
 * Method loadApplicationData - auto-documented method.
 */
    public void loadApplicationData(String applicationCSV, Map<String, Applicant> applicantMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(applicationCSV))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length >= 5) {
                    String nric = fields[0].trim();
                    String projectName = fields[1].trim(); // Store as project name string
                    String flatType = fields[2].trim();
                    String applicationStatus = fields[3].trim();
                    String assignedOfficer = fields[4].trim();

                    // Retrieve applicant from map using NRIC
                    Applicant applicant = applicantMap.get(nric);
                    if (applicant != null) {
                        applicant.setAppliedProjectName(projectName);
                        applicant.setFlatType(flatType);
                        applicant.setApplicationStatus(applicationStatus);
                        applicant.setAssignedOfficer(assignedOfficer);

                    } else {
                        System.out.println("No personal data found for NRIC: " + nric);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading application CSV: " + e.getMessage());
        }
    }

/**
 * Method loadApplicants - auto-documented method.
 */
    public List<Applicant> loadApplicants(String personalCSV, String applicationCSV) {
        // First, load personal details into the map
        Map<String, Applicant> applicantMap = loadPersonalDetails(personalCSV);

        // Then, load application data and associate it with the personal details
        loadApplicationData(applicationCSV, applicantMap);

        // Return a list of applicants
        return new ArrayList<>(applicantMap.values());
    }
}
