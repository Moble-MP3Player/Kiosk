package GUI;

import backend.DBs;
import util.Reflections;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ListTable {
    private static Point locations;
    private JTable jtable;
    private String[][] data;
    private String[] titles;
    private ArrayList<?> arrayList;

    public ListTable(ArrayList<?> arrayList) {
        this.arrayList = arrayList;
        initJFrame();
    }

    /**
     * JFrame을 생성하고 성공 시, true를 반환합니다. 실패 시 false를 반환합니다.
     * @return True : 생성 성공 <br> False : 생성 실패
     */
    private boolean initJFrame() {
        // 존재하지 않는 데이터 베이스 출력 시,
        if(arrayList.isEmpty()) {
            // 에러 출력 및 화면 생성 실패
            DBs.log("데이터가 존재하지 않아 ListTable을 생성할 수 없습니다.");
            return false;
        }
        if(locations == null) locations = new Point(10,10);

        titles = Reflections.getTitles(arrayList.get(0).getClass());
        data = Reflections.convertToArray(arrayList);
        JFrame frame = new JFrame();
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
        };
        jtable = new JTable(defaultTableModel);

        JScrollPane scrollPane = new JScrollPane(jtable);
        frame.add(scrollPane);
        frame.setVisible(true);
        return true;
    }


    /**
     * arrayList의 값으로 해당 Table을 update하는 함수
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

    public String[][] getData() {
        return data;
    }

    public ArrayList<?> getArrayList() {
        return arrayList;
    }
}
