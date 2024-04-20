package backend.reflections;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 키오스크 메뉴를 담는 데이터 모델입니다.
 *
 */
public class KioskMenu {
    private static AtomicInteger counter = new AtomicInteger(0); // Id를 순차적으로 부여하기 위한 counter
    private Method menuMethod; // 실행 시킬 함수
    private String menuTitle; // 해당 메뉴의 제목
    private int menuId; // 실행을 위해 부여한 ID

    public KioskMenu(Method menuMethod, String menuTitle) {
        this.menuMethod = menuMethod;
        this.menuTitle = menuTitle;
        this.menuId = counter.incrementAndGet();
    }

    /**
     * Id를 부여하는 Counter를 초기화합니다.
     */
    public static void resetId(){
        counter = new AtomicInteger(0);
    }

    public Method getMenuMethod() {
        return menuMethod;
    }


    public String getMenuTitle() {
        return menuTitle;
    }


    public int getMenuId() {
        return menuId;
    }

}
