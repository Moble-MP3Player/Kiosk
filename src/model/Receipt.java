package model;



public class Receipt {
    //결제 내역 클래스에서 값을 받아와서 저장해야 할 변수들
    String productName; //상품명
    int price; //상품 가격
    int count; //상품 수량

    String paymentMethod; //결제 방법
    String paymentDate; //결제 일시
    int amountReceived; //받은 금액
    int discount; //할인
    int totalPrice; //총 결제 금액
    int change; //거스름돈
    String cardName; //카드명
    int cardNum; //카드번호
    String receiptDate; //영수증 발행 일시
    int receiptNumber = 0; //영수증 번호


    //영수증 생성자
    public Receipt(String productName, int price, int count, String paymentMethod, String paymentDate, int amountReceived, int discount, int totalPrice, int change, String cardName, int cardNum, String receiptDate, int receiptNumber){
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.amountReceived = amountReceived;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.change = change;
        this.cardName = cardName;
        this.cardNum = cardNum;
        this.receiptDate = receiptDate;
        this.receiptNumber = receiptNumber;
    }

    void setProductName(String productName){
        this.productName = productName;
    }

    void setPrice(int price){
        this.price = price;
    }

    void setCount(int count){
        this.count = count;
    }

    void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
    }

    void setPaymentDate(String paymentDate){
        this.paymentDate = paymentDate;
    }

    void setAmountReceived(int amountReceived){
        this.amountReceived = amountReceived;
    }

    //총합 금액 메서드
    void setTotalPrice(int price){
        this.totalPrice += price; //매개변수로 전달받은 상품의 금액을 총합 금액(totalPrice)에 더함
    }

    //거스름 돈 메서드
    void setChange(int totalPrice, int amountReceived){
        this.change = amountReceived - totalPrice; //매개변수로 전달받은 받은 돈에서 총합 금액을 빼서 거스름 돈(change)에 저장
    }

    void setCardName(String cardName){
        this.cardName = cardName;
    }

    void setReceiptNumber(int receiptNumber){
        this.receiptNumber = receiptNumber;
    }

    void setReceiptDate(String receiptDate){
        this.receiptDate = receiptDate;
    }

    void setDiscount(int discount){
        this.discount = discount;
    }
    
    void setCardNum(int cardNum){
        this.cardNum = cardNum;
    }

    String getProductName(){
        return productName;
    }

    int getPrice(){
        return price;
    }

    int getCount(){
        return count;
    }

    String getPaymentMethod(){
        return paymentMethod;
    }

    String getPaymentDate(){
        return paymentDate;
    }

    int getAmountReceived(){
        return amountReceived;
    }

    int getTotalPrice(){
        return totalPrice;
    }

    int getChange(){
        return change;
    }

    String getCardName(){
        return cardName;
    }

    int getReceiptNumber(){
        return receiptNumber;
    }

    String getReceiptDate(){
        return receiptDate;
    }

    int getDiscount(){
        return discount;
    }
    
    int getCardNum(){
        return cardNum;
    }

    //영수증 출력 메서드
    void printReceipt(){
        System.out.println("               영수증");
        System.out.println("===================================");
        System.out.println("결제 일시 : " + this.getPaymentDate() + " 영수증 번호 : " + this.getReceiptNumber());
        System.out.println("-----------------------------------");
        System.out.println("상품명                 단가       수량");
        System.out.println("-----------------------------------");
        System.out.println(this.getProductName() + "                   " + this.getPrice() + "        " + this.getCount());
        System.out.println("-----------------------------------");
        System.out.println("판매 총액 : " + this.getTotalPrice());
        System.out.println("-----------------------------------");
        System.out.println("받은 금액 : " + this.getAmountReceived());
        System.out.println("할인 금액 : " + this.getDiscount());
        System.out.println("거스름 돈 : " + this.getChange());
        System.out.println("카드 번호 : " + this.getCardNum() + " " + this.getCardName() + "님");
        System.out.println("-----------------------------------");
        System.out.println("영수증 발행 일시 : " + this.getReceiptDate());
        System.out.println("-----------------------------------");
        System.out.println("            *감사합니다*");

    }


}
