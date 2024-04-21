package backend.reflections;

import backend.annotations.ManagerMenu;
import backend.annotations.UserMenu;
import backend.db.DBs;
import menu.CustomerService;
import menu.ManagementService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * 코드를 동적으로 읽어오는 기능을 가진 클래스입니다.
 * ListTable을 생성하기 위해, arrayList를 직렬화 하거나
 * 키오스크 메뉴들을 읽어와 제공합니다.
 */
public class Reflections {

    /**
     * ArrayList 의 객체를 행과 열로 나누어 String[][]로 반환함.
     * @param arrayList 직렬화 할 ArrayList
     * @param  elementClass ArrayList가 대상으로 하는 모델 클래스
     * @return String[][]
     */
    public static String[][] convertToArray(ArrayList<?> arrayList,Class<?> elementClass) {
        // 만약 arrayList가 비어있을 떄, 열의 갯수를 가져올 수 없는 문제가 발생하여, elementClass를 인자로 받음.

        final int ARRAY_SIZE = arrayList.size(); // 행
        final int FIELD_SIZE = elementClass.getDeclaredFields().length; // 열

        String[][] contents = new String[ARRAY_SIZE][FIELD_SIZE];

        int j = 0; // contents 에 값을 넣어주기 위한 반복자 열을 가르킴

        for (Object obj : arrayList) {
            Class<?> claz = obj.getClass(); //데이터를 가진 클래스
            Field[] fields = claz.getDeclaredFields(); //클래스의 변수 필드를 가져옴.
            String[] memberValues = new String[FIELD_SIZE]; // 해당 열에 담길 값을 담는 String[]

            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    String memberValue = String.valueOf(fields[i].get(obj));
                    memberValues[i] = memberValue;

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e.getCause());
                }
            }
            contents[j++] = memberValues; // 해당 행에 데이터 적용하고, 다음행로 넘감
        }

        return contents;
    }

    /**
     * 표의 제목을 형성하기 위한 변수들의 이름을 String 배열로 만들어 반환하는 함수.
     *
     * @return String[] 해당클래스의 변수의 이름으로 구성된 배열
     */
    public static String[] getTitles(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] titles = new String[fields.length];
        int i = 0;

        for (Field field : fields) {
            field.setAccessible(true);
            titles[i++] = field.getName();
        }
        return titles;
    }

    private static ArrayList<KioskMenu> makeMenu(Object service, Class<? extends Annotation> menuAnnotation, Function<Annotation,String> getValue) {
        ArrayList<KioskMenu> menus = new ArrayList<>();
        KioskMenu.resetId();
        Method[] methods = service.getClass().getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(menuAnnotation)) continue; // 메뉴 어노테이션 찾기
            Annotation menu = method.getAnnotation(menuAnnotation); // 어노테이션 가져오기
            String menuTitle = getValue.apply(menu); // 메뉴 설명 가져오기
            menus.add(new KioskMenu(method,menuTitle));
        }
        return menus;
    }


    public static ArrayList<KioskMenu> makeUserMenu(CustomerService customerService) {
        return makeMenu(customerService, UserMenu.class,(annotation-> ((UserMenu)annotation).value()));
    }


    public static ArrayList<KioskMenu> makeManagerMenu(ManagementService managementService) {
        return makeMenu(managementService, ManagerMenu.class,(annotation -> ((ManagerMenu)annotation).value()));
    }
}


