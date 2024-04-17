package util;

import model.Card;
import model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;

public class MockDataLoader implements DataLoader{
    @Override
    public List loadProductData() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1,"담배","2024-04-17",10,"2034-04-17",4500));
        products.add(new Product(2,"탄산음료","2024-04-17",15,"2026-04-17",1000));
        products.add(new Product(3,"이온음료","2024-04-17",18,"2028-04-17",1500));
        products.add(new Product(4,"라면","2024-04-17",4,"2025-1-23",900));
        products.add(new Product(5,"과자","2024-04-17",14,"2025-03-28",1800));
        products.add(new Product(6,"빵","2024-04-17",5,"2024-04-24",2000));

        return products;
    }

    @Override
    public List loadRecipeData() {
        return null;
    }

    @Override
    public List<Card> loadCardData() {
        List<Card> cards = new ArrayList<>();

        return cards;
    }

    @Override
    public List loadBucketData() {
        return null;
    }
}
