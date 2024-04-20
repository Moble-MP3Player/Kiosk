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

    private final CustomerService customerService; // 유저 메뉴가 정의된 클래스
    private final ManagementService managementService; // 관리자 메뉴가 정의된 클래스

    private ArrayList<KioskMenu> uesrMenus; // @UserMenu 사용자 메뉴 목록(함수와 기능의 이름들)
    private ArrayList<KioskMenu> managerMenus; // @ManagerMenu 관리자 메뉴 목록(함수와 기능의 이름들)


    public KioskService(CustomerService customerService, ManagementService managementService) {
        this.customerService = customerService; // 사용자 메뉴 클래스 초기화
        this.managementService = managementService; // 관리자 메뉴 클래스 초기화
    }

    public void initMenu() {
        uesrMenus = Reflections.makeUserMenu(customerService); // 동적으로 코드를 읽어 @UserMenu가 정의된 함수를 목록으로 가져옴.
        managerMenus = Reflections.makeManagerMenu(managementService); // 동적으로 코드를 읽어 @ManagerMenu 정의된 함수를 목록으로 가져옴.
    }

    /**
     * 해당 메뉴를 출력합니다.
     * @param menus 출력할 메뉴
     */
    private void showMenu(ArrayList<KioskMenu> menus){
        for (KioskMenu kioskMenu : menus) { // 메뉴 목록마다
            System.out.printf("%d) %s\n", // id) "menuTitle" 형식으로 출력합니다.
                    kioskMenu.getMenuId(),
                    kioskMenu.getMenuTitle()
            );
        }
    }

    /**
     * 관리자 메뉴를 보여줍니다.
     */
    public void showManageMenu() {
        showMenu(managerMenus); // 유저 메뉴 출력
    }

    /**
     * 유저 메뉴를 보여줍니다.
     */
    public void showUserMenu(){
        showMenu(uesrMenus); // 관리자 메뉴 출력
    }



    /**
     * 매개변수로 입력받은 값으로 해당 메뉴의 함수를 실행시킵니다.
     * @param input 사용자 입력
     */
    public void chooseAndExecuteUserMenu(int input){ // 입력받은 input에 해당하는 유저메뉴 함수 출력
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

    public void chooseAndExecuteManagerMenu(int input){  // 입력받은 input에 해당하는 관리자 메뉴 함수 출력
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
