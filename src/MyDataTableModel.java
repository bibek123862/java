import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class MyDataTableModel extends DefaultTableModel {
    private ArrayList<String[]> values;
    private String[] columns;

    public MyDataTableModel(ArrayList<String[]> values, String[] columns) {
        this.values = values;
        this.columns = columns;
    }
    

    public MyDataTableModel() {
    }


    @Override
    public Object getValueAt(int row, int column){
        return values.get(row)[column];
    }
    
    @Override
    public String getColumnName(int column){
        return columns[column];
    }
    
    @Override
    public int getColumnCount(){
        if (columns == null){
            return 0;
        }
        return columns.length;
    }
    
    @Override
    public int getRowCount(){
        if(values == null){
            return 0;
        }
        
        return values.size();
    }
    
    
}
