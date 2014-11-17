package com.miw.dasm.connection;

import com.miw.dasm.model.HandlerPersona;

public class ClientConnectionResponse {

	private HandlerPersona handlerPersona;
	private Integer numReg;

	public ClientConnectionResponse(HandlerPersona handlerPersona,
			Integer numReg) {
		this.setHandlerPersona(handlerPersona);
		this.setNumReg(numReg);
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

}
