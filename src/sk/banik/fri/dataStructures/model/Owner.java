package sk.banik.fri.dataStructures.model;

import java.util.LinkedList;

public class Owner {
	
	private String firstName;
	private String lastname;
	private String bornNumber;
	private LinkedList<Property> ownerOf;
	private Property residance;
	
	public Owner() {
		ownerOf = new LinkedList<Property>();
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getBornNumber() {
		return bornNumber;
	}
	public void setBornNumber(String bornNumber) {
		this.bornNumber = bornNumber;
	}
	public LinkedList<Property> getOwnerOf() {
		return ownerOf;
	}
	public void setOwnerOf(LinkedList<Property> ownerOf) {
		this.ownerOf = ownerOf;
	}
	public Property getResidance() {
		return residance;
	}
	public void setResidance(Property residance) {
		this.residance = residance;
	}
	
	
}
