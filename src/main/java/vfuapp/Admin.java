package vfuapp;

import utils.Utils;
import vfuapp.database.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static vfuapp.Student.students;


public class Admin extends Person implements IPrintAdmin, IExit {
    private String id = "A";
    private static int adminID = 1000001;
    private int tblAdminId;
    private int tblAdminPersonId;
    public static List<Admin> admins = new ArrayList<>();
    private static int idAdmin = 1;

    public Admin(String firstName, String lastName) {
        super(firstName, lastName);
        this.id = id + adminID;
        adminID++;
        this.tblAdminId = idAdmin;
        idAdmin++;
        this.tblAdminPersonId = getTblPersonId();
    }

    public Admin(){};

    public static void addAdmin(Admin admin){
        DBUtils.insertAdmin(admin);
        admins = DBUtils.getTableAdminData();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTblAdminId() {
        return tblAdminId;
    }

    public void setTblAdminId(int tblAdminId) {
        this.tblAdminId = tblAdminId;
    }

    public int getTblAdminPersonId() {
        return tblAdminPersonId;
    }

    public void setTblAdminPersonId(int tblAdminPersonId) {
        this.tblAdminPersonId = tblAdminPersonId;
    }

    /* for debugging */
    @Override
    public String toString() {
        return "Admin {" +
                "tblAdminId = " + getTblAdminId() +
                ", tblPersonAdminId = " + getTblAdminPersonId() +
                ", tblPersonId = " + getTblPersonId() +
                ", firstName = '" + getFirstName() + "'" +
                ", lastName = '" + getLastName() + "'" +
                ", username = '" + getUserName() + "'" +
                ", password = '" + getPassword() + "'" +
                ", id = " + getId() +
                "}";
    }

    public void runAdmin() {
        printQForExit();

        Scanner in = new Scanner(System.in);

        System.out.println("Would you like ");
        System.out.println("1 - Register new user");
        System.out.println("2 - Print existing data (from DB)");
        String input = in.nextLine();

        switch (input) {
            case "Q", "q" -> {
                exitIfQ();
            }
            case "1" -> runRegistration();
            case "2" -> runPrintInformation();
        }
    }

    private void runRegistration() {
        System.out.println("Running Administration");
        printQForExit();

        Scanner in = new Scanner(System.in);

        System.out.println("Would you like ");
        System.out.println("1 - Register new Student");
        System.out.println("2 - Register new Professor");
        System.out.println("3 - Register new Admin");

        String input = in.nextLine();

        switch (input) {
            case "Q", "q" -> {
                exitIfQ();
            }
            case "1" -> runRegisterNewStudent();
            case "2" -> runRegisterNewProfessor();
            case "3" -> runRegisterNewAdmin();
        }
    }

    private void runPrintInformation() {
        System.out.println("Running Print Information");
        printQForExit();
    }

    private void runRegisterNewStudent() {
        System.out.println("Register New Student");
        printQForExit();

        Scanner in = new Scanner(System.in);

        System.out.print("Please enter the student's First name: ");
        String input = in.nextLine();
        exitIfQ(input);
        String firstName = input;

        System.out.print("Please enter the student's Last name: ");
        input = in.nextLine();
        exitIfQ(input);
        String lastName = input;

        Student student = new Student(firstName, lastName);
//        student.addStudent(student);

        System.out.println("Would you like register the student for courses? ");
        System.out.println("1 - yes");
        System.out.println("2 - no");
        System.out.println("Q - to exit");
        input = in.nextLine();

        students = DBUtils.getTableStudentData();

        switch (input) {
            case "Q", "q" -> {
                exitIfQ();

            }
            case "1" -> runCourseRegistration();
            case "2" -> {
                student = new Student(firstName, lastName, new ArrayList<>());
                Student.addStudent(student);
                runRegistration();
            }
        }

    }

    private void runRegisterNewProfessor() {

    }

    private void runRegisterNewAdmin() {

    }

    private void runCourseRegistration() {
        System.out.println("Register the Student for Courses");
        Utils.printExitMessage();

        List<String> courses = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        boolean flag = true;

        do {
            System.out.print("Please enter a course name OR 'Q' to quit: ");
            String input = in.nextLine();
            switch (input) {
                case "Q", "q" -> {
                    Utils.printList(courses);
                    flag = false;
                    runRegistration();
                }
                default -> {
                    courses.add(input);
                    Utils.printList(courses);
                }
            }

        } while (flag);
    }
}
