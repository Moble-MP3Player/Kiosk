package util;

import GUI.ListTable;
import backend.DBs;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ArrayList의 데이터의 변화가 생기면 업데이트를 하기 위해 사용합니다.
 */
public class ListObserver extends Thread {
    private static volatile ListObserver instance;

    private final HashMap<ArrayList<?>, ListTable > uiMap;
    private final HashMap<ArrayList<?>, String[][]> listMap;

    private ListObserver(){
        uiMap = new HashMap<>();
        listMap = new HashMap<>();
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
     * List를 관찰목록에 추가하는 메서드
     * @param arrayList 관찰목록에 추가할 리스트
     * @param listTable 해당 arrayList의 변경이 있을 때, 업데이트할 Ui
     */
    public void addList(ArrayList<?> arrayList, ListTable listTable){
        uiMap.putIfAbsent(arrayList, listTable);
        listMap.putIfAbsent(arrayList, Reflections.convertToArray(arrayList) );
    }

    /**
     * 관찰목록에 등록된 리스트 삭제하는 메서드
     * @param  arrayList 삭제할 리스트 (값의 참조값이 다를 경우 작동하지 않습니다)
     * @return 삭제에 성공했을 때, True를 반환합니다.
     */
    public boolean removeList(ArrayList<?> arrayList){
        // remove()는 삭제 성공 시, 값 | 실패시 null 반환
        uiMap.remove(arrayList);
        return null != listMap.remove(arrayList);// 삭제 성공
    }

    /**
     * 현재 관찰목록의 모든 데이터 관찰후, 데이터 변화 감지되면 해당 ListTable 업데이트
     */
    private void checkDiff() {
        listMap.keySet().forEach(arrayList -> {
            String[][] newList = Reflections.convertToArray(arrayList);
            String[][] oldList = listMap.get(arrayList);

            // 데이터 변화 체크
            if (!areStringArraysEqual(newList, oldList)) {
                DBs.log("%s의 데이터 변경됨\n".formatted(arrayList.get(0).getClass().getName()));
                // 변경된 리스트를 참조하는 테이블을 가져옴.
                ListTable listTable = uiMap.get(arrayList);
                listTable.update(newList); // GUI 업데이트

                //변경 사항 저장
                listMap.put(arrayList,newList);
            }

        });
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

    public static void main(String[] args) {
        DBs.getProducts().get(3).setName("테스트");
    }
}
