import GUI.ListTable;
import model.Card;
import model.Product;
import model.Receipt;
import util.DataLoader;

import java.util.ArrayList;
import java.util.Scanner;

public class Kiosk {
    private final DataLoader dataLoader;
    public ArrayList<Card> cards;
    public ArrayList<Product> products;
    public ArrayList<Receipt> receipts;
    public Scanner sc = new Scanner(System.in);

    boolean debugMode = false; // 디버그 모드 설정

    public Kiosk(DataLoader dataLoader, boolean debugMode){
        this.dataLoader = dataLoader;
        this.debugMode = debugMode;
    }

    /**
     * Kiosk 초기화 함수로 Kiosk 생성 시 호출되며 파일을 통해서 데이터를 불러옴.
     */
    public void init(){
        cards = dataLoader.loadCardData();
        products = dataLoader.loadProductData();
        receipts = dataLoader.loadReceiptData();

        if(debugMode){
            new ListTable(cards);
            new ListTable(products);
            new ListTable(receipts);
        }
    }

    /**
     * 키오스크 실행 함수 화면 출력 및 기능 동작 관리
     */
    public void start(){

    }

    /**
     *
     */
    public void test() {
        // 삭제해도돼요
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

    /**
     * 키오스크 종료 함수, 저장되지 않은 변경된 파일을 저장하고 프로그램 종료
     */
    public void end(){

    }


    /**
     * 관리자 모드로 전환하는 함수
     */
    public void enterManagerMode(){

    }





}
