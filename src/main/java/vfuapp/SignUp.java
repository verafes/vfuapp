package vfuapp;

import vfuapp.database.DBUtils;

import java.util.List;
import java.util.Scanner;

import static vfuapp.Admin.admins;


public class SignUp implements IExit {
    private void printWelcomeMessage() {
        System.out.println("        Welcome to VFUniversity!");
        System.out.println();
    }

    private void signUp() {
        admins = DBUtils.getTableAdminData();

        if (admins.size() == 0) {
            Admin admin = new Admin("Ivan", "Sidorov");
            Admin.addAdmin(admin);
        }

        // print admins: is accessible for Admins only
        admins.get(0)
                .printAdminsList();

        printQForExit();

        Scanner in = new Scanner(System.in);

        System.out.print("Enter username: ");
        String input = in.nextLine();
        exitIfQ(input);
        String username = input;

        System.out.print("Enter password: ");
        input = in.nextLine();
        exitIfQ(input);
        String password = input;

        checkCredentials(username, password);
    }

    private void checkCredentials(String username, String password){

        for (Admin admin : admins) {
            if (admin.getUserName().equals(username)
                    && admin.getPassword().equals(password)
                    && admin.getId().startsWith("A")) {
                System.out.println("Welcome, " + admin.getFirstName() + " " + admin.getLastName() + "!");
                admin.runAdmin();
            }
            // To DO: check Credentials for Students and Professors
            else {
                ifUnauthorizedUser();
                return;
            }
        }
    }

    public void runVFUApp(){
        printWelcomeMessage();
        signUp();
    }
}
