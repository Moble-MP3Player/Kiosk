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

    /**
     * 데이터 값이 변경되었을 때, 콘솔에 로그를 출력할 지 설정합니다.
     * @param isDebug true 시, 값이 변경되었을때 콘솔에 출력합니다.
     */
    public static void setLogging(boolean isDebug){
        getInstance().setDebugMode(isDebug);
    }

    /**
     * 로깅이 활성화되어있다면 전달된 문자열을 출력합니다.
     */
    public static void log(Object... objs){
        if(objs.length == 0) return;
        if(!getInstance().getDebugMode()) return; // 디버그 모드 비활성화시 종료

        StringBuilder stringBuilder = new StringBuilder("[DBs] ");

        for(Object obj : objs){
            stringBuilder.append(obj);
        }

        System.out.println(stringBuilder.toString());
    }

    /**
     * 데이터베이스가 형성되지 않았다면 생성합니다.
     */
    public static void init(){
        if(!DB.getInstance().isIsinitalized()) DB.getInstance().initDB();
    }
}
