package Registration;

import User.User;

import java.io.*;
import java.util.*;

public class UserLogin {

    //constructor of login class
    public UserLogin() {

    }

    //a method to verify the credentials using which the user has logged into the application
    public User verifyUser(String id) {
        Scanner verification = null;
            /*checks if the id of the user, which has been passed as a parameter is the same as any of the ids of
            the users in the file. This is achieved by parsing through each line of the file and comparing ids*/
        try {
            verification = new Scanner(new FileInputStream("userlist.txt"));
            //scanner is linked to a file which has a list of all registered users
            while (verification.hasNextLine()) {
                String[] userDetails = (verification.nextLine()).split(",");
                //array which houses the data of a registered user read from a line of the file
                if (userDetails[1].equals(id)) {
                    verification.close();
                    System.out.println("Welcome! " + userDetails[0]);
                    //welcome message with the name read from the file of users
                    return new User(userDetails[0], userDetails[1], userDetails[2], userDetails[3]);
                    //user object is created and returned with the details of the user if the user is found
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Register first please");
            //if the user is not present in the database, a message asking them to register is instead
        }

        System.out.println("Invalid Credentials");
        return null;
    }
}