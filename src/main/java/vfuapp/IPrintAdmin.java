package vfuapp;

import static vfuapp.Admin.admins;

public interface IPrintAdmin {
    default void printAdminsList(){
        System.out.println(admins);
    }

}
