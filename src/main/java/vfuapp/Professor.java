package vfuapp;

import vfuapp.database.DBUtils;
import vfuapp.database.Table;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Academic {
    private int tblProfessorId;
    private int tblProfessorPersonId;
    private int tblProfessorAcademicId;
    private String roleId = "P";
    private static int professorID = 1000001;
    public static List<Professor> professors = new ArrayList<>();

    public Professor(String firstName, String lastName, List<String> courses) {
        super(firstName, lastName, courses);
        int lastTblStudentId = DBUtils.getLastId(Table.NAME.TBL_STUDENT);
        this.tblProfessorId = lastTblStudentId + 1;
        this.tblProfessorPersonId = getTblPersonId();
        this.tblProfessorAcademicId = getTblAcademicId();
        this.roleId = roleId + 2000 + tblProfessorId;
    }
    public Professor(String firstName, String lastName) {
        super(firstName, lastName, new ArrayList<>());
        int lastTblStudentId = DBUtils.getLastId(Table.NAME.TBL_STUDENT);
        this.tblProfessorId = lastTblStudentId + 1;
        this.tblProfessorPersonId = getTblPersonId();
        this.tblProfessorAcademicId = getTblAcademicId();
        this.roleId = roleId + 2000 + tblProfessorId;
    }

    public Professor() {}
    public int getTblProfessorId() {
        return tblProfessorId;
    }

    public void setTblProfessorId(int tblProfessorId) {
        this.tblProfessorId = tblProfessorId;
    }

    public int getTblProfessorPersonId() {
        return tblProfessorPersonId;
    }

    public void setTblProfessorPersonId(int tblProfessorPersonId) {
        this.tblProfessorPersonId = tblProfessorPersonId;
    }

    public int getTblProfessorAcademicId() {
        return tblProfessorAcademicId;
    }

    public void setTblProfessorAcademicId(int tblProfessorAcademicId) {
        this.tblProfessorAcademicId = tblProfessorAcademicId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public char getRole() {
        return roleId.charAt(0);
    }

//    @Override
//    public Professor getTableData(int id) {
//        return DBUtils.getTableProfessorData(id);
//    }
//
//    @Override
//    public int getAcademicId() {
//        return 0;
//    }

    public void runProfessor(){

    }

}
