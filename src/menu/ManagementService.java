package menu;

import backend.annotations.ManagerMenu;
import backend.db.DB;
import backend.db.DBs;
import model.Product;
import model.Receipt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        boolean found=false;
       for (Product p : arrayList) {
           if (p.getInventory() == 0) {
               found=true;
               System.out.println("재고가 없는 상품은 " + p.getName() + "입니다.");
               System.out.println("상품을 추가 발주하시겠습니까? Y/N");
               agree = sc.next();
               if (agree.equals("Y") || agree.equals("y")) {
                   System.out.println("오늘의 날짜를 년 월 일 순으로 입력해주세요 '예:2024-04-23'");
                   date = sc.next();
                   p.setDate(date);
                   p.setInventory(10);
               } else if (agree.equals("n") || agree.equals("N")) {
                   continue;
               } else {
                   System.out.println("잘못된 입력입니다. Y또는 N을 눌러주세요");
                   break;
               }
           }
       }
       if(!found){
           System.out.println("재고가 없는 상품이 없습니다. 처음으로 돌아갑니다.");
       }

   }
   @ManagerMenu("상품 검색 및 재고 확인")
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
   @ManagerMenu("모든 상품 확인")
    public void checkAllProducts(){
       for (Product product: arrayList){
           if(product.getName()!=null){
               System.out.println("상품 이름: "+product.getName()+" ,개수: "+product.getInventory()+" ,유통기한: "+product.getExpiryDate()+" ,입고일: "+product.getDate());
           }
       }
   }
   @ManagerMenu("마감")
    public void closing(){
        ArrayList<Receipt>receipts= DBs.getReceipts();
        LocalDate today=LocalDate.now();

       // DateTimeFormatter로 포맷 정의
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

       // 날짜/시간 포매팅
       String formattedDate = today.format(formatter);

        int totalMoney=0;
       for (Receipt receipt:receipts ){
            LocalDate receiptDate=receipt.getCreateDate().toLocalDate();
            if(today.isEqual(receiptDate)){
                totalMoney+= (int) receipt.getTotalPrice();
                System.out.println("판매된 상품: "+receipt.getProductName()+" 개수: "+receipt.getCount());
            }
        }
       System.out.println("오늘 날짜: "+today+" 총 판매금액: "+totalMoney);
   }

}

