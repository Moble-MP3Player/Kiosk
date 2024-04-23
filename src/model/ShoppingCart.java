package model;

import backend.db.DBs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Product, Integer> shoppingCart;

    public ShoppingCart() {
        shoppingCart = new HashMap<>();
    }

    public Map<Product, Integer> getShoppingCart() {
        return shoppingCart;
    }

    // 장바구니에 추가
    public void addProduct(Product product, int quantity) {
        int currentQuantity = shoppingCart.getOrDefault(product, 0); // 기존 수량
        int newQuantity = currentQuantity + quantity; // 최종 수량 = 기존 수량 + 입력한 수량

        // 재고 수량과 비교후 추가
        if (newQuantity <= product.getInventory()) {
            shoppingCart.put(product, newQuantity);
            System.out.println(product.getName() + "을(를) " + quantity + "개 장바구니에 담았습니다.");
        } else {
            System.out.println("입력하신 수량이 너무 많습니다.");
            System.out.println(product.getInventory() - currentQuantity + "개 이하로 담아주세요.");
        }
    }

    // 장바구니에서 상품 삭제
    public void removeProduct(Product product, int quantity) {
        int currentQuantity = shoppingCart.getOrDefault(product, 0); // 기존 수량
        int newQuantity = currentQuantity - quantity; // 최종 수량 = 기존수량 - 삭제할 수량

        // 삭제 후 수량을 0과 비교 후 삭제
        if (newQuantity >= 0) {
            shoppingCart.put(product, newQuantity);
            System.out.println(product.getName() + "을(를) " + quantity + "개 삭제했습니다.");
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


        for (Product product : shoppingCart.keySet()) {
            int quantity = shoppingCart.get(product); // 상품 수량 가져오기
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
}