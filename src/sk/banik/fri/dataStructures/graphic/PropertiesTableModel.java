package sk.banik.fri.dataStructures.graphic;

import sk.banik.fri.dataStructures.Labels;
import sk.banik.fri.dataStructures.model.Owner;
import sk.banik.fri.dataStructures.model.Property;
import sk.banik.fri.dataStructures.model.ShareholdEntry;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by majky on 3.11.2015.
 */
public class  PropertiesTableModel  extends AbstractTableModel {
    List<Property> properties;
    String[] names = new String[]{Labels.propertyRegNumber, Labels.address};

    public PropertiesTableModel(List<Property> properties) {
        super();
        this.properties = properties;
        init();
    }

    private void init() {
    }

    @Override
    public int getRowCount() {
        if (properties == null) {
            return 0;
        }
        return properties.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return properties.get(rowIndex).getAddress();
            case 1:
                return properties.get(rowIndex).getRegisterNumber();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }

    public Property getProperty(int index){
        if (properties == null || properties.isEmpty())
            return null;

        return properties.get(index);
    }
}
