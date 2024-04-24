package model;

import backend.db.DBs;
import model.Card;
import model.Product;
import model.Receipt;


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
    public Product findProductByName(String productName) {
        for (Product product : DBs.getProducts()) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null; // 해당 이름을 가진 상품이 없는 경우
    }

    // 장바구니에 추가
    public void addProduct(String productName, int quantity) {
        Scanner sc = new Scanner(System.in);
        int currentQuantity = shoppingCart.getOrDefault(productName, 0); // 기존 수량
        int newQuantity = currentQuantity + quantity; // 최종 수량 = 기존 수량 + 입력한 수량

        // 입력한 상품 이름이 유효한지 확인
        Product existingProduct = findProductByName(productName);
        while (true) {
            if (existingProduct == null) {
                System.out.println("유효하지 않은 상품입니다. 다시 입력해주세요.");
                System.out.print("상품 이름 : ");
                productName = sc.next();
            } else break;
        }

        // 재고 수량과 비교후 추가
        while (true){
            if (newQuantity > existingProduct.getInventory()) {System.out.println("입력하신 수량이 재고를 초과하였습니다.");
                System.out.println(existingProduct.getInventory() - currentQuantity + "개 이하로 담아주세요.");
                System.out.print("수량: ");
                quantity = sc.nextInt();
                newQuantity = currentQuantity + quantity;
            } else break;
        }
        shoppingCart.put(productName, newQuantity);
    }

    // 장바구니에서 상품 삭제
    public void removeProduct(String productName, int quantity) {
        int currentQuantity = shoppingCart.getOrDefault(productName, 0); // 기존 수량
        int newQuantity = currentQuantity - quantity; // 최종 수량 = 기존 수량 - 삭제할 수량
        Scanner sc = new Scanner(System.in);

        // 입력한 상품 이름이 유효한지 확인
        Product existingProduct = findProductByName(productName);
        while(true) {
            if (existingProduct == null) {
                System.out.println("유효하지 않은 상품입니다.");
                System.out.print("상품 이름 : ");
                productName = sc.next();
            } else break;
        }

        // 삭제 후 수량을 0과 비교 후 삭제
        while (true) {
            if (newQuantity < 0) {
                System.out.println("입력하신 수량이 너무 많습니다.");
                System.out.println(currentQuantity + "개 이하로 입력해주세요.");
                System.out.print("수량: ");
                quantity = sc.nextInt();
                newQuantity = currentQuantity - quantity;
            } else break;
        }

        System.out.println(existingProduct.getName() + "을(를) " + quantity + "개 삭제했습니다.");
        shoppingCart.put(productName, newQuantity);
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
        for (String productName : shoppingCart.keySet()) {
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
}
