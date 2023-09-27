package vfuapp;

public abstract class Person {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private static int userNameId = 103;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = String.valueOf(userNameId);
        generateUsername(firstName, lastName);
        userNameId++;
    }

    private void generateUsername(String firstName, String lastName) {
        this.username = firstName.trim().charAt(0) + lastName.trim() + userNameId;
        userNameId++;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
