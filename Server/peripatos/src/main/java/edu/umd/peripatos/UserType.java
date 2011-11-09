package edu.umd.peripatos;

public enum UserType {
	STUDENT("STUDENT"),
	PROFESSOR("PROFESSOR");
	
	private String name;
	private UserType(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
