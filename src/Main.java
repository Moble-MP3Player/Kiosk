import backend.DBs;

public class Main {

    public static void main(String[] args) {
        DBs.init();
        Kiosk koKiosk = new Kiosk(new KioskService());
        koKiosk.init(); // 키오스크 초기화
//        koKiosk.start(); // 키오스크 실행
        koKiosk.end(); // 키오스크 종료
    }



}