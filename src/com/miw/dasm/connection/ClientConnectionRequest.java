package com.miw.dasm.connection;

import com.miw.dasm.model.Persona;

public class ClientConnectionRequest {

	public ClientConnectionRequest(Boolean conectivity) {
		this.setConectivity(conectivity);
		this.dni = "";
	}

	public ClientConnectionRequest(String dni) {
		this.setConectivity(false);
		this.dni = dni;
	}

	public ClientConnectionRequest(Persona persona) {
		this.setConectivity(false);
		this.setPersona(persona.toJSON());
		this.dni = "";
	}

	public ClientConnectionRequest(String pDni, Integer idButton) {
		if (pDni == null) {
			this.dni = "";
		} else {
			this.dni = pDni;
		}
		this.idButton = idButton;
		this.setConectivity(false);
	}

	private Boolean conectivity;
	private String persona;
	private String dni;
	private Integer idButton;

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

	public Boolean getConectivity() {
		return conectivity;
	}

	public void setConectivity(Boolean conectivity) {
		this.conectivity = conectivity;
	}

	public Integer getIdButton() {
		return idButton;
	}

	public void setIdButton(Integer idButton) {
		this.idButton = idButton;
	}
}
