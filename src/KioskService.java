import backend.db.DBs;
import backend.reflections.KioskMenu;
import backend.reflections.Reflections;
import menu.CustomerService;
import menu.ManagementService;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class KioskService {

    public static boolean isManageMode;
    private CustomerService customerService;
    private ManagementService managementService;

    private ArrayList<KioskMenu> uesrMenus;
    private ArrayList<KioskMenu> managerMenus;

    private Scanner scanner = new Scanner(System.in);

    public KioskService(CustomerService customerService, ManagementService managementService) {
        this.customerService = customerService;
        this.managementService = managementService;
    }

    public void initMenu() {
        uesrMenus = Reflections.makeUserMenu(customerService);
        managerMenus = Reflections.makeManagerMenu(managementService);
        isManageMode = false;
    }

    public void showManageMenu() {
        System.out.println("관리자 메뉴입니다.");
        System.out.println("=======================");

        for (KioskMenu kioskMenu : managerMenus) {
            System.out.printf("%d) %s\n",
                    kioskMenu.getMenuId(),
                    kioskMenu.getMenuTitle()
            );
        }
        System.out.println("=======================");
    }

    public void printUserMenu(){
        for (KioskMenu kioskMenu : uesrMenus) {
            System.out.printf("%d) %s\n",
                    kioskMenu.getMenuId(),
                    kioskMenu.getMenuTitle()
            );
        }
    }

    public void chooseAndExecuteUserMenu(){
        int input = scanner.nextInt();
        boolean isExecuted = false;

        for (KioskMenu kioskMenu : uesrMenus) {
            if (kioskMenu.getMenuId() == input) {
                try {
                    kioskMenu.getMenuMethod().invoke(customerService, null);
                } catch (Exception e) {
                    DBs.log("reflection 오류 " + e.getCause());
                    throw new RuntimeException("메뉴 메서드에서 매개변수가 있으면 안됩니다.");
                }
                isExecuted = true;
            }
        }
        if(!isExecuted) {
            System.out.println("입력 값이 정확하지 않습니다.");
        }
    }

    public void chooseAndExecuteManagerMenu(){
        int input = scanner.nextInt();

        for (KioskMenu kioskMenu : managerMenus) {
            if (kioskMenu.getMenuId() == input) {
                try {
                    kioskMenu.getMenuMethod().invoke(customerService, null);
                } catch (Exception e) {
                    DBs.log("reflection 오류 " + e.getCause());
                    throw new RuntimeException("메뉴 메서드에서 매개변수가 있으면 안됩니다.");
                }
            }
        }
    }

}
