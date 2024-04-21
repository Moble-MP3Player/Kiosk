package backend.util;

import GUI.ListTable;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 관찰 대상에 추가할 엔티티 클래스입니다.
 * 단순히 데이터를 저장하는 모델입니다.
 * 프로젝트 모델 패키지에 넣으면 햇갈릴까봐 따로 뻈어요.
 */
public class ObservableEntity {

    private static AtomicInteger counter = new AtomicInteger();
    private int id;
    private ListTable GUI;
    private ArrayList<?> list;
    private String[][] data;
    private Class<?> clazz;

    public ObservableEntity(ListTable GUI, ArrayList<?> list,Class<?> clazz, String[][] data) {
        this.id = counter.getAndIncrement();
        this.GUI = GUI;
        this.list = list;
        this.data = data;
        this.clazz = clazz;
    }

    public ObservableEntity(ArrayList<?> list, String[][] data, Class<?> clazz) {
        this.id = counter.getAndIncrement();
        this.list = list;
        this.data = data;
        this.clazz = clazz;
        this.GUI = null;
    }

    /*
     * getter 목록
     *  id, GUI, list, data
     *
     * setter 목록
     *  GUI, list, data
     */
    public int getId() {
        return id;
    }

    public ListTable getGUI() {
        return GUI;
    }

    public Class<?> getClazz() {return clazz;}

    public ArrayList<?> getList() {
        return list;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    public boolean hasGUI(){
        return GUI != null;
    }

}
