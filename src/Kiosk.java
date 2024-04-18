import model.Card;

import java.util.Scanner;

public class Kiosk {
    KioskService kioskService;


    public Kiosk(KioskService kioskService){
        this.kioskService = kioskService;
    }


    /**
     * Kiosk 초기화 함수로 Kiosk 생성 시 호출되며 파일을 통해서 데이터를 불러옴.
     */
    public void init(){
        kioskService.test();
    }

    /**
     * 키오스크 실행 함수 화면 출력 및 기능 동작 관리
     */
    public void start(){
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
