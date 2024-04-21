package GUI;

import backend.reflections.Reflections;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ListTable {

    private static final Point locations = new Point(10,10); // 표화면을 배치할 위치
    private JFrame frame;
    private JTable jtable; // 화면에 보여줄 표
    private String[][] data; // 표의 각 셀에 담기는 데이터 배열
    private String[] titles; // 표의 속성들의 이름을 담는 배열
    private final ArrayList<?> arrayList; // 표를 구성하는 arrayList
    private final Class<?> listElementClass; // ArrayList 가 담고있는 클래스

    public ListTable(String title,ArrayList<?> arrayList, Class<?> listElementClass) {
        this.arrayList = arrayList;
        this.listElementClass = listElementClass;
        initJFrame(title);
    }

    /**
     * 화면을 생성하고 성공 시, true 를 반환합니다. 실패 시 false 를 반환합니다.
     * @return True : 생성 성공 <br> False : 생성 실패
     */
    private boolean initJFrame(String title) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setTitle(title);

        titles = Reflections.getTitles(listElementClass); // GUI의 제목설정
        data = Reflections.convertToArray(arrayList, listElementClass);

        updateFrameLocation();

        DefaultTableModel defaultTableModel = new DefaultTableModel(data, titles) {
            // cell 편집 불가하게
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }; // 표에 담기는 데이터를 설정하면서 해당 표를 조작하지 못하게 함.
        jtable = new JTable(defaultTableModel);

        JScrollPane scrollPane = new JScrollPane(jtable); // 표가 스크롤이 가능하게함.
        frame.add(scrollPane); // 프레임에 부여
        frame.setVisible(true); // 화면 보여주기
        return true; // 정상종료
    }


    /**
     * 표를 최신화하여 다시 보여주는 기능을 가집니다.
     * 데이터가 변경될 경우 호출됩니다. (호출 위치 ListObserver.java)
     */
    public void update(String[][] newList) {

        DefaultTableModel newModel = new DefaultTableModel(newList, titles) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtable.setModel(newModel);
    }

    @Override
    public String toString() {
        return frame.getTitle() ;
    }

    public ArrayList<?> getList() {
        return this.arrayList;
    }

    public Class<?> getListElementClazz() {
        return this.listElementClass;
    }

    /**
     * GUI의 배치 장소를 설정합니다.
     * 10,10 부터 행마다 3개씩 배치합니다.
     */
    private void updateFrameLocation(){
        frame.setLocation(locations);
        locations.x = locations.x + 500;
        if(locations.x>1500){ // x가 1500이 넘어갈 경우
            locations.x = 10; // x를 10으로 수정
            locations.y = locations.y + 310; // y축 310 이동(ui 화면 크기 + 10)
        }
    }

    /**
     * GUI가 보일지 말지 설정하는 함수
     */
    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}
