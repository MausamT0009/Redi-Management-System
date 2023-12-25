package Redi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;
public class Redi implements Runnable{
    private Menu menu;
    //private Owner rediOwner;
    private final String rediId;
    public Redi(String rediId){
        this.menu = new Menu(rediId);
        this.rediId = rediId;
    }
    public String getRediId(){
        return this.rediId;
    }
    public Menu getMenu(){
        return this.menu;
    }
    public void run(){
        rediMethod();
    }
    public synchronized String getMostOrderedItem(){
        Scanner menuReader = null;
        String ret = new String();
        ArrayList<String> itemIdString = new ArrayList<>();
        ArrayList<Integer> itemFrequency = new ArrayList<Integer>();
        try{
            menuReader = new Scanner(new FileInputStream(rediId + "orders.txt"));
            while(menuReader.hasNextLine()){
                String[] s = menuReader.nextLine().split(",");
                if(itemIdString.contains(s[2])){
                    int i = itemIdString.indexOf(s[2]);
                    itemFrequency.set(i,itemFrequency.get(i) +Integer.parseInt(s[3]));
                }
                else{
                    itemIdString.add(s[2]);
                    itemFrequency.add(Integer.parseInt(s[3]));
                }
            }
            int index = 0;
            for(int i=0; i<itemFrequency.size();i++){
                if(itemFrequency.get(i)>itemFrequency.get(index)){
                    index = i;
                }
            }
             ret = "The most ordered item at "  + rediId + " redi " + "is item " + itemIdString.get(index) + " with " + itemFrequency.get(index) + " orders";
            System.out.println("The most ordered item at "  + rediId + " redi " + "is item " + itemIdString.get(index) + " with "
                    + itemFrequency.get(index) + " orders");

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return ret;
    }
    public synchronized void deliverOrder(String timestamp, int id){
        Scanner orderReader = null;
        boolean flag = false;
        FileWriter delivery = null;
        try{
            orderReader = new Scanner(new FileInputStream(rediId + "orders.txt"));
            ArrayList<String []> orders = new ArrayList<>();
            while(orderReader.hasNextLine()){
                String[] s = orderReader.nextLine().split(",");
                if(s[0].equals((new java.util.Date(Integer.parseInt(timestamp)*1000L)).toString()) && s[1].equals(""+ id)){
                    s[5] = "Delivered";
                    flag = true;
                }
                orders.add(s);
            }
            if(!flag){
                System.out.println("No order found with this id at the given timestamp");
                return;
            }
            delivery = new FileWriter(rediId + "orders.txt");
            for(int i=0; i< orders.size();i++){
                String[] t = orders.get(i);
                delivery.write(t[0] + "," + t[1] + "," + t[2] + "," + t[3] + "," + t[4] + "," + t[5] + "\n");
            }
            delivery.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public synchronized String calculateRevenue(){
        float revenue = 0;
        Scanner calcRev = null;
        String ret = new String();
        try{
            calcRev = new Scanner(new FileInputStream(rediId+"orders.txt"));
            while(calcRev.hasNextLine()){
                String[] s = calcRev.nextLine().split(",");
                if(s[5].equals("Delivered")){
                    revenue += Float.parseFloat((s[4].split(" "))[1]);
                }
            }
            ret = "The total revenue generated is ₹" + revenue;
            System.out.println("The total revenue generated is ₹" + revenue);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return ret;
    }
    public synchronized String displayMenu(){
        Scanner printMenu = null;
        String Menudis = new String();
        try{
            System.out.println("The menu for this redi is :");
            printMenu = new Scanner(new FileInputStream(rediId + "menu.txt"));

            while(printMenu.hasNextLine()){
                String[] s = printMenu.nextLine().split(",");
                System.out.println("ItemName: " + s[0] + "    " + "ItemId: " + s[1] + "   " + "Price: " + s[2]);
                Menudis += "ItemName: " + s[0] + "    " + "ItemId: " + s[1] + "   " + "Price: " + s[2] + "   " ;
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

       return Menudis;
    }
    public synchronized String viewPendingOrders(){
        Scanner viewPenOrd = null;
        String ret = new String();
        try{
            viewPenOrd = new Scanner(new FileInputStream(rediId + "orders.txt"));
            while(viewPenOrd.hasNextLine()){
                String s[] = viewPenOrd.nextLine().split(",");
                java.util.Date cuDate = new java.util.Date();
                long time = cuDate.getTime()/1000;
                SimpleDateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                java.util.Date date = format.parse(s[0]);
                long timestamp = date.getTime()/1000;
                if(s[5].equals("Pending")){
                    System.out.println("Timestamp: " + timestamp + "    OrderingId: " + s[1] + "    ItemId: " + s[2] + "    Quantity: "
                            + s[3] + "  Price: " + s[4]);
                    ret += "Timestamp: " + timestamp + "    OrderingId: " + s[1] + "    ItemId: " + s[2] + "    Quantity: "
                            + s[3] + "  Price: " + s[4] + " ;;; ";
                }
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
        }
        return ret;
    }
    public synchronized String mostFreqTime(){
        Scanner orderTime = null;
        String ret = new String();
        String[] tinfo = {"Morning - 5AM to 10AM","Afternoon - 11AM to 4PM","Evening - 5PM to 10PM","Night - 11PM to 4AM"};
        try{
            orderTime =  new Scanner(new FileInputStream(rediId + "orders.txt"));
            int[] frequency = new int[4];

            while(orderTime.hasNextLine()){
                String time = orderTime.nextLine().split(",")[0].split(" ")[3].split(":")[0];
                int t = Integer.parseInt(time);
                if(t>=5&&t<=10){
                    frequency[0]++;
                }
                if(t>=11&&t<=16){
                    frequency[1]++;
                }
                if(t>=17&&t<=22){
                    frequency[2]++;
                }
                else{
                    frequency[3]++;
                }
            }
            int max_time = 0;
            for(int i=0; i<4; i++){
                if(frequency[i]>frequency[max_time]){
                    max_time = i;
                }
            }
            ret = "The most frequent time for orders is: " + tinfo[max_time];
            System.out.println("The most frequent time for orders is: " + tinfo[max_time]);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return ret;
    }
    public synchronized String viewLastWeekIncome(){
        Scanner lastWeek = null;
        String ret = new String();
        try{
            lastWeek = new Scanner(new FileInputStream(rediId + "orders.txt"));
            float weekInc = 0;
            while(lastWeek.hasNextLine()){
                String[] s = lastWeek.nextLine().split(",");
                Date cuDate = new Date();
                long cuSec = cuDate.getTime()/1000;
                SimpleDateFormat format = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                Date orderDate = format.parse(s[0]);
                long orderTime = orderDate.getTime()/1000;
                if(s[5].equals("Delivered")&&cuSec - orderTime<=604800){
                    weekInc = weekInc + Float.parseFloat(s[4].split(" ")[1]);
                }
            }
            System.out.println("The total earnings for the last week are - ₹" + weekInc);
            ret = "The total earnings for the last week are - ₹" + weekInc;
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return ret;
    }
    public synchronized void rediMethod(){

        JFrame fRedi = new JFrame("REDI");
        fRedi.setSize(600,500);

        JLabel ownerlabel = new JLabel("Please Enter your REDI ID");
        ownerlabel.setBounds(100,350,200,30);

        String Redis[] = {"Shankar","Meera","CVR","SR"};
        JComboBox ownerCb = new JComboBox(Redis);
        ownerCb.setBounds(350,350,150,30);
        ownerCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // rediId = ownerCb.getItemAt(ownerCb.getSelectedIndex()).toString();
            }
        });                                                          ///////////// TRIAL ///////////

        JButton b1 = new JButton("Get most ordered item");
        b1.setBounds(25,50,200,30);
        b1.addActionListener(new ActionListener() { //// PRESS 1
            @Override
            public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(fRedi,getMostOrderedItem());
            }
        });

        JButton b2 = new JButton("Calculate Revenue");
        b2.setBounds(250,50,200,30);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(fRedi,calculateRevenue());
            }
        });

        JButton b3 = new JButton("Deliver Order");
        b3.setBounds(25,100,200,30);
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(fRedi,viewPendingOrders());
                JFrame delorder = new JFrame("Deliver Order");
                delorder.setSize(600,400);

                JLabel timestamp = new JLabel("Enter the timesamp u want to deliver");
                timestamp.setBounds(25,50,300,30);

                JLabel custID = new JLabel("Enter the id u like to deliver to");
                custID.setBounds(25,100,300,30);

                JTextField timestampText = new JTextField();
                timestampText.setBounds(400,50,100,30);

                JTextField custIDText = new JTextField();
                custIDText.setBounds(400,100,100,30);

                JButton deliver = new JButton("DELIVER");
                deliver.setBounds(100,300,100,30);
                deliver.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String timest = timestampText.getText();
                        int id = Integer.parseInt(custIDText.getText());
                        deliverOrder(timest,id);
                    }
                });

