package util;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * ListTable에서 해당클래스의 맴버를 직렬화 하기 위한 클래스입니다.
 */
public class Reflections {

    /**
     * @param arrayList 직렬화 할 ArrayList
     * ArrayList 의 객체를 직렬화하여 String[][]로 반환함.
     * @return String[][]
     */
    public static String[][] convertToArray(ArrayList<?> arrayList){
        if (arrayList.isEmpty()) throw new RuntimeException("ListTable 생성자 오류");

        Object firstElement = arrayList.get(0);
        Class<?> clazz = firstElement.getClass();

        final int ARRAY_SIZE = arrayList.size();
        final int FIELD_SIZE = clazz.getDeclaredFields().length;


        String[][] contents = new String[ARRAY_SIZE][FIELD_SIZE];

        int j = 0; // contents 에 값을 넣어주기 위한 반복자

        for(Object obj : arrayList){
            Class<?> claz = obj.getClass();
            Field[] fields = claz.getDeclaredFields();

            String[] memberValues = new String[FIELD_SIZE];

            for(int i =0; i<fields.length; i++){

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
     * @return String[] 해당클래스의 변수의이름
     */
    public static String[] getTitles(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        String[] titles = new String[fields.length];
        int i = 0;

        for(Field field : fields){
            field.setAccessible(true);
            titles[i++] = field.getName();
        }
        return titles;
    }





    // 테스트코드
//    public static void main(String[] args) {
//        MockDataLoader mockDataLoader = new MockDataLoader();
//        ArrayList<Receipt> products = mockDataLoader.loadReceiptData();
//
//        String[][] titles = Reflections.convertToArray(products);
//
//        for(String[] title : titles){
//            System.out.println(Arrays.toString(title));
//        }
//    }
}
