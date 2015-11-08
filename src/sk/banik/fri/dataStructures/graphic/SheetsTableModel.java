package sk.banik.fri.dataStructures.graphic;

import sk.banik.fri.dataStructures.Labels;
import sk.banik.fri.dataStructures.model.PropertySheet;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by majky on 8.11.2015.
 */
public class SheetsTableModel extends AbstractTableModel {
    List<PropertySheet> sheets;
    String[] names = new String[]{Labels.propertySheetNumber, Labels.propertiesCount, Labels.ownersCount};

    public SheetsTableModel(List<PropertySheet> sheets) {
        super();
        this.sheets = sheets;
    }

    @Override
    public int getRowCount() {
        if (sheets == null)
            return 0;

        return sheets.size();
    }

    @Override
    public int getColumnCount() {
        return names.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (sheets != null)
            switch (columnIndex) {
                case 0:
                    return sheets.get(rowIndex).getNumber();
                case 1:
                    return sheets.get(rowIndex).getPropertiesList().size();
                case 2:
                    return sheets.get(rowIndex).getShareholdersList().size();
            }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }

    public PropertySheet getSheet(int index){
        if (sheets == null)
            return null;

        return sheets.get(index);
    }
}
