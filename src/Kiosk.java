public class Kiosk {
    KioskService kioskService;


    public Kiosk(KioskService kioskService){
        this.kioskService = kioskService;
    }


    /**
     * Kiosk 초기화 함수로 Kiosk 생성 시 호출되며 파일을 통해서 데이터를 불러옴.
     */
    public void init(){
        kioskService.test();
    }

    /**
     * 키오스크 실행 함수 화면 출력 및 기능 동작 관리
     */
    public void start(){

    }

    /**
     * 키오스크 종료 함수, 저장되지 않은 변경된 파일을 저장하고 프로그램 종료
     */
    public void end(){

    }


    /**
     * 관리자 모드로 전환하는 함수
     */
    public void enterManagerMode(){

    }





}
