package edu.umd.peripatos;

public enum SubmissionReceiptStatusCode {
	OK ("OK"),
	ERROR("ERROR");
	
	private String status;
	
	private SubmissionReceiptStatusCode(String status){
		this.status = status;
	}
	
	public String toString(){
		return status;
	}
}
