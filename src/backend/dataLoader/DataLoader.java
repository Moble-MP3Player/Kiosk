package backend.dataLoader;

import model.Card;
import model.Product;
import model.Receipt;

import java.util.ArrayList;

/**
 * 파일을 불러오고 저장하는 클래스
 */
public interface DataLoader {
    public ArrayList<Product> loadProductData();
    public ArrayList<Receipt> loadReceiptData();
    public ArrayList<Card> loadCardData();

}
