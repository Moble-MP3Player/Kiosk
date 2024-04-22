package backend.util;

import GUI.ListTable;
import backend.db.DBs;
import backend.reflections.Reflections;

import java.util.ArrayList;

/**
 * ArrayList의 데이터의 변화가 생기면 업데이트를 하기 위해 사용합니다.
 */
public class ListObserver extends Thread {
    private static volatile ListObserver instance;

    /**
     * 관찰하는 데이터들의 리스트
     */
    private final ArrayList<ObservableEntity> observableEntities;

    private ListObserver(){
        observableEntities = new ArrayList<>();
        this.start();
    }

    /**
     * 싱글톤의 인스턴스를 가져옵니다.
     * 해당 ListObserver의 쓰래드가 하나로 유지하기위해 사용합니다.
     * @return ListObserver 객체
     */
    public static ListObserver getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (ListObserver.class) {
                if (instance == null) instance = new ListObserver();
            }
        }
        return instance;
    }

    /**
     * 옵저버의 관찰목록에 추가하는 메서드
     * @param listTable 리스트를 담고 있는 UI
     */
    public void add(ListTable listTable){
        ArrayList<?> arrayList = listTable.getList();
        Class<?> clazz = listTable.getListElementClazz();

        String[][] data = Reflections.convertToArray(arrayList,clazz);
        ObservableEntity entity = new ObservableEntity(listTable,arrayList,clazz,data);
        observableEntities.add(entity);
    }

    /**
     * 옵저버의 관찰목록에 ArrayList만 추가하는 메서드
     * @param arrayList 관찰목록에 추가할 리스트
     * @param clazz 해당 리스크가 담고있는 객체 클래스
     */
    public void add(ArrayList<?> arrayList, Class<?> clazz){
        String[][] data = Reflections.convertToArray(arrayList,clazz);
        ObservableEntity entity = new ObservableEntity(arrayList,data,clazz);
        observableEntities.add(entity);
    }
    

    /**
     * 현재 관찰목록의 모든 데이터 관찰후, 데이터 변화 감지되면 해당 ListTable 업데이트
     */
    private void checkDiff() {

        for(ObservableEntity entity : observableEntities){
            String[][] newData = Reflections.convertToArray(entity.getList(), entity.getClazz());
            String[][] oldData = entity.getData();

            // 데이터 변화 체크
            if (!areStringArraysEqual(newData, oldData)) {
                DBs.log(entity.getId() + "의 데이터 변경 : ");
                // 변경된 리스트를 참조하는 테이블을 가져옴.
                entity.setData(newData);
                DBs.update();

                if(entity.hasGUI()) entity.getGUI().update(newData);
            }

        }
    }


    /**
     * 2차원 배열 String[][]의 값이 다른 지 확인하는 함수입니다.
     * 값이 같을 경우 true, 다를경우 false를 반환합니다.
     * @param arr1 비교할 String 2차원 배열
     * @param arr2 비교할 String 2차원 배열2
     */
    private static boolean areStringArraysEqual(String[][] arr1, String[][] arr2){
        if (arr1 == null || arr2 == null) {
            return arr1 == arr2;
        }

        if (arr1.length != arr2.length) {
            return false; // 길이가 다르면 바로 false 반환
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i].length != arr2[i].length) {
                return false; // 내부 배열의 길이가 다르면 false 반환
            }

            for (int j = 0; j < arr1[i].length; j++) {
                if(arr1[i][j] == null) arr1[i][j] = "";
                if(arr2[i][j] == null) arr2[i][j] = "";
                if (!arr1[i][j].equals(arr2[i][j])) {
                    return false; // 요소 비교, 다르면 false 반환
                }
            }
        }

        return true; // 모든 요소가 같으면 true 반환
    }


    public void run(){
        while (!Thread.interrupted()) {
            // 반복 작업 수행
            try {
                sleep(1000);
                checkDiff();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
