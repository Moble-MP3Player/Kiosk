import model.Card;
import service.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    public static void main(String[] args) {
        Kiosk koKiosk = new Kiosk();

        koKiosk.init(); // 키오스크 초기화
        koKiosk.start(); // 키오스크 실행
        koKiosk.end(); // 키오스크 종료

        List<Card> cards = DataService.getInstance().loadCardData();

        int size = cards.size();


    }
}