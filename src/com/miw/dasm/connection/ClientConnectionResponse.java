package com.miw.dasm.connection;

public class ClientConnectionResponse {

	public ClientConnectionResponse (String reponseSerializable){
		this.reponseSerializable = reponseSerializable;
	}
	
	private String reponseSerializable;

	public String getReponseSerializable() {
		return reponseSerializable;
	}

	public void setReponseSerializable(String reponseSerializable) {
		this.reponseSerializable = reponseSerializable;
	}
}
