package kiosk;

import backend.db.DBs;
import backend.reflections.KioskMenu;
import backend.reflections.Reflections;
import menu.CustomerService;
import menu.ManagementService;

import java.util.ArrayList;

/**
 * 메뉴 전반 기능을 담당하는 클래스입니다.
 *
 */
public class KioskService {

    private final CustomerService customerService;
    private final ManagementService managementService;

    private ArrayList<KioskMenu> uesrMenus;
    private ArrayList<KioskMenu> managerMenus;


    public KioskService(CustomerService customerService, ManagementService managementService) {
        this.customerService = customerService; // 사용자 메뉴
        this.managementService = managementService; // 관리자 메뉴
    }

    public void initMenu() {
        uesrMenus = Reflections.makeUserMenu(customerService);
        managerMenus = Reflections.makeManagerMenu(managementService);
    }

    /**
     * 해당 메뉴를 출력합니다.
     * @param menus 출력할 메뉴
     */
    private void showMenu(ArrayList<KioskMenu> menus){
        for (KioskMenu kioskMenu : menus) {
            System.out.printf("%d) %s\n",
                    kioskMenu.getMenuId(),
                    kioskMenu.getMenuTitle()
            );
        }
    }

    /**
     * 관리자 메뉴를 보여줍니다.
     */
    public void showManageMenu() {
        showMenu(managerMenus);
    }

    /**
     * 유저 메뉴를 보여줍니다.
     */
    public void showUserMenu(){
        showMenu(uesrMenus);
    }



    /**
     * 매개변수로 입력받은 값으로 해당 메뉴의 함수를 실행시킵니다.
     * @param input 사용자 입력
     */
    public void chooseAndExecuteUserMenu(int input){
        boolean isExecuted = false;

        for (KioskMenu kioskMenu : uesrMenus) {
            if (kioskMenu.getMenuId() == input) {
                try {
                    kioskMenu.getMenuMethod().invoke(customerService, null);
                } catch (Exception e) {
                    DBs.log("reflection 오류 " + e.getCause());
                    throw new RuntimeException("[유저모드 오류]; 서윤오한테 말좀해주세요..." + e.getCause());
                }
                isExecuted = true;
            }
        }
        if(!isExecuted) {
            System.out.println("입력 값이 정확하지 않습니다.");
        }
    }

    public void chooseAndExecuteManagerMenu(int input){
        boolean isExecuted = false;

        for (KioskMenu kioskMenu : managerMenus) {
            DBs.log("kioskMenu id : "+ kioskMenu.getMenuId() + "input : " + input);
            if (kioskMenu.getMenuId() == input) {
                DBs.log(true);
                try {
                    kioskMenu.getMenuMethod().invoke(managementService, null);
                } catch (Exception e) {
                    DBs.log("reflection 오류 " + e.getCause());
                    throw new RuntimeException("[관리자메뉴 오류] 서윤오한테 말좀해주세요..." + e.getCause());
                }
                isExecuted = true;
            }
        }
        if(!isExecuted) {
            System.out.println("입력 값이 정확하지 않습니다.");
        }
    }

}
