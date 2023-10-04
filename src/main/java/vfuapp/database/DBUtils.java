package vfuapp.database;

import vfuapp.Admin;
import vfuapp.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static final String SELECT_USERS = "SELECT * FROM tbl_person";
    private static final String SELECT_ADMINS = "SELECT * FROM tbl_admin";

    private static final String CREATE_USER =
            "INSERT INTO tbl_person(person_id, firstName, lastName, userName, password) VALUES (?, ?, ?, ?, ?);";
    private static final String CREATE_ADMIN =
            "INSERT INTO tbl_admin(admin_id, person_id, id) VALUES (?, ?, ?);";

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
        List<Person> dbpersons = new ArrayList<>();
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

                Person person = new Admin();
//                Person person = new Person();

                person.setTblPersonId(person_id);
                person.setFirstName(firstName);
                person.setLastName(lastName);
                person.setUserName(username);
                person.setPassword(password);

                dbpersons.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbpersons;
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

    public static void createAdmin(Admin admin) {
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
}

/* */
/*  init.sql
    CREATE TABLE IF NOT EXISTS tbl_person (
    person_id BIGINT PRIMARY KEY,
    firstName VARCHAR(80),
    lastName VARCHAR(100),
    username VARCHAR(115),
    password VARCHAR(64)
);

    CREATE TABLE IF NOT EXISTS tbl_admin (
    admin_id BIGINT PRIMARY KEY,
    person_id BIGINT,
    id VARCHAR(8)
    );
*/