package edu.umd.peripatos;

public enum Authority {
	STUDENT("ROLE_STUDENT"),
	PROFESSOR("ROLE_PROFESSOR");
	
	private String name;
	private Authority(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
