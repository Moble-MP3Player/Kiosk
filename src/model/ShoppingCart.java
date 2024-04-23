package model;

import backend.db.DBs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShoppingCart {
    private Map<String, Integer> shoppingCart;

    public ShoppingCart() {
        shoppingCart = new HashMap<>();
    }

    public Map<String, Integer> getShoppingCart() {
        return shoppingCart;
    }

    // 상품 이름으로 상품 객체 찾기
    private Product findProductByName(String productName) {
        for (Product product : DBs.getProducts()) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null; // 해당 이름을 가진 상품이 없는 경우
    }

    // 장바구니에 추가
    public void addProduct(String productName, int quantity) {
        int currentQuantity = shoppingCart.getOrDefault(productName, 0); // 기존 수량
        int newQuantity = currentQuantity + quantity; // 최종 수량 = 기존 수량 + 입력한 수량

        // 입력한 상품 이름이 유효한지 확인
        Product existingProduct = findProductByName(productName);
        if (existingProduct == null) {
            System.out.println("유효하지 않은 상품입니다.");
            return;
        }

        // 재고 수량과 비교후 추가
        if (newQuantity <= existingProduct.getInventory()) {
            shoppingCart.put(productName, newQuantity);
            System.out.println(existingProduct.getName() + "을(를) " + quantity + "개 장바구니에 담았습니다.");
        } else {
            System.out.println("입력하신 수량이 너무 많습니다.");
            System.out.println(existingProduct.getInventory() - currentQuantity + "개 이하로 담아주세요.");
        }
    }

    // 장바구니에서 상품 삭제
    public void removeProduct(String productName, int quantity) {
        int currentQuantity = shoppingCart.getOrDefault(productName, 0); // 기존 수량
        int newQuantity = currentQuantity - quantity; // 최종 수량 = 기존 수량 - 삭제할 수량

        // 입력한 상품 이름이 유효한지 확인
        Product existingProduct = findProductByName(productName);
        if (existingProduct == null) {
            System.out.println("유효하지 않은 상품입니다.");
            return;
        }

        // 삭제 후 수량을 0과 비교 후 삭제
        if (newQuantity >= 0) {
            shoppingCart.put(productName, newQuantity);
            System.out.println(existingProduct.getName() + "을(를) " + quantity + "개 삭제했습니다.");
        } else {
            System.out.println("입력하신 수량이 너무 많습니다.");
            System.out.println(currentQuantity + "개 이하로 입력해주세요.");
        }
    }

    // 장바구니 초기화
    public void resetShoppingCart() {
        shoppingCart.clear();
    }

    // 장바구니 출력
    public void printShoppingCart() {
        System.out.println("=================== 장바구니 목록 ===================");
        System.out.println(" 상품명        단가          수량           금액      ");

        long totalPrice = 0; // 최종 결제 예상 금액
        int cartQuantity = 0; // 장바구니에 들어간 상품의 수량

        // 상품 수량 가져오기
        for(String productName : shoppingCart.keySet()) {
            int quantity = shoppingCart.get(productName);

            // 상품 이름으로 상품 객체를 조회 후 가격 가져오기
            Product product = findProductByName(productName);
            if (product == null) {
                System.out.println("상품을 찾을 수 없습니다: " + productName);
                continue;
            }

            long productPrice = (long) product.getPrice(); // 상품 단가 가져오기
            long subtotal = productPrice * quantity; // 상품별 금액 = 단가 * 수량
            totalPrice += subtotal;
            cartQuantity += quantity;
            System.out.println(product.getName() + "                   " +
                    productPrice + "                   " +
                    quantity + "                   " +
                    subtotal + "                   ");
        }

        System.out.println();
        System.out.println("  상품: " + cartQuantity + "개  가격: " + totalPrice + "원");
    }


    // 상품 교환
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

        if(c == null)  {
            System.out.println("해당 카드가 존재하지 않습니다.");
            return;
        }

        // 3. 카드 비밀번호 확인
        int countPw = 0; // 비밀번호 입력 시도 횟수 초기화
        final int MAX_PASSWORD_ATTEMPTS = 5; // 최대 시도 횟수

        while(true) {
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
        for(Receipt r : DBs.getReceipts()) {
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
            }

            // 선택한 결제 내역에 대한 교환 작업
//            exchangeReceipt.printReceipt();

//            // 결제 내역 중 교환할 상품 및 수량 선택
//            Product exchangedProduct = exchangeReceipt.productName();
//            int exchangedQuantity = exchangeReceipt.getQuantity();
//
//            for (Product product : DBs.getProducts()) {
//                if (product.getName().equals(exchangeReceipt.getProductName())) {
//                    product.setInventory(product.getInventory() - exchangeReceipt.getCount());
//                    break;
//                }
//            }
//
//            // 장바구니에서 해당 결제 내역 삭제
//            DBs.getReceipts().remove(exchangeReceipt);
//        }
//    } else {
//        System.out.println("해당 카드 번호로 결제한 기록이 없습니다.");
//    }
}}}