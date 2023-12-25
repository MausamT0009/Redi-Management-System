package Registration;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserRegistration{

    public UserRegistration(String name, String id, String email, String mobile){
        //constructor for user registration which uses name, id, email and phone number
        boolean flag = false;   //variable which stores whether or not the ID is registered
        FileWriter UserReg = null;  //object which adds unregistered user to the userlist
        FileWriter user = null;     //object which creates a new file for storing order data related solely to the user to be registered
        Scanner checkUser = null;   //scanner object which will be used to check if the user is already registered
        try {
            if (email.endsWith("@pilani.bits-pilani.ac.in")) {
                //checks if the user is a student/professor at bits pilani by checking for the use of a bits email address

                checkUser = new Scanner(new FileInputStream("userlist.txt"));


                //checking to see if the user is already registered
                while (checkUser.hasNextLine()) {
                    if (checkUser.nextLine().split(",")[1].equals(id)) {
                        System.out.println("This ID is already registered.");
                        flag = true;
                        break;
                    }
                }

                //the following code is for users not already registered
                //it adds the user to the userlist using the parameters in the constructor
                if (!flag) {
                    UserReg = new FileWriter("userlist.txt", true);

                    UserReg.write(name + "," + id + "," + email + "," + mobile);    //adding details to the userlist
                    UserReg.write("\n");
                    System.out.println("You are successfully registered");
                    user = new FileWriter(id + ".txt");  //creating a new file for storing details of the user's orders

                    user.close();
                    UserReg.close();
                }
            }
            else{
                System.out.println("Register using BITS email only");   //message displayed if bits email was not used
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

}