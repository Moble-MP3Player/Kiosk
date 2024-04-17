package model;

public class Product {
    private int id;// 상품 ID
    private String name;// 상품 이름
    private String inDate;// 상품 입고일
    private int inventory;// 상품 재고

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
}
