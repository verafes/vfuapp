package vfuapp.database;

import vfuapp.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static final String SELECT_ALL = "SELECT * FROM ";
    private static final String SELECT_LAST = "SELECT max(id) FROM ";
    private static final String DROP = "DROP TABLE IF EXISTS ";

    private static final String INSERT_PERSON =
            "INSERT INTO tbl_person(person_id, firstName, lastName, userName, password) VALUES (?, ?, ?, ?, ?);";
    private static final String INSERT_ADMIN =
            "INSERT INTO tbl_admin(admin_id, person_id, id) VALUES (?, ?, ?);";
    private static final String INSERT_STUDENT =
            "INSERT INTO tbl_student(student_id, person_id, academic_id, id) VALUES (?, ?, ?, ?);";
    private static final String INSERT_ACADEMIC_EMPTY_ENROLL =
            "INSERT INTO tbl_academic(academic_id) VALUES (?);";

    private static final String INSERT_COURSE = "INSERT INTO tbl_course(course_id, course_name, price) VALUES (?, ?, ?);";
    private static final String SELECT_MAX_ID = "";

    public static void dropTable(Table.NAME tableName) {

        try (
                Connection connection = DBConnect.getConnection();
                PreparedStatement statement = connection.prepareStatement(DROP + tableName);
        ) {
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getLastId(Table.NAME tableName) {
        final String tableId = Table.getID(tableName);
        final String selectLastId = SELECT_MAX_ID.replace("id", tableId) + tableName;
        int lastId = 0;

        try (
                Connection connection = DBConnect.getConnection();
                PreparedStatement statementQuery = connection.prepareStatement(selectLastId);
        ) {

            ResultSet resultQuery = statementQuery.executeQuery();

            while (resultQuery.next()) {
                lastId = resultQuery.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastId;
    }

    public static List<Person> getTablePersonData() {
        List<Person> dbUsers = new ArrayList<>();
        try (
                Connection connection = DBConnect.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_PERSON);
                ) {

            /* getting data from table */
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int person_id = result.getInt("person_id");
                String firstName = result.getString("firstName");
                String lastName =result.getString("lastName");
                String username =result.getString("username");
                String password =result.getString("password");

                Person person = new Admin();

                person.setTblPersonId(person_id);
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setUserName(username);
                person.setPassword(password);

                dbUsers.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbUsers;
    }

    public static List<Admin> getTableAdminData() {
        List<Admin> dbAdmins = new ArrayList<>();

        try(
            Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_PERSON);
            PreparedStatement statementAdmin = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_ADMIN);

            ) {

            ResultSet resultPerson = statementPerson.executeQuery();
            ResultSet resultAdmin = statementAdmin.executeQuery();

            /* filter for admins only */
            while (resultAdmin.next()) {
                int tblAdminId = resultAdmin.getInt("admin_id");
                int tblAdminPersonId = resultAdmin.getInt("person_id");
                String id = resultAdmin.getString("id");

                while (resultPerson.next()) {
                    int tblPersonId = resultPerson.getInt("person_id");

                    if (tblAdminPersonId == tblPersonId) {
                        String firstName = resultPerson.getString("firstName");
                        String lastName = resultPerson.getString("lastName");
                        String userName = resultPerson.getString("userName");
                        String password = resultPerson.getString("password");

                        Admin admin = new Admin();
                        admin.setTblAdminId(tblAdminId);
                        admin.setTblAdminPersonId(tblAdminPersonId);
                        admin.setRoleId(id);

                        admin.setTblPersonId(tblPersonId);
                        admin.setFirstName(firstName);
                        admin.setLastName(lastName);
                        admin.setUserName(userName);
                        admin.setPassword(password);

                        dbAdmins.add(admin);
                        break;
                    }
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return dbAdmins;
    }

    public static List<Student> getTableStudentData() {
        List<Student> dbStudents = new ArrayList<>();

        try(
                Connection connection = DBConnect.getConnection();
                PreparedStatement statementPerson = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_PERSON);
                PreparedStatement statementStudent = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_STUDENT);
                PreparedStatement statementAcademic = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_ACADEMIC);
        ) {

            ResultSet resultPerson = statementPerson.executeQuery();
            ResultSet resultStudent = statementStudent.executeQuery();
            ResultSet resultAcademic = statementAcademic.executeQuery();

            /* filter for Students only */
            while (resultStudent.next()) {

                int tblStudentId = resultStudent.getInt("student_id");
                int tblStudentPersonId = resultStudent.getInt("person_id");
                String id = resultStudent.getString("id");
                int tblStudentAcademicId = resultStudent.getInt("academic_id");

                while (resultPerson.next()) {
                    int tblPersonId = resultPerson.getInt("person_id");

                    if (tblStudentPersonId == tblPersonId) {
                        String firstName = resultPerson.getString("firstName");
                        String lastName = resultPerson.getString("lastName");
                        String userName = resultPerson.getString("userName");
                        String password = resultPerson.getString("password");

                        while (resultAcademic.next()) {
                            int tblAcademicId = resultAcademic.getInt("academic_id");

                            if (tblStudentAcademicId == tblAcademicId) {
                                int course1 = resultAcademic.getInt("course_id");
                                int course2 = resultAcademic.getInt("course2_id");
                                int course3 = resultAcademic.getInt("course3_id");
                                int course4 = resultAcademic.getInt("course4_id");
                                int course5 = resultAcademic.getInt("course5_id");
                                int course6 = resultAcademic.getInt("course6_id");

                                Student student = new Student();
                                student.setTblStudentId(tblStudentId);
                                student.setTblStudentPersonId(tblStudentPersonId);
                                student.setRoleId(id);
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
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return dbStudents;
    }

    public static List<Professor> getTableProfessorData() {
        List<Professor> dbProfessors = new ArrayList<>();

        try(
                Connection connection = DBConnect.getConnection();
                PreparedStatement statementPerson = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_PERSON);
                PreparedStatement statementProfessor = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_PROFESSOR);
                PreparedStatement statementAcademic = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_ACADEMIC);
        ) {

            ResultSet resultPerson = statementPerson.executeQuery();
            ResultSet resultProfessor = statementProfessor.executeQuery();
            ResultSet resultAcademic = statementAcademic.executeQuery();

            while (resultProfessor.next()) {

                int tblProfessorId = resultProfessor.getInt("student_id");
                int tblProfessorPersonId = resultProfessor.getInt("person_id");
                String id = resultProfessor.getString("id");
                int tblProfessorAcademicId = resultProfessor.getInt("academic_id");

                while (resultPerson.next()) {
                    int tblPersonId = resultPerson.getInt("person_id");

                    if (tblProfessorPersonId == tblPersonId) {
                        String firstName = resultPerson.getString(Table.TBL_PERSON.FIRST_NAME.toString());
                        String lastName = resultPerson.getString("lastName");
                        String userName = resultPerson.getString("userName");
                        String password = resultPerson.getString("password");

                        while (resultAcademic.next()) {
                            int tblAcademicId = resultAcademic.getInt("academic_id");

                            if (tblProfessorAcademicId == tblAcademicId) {
                                int course1 = resultAcademic.getInt("course_id");
                                int course2 = resultAcademic.getInt("course2_id");
                                int course3 = resultAcademic.getInt("course3_id");
                                int course4 = resultAcademic.getInt("course4_id");
                                int course5 = resultAcademic.getInt("course5_id");
                                int course6 = resultAcademic.getInt("course6_id");

                                Professor professor = new Professor();
                                professor.setTblProfessorId(tblProfessorId);
                                professor.setTblProfessorPersonId(tblProfessorPersonId);
                                professor.setRoleId(id);
                                professor.setTblProfessorAcademicId(tblProfessorAcademicId);
                                professor.setTblPersonId(tblPersonId);
                                professor.setFirstName(firstName);
                                professor.setLastName(lastName);
                                professor.setUserName(userName);
                                professor.setPassword(password);

                                professor.setTblAcademicId(tblAcademicId);
                                professor.setCourse1(course1);
                                professor.setCourse2(course2);
                                professor.setCourse3(course3);
                                professor.setCourse4(course4);
                                professor.setCourse5(course5);
                                professor.setCourse6(course6);

                                dbProfessors.add(professor);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return dbProfessors;
    }
    public static List<Course> getTableCourseData() {
        List<Course> dbCourse = new ArrayList<>();

        try (
                Connection connection = DBConnect.getConnection();
                PreparedStatement statementCourse = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_COURSE);
        ) {

            ResultSet resultCourse = statementCourse.executeQuery();

            while (resultCourse.next()) {
                int tblCourseId = resultCourse.getInt("course_id");
                String courseName = resultCourse.getString("course_name");
                int price = resultCourse.getInt("price");

                Course course = new Course();
                course.setTblCourseId(tblCourseId);
                course.setCourseName(courseName);
                course.setPrice(price);

                dbCourse.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbCourse;
    }

    public static Academic getTableAcademicData(int academicId) {
        Academic dbAcademic = new Academic();

        try (Connection connection = DBConnect.getConnection();
             PreparedStatement statementSelectAcademics = connection.prepareStatement(SELECT_ALL + Table.NAME.TBL_ACADEMIC);
        ) {

            ResultSet resultAcademic = statementSelectAcademics.executeQuery();

            while (resultAcademic.next()) {
                int tblAcademicId = resultAcademic.getInt("academic_id");

                if (academicId == tblAcademicId) {
                    int course1Id = resultAcademic.getInt("course1_id");
                    int course2Id = resultAcademic.getInt("course2_id");
                    int course3Id = resultAcademic.getInt("course3_id");
                    int course4Id = resultAcademic.getInt("course4_id");
                    int course5Id = resultAcademic.getInt("course5_id");
                    int course6Id = resultAcademic.getInt("course6_id");

                    dbAcademic.setTblAcademicId(tblAcademicId);
                    dbAcademic.setCourse1(course1Id);
                    dbAcademic.setCourse2(course2Id);
                    dbAcademic.setCourse3(course3Id);
                    dbAcademic.setCourse4(course4Id);
                    dbAcademic.setCourse5(course5Id);
                    dbAcademic.setCourse6(course6Id);

                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbAcademic;
    }

    public static void insertAdmin(Admin admin) {
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(INSERT_PERSON);
            PreparedStatement statementAdmin = connection.prepareStatement(INSERT_ADMIN);)
        {

            statementPerson.setInt(1, admin.getTblPersonId());
            statementPerson.setString(2, admin.getFirstName());
            statementPerson.setString(3, admin.getLastName());
            statementPerson.setString(4, admin.getUserName());
            statementPerson.setString(5, admin.getPassword());

            statementPerson.executeUpdate();

            statementAdmin.setInt(1, admin.getTblAdminId());
            statementAdmin.setInt(2, admin.getTblAdminPersonId());
            statementAdmin.setString(3, admin.getRoleId());

            statementAdmin.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertStudent(Student student) {
        try(Connection connection = DBConnect.getConnection();
//            PreparedStatement statementPerson = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement statementPerson = connection.prepareStatement(INSERT_PERSON);
            PreparedStatement statementStudent = connection.prepareStatement(INSERT_STUDENT);
            PreparedStatement statementAcademic = connection.prepareStatement(INSERT_ACADEMIC_EMPTY_ENROLL);)
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
            statementStudent.setString(4, student.getRoleId());

            statementStudent.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertProfessor(Professor professor) {
        try(Connection connection = DBConnect.getConnection();
            PreparedStatement statementPerson = connection.prepareStatement(INSERT_PERSON);
            PreparedStatement statementProfessor = connection.prepareStatement(INSERT_PERSON);
            PreparedStatement statementAcademic = connection.prepareStatement(INSERT_ACADEMIC_EMPTY_ENROLL);)
        {

            statementPerson.setInt(1, professor.getTblPersonId());
            statementPerson.setString(2, professor.getFirstName());
            statementPerson.setString(3, professor.getLastName());
            statementPerson.setString(4, professor.getUserName());
            statementPerson.setString(5, professor.getPassword());

            statementPerson.executeUpdate();


            statementAcademic.setInt(1, professor.getTblProfessorAcademicId());

            statementAcademic.executeUpdate();

            statementProfessor.setInt(1, professor.getTblProfessorId());
            statementProfessor.setInt(2, professor.getTblProfessorPersonId());
            statementProfessor.setInt(3, professor.getTblProfessorAcademicId());
            statementProfessor.setString(4, professor.getRoleId());

            statementProfessor.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertCourse(Course course) {
        try (
                Connection connection = DBConnect.getConnection();
                PreparedStatement statementCourse = connection.prepareStatement(INSERT_COURSE);
        ) {

            statementCourse.setInt(1, course.getTblCourseId());
            statementCourse.setString(2, course.getCourseName());
            statementCourse.setInt(3, course.getPrice());

            statementCourse.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAcademicEnroll(Academic academic) {
        try (Connection connection = DBConnect.getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ) {

            int idAcademic = academic.getTblAcademicId();
            String query = SELECT_ALL + Table.NAME.TBL_ACADEMIC + " WHERE academic_id = " + idAcademic;

            ResultSet resultAcademic = statement.executeQuery(query);

            while (resultAcademic.next()) {
                int tblAcademicId = resultAcademic.getInt("academic_id");

                if (idAcademic == tblAcademicId) {
                    resultAcademic.updateInt("course1_id", academic.getCourse1());
                    resultAcademic.updateInt("course2_id", academic.getCourse2());
                    resultAcademic.updateInt("course3_id", academic.getCourse3());
                    resultAcademic.updateInt("course4_id", academic.getCourse4());
                    resultAcademic.updateInt("course5_id", academic.getCourse5());
                    resultAcademic.updateInt("course6_id", academic.getCourse6());

                    resultAcademic.updateRow();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
