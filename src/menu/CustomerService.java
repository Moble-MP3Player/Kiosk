package menu;

import backend.annotations.UserMenu;
import model.Card;

import java.util.Scanner;

/**
 * 사용자 메뉴 구현하시면됩니다.
 * 메뉴에 보여줄 함수는 @UserMenu("메뉴에 보여줄 텍스트") 형식으로 작성하시면 되고,
 * 메뉴에 안보여줄 함수나, 변수는 자유롭게 쓰시면 됩니다.
 * DBs 클래스 참고하시면 데이터 가져오는 함수 있어요!
 */
public class CustomerService {

    @UserMenu("테스트 출력하기")
    public void print(){
        System.out.println("테스트 결과입니다.");
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
