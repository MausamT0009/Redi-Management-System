import Redi.Redi;
import Registration.UserLogin;
import Registration.UserRegistration;
import User.User;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Initiator {
    public static void Initiate(){
        JFrame f = new JFrame("opened");
        f.setSize(400,400);

        JLabel label = new JLabel("Welcome to redi mania");
        label.setBounds(50,50,150,30);

        JButton b1 = new JButton("USER");
        b1.setBounds(50,100,95,30);
        b1.addActionListener(new ActionListener() {   //// IF USER IS CLICKED
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f1 = new JFrame("USER");
                f1.setSize(400,400);

                JButton UserReg = new JButton("REGISTER");
                UserReg.setBounds(50,100,100,30);
                UserReg.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { /// USER REGISTRATION
                        JFrame USReg = new JFrame("USER REGISTRATION");
                        USReg.setSize(400,500);

                        JLabel name = new JLabel("Enter Name");
                        name.setBounds(10,50,150,30);
                        JLabel bitsid = new JLabel("Enter Bits ID");
                        bitsid.setBounds(10,100,150,30);
                        JLabel email = new JLabel("Enter email (BITS only)");
                        email.setBounds(10,150,150,30);
                        JLabel mobile = new JLabel("Enter mobile no.");
                        mobile.setBounds(10,200,150,30);
                        JTextField nameText = new JTextField();
                        nameText.setBounds(160,50,200,30);
                        JTextField idText = new JTextField();
                        idText.setBounds(160,100,200,30);
                        JTextField emailText = new JTextField();
                        emailText.setBounds(160,150,200,30);
                        JTextField mobileText = new JTextField();
                        mobileText.setBounds(160,200,200,30);

                        JTextField printmsg = new JTextField();
                        printmsg.setBounds(50,250,200,30);

                        JButton regExit = new JButton("EXIT");
                        regExit.setBounds(50,350,100,30);
                        regExit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                USReg.dispose();
                            }
                        });

                        JButton Reg = new JButton("Register");
                        Reg.setBounds(100,300,100,30);
                        Reg.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(emailText.getText().endsWith("@pilani.bits-pilani.ac.in")){
                                    new UserRegistration(nameText.getText(), idText.getText(), emailText.getText(), mobileText.getText());
                                    printmsg.setText("YOU CAN NOW LOGIN");
                                }
                                else {
                                    printmsg.setText("RE-ENTER EMAIL");
                                }
                            }
                        });
                        USReg.add(name);
                        USReg.add(bitsid);
                        USReg.add(email);
                        USReg.add(mobile);
                        USReg.add(nameText);
                        USReg.add(idText);
                        USReg.add(emailText);
                        USReg.add(mobileText);
                        USReg.add(Reg);
                        USReg.add(printmsg);
                        USReg.add(regExit);
                        USReg.setLayout(null);
                        USReg.setVisible(true);

                    }
                });


                JButton UserLogin = new JButton("LOGIN");
                UserLogin.setBounds(50,150,100,30);
                UserLogin.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {    /// USER LOGIN
                        JFrame USLog = new JFrame("USER LOGIN");
                        USLog.setSize(400,400);

                        JLabel id = new JLabel("ENTER BITS-ID");
                        id.setBounds(50,150,100,30);

                        JTextField idText = new JTextField();
                        idText.setBounds(150,150,100,30);

                        JButton logExit = new JButton("EXIT");
                        logExit.setBounds(50,300,100,30);
                        logExit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                USLog.dispose();
                            }
                        });

                        JTextField errormsg = new JTextField();
                        errormsg.setBounds(50,200,200,30);

                        JButton login = new JButton("LOGIN");
                        login.setBounds(100,250,100,30);
                        login.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String inpId = idText.getText();
                                User u = new UserLogin().verifyUser(inpId);
                                if(u!=null){
                                    Thread t = new Thread(u);
                                    t.start();
                                }
                                else{
                                    errormsg.setText("incorrect credentials, enter again");
                                }


                            }
                        });

                        USLog.add(id);
                        USLog.add(idText);
                        USLog.add(login);
                        USLog.add(errormsg);
                        USLog.add(logExit);
                        USLog.setLayout(null);
                        USLog.setVisible(true);
                    }
                });

                JButton UserExit = new JButton("EXIT");
                UserExit.setBounds(50,200,100,30);
                UserExit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        f1.dispose();
                    }
                });

                f1.add(UserReg);
                f1.add(UserLogin);
                f1.add(UserExit);
                f1.setLayout(null);
                f1.setVisible(true);
            }
        });

        JButton b2 = new JButton("OWNER");
        b2.setBounds(50,150,95,30);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  /// IF OWNER CLICKED
                JFrame f2 = new JFrame("OWNER");
                f2.setSize(400,400);

                JLabel ownerlabel = new JLabel("Please Enter your REDI ID");
                ownerlabel.setBounds(20,50,150,30);

                String Redis[] = {"Shankar","Meera","CVR","SR"};
                JComboBox ownerCb = new JComboBox(Redis);
                ownerCb.setBounds(200,50,150,30);
                ownerCb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String str = ownerCb.getItemAt(ownerCb.getSelectedIndex()).toString();
                        Thread s = new Thread(new Redi(str));
                        s.start();
                    }
                });

                f2.add(ownerlabel);
                f2.add(ownerCb);
                f2.setLayout(null);
                f2.setVisible(true);
            }
        });

        JButton b3 = new JButton("EXIT");
        b3.setBounds(50,200,95,30);
        b3.addActionListener(new ActionListener() {    //// IF EXIT CLICKED
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });

        f.add(label);
        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.setLayout(null);
        f.setVisible(true);

        Scanner sc = new Scanner(System.in);    //scanner object to take inputs from the user regarding instructions and the
        //System.out.println("Welcome to Redi-Mania");
        //System.out.println("If you are user, press 1 and then enter");
        //System.out.println("If you are owner, press 2 and then enter");
        //System.out.println("To exit, press 0 and then enter");
        int n = sc.nextInt();
        //reading the number entered by the user
        sc.nextLine();  //clearing the scanner
        switch(n){
            //the client is a student who wishes to order food
            case 1: System.out.println("To register, press 1");
                System.out.println("To login, press 2");
                System.out.println("To exit, press 0");
                int m = sc.nextInt();
                sc.nextLine();
               //switch(m){
                    //registration
                    //case 1: System.out.println("Enter the following details :");
                      //  System.out.println("Enter name :");
                        //String name = sc.nextLine();
                        //System.out.println("Enter BITSid :");
                        //String BITSid = sc.nextLine();
                        //System.out.println("Enter email(must be a BITS email only) :");
                        //String email = sc.nextLine();
                        //System.out.println("Enter mobile number :");
                        //String mobile = sc.nextLine();
                        ///////////   LOOK INTO THIS  //////// new UserRegistration(name, BITSid, email, mobile);
                        //UserRegistration class is called with the parameters being the user's data gathered using the scanner
                        //if(email.endsWith("@pilani.bits-pilani.ac.in")){
                          //  System.out.println("You can now login to use the service. ");
                        //}
                        //Initiate();
                        //break;
                    //case 2: System.out.println("Enter the following details :");
                      //  System.out.println("Enter your BITSID :");
                        //String id = sc.nextLine();
                        //User u = new UserLogin().verifyUser(id);
                        //if(u!=null){
                        //    Thread t = new Thread(u);
                        //    t.start();
                        //}
                        //else{
                        //    System.out.println("You didn't enter correct inputs. Try again");
                        //    Initiate();
                        //}
                        //break;
                    //case 0: return;
                    //default: System.out.println("You didn't enter correct inputs. Try Again");
                        //Initiate();
                //}
                //break;


        }


    }
}