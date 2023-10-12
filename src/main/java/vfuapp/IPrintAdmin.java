package vfuapp;

import static vfuapp.Admin.admins;
import static vfuapp.Student.students;

public interface IPrintAdmin {
    default void printAdminsList(){
        System.out.println(admins);
    }
    default void printStudentsList(){
        System.out.println(students);
    }

}
