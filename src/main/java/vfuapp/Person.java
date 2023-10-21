package vfuapp;

import vfuapp.database.DBUtils;
import vfuapp.database.Table;
import vfuapp.database.TableName;

import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    private int tblPersonId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    public List<Admin> users = new ArrayList<>();

    public Person(String firstName, String lastName) {
        int lastTblPersonId = DBUtils.getLastId(Table.NAME.TBL_PERSON);
        this.tblPersonId = lastTblPersonId + 1;
        int userNameId = tblPersonId + 1097523;
        this.firstName = capitalizeString(firstName);
        this.lastName = capitalizeString(lastName);
        this.userName = generateUsername(userNameId);
        this.password = generatePassword(userNameId);
    }

    public Person(int tblPersonId, String firstName, String lastName, String userName, String password) {
        this.tblPersonId = tblPersonId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    };

    public Person() {};

    private String capitalizeString(String string) {
        return string.trim().toUpperCase().charAt(0) + string.substring(1).toLowerCase();
    }

    private String generateUsername(int userNameId) {
        return getFirstName().trim().charAt(0) + lastName.trim() + userNameId;
    }

    private String generatePassword(int userNameId) {
        String fLetter = String.valueOf(getFirstName().trim().toLowerCase().charAt(0));
        String sLetter = String.valueOf(getLastName().toUpperCase().trim().charAt(0));
        return  fLetter + sLetter + (userNameId / 9);
    }

    public int getTblPersonId() {
        return tblPersonId;
    }

    public void setTblPersonId(int tblPersonId) {
        this.tblPersonId = tblPersonId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract char getRole();

//    public T getTableData(int id) {
//        users = DBUtils.getTablePersonData();
//        for(Admin user : users) {
//            if (user.getTblPersonId() == id) {
//                return (T)user;
//            }
//        }
//        return null;
//    };

    public int getId() {
        return getTblPersonId();
    }

    @Override
    public String toString() {
        return "Person {" +
                "tblPersonId = " + getTblPersonId() + ",\n" +
                "firstName = '" + getFirstName() + "'\n" +
                "lastName = '" + getLastName() + "'\n" +
                "username = '" + getUserName() + "'\n" +
                "password = '" + getPassword() + "'\n" +
                "}\n";
    }
}
