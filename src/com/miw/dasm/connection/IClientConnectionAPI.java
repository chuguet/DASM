package com.miw.dasm.connection;

public interface IClientConnectionAPI {

	ClientConnectionResponse executeREST(ClientConnectionRequest request);
}