                delorder.add(timestamp);
                delorder.add(timestampText);
                delorder.add(custIDText);
                delorder.add(custID);
                delorder.add(deliver);
                delorder.setLayout(null);
                delorder.setVisible(true);
            }
        });

        JButton b4 = new JButton("Display Menu");
        b4.setBounds(250,100,200,30);
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(fRedi,displayMenu());
            }
        });

        JButton b5 = new JButton("View pending Orders");
        b5.setBounds(25,150,200,30);
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(fRedi,viewPendingOrders());
            }
        });

        JButton b6 = new JButton("Update Item Quantity");
        b6.setBounds(250,150,200,30);

        JButton b7 = new JButton("Add Item");
        b7.setBounds(25,200,200,30);

        JButton b8 = new JButton("Make item unavailable");
        b8.setBounds(250,200,200,30);

        JButton b9 = new JButton("Update Price of an Item");
        b9.setBounds(25,250,200,30);

        JButton b10 = new JButton("Most freq. time for orders");
        b10.setBounds(250,250,200,30);
        b10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(fRedi,mostFreqTime());
            }
        });

        JButton b11 = new JButton("Earnings of last week");
        b11.setBounds(125,300,200,30);
        b11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(fRedi,viewLastWeekIncome());
            }
        });

        JButton b12 = new JButton("EXIT");
        b12.setBounds(25,400,100,30);
        b12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fRedi.dispose();
            }
        });


        fRedi.add(b1);
        fRedi.add(b2);
        fRedi.add(b3);
        fRedi.add(b4);
        fRedi.add(b5);
        fRedi.add(b6);
        fRedi.add(b7);
        fRedi.add(b8);
        fRedi.add(b9);
        fRedi.add(b10);
        fRedi.add(b11);
        fRedi.add(b12);
        fRedi.add(ownerCb);
        fRedi.add(ownerlabel);
        fRedi.setLayout(null);
        fRedi.setVisible(true);

        Scanner sc = new Scanner(System.in);
        System.out.println("To get most ordered item press 1");
        System.out.println("To calculate revenue press 2");
        System.out.println("To deliver order press 3");
        System.out.println("To display menu press 4");
        System.out.println("To view pending orders press 5");
        System.out.println("To update item quantity press 6");
        System.out.println("To add item press 7");
        System.out.println("To make item unavailable press 8");
        System.out.println("To update price of an item press 9");
        System.out.println("To view the most frequent time for orders press 10");
        System.out.println("To view the earnings of last week press 11");
        System.out.println("To exit press 0");
        int k = sc.nextInt();
        sc.nextLine();
        switch(k){
            case 1: getMostOrderedItem();
                rediMethod();
                break;
            case 2: calculateRevenue();
                rediMethod();
                break;
            case 3: viewPendingOrders();
                System.out.println("Enter the timestamp for the order which you want to deliver: ");
                long timest = sc.nextLong();
                sc.nextLine();
                System.out.println("Enter the ordering id whose order you want to deliver: ");
                int id = sc.nextInt();
                sc.nextLine();
                deliverOrder(""+timest,id);
                rediMethod();
                break;
            case 4: displayMenu();
                rediMethod();
                break;
            case 5: viewPendingOrders();
                rediMethod();
                break;
            case 6: displayMenu();
                System.out.println("Enter the item ID for which you want to update the quantity: ");
                int itemid = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter the new quantity: ");
                int quantity = sc.nextInt();
                sc.nextLine();
                this.menu.updateQuantity(itemid, quantity);
                rediMethod();
                break;
            case 7: System.out.println("Enter the item name: ");
                String name = sc.nextLine();
                System.out.println("Enter the item ID: ");
                int itemId = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter quantity available: ");
                int quant = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter the price: ");
                float price = sc.nextFloat();
                sc.nextLine();
                this.menu.addItem(new Item(name, "" + itemId, "" + quant, "" + price));
                rediMethod();
                break;

            case 8: displayMenu();
                System.out.println("Enter item ID which you make unavailable");
                int item = sc.nextInt();
                sc.nextLine();
                this.menu.updateQuantity(item, -1);
                rediMethod();
                break;
            case 9: displayMenu();
                System.out.println("Enter the item ID whose price needs to be updated");
                int itemID = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter the new price");
                float newPrice = sc.nextFloat();
                sc.nextLine();
                this.getMenu().updatePrice(itemID, newPrice);
                rediMethod();
                break;
            case 10: mostFreqTime();
                rediMethod();
                break;
            case 11: viewLastWeekIncome();
                rediMethod();
                break;
            case 0: return;

            default: System.out.println("YOu didn't enter valid option. Try again");
                rediMethod();
        }
    }
}
