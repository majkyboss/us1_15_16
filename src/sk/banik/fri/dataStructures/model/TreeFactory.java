package sk.banik.fri.dataStructures.model;

import sk.banik.fri.dataStructures.BasicMapCollection;
import sk.banik.fri.dataStructures.RBTree;
import sk.banik.fri.dataStructures.RBTreeOld;

public class TreeFactory<Key extends Comparable<Key>, Value> {
	
	public BasicMapCollection<Key, Value> getTreeInstance() {
		BasicMapCollection<Key, Value> instance = new RBTree<Key, Value>();
//		instance = new RBTreeOld<Key, Value>();
		
		return instance;
	}
}
