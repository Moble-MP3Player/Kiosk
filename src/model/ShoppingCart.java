//package model;
//
//import java.util.ArrayList;
//
////장바구니
//public class ShoppingCart {
//
//    private ArrayList<Product> products;
//
//    public ShoppingCart(){
//        products = new ArrayList<>();
//    }
//
//    public void addProduct(String name, int quantity){
//        //이미 장바구니에 존재하는 상품인지 확인
//      for(Product product : products){
//          if(product.getName().equals(name)){
//              //이미 존재하는 상품이면 수량 증가
//              product.setQuantity(product.getQuantity() + quantity);
//              return;
//          }
//      }
//      // 새 상품 장바구니에 추가
//        products.add(new Product(name, quantity));
//    }
//
//    //장바구니 내용 출력
//    public void display(){
//        System.out.println("장바구니 상품:");
//        for(Product product : products){
//            System.out.println(product.getName() + ": " + product.getQuantity());
//        }
//    }
//}
