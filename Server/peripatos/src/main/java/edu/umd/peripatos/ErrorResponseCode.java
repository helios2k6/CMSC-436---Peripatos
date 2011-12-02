package edu.umd.peripatos;

public enum ErrorResponseCode {
	INVALID_LOGIN(1000),
	INVALID_SUBMISSION(1001),
	INVALID_REQUEST(1002);
	
	private long code;
	
	private ErrorResponseCode(long code){
		this.code = code;
	}
	
	public String toString(){
		return code + "";
	}
}
