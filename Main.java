package myvc;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Provides the main user interface for the MYVC Registration System.
 * Allows users to register members, manage tournament registrations,
 * view member information, search members, and view club statistics.
 */
public class Main {

    // Scanner gets input from the user.
    static Scanner scanner = new Scanner(System.in);

    // This object manages the members.
    static RegistrationSystem system = new RegistrationSystem();

    // Name of the file used to save member information.
    static String fileName = "members.txt";

    /**
     * Starts the MYVC Registration System and displays the main menu.
     * Previously saved member information is loaded when the program starts.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        // Load previously saved members when the program starts.
        system.loadFromFile(fileName);

        System.out.println("==============================================");
        System.out.println("   YOUTH VOLLEYBALL CLUB REGISTRATION SYSTEM");
        System.out.println("==============================================");

        boolean running = true;

        while (running) {

            displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    registerMember();
                    break;

                case 2:
                    displayMembers();
                    break;

                case 3:
                    displayMembersAlphabetically();
                    break;

                case 4:
                    registerForTournament();
                    break;

                case 5:
                    displayStatistics();
                    break;

                case 6:
                    searchMember();
                    break;

                case 7:
                    editMember();
                    break;

                case 8:
                    deleteMember();
                    break;

                case 9:
                    system.saveToFile(fileName);
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please choose 1-7.");
            }
        }

        System.out.println();
        System.out.println("Thank you for using the MYVC Registration System!");

        scanner.close();
    }

    /**
     * Displays the main menu and available registration system options.
     */
    public static void displayMenu() {

        System.out.println();
        System.out.println("--------------- MAIN MENU ----------------");
        System.out.println("1. Register new member");
        System.out.println("2. View all members");
        System.out.println("3. View members alphabetically");
        System.out.println("4. Register for tournament");
        System.out.println("5. View club statistics");
        System.out.println("6. Search for member");
        System.out.println("7. Edit member");
        System.out.println("8. Delete member");
        System.out.println("9. Save and exit");
        System.out.println("------------------------------------------");
    }

    /**
     * Collects information and registers a new member.
     */
    public static void registerMember() {

        if (system.getMemberCount() >= 20) {
            System.out.println("The club has reached the maximum of 20 members.");
            return;
        }

        System.out.println();
        System.out.println("---------- NEW MEMBER REGISTRATION ----------");

        String firstName = readName("First name: ");
        String lastName = readName("Last name: ");

        String relation = readRequired("Parent/guardian relationship: ");

        int year;
        int month;
        int day;

        // Repeats until a valid birthday is entered.
        do {

            year = readInt("Birth year (2007-2014): ");
            month = readInt("Birth month (1-12): ");
            day = readInt("Birth day (1-31): ");

            if (!validBirthday(year, month, day)) {
                System.out.println("Invalid birthday. Please try again.");
            }

        } while (!validBirthday(year, month, day));

        String gender = readGender();

        String address = readAddress();

        String city = readCity();

        String postalCode = readPostalCode();

        String phone = readPhone();

        // Create the family/guardian object.
        Family family = new Family(firstName, lastName, relation, address, city, postalCode, phone);

        // Create and add the member.
        Member member = system.createMember(firstName, lastName, year, month, day, gender, address, city, postalCode, phone, family);

        if (member == null) {
            System.out.println("Unable to register member.");
            return;
        }

        System.out.println();
        System.out.println("Member successfully registered!");
        System.out.println("Name: " + member.getFullName());
        System.out.println("Membership #: " + member.getMembershipNumber());

        // Ask if the member wants to register for a tournament.
        registerForTournament(member);

        // Save the updated information.
        system.saveToFile(fileName);
    }

    /**
     * Displays information for all registered members.
     */
    public static void displayMembers() {

        ArrayList<Member> members = system.getMembers();

        if (members.size() == 0) {
            System.out.println("No members have been registered.");
            return;
        }

        System.out.println();
        System.out.println("--------------- ALL MEMBERS ---------------");

        for (int i = 0; i < members.size(); i++) {
            displayMember(members.get(i));
        }
    }

    /**
     * Displays the personal, contact, and tournament information
     * for a specified member.
     *
     * @param member the member whose information will be displayed
     */
    public static void displayMember(Member member) {

        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("Name: " + member.getFullName());
        System.out.println("Membership #: " + member.getMembershipNumber());

        System.out.println(
                "Birthday: "
                        + member.getMonth() + "/"
                        + member.getDay() + "/"
                        + member.getYear()
        );

        System.out.println("Gender: " + member.getGender());
        System.out.println("Address: " + member.getAddress());
        System.out.println("City: " + member.getCity());
        System.out.println("Postal Code: " + member.getPostalCode());
        System.out.println("Phone: " + member.getPhoneNumber());

        System.out.println("Tournament Periods: " + member.getRegisteredPeriods());
    }

