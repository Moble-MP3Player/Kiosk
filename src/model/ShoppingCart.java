package model;

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

    // 장바구니 출력 메서드
    public void printShoppingCart() {
        System.out.println("=================== 장바구니 목록 ===================");
        System.out.println(" 상품명        단가          수량           금액      ");
        System.out.println(getProductName() + "                   " +
                            getPrice() +  "                   "  +
                            getQuantity() +  "                   "  +
                            getProductPrice() +  "                   ");
        System.out.println("  상품: " + getCartQuantity() + "개  가격: " + getTotalPrice() + "원");
    }


    private HashMap<String, Integer> shoppingCart;

    public ShoppingCart() {
        shoppingCart = new HashMap<>();
    }

    // 장바구니 추가
    //// 재고 수량 내에서만 상품 추가
    //// 이미 존재하는 상품이면 기존 수량에 입력한 수량 더함
    //// 장바구니에 없는 상품이면 항목, 수량 추가
    //// 재고 수량보다 많은 수량 추가시 오류 추가X, 오류 메시지
    public void addProduct(String productName, int quantity) {
        //// 장바구니에 이미 존재하는 상품인지 확인
        if(shoppingCart.containsKey(productName)) {
            int currentQuantity; // 기존 수량
            int newQuantity; // 기존 수량 + 입력한 수량
            currentQuantity = shoppingCart.get(productName); // 기존 장바구니 수량 가져오기

        }
    }

    // 장바구니 상품 제거 메서드
    //// 제품 선택받고 선택한 수량만큼 장바구니의 수량 감소
    //// 장바구니에 들어있는 수량보다 많으면 제거X, 오류 메시지

    // 상품의 재고 수량 확인하는 메서드
    //// 상품 재고 수량 가져오는 것 구현
}