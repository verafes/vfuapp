package vfuapp.database;

import vfuapp.Admin;
import vfuapp.Person;
import vfuapp.Student;
import vfuapp.Professor;

import java.util.Arrays;
import java.util.List;

public class DBClear {
    public static void main(String[] args) {
        DBUtils.dropTable(Table.NAME.TBL_PERSON);
        DBUtils.dropTable(Table.NAME.TBL_ADMIN);
        DBUtils.dropTable(Table.NAME.TBL_STUDENT);
        DBUtils.dropTable(Table.NAME.TBL_PROFESSOR);
        DBUtils.dropTable(Table.NAME.TBL_ACADEMIC);
        DBUtils.dropTable(Table.NAME.TBL_COURSE);


        final List<Person> dbUsers = DBUtils.getTablePersonData();
        final List<Admin> dbAdmins = DBUtils.getTableAdminData();
        final List<Student> dbStudents = DBUtils.getTableStudentData();
        final List<Professor> dbProfessors = DBUtils.getTableProfessorData();

        System.out.println("Persons: ");
        System.out.println(Arrays.toString(dbUsers.toArray()));
        System.out.println("Admins: ");
        System.out.println(Arrays.toString(dbAdmins.toArray()));
        System.out.println("Student:");
        System.out.println(Arrays.toString(dbStudents.toArray()));
        System.out.println("Professor:");
        System.out.println(Arrays.toString(dbProfessors.toArray()));
    }
}
