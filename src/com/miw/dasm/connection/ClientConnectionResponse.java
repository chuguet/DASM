package com.miw.dasm.connection;

import com.miw.dasm.model.HandlerPersona;

public class ClientConnectionResponse {

	private String dniFind;
	private Integer buttonId;
	private HandlerPersona handlerPersona;
	private Integer numReg;
	private Boolean connectivity;

	public ClientConnectionResponse(HandlerPersona handlerPersona,
			Integer numReg, Integer buttonId, String dniFind, Boolean connectivity) {
		this.setHandlerPersona(handlerPersona);
		this.setNumReg(numReg);
		this.setButtonId(buttonId);
		this.setDniFind(dniFind);
		this.setConnectivity(connectivity);
	}

	public HandlerPersona getHandlerPersona() {
		return handlerPersona;
	}

	public void setHandlerPersona(HandlerPersona handlerPersona) {
		this.handlerPersona = handlerPersona;
	}

	public Integer getNumReg() {
		return numReg;
	}

	public void setNumReg(Integer numReg) {
		this.numReg = numReg;
	}

	public String getDniFind() {
		return dniFind;
	}

	public void setDniFind(String dniFind) {
		this.dniFind = dniFind;
	}

	public Integer getButtonId() {
		return buttonId;
	}

	public void setButtonId(Integer buttonId) {
		this.buttonId = buttonId;
	}

	public Boolean getConnectivity() {
		return connectivity;
	}

	public void setConnectivity(Boolean connectivity) {
		this.connectivity = connectivity;
	}

}
