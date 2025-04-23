import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

public class LoginManager {

	private ArrayList<Users> userList;
	
	public LoginManager()
	{
		userList = new ArrayList<>();
	}

	public void addUser(Users user)
	{
		userList.add(user);
	}

	/**
	 * 
	 * @param nric
	 * @param password
	 */
	public Users authenticateUser(String nric, String password) {
		// TODO - implement LoginManager.authenticateUser
		for(Users user: userList)
		{
			if(user.login(nric, password))
			{
				return user;
			}
		}
		return null;
		//throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param user
	 * @param password
	 */
	public void changeUserPassword(Users user, String password) {
		
		user.changePassword(password);
	}

	public void loadApplicant(String filename)
	{
		try(BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
			String line;
			boolean skipHeader = true;

			while((line = br.readLine()) != null)
			{
				if(skipHeader)
				{
					skipHeader = false;
					continue;
				}

				String[] fields = line.split(",");
				if(fields.length >= 5)
				{
					String name = fields[0].trim();
					String nric = fields[1].trim();
					int age = Integer.parseInt(fields[2].trim());
					boolean maritalStatus = Boolean.parseBoolean(fields[3].trim());
					String password = fields[4].trim();

					Applicant app = new Applicant(
						name,
						nric,
						password,
						age,
						maritalStatus
					);

					addUser(app);
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Error reading applicants from CSV: "+ e.getMessage());
		}
	}

	public void saveApplicantToCSV(String pathname)
	{
		try(FileWriter writer = new FileWriter(pathname))
		{
			writer.write("Name,NRIC,Age,Marital Status, Password\n");
			for(Users user: userList)
			{
				if(user instanceof Applicant)
				{
					Applicant app = (Applicant) user;
					String writeLine = String.format("%s,%s,%d,%b,%s\n",
						app.getName(),
						app.getNRIC(),
						app.getAge(),
						app.getMaritalStatus(),
						app.getPassword()
					);
					writer.write(writeLine);
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Error saving applicant to CSV: " + e.getMessage());
		}
	}

	public void loadOfficer(String officerFilename)
	{
		try(BufferedReader br = new BufferedReader(new FileReader(officerFilename)))
		{
			String reader;
			boolean skipHeader  = true;

			while((reader = br.readLine()) != null)
			{
				if(skipHeader)
				{
					skipHeader = false;
					continue;
				}

				String[] fields = reader.split(",");
				if(fields.length >= 5)
				{
					String name = fields[0].trim();
					String nric = fields[1].trim();
					int age = Integer.parseInt(fields[2].trim());
					boolean maritalStatus = Boolean.parseBoolean(fields[3].trim());
					String password = fields[4].trim();

					//Officer's constructor have yet to be made, hence the errors
					HDBOfficer officer = new HDBOfficer(
						name,
						nric,
						password,
						age,
						maritalStatus
						//more attributes to be added(eg: role, staffID, assignedproject)
					);

					addUser(officer);
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Error reading officers from CSV: " + e.getMessage());
		}
	}

	//Update changes into officerList.csv
	public void saveOfficerToCSV(String pathname)
	{
		try(FileWriter writer = new FileWriter(pathname))
		{
			writer.write("Name,NRIC,Age,Marital Status, Password\n");
			for(Users user: userList)
			{
				if(user instanceof HDBOfficer)
				{
					HDBOfficer app = (HDBOfficer) user;
					String writeLine = String.format("%s,%s,%d,%b,%s\n",
						app.getName(),
						app.getNRIC(),
						app.getAge(),
						app.getMaritalStatus(),
						app.getPassword()
					);
					writer.write(writeLine);
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Error saving applicant to CSV: " + e.getMessage());
		}
	}


	//LoadManager should be commented out when HDBManager's constructor is completed

	public void loadManager(String managerFilename)
	{
		try(BufferedReader br = new BufferedReader(new FileReader(managerFilename)))
		{
			String reader;
			boolean skipHeader  = true;

			while((reader = br.readLine()) != null)
			{
				if(skipHeader)
				{
					skipHeader = false;
					continue;
				}

				String[] fields = reader.split(",");
				if(fields.length >= 6)
				{
					String name = fields[0].trim();
					String nric = fields[1].trim();
					int age = Integer.parseInt(fields[2].trim());
					boolean maritalStatus = Boolean.parseBoolean(fields[3].trim());
					String password = fields[4].trim();
					int staffID = Integer.parseInt(fields[5].trim());

					//Manager constructor have yet to be made, hence the errors
					HDBManager manager = new HDBManager(
						name,
						nric,
						password,
						age,
						maritalStatus,
						staffID
						//more attributes to be added(eg: role, staffID, assignedproject)
					);

					addUser(manager);
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Error reading officers from CSV: " + e.getMessage());
		}
	}

	public void saveManagerToCSV(String pathname)
	{
		try(FileWriter writer = new FileWriter(pathname))
		{
			writer.write("Name,NRIC,Age,Marital Status, Password\n");
			for(Users user: userList)
			{
				if(user instanceof HDBManager)
				{
					HDBManager manager = (HDBManager) user;
					String writeLine = String.format("%s,%s,%d,%b,%s,%d\n",
						manager.getName(),
						manager.getNRIC(),
						manager.getAge(),
						manager.getMaritalStatus(),
						manager.getPassword(),	
						manager.getStaffID()
					);
					writer.write(writeLine);
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Error saving applicant to CSV: " + e.getMessage());
		}
	}

    public HDBOfficer findOfficerByNRIC(String nric) {
        for (Users users : userList) {
            if (users instanceof HDBOfficer && users.getNRIC().equals(nric)) {
                return (HDBOfficer) users;
            }
        }
        return null;
    }

	public Applicant findApplicantByNRIC(String nric)
	{
		for(Users users : userList)
		{
			if(users instanceof Applicant && users.getNRIC().equals(nric))
			{
				return (Applicant) users;
			}
		}
		return null;
	}


}