package model;

public class Product {
    private int id;
    private String name;
    private String inDate;

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
}
