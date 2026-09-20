package myvc;

import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Manages member registration for the Montreal Youth Volleyball Club (MYVC).
 * Stores member and family information, handles membership operations,
 * and provides functionality for sorting, statistics, and file storage.
 */
public class RegistrationSystem {

    // Stores all registered members.
    private ArrayList<Member> members;

    // Stores the family information for each member.
    private ArrayList<Family> families;

    // Membership numbers start at 1001.
    private int nextMembershipNumber;

    /**
     * Constructs an empty registration system and initializes
     * the membership number sequence.
     */
    public RegistrationSystem() {

        members = new ArrayList<Member>();
        families = new ArrayList<Family>();

        nextMembershipNumber = 1001;
    }

    /**
     * Adds a member and their family information to the system.
     *
     * @param member the member to add
     * @param family the member's family information
     * @return true if the member was added successfully;
     * false if the system already contains 20 members
     */
    public boolean addMember(Member member, Family family) {
        if (members.size() >= 20) {
            return false;
        }

        members.add(member);
        families.add(family);

        return true;
    }

    /**
     * Creates a new member, assigns a unique membership number,
     * and adds the member and family information to the system.
     *
     * @param firstName   the member's first name
     * @param lastName    the member's last name
     * @param year        the member's birth year
     * @param month       the member's birth month
     * @param day         the member's birth day
     * @param gender      the member's gender
     * @param address     the member's street address
     * @param city        the member's city
     * @param postalCode  the member's postal code
     * @param phoneNumber the member's phone number
     * @param family      the member's family information
     * @return the newly created Member, or null if the system is full
     */
    public Member createMember(String firstName, String lastName, int year, int month, int day, String gender, String address, String city, String postalCode, String phoneNumber, Family family) {
        Member member = new Member(firstName, lastName, year, month, day, gender, address, city, postalCode, phoneNumber, nextMembershipNumber);

        if (addMember(member, family)) {
            nextMembershipNumber++;
            return member;
        }

        return null;
    }

    /**
     * Searches for a member using their unique membership number.
     *
     * @param membershipNumber the membership number to search for
     * @return the matching Member, or null if no member is found
     */
    public Member findMember(int membershipNumber) {

        for (int i = 0; i < members.size(); i++) {

            if (members.get(i).getMembershipNumber()
                    == membershipNumber) {
                return members.get(i);
            }
        }

        return null;
    }

    /**
     * Finds all members with the specified first name.
     *
     * @param firstName the first name to search for
     * @return a list of members matching the first name
     */
    public ArrayList<Member> findMembersByFirstName(String firstName) {
        ArrayList<Member> results = new ArrayList<Member>();

        for (int i = 0; i < members.size(); i++) {

            Member member = members.get(i);

            if (member.getFirstName().equalsIgnoreCase(firstName)) {
                results.add(member);
            }
        }

        return results;
    }

    /**
     * Finds all members with the specified last name.
     *
     * @param lastName the first name to search for
     * @return a list of members matching the last name
     */
    public ArrayList<Member> findMembersByLastName(String lastName) {
        ArrayList<Member> results = new ArrayList<Member>();

        for (int i = 0; i < members.size(); i++) {

            Member member = members.get(i);

            if (member.getLastName().equalsIgnoreCase(lastName)) {
                results.add(member);
            }
        }

        return results;
    }

    /**
     * Finds all members with the specified gender.
     *
     * @param gender the gender to search for
     * @return a list of members matching the gender
     */
    public ArrayList<Member> findMembersByGender(String gender) {
        ArrayList<Member> results = new ArrayList<Member>();

        for (int i = 0; i < members.size(); i++) {

            Member member = members.get(i);

            if (member.getGender().equalsIgnoreCase(gender)) {
                results.add(member);
            }
        }

        return results;
    }

    /**
     * Finds all members registered for a specified tournament period.
     *
     * @param period the tournament period to search for
     * @return a list of members registered for the specified period
     */
    public ArrayList<Member> findMembersByPeriod(int period) {

        ArrayList<Member> results = new ArrayList<Member>();

        for (int i = 0; i < members.size(); i++) {

            Member member = members.get(i);

            if (member.isRegisteredForPeriod(period)) {
                results.add(member);
            }
        }

        return results;
    }

    /**
     * Sorts all registered members alphabetically by full name
     * using the bubble sort algorithm. Family information is
     * swapped along with each corresponding member.
     */
    public void sortAlphabetically() {

        for (int i = 0; i < members.size() - 1; i++) {

            for (int j = 0; j < members.size() - i - 1; j++) {

                String firstName = members.get(j).getFullName();
                String secondName = members.get(j + 1).getFullName();

                if (firstName.compareToIgnoreCase(secondName) > 0) {

                    // Swap the members.
                    Member temp = members.get(j);

                    members.set(j, members.get(j + 1));
                    members.set(j + 1, temp);

                    // Keep the family information matched.
                    Family familyTemp = families.get(j);

                    families.set(j, families.get(j + 1));
                    families.set(j + 1, familyTemp);
                }
            }
        }
    }

    /**
     * Finds the oldest registered member by comparing birth dates.
     *
     * @return the oldest Member, or null if there are no registered members
     */
    public Member getOldestMember() {

        if (members.size() == 0) {
            return null;
        }

        Member oldest = members.get(0);

        for (int i = 1; i < members.size(); i++) {

            Member current = members.get(i);

            if (isOlder(current, oldest)) {
                oldest = current;
            }
        }

        return oldest;
    }

