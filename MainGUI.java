package myvc;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Provides the graphical user interface for the MYVC registration system.
 *
 * <p>This class allows users to register, view, search, edit, and delete
 * members. It also provides tournament registration and displays club
 * statistics.</p>
 */
public class MainGUI extends Application {

    // Manages all members in the registration system.
    private RegistrationSystem system = new RegistrationSystem();

    // File used to save and load member information.
    private String fileName = "members.txt";


    /**
     * Starts and displays the main MYVC graphical user interface.
     *
     * <p>Previously saved member information is loaded when the application
     * starts. The main menu provides access to all available functions.</p>
     *
     * @param stage the main application window
     */
    @Override
    public void start(Stage stage) {

        // Load previously saved members.
        system.loadFromFile(fileName);

        // Create main menu buttons.
        Button registerButton = new Button("Register Member");
        Button viewButton = new Button("View Members");
        Button searchButton = new Button("Search Members");
        Button editButton = new Button("Edit Member");
        Button deleteButton = new Button("Delete Member");
        Button tournamentButton = new Button("Tournament Registration");
        Button statisticsButton = new Button("Statistics");

        // Connect each button to its corresponding method.
        registerButton.setOnAction(event -> showRegistrationForm());
        viewButton.setOnAction(event -> displayMembers());
        searchButton.setOnAction(event -> showSearchForm());
        editButton.setOnAction(event -> showEditForm());
        deleteButton.setOnAction(event -> showDeleteForm());
        tournamentButton.setOnAction(event -> showTournamentForm());
        statisticsButton.setOnAction(event -> showStatistics());

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                registerButton,
                viewButton,
                searchButton,
                editButton,
                deleteButton,
                tournamentButton,
                statisticsButton
        );

        Scene scene = new Scene(layout, 400, 400);

        stage.setTitle("MYVC Registration System");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Displays the form used to register a new member.
     *
     * <p>The form collects the member's personal, contact, and
     * family information. The entered information is validated
     * before the member is added to the registration system.</p>
     */
    private void showRegistrationForm() {

        Stage formStage = new Stage();

        GridPane form = new GridPane();

        form.setPadding(new Insets(15));
        form.setHgap(10);
        form.setVgap(10);

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField yearField = new TextField();
        TextField monthField = new TextField();
        TextField dayField = new TextField();
        TextField addressField = new TextField();
        TextField cityField = new TextField();
        TextField postalCodeField = new TextField();
        TextField phoneField = new TextField();
        TextField relationField = new TextField();

        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("M", "F");

        Button registerButton = new Button("Register");

        Label messageLabel = new Label();

        form.add(new Label("First Name:"), 0, 0);
        form.add(firstNameField, 1, 0);

        form.add(new Label("Last Name:"), 0, 1);
        form.add(lastNameField, 1, 1);

        form.add(new Label("Birth Year:"), 0, 2);
        form.add(yearField, 1, 2);

        form.add(new Label("Birth Month:"), 0, 3);
        form.add(monthField, 1, 3);

        form.add(new Label("Birth Day:"), 0, 4);
        form.add(dayField, 1, 4);

        form.add(new Label("Gender:"), 0, 5);
        form.add(genderBox, 1, 5);

        form.add(new Label("Address:"), 0, 6);
        form.add(addressField, 1, 6);

        form.add(new Label("City:"), 0, 7);
        form.add(cityField, 1, 7);

        form.add(new Label("Postal Code:"), 0, 8);
        form.add(postalCodeField, 1, 8);

        form.add(new Label("Phone:"), 0, 9);
        form.add(phoneField, 1, 9);

        form.add(new Label("Parent/Guardian Relation:"), 0, 10);
        form.add(relationField, 1, 10);

        form.add(registerButton, 1, 11);
        form.add(messageLabel, 1, 12);

        registerButton.setOnAction(event -> {

            try {

                if (system.getMemberCount() >= 20) {
                    messageLabel.setText(
                            "Maximum of 20 members reached."
                    );
                    return;
                }

                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();

                int year = Integer.parseInt(yearField.getText().trim());
                int month = Integer.parseInt(monthField.getText().trim());
                int day = Integer.parseInt(dayField.getText().trim());

                String gender = genderBox.getValue();

                String address = addressField.getText().trim();
                String city = cityField.getText().trim();
                String postalCode = postalCodeField.getText().trim();
                String phone = phoneField.getText().trim();
                String relation = relationField.getText().trim();

                if (firstName.length() < 2 ||
                        lastName.length() < 2 ||
                        gender == null ||
                        address.length() < 2 ||
                        city.length() < 2 ||
                        postalCode.length() != 6 ||
                        phone.length() == 0 ||
                        relation.length() == 0) {

                    messageLabel.setText(
                            "Please complete all fields."
                    );
                    return;
                }

                if (!validBirthday(year, month, day)) {
                    messageLabel.setText("Invalid birthday.");
                    return;
                }

                Family family = new Family(
                        firstName,
                        lastName,
                        relation,
                        address,
                        city,
                        postalCode,
                        phone
                );

                Member member = system.createMember(
                        firstName,
                        lastName,
                        year,
                        month,
                        day,
                        gender,
                        address,
                        city,
                        postalCode,
                        phone,
                        family
                );

                if (member == null) {

                    messageLabel.setText(
                            "Unable to register member."
                    );

                } else {

                    system.saveToFile(fileName);

                    messageLabel.setText(
                            "Member registered! Membership #: "
                                    + member.getMembershipNumber()
                    );

                    firstNameField.clear();
                    lastNameField.clear();
                    yearField.clear();
                    monthField.clear();
                    dayField.clear();
                    genderBox.setValue(null);
                    addressField.clear();
                    cityField.clear();
                    postalCodeField.clear();
                    phoneField.clear();
                    relationField.clear();
                }

            } catch (NumberFormatException e) {

                messageLabel.setText(
                        "Birth date must contain numbers."
                );
            }
        });

        Scene scene = new Scene(form, 550, 550);

        formStage.setTitle("Register Member");
        formStage.setScene(scene);
        formStage.show();
    }


