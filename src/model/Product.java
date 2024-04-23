package model;

public class Product {
    private int id;// 상품 ID
    private String name;// 상품 이름
    private String inDate;// 상품 입고일
    private int inventory;// 상품 재고
    private String expiryDate;// 유통 기한
    private double price;//가격

    public Product(int id, String name, String inDate, int inventory, String expiryDate, double price) {
        this.id = id;
        this.name = name;
        this.inDate = inDate;
        this.inventory = inventory;
        this.expiryDate = expiryDate;
        this.price=price;
    }

    public Product(String name, int quantity) {
        this.name = name;
        this.inventory = quantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date){this.inDate=date;}

    public String getDate(){return inDate;}

    public void setInventory(int inventory){this.inventory=inventory;}

    public int getInventory(){return inventory;}

    public void setExpiryDate(String expiryDate){this.expiryDate=expiryDate;}

    public String getExpiryDate(){return  expiryDate;}

    public double getPrice(){return price;}

    public void setPrice(double price) {this.price = price;}

    public int getQuantity() {
        return quantity;
    }
}