    /**
     * Finds the youngest registered member by comparing birth dates.
     *
     * @return the youngest Member, or null if there are no registered members
     */
    public Member getYoungestMember() {

        if (members.size() == 0) {
            return null;
        }

        Member youngest = members.get(0);

        for (int i = 1; i < members.size(); i++) {

            Member current = members.get(i);

            if (isYounger(current, youngest)) {
                youngest = current;
            }
        }

        return youngest;
    }

    /**
     * Determines whether the first member was born before the second member.
     *
     * @param first  the first member to compare
     * @param second the second member to compare
     * @return true if the first member is older than the second member
     */
    public boolean isOlder(Member first, Member second) {

        if (first.getYear() != second.getYear()) {
            return first.getYear() < second.getYear();
        }

        if (first.getMonth() != second.getMonth()) {
            return first.getMonth() < second.getMonth();
        }

        return first.getDay() < second.getDay();
    }

    /**
     * Determines whether the first member was born after the second member.
     *
     * @param first  the first member to compare
     * @param second the second member to compare
     * @return true if the first member is younger than the second member
     */
    public boolean isYounger(Member first, Member second) {

        if (first.getYear() != second.getYear()) {
            return first.getYear() > second.getYear();
        }

        if (first.getMonth() != second.getMonth()) {
            return first.getMonth() > second.getMonth();
        }

        return first.getDay() > second.getDay();
    }

    /**
     * Returns the total number of registered members.
     *
     * @return the number of registered members
     */
    public int getMemberCount() {
        return members.size();
    }

    /**
     * Counts the number of male members registered in the system.
     *
     * @return the number of male members
     */
    public int getBoysCount() {

        int count = 0;

        for (int i = 0; i < members.size(); i++) {

            if (members.get(i).getGender().equalsIgnoreCase("M")) {
                count++;
            }
        }

        return count;
    }

    /**
     * Counts the number of female members registered in the system.
     *
     * @return the number of female members
     */
    public int getGirlsCount() {

        int count = 0;

        for (int i = 0; i < members.size(); i++) {

            if (members.get(i).getGender().equalsIgnoreCase("F")) {
                count++;
            }
        }

        return count;
    }

    /**
     * Provides access to the list of registered members.
     *
     * @return the list of registered members
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Saves all registered member, family, and tournament information
     * to a text file. Each member is stored on a separate line.
     *
     * @param fileName the name or path of the file to save to
     */
    public void saveToFile(String fileName) {

        try {

            PrintWriter output = new PrintWriter(fileName);

            // Save every member on a separate line.
            for (int i = 0; i < members.size(); i++) {

                Member member = members.get(i);
                Family family = families.get(i);

                /*
                 * Each | separates a different piece of information.
                 */
                output.println(member.getMembershipNumber() + "|" +
                        member.getFirstName() + "|" +
                        member.getLastName() + "|" +
                        member.getYear() + "|" +
                        member.getMonth() + "|" +
                        member.getDay() + "|" +
                        member.getGender() + "|" +
                        member.getAddress() + "|" +
                        member.getCity() + "|" +
                        member.getPostalCode() + "|" +
                        member.getPhoneNumber() + "|" +
                        family.getRelation() + "|" +
                        member.getPeriodsForFile()
                );
            }

            output.close();

        } catch (Exception e) {

            System.out.println("Error saving member information.");
        }
    }

    /**
     * Loads member, family, and tournament information from a text file.
     * Existing member data is preserved while the loaded information
     * is added to the system.
     *
     * @param fileName the name or path of the file to load
     */
    public void loadFromFile(String fileName) {

        File file = new File(fileName);

        // If the file doesn't exist, there is nothing to load.
        if (!file.exists()) {
            return;
        }

        try {

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                if (line.length() == 0) {
                    continue;
                }

                /*
                 * Split the saved line into separate pieces.
                 */
                String[] data = line.split("\\|");

                if (data.length < 13) {
                    continue;
                }

                int membershipNumber = Integer.parseInt(data[0]);

                String firstName = data[1];
                String lastName = data[2];

                int year = Integer.parseInt(data[3]);

                int month = Integer.parseInt(data[4]);

                int day = Integer.parseInt(data[5]);

                String gender = data[6];
                String address = data[7];
                String city = data[8];
                String postalCode = data[9];
                String phoneNumber = data[10];
                String relation = data[11];

                // Recreate the Member object.
                Member member = new Member(firstName, lastName, year, month,
                        day, gender, address, city, postalCode, phoneNumber,
                        membershipNumber);

                // Restore tournament registrations.
                if (data[12].length() > 0) {

                    String[] savedPeriods = data[12].split(",");

                    for (int i = 0; i < savedPeriods.length; i++) {

                        int period = Integer.parseInt(savedPeriods[i]);

                        member.loadPeriod(period);
                    }
                }

                // Recreate the Family object.
                Family family = new Family(firstName, lastName, relation, address, city, postalCode, phoneNumber);

                // Add the loaded objects to the system.
                members.add(member);
                families.add(family);

                // Make sure future membership numbers are unique.
                if (membershipNumber >= nextMembershipNumber) {

                    nextMembershipNumber = membershipNumber + 1;
                }
            }

            fileScanner.close();

        } catch (Exception e) {

            System.out.println("Error loading member information.");
        }
    }

    public boolean deleteMember(int membershipNumber) {

        for (int i = 0; i < members.size(); i++) {

            if (members.get(i).getMembershipNumber() == membershipNumber) {

                members.remove(i);
                families.remove(i);

                return true;
            }
        }

        return false;
    }
}