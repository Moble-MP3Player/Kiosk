
package menu;

import backend.annotations.UserMenu;
import backend.db.DBs;
import model.Card;
import model.Product;
import model.Receipt;
import model.Product;
import model.ShoppingCart;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 사용자 메뉴 구현하시면됩니다.
 * 메뉴에 보여줄 함수는 @UserMenu("메뉴에 보여줄 텍스트") 형식으로 작성하시면 되고,
 * 메뉴에 안보여줄 함수나, 변수는 자유롭게 쓰시면 됩니다.
 * DBs 클래스 참고하시면 데이터 가져오는 함수 있어요!
 */
public class CustomerService {
    private ShoppingCart shoppingCart;

    public CustomerService(){
        shoppingCart = new ShoppingCart();
    }

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

    @UserMenu("상품 결제")
    public void payment(){
        ArrayList<Card> cards = DBs.getCards(); //카드 객체들을 가져와서 ArrayList에 저장함.
        ArrayList<Receipt> receipts = DBs.getReceipts();

        Card selectedCard = null; // 선택된 카드 객체를 저장할 변수

        long totalPrice = 13000; //(테스트용) 장바구니에 담긴 상품들 가격 총 합
        long payBalance = 0;

        Scanner sc = new Scanner(System.in);

        // 먼저 장바구니에 담긴 상품들, 총 결제 금액을 출력하는 로직(미구현)
        System.out.println("장바구니의 담긴 상품1");
        System.out.println("장바구니의 담긴 상품2");
        System.out.println("장바구니의 담긴 상품3");

        //결제 여부
        System.out.print("결제 하시겠습니까?(Y/N)");
        String decision = sc.next().toUpperCase(); //사용자가 소문자로 입력해도 대문자로 변환하여 확인함

        if(decision.equals("Y")) { //결제를 진행하는 경우
            boolean cardFound = false; //카드 ArrayList에서 사용자가 입력하는 값과 일치하는 카드가 있는지 확인할 때 사용할 변수

            while (!cardFound) { //일치하는 카드를 찾을 때까지 반복
                System.out.println("========================================");
                System.out.print("결제하실 카드 번호 입력 : ");
                int cardNum = sc.nextInt();

                for (Card card : cards) { //ArrayList를 돌면서 일치하는 카드가 있는지 검사
                    if (card.getCardNum() == cardNum) { //사용자가 입력한 카드번호와 ArrayList에 있는 카드객체들의 카드번호가 일치할 때
                        cardFound = true; //카드 일치 여부를 true로 바꿈

                        System.out.println("카드가 확인되었습니다.");
                        System.out.println();
                        System.out.print("카드 비밀번호 입력 : ");
                        int cardPassword = sc.nextInt();

                        if (card.getPassword() == cardPassword) {
                            System.out.println("비밀번호가 일치합니다. 결제가 진행됩니다.");
                            System.out.println("========================================");
                            selectedCard = card; // 일치하는 카드 객체를 변수에 저장합니다.

                        } else {
                            System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
                            cardFound = false; // 카드 비밀번호가 일치하지 않으므로 다시 반복
                        }

                        break; //일치하는 카드를 찾았으므로 반복문 종료
                    }
                }

                if (!cardFound) {
                    System.out.println("일치하는 카드를 찾을 수 없습니다. 다시 입력해주세요.");
                    //다시 카드번호를 입력받는 부분으로 이동
                }
            }

            //결제 진행
            System.out.println("현재 포인트 : " + selectedCard.getPoint() + "원");
            System.out.print("포인트를 사용하시겠습니까?(Y/N)");
            String PointDecision = sc.next().toUpperCase();
            long usedPoint = 0; //사용한 포인트
            long earnedPoint = 0; //적립금
            long ep1 = 0;
            long remainingPoint = selectedCard.getPoint();

            if (PointDecision.equals("Y")) {
                boolean pointEnough = false;

                while (!pointEnough) {
                    System.out.print("사용할 포인트 입력 : ");
                    usedPoint = sc.nextLong(); //사용자가 사용할 포인트

                    if (selectedCard.getPoint() >= usedPoint) {
                        selectedCard.subPoint(usedPoint); //subPoint 메서드를 사용하면 사용자의 포인트에서 사용한 포인트를 차감한 후에 남은 포인트를 반환함
                        payBalance = totalPrice - usedPoint;
                        System.out.println("포인트를 사용하여 결제합니다.");
                        System.out.println("========================================");

                        if (totalPrice > 0) {
                            System.out.println("남은 결제금액: " + payBalance + "원");
                            selectedCard.pay(payBalance);
                            pointEnough = true; // 결제가 완료되었으므로 반복문 종료
                        }

                        earnedPoint = selectedCard.addPoint(totalPrice);
                        ep1 = (long) (totalPrice * 0.01);
                        System.out.println("결제로 적립된 포인트: " + ep1 + "원");
                        remainingPoint = selectedCard.getPoint();
                        System.out.println("잔여 포인트: " + remainingPoint + "원");

                        pointEnough = true; // 결제가 완료되었으므로 반복문 종료
                    } else {
                        System.out.println("포인트가 부족하여 결제할 수 없습니다. 다시 입력해주세요.");
                    }
                }
            }

            else if (PointDecision.equals("N")) {
                selectedCard.pay(totalPrice); //포인트를 사용하지 않고 해당 카드의 잔액으로 결제
                earnedPoint = selectedCard.addPoint(totalPrice);
                ep1 = (long) (totalPrice * 0.01);
                System.out.println("결제로 적립된 포인트: " + earnedPoint + "원");
                remainingPoint = selectedCard.getPoint();
                System.out.println("잔여 포인트: " + remainingPoint + "원");
                payBalance = totalPrice;
            }//.

            //영수증 생성(수정 필요)
            Receipt receipt = new Receipt(
                    "발렌타인 21Y",    // 상품명
                    13000,            // 상품 가격
                    1,                // 상품 수량
                    payBalance,       // 받은 금액 (사용자의 카드 잔액에서 사용한 금액)
                    usedPoint,        // 사용한 포인트
                    totalPrice,       // 총 결제 금액
                    selectedCard.getCardName(), // 카드명
                    selectedCard.getCardNum(),  // 카드번호
                    ep1, // 결제로 적립된 포인트
                    remainingPoint // 해당 사용자의 잔여 포인트
            );


            //영수증 List에 방금 생성한 영수증 추가
            receipts.add(receipt);

            //영수증 발행여부
            System.out.print("영수증을 발행하시겠습니까?(Y/N)");
            String ReceiptDecision = sc.next().toUpperCase();

            //영수증 발행
            if(ReceiptDecision.equals("Y")) {
                receipt.printReceipt();
                System.out.println();
                System.out.println("이용해 주셔서 감사합니다.");
                System.out.println();
            }
            //영수증 미발행
            else {
                System.out.println();
                System.out.println("이용해 주셔서 감사합니다.");
                System.out.println();
            }
        }
        else if(decision.equals("N")) { //결제를 취소하는 경우
            System.out.println("결제를 취소하였습니다. 메뉴 탭으로 이동합니다.");
            System.out.println();
        }
        else { // Y나 N 이외의 문자를 입력했을 경우
            System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
            System.out.println();
        }
    }

//    // 상품 반품
//    public void refund() {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("카드 번호를 입력해주세오 : ");
//        int card = sc.nextInt();
//
//        Card c = new Card();
//        Product p = new Product();
//        Receipt receipt = new Receipt();
//        // 결제내역 클래스의 카드번호와 입력한 카드번호가 같으면
//        receipt.printReceipt();
//        c.refund(receipt.getTotalPrice());
//
//        if (usedPoint != 0) {
//            c.point += usedPoint;
//        }
//
//        c.point -= (long) (receipt.getTotalPrice() * 0.01);
//
//        // 재고 수량 회수
//        // 상품 클래스의 상품과 영수증 상품이 같으면
//        p.setInventory(receipt.getCount());
//
//    }
//
//    // 상품 장바구니에 담기
//    public void addCart() {
//        Scanner scanner = new Scanner(System.in);
//        ArrayList<Product> productList = new ArrayList<Product>();
//
//        System.out.println("상품과 수량을 입력하세요. 종료하려면 '끝'을 입력하세요.");
//
//        while (true) {
//            System.out.print("상품 이름: ");
//            String name = scanner.nextLine();
//
//            if (name.equalsIgnoreCase("끝")) {
//                break;
//            }
//
//            // 상품이 이미 목록에 있는지 확인
//            Product existingProduct = null;
//            for (Product product : productList) {
//                if (product.getName().equalsIgnoreCase(name)) {
//                    existingProduct = product;
//                    break;
//                }
//            }
//
//            int quantity;
//            if (existingProduct != null) {
//                while (true) {
//                    System.out.print("수량: ");
//                    quantity = Integer.parseInt(scanner.nextLine());
//
//                    if (quantity <= existingProduct.getQuantity()) {
//                        break; // 입력한 수량이 기존 재고보다 작거나 같을 때 루프 탈출
//                    } else {
//                        System.out.println("입력한 수량이 기존 재고를 초과했습니다. 다시 입력하세요.");
//                    }
//                }
//            } else {
//                System.out.print("수량: ");
//                quantity = Integer.parseInt(scanner.nextLine());
//            }
//
//            Product newProduct = new Product(name, quantity);
//            productList.add(newProduct);
//
//            System.out.println("상품이 추가되었습니다.\n");
//        }
//
//        System.out.println("입력된 모든 상품과 수량 :");
//        for (Product product : productList) {
//            System.out.println(product.name + " " + product.quantity + "개");
//        }
//
//
//    }
}
