package kiosk;

import backend.db.DBs;
import backend.reflections.KioskMenu;
import backend.reflections.Reflections;
import menu.CustomerService;
import menu.ManagementService;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * 메뉴 전반 기능을 담당하는 클래스입니다.
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
     *
     * @param menus 출력할 메뉴
     */
    private void showMenu(ArrayList<KioskMenu> menus) {
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
    public void showUserMenu() {
        showMenu(uesrMenus); // 관리자 메뉴 출력
    }

    /**
     * 해당 메뉴에 해당하는 매개변수로 입력받은 값으로 해당 메뉴의 함수를 실행시킵니다.
     *
     * @param menuList KioskMenu 리스트
     * @param input    사용자 입력
     * @param obj      메뉴가 정의되어 있는 클래스의 객체
     */
    private void chooseAndExecuteMenu(int input, ArrayList<KioskMenu> menuList, Object obj) {
        boolean isExecuted = false;

        for (KioskMenu kioskMenu : menuList) {
            if (kioskMenu.getMenuId() == input) {
                try {
                    kioskMenu.getMenuMethod().invoke(obj, null);
                } catch (Exception e) {
                    DBs.setLogging(true);
                    if (e.getCause().getClass() == InputMismatchException.class) throw new InputMismatchException("""
                            콘솔로 입력받은 데이터의 자료형 불일치
                            Scanner를 통한 입출력의 자료형이 맞지 않습니다.
                            ex) sc.nextInt()를 통해 숫자 입력을 요청했는데 int가 아닌 String을 입력했을 경우

                            해당 메뉴 제목 : %s
                            해당 함수 이름 : %s << 여길 확인하세요.""".formatted(kioskMenu.getMenuTitle(), kioskMenu.getMenuMethod()));


                    DBs.log("reflection 오류 " + e.getCause());
                    throw new RuntimeException("서윤오한테 말좀해주세요..." + e.getCause());

                }
                isExecuted = true;
            }
        }
        if (!isExecuted) {
            System.out.println("입력 값이 정확하지 않습니다.");
        }
    }


    /**
     * 입력 값을 통해, 사용자 모드의 해당 번호에 해당하는 메뉴함수를 실행시킵니다.
     *
     * @param input 사용자 입력
     */
    public void chooseAndExecuteUserMenu(int input) { // 입력받은 input에 해당하는 유저메뉴 함수 출력
        chooseAndExecuteMenu(input, uesrMenus, customerService);
    }

    /**
     * 입력 값을 통해, 관리자 모드의 해당 번호에 해당하는 메뉴함수를 실행시킵니다.
     *
     * @param input 사용자 입력
     */
    public void chooseAndExecuteManagerMenu(int input) {  // 입력받은 input에 해당하는 관리자 메뉴 함수 출력
        chooseAndExecuteMenu(input, managerMenus, managementService);
    }

}
