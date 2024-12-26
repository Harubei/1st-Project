package ProjectITP;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private final int maxLength = 20;
    private String[] AddedUser = new String[20];
    private int UserCount = 0;
    private final String userFile = "Staff_users.txt";  
    Scanner in = new Scanner(System.in);

    String IssueDate = LocalDate.now().toString();
    private String GuestName;
    private String studentNumber;
    private String guestcontact;
    private String role = "Student";
    private String[] ReportedIssue = new String[100];
    private String[] assignedIssues = new String[100];
    private String[] assignedDetails = new String[100];
    private int IssueCount = 0;
    private int assignedCount = 0;
    
    
    
    public static void main(String[] args) {
        Main app = new Main();
        app.loadStaffUsersFromFile();  
        app.loadIssuesFromFile(); 
        app.mainSelector();
    }
    
    public void mainSelector() {
        while (true) {
            System.out.println("\n----- Main Login Menu -----");
            System.out.println("1 - Admin Login");
            System.out.println("2 - Staff Login");
            System.out.println("3 - Guest Login");
            System.out.println("0 - Exit");
            System.out.print(": ");

            int choice = in.nextInt();
            in.nextLine(); 

            switch (choice) {
                case 1 -> AdminPassword(); 
                case 2 -> staffLogin();    
                case 3 -> GuestSignUp();
                case 0 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }
    
    public void AdminPassword() {
        final String setupPassword = "Administrator";

        while (true) {
            System.out.print("Enter the verification password for the admin: ");
            String enteredPassword = in.nextLine().trim();

            if (enteredPassword.equals(setupPassword)) {
                System.out.println("Verification successful.");
                adminLogin();
                return;
            } else {
                System.out.print("Incorrect password. Would you like to try again? (yes/no): ");
                String again = in.nextLine().trim();
                if (again.equalsIgnoreCase("no")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
        }
    }

    public void adminLogin() {
        final String adminUsername = "admin";  
        final String adminPassword = "admin123";

        int attempts = 0;
        final int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.print("Enter username: ");
            String username = in.nextLine().trim();

            System.out.print("Enter password: ");
            String password = in.nextLine().trim();

            if (username.equals(adminUsername) && password.equals(adminPassword)) {
                System.out.println("Login successful!");
                AdminMenu();
                return;
            } else {
                attempts++;
                System.out.println("Incorrect username or password. (" + attempts + "/" + maxAttempts + " attempts)");
            }
        }

        System.out.println("Maximum login attempts reached. Exiting...");
        System.exit(0);
    }

    public void AdminMenu() {
        while (true) {
            System.out.println("----- Admin Main Menu -----");
            System.out.println("1 - Manage Users");
            System.out.println("2 - Manage Reports");
            System.out.println("3 - Facility Status");
            System.out.println("0 - Logout/Exit");
            System.out.print(": ");
            byte choice = in.nextByte();
            in.nextLine(); 
            switch (choice) {
                case 1 -> manageUsersMenu();
                case 2 -> manageReportsMenu();
                case 3 -> viewFacilityStatus();
                case 0 -> {
                    saveStaffUsersToFile();
                    System.out.println("------------------------------");   
                    System.out.print("1-Logout / 2-Exit: ");
                    byte BackExit = in.nextByte();
                    System.out.println("------------------------------"); 
                    switch (BackExit) {
                        case 1 -> mainSelector();
                        case 2 -> System.exit(0);
                        default ->{
                            System.out.println("Invalid input");
                            break;
                        }
                    }
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void manageUsersMenu() {
        while (true) {
            System.out.println("----- Manage Users -----");
            System.out.println("1 - Add Users");
            System.out.println("2 - Update User Info");
            System.out.println("3 - Delete Users");
            System.out.println("4 - View Users");
            System.out.println("0 - Back to Admin Menu");
            System.out.print(": ");
            byte choice = in.nextByte();
            in.nextLine(); 
            switch (choice) {
                case 1 -> AddUser();
                case 2 -> updateUserInfo();
                case 3 -> deleteUser();
                case 4 -> viewUsers();
                case 0 -> {
                    return; 
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void manageReportsMenu() {
        while (true) {
            System.out.println("\n----- Manage Reports -----");
            System.out.println("1 - View Reported Issues");
            System.out.println("2 - Assign Issues to Staff");
            System.out.println("3 - Update Issues");
            System.out.println("0 - Exit");
            System.out.print(": ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1" -> viewReportedIssues();
                case "2" -> assignIssueToStaff();
                case "3" -> updateAssignedTaskStatus();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

        public void viewFacilityStatus() {
        System.out.println("----- Facility Status -----");
        System.out.println("1 - Computer Lab");
        System.out.println("2 - Library");
        System.out.println("3 - Auditorium");
        System.out.println("4 - Sports Complex");
        System.out.println("0 - Back to Admin Menu");
        System.out.print(": ");
        int choice = in.nextInt();
        in.nextLine();

        switch (choice) {
            case 1 -> System.out.println("Computer Lab is open.");
            case 2 -> System.out.println("Library is open.");
            case 3 -> System.out.println("Auditorium is available.");
            case 4 -> System.out.println("Sports Complex is open.");
            case 0 -> {
                return;
            }
            default -> System.out.println("Invalid input. Please try again.");
        }
    }

    public void AddUser() {
        while (true) {
            String lastName, firstName, middleInitial, contact, username, password;

            do {
                System.out.print("Enter your Last Name: ");
                lastName = in.nextLine().trim();
                if (lastName.isBlank()) {
                    System.out.println("Error: Last Name cannot be blank.");
                }
            } while (lastName.isBlank());

            do {
                System.out.print("Enter your First Name: ");
                firstName = in.nextLine().trim();
                if (firstName.isBlank()) {
                    System.out.println("Error: First Name cannot be blank.");
                }
            } while (firstName.isBlank());

            System.out.print("Enter your Middle Initial: ");
            middleInitial = in.nextLine().trim();

            do {
                System.out.print("Enter your Contact No.: ");
                contact = in.nextLine().trim();
                if (contact.isBlank()) {
                    System.out.println("Error: Contact Number cannot be blank.");
                }
            } while (contact.isBlank());

            do {
                System.out.print("Create Username: ");
                username = in.nextLine().trim();
                if (username.isBlank()) {
                    System.out.println("Error: Username cannot be blank.");
                } else if (username.length() > maxLength) {
                    System.out.println("Error: Username exceeds the maximum length of " + maxLength + " characters.");
                }
            } while (username.isBlank() || username.length() > maxLength);

            do {
                System.out.print("Create Password: ");
                password = in.nextLine().trim();
                if (password.isBlank()) {
                    System.out.println("Error: Password cannot be blank.");
                } else if (password.length() > maxLength) {
                    System.out.println("Error: Password exceeds the maximum length of " + maxLength + " characters.");
                }
            } while (password.isBlank() || password.length() > maxLength);

            LocalDate creationDate = LocalDate.now();

            AddedUser[UserCount] = "Last Name: " + lastName + "\n" +
                    "First Name: " + firstName + "\n" +
                    "Middle Initial: " + middleInitial + "\n" +
                    "Contact No.: " + contact + "\n" +
                    "Username: " + username + "\n" +
                    "Password: " + password + "\n" +
                    "Account Created On: " + creationDate + "\n";
            UserCount++;

            System.out.println("User Added Successfully!");

            saveStaffUsersToFile();
            break;
        }
    }    

    public void updateUserInfo() {
        System.out.print("Enter the First Name of the user to update: ");
        String firstName = in.nextLine().trim();

        System.out.print("Enter the Last Name of the user to update: ");
        String lastName = in.nextLine().trim();

        int userIndex = findUserByFirstAndLastName(firstName, lastName);
        if (userIndex == -1) {
            System.out.println("User not found.");
            return;
        }

        while (true) {
            System.out.println("\n--- Update User Info ---");
            System.out.println("1 - Change Username");
            System.out.println("2 - Change Password");
            System.out.println("3 - Change Contact");
            System.out.println("0 - Back");
            System.out.print(": ");
            int choice = in.nextInt();
            in.nextLine(); 
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new Username: ");
                    String newUsername = in.nextLine().trim();
                    updateField(userIndex, 5, newUsername);
                    System.out.println("Username updated successfully.");
                }
                case 2 -> {
                    System.out.print("Enter new Password: ");
                    String newPassword = in.nextLine().trim();
                    updateField(userIndex, 0, newPassword);
                    System.out.println("Password updated successfully.");
                }
                case 3 -> {
                    System.out.print("Enter new Contact: ");
                    String newContact = in.nextLine().trim();
                    updateField(userIndex, 4, newContact);
                    System.out.println("Contact updated successfully.");
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    public void deleteUser() {
        System.out.print("Enter the First Name of the user to delete: ");
        String firstName = in.nextLine().trim();

        System.out.print("Enter the Last Name of the user to delete: ");
        String lastName = in.nextLine().trim();

        int userIndex = findUserByFirstAndLastName(firstName, lastName);
        if (userIndex == -1) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Are you sure you want to delete this account? (yes/no): ");
        String confirmation = in.nextLine().trim();
        if (confirmation.equalsIgnoreCase("yes")) {
            for (int i = userIndex; i < UserCount - 1; i++) {
                AddedUser[i] = AddedUser[i + 1];
            }
            AddedUser[UserCount - 1] = null;
            UserCount--;
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("Account deletion canceled.");
        }
    }

    private int findUserByFirstAndLastName(String firstName, String lastName) {
        for (int i = 0; i < UserCount; i++) {
            String[] userDetails = AddedUser[i].split("\n");
            String storedLastName = userDetails[0].split(": ")[1].trim(); 
            String storedFirstName = userDetails[1].split(": ")[1].trim(); 

            if (storedFirstName.equalsIgnoreCase(firstName) && storedLastName.equalsIgnoreCase(lastName)) {
                return i;
            }
        }
        return -1; 
    }

    private void updateField(int userIndex, int fieldIndex, String newValue) {
        String[] userDetails = AddedUser[userIndex].split("\n");
        StringBuilder updatedUser = new StringBuilder();
        for (int i = 0; i < userDetails.length; i++) {
            if (i == fieldIndex) {
                updatedUser.append(userDetails[i].split(":")[0]).append(": ").append(newValue).append("\n");
            } else {
                updatedUser.append(userDetails[i]).append("\n");
            }
        }
        AddedUser[userIndex] = updatedUser.toString();
    }

    public void viewUsers() {
        System.out.println("\n--- Staff User List ---");
        for (int i = 0; i < UserCount; i++) {
            System.out.println((i + 1) + ": " + AddedUser[i]);
        }
    }

    public void saveStaffUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (int i = 0; i < UserCount; i++) {
                writer.write(AddedUser[i]);
                writer.write("\n------------------------------------\n");
            }
            System.out.println("Users saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }
    
    public void loadStaffUsersFromFile() {
        try (Scanner fileScanner = new Scanner(new File(userFile))) {
            StringBuilder userDetails = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.equals("----")) {
                    if (userDetails.length() > 0) {
                        AddedUser[UserCount] = userDetails.toString();
                        UserCount++;
                    }
                    userDetails.setLength(0); 
                } else {
                    userDetails.append(line).append("\n");
                }
            }

            if (userDetails.length() > 0) {
                AddedUser[UserCount] = userDetails.toString();
                UserCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("No user data found. Starting fresh.");
        }
    }
    
        public void GuestSignUp() {
        System.out.println("----- Guest -----");

        do {
            System.out.print("Enter Name: ");
            GuestName = in.nextLine().trim();
            if (GuestName.isBlank()) {
                System.out.println("Error: Name cannot be blank. Please enter a valid name.");
            }
        } while (GuestName.isBlank());

        System.out.print("Enter Student Number (leave blank if not a student): ");
        studentNumber = in.nextLine().trim();
        if (studentNumber.isBlank()) {
            studentNumber = "N/A";
            role = "Guest";  
        }

        do {
            System.out.print("Enter Contact Info: ");
            guestcontact = in.nextLine().trim();
            if (guestcontact.isBlank()) {
                System.out.println("Error: Contact Info cannot be blank. Please enter a valid contact.");
            }
        } while (guestcontact.isBlank());

        do {
            System.out.print("Enter Role: ");
            role = in.nextLine().trim();
            if (role.isBlank()) {
                System.out.println("Error: Role cannot be blank. Please enter a valid role.");
            }
        } while (role.isBlank());

        System.out.println("Guest Sign-Up Successful! Role: " + role);
        
        logGuestLogin();
        guestMenu();
    }

    public void guestMenu() {
        while (true) {
            System.out.println("\n----- Guest Menu -----");
            System.out.println("1 - Report an Issue");
            System.out.println("2 - View Reported Issues");
            System.out.println("3 - View Facilities");
            System.out.println("0 - Logout/Exit");
            System.out.print(": ");
            byte choice = in.nextByte();
            in.nextLine(); 

            switch (choice) {
                case 1 -> reportIssue();
                case 2 -> viewReportedIssues(); 
                case 3 -> viewFacilityStatus();
                case 0 -> {
                    saveStaffUsersToFile();
                    System.out.println("------------------------------");   
                    System.out.print("1-Logout / 2-Exit: ");
                    byte BackExit = in.nextByte();
                    System.out.println("------------------------------"); 
                    switch (BackExit) {
                        case 1 -> mainSelector();
                        case 2 -> System.exit(0);
                        default ->{
                            System.out.println("Invalid input");
                            break;
                        }
                    }
                }
                default -> System.out.println("Incorrect input. Please try again.");
            }
        }
    }
    
    public void assignIssueToStaff() {
        System.out.println("\n----- Assign Issues to Staff -----");

        if (IssueCount == 0) {
            System.out.println("No issues available to assign.");
            return;
        }

        System.out.println("Select an Issue to Assign:");
        for (int i = 0; i < IssueCount; i++) {
            System.out.println((i + 1) + " - " + ReportedIssue[i]);
        }

        System.out.print(": ");
        int issueChoice = in.nextInt();
        in.nextLine(); 

        if (issueChoice < 1 || issueChoice > IssueCount) {
            System.out.println("Invalid selection. Please try again.");
            return;
        }

        String selectedIssue = ReportedIssue[issueChoice - 1];
        System.out.println("Selected Issue: " + selectedIssue);

        System.out.println("\nSelect Staff to Assign to the Issue:");
        boolean staffAvailable = false;
        for (int i = 0; i < AddedUser.length; i++) {
            if (AddedUser[i] != null) {
                System.out.println((i + 1) + " - " + AddedUser[i]);
                staffAvailable = true;
            }
        }

        if (!staffAvailable) {
            System.out.println("No staff available to assign.");
            return;
        }

        System.out.print(": ");
        int staffChoice = in.nextInt();
        in.nextLine(); 

        if (staffChoice < 1 || staffChoice > AddedUser.length || AddedUser[staffChoice - 1] == null) {
            System.out.println("Invalid selection. Please try again.");
            return;
        }

        String assignedStaff = AddedUser[staffChoice - 1];
        System.out.println("Assigned to: " + assignedStaff);

        String status = "Pending";
        String verificationLine = "A staff member will visit to verify the issue.";

        String assignmentDetails = "Issue: " + selectedIssue + " assigned to " + assignedStaff + "\nStatus: " + status + "\n" + verificationLine;
        assignedIssues[assignedCount] = assignmentDetails;
        assignedDetails[assignedCount] = selectedIssue;
        assignedCount++;

        System.out.println("\n---- Issue Assigned Successfully ----");
        System.out.println("Assignment Details: " + assignmentDetails);
    }


    public void updateAssignedTaskStatus() {
        System.out.println("\n----- Update Task Status -----");

        if (assignedCount == 0) {
            System.out.println("No assigned tasks to update.");
            return;
        }

        System.out.println("Select a Task to Update:");
        for (int i = 0; i < assignedCount; i++) {
            System.out.println((i + 1) + " - " + assignedIssues[i]);
        }

        System.out.print(": ");
        int taskChoice = in.nextInt();
        in.nextLine(); 

        if (taskChoice < 1 || taskChoice > assignedCount) {
            System.out.println("Invalid selection. Please try again.");
            return;
        }

        String selectedTask = assignedIssues[taskChoice - 1];
        System.out.println("Selected Task: " + selectedTask);

        System.out.println("Select New Status for the Task:");
        System.out.println("1 - Pending");
        System.out.println("2 - Ongoing");
        System.out.println("3 - Completed");
        System.out.print(": ");
        String statusChoice = in.nextLine().trim();

        String newStatus = "";

        switch (statusChoice) {
            case "1" -> newStatus = "Pending";
            case "2" -> newStatus = "Ongoing";
            case "3" -> newStatus = "Completed";
            default -> {
                System.out.println("Invalid status choice. Please try again.");
                return;
            }
        }

        String updatedTask = selectedTask.replaceFirst("Status: [a-zA-Z]+", "Status: " + newStatus);
        assignedIssues[taskChoice - 1] = updatedTask;

        System.out.println("\n---- Task Status Updated Successfully ----");
        System.out.println("Updated Task: " + updatedTask);
    }

    public void reportIssue() {
        String facilityType, issueType, description, urgencyLevel;
        String choice;

        System.out.println("------------Report an Issue------------");

        System.out.println("Select Facility:");
        System.out.println("1 - Room");
        System.out.println("2 - Laboratory");
        System.out.println("3 - Restroom");
        System.out.println("4 - Other");
        System.out.println("0 - Back");

        while (true) {
            System.out.print(": ");
            choice = in.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Room No.: ");
                    facilityType = "Room No.: " + in.nextLine();
                }
                case "2" -> {
                    System.out.print("Laboratory Name: ");
                    facilityType = "Laboratory: " + in.nextLine();
                }
                case "3" -> {
                    System.out.print("Restroom No.: ");
                    facilityType = "Restroom No.: " + in.nextLine();
                }
                case "4" -> {
                    System.out.print("Specify: ");
                    facilityType = "Other: " + in.nextLine();
                }
                case "0" -> {
                    return;
                }
                default -> {
                    System.out.println("Incorrect Input. Please try again.");
                    continue;
                }
            }

            System.out.println("Select Issue Type:");
            System.out.println("1 - Electrical");
            System.out.println("2 - Plumbing");
            System.out.println("3 - Cleanliness");
            System.out.println("4 - Safety");
            System.out.println("5 - Other");

            while (true) {
                System.out.print(": ");
                choice = in.nextLine();

                switch (choice) {
                    case "1" -> issueType = "Electrical Issue";
                    case "2" -> issueType = "Plumbing Issue";
                    case "3" -> issueType = "Cleanliness Issue";
                    case "4" -> issueType = "Safety Issue";
                    case "5" -> {
                        System.out.print("Specify Issue: ");
                        issueType = "Other Issue: " + in.nextLine();
                    }
                    default -> {
                        System.out.println("Incorrect Input. Please try again.");
                        continue;
                    }
                }
                break;
            }

            System.out.print("Issue Description: ");
            description = in.nextLine().trim();

            System.out.println("Select Urgency Level:");
            System.out.println("1 - Low");
            System.out.println("2 - Medium");
            System.out.println("3 - High");

            while (true) {
                System.out.print(": ");
                choice = in.nextLine();

                switch (choice) {
                    case "1" -> urgencyLevel = "Low";
                    case "2" -> urgencyLevel = "Medium";
                    case "3" -> urgencyLevel = "High";
                    default -> {
                        System.out.println("Incorrect Input. Please try again.");
                        continue;
                    }
                }
                break;
            }

            String issueDetails = "Date of Report: " + IssueDate +
                    "\nFacility Type: " + facilityType +
                    "\nIssue Type: " + issueType +
                    "\nDescription: " + description +
                    "\nUrgency Level: " + urgencyLevel +
                    "\nReported by: " + GuestName +
                    "\nPosition: " + role +
                    "\nContact Information: " + guestcontact;

            if (IssueCount < ReportedIssue.length) {
                ReportedIssue[IssueCount] = issueDetails;
                IssueCount++;
                System.out.println("Issue reported successfully!");
            } else {
                System.out.println("Max issue limit reached.");
            }

            saveIssueToFile(issueDetails);
            break;
        }
    }
    
    public void saveIssueToFile(String issueDetails) {
        String issueFile = "Created_Issues.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(issueFile, true))) {
            writer.write(issueDetails);
            writer.write("\n------------------------------------\n");
            System.out.println("Issue saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving issue to file: " + e.getMessage());
        }
    }
    
        public void loadIssuesFromFile() {
        String issueFile = "Created_Issues.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(issueFile))) {
            String line;
            StringBuilder issueDetails = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                issueDetails.append(line).append("\n");

                if (line.trim().equals("------------------------------------")) {
                    ReportedIssue[IssueCount] = issueDetails.toString();
                    IssueCount++;
                    issueDetails.setLength(0);
                }
            }
            System.out.println("Issues loaded successfully.");
        } catch (IOException e) {
        }
    }
    
    public void viewReportedIssues() {
        System.out.println("\n----- Reported Issues -----");

        if (IssueCount == 0) {
            System.out.println("No issues have been reported yet.");
        } else {
            for (int i = 0; i < IssueCount; i++) {
                System.out.println("\nIssue #" + (i + 1) + ":");
                System.out.println(ReportedIssue[i]);
                System.out.println("----------------------------------------");
            }
        }
    }

    public void Staffmenu() {
        while (true) {
            System.out.println("\n----- Staff Main Menu -----");
            System.out.println("1 - View Assigned Issues");
            System.out.println("2 - Update Assigned Task Status");
            System.out.println("3 - Report Issues");
            System.out.println("4 - Update Profile");
            System.out.println("0 - Logout/Exit");
            System.out.print(": ");

            byte choice = in.nextByte();
            in.nextLine(); 

            switch (choice) {
                case 1 -> viewAssignedIssues();
                case 2 -> updateAssignedTaskStatus();
                case 3 -> reportIssue();
                case 4 -> updateUserInfo();
                case 0 -> {
                    saveStaffUsersToFile();
                    System.out.println("------------------------------");   
                    System.out.print("1-Logout / 2-Exit: ");
                    byte BackExit = in.nextByte();
                    System.out.println("------------------------------"); 
                    switch (BackExit) {
                        case 1 -> mainSelector();
                        case 2 -> System.exit(0);
                        default ->{
                            System.out.println("Invalid input");
                            break;
                        }
                    }
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void staffLogin() {
        System.out.println("\n----- Staff Login -----");
        for (int attempts = 1; attempts <= 3; attempts++) {
            System.out.print("Enter Username: ");
            String username = in.nextLine().trim();

            System.out.print("Enter Password: ");
            String password = in.nextLine().trim();

            int userIndex = findUserByUsername(username);
            if (userIndex != -1) {
                String[] userDetails = AddedUser[userIndex].split("\n");
                String storedPassword = userDetails[5].split(": ")[1].trim();

                if (password.equals(storedPassword)) {
                    System.out.println("Login successful! Welcome, " + userDetails[1].split(": ")[1] + "!");
                    Staffmenu();
                    return; 
                }
            }

            System.out.println("Invalid username or password. (" + attempts + "/3 attempts)");
            if (attempts == 3) {
                System.out.println("Maximum login attempts reached. Exiting...");
                return; 
            }
        }
    }
    
        private int findUserByUsername(String username) {
        for (int i = 0; i < UserCount; i++) {
            String[] userDetails = AddedUser[i].split("\n");
            String storedUsername = userDetails[4].split(": ")[1].trim();

            if (storedUsername.equalsIgnoreCase(username)) {
                return i; 
            }
        }
        return -1;
    }
    
    public void viewAssignedIssues() {
        System.out.println("\n----- Assigned Issues -----");

        if (assignedCount == 0) {
            System.out.println("No issues have been assigned yet.");
        } else {
            for (int i = 0; i < assignedCount; i++) {
                System.out.println("\nAssigned Issue #" + (i + 1) + ":");
                System.out.println(assignedIssues[i]);
                System.out.println(assignedDetails[i]);
                System.out.println("----------------------------------------");
            }
        }
    }
 
    public void logGuestLogin() {
        String guestFile = "Guest_Logins.txt";
        String logEntry = "Date: " + IssueDate +
                          "\nName: " + GuestName +
                          "\nStudent Number: " + studentNumber +
                          "\nContact Info: " + guestcontact +
                          "\nRole: " + role +
                          "\n------------------------------------\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(guestFile, true))) {
            writer.write(logEntry);
            System.out.println("Guest login details saved to file.");
        } catch (IOException e) {
            System.out.println("Error logging guest login: " + e.getMessage());
        }
    }
}