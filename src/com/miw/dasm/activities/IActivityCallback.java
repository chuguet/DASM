package com.miw.dasm.activities;

import com.miw.dasm.connection.ClientConnectionResponse;

public interface IActivityCallback {

	void processResponse(ClientConnectionResponse clientConnectionResponse);

}
