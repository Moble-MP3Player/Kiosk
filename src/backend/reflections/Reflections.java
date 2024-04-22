package backend.reflections;

import backend.annotations.ManagerMenu;
import backend.annotations.UserMenu;
import menu.CustomerService;
import menu.ManagementService;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

/**
 * 코드를 동적으로 읽어오는 기능을 가진 클래스입니다.
 * ListTable을 생성하기 위해, arrayList를 직렬화 하거나
 * 키오스크 메뉴들을 읽어와 제공합니다.
 */
public class Reflections {

    /**
     * ArrayList 의 객체를 행과 열로 나누어 String[][]로 반환함.
     *
     * @param arrayList    직렬화 할 ArrayList
     * @param elementClass ArrayList가 대상으로 하는 모델 클래스
     * @return String[][]
     */
    public static String[][] convertToArray(ArrayList<?> arrayList, Class<?> elementClass) {
        // 만약 arrayList가 비어있을 떄, 열의 갯수를 가져올 수 없는 문제가 발생하여, elementClass를 인자로 받음.

        final int ARRAY_SIZE = arrayList.size(); // 행
        final int FIELD_SIZE = countNonStaticField(elementClass.getDeclaredFields()); // 열

        String[][] contents = new String[ARRAY_SIZE][FIELD_SIZE];

        int j = 0; // contents 에 값을 넣어주기 위한 반복자 열을 가르킴

        for (Object obj : arrayList) {
            Class<?> claz = obj.getClass(); //데이터를 가진 클래스
            Field[] fields = claz.getDeclaredFields(); //클래스의 변수 필드를 가져옴.
            String[] memberValues = new String[FIELD_SIZE]; // 해당 열에 담길 값을 담는 String[]

            int i = 0;
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (Modifier.isStatic(field.getModifiers())) continue; // static인지 체크
                    String memberValue = String.valueOf(field.get(obj));
                    memberValues[i++] = memberValue;

                } catch (Exception e) {
                    checkFirstElementType(arrayList, elementClass);
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
        String[] titles = new String[countNonStaticField(fields)];
        int j = 0; // 타이틀 iterator

        for (Field field : fields) {
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            titles[j++] = field.getName();
        }
        return titles;
    }

    /**
     * static 필드가 아닌 값의 갯수를 반환하는 함수
     *
     * @param fields 검사할 필드
     * @return static 이 아닌 변수의 갯수
     */
    public static int countNonStaticField(Field[] fields) {
        int count = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            if (!Modifier.isStatic(field.getModifiers()))
                count++;
        }
        return count;
    }


    private static ArrayList<KioskMenu> makeMenu(Object service, Class<? extends Annotation> menuAnnotation, Function<Annotation, String> getValue) {
        ArrayList<KioskMenu> menus = new ArrayList<>();
        KioskMenu.resetId();
        Method[] methods = service.getClass().getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(menuAnnotation)) continue; // 메뉴 어노테이션 찾기
            Annotation menu = method.getAnnotation(menuAnnotation); // 어노테이션 가져오기
            String menuTitle = getValue.apply(menu); // 메뉴 설명 가져오기
            menus.add(new KioskMenu(method, menuTitle));
        }
        return menus;
    }


    public static ArrayList<KioskMenu> makeUserMenu(CustomerService customerService) {
        return makeMenu(customerService, UserMenu.class, (annotation -> ((UserMenu) annotation).value()));
    }


    public static ArrayList<KioskMenu> makeManagerMenu(ManagementService managementService) {
        return makeMenu(managementService, ManagerMenu.class, (annotation -> ((ManagerMenu) annotation).value()));
    }

    public static void checkFirstElementType(ArrayList<?> arrayList, Class<?> elementClass) {
        if (!arrayList.isEmpty() && !(arrayList.get(0).getClass().equals(elementClass))) {
            throw new RuntimeException("매개변수로 받은 ArrayList와 클래스 객체가 불일치합니다.");
        }
    }

    /***
     * String[]을 받아서 해당 클래스의 맴버 변수의 타입에 맞게 변환하는 함수
     * 파일 IO에서 받아온 값을 동적으로 바꾸기 위함.. 남이 클래스 수정하면 내가 너무 힘들어..
     * @param clazz 대상이 되는 클래스
     * @param datas 맴버변수의 값이 들어가 있는 배열
     */
    public static Object convertDataTypeStringToMemberVarType(Class<?> clazz, String[] datas) {
        Object instance;
        Constructor<?>[] constructors = clazz.getConstructors(); // 생성자 가져오기
        Constructor<?> targetConstructor = null; // 생성자에 매개변수가 가장 많은애..
        int max = -1;

        // 가장 매개변수가 많은 생성자 가져오기
        for (Constructor<?> constructor : constructors) {
            int paramCount = constructor.getParameterCount(); // 변수의 갯수
            if (max < paramCount) {
                targetConstructor = constructor;
                max = paramCount;
            }
        }
        Object[] params = convertParams(targetConstructor.getParameterTypes(), datas);

        try {
            instance = targetConstructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException("cannot Create Instance from " + clazz.getName());
        }
        return instance;
    }

    private static Object[] convertParams(Class<?>[] paramClasses, String[] paramData) {
        if (paramClasses.length != paramData.length) {
            System.out.println(Arrays.toString(paramData));
            System.out.printf("paramClasses : %d , paramData : %d", paramClasses.length, paramData.length);
            throw new RuntimeException("Refection, 생성자의 매개변수의 갯수와 해당 paramData의 길이가 일치하지 않음.");
        }

        Object[] newParams = new Object[paramClasses.length];

        for (int i = 0; i < paramClasses.length; i++) {
            System.out.println(paramClasses[i].toString());
            newParams[i] = switch (paramClasses[i].toString()) {
                case "class java.lang.String" -> paramData[i];
                case "int" -> Integer.parseInt(paramData[i]);
                case "long" -> Long.parseLong(paramData[i]);
                case "double" -> Double.parseDouble(paramData[i]);
                case "float" -> Float.parseFloat(paramData[i]);
                case "class java.time.LocalDateTime" ->
                        LocalDateTime.parse(paramData[i]);
                default -> throw new IllegalStateException("Unexpected value: " + paramClasses[i].toString());
            };
        }
        return newParams;
    }

}


