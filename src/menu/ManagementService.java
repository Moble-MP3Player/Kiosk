package menu;

import backend.annotations.ManagerMenu;
import backend.db.DB;
import backend.db.DBs;
import model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
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
        Scanner sc=new Scanner(System.in);
        String date;
        String agree;
       for (int i = 0; i < arrayList.size(); i++) {
           Product p=arrayList.get(i);
           if (p.getInventory()==0){
               System.out.println("재고가 없는 상품은 "+p.getName()+"입니다.");
               System.out.println("상품을 추가 발주하시겠습니까? Y/N");
               agree=sc.next();
               if (agree.equals("Y") || agree.equals("y")){
                   System.out.println("오늘의 날짜를 년 월 일 순으로 입력해주세요 '예:2024-04-23'");
                   date=sc.next();
                   p.setDate(date);
                   p.setInventory(10);
               }
               else if (agree.equals("n") || agree.equals("N")){
                    continue;
               }
               else{
                   System.out.println("잘못된 입력입니다. Y또는 N을 눌러주세요" );
                   break;
               }
           }
       }
   }
   @ManagerMenu("상품 검색")
    public void serchProduct(){
       Scanner sc=new Scanner(System.in);
       System.out.println("검색할 상품을 입력해주세요");
       String serch=sc.next();
       boolean found=false;
       for (Product product:arrayList) {
           if (serch.equals(product.getName())){
               System.out.println("검색하신 상품의 이름: "+product.getName()+"재고: "+product.getInventory());
               found=true;
               break;
           }

       }
       if (!found){
           System.out.println("해당되는 이름의 상품이 없습니다.");
       }

   }
   @ManagerMenu("상품 수량 선택")
    public void selectProduct(){
        Scanner scanner=new Scanner(System.in);

        System.out.println("상품을 선택해주세요");

        String select=scanner.next();

        Product selectedProduct=null;
        for (Product product : arrayList){
            if (select.equals(product.getName())){
                selectedProduct=product;

            }
        }
        if (selectedProduct==null){
            System.out.println("해당되는 상품이 없습니다.");
            return;
        }

        int choiceproduct= scanner.nextInt();
        if (choiceproduct<selectedProduct.getInventory()){
            selectedProduct.setInventory(selectedProduct.getInventory()-choiceproduct);

        }
        else {
            System.out.println("현재 재고량: "+selectedProduct.getInventory());
        }
   }


}

