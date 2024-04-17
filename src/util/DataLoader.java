package util;

import model.Card;
import model.Product;

import java.util.List;

/**
 * 파일을 불러오고 저장하는 클래스
 */
public interface DataLoader {
    public List<Product> loadProductData();
    public List loadRecipeData();
    public List<Card> loadCardData();
    public List loadBucketData();

}
