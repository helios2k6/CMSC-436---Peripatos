package edu.umd.peripatos;

public enum Authority {
	ROLE_STUDENT("ROLE_STUDENT"),
	ROLE_PROFESSOR("ROLE_PROFESSOR");
	
	private String name;
	
	private Authority(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
