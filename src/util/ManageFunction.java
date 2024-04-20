package util;

import backend.MockDataLoader;
import model.Product;

import java.util.ArrayList;

public class ManageFunction {

    ArrayList<Product> products=new MockDataLoader().loadProductData();


    public ManageFunction(){

    }

    public static void main(String[] args) {
        ManageFunction manageFunction=new ManageFunction();
        System.out.println(manageFunction.products);
        for(Product product: manageFunction.products){
            System.out.println(product.getId()+product.getName()+product.getPrice()+product.getDate());
        }
    }
}
