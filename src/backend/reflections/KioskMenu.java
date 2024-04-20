package backend.reflections;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

public class KioskMenu {
    private static AtomicInteger counter = new AtomicInteger(0);
    private Method menuMethod;
    private String menuTitle;
    private int menuId;

    public KioskMenu(Method menuMethod, String menuTitle) {
        this.menuMethod = menuMethod;
        this.menuTitle = menuTitle;
        this.menuId = counter.incrementAndGet();
    }

    public static void resetId(){
        counter = new AtomicInteger(0);
    }

    public Method getMenuMethod() {
        return menuMethod;
    }

    public void setMenuMethod(Method menuMethod) {
        this.menuMethod = menuMethod;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
