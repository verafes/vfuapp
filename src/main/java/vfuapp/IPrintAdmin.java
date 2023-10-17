package vfuapp;

import vfuapp.database.DBUtils;

import static vfuapp.Admin.admins;
import static vfuapp.Student.students;
import static vfuapp.Course.courses;

public interface IPrintAdmin {
    default void printAdminsList(){
        admins = DBUtils.getTableAdminData();
        System.out.println(admins);
    }

    default void printFirstAdmin() {
        admins = DBUtils.getTableAdminData();
        System.out.println(admins.get(0));
    }

    default void printLastAdmin() {
        admins = DBUtils.getTableAdminData();
        System.out.println(admins.get(admins.size() - 1));
    }

    default void printStudentsList(){
        students = DBUtils.getTableStudentData();
        System.out.println(students);
    }

    default void printLastStudent() {
        students = DBUtils.getTableStudentData();
        System.out.println(students.get(students.size() - 1));
    }

    default void printCoursesList() {
        System.out.println("Available Courses:");
        courses = DBUtils.getTableCourseData();
        System.out.println(courses);
        System.out.println();
    }
}
