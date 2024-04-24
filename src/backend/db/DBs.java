package backend.db;

import model.Card;
import model.Product;
import model.Receipt;

import java.util.ArrayList;

import static backend.db.DB.getInstance;

/**
 * DBS 클래스는 함수호출을 위한 클래스입니다.
 * 함수만 참고해서 쓰면됩니다
 *
 * @apiNote 상품 목록 가져오기 -> DBs.getProducts() <br>
 * 카드 목록 가져오기 -> DBs.getCards() <br>
 * 영수증 목록 가져오기 -> DBs.getReceipts() <br>
 */
public class DBs {

    /**
     * 데이터베이스에 등록된 상품의 목록을 가져옵니다.
     *
     * @return 상품목록
     */
    public static ArrayList<Product> getProducts() {
        return getInstance().getProducts();
    }


    /**
     * 데이터 베이스에 등록된 카드의 목록을 가져옵니다.
     *
     * @return 카드목록
     */
    public static ArrayList<Card> getCards() {
        return getInstance().getCards();
    }


    /**
     * 데이터 베이스에 등록된 영수증의 목록을 가져옵니다.
     *
     * @return 영수증목록
     */
    public static ArrayList<Receipt> getReceipts() {
        return getInstance().getReceipts();
    }

    /**
     * 데이터 값이 변경되었을 때, 콘솔에 로그를 출력할 지 설정합니다.
     *
     * @param isDebug true 시, 값이 변경되었을때 콘솔에 출력합니다.
     */
    public static void setLogging(boolean isDebug) {
        getInstance().setDebugMode(isDebug);
    }

    /**
     * 로깅이 활성화되어 있을 경우,
     * 매개변수로 전달된 내용을 출력합니다. 또한 해당 함수(log)가 출력된 위치 또한 출력합니다.
     */
    public static void log(Object... objs) {
        if (objs.length == 0) return;
        if (!getInstance().getDebugMode()) return; // 디버그 모드 비활성화시 종료

        StringBuilder stringBuilder = new StringBuilder("[ Debug ] ");

        for (Object obj : objs) {
            stringBuilder.append(obj);
        }

        stringBuilder.append("\n\t 위치: ")
                .append(Thread.currentThread().getStackTrace()[2]);

        System.out.println(stringBuilder.toString());
    }

    /**
     * 데이터베이스가 형성되지 않았다면 생성합니다.
     */
    public static void init() {
        if (!DB.getInstance().isIsinitalized()) DB.getInstance().initDB();
    }

    public static void update() {
        getInstance().updateDB();
    }

    /**
     * 해당 이름으로 검색해서 가격을 가져옴.
     * @param name 상품 이름
     * @return 상품 가격 double
     */
    public static double getPriceByName(String name){
        Product newProduct;
        for(Product product : DBs.getProducts()){
            if(product.equals(name)){
                return product.getPrice();
            }
        }
        return -1;
    }

    /**
     * 카드 비밀번호로 일치하는 해당 카드 객체를 가져옴.
     */
    public static Card getCardUserFromPassword(int password){
        // Card 목록에서 해당 카드에 비밀번호 체크
        Card c =null;
        for(Card card : DBs.getCards()){
            if(card.getCardNum() == password ){
                c = card;
            }
        }
        return c;
    }
}

