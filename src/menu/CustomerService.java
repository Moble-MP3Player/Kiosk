package menu;

import backend.annotations.UserMenu;
import backend.db.DBs;
import model.Card;
import model.Product;
import model.Receipt;
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

    public CustomerService() {
        this.shoppingCart = new ShoppingCart();
    }


    @UserMenu("테스트 출력하기")
    public void print() {
        System.out.println("테스트 결과입니다.");
    }

    @UserMenu("상품 결제하기")
    public void parchaseProducts() {
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
    public void payment() {
        ArrayList<Card> cards = DBs.getCards(); //카드 객체들을 가져와서 ArrayList에 저장함.
        ArrayList<Receipt> receipts = DBs.getReceipts();

        Card selectedCard = null; // 선택된 카드 객체를 저장할 변수

        long totalPrice = 13000; //결제 금액(0으로 수정)
        long payBalance = 0; //totalPrice에서 usedPoint를 뺀 값 -> 총 결제 금액에서 포인트를 쓰고 남은 결제 금액

        Scanner sc = new Scanner(System.in);

        // 먼저 장바구니에 담긴 상품들, 총 결제 금액을 출력하는 로직(미구현)
        System.out.println("장바구니의 담긴 상품1");
        System.out.println("장바구니의 담긴 상품2");
        System.out.println("장바구니의 담긴 상품3");

        //결제 여부
        System.out.print("결제 하시겠습니까?(Y/N)");
        String decision = sc.next().toUpperCase(); //사용자가 소문자로 입력해도 대문자로 변환하여 확인함

        if (decision.equals("Y")) { //결제를 진행하는 경우
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
            } else if (PointDecision.equals("N")) {
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
            if (ReceiptDecision.equals("Y")) {
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
        } else if (decision.equals("N")) { //결제를 취소하는 경우
            System.out.println("결제를 취소하였습니다. 메뉴 탭으로 이동합니다.");
            System.out.println();
        } else { // Y나 N 이외의 문자를 입력했을 경우
            System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
            System.out.println();
        }
    }

    // 상품 반품
//    @UserMenu("반품")
//    public void refund() {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("카드 번호를 입력해주세오 : ");
//        int num = sc.nextInt();
//
//        // Card 목록에서 해당 카드에 비밀번호 체크
//        Card c =null;
//        for(Card card : DBs.getCards()){
//            if(card.getCardNum() == num){
//                c = card;
//            }
//        }
//
//        System.out.print("카드 비밀번호를 입력해주세오 : ");
//        int cardPw = sc.nextInt();
//
//        // 카드 비밀번호 확인 절차 <<
//        while (true) {
//            if (cardPw == c.getPassword()) {
//                break;
//            } else {
//                System.out.println("비밀번호가 일치하지 않습니다.");
//            }
//        }
//
//        Receipt receipt = null;
//        // Receipt 목록에서 해당 결제기록 찾아오기 <<
//        //
//        ArrayList<Long> list = new ArrayList<>();
//        for(Receipt receipt1 : DBs.getReceipts()) {
//            if (receipt1.getCardNum() == num) {
//                list.add(receipt1.getTotalPrice());
//                receipt = receipt1;
//            }
//        }
//
//        // 결제내역 클래스의 카드번호와 입력한 카드번호가 같으면 >> 여러개 일수있음. 선택하게 해야함.
//        long selectedAmount = 0;
//        if (list.isEmpty()) {
//            System.out.println("해당 카드 번호로 결제된 기록이 없습니다.");
//        } else {
//            System.out.println("해당 카드 번호로 결제된 금액:");
//            for (int i = 0; i < list.size(); i++) {
//                System.out.println((i + 1) + ". " + list.get(i));
//            }
//
//            System.out.print("선택할 금액의 번호를 입력하세요: ");
//            int choice = Integer.parseInt(sc.next());
//
//            if (choice < 1 || choice > list.size()) {
//                System.out.println("유효하지 않은 선택입니다.");
//            } else {
//                selectedAmount = list.get(choice - 1);
//                System.out.println("선택한 금액: " + selectedAmount);
//            }
//        }
//
//        // 결제내역 클래스의 카드번호의 금액 선택 후
//        for(Receipt receipt : DBs.getReceipts()) {
//            if (receipt.getCardNum() == num && receipt.getTotalPrice() == selectedAmount) {
//                receipt.printReceipt();  // 영수증 재발행
//                c.refund(receipt.getTotalPrice());  // 카드 환불
//                if (receipt.getUsedPoint() != 0) {
//                    c.point += receipt.getUsedPoint();  // 카드 포인트를 사용했다면 포인트 반환
//                }
//                c.point -= (long) (receipt.getTotalPrice() * 0.01);  // 적립한 포인트 차감
//            }
//        }
//
//        // 재고 수량 회수
//        // 상품 클래스의 상품과 영수증 상품이 같으면
//        for (Product product : DBs.getProducts()) {
//            product.setInventory(product.getInventory() - receipt.getCount());
//        }
//        System.out.println("qweqweqeqwe!!!!!!!!!!!!!!!");
//        receipt.printReceipt();
//    }

    public void addCart() {
        Scanner scanner = new Scanner(System.in);
        ShoppingCart wishlist = new ShoppingCart();


        System.out.println("상품과 수량을 입력하세요. 종료하려면 '끝'을 입력하세요.");

        while (true) {

            System.out.print("상품 이름: ");
            String name = scanner.nextLine();

            if (name.equalsIgnoreCase("끝")) {
                break;
            }

            // 상품이 이미 목록에 있는지 확인
            Product existingProduct = null;
            for (Product product : wishlist.getShoppingCart()) {
                if (product.getName().equalsIgnoreCase(name)) {
                    existingProduct = product;
                    break;
                }
            }

            int quantity;
            if (existingProduct != null) {
                while (true) {
                    System.out.print("수량: ");
                    quantity = Integer.parseInt(scanner.nextLine());

                    if (quantity <= existingProduct.getInventory()) {
                        break; // 입력한 수량이 기존 재고보다 작거나 같을 때 루프 탈출
                    } else {
                        System.out.println("입력한 수량이 기존 재고를 초과했습니다. 다시 입력하세요.");
                    }
                }
            } else {
                System.out.print("수량: ");
                quantity = Integer.parseInt(scanner.nextLine());
            }


            wishlist.addProduct(name, quantity);

            System.out.println("상품이 추가되었습니다.\n");
        }

        wishlist.printShoppingCart();
    }
    // 상품 교환
    @UserMenu("상품 교환")
    public void exchange() {
        Scanner sc = new Scanner(System.in);

        // 1. 카드 번호 입력
        System.out.print("카드 번호를 입력해주세오 : ");
        int cardNumber = Integer.parseInt(sc.nextLine());

        // 2. 입력한 카드 존재 유무 확인
        Card c = null;
        for (Card card : DBs.getCards()) {
            if (card.getCardNum() == cardNumber) {
                c = card;
                break;
            }
        }

        if (c == null) {
            System.out.println("해당 카드가 존재하지 않습니다.");
            return;
        }

        // 3. 카드 비밀번호 확인
        int countPw = 0; // 비밀번호 입력 시도 횟수 초기화
        final int MAX_PASSWORD_ATTEMPTS = 5; // 최대 시도 횟수

        while (true) {
            // 카드 비밀번호 입력
            System.out.print("카드 번호를 입력해주세요");
            int cardPw = Integer.parseInt(sc.nextLine());

            // 비밀번호 일치 여부 확인
            if (c.getPassword() == cardPw) {
                break; // 일치하면 반복문 종료
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
                countPw++; // 시도 횟수 증가
            }
        }

        // 비밀번호 5회 이상 잘못 입력 시 프로그램 종료
        if (countPw >= MAX_PASSWORD_ATTEMPTS) {
            System.out.println("비밀번호를 5회 이상 잘못 입력하여 프로그램을 종료합니다.");
            return;
        }

        // 4. 교환할 결제 내역 선택
        System.out.println("카드 결제 내역");

        // 해당 카드로 결제된 모든 내역 가져오기
        ArrayList<Receipt> cardReceipts = new ArrayList<>();
        for (Receipt r : DBs.getReceipts()) {
            if (r.getCardNum() == cardNumber) {
                cardReceipts.add(r);
            }
        }

        // 5. 교환
        if (!cardReceipts.isEmpty()) {
            System.out.println("해당 카드 번호로 결제한 내역입니다.");

            // 결제 내역 출력
            for (int i = 0; i < cardReceipts.size(); i++) {
                System.out.println((i + 1) + ". " + cardReceipts.get(i));
            }

            // 출력된 결제 내역 중 사용자 입력으로 교환할 내역 선택
            System.out.print("교환할 결제 내역의 번호를 입력해주세요. ");
            int exchangeIndex = Integer.parseInt(sc.nextLine()) - 1; // 사용자가 선택한 교환할 결제 내역

            // 입력이 올바르지 않을 경우
            Receipt exchangeReceipt = null;
            if (exchangeIndex < 0 || exchangeIndex >= cardReceipts.size()) {
                System.out.println("유효하지 않은 선택입니다.");
            } else {
                // 올바른 입력일 경우 선택한 내역 출력하여 사용자에게 확인
                exchangeReceipt = cardReceipts.get(exchangeIndex);
                System.out.println("교환할 내역: " + exchangeReceipt);


                inputVailed:
                while (true) {
                    System.out.println("수량 선택");
                    int count = sc.nextInt();

                    for (Product product : DBs.getProducts()) {
                        if (count > exchangeReceipt.getCount()) {
                            System.out.println("구매한 갯수보다 입력 값이 큽니다.");
                            continue inputVailed;
                        }
                        if (product.getName().equals(exchangeReceipt.getProductName())) {

                            if (product.getInventory() == 0) {
                                System.out.println("상품의 재고가 없습니다.");
                                break inputVailed;
                            }

                            if (product.getInventory() >= count) {
                                System.out.println("교환 되셨습니다.");
                                product.setInventory(count);

                                // 영수증 날리기
                                DBs.getReceipts().remove(exchangeReceipt);

                                break inputVailed;

                            } else {
                                System.out.println("입력값이 현재 재고보다 큽니다.");
                                continue inputVailed; // 입력 다시 받기
                            }
                        }
                    }
                }
            }

        }
    }
}

