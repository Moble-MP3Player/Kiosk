package menu;

import backend.annotations.UserMenu;
import backend.db.DBs;
import backend.util.Strings;
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
    private ShoppingCart cart;
    private Scanner sc = new Scanner(System.in);

    public CustomerService() {
        this.cart = new ShoppingCart();
    }


    @UserMenu("상품 확인")
    public void print() {
        ArrayList<Product>products=DBs.getProducts();
        System.out.println("==================================================================");
        for (Product product : products){
            if(product.getName()!=null){
                System.out.println("| 상품 이름: "+product.getName()+product.getEmoji()+" | 개수: "+product.getInventory()+" | 유통기한: "+product.getExpiryDate()+" |");
                System.out.println("==================================================================");
            }
        }
    }

    @UserMenu("상품 결제")
    public void payment() {
        ArrayList<Card> cards = DBs.getCards(); //카드 객체들을 가져옴
        ArrayList<Receipt> receipts = DBs.getReceipts(); //영수증 객체를 가져옴
        ArrayList<Product>products=DBs.getProducts();

        Card selectedCard = null; // 선택된 카드 객체를 저장할 변수

        long totalPrice = 0; //결제 금액
        long payBalance = 0; //totalPrice에서 usedPoint를 뺀 값 -> 총 결제 금액에서 포인트를 쓰고 남은 결제 금액

        Scanner sc = new Scanner(System.in);
        
        for(String productName : cart.getShoppingCart().keySet()){ //상품들의 총 가격의 합을 계산하는 반복문
            totalPrice += DBs.getPriceByName(productName) * cart.getShoppingCart().get(productName);
        }
        
        if(cart.getShoppingCart().size() == 0){ //장바구니에 상품을 담지 않고 결제를 실행할 때
            System.out.println("장바구니에 담긴 상품이 없습니다.");
            return;
        }
        
        // 먼저 장바구니에 담긴 상품들, 총 결제 금액을 출력
        cart.printShoppingCart();

        //결제 여부
        System.out.print("결제 하시겠습니까?(Y/N)");
        String decision = sc.next().toUpperCase(); //사용자가 소문자로 입력해도 대문자로 변환하여 확인함

        if(decision.equals("Y")) { //결제를 진행하는 경우
            boolean cardFound = false; //카드 ArrayList에서 사용자가 입력하는 값과 일치하는 카드가 있는지 확인할 때 사용할 변수

            while (!cardFound) { //일치하는 카드를 찾을 때까지 반복
                System.out.println("========================================");
                System.out.print("결제하실 카드 번호 입력 : ");
                int cardNum = sc.nextInt(); //사용자가 입력한 카드 번호를 저장

                for (Card card : cards) { //ArrayList를 돌면서 일치하는 카드가 있는지 검사
                    if (card.getCardNum() == cardNum) { //사용자가 입력한 카드번호와 ArrayList에 있는 카드객체들의 카드번호가 일치할 때
                        cardFound = true; //카드 일치 여부를 true로 바꿈

                        System.out.println("카드가 확인되었습니다.");
                        System.out.println();
                        System.out.print("카드 비밀번호 입력 : ");
                        int cardPassword = sc.nextInt(); //사용자가 입력한 카드 비밀번호 저장

                        if (card.getPassword() == cardPassword) { //사용자가 입력한 비밀번호와 ArrayList에 있는 카드객체들의 비밀번호가 일치할 때
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

            if (selectedCard.getCardBal() + selectedCard.getPoint() < totalPrice) {
                System.out.println("잔액이 부족하여 결제할 수 없습니다. 메뉴창으로 이동합니다.");
                System.out.println("결제 금액 : " + totalPrice + "원");
                System.out.println("현재 잔액 : " + selectedCard.getCardBal() + "원, 포인트 : " + selectedCard.getPoint() + "원");
                return;
            } else {
                //결제 진행
                System.out.println("결제 금액 : " + totalPrice + "원");
                System.out.println("현재 잔액 : " + selectedCard.getCardBal() + "원");
                System.out.println("현재 포인트 : " + selectedCard.getPoint() + "원");
                System.out.print("포인트를 사용하시겠습니까?(Y/N)");
                String PointDecision = sc.next().toUpperCase(); //포인트 사용여부를 입력 받음
                long usedPoint = 0; //사용한 포인트
                long earnedPoint = 0; //적립금
                long ep1 = 0; //결제 후 적립 될 포인트
                long remainingPoint = selectedCard.getPoint(); //사용자 카드의 현재 포인트

                if (PointDecision.equals("Y")) { //포인트를 사용할 때
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
                    System.out.println("결제로 적립된 포인트: " + ep1 + "원");
                    remainingPoint = selectedCard.getPoint();
                    System.out.println("잔여 포인트: " + remainingPoint + "원");
                    payBalance = totalPrice;///
                }

                for (String productName : cart.getShoppingCart().keySet()) {
                    int productPrice = (int) DBs.getPriceByName(productName);
                    int quantity = cart.getShoppingCart().get(productName); // 상품 수량 가져오기

                    // 해당 상품을 장바구니에서 찾아서 재고를 감소시킴
                    for (Product product : products) {
                        if (product.getName().equalsIgnoreCase(productName)) { // 상품 이름이 일치하는 경우
                            int currentInventory = product.getInventory(); // 현재 재고
                            if (currentInventory >= quantity) { // 결제 수량이 재고보다 적은 경우에만 감소
                                product.setInventory(currentInventory - quantity); // 상품 재고 감소
                            } else {
                                System.out.println("상품 " + productName + "의 재고가 부족하여 결제가 취소됩니다.");
                                return; // 재고 부족으로 결제 취소
                            }
                            break;
                        }
                    }
                    //영수증 생성
                    Receipt receipt = new Receipt(
                            productName, // 상품명
                            productPrice, // 상품 가격(단가)
                            cart.getShoppingCart().get(productName), // 상품 수량
                            usedPoint >= productPrice ? 0 : (int)(productPrice * cart.getShoppingCart().get(productName)) - usedPoint, // 받은 금액 (사용자의 카드 잔액에서 사용한 금액)
                            usedPoint > productPrice ? productPrice : usedPoint < 0 ? 0 : usedPoint, // 사용한 포인트
                            (long) DBs.getPriceByName(productName) * cart.getShoppingCart().get(productName),       // 총 결제 금액
                            selectedCard.getCardName(), // 카드명
                            selectedCard.getCardNum(),  // 카드번호
                            ep1, // 결제로 적립된 포인트
                            remainingPoint // 해당 사용자의 잔여 포인트
                    );
                    if(usedPoint - productPrice <= 0){
                        usedPoint = 0;
                    }
                    else {
                        usedPoint -= productPrice;
                    }

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
                }

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

    @UserMenu("반품 하기")
    public void refund() {
        // 1. 카드로 로그인
        System.out.print("카드 번호를 입력해주세요 : ");
        int num = sc.nextInt();
        Card c = DBs.getCardUserFromPassword(num);


        int chance = 5; // 카드 시도횟수

        System.out.println(); // 줄바꿈 그냥

        // 카드 비밀번호 확인 절차 <<
        while (chance > 0) {
            System.out.print("카드 비밀번호를 입력해주세요 : ");

            int cardPwInput = sc.nextInt();

            if (cardPwInput == c.getPassword()) {
                break;
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
                System.out.println("남은 시도 횟수 : " + chance--);
            }
        }

        // Receipt 목록에서 해당 결제기록 찾아오기 <<
        // 2. 해당 카드로 결제한 영수증 찾기
        ArrayList<Long> list = new ArrayList<>();
        for (Receipt receipt1 : DBs.getReceipts()) {
            if (receipt1.getCardNum() != num) continue; // 카드 번호 불일치 넘김.
            if (receipt1.getTotalPrice() == 0) continue; // 금액이 0원(환불영수증일시 넘김)
            list.add(receipt1.getTotalPrice()); // 영수증 목록에 추가
        }

        // 영수증 찾기
        long selectedAmount = 0;
        if (list.isEmpty()) { // 결제 기록이 존재하지 않을때
            System.out.println("해당 카드 번호로 결제된 기록이 없습니다.");
        } else {
            System.out.println("해당 카드 번호로 결제된 금액:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i));
            }

            System.out.print("선택할 금액의 번호를 입력하세요: ");
            int choice = Integer.parseInt(sc.next());

            if (choice < 1 || choice > list.size()) {
                System.out.println("유효하지 않은 선택입니다.");
            } else {
                selectedAmount = list.get(choice - 1);
                System.out.println("선택한 금액: " + selectedAmount);
            }
        }

        Receipt toRefundReceipt = null;
        // 결제내역 클래스의 카드번호의 금액 선택 후
        for (Receipt receipt : DBs.getReceipts()) {
            // 카드번호가 불일치하는 영수증은 넘김.
            if (receipt.getCardNum() != num) continue;
            // 선택한 금액이 아닌 영수증도 넘김.
            if (receipt.getTotalPrice() != selectedAmount) continue;
            toRefundReceipt = receipt;
        }

        if(toRefundReceipt == null ){
            // 발생할 경우가 없음. 영수증 선택하래서 했는데, 그 영수증이 없을 수 있나.
            System.out.println("해당 영수증은 존재하지 않습니다.");
            return;
        }
        // 정상적으로 금액을 선택했을 경우,
        // 1 영수증 재발행 ?
        Receipt newRefundReceipt = new Receipt(
                toRefundReceipt.getProductName()+"(반품)",
                toRefundReceipt.getPrice(),
                toRefundReceipt.getCount(),
                0, // 받은 금액
                0, // 사용한 포인트
                0, // 총 결제 금액
                toRefundReceipt.getCardName(), // 해당카드 이름
                toRefundReceipt.getCardNum(), // 카드 번호
                toRefundReceipt.getResidual(), // 해당카드 잔여포인트
                0); // 적립된 포인트

        DBs.getReceipts().add(newRefundReceipt);  // 영수증 재발행
        System.out.println("환불 영수증이 발행되었습니다.");
        System.out.println("\t 영수증 번호 :" + newRefundReceipt.getReceiptNumber());
        // 2. 돈 환불
        c.refund(toRefundReceipt.getTotalPrice());  // 카드 환불

        // 3. 해당 결제에서 사용했던 포인트 반환 ( 0포인트를 사용했다면 어차피 0만 반환됨)
        c.point += toRefundReceipt.getUsedPoint();  // 카드 포인트를 사용했다면 포인트 반환

        // 4. 해당 결제에서 적립했었던 포인트 차감.
        c.point -= (long) (toRefundReceipt.getTotalPrice() * 0.01);  // 적립한 포인트 차감

        // 5. 완료 출력
        System.out.println("\t해당 결제 번호 : " + toRefundReceipt.getReceiptNumber());
        System.out.println();

        System.out.println("환불이 정상적으로 처리되었습니다");

        // 6. N초 뒤 영수증 출력
        try {
            Strings.delayAndPrint(3, "초 뒤 환불 영수증을 출력합니다.", newRefundReceipt::printReceipt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


   // 상품 담기
    @UserMenu("장바구니 담기")
    public void addCart() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("상품과 수량을 입력하세요. 종료하려면 '끝'을 입력하세요.");

        while (true) {

            System.out.print("상품 이름: ");
            String name = scanner.next();

            if (name.equalsIgnoreCase("끝")) {
                scanner.nextLine();
                break;
            }

            System.out.print("수량: ");
            int quantity = scanner.nextInt();

            cart.addProduct(name, quantity);
            System.out.println("상품이 추가되었습니다.\n");
        }

        System.out.println("상품을 삭제하려면 d키, 상품 결제는 c키를 눌러 진행해주세요.");

        String key = sc.next();

        while (true) {
            if (key.equals("d")) {
                System.out.print("삭제할 상품을 입력해주세요 : ");
                String dname = sc.next();

                if (dname.equals("c")) {
                    break;
                }

                System.out.print("수량: ");
                int dquantity = scanner.nextInt();
                cart.removeProduct(dname, dquantity);
            } else if (key.equals("c")) {
                break;
            } else break;
        }


        payment();
    }

    // 상품 교환
    @UserMenu("상품 교환")
    public void exchange() {
        Scanner sc = new Scanner(System.in);

        // 1. 카드 번호 입력
        System.out.print("카드 번호를 입력해주세요 : ");
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
            System.out.print("카드 번호를 입력해주세요 : ");
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
                if(r.getTotalPrice() != 0)
                cardReceipts.add(r);
            }
        }
        if(cardReceipts.isEmpty()){
            System.out.println("해당 카드로 결제한 내역이 존재하지 않습니다.");
            System.out.println("메뉴로 돌아갑니다. ");
            return;
        }

        // 5. 교환
            System.out.println("해당 카드 번호로 결제한 내역입니다.");

            // 결제 내역 출력
            for (int i = 0; i < cardReceipts.size(); i++) {
                Receipt toChangeReceipt = cardReceipts.get(i);
                System.out.println((i + 1) + ". " + toChangeReceipt.getProductName() + "구매 수량 :" + toChangeReceipt.getCount());
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
                System.out.println("교환할 상품: " + exchangeReceipt.getProductName());


                inputVailed:
                while (true) {
                    System.out.println("수량 선택");
                    int count = sc.nextInt();

                    for (Product product : DBs.getProducts()) {
                        if (count > exchangeReceipt.getCount()) {
                            System.out.println("구매한 갯수보다 입력 값이 큽니다.");
                            continue inputVailed;
                        }
                        if (count == 0 ){
                            System.out.println("교환을 취소합니다.");
                            continue ;
                        }

                        if (product.getName().equals(exchangeReceipt.getProductName())) {

                            if (product.getInventory() == 0) {
                                System.out.println("상품의 재고가 없습니다.");
                                break inputVailed;
                            }

                            if (product.getInventory() >= count) {
                                System.out.println("교환 되셨습니다.");
                                product.setInventory(product.getInventory()-count);

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

