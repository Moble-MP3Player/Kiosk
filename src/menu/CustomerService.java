package menu;

import backend.annotations.UserMenu;
import backend.db.DBs;
import model.Card;
import model.Product;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerService {

    @UserMenu("테스트 출력하기")
    public void print(){
        System.out.println("테스트 결과입니다.");
    }
    
    @UserMenu("상품 전체 보기")
    public void makeshop(){
        ArrayList<Product> arrayList = DBs.getProducts(); // 상품 전체 가져오기
        for(int i = 0; i<arrayList.size(); i++){ //상품 리스트을 순회
            Product product = arrayList.get(i);
            System.out.println(product.getName()); // 사품이름을 출력
        }
    }

    @UserMenu("상품 결제하기")
    public void parchaseProducts(){
        Scanner sc = new Scanner(System.in);

        System.out.print("상품을 결제하려면 아무 키나 눌러 진행해주세요.");
        String s = sc.next();

        if (s.equals("0000")) {
            // 비밀번호 일치 시 관리자 모드 실행
        } else {
            // 장바구니 이동

            // 상품 결제
            Card c = new Card();
//            c.pay(price); // 총 상품 가격 = price



            // 영수증 출력
        }
    }

}
