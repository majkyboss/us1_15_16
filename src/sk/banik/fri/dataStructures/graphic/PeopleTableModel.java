package sk.banik.fri.dataStructures.graphic;

import sk.banik.fri.dataStructures.model.Owner;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by majky on 8.11.2015.
 */
public class PeopleTableModel extends AbstractTableModel{
    public PeopleTableModel(List<Owner> people) {
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    public Owner getOwner(int selectedRow) {
        return null;
    }
}
