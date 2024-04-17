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
//        products.add(new Product())
        return products;
    }

    @Override
    public List loadRecipeData() {
        return null;
    }

    @Override
    public List<Card> loadCardData() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card());
        return cards;
    }

    @Override
    public List loadBucketData() {
        return null;
    }
}
