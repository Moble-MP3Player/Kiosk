package util;

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

    public ObservableEntity(ListTable GUI, ArrayList<?> list, String[][] data) {
        this.id = counter.getAndIncrement();
        this.GUI = GUI;
        this.list = list;
        this.data = data;
    }

    public ObservableEntity(ArrayList<?> list, String[][] data) {
        this.id = counter.getAndIncrement();
        this.list = list;
        this.data = data;
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

    public void setGUI(ListTable GUI) {
        this.GUI = GUI;
    }

    public ArrayList<?> getList() {
        return list;
    }

    public void setList(ArrayList<?> list) {
        this.list = list;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }
}
