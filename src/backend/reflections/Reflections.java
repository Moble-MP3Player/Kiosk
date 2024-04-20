package backend.reflections;

import backend.annotations.ManagerMenu;
import backend.annotations.UserMenu;
import menu.CustomerService;
import menu.ManagementService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 코드를 동적으로 읽어오는 기능을 가진 클래스입니다.
 * ListTable을 생성하기 위해, arrayList를 직렬화 하거나
 * 키오스크 메뉴들을 읽어와 제공합니다.
 */
public class Reflections {

    /**
     * @param arrayList 직렬화 할 ArrayList
     *                  ArrayList 의 객체를 직렬화하여 String[][]로 반환함.
     * @return String[][]
     */
    public static String[][] convertToArray(ArrayList<?> arrayList) {
        if (arrayList.isEmpty()) throw new RuntimeException("ListTable 생성자 오류");

        Object firstElement = arrayList.get(0);
        Class<?> clazz = firstElement.getClass();

        final int ARRAY_SIZE = arrayList.size();
        final int FIELD_SIZE = clazz.getDeclaredFields().length;


        String[][] contents = new String[ARRAY_SIZE][FIELD_SIZE];

        int j = 0; // contents 에 값을 넣어주기 위한 반복자

        for (Object obj : arrayList) {
            Class<?> claz = obj.getClass();
            Field[] fields = claz.getDeclaredFields();

            String[] memberValues = new String[FIELD_SIZE];

            for (int i = 0; i < fields.length; i++) {

                fields[i].setAccessible(true);
                try {
                    String memberValue = String.valueOf(fields[i].get(obj));
                    memberValues[i] = memberValue;

                } catch (Exception e) {
                    throw new RuntimeException(e.getCause());
                }
            }
            contents[j++] = memberValues;
        }

        return contents;
    }

    /**
     * 전달된 Class 직렬화하여 맴버변수의 이름을 가져와 String[]로 반환
     *
     * @return String[] 해당클래스의 변수의이름
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


    public static ArrayList<KioskMenu> makeUserMenu(CustomerService customerService) {
        ArrayList<KioskMenu> menus = new ArrayList<>();
        KioskMenu.resetId();
        Method[] methods = customerService.getClass().getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(UserMenu.class)) continue; // 메뉴 어노테이션 찾기
            UserMenu menu = method.getAnnotation(UserMenu.class); // 어노테이션 가져오기
            String menuTitle = menu.value(); // 메뉴 설명 가져오기
            menus.add(new KioskMenu(method,menuTitle));
        }
        return menus;
    }


    public static ArrayList<KioskMenu> makeManagerMenu(ManagementService managementService) {
        ArrayList<KioskMenu> menus = new ArrayList<>();
        KioskMenu.resetId();
        Method[] methods = managementService.getClass().getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(ManagerMenu.class)) continue; // 메뉴 어노테이션 찾기
            ManagerMenu menu = method.getAnnotation(ManagerMenu.class); // 어노테이션 가져오기
            String menuTitle = menu.value(); // 메뉴 설명 가져오기
            menus.add(new KioskMenu(method,menuTitle));
        }
        return menus;
    }
}


