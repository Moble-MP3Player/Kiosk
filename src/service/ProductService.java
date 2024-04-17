package service;

import model.Product;

import java.util.ArrayList;

public class ProductService {
    private ArrayList<Product> products;

    public ProductService(ArrayList<Product> products) {
        this.products = products;
    }

    public boolean setQuantity(String name, int count){
        if (countByName(name)<count) return false;

        int left = count;

        for (Product product : products) {
            if(product.getName().equals(name)){
                products.remove(product);
                left--;
            }

            if(left == 0 ){
                break;
            }
        }
        return true;
    }

    /**
     * 상품이 이름으로 해당 상품 재고 확인
     * @param name 상품이름
     * @return 상품의 갯수
     */
    public int getQuantity(String name){
        return countByName(name);
    }

    /**
     * 상품의 이름으로 상품의 총 갯수를 구하는 함수
     * @param name 상품의 이름
     * @return 해당상품의 갯수
     */
    public int countByName(String name){
        int count = 0;

        for (Product product : products) {
            product.getName().equals(name);
            count++;
        }

        return count;
    }
}
