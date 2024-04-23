package model;

public class Card {

    private String cardName; // 카드 사용자 이름
    private int cardNum; // 카드 번호
    public long point; // 적립 포인트 잔액
    private long cardBal; // 카드 잔액
    private int password; // 카드 비밀번호

    public Card(){

    }
    public Card(String cardName, int cardNum, long point, long cardBal, int password) {
        this.cardName = cardName;
        this.cardNum = cardNum;
        this.point = point;
        this.cardBal = cardBal;
        this.password = password;
    }

    public String getCardName() { return cardName; }

    public int getCardNum() { return cardNum; }

    public long getPoint() { return point; }

    public long getCardBal() { return cardBal; }

    public int getPassword() { return password; }


    public void setCardName(String cardName) { this.cardName = cardName; }

    public void setCardNum(int cardNum) { this.cardNum = cardNum; }

    public void setCardBal(long cardBal) { this.cardBal = cardBal; }

    public void setPassword(int password) { this.password = password; }

    public void setPoint(long point) { this.point = point; }

    public long addPoint(long price) {   // 전달받은 가격의 1% 만큼 포인트 적립
        point += (long) (price * (0.01));
        return point;
    }

    public long subPoint(long point) {  // 사용한 포인트만큼 차감
        this.point -= point;
        return point;
    }
    
    public void pay(long price) {  // 상품 결제하기
        if( cardBal - price >= 0) {
            cardBal -= price;
            System.out.println("결제가 완료되었습니다.");
        } else System.out.println("잔액이 부족합니다.");
    }

    public void refund(long price) {  // 환불 금액 잔액에 추가
        cardBal += price;
    }  // 상품 환불받기
    
    
}