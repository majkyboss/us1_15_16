package sk.banik.fri.dataStructures.model;

import java.util.HashSet;

public class Owner {
	
	private String firstName;
	private String lastname;
	private String bornNumber;
	private HashSet<Property> ownerOf;
	private Property residance;
	
	public Owner() {
		ownerOf = new HashSet<>();
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
	public HashSet<Property> getOwnerOf() {
		return ownerOf;
	}
	public void setOwnerOf(HashSet<Property> ownerOf) {
		this.ownerOf = ownerOf;
	}
	public Property getResidance() {
		return residance;
	}
	public void setResidance(Property residance) {
		this.residance = residance;
	}
	
	
}