    /**
     * Displays all members currently registered in the system.
     *
     * <p>If no members are registered, an appropriate message is displayed.
     * Otherwise, the information for each member is displayed.</p>
     */
    private void displayMembers() {

        Stage memberStage = new Stage();

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(15));

        ArrayList<Member> members = system.getMembers();

        if (members.size() == 0) {

            layout.getChildren().add(
                    new Label("No members have been registered.")
            );

        } else {

            for (Member member : members) {

                Label memberLabel = new Label(
                        "Membership #: "
                                + member.getMembershipNumber()
                                + "\nName: "
                                + member.getFullName()
                                + "\nBirthday: "
                                + member.getMonth()
                                + "/"
                                + member.getDay()
                                + "/"
                                + member.getYear()
                                + "\nGender: "
                                + member.getGender()
                                + "\nAddress: "
                                + member.getAddress()
                                + "\nCity: "
                                + member.getCity()
                                + "\nPostal Code: "
                                + member.getPostalCode()
                                + "\nPhone: "
                                + member.getPhoneNumber()
                                + "\nTournament Periods: "
                                + member.getRegisteredPeriods()
                                + "\n-----------------------------"
                );

                layout.getChildren().add(memberLabel);
            }
        }

        Scene scene = new Scene(layout, 500, 600);

