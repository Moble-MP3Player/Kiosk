package backend.dataLoader;

import model.Card;
import model.Product;
import model.Receipt;

import java.util.ArrayList;

/**
 * 로컬파일의 데이터를 불러와 제공하는 함수를 가진 클래스
 */
public class FileDataLoader implements DataLoader {
    @Override
    public ArrayList<Product> loadProductData() {
        return null;
    }

    @Override
    public ArrayList<Receipt> loadReceiptData() {
        return null;
    }

    @Override
    public ArrayList<Card> loadCardData() {
        return null;
    }
}
