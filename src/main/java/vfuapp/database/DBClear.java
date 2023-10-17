package vfuapp.database;

import vfuapp.Admin;
import vfuapp.Person;
import vfuapp.Student;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

public class DBClear {
    public static void main(String[] args) {
//        DBUtils.dropTable(TableName.PERSON);
//        DBUtils.dropTable(TableName.ADMIN);
//        DBUtils.dropTable(TableName.STUDENT);
//        DBUtils.dropTable(TableName.PROFESSOR);
//        DBUtils.dropTable(TableName.ACADEMIC);
//        DBUtils.dropTable(TableName.COURSE);
//        DBUtils.dropTableAdmin();
//        DBUtils.dropTablePerson();

        List<Person> dbUsers = DBUtils.getTablePersonData();
        List<Admin> dbAdmins = DBUtils.getTableAdminData();
        List<Student> dbStudents = DBUtils.getTableStudentData();

        System.out.println("Persons: ");
        System.out.println(Arrays.toString(dbUsers.toArray()));
        System.out.println("Admins: ");
        System.out.println(Arrays.toString(dbAdmins.toArray()));
        System.out.println("Student:");
        System.out.println(Arrays.toString(dbStudents.toArray()));
    }
}