        memberStage.setTitle("All Members");
        memberStage.setScene(scene);
        memberStage.show();
    }


    /**
     * Displays the member search form.
     *
     * <p>Members can be searched by membership number, first name,
     * last name, gender, or tournament period.</p>
     */
    private void showSearchForm() {

        Stage searchStage = new Stage();

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(15));

        ComboBox<String> searchType = new ComboBox<>();

        searchType.getItems().addAll(
                "Membership Number",
                "First Name",
                "Last Name",
                "Gender",
                "Tournament Period"
        );

        searchType.setValue("First Name");

        TextField searchField = new TextField();

        searchField.setPromptText("Enter search value");

        Button searchButton = new Button("Search");

        VBox resultsBox = new VBox(10);

        searchButton.setOnAction(event -> {

            resultsBox.getChildren().clear();

            String type = searchType.getValue();
            String value = searchField.getText().trim();

            if (value.length() == 0) {

                resultsBox.getChildren().add(
                        new Label("Please enter a search value.")
                );

                return;
            }

            if (type.equals("Membership Number")) {

                try {

                    int number = Integer.parseInt(value);

                    Member member = system.findMember(number);

                    if (member == null) {

                        resultsBox.getChildren().add(
                                new Label("Member was not found.")
                        );

                    } else {

                        displaySearchMember(member, resultsBox);
                    }

                } catch (NumberFormatException e) {

                    resultsBox.getChildren().add(
                            new Label("Membership number must be a number.")
                    );
                }

            } else if (type.equals("First Name")) {

                ArrayList<Member> results =
                        system.findMembersByFirstName(value);

                for (Member member : results) {
                    displaySearchMember(member, resultsBox);
                }

            } else if (type.equals("Last Name")) {

                ArrayList<Member> results =
                        system.findMembersByLastName(value);

                for (Member member : results) {
                    displaySearchMember(member, resultsBox);
                }

            } else if (type.equals("Gender")) {

                if (!value.equalsIgnoreCase("M")
                        && !value.equalsIgnoreCase("F")) {

                    resultsBox.getChildren().add(
                            new Label("Enter M or F.")
                    );

                } else {

                    ArrayList<Member> results =
                            system.findMembersByGender(value);

                    for (Member member : results) {
                        displaySearchMember(member, resultsBox);
                    }
                }

            } else if (type.equals("Tournament Period")) {

                try {

                    int period = Integer.parseInt(value);

                    if (period < 1 || period > 3) {

                        resultsBox.getChildren().add(
                                new Label(
                                        "Period must be 1, 2, or 3."
                                )
                        );

                    } else {

                        ArrayList<Member> results =
                                system.findMembersByPeriod(period);

                        for (Member member : results) {
                            displaySearchMember(member, resultsBox);
                        }
                    }

                } catch (NumberFormatException e) {

                    resultsBox.getChildren().add(
                            new Label("Period must be a number.")
                    );
                }
            }

            if (resultsBox.getChildren().size() == 0) {

                resultsBox.getChildren().add(
                        new Label("No matching members were found.")
                );
            }
        });

        layout.getChildren().addAll(
                new Label("Search by:"),
                searchType,
                searchField,
                searchButton,
                resultsBox
        );

        Scene scene = new Scene(layout, 500, 550);

        searchStage.setTitle("Search Members");
        searchStage.setScene(scene);
        searchStage.show();
    }


    /**
     * Displays one member's information in the search results.
     *
     * @param member the member whose information will be displayed
     * @param resultsBox the container used to display the search result
     */
    private void displaySearchMember(
            Member member,
            VBox resultsBox) {

        Label label = new Label(
                "Membership #: "
                        + member.getMembershipNumber()
                        + "\nName: "
                        + member.getFullName()
                        + "\nGender: "
                        + member.getGender()
                        + "\nPhone: "
                        + member.getPhoneNumber()
                        + "\nTournament Periods: "
                        + member.getRegisteredPeriods()
                        + "\n-----------------------------"
        );

        resultsBox.getChildren().add(label);
    }


    /**
     * Displays the form used to edit an existing member.
     *
     * <p>The member is located using their membership number.
     * The member's information can then be updated and saved.</p>
     */
    private void showEditForm() {

        Stage editStage = new Stage();

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(15));

        TextField membershipField = new TextField();

        membershipField.setPromptText(
                "Enter membership number"
        );

        Button findButton = new Button("Find Member");

        VBox formBox = new VBox(10);

        Label messageLabel = new Label();

        layout.getChildren().addAll(
                new Label("Membership Number:"),
                membershipField,
                findButton,
                messageLabel,
                formBox
        );

        findButton.setOnAction(event -> {

            formBox.getChildren().clear();
            messageLabel.setText("");

            try {

                int membershipNumber =
                        Integer.parseInt(
                                membershipField.getText().trim()
                        );

                Member member =
                        system.findMember(membershipNumber);

                if (member == null) {

                    messageLabel.setText(
                            "Member was not found."
                    );

                    return;
                }

                TextField firstNameField =
                        new TextField(member.getFirstName());

                TextField lastNameField =
                        new TextField(member.getLastName());

                TextField yearField =
                        new TextField(
                                String.valueOf(member.getYear())
                        );

                TextField monthField =
                        new TextField(
                                String.valueOf(member.getMonth())
                        );

                TextField dayField =
                        new TextField(
                                String.valueOf(member.getDay())
                        );

                ComboBox<String> genderBox =
                        new ComboBox<>();

                genderBox.getItems().addAll("M", "F");
                genderBox.setValue(member.getGender());

                TextField addressField =
                        new TextField(member.getAddress());

                TextField cityField =
                        new TextField(member.getCity());

                TextField postalCodeField =
                        new TextField(member.getPostalCode());

                TextField phoneField =
                        new TextField(member.getPhoneNumber());

                Button saveButton =
                        new Button("Save Changes");

                Label saveLabel =
                        new Label();

                GridPane form = new GridPane();

                form.setHgap(10);
                form.setVgap(10);

                form.add(new Label("First Name:"), 0, 0);
                form.add(firstNameField, 1, 0);

                form.add(new Label("Last Name:"), 0, 1);
                form.add(lastNameField, 1, 1);

                form.add(new Label("Birth Year:"), 0, 2);
                form.add(yearField, 1, 2);

                form.add(new Label("Birth Month:"), 0, 3);
                form.add(monthField, 1, 3);

                form.add(new Label("Birth Day:"), 0, 4);
                form.add(dayField, 1, 4);

                form.add(new Label("Gender:"), 0, 5);
                form.add(genderBox, 1, 5);

                form.add(new Label("Address:"), 0, 6);
                form.add(addressField, 1, 6);

                form.add(new Label("City:"), 0, 7);
                form.add(cityField, 1, 7);

                form.add(new Label("Postal Code:"), 0, 8);
                form.add(postalCodeField, 1, 8);

                form.add(new Label("Phone:"), 0, 9);
                form.add(phoneField, 1, 9);

                form.add(saveButton, 1, 10);
                form.add(saveLabel, 1, 11);

                formBox.getChildren().add(form);

                saveButton.setOnAction(saveEvent -> {

                    try {

                        String firstName =
                                firstNameField.getText().trim();

                        String lastName =
                                lastNameField.getText().trim();

                        int year =
                                Integer.parseInt(
                                        yearField.getText().trim()
                                );

                        int month =
                                Integer.parseInt(
                                        monthField.getText().trim()
                                );

                        int day =
                                Integer.parseInt(
                                        dayField.getText().trim()
                                );

                        String gender =
                                genderBox.getValue();

                        String address =
                                addressField.getText().trim();

                        String city =
                                cityField.getText().trim();

                        String postalCode =
                                postalCodeField.getText().trim();

                        String phone =
                                phoneField.getText().trim();

                        if (firstName.length() < 2 ||
                                lastName.length() < 2 ||
                                gender == null ||
                                address.length() < 2 ||
                                city.length() < 2 ||
                                postalCode.length() != 6 ||
                                phone.length() == 0) {

                            saveLabel.setText(
                                    "Please enter valid information."
                            );

                            return;
                        }

                        if (!validBirthday(
                                year,
                                month,
                                day)) {

                            saveLabel.setText(
                                    "Invalid birthday."
                            );

                            return;
                        }

                        member.setFirstName(firstName);
                        member.setLastName(lastName);
                        member.setYear(year);
                        member.setMonth(month);
                        member.setDay(day);
                        member.setGender(gender);
                        member.setAddress(address);
                        member.setCity(city);
                        member.setPostalCode(postalCode);
                        member.setPhoneNumber(phone);

                        system.saveToFile(fileName);

                        saveLabel.setText(
                                "Member updated successfully!"
                        );

                    } catch (NumberFormatException e) {

                        saveLabel.setText(
                                "Birth date must contain numbers."
                        );
                    }
                });

            } catch (NumberFormatException e) {

                messageLabel.setText(
                        "Membership number must be a number."
                );
            }
        });

        Scene scene =
                new Scene(layout, 600, 650);

        editStage.setTitle("Edit Member");
        editStage.setScene(scene);
        editStage.show();
    }


    /**
     * Displays the form used to delete a member.
     *
     * <p>The member is located using their membership number
     * and removed from the registration system.</p>
     */
    private void showDeleteForm() {

        Stage deleteStage = new Stage();

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(15));

        TextField membershipField = new TextField();

        membershipField.setPromptText(
                "Enter membership number"
        );

        Button deleteButton =
                new Button("Delete Member");

        Label messageLabel =
                new Label();

        layout.getChildren().addAll(
                new Label("Membership Number:"),
                membershipField,
                deleteButton,
                messageLabel
        );

        deleteButton.setOnAction(event -> {

            try {

                int membershipNumber =
                        Integer.parseInt(
                                membershipField.getText().trim()
                        );

                Member member =
                        system.findMember(membershipNumber);

                if (member == null) {

                    messageLabel.setText(
                            "Member was not found."
                    );

                    return;
                }

                system.deleteMember(membershipNumber);

                system.saveToFile(fileName);

                messageLabel.setText(
                        "Member deleted successfully."
                );

                membershipField.clear();

            } catch (NumberFormatException e) {

                messageLabel.setText(
                        "Membership number must be a number."
                );
            }
        });

        Scene scene =
                new Scene(layout, 400, 250);

        deleteStage.setTitle("Delete Member");
        deleteStage.setScene(scene);
        deleteStage.show();
    }


    /**
     * Displays the tournament registration form.
     *
     * <p>Allows a member to register for one of the available
     * tournament periods.</p>
     */
    /**
     * Displays the tournament registration form.
     */
    private void showTournamentForm() {

        Stage tournamentStage = new Stage();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        TextField membershipField = new TextField();
        membershipField.setPromptText("Enter membership number");

        // Dropdown displays both the tournament period and its dates.
        ComboBox<String> periodBox = new ComboBox<>();

        periodBox.getItems().add("Period 1: June 2-27");
        periodBox.getItems().add("Period 2: July 7-31");
        periodBox.getItems().add("Period 3: August 4-29");

        periodBox.setPromptText("Select tournament period");

        Button registerButton = new Button("Register for Period");

        Label messageLabel = new Label();

        layout.getChildren().addAll(
                new Label("Membership Number:"),
                membershipField,
                new Label("Tournament Period:"),
                periodBox,
                registerButton,
                messageLabel
        );

        registerButton.setOnAction(event -> {

            try {

                int membershipNumber =
                        Integer.parseInt(
                                membershipField.getText().trim()
                        );

                Member member =
                        system.findMember(membershipNumber);

                if (member == null) {

                    messageLabel.setText(
                            "Member was not found."
                    );

                    return;
                }

                String selectedPeriod = periodBox.getValue();

                if (selectedPeriod == null) {

                    messageLabel.setText(
                            "Please select a tournament period."
                    );

                    return;
                }

                int period;

                if (selectedPeriod.startsWith("Period 1")) {
                    period = 1;
                } else if (selectedPeriod.startsWith("Period 2")) {
                    period = 2;
                } else {
                    period = 3;
                }

                if (member.registerForPeriod(period)) {

                    system.saveToFile(fileName);

                    messageLabel.setText(
                            "Successfully registered for " +
                                    selectedPeriod + "."
                    );

                } else {

                    messageLabel.setText(
                            "Member is already registered for this period."
                    );
                }

            } catch (NumberFormatException e) {

                messageLabel.setText(
                        "Membership number must be a number."
                );
            }
        });

        Scene scene =
                new Scene(layout, 450, 300);

        tournamentStage.setTitle("Tournament Registration");
        tournamentStage.setScene(scene);
        tournamentStage.show();
    }


    /**
     * Displays statistics about the members registered with the club.
     *
     * <p>Displays the total number of members, the number of boys
     * and girls, and the names of the oldest and youngest members.</p>
     */
    private void showStatistics() {

        Stage statisticsStage =
                new Stage();

        VBox layout =
                new VBox(10);

        layout.setPadding(
                new Insets(20)
        );

        int total =
                system.getMemberCount();

        int boys =
                system.getBoysCount();

        int girls =
                system.getGirlsCount();

        Member oldest =
                system.getOldestMember();

        Member youngest =
                system.getYoungestMember();

        layout.getChildren().add(
                new Label("CLUB STATISTICS")
        );

        layout.getChildren().add(
                new Label("Total Members: " + total)
        );

        layout.getChildren().add(
                new Label("Boys: " + boys)
        );

        layout.getChildren().add(
                new Label("Girls: " + girls)
        );

        if (oldest != null) {

            layout.getChildren().add(
                    new Label(
                            "Oldest Member: "
                                    + oldest.getFullName()
                    )
            );
        }

        if (youngest != null) {

            layout.getChildren().add(
                    new Label(
                            "Youngest Member: "
                                    + youngest.getFullName()
                    )
            );
        }

        Scene scene =
                new Scene(layout, 350, 300);

        statisticsStage.setTitle(
                "Club Statistics"
        );

        statisticsStage.setScene(scene);
        statisticsStage.show();
    }


    /**
     * Checks whether a birthday falls within the allowed
     * membership age range.
     *
     * @param year the member's birth year
     * @param month the member's birth month
     * @param day the member's birth day
     * @return true if the birthday is within the allowed range;
     *         false otherwise
     */
    private boolean validBirthday(
            int year,
            int month,
            int day) {

        return year >= 2007
                && year <= 2014
                && month >= 1
                && month <= 12
                && day >= 1
                && day <= 31;
    }


    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {

        launch(args);
    }
}