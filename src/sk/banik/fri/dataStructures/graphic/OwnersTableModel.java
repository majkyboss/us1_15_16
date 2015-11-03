package sk.banik.fri.dataStructures.graphic;

import java.util.LinkedList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import sk.banik.fri.dataStructures.Labels;
import sk.banik.fri.dataStructures.model.Owner;
import sk.banik.fri.dataStructures.model.Property;
import sk.banik.fri.dataStructures.model.ShareholdEntry;

public class OwnersTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 63887743008325446L;
	List<ShareholdEntry> shareholdings;
	List<Owner> ownersList;

	public OwnersTableModel(LinkedList<ShareholdEntry> shareholdings) {
		super();
		this.shareholdings = shareholdings;
		init();
	}
	
	public OwnersTableModel(List<Owner> ownersList) {
		super();
		this.ownersList = ownersList;
		init();
	}

	private void init() {
	}

	@Override
	public int getRowCount() {
		if (shareholdings != null) {
			return shareholdings.size();
		} else if (ownersList != null) {
			return ownersList.size();
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		if (shareholdings != null) {
			return 3;
		}
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (shareholdings != null) {
			ShareholdEntry sh = shareholdings.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return sh.owner.getFirstName() + " " + sh.owner.getLastname();
			case 1:
				Property residance = sh.owner.getResidance();
				if (residance == null) return null;
				return residance.getAddress();
			case 2:
				return sh.shareholding;
			default:
				break;
			}
		} else if (ownersList != null) {
			Owner o = ownersList.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return o.getFirstName() + o.getLastname();
			case 1:
				Property residance = o.getResidance();
				if (residance == null) return null;
				return residance.getAddress();
			default:
				break;
			}
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		String[] names = null;
		if (shareholdings != null) {
			names = new String[]{Labels.ownerLabel, Labels.address, Labels.shareholding};
		} else if (ownersList != null) {
			names = new String[]{Labels.ownerLabel, Labels.address};
		}
		return names[column];
	}
	
	public Owner getOwner(int index) {
		if (shareholdings != null) {
			return shareholdings.get(index).owner;
		} else if (ownersList != null) {
			return ownersList.get(index);
		}
		return null;
	}
}
