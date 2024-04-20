package kiosk;

import menu.CustomerService;
import menu.ManagementService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Kiosk {
    public static boolean isContinue = true;
    private Scanner sc = new Scanner(System.in);
    KioskService kioskService;

    /**
     * 키오스크를 생성하는데 초기화해야하는 변수들에게 초깃값을 부여합니다.
     */
    public void init() {
        CustomerService customerService = new CustomerService(); // 고객 메뉴 생성
        ManagementService managementService = new ManagementService(); // 관리자 메뉴 생성
        kioskService = new KioskService(customerService, managementService); // 키오스크 서비스 생성
        kioskService.initMenu(); // 메뉴 초기화
    }

    /**
     * 키오스크 실행 함수 화면 출력 및 기능 동작 관리
     */
    public void start() throws InputMismatchException {
        System.out.println("\uD83D\uDE22 메뉴 디자인이 너무 구립니다.. 추천좀해주세요");
        while (isContinue) {
            // 디자인 재능있으신분 도와주세요... 진짜 가독성 개별로에요
            System.out.println("====== [사용자모드] ======");
            System.out.println("메뉴를 선택해주세요.");
            System.out.println("=======================");

            kioskService.showUserMenu();

            System.out.println("=======================");
            System.out.println("0) 종료");
            System.out.println("-1) 관리자모드");
            System.out.println("=======================");
            System.out.println();
            System.out.println();

            int input = 0; // 입력 받기

            try {
                input = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("입력이 정확하지 않습니다.");
                input = -2;// 프로그램 안터지게 아무 숫자나 넣음.
                sc.nextLine(); // 프로그램 안터지게 버퍼 비우기
            }

            if (input == 0) break; // 0입력시 종료
            if (input != -1) kioskService.chooseAndExecuteUserMenu(input); // -1입력시 관리자 모드
            else {
                // 관리자 모드
                while (true) {
                    System.out.println("===== [관리자 모드.] ======");
                    System.out.println("메뉴를 선택해주세요.");
                    System.out.println("=======================");

                    kioskService.showManageMenu();

                    System.out.println("=======================");
                    System.out.println("0) 관리자 모드 종료");
                    System.out.println("=======================");

                    try {
                        input = sc.nextInt();
                    }catch (InputMismatchException e){
                        System.out.println("입력이 정확하지 않습니다.");
                        input = -2; // 프로그램 안터지게 아무 숫자나 넣음.
                        sc.nextLine();
                    }

                    if (input == 0) break; // 0이면 종료

                    kioskService.chooseAndExecuteManagerMenu(input);

                }
            }
        }
    }


//    public void start(){
    // 코드 옮겨놨어요.
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("상품을 결제하려면 아무 키나 눌러 진행해주세요.");
//        String s = sc.next();
//
//        if (s.equals("0000")) {
//            // 비밀번호 일치 시 관리자 모드 실행
//        } else {
//            // 장바구니 이동
//
//            // 상품 결제
//            Card c = new Card();
////            c.pay(price); // 총 상품 가격 = price
//
//
//
//            // 영수증 출력
//        }


//    }


    /**
     * 키오스크 종료 함수, 저장되지 않은 변경된 파일을 저장하고 프로그램 종료
     */
    public void end() {
        System.exit(0);
    }


    /**
     * 관리자 모드로 전환하는 함수
     */
    public void enterManagerMode() {

    }

    /**
     * 메뉴를 보여줍니다.
     */
    public void showMenu() {
    }


}
