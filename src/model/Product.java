package model;

public class Product {
    private int id;// 상품 ID
    private String name;// 상품 이름
    private String inDate;// 상품 입고일
    private int inventory;// 상품 재고
    private String expiryDate;// 유통 기한


    public Product(int id, String name, String inDate, int inventory, String expiryDate) {
        this.id = id;
        this.name = name;
        this.inDate = inDate;
        this.inventory = inventory;
        this.expiryDate = expiryDate;
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
}
