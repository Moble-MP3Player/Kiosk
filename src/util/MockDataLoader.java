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
    }

    @Override
    public List loadRecipeData() {
    }

    @Override
    public List<Card> loadCardData() {
        List<Card> cards = new ArrayList<>();

        return cards;
    }

    @Override
    public List loadBucketData() {
    }
}
