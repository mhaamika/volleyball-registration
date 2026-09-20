package myvc;

/**
 * Represents a family member associated with an MYVC member.
 * Stores personal and contact information.
 */

public class Family {

    private String firstName;
    private String lastName;
    private String relation;
    private String address;
    private String city;
    private String postalCode;
    private String phoneNumber;

    public Family(String firstName, String lastName, String relation, String address, String city, String postalCode, String phoneNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.relation = relation;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getRelation() {
        return relation;
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
}
