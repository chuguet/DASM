package com.miw.dasm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.miw.dasm.connection.ClientConnectionResponse;

public class HandlerPersona implements Iterable<Persona> {

	private final List<Persona> personas = new ArrayList<Persona>();

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

	public void parseReponse(ClientConnectionResponse response) {
		JSONArray arrayDatos;
		Persona persona;
		try {
			arrayDatos = new JSONArray(response.getReponseSerializable());
			int numRegistros = arrayDatos.getJSONObject(0).getInt("NUMREG");
			if (numRegistros > 0) {
				for (int i = 1; i <= numRegistros; i++) {
					persona = new Persona(arrayDatos.getJSONObject(i)
							.getString("DNI"), arrayDatos.getJSONObject(i)
							.getString("Nombre"), arrayDatos.getJSONObject(i)
							.getString("Apellidos"), arrayDatos.getJSONObject(i)
							.getString("Direccion"), arrayDatos.getJSONObject(i)
							.getString("Telefono"), arrayDatos.getJSONObject(i)
							.getInt("Equipo"));
					this.addPersona(persona);
				}
			}
		} catch (JSONException e) {
			Log.e("API_ERROR", e.getMessage());
		}
	}

}
