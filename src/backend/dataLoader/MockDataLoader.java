package backend.dataLoader;

import model.Card;
import model.Product;
import model.Receipt;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 테스트를 위한 임시로 만든 샘플 데이터를 반환하는 기능을 가진 클래스
 */
public class MockDataLoader implements DataLoader {
    @Override
    public ArrayList<Product> loadProductData() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1,"담배","2024-04-17",10,"2034-04-17",4500));
        products.add(new Product(2,"탄산음료","2024-04-17",15,"2026-04-17",1000));
        products.add(new Product(3,"이온음료","2024-04-17",18,"2028-04-17",1500));
        products.add(new Product(4,"라면","2024-04-17",4,"2025-1-23",900));
        products.add(new Product(5,"과자","2024-04-17",14,"2025-03-28",1800));
        products.add(new Product(6,"빵","2024-04-17",5,"2024-04-24",2000));
        products.add(new Product(7,"맥주","2024-04-23",20,"2034-04-23",3900));
        products.add(new Product(8,"소주","2024-04-23",20,"2034-04-23",3000));
        products.add(new Product(9,"냉동만두","2024-04-23",3,"2024-10-23",4000));
        products.add(new Product(10,"육포","2024-04-23",5,"2024-11-22",5000));
        products.add(new Product(11,"우유","2024-04-23",9,"2024-04-31",1500));
        products.add((new Product(12,"햄버거","2024-04-23",5,"2024-04-27",1200)));



        return products;
    }

    @Override
    public ArrayList<Receipt> loadReceiptData() {
        ArrayList<Receipt> receipts = new ArrayList<>();
        LocalDateTime createDate1 = LocalDateTime.parse("2024-04-22T08:42:05");
        LocalDateTime createDate2 = LocalDateTime.parse("2024-04-22T09:02:41");
        LocalDateTime createDate3 = LocalDateTime.parse("2024-04-22T09:02:41");
        LocalDateTime createDate4 = LocalDateTime.parse("2024-04-22T11:51:02");
        LocalDateTime createDate5 = LocalDateTime.parse("2024-04-22T12:57:58");
        LocalDateTime createDate6 = LocalDateTime.parse("2024-04-22T14:21:11");
        LocalDateTime createDate7 = LocalDateTime.parse("2024-04-22T14:30:01");
        LocalDateTime createDate8 = LocalDateTime.parse("2024-04-22T15:47:42");
        LocalDateTime createDate9 = LocalDateTime.parse("2024-04-22T19:02:12");
        LocalDateTime createDate10 = LocalDateTime.parse("2024-04-22T20:27:54");


        receipts.add(new Receipt("담배", 4500, 1, 4455, 45,4500, "홍길동", 1234,10000,45, createDate1));
        receipts.add(new Receipt("과자", 1800, 2, 3564, 46,3600, "임꺽정", 1235,14990,36, createDate2));
        receipts.add(new Receipt("탄산음료", 1000, 1, 1000, 0,1000, "임꺽정", 1235,15000,10, createDate2));
        receipts.add(new Receipt("이온음료", 1500, 3, 4455, 45, 4500, "이영희", 1236, 20000, 45, createDate3));
        receipts.add(new Receipt("라면", 900, 2, 1800, 18, 1800, "박민수", 1237, 5000, 18, createDate4));
        receipts.add(new Receipt("빵", 2000, 1, 1980, 20, 2000, "최지은", 1238, 7500, 20, createDate5));
        receipts.add(new Receipt("맥주", 3900, 1, 3861, 39, 3900, "강하늘", 1239, 8000, 39, createDate6));
        receipts.add(new Receipt("소주", 3000, 2, 5940, 60, 6000, "조미라", 1240, 12000, 60, createDate7));
        receipts.add(new Receipt("냉동만두", 4000, 1, 3960, 40, 4000, "유재석", 1241, 30000, 40, createDate8));
        receipts.add(new Receipt("육포", 5000, 3, 14850, 150, 15000, "하하", 1242, 45000, 150, createDate9));
        receipts.add(new Receipt("우유", 1500, 2, 2880, 120, 3000, "노홍철", 1243, 24910, 30, createDate10));
        receipts.add(new Receipt("담배", 4500, 2, 9000, 0, 9000, "노홍철", 1243, 25000, 90, createDate10));
        return receipts;
    }

    @Override
    public ArrayList<Card> loadCardData() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add( new Card("홍길동", 1234, 10000, 50000, 1111));
        cards.add( new Card("임꺽정", 1235, 15000, 75000, 2222));
        cards.add( new Card("이영희", 1236, 20000, 25000, 3333));
        cards.add( new Card("박민수", 1237, 5000, 55000, 4444));
        cards.add( new Card("최지은", 1238, 7500, 15000, 5555));
        cards.add( new Card("강하늘", 1239, 8000, 20000, 6666));
        cards.add( new Card("조미라", 1240, 12000, 30000, 7777));
        cards.add( new Card("유재석", 1241, 30000, 40000, 8888));
        cards.add( new Card("하하", 1242, 45000, 10000, 9999));
        cards.add( new Card("노홍철", 1243, 25000, 15000, 0000));

        return cards;
    }

}
