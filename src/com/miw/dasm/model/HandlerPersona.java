package com.miw.dasm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class HandlerPersona implements Iterable<Persona> {

	private final List<Persona> personas = new ArrayList<Persona>();

	public HandlerPersona(String responseSerialize) throws JSONException {
		this.parseReponse(responseSerialize);
	}

	public Integer size() {
		return personas.size();
	}

	@Override
	public Iterator<Persona> iterator() {
		return personas.iterator();
	}

	public void addPersona(Persona persona) {
		this.personas.add(persona);
	}

	public Boolean contains(String dni) {
		Boolean result = Boolean.FALSE;
		for (Persona persona : personas) {
			if (persona.getDni().equals(dni)) {
				result = Boolean.TRUE;
				break;
			}
		}
		return result;
	}

	public void parseReponse(String responseSerialize) throws JSONException {
		JSONArray arrayDatos;
		Persona persona;
		arrayDatos = new JSONArray(responseSerialize);
		int numRegistros = arrayDatos.getJSONObject(0).getInt("NUMREG");
		if (numRegistros > 0) {
			for (int i = 1; i <= numRegistros; i++) {
				persona = new Persona(arrayDatos.getJSONObject(i).getString(
						"DNI"),
						arrayDatos.getJSONObject(i).getString("Nombre"),
						arrayDatos.getJSONObject(i).getString("Apellidos"),
						arrayDatos.getJSONObject(i).getString("Direccion"),
						arrayDatos.getJSONObject(i).getString("Telefono"),
						arrayDatos.getJSONObject(i).getInt("Equipo"));
				this.addPersona(persona);
			}
		}
	}

}
