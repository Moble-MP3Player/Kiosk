package menu;

import backend.annotations.ManagerMenu;
import backend.db.DB;
import backend.db.DBs;
import model.Product;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 관리자 메뉴 구현하시면됩니다.
 * 메뉴에 보여줄 함수는 @ManagerMenu("메뉴에 보여줄 텍스트") 형식으로 작성하시면 되고,
 * 메뉴에 안보여줄 함수나, 변수는 자유롭게 쓰시면 됩니다.
 * DBs 클래스 참고하시면 데이터 가져오는 함수 있어요!
 */
public class ManagementService {
    // 예시 코드
     private ArrayList<Product> arrayList=DBs.getProducts();


    @ManagerMenu("테스트 출력하기")
    public void printTest(){
        System.out.println("매니저 모드입니다.");
    }

   @ManagerMenu("상품 발주하기")
    public void productOrder(){
       for (int i = 0; i < arrayList.size(); i++) {
           Product p=arrayList.get(i);
           if (p.getInventory()==0){
               p.setInventory(10);

           }
       }
   }
   @ManagerMenu("상품 검색")
    public void serchProduct(){
       Scanner sc=new Scanner(System.in);
       String serch=sc.next();
       boolean found=false;
       for (Product product:arrayList) {
           if (serch.equals(product.getName())){
               System.out.println(product.getName()+product.getInventory());
               found=true;
           }
           if (!found){
               System.out.println("해당되는 이름의 상품이 없습니다.");
           }
       }

   }
   @ManagerMenu("상품 수량 선택")
    public void selectProduct(){
        Scanner scanner=new Scanner(System.in);
        String select=scanner.next();
        Product selectedProduct=null;
        for (Product product : arrayList){
            if (select.equals(product.getName())){
                selectedProduct=product;
            }
        }
        int buy= scanner.nextInt();
        if (selectedProduct!=null && buy<selectedProduct.getInventory()){
            selectedProduct.setInventory(selectedProduct.getInventory()-buy);
        }
        else {
            System.out.println("현재 재고량: "+selectedProduct.getInventory());
        }
   }


}

