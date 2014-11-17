package com.miw.dasm.connection;

public class ClientConnectionRequest {

	public ClientConnectionRequest (String dni){
		this.dni = dni;
	}
	
	private String dni;

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
}
