package backend;

import GUI.ListTable;
import model.Card;
import model.Product;
import model.Receipt;
import util.DataLoader;
import util.ListObserver;
import util.MockDataLoader;

import java.util.ArrayList;

/**
 * DB 클래스는 시스템의 상품, 영수증, 카드 목록을 유지하는 데이터베이스 클래스입니다. <br>
 *
 * 기능을 호출하려면 DBs 클래스를 사용해주세요.
 */
public class DB {

    // 싱글톤 구현부 입니다. DB 객체를 하나로 유지하기 위해 사용합니다.
    private static volatile DB instance;

    private DB(DataLoader dataLoader){
        this.dataLoader = dataLoader;
        debugMode = false;
    }

    public static DB getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (DB.class) {
                if (instance == null) { // Second check (with locking)
                    // 다른 쓰레드에서 if문을 통과할수있기떄문에 double Locking을 함.
                    instance = new DB(new MockDataLoader());
                }
            }
        }
        return instance;
    }
    //

    private boolean isinitalized = false;
    private DataLoader dataLoader;
    private ArrayList<Product> products;
    private ArrayList<Card> cards;
    private ArrayList<Receipt> receipts;
    private boolean debugMode;

    public ArrayList<Product> getProducts(){
        if(!isinitalized) initDB();
        return products;
    }

    public ArrayList<Card> getCards(){
        if(!isinitalized) initDB();
        return cards;
    }
    public ArrayList<Receipt> getReceipts(){
        if(!isinitalized) initDB();
        return receipts;
    }

    public void setDebugMode(boolean isDebug){
        debugMode = isDebug;
    }

    public boolean getDebugMode() {
        return debugMode;
    }

    public boolean isIsinitalized(){
        return isinitalized;
    }

    /**
     * 데이터베이스 초기화 과정입니다. 생성자 호출 시 호출됩니다.
     */
    public void initDB(){
        DBs.log("데이터베이스 초기화");
        
        // 데이터 로더에서 각종 데이터를 불러옵니다.
        products = dataLoader.loadProductData();
        cards = dataLoader.loadCardData();
        receipts = dataLoader.loadReceiptData();

        // 리스트 테이블을 생성합니다. (JFrame GUI)
        ArrayList<ListTable> arrayList = new ArrayList<>();
        arrayList.add(new ListTable(products));
        arrayList.add(new ListTable(cards));
        arrayList.add(new ListTable(receipts));

        // 데이터 변화를 관찰하기 위해 관찰목록에 등록합니다.
        for (ListTable listTable : arrayList) {
            ListObserver.getInstance().addList(listTable);
        }

        isinitalized = true;
    }


}
