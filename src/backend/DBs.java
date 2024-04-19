package backend;

import model.Card;
import model.Product;
import model.Receipt;

import java.util.ArrayList;

import static backend.DB.getInstance;

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
     * @return 상품목록
     */
    public static ArrayList<Product> getProducts(){
        return getInstance().getProducts();
    }


    /**
     * 데이터 베이스에 등록된 카드의 목록을 가져옵니다.
     * @return 카드목록
     */
    public static ArrayList<Card> getCards(){
        return getInstance().getCards();
    }


    /**
     * 데이터 베이스에 등록된 영수증의 목록을 가져옵니다.
     * @return 영수증목록
     */
    public static ArrayList<Receipt> getReceipts(){
        return getInstance().getReceipts();
    }
}
