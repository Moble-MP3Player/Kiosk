import GUI.ListTable;
import model.Card;
import model.Product;
import model.Receipt;
import util.DataLoader;
import util.MockDataLoader;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 키오스의 기능을 정의하는 클래스입니다.
 * static 클래스로 정의해주시면 됩니다.
 */
public class KioskService {
    public Scanner sc = new Scanner(System.in); // 데이터 입출력에 사용
    boolean debugMode = true; // 디버그 모드 설정

    private final DataLoader dataLoader = new MockDataLoader();
    public ArrayList<Card> cards; // 카드 전체 리스트
    public  ArrayList<Product> products; // 상품 전체 리스트
    public  ArrayList<Receipt> receipts; // 영수증 전체 리스트

    public KioskService(){
        products = dataLoader.loadProductData();
        cards = dataLoader.loadCardData();
        receipts = dataLoader.loadReceiptData();

        if(debugMode) {
            new ListTable(products);
            new ListTable(cards);
            new ListTable(receipts);
        }
    }

    public void test() {

        while(debugMode) {
            System.out.println("""
                    테스트할 항목을 골라주세요.
                    프로그램이 종료되어도 데이터 변화는 반영되지 않습니다.
                    
                    Product 값 변경
                    1) Product의 N번째 값을 변경합니다.
                    """);
            int input = sc.nextInt();
            Product product = products.get(input);
            System.out.println("변경할 문자열 입력 :");
            product.setName(sc.next());
        }
    }
}
