package backend.db;

import GUI.ListTable;
import backend.dataLoader.DataLoader;
import backend.dataLoader.FileDataLoader;
import backend.dataLoader.MockDataLoader;
import backend.util.ListObserver;
import model.Card;
import model.Product;
import model.Receipt;

import java.util.ArrayList;

/**
 * DB 클래스는 시스템의 상품, 영수증, 카드 목록을 유지하는 데이터베이스 클래스입니다. <br>
 * <p>
 * 기능을 호출하려면 DBs 클래스를 사용해주세요.
 */
public class DB {
    private boolean isInitalized = false; // 데이터베이스 초기화 여부
    private boolean isGUIEnabled = true; // 화면에 표를 띄울지 말지
    private boolean debugMode = false; // 로그 남기기 여부

    private DataLoader dataLoader;
    private ArrayList<Product> products;
    private ArrayList<Card> cards;
    private ArrayList<Receipt> receipts;

    private static final DB instance = new DB(new FileDataLoader());

    private DB(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public static DB getInstance() {
        return instance;
    }

    public ArrayList<Product> getProducts() {
        if (!isInitalized) initDB();
        return products;
    }

    public ArrayList<Card> getCards() {
        if (!isInitalized) initDB();
        return cards;
    }

    public ArrayList<Receipt> getReceipts() {
        if (!isInitalized) initDB();
        return receipts;
    }

    public void setDebugMode(boolean isDebug) {
        debugMode = isDebug;
    }

    public boolean getDebugMode() {
        return debugMode;
    }


    public boolean isIsinitalized() {
        return isInitalized;
    }

    /**
     * 데이터베이스 초기화 과정입니다. 생성자 호출 시 호출됩니다.
     */
    public void initDB() {
        DBs.log("데이터베이스 초기화");

        // 데이터 로더에서 각종 데이터를 불러옵니다.
        products = dataLoader.loadProductData();
        cards = dataLoader.loadCardData();
        receipts = dataLoader.loadReceiptData();

        if (isGUIEnabled) {
            // 리스트 테이블을 생성하고 관찰목록에 등록합니다.
            ListObserver.getInstance().add(new ListTable("상품 재고 목록",products,Product.class));
            ListObserver.getInstance().add(new ListTable("등록된 카드 목록",cards,Card.class));
            ListObserver.getInstance().add(new ListTable("영수증 전체 목록",receipts,Receipt.class));
        } else {
            // GUI가 없시, 데이터만 관찰목록에 추가
            ListObserver.getInstance().add(products, Product.class);
            ListObserver.getInstance().add(cards, Card.class);
            ListObserver.getInstance().add(receipts, Receipt.class);
        }
        isInitalized = true;
    }


    public void updateDB() {
        if(dataLoader instanceof FileDataLoader) {
            ((FileDataLoader) dataLoader).notifyDataSetChanged();
        }
    }
}
