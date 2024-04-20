package GUI;

import backend.db.DBs;
import backend.reflections.Reflections;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ListTable {

    private static Point locations; // 표화면을 배치할 위치
    private JFrame frame;
    private JTable jtable; // 화면에 보여줄 표
    private String[][] data; // 표의 각 셀에 담기는 데이터 배열
    private String[] titles; // 표의 속성들의 이름을 담는 배열
    private final ArrayList<?> arrayList; // 표를 구성하는 arrayList

    public ListTable(ArrayList<?> arrayList) {
        this.arrayList = arrayList;
        initJFrame();
    }

    /**
     * 화면을 생성하고 성공 시, true 를 반환합니다. 실패 시 false 를 반환합니다.
     * @return True : 생성 성공 <br> False : 생성 실패
     */
    private boolean initJFrame() {
        // 존재하지 않는 데이터 베이스 출력 시,
        if(arrayList.isEmpty()) {
            // 에러 출력 및 화면 생성 실패
            DBs.log("데이터가 존재하지 않아 ListTable을 생성할 수 없습니다.");
            return false;
        }
        // 처음으로 생성되는 GUI 위치 설정 (10,10 에 배치)
        if(locations == null) locations = new Point(10,10);

        titles = Reflections.getTitles(arrayList.get(0).getClass()); // GUI의 제목설정
        data = Reflections.convertToArray(arrayList);
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setTitle(arrayList.get(0).getClass().getName() + " data");
        frame.setLocation(locations);
        locations.x = locations.x + 500;

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
}
