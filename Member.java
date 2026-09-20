package myvc;

import java.util.ArrayList;

/**
 * Represents a member of the Montreal Youth Volleyball Club (MYVC).
 * Stores the member's personal information, membership number,
 * and registered tournament periods.
 */
public class Member {

    // Member information.
    private String firstName;
    private String lastName;
    private int year;
    private int month;
    private int day;
    private String gender;
    private String address;
    private String city;
    private String postalCode;
    private String phoneNumber;

    // Unique membership number.
    int membershipNumber;

    // Stores tournament periods.
    ArrayList<Integer> periods;

    /**
     * Constructs a Member object with the specified personal information
     * and unique membership number.
     *
     * @param firstName the member's first name
     * @param lastName the member's last name
     * @param year the member's birth year
     * @param month the member's birth month
     * @param day the member's birth day
     * @param gender the member's gender
     * @param address the member's street address
     * @param city the member's city
     * @param postalCode the member's postal code
     * @param phoneNumber the member's phone number
     * @param membershipNumber the member's unique membership number
     */
    public Member(String firstName, String lastName, int year, int month, int day, String gender, String address, String city, String postalCode, String phoneNumber, int membershipNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
        this.month = month;
        this.day = day;
        this.gender = gender;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.membershipNumber = membershipNumber;

        periods = new ArrayList<Integer>();
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getMembershipNumber() {
        return membershipNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setYear(int year) {
        this.year = year;
    }


    public void setMonth(int month) {
        this.month = month;
    }


    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Registers the member for a tournament period.
     * Only periods 1, 2, and 3 are valid, and a member cannot
     * register for the same period more than once.
     *
     * @param period the tournament period to register for
     * @return true if the member was successfully registered;
     *         false if the period is invalid or already registered
     */
    public boolean registerForPeriod(int period) {

        // Only periods 1, 2 and 3 are available.
        if (period < 1 || period > 3) {
            return false;
        }

        // Prevent duplicate registrations.
        if (periods.contains(period)) {
            return false;
        }

        periods.add(period);

        return true;
    }

    /**
     * Returns a formatted list of the member's registered tournament periods.
     *
     * @return the registered tournament periods, or "None" if the member
     *         is not registered for any periods
     */
    public String getRegisteredPeriods() {

        if (periods.size() == 0) {
            return "None";
        }

        String result = "";

        for (int i = 0; i < periods.size(); i++) {

            if (i > 0) {
                result += ", ";
            }

            result += "Period " + periods.get(i);
        }

        return result;
    }

    /**
     * Adds a tournament period to the member's registrations when
     * loading member information from a file.
     *
     * @param period the tournament period to load
     */
    public void loadPeriod(int period) {

        if (period >= 1 && period <= 3 && !periods.contains(period)) {

            periods.add(period);
        }
    }

    /**
     * Creates a comma-separated string containing the member's
     * registered tournament periods for file storage.
     *
     * @return a comma-separated list of registered periods
     */
    public String getPeriodsForFile() {

        String result = "";

        for (int i = 0; i < periods.size(); i++) {

            if (i > 0) {
                result += ",";
            }

            result += periods.get(i);
        }

        return result;
    }

    /**
     * Checks whether the member is registered for a specific tournament period.
     *
     * @param period the tournament period to check
     * @return true if the member is registered for the period
     */
    public boolean isRegisteredForPeriod(int period) {
        return periods.contains(period);
    }


}
