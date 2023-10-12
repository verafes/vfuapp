package vfuapp.database;

import vfuapp.Admin;
import vfuapp.Person;
import vfuapp.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static final String SELECT_USERS = "SELECT * FROM tbl_person";
    private static final String SELECT_ADMINS = "SELECT * FROM tbl_admin";
    private static final String SELECT_STUDENTS = "SELECT * FROM tbl_student";
    private static final String SELECT_ACADEMICS = "SELECT * FROM tbl_academic";

    private static final String CREATE_USER =
            "INSERT INTO tbl_person(person_id, firstName, lastName, userName, password) VALUES (?, ?, ?, ?, ?);";
    private static final String CREATE_ADMIN =
            "INSERT INTO tbl_admin(admin_id, person_id, id) VALUES (?, ?, ?);";
    private static final String CREATE_STUDENT =
            "INSERT INTO tbl_student(student_id, person_id, academic_id, id) VALUES (?, ?, ?, ?);";
    private static final String CREATE_ACADEMIC =
            "INSERT INTO tbl_academic(academic_id) VALUES (?);";

    /* debugging of creating the first admin */
    private static final String DROP_TBL_PERSON = "DROP TABLE IF EXISTS tbl_person";
    private static final String DROP_TBL_ADMIN = "DROP TABLE IF EXISTS tbl_admin";

    public static void dropTablePerson() {
        try (
                Connection connection = DBConnect.getConnection();
                PreparedStatement statement = connection.prepareStatement(DROP_TBL_PERSON);
                ) {
            /* deleting table tbl_person */
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTableAdmin() {
        try (
                Connection connection = DBConnect.getConnection();
                PreparedStatement statement = connection.prepareStatement(DROP_TBL_ADMIN);
        ) {
            /* deleting table tbl_admin */
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Person> getTablePersonData() {
        List<Person> dbPersons = new ArrayList<>();
        try (
                Connection connection = DBConnect.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_USERS);
                ) {

            /* getting data from table */
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                int person_id = result.getInt("person_id");
                String firstName = result.getString("firstName");
                String lastName =result.getString("lastName");
                String username =result.getString("username");
                String password =result.getString("password");

//                Person person = new Admin();
                 Person person = new Person();

                person.setTblPersonId(person_id);
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setUserName(username);
                person.setPassword(password);

                dbPersons.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbPersons;
    }

    public static List<Admin> getTableAdminData() {
        List<Admin> dbAdmins = new ArrayList<>();

        try(
                Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(SELECT_USERS);
            PreparedStatement statementAdmin = connection.prepareStatement(SELECT_ADMINS);
            ) {

            ResultSet resultPerson = statementPerson.executeQuery();
            ResultSet resultAdmin = statementAdmin.executeQuery();

            /* to do filter for admins only */
            while (resultPerson.next() && resultAdmin.next()) {
                int tblAdminId = resultAdmin.getInt("admin_id");
                int tblAdminPersonId = resultAdmin.getInt("person_id");
                String id = resultAdmin.getString("id");

                int tblPersonId = resultPerson.getInt("person_id");
                String firstName = resultPerson.getString("firstName");
                String lastName = resultPerson.getString("lastName");
                String userName = resultPerson.getString("userName");
                String password = resultPerson.getString("password");

                Admin admin = new Admin();
                admin.setTblAdminId(tblAdminId);
                admin.setTblAdminPersonId(tblAdminPersonId);
                admin.setId(id);
                admin.setTblPersonId(tblPersonId);
                admin.setFirstName(firstName);
                admin.setLastName(lastName);
                admin.setUserName(userName);
                admin.setPassword(password);

                dbAdmins.add(admin);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return dbAdmins;
    }

    public static void insertAdmin(Admin admin) {
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(CREATE_USER);
            PreparedStatement statementAdmin = connection.prepareStatement(CREATE_ADMIN);)
        {
            //"INSERT INTO tbl_person(person_id, firstName, lastName, userName, password) VALUES (?, ?, ?, ?, ?);";
            //"INSERT INTO tbl_admin(admit_id, person_id, id) VALUES (?, ?, ?);";
            statementPerson.setInt(1, admin.getTblPersonId());
            statementPerson.setString(2, admin.getFirstName());
            statementPerson.setString(3, admin.getLastName());
            statementPerson.setString(4, admin.getUserName());
            statementPerson.setString(5, admin.getPassword());

            statementPerson.executeUpdate();

            statementAdmin.setInt(1, admin.getTblAdminId());
            statementAdmin.setInt(2, admin.getTblAdminPersonId());
            statementAdmin.setString(3, admin.getId());

            statementAdmin.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertStudent(Student student) {
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement statementStudent = connection.prepareStatement(CREATE_STUDENT);
            PreparedStatement statementAcademic = connection.prepareStatement(CREATE_ACADEMIC);)
        {

            statementPerson.setInt(1, student.getTblPersonId());
            statementPerson.setString(2, student.getFirstName());
            statementPerson.setString(3, student.getLastName());
            statementPerson.setString(4, student.getUserName());
            statementPerson.setString(5, student.getPassword());

            statementPerson.executeUpdate();


            statementAcademic.setInt(1, student.getTblStudentAcademicId());

            statementAcademic.executeUpdate();

            statementStudent.setInt(1, student.getTblStudentId());
            statementStudent.setInt(2, student.getTblStudentPersonId());
            statementStudent.setInt(3, student.getTblStudentAcademicId());
            statementStudent.setString(4, student.getId());

            statementStudent.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> getTableStudentData() {
        List<Student> dbStudents = new ArrayList<>();

        try(
                Connection connection = DBConnect.getConnection();
                PreparedStatement statementPerson = connection.prepareStatement(SELECT_USERS);
                PreparedStatement statementStudent = connection.prepareStatement(SELECT_STUDENTS);
                PreparedStatement statementAcademic = connection.prepareStatement(SELECT_ACADEMICS);
        ) {

            ResultSet resultPerson = statementPerson.executeQuery();
            ResultSet resultStudent = statementStudent.executeQuery();
            ResultSet resultAcademic = statementAcademic.executeQuery();

            /* to do filter for Students only */
            while (resultPerson.next() && resultStudent.next() && resultAcademic.next()) {
                if(resultPerson.getInt("person_id") == resultStudent.getInt("person_id")) {
                    int tblStudentId = resultStudent.getInt("student_id");
                    int tblStudentPersonId = resultStudent.getInt("person_id");
                    String id = resultStudent.getString("id");
                    int tblStudentAcademicId = resultStudent.getInt("academic_id");

                    int tblPersonId = resultPerson.getInt("person_id");
                    String firstName = resultPerson.getString("firstName");
                    String lastName = resultPerson.getString("lastName");
                    String userName = resultPerson.getString("userName");
                    String password = resultPerson.getString("password");

                    int tblAcademicId = resultAcademic.getInt("academic_id");
                    int course1 = resultAcademic.getInt("course_id");
                    int course2 = resultAcademic.getInt("course2_id");
                    int course3 = resultAcademic.getInt("course3_id");
                    int course4 = resultAcademic.getInt("course4_id");
                    int course5 = resultAcademic.getInt("course5_id");
                    int course6 = resultAcademic.getInt("course6_id");

                    Student student = new Student();
                    student.setTblStudentId(tblStudentId);
                    student.setTblStudentPersonId(tblStudentPersonId);
                    student.setId(id);
                    student.setTblStudentAcademicId(tblStudentAcademicId);
                    student.setTblPersonId(tblPersonId);
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setUserName(userName);
                    student.setPassword(password);

                    student.setTblAcademicId(tblAcademicId);
                    student.setCourse1(course1);
                    student.setCourse2(course2);
                    student.setCourse3(course3);
                    student.setCourse4(course4);
                    student.setCourse5(course5);
                    student.setCourse6(course6);

                    dbStudents.add(student);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return dbStudents;
    }
}
