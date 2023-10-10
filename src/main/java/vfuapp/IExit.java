package vfuapp;

public interface IExit {
    default void printQForExit(){
        System.out.println();
        System.out.println("Enter 'Q' for quit the registration system");
        System.out.println();
    }

    default void exitIfQ(String input){
        if (input.equalsIgnoreCase("Q")) {
            System.out.println("Thank you for using our registration system. Goodbye!");
            System.exit(0);
        }
    }

    default void ifUnauthorizedUser(){
        System.out.println("Sorry, we can't recognize credentials. Please double-check and try again later");
        System.out.println("Thank you for using our registration system. Goodbye!");
        System.exit(0);
    }

}
