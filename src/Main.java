import util.MockDataLoader;

public class Main {
    public static void main(String[] args) {
        Kiosk koKiosk = new Kiosk(new MockDataLoader(), false);

        koKiosk.init(); // 키오스크 초기화
//        koKiosk.start(); // 키오스크 실행
        koKiosk.test();
        koKiosk.end(); // 키오스크 종료

    }
}