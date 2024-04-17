package model;

public class Card {

    public String cardName; // 카드 사용자 이름
    public int cardNum; // 카드 번호
    public long point; // 적립 포인트 잔액
    public long cardBal; // 카드 잔액
    public int password; // 카드 비밀번호

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

    public void pay(long price) {  // 총 상품 가격만큼 잔액 차감
        cardBal -= price;
    }

    public void refund(long price) {  // 환불 금액 잔액에 추가
        cardBal += price;
    }


}