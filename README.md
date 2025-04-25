# HDB BTO Application Project

This is a Java-based HDB BTO Application Management System designed for applicants to apply for BTO projects and for HDB officers and managers to oversee and manage those projects efficiently. The project adopts the MVC (Model-View-Controller) design pattern and uses CSV files for data persistence.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [How to Run](#how-to-run)
- [Credits](#credits)

---

## Features

### Applicants
- Register and log in securely
- View available BTO projects
- Apply for a project and specific flat type
- Submit enquiries about BTO projects

### HDB Officers
- Log in securely
- Can apply as applicants (except for projects they manage)
- Manage assigned BTO projects (flat availability, enquiries)
- Respond to applicant enquiries

### HDB Managers
- Log in securely
- Create, edit, or remove BTO projects
- Approve officer registrations
- Respond to applicant enquiries

---

## Tech Stack

- Language: Java (latest version)
- File Storage: CSV
- Design Pattern: MVC (Model-View-Controller)
- Tools: Java I/O (BufferedReader, BufferedWriter, FileReader, FileWriter)

---

## Project Structure

- Model Layer: Contains all core classes (e.g., `Applicant`, `Application`, `BTOProject`)
- Controller Layer: Manages the logic between view and model (e.g., `HDBManagerCLI`, `HDBOfficerCLI`)
- View Layer: Handles user interactions (console-based)
- Utility/Helper: Includes file data loaders (`dataLoader`) and CSV operations

---

## How to Run

1. Ensure you have the latest version of Java installed.
2. Download or clone the repository to your local machine.
3. Adjust all CSV file paths in the source code to match your local directory structure.
4. Compile the `.java` files and run the main class (e.g., `Main.java` or your controller CLI).

---

## Credits

Developed by:

- Muhammad Rushaidy bin Roslan  
- Lim Shi En Stacia  
- Lin Mei Tong  
- Nai Junn Rong  
- Koh Tat Yao

---

## License

This project is for educational purposes and is not affiliated with or endorsed by HDB Singapore.
