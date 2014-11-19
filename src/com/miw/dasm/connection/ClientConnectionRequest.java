package com.miw.dasm.connection;

import com.miw.dasm.model.Persona;

public class ClientConnectionRequest {

	public ClientConnectionRequest (String dni){
		this.dni = dni;
	}
	
	public ClientConnectionRequest(Persona persona) {
		this.setPersona(persona.toJSON());
		this.dni = "";
	}

	private String persona;
	private String dni;

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}
}
