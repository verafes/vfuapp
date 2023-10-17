package vfuapp;

import utils.Utils;
import vfuapp.database.DBUtils;
import vfuapp.database.TableName;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static vfuapp.Student.students;


public class Admin extends Person implements IPrintAdmin, IExit {
    private int tblAdminId;
    private int tblAdminPersonId;
    private String roleId = "A";
    public static List<Admin> admins = new ArrayList<>();

    public Admin(String firstName, String lastName) {
        super(firstName, lastName);
        int lastTblAdminId = DBUtils.getLastIdFromTable(TableName.ADMIN);
        this.tblAdminId = lastTblAdminId + 1;
        this.tblAdminPersonId = getTblPersonId();
        this.roleId = roleId + 10000 + tblAdminId;
    }

    public Admin(){};

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /* for debugging */
    @Override
    public String toString() {
        return "Admin {" +
                "tblAdminId = " + getTblAdminId() + ",\n" +
                "tblPersonId = " + getTblPersonId() + ",\n" +
                "tblPersonAdminId = " + getTblAdminPersonId() + ",\n" +
                "roleId = " + getRoleId() + ",\n" +
                "firstName = '" + getFirstName() + "',\n" +
                "lastName = '" + getLastName() + "',\n" +
                "username = '" + getUserName() + "',\n" +
                "password = '" + getPassword() + "',\n" +
                "}\n";
    }

    public static void addAdmin(Admin admin){
        DBUtils.insertAdmin(admin);
        admins = DBUtils.getTableAdminData();
    }

    public void runAdmin() {
        printQForExit();

        Scanner in = new Scanner(System.in);

        System.out.println("Would you like ");
        System.out.println("1 - Register new user");
        System.out.println("2 - Add new course)");
        System.out.println("3 - Print existing data (from DB)");
        String input = in.nextLine();

        switch (input) {
            case "Q", "q" -> exitIfQ();
            case "1" -> runRegistration();
            case "2" -> runAddCourse();
            case "3" -> runPrintInformation();
        }
    }

    private void runRegistration() {
        System.out.println("Running Administration");

        Scanner in = new Scanner(System.in);

        printQForExit();
        System.out.println("Would you like ");
        System.out.println("1 - Register new Student");
        System.out.println("2 - Register new Professor");
        System.out.println("3 - Register new Admin");

        String input = in.nextLine();

        switch (input) {
            case "Q", "q" -> runAdmin();
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

        Scanner in = new Scanner(System.in);

        printQForExit();
        System.out.print("Please enter the student's First name: ");
        String input = in.nextLine();
        exitIfQ(input);
        String firstName = input;

        System.out.print("Please enter the student's Last name: ");
        input = in.nextLine();
        exitIfQ(input);
        String lastName = input;

        Student student = new Student(firstName, lastName);
        Student.addStudent(student);

        System.out.println("New Student registered:");
        printLastStudent();

        printQForExit();
        System.out.println("Would you like register the student for courses? ");
        System.out.println("1 - yes");
        System.out.println("2 - no");

        input = in.nextLine();

        students = DBUtils.getTableStudentData();

        switch (input) {
            case "Q", "q", "2" -> runRegistration();
            case "1" -> runCourseRegistration(
                    student.getFirstName(),
                    student.getLastName(),
                    student.getRoleId(),
                    student.getTblStudentAcademicId()
                );
        }
    }

    private void runRegisterNewProfessor() {

    }

    private void runRegisterNewAdmin() {

    }

    private void runCourseRegistration(String firstName, String lastName, String roleId, int academicId) {
        char role = roleId.charAt(0);
        switch (role) {
            case 'S' -> System.out.println("Register student " +  firstName + " " + lastName +  " for Courses.");
            case 'P' -> System.out.println("Assign professor " +  firstName + " " + lastName +  " to Courses.");
        }

        int count = role == 'S' ? 6 : 3;

        List<Integer> coursesIds = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        printCoursesList();
        printQForExit();

        do {
            System.out.print("Please enter a course name: ");
            String input = in.nextLine();
            if(input.equalsIgnoreCase("Q")) {
                Academic.addCourses(academicId, coursesIds);
                printLastStudent();
                runRegistration();
            } else {
                for (Course course : Course.courses) {
                    if (Integer.parseInt(input) == course.getTblCourseId()) {
                        coursesIds.add(course.getTblCourseId());
                    }
                }
            }
            count --;
        } while (count != 0);
    }

    private void runAddCourse() {
        System.out.println("Add New Course");

        Scanner in = new Scanner(System.in);

        printQForExit();
        System.out.print("Enter course name: ");
        String input = in.nextLine();
        exitIfQ(input);
        String courseName = input;

        System.out.print("Enter course price (whole number): ");
        input = in.nextLine();
        exitIfQ(input);
        String coursePrice = input;
        int price = Integer.parseInt(coursePrice);

        Course course = new Course(courseName, price);
        Course.addCourse(course);

        printCoursesList();

        printQForExit();
        System.out.println("Would you like to add another course?");
        System.out.println("1 - yes");
        System.out.println("2 - no");
        input = in.nextLine();

        switch (input) {
            case "Q", "q", "2" -> runAdmin();
            case "1" -> runAddCourse();
        }
    }
}
