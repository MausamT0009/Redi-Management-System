package Redi;
import java.io.*;

import java.util.*;

public class Menu {
    //private ArrayList<Item> menu;
    FileWriter menu;
    private final String name;
    FileWriter updateMenu = null;
    public Menu(String redi){
        name = redi + "menu.txt";
        try {
            menu = new FileWriter(redi + "menu.txt",true);
            //menu = new ArrayList<Item>();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void addItem(Item item){
        FileWriter updateMenu = null;
        try{
            updateMenu = new FileWriter(name,true);
            Scanner menuCheck = null;
            try{
                menuCheck = new Scanner(new FileInputStream(name));
                while(menuCheck.hasNextLine()){
                    String s = menuCheck.nextLine();
                    if(s.equals(item.toString())){
                        System.out.println("Item is already in menu");
                        return;
                    }
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }

            updateMenu.write(item.toString());
            updateMenu.write("\n");
            System.out.println("Item is added successfully");
            updateMenu.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public FileWriter getMenu(){
        return menu;
    }
    public void updateQuantity(int itemId, int quantity){//to make item unavailable set quantity to -1
        ArrayList<String> menuTrack = new ArrayList<>();
        Scanner readMenu = null;
        FileWriter update = null;
        try{
            readMenu = new Scanner(new FileInputStream(name));
            while(readMenu.hasNextLine()){
                String s = readMenu.nextLine();
                if(s.split(",")[1].equals("" + itemId)){
                    String[] orderedItem = s.split(",");
                    menuTrack.add(orderedItem[0] + "," + orderedItem[1] + "," + orderedItem[2] + "," + "" + quantity);
                }
                else{
                    menuTrack.add(s);
                }
            }
            update = new FileWriter(name);
            for(int i=0;i<menuTrack.size();i++){
                update.write(menuTrack.get(i) + "\n");
            }
            update.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void updatePrice(int itemId, float price){
        Scanner reader = null;
        FileWriter writer = null;
        ArrayList<String> menu = new ArrayList<>();
        try{
            reader = new Scanner(new FileInputStream(name));
            while(reader.hasNextLine()){
                String s = reader.nextLine();
                if((s.split(","))[1].equals(""+itemId)){
                    String[] p = s.split(",");
                    menu.add(p[0] + "," + p[1] + "," + "" + price + "," + p[3]);
                }
                else{
                    menu.add(s);
                }
            }
            writer = new FileWriter(name);
            for(int i=0; i<menu.size();i++){
                writer.write(menu.get(i) + "\n");
            }
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

}