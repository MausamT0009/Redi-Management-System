package Redi;

public class Item {
    //all the fields are private in effort to maintain encapsulation
    private final int id;
    private int quantity;
    private float price;
    private final String name;
    public Item(String... item){
        this.name = item[0];
        this.id = Integer.parseInt(item[1]);
        this.quantity = Integer.parseInt(item[2]);
        this.price = Float.parseFloat(item[3]);
    }
    //get and set methods for the private fields
    public int getQuantity(){
        return quantity;
    }
    public int getId(){
        return id;
    }
    public float getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setPrice(float price){
        this.price = price;
    }
    public String toString(){
        String s =  name + "," + id + "," + price + "," + quantity;
        return s;
    }

}