package GUI;

import util.Reflections;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ListTable {
    private JTable jtable;
    private String[][] data;
    private String[] titles;
    private ArrayList<?> arrayList;

    public ListTable(ArrayList<?> arrayList) {
        this.arrayList = arrayList;
        titles = Reflections.getTitles(arrayList.get(0).getClass());
        data = Reflections.convertToArray(arrayList);
        initJFrame();
    }

    private void initJFrame() {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);


        // cell 편집 불가하게
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, titles) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtable = new JTable(defaultTableModel);

        JScrollPane scrollPane = new JScrollPane(jtable);
        frame.add(scrollPane);
        frame.setVisible(true);
    }


    /**
     * arrayList의 값으로 해당 Table을 update하는 함수
     */
    public void update() {
        String[][] contents = Reflections.convertToArray(arrayList);

        DefaultTableModel newModel = new DefaultTableModel(contents, titles) {
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
