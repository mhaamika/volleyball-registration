# MYVC Registration System

A Java-based youth volleyball club registration system designed to manage member information, tournament registrations, and club statistics through both a **JavaFX graphical user interface** and a command-line interface.

The project is a complete application with a graphical interface, persistent data storage, member search and management functionality, input validation, and object-oriented design.

## Features

* **Member Registration**

    * Register new club members with personal, contact, and guardian information
    * Automatically assign unique membership numbers
    * Enforce a maximum capacity of 20 members
    * Validate names, birthdays, gender, postal codes, and contact information

* **Member Management**

    * View all registered members
    * Search members by:

        * Membership number
        * First name
        * Last name
        * Gender
        * Tournament period
    * Edit existing member information
    * Delete members using their membership number

* **Tournament Registration**

    * Register members for available tournament periods
    * Prevent duplicate tournament registrations
    * Display tournament dates directly in the JavaFX interface

* **Club Statistics**

    * Display total membership
    * Count male and female members
    * Identify the oldest and youngest members

* **Persistent Data Storage**

    * Save member information to a text file
    * Load previously saved information when the application starts
    * Preserve membership numbers and tournament registrations between sessions

* **Dual Interfaces**

    * Command-line interface for core system interaction
    * JavaFX graphical interface for a more accessible user experience

## Technical Skills Demonstrated

### Programming & Object-Oriented Design

* Java
* Object-oriented programming
* Classes and objects
* Encapsulation
* Inheritance and polymorphism
* Constructors
* Accessors and mutators
* ArrayLists
* Method decomposition
* Exception handling

### Algorithms & Data Structures

* ArrayList-based data management
* Linear search
* Multi-criteria filtering
* Bubble sort for alphabetical member organization
* Iterative algorithms
* Data validation and conditional logic

### GUI Development

* JavaFX
* `Application`
* `Stage`
* `Scene`
* `VBox`
* `GridPane`
* `Label`
* `TextField`
* `Button`
* `ComboBox`
* Event-driven programming
* Form validation
* Multi-window application design

### File Handling

* File I/O
* `Scanner`
* `PrintWriter`
* Structured text-file storage
* Data serialization using delimited records
* Loading and reconstructing objects from persistent data

## Screenshots

1. Main interface

<img width="1440" height="811" alt="Screenshot 2026-09-20 at 11 35 51 AM" src="https://github.com/user-attachments/assets/075d53c6-3e6f-4e79-a425-3fc6d2af83ca" />

2. View members Function

<img width="1440" height="900" alt="Screenshot 2026-09-20 at 11 44 53 AM" src="https://github.com/user-attachments/assets/f7a22aef-58aa-4aee-93ed-9ac721219ccb" />

3. Search Members

<img width="1257" height="697" alt="Screenshot 2026-09-20 at 11 49 13 AM" src="https://github.com/user-attachments/assets/b953bb11-468b-4b24-b9f1-d16d20c7ce23" />

4. Club Statistics

<img width="1298" height="659" alt="Screenshot 2026-09-20 at 11 52 19 AM" src="https://github.com/user-attachments/assets/1f6e74c0-605e-4422-be8f-5e29902d89bc" />


## Project Architecture

The application separates responsibilities across multiple classes rather than placing all functionality into a single program.

```text
MYVC Registration System
│
├── Runner.java
│   └── JavaFX graphical interface
│
├── Main.java
│   └── Command-line interface
│
├── RegistrationSystem.java
│   └── Member management and system operations
│
├── Member.java
│   └── Member data and tournament registrations
│
├── Family.java
│   └── Guardian/family information
│
└── members.txt
    └── Persistent member data
```

This structure separates the **user interface**, **system logic**, and **data models**, making the application easier to maintain and extend.


## Data Persistence

Member records are stored in a structured text format using the `|` character as a delimiter.

Example:

```text
1001|John|Smith|2010|6|15|M|123 Main St|Philadelphia|19104|5551234567|Parent|1,3
```

When the application starts, the file is read and the stored information is used to reconstruct `Member` and `Family` objects.

Tournament registrations are also restored so that application state persists between sessions.

## Input Validation

The application validates user input before modifying system data.

Examples include:

* Required fields cannot be empty
* Names must contain a minimum number of characters
* Birth years must fall within the club's allowed range
* Birth months and days must be within valid ranges
* Gender must be `M` or `F`
* Postal codes must contain six characters
* Phone numbers must contain a minimum number of digits
* Membership numbers must be numeric
* Tournament periods must be within the available range
* Duplicate tournament registrations are prevented

Invalid input is handled without terminating the application.

## Design Considerations

### Encapsulation

Member and family information is stored inside dedicated classes rather than being managed entirely through the user interface.

This allows the application to separate data representation from how that data is displayed or entered.

### Separation of Responsibilities

The `RegistrationSystem` class manages core registration operations, while `Runner` handles JavaFX presentation and user interaction.

This makes it possible to change the user interface without rewriting the underlying member-management functionality.

### Reusable Methods

Common operations such as searching, displaying member information, validating birthdays, and registering tournament periods are implemented as reusable methods.

This reduces duplicated logic and makes future modifications easier.

## Technologies

| Technology    | Purpose                                |
| ------------- | -------------------------------------- |
| Java          | Core application development           |
| JavaFX        | Graphical user interface               |
| ArrayList     | Member and family data management      |
| File I/O      | Persistent data storage                |
| Git/GitHub    | Version control and project management |
| IntelliJ IDEA | Development environment                |

## Future Improvements

Potential future enhancements could include:

* Database integration using SQL
* More advanced sorting and searching algorithms
* Improved GUI styling and navigation
* Additional data validation
* Automated testing
* Role-based access for administrators


