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


    public Kiosk(DataLoader dataLoader){
        this.dataLoader = dataLoader;
    }

    /**
     * Kiosk 초기화 함수로 Kiosk 생성 시 호출되며 파일을 통해서 데이터를 불러옴.
     */
    public void init(){
        cards = dataLoader.loadCardData();
        products = dataLoader.loadProductData();
        receipts = dataLoader.loadReceiptData();
    }

    /**
     * 키오스크 실행 함수 화면 출력 및 기능 동작 관리
     */
    public void start(){
        System.out.println(cards);
        System.out.println(products);
        System.out.println(receipts);
    }

    /**
     *
     */
    public void test() {
        System.out.println("""
            테스트할 항목을 골라주세요.
            프로그램이 종료되어도 데이터 변화는 반영되지 않습니다.
            """);
        switch (new Scanner(System.in).nextInt()){

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
