package sk.banik.fri.dataStructures.model;

import sk.banik.fri.dataStructures.BasicMapCollection;

public class CatastralOffice {
	private static int idCounter = 0;
	
	private int offcieId;
	private BasicMapCollection<Integer, CatastralArea> areasById;
	private BasicMapCollection<String, CatastralArea> areasByName;
	private BasicMapCollection<String, Owner> owners;
	
	public CatastralOffice() {
		super();
		offcieId = idCounter++;
		areasById =  new TreeFactory<Integer, CatastralArea>().getTreeInstance();
		areasByName =  new TreeFactory<String, CatastralArea>().getTreeInstance();
		owners = new TreeFactory<String, Owner>().getTreeInstance();
	}
	
	public int getOffcieId() {
		return offcieId;
	}
	public void setOffcieId(int offcieId) {
		this.offcieId = offcieId;
	}
	public BasicMapCollection<Integer, CatastralArea> getAreasById() {
		return areasById;
	}
	public void setAreasById(BasicMapCollection<Integer, CatastralArea> areasById) {
		this.areasById = areasById;
	}
	public BasicMapCollection<String, CatastralArea> getAreasByName() {
		return areasByName;
	}
	public void setAreasByName(BasicMapCollection<String, CatastralArea> areasByName) {
		this.areasByName = areasByName;
	}
	public BasicMapCollection<String, Owner> getOwners() {
		return owners;
	}
	public void setOwners(BasicMapCollection<String, Owner> owners) {
		this.owners = owners;
	}
}
