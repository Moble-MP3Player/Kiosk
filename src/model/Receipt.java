package model;

import  java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {
    //결제 내역 클래스에서 값을 받아와서 저장해야 할 변수들
    String productName; //상품명(상품 ID로 수정필요)
    int price; //상품 가격
    int count; //상품 수량

    long amountReceived; //받은 금액(사용자의 카드 잔액에서 사용한 금액)
    long usedPoint; //사용한 포인트
    long totalPrice; //총 결제 금액
    String cardName; //카드명
    int cardNum; //카드번호
    int receiptNumber; //영수증 번호
    long accumulate = 0; //적립 금액

    private static int receiptCounter = 0; //영수증 번호를 관리하기 위한 static 변수

    LocalDateTime createDate; //영수증 생성 날짜와 시간

    //영수증 생성자
    public Receipt(String productName, int price, int count, long amountReceived, long usedPoint, long totalPrice, String cardName, int cardNum, long accumulate){
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.amountReceived = amountReceived;
        this.usedPoint = usedPoint;
        this.totalPrice = totalPrice;
        this.cardName = cardName;
        this.cardNum = cardNum;
        this.receiptNumber = ++receiptCounter; //영수증 객체가 생성될 때마다 receiptCounter를 1씩 증가시키고 해당 영수증 객체의 영수증 번호에 저장
        this.accumulate = accumulate;
        this.createDate = LocalDateTime.now(); // 현재 날짜와 시간으로 설정
    }

    // 영수증 생성자 (파일에서 불러오기 위함)


    public Receipt(String productName, int price, int count, long amountReceived, long usedPoint, long totalPrice, String cardName, int cardNum, int receiptNumber, long accumulate, LocalDateTime createDate) {
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.amountReceived = amountReceived;
        this.usedPoint = usedPoint;
        this.totalPrice = totalPrice;
        this.cardName = cardName;
        this.cardNum = cardNum;
        this.receiptNumber = receiptNumber;
        this.accumulate = accumulate;
        this.createDate = createDate;
    }

    //setter
    void setAccumulate(long accumulate){ this.accumulate = accumulate; }

    void setProductName(String productName){
        this.productName = productName;
    }

    void setPrice(int price){
        this.price = price;
    }

    void setCount(int count){
        this.count = count;
    }

    void setAmountReceived(long amountReceived){
        this.amountReceived = amountReceived;
    }

    void setTotalPrice(long TotalPrice){
        this.totalPrice += TotalPrice;
    }

    public void setCardName(String cardName){
        this.cardName = cardName;
    }

    public void setReceiptNumber(int receiptNumber){
        this.receiptNumber = receiptNumber;
    }

    public void setUsedPoint(long usedPoint){
        this.usedPoint = usedPoint;
    }

    public void setCardNum(int cardNum){
        this.cardNum = cardNum;
    }


    //getter
    public long getAccumulate(){ return accumulate; }

    public String getProductName(){
        return productName;
    }

    public int getPrice(){
        return price;
    }

    public int getCount(){
        return count;
    }

    public long getAmountReceived(){
        return amountReceived;
    }

    public long getTotalPrice(){
        return totalPrice;
    }

    public String getCardName(){
        return cardName;
    }

    public int getReceiptNumber(){
        return receiptNumber;
    }

    public long getUsedPoint(){
        return usedPoint;
    }

    public int getCardNum(){
        return cardNum;
    }

    //영수증 생성 일시 가져오는 메서드
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    //영수증 출력 메서드
    public void printReceipt(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd a hh:mm");

        System.out.println("                     영수증");
        System.out.println("===============================================");
        System.out.println("결제 일시 : " + this.getCreateDate().format(formatter) + " 영수증 번호 : " + this.getReceiptNumber());
        System.out.println("-----------------------------------------------");
        System.out.println("상품명                       단가             수량");
        System.out.println("-----------------------------------------------");
        System.out.println(this.getProductName() + "                   " + this.getPrice() + "        " + this.getCount());
        System.out.println("-----------------------------------------------");
        System.out.println("판매 총액 : " + this.getTotalPrice());
        System.out.println("-----------------------------------------------");
        System.out.println("사용 포인트 : " + this.getUsedPoint());
        System.out.println("받은 금액 : " + this.getAmountReceived());
        System.out.println("적립 금액 : " + this.getAccumulate());
        System.out.println("-----------------------------------------------");
        System.out.println("카드 번호 : " + this.getCardNum() + " " + this.getCardName() + "님");
        System.out.println("-----------------------------------------------");
        System.out.println("영수증 발행 일시 : " + this.getCreateDate().format(formatter));
        System.out.println("-----------------------------------------------");
        System.out.println("                  *감사합니다*");

    }
}
