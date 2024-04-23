package model;

import backend.db.DBs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart{
    private String productName; // 상품이름
    private long price; // 상품단가
    private int quantity;
    private long productPrice; // 금액
    private int cartQuantity; // 총상품수량
    private long totalPrice; // 총상품금액

    public ShoppingCart(String productName, int price, int quantity , int productPrice, int cartQuantity , int totalPrice){
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.cartQuantity = cartQuantity;
        this.totalPrice = totalPrice;
    }

    void setProductName(String productName) { this.productName = productName; }
    void setPrice(long price) { this.price = price; }
    void setQuantity(int quantity) { this.quantity = quantity; }
    void setProductPrice(long productPrice) { this.productPrice = productPrice; }
    void setCartQuantity(int cartQuantity) { this.cartQuantity = cartQuantity; }
    void setTotalPrice(long totalPrice) { this.totalPrice = totalPrice; }

    String getProductName() { return productName; }
    long getPrice() { return price; }
    int getQuantity() { return quantity; }
    long getProductPrice() { return productPrice; }
    int getCartQuantity() { return cartQuantity; }
    long getTotalPrice() { return totalPrice; }

    // 장바구니 출력
    public void printShoppingCart() {
        System.out.println("=================== 장바구니 목록 ===================");
        System.out.println(" 상품명        단가          수량           금액      ");
        System.out.println(getProductName() + "                   " +
                getPrice() +  "                   "  +
                getQuantity() +  "                   "  +
                getProductPrice() +  "                   ");
        System.out.println();
        System.out.println("  상품: " + getCartQuantity() + "개  가격: " + getTotalPrice() + "원");
    }


    public Map<String, Integer> shoppingCart;
    int newQuantity; // 최종수량 = 기존 수량 +(-) 입력한 수량
    int currentQuantity; // 기존 장바구니 수량

    public ShoppingCart() {
        shoppingCart = new HashMap<>();
    }

    // 장바구니 추가
    public void addProduct(String productName, int quantity) {
        // 장바구니에 이미 존재하는 상품인지 확인
        if (shoppingCart.containsKey(productName)) {
            currentQuantity = shoppingCart.get(productName); // 기존 장바구니 수량 가져오기
            newQuantity = currentQuantity + quantity; // 기존 수량 + 입력한 수량
            // 재고 수량과 비교
            if (newQuantity <= getAvailableQuantity(productName)) {
                shoppingCart.put(productName, newQuantity); // 장바구니 업데이트
                System.out.println(productName + "을(를) " + quantity + "개 담았습니다.");
            } else {
                System.out.println("입력하신 수량이 너무 많습니다.");
                System.out.println(getAvailableQuantity(productName) - currentQuantity + "개 이하로 담아주세요.");
            }
        } else { // 장바구니에 없는 상품일 경우 > 재고 수량과 비교 후 추가 or 오류메시지
            if (quantity <= getAvailableQuantity(productName)) {
                shoppingCart.put(productName, quantity); // 장바구니 업데이트
                System.out.println(productName + "을(를) " + quantity + "개 장바구니에 담았습니다.");
            } else {
                System.out.println("입력하신 수량이 너무 많습니다.");
                System.out.println(getAvailableQuantity(productName) + "개 이하로 담아주세요.");
            }
        }
    }


    // 장바구니 상품 수량 제거
    public void removeProduct(String productName, int quantity) {
        // 장바구니에 입력한 상품이 존재하는지 확인
        if (shoppingCart.containsKey(productName)) {
            currentQuantity = shoppingCart.get(productName); // 기존 장바구니 수량 가져오기
            newQuantity = currentQuantity - quantity; // 최종 수량 = 기존 수량 - 입력한 수량

            // 삭제 후 수량 >= 0 인지 확인
            if (newQuantity >= 0) {
                shoppingCart.put(productName, newQuantity); // 장바구니 업데이트
                System.out.println(productName + "을(를) " + quantity + "개 삭제했습니다.");
                // 재고 수량과 비교
            } else { // 삭제 후 수량이 0보다 작을 경우
                System.out.println("입력하신 수량이 너무 많습니다.");
                System.out.println(currentQuantity + "개 이하로 입력해주세요.");
            }
        } else { // 장바구니에 없는 상품일 경우 오류메시지
            if (quantity <= getAvailableQuantity(productName)) {
                System.out.println("장바구니에 존재하지 않는 상품입니다.");
            }
        }
    }

    // 장바구니 초기화 (내용만 지워짐)
    public void resetShoppingCart() {
        shoppingCart.clear();
    }

    // 상품의 재고 수량 확인
    public int getAvailableQuantity(String productName) {
        ArrayList<Product> arrayList = DBs.getProducts();

        for (Product product : arrayList) {
            if (product.getName().equals(productName)) {
                return product.getInventory();
            }
        }
        return -1; // 입력된 상품명과 일치하는 상품이 없을 경우 -1 반환
    }
}