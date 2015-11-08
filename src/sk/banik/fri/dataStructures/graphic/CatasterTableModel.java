package sk.banik.fri.dataStructures.graphic;

import sk.banik.fri.dataStructures.Labels;
import sk.banik.fri.dataStructures.model.CatastralArea;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by majky on 8.11.2015.
 */
public class CatasterTableModel extends AbstractTableModel {
    List<CatastralArea> catastralAreas;
    String[] names = new String[]{Labels.catasterNumber , Labels.catasterName};

    public CatasterTableModel(List<CatastralArea> areas) {
        this.catastralAreas = areas;
    }

    @Override
    public int getRowCount() {
        if (catastralAreas==null)
            return 0;

        return catastralAreas.size();
    }

    @Override
    public int getColumnCount() {
        return names.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return catastralAreas.get(rowIndex).getId();
            case 1:
                return catastralAreas.get(rowIndex).getName();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }
}
