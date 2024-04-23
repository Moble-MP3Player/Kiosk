package model;


import java.util.ArrayList;

//장바구니
public class ShoppingCart {
    int quantity; //수량

    private ArrayList<Product> products;

    public ShoppingCart(){
        products = new ArrayList<>();
    }

    public void addProduct(String name, int quantity){
        int cartquantity = 0; //장바구니에 들어있는 기존 수량
        //이미 장바구니에 존재하는 상품인지 확인
      for(Product product : products){
          if(product.getName().equals(name)){
              //이미 존재하는 상품이면 수량 증가
              cartquantity += quantity;

              //현 재고에서 장바구니에 넣는 수량만큼 항목의 수량 삭제
              ///////
              return;
          }
      }
      // 새 상품 장바구니에 추가
//        products.add(new Product(name, quantity));
    }

    //장바구니 내용 출력
    public void display(){
        System.out.println("=== 장바구니 목록 ===");
        for(Product product : products){
            System.out.println(product.getName() + ": " + product.getQuantity());
        }
    }
}