    /**
     * Sorts and displays members alphabetically by full name.
     */
    public static void displayMembersAlphabetically() {

        if (system.getMemberCount() == 0) {
            System.out.println("No members have been registered.");
            return;
        }

        system.sortAlphabetically();

        ArrayList<Member> members = system.getMembers();

        System.out.println();
        System.out.println("------- MEMBERS ALPHABETICALLY -------");

        for (int i = 0; i < members.size(); i++) {

            System.out.println((i + 1) + ". " + members.get(i).getFullName());
        }
    }

    /**
     * Displays the results of a member search.
     *
     * @param results the list of members found by the search
     */
    public static void displaySearchResults(ArrayList<Member> results) {

        if (results.size() == 0) {
            System.out.println("No matching members were found.");
            return;
        }

        System.out.println();
        System.out.println("----------- SEARCH RESULTS -----------");

        for (int i = 0; i < results.size(); i++) {
            displayMember(results.get(i));
        }
    }
    /**
     * Searches for a member using their membership number.
     */
    /**
     * Allows the user to search for members using different search criteria.
     */
    /**
     * Allows the user to search for members using different search criteria.
     */
    public static void searchMember() {

        System.out.println();
        System.out.println("----------- SEARCH MEMBERS -----------");
        System.out.println("1. Search by membership number");
        System.out.println("2. Search by first name");
        System.out.println("3. Search by last name");
        System.out.println("4. Search by gender");
        System.out.println("5. Search by tournament period");
        System.out.println("6. Return to main menu");

        int choice = readInt("Choose a search option: ");

        switch (choice) {

            case 1:
                int number = readInt("Enter membership #: ");

                Member member = system.findMember(number);

                if (member == null) {
                    System.out.println("Member was not found.");
                } else {
                    displayMember(member);
                }
                break;

            case 2:
                String firstName = readName("Enter first name: ");

                ArrayList<Member> firstNameResults =
                        system.findMembersByFirstName(firstName);

                displaySearchResults(firstNameResults);
                break;

            case 3:
                String lastName = readName("Enter last name: ");

                ArrayList<Member> lastNameResults =
                        system.findMembersByLastName(lastName);

                displaySearchResults(lastNameResults);
                break;

            case 4:
                String gender = readGender();

                ArrayList<Member> genderResults =
                        system.findMembersByGender(gender);

                displaySearchResults(genderResults);
                break;

            case 5:
                int period = readInt("Enter tournament period (1-3): ");

                if (period < 1 || period > 3) {
                    System.out.println("Invalid tournament period.");
                } else {
                    ArrayList<Member> periodResults =
                            system.findMembersByPeriod(period);

                    displaySearchResults(periodResults);
                }
                break;

            case 6:
                return;

            default:
                System.out.println("Invalid choice. Please choose 1-6.");
        }
    }

    /**
     * Allows the user to choose a member for tournament registration.
     */
    public static void registerForTournament() {

        if (system.getMemberCount() == 0) {
            System.out.println("No members have been registered.");
            return;
        }

        int number = readInt("Enter membership #: ");

        Member member = system.findMember(number);

        if (member == null) {
            System.out.println("Member was not found.");
            return;
        }

        registerForTournament(member);
    }

    /**
     * Registers a specific member for one or more tournament periods.
     *
     * @param member the member to register for tournament periods
     */
    public static void registerForTournament(Member member) {

        String answer;

        do {

            System.out.println();
            System.out.println("--------- TOURNAMENT PERIODS ---------");
            System.out.println("1. Period 1: June 2-27");
            System.out.println("2. Period 2: July 7-31");
            System.out.println("3. Period 3: August 4-29");

            int period = readInt("Choose a period (1-3): ");

            if (member.registerForPeriod(period)) {

                System.out.println(
                        "Successfully registered for Period " + period + "."
                );

                system.saveToFile(fileName);

            } else {

                System.out.println("Invalid period or member is already registered.");
            }

            answer = readRequired("Register for another period? (yes/no): ");

        } while (answer.equalsIgnoreCase("yes"));
    }

    /**
     * Displays basic statistics about the club, including
     * membership totals and the oldest and youngest members.
     */
    public static void displayStatistics() {

        System.out.println();
        System.out.println("----------- CLUB STATISTICS -----------");

        System.out.println("Total members: " + system.getMemberCount());

        System.out.println("Boys: " + system.getBoysCount());

        System.out.println("Girls: " + system.getGirlsCount());

        Member oldest = system.getOldestMember();
        Member youngest = system.getYoungestMember();

        if (oldest != null) {
            System.out.println("Oldest member: " + oldest.getFullName());
        }

        if (youngest != null) {
            System.out.println("Youngest member: " + youngest.getFullName());
        }
    }

    // Makes sure the user enters a valid name.
    public static String readName(String message) {

        while (true) {

            String name = readRequired(message);

            if (name.length() >= 2) {
                return name;
            }

            System.out.println("Name must contain at least 2 characters.");
        }
    }

    // Makes sure the address is valid.
    public static String readAddress() {

        while (true) {

            String address = readRequired("Address: ");

            if (address.length() >= 2) {
                return address;
            }

            System.out.println("Invalid address.");
        }
    }

    // Makes sure the city is valid.
    public static String readCity() {

        while (true) {

            String city = readRequired("City: ");

            if (city.length() >= 2) {
                return city;
            }

            System.out.println("Invalid city.");
        }
    }

    // Makes sure the postal code has six characters.
    public static String readPostalCode() {

        while (true) {

            String postalCode = readRequired("Postal code: ");

            if (postalCode.length() == 6) {
                return postalCode.toUpperCase();
            }

            System.out.println("Postal code must contain 6 characters.");
        }
    }

    // Makes sure the phone number contains at least 10 digits.
    public static String readPhone() {

        while (true) {

            String phone = readRequired("Phone number: ");

            String digits = phone.replaceAll("\\D", "");

            if (digits.length() >= 10) {
                return phone;
            }

            System.out.println("Phone number must contain at least 10 digits.");
        }
    }

    // Makes sure the user enters M or F.
    public static String readGender() {

        while (true) {

            String gender = readRequired("Gender (M/F): ");

            if (gender.equalsIgnoreCase("M")
                    || gender.equalsIgnoreCase("F")) {

                return gender.toUpperCase();
            }

            System.out.println("Please enter M or F.");
        }
    }

    // Makes sure the user does not enter an empty value.
    public static String readRequired(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (input.length() > 0) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    // Reads an integer from the user.
    public static int readInt(String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Allows the user to edit information for an existing member.
     */
    public static void editMember() {

        int number = readInt("Enter membership #: ");

        Member member = system.findMember(number);

        if (member == null) {
            System.out.println("Member was not found.");
            return;
        }

        boolean editing = true;

        while (editing) {

            System.out.println();
            System.out.println("----------- EDIT MEMBER -----------");
            System.out.println("Member: " + member.getFullName());
            System.out.println("1. First name");
            System.out.println("2. Last name");
            System.out.println("3. Address");
            System.out.println("4. City");
            System.out.println("5. Postal code");
            System.out.println("6. Phone number");
            System.out.println("7. Gender");
            System.out.println("8. Done");

            int choice = readInt("Choose what to edit: ");

            switch (choice) {

                case 1:
                    member.setFirstName(readName("Enter new first name: "));
                    System.out.println("First name updated.");
                    break;

                case 2:
                    member.setLastName(readName("Enter new last name: "));
                    System.out.println("Last name updated.");
                    break;

                case 3:
                    member.setAddress(readAddress());
                    System.out.println("Address updated.");
                    break;

                case 4:
                    member.setCity(readCity());
                    System.out.println("City updated.");
                    break;

                case 5:
                    member.setPostalCode(readPostalCode());
                    System.out.println("Postal code updated.");
                    break;

                case 6:
                    member.setPhoneNumber(readPhone());
                    System.out.println("Phone number updated.");
                    break;

                case 7:
                    member.setGender(readGender());
                    System.out.println("Gender updated.");
                    break;

                case 8:
                    editing = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please choose 1-9.");
            }
        }

        // Save the updated member information.
        system.saveToFile(fileName);

        System.out.println("Member information saved.");
    }

    /**
     * Validates whether a birthday falls within the club's
     * allowed birth year, month, and day ranges.
     *
     * @param year the birth year
     * @param month the birth month
     * @param day the birthday
     * @return true if the birthday is valid; false otherwise
     */
    public static boolean validBirthday(int year, int month, int day) {

        return year >= 2007
                && year <= 2014
                && month >= 1
                && month <= 12
                && day >= 1
                && day <= 31;
    }

    /**
     * Deletes a member from the registration system.
     */
    public static void deleteMember() {

        int number = readInt("Enter membership #: ");

        Member member = system.findMember(number);

        if (member == null) {
            System.out.println("Member was not found.");
            return;
        }

        System.out.println();
        System.out.println("Member found: " + member.getFullName());

        String answer = readRequired(
                "Are you sure you want to delete this member? (yes/no): "
        );

        if (answer.equalsIgnoreCase("yes")) {

            if (system.deleteMember(number)) {

                system.saveToFile(fileName);

                System.out.println("Member successfully deleted.");
            }

        } else {

            System.out.println("Member was not deleted.");
        }
    }
}