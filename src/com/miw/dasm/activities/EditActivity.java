package com.miw.dasm.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.miw.dasm.R;
import com.miw.dasm.connection.ClientConnectionAPI;
import com.miw.dasm.connection.ClientConnectionRequest;
import com.miw.dasm.connection.ClientConnectionResponse;
import com.miw.dasm.connection.TypeRequest;
import com.miw.dasm.model.ModelSerializer;
import com.miw.dasm.model.Persona;

public class EditActivity extends Activity implements IActivityCallback {

	private OnClickListener editEvent = new OnClickListener() {

		@Override
		public void onClick(View view) {
			new ClientConnectionAPI(getContext(), TypeRequest.PUT)
					.executeREST(new ClientConnectionRequest((getPersona())));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		try {
			Persona persona = (Persona) ModelSerializer.getInstance()
					.deserialize(getIntent().getExtras().getByteArray("value"));
			this.setPersona(persona);
		} catch (ClassNotFoundException e) {
			Log.e("ERROR_SERIALIZE", e.getMessage());
		} catch (IOException e) {
			Log.e("ERROR_SERIALIZE", e.getMessage());
		}

		ImageButton button = (ImageButton) findViewById(R.id.buttonEdit);
		button.setOnClickListener(editEvent);
	}

	private void setPersona(Persona persona) {
		EditText editText;

		editText = (EditText) findViewById(R.id.textDni);
		editText.setText(persona.getDni(), TextView.BufferType.EDITABLE);
		editText.setFocusable(false);

		editText = (EditText) findViewById(R.id.textNombre);
		editText.setText(persona.getNombre(), TextView.BufferType.EDITABLE);

		editText = (EditText) findViewById(R.id.textApellidos);
		editText.setText(persona.getApellidos(), TextView.BufferType.EDITABLE);

		editText = (EditText) findViewById(R.id.textDireccion);
		editText.setText(persona.getDireccion(), TextView.BufferType.EDITABLE);

		editText = (EditText) findViewById(R.id.textTelefono);
		editText.setText(persona.getTelefono(), TextView.BufferType.EDITABLE);

		editText = (EditText) findViewById(R.id.textEquipo);
		editText.setText(persona.getEquipo(), TextView.BufferType.EDITABLE);
	}

	private Persona getPersona() {
		String dni = ((EditText) findViewById(R.id.textDni)).getText()
				.toString();
		String nombre = ((EditText) findViewById(R.id.textNombre)).getText()
				.toString();
		String apellidos = ((EditText) findViewById(R.id.textApellidos))
				.getText().toString();
		String direccion = ((EditText) findViewById(R.id.textDireccion))
				.getText().toString();
		String telefono = ((EditText) findViewById(R.id.textTelefono))
				.getText().toString();
		String equipo = ((EditText) findViewById(R.id.textEquipo)).getText()
				.toString();
		return new Persona(dni, nombre, apellidos, direccion, telefono, equipo);
	}

	public EditActivity getContext() {
		return this;
	}

	@Override
	public void processResponse(
			ClientConnectionResponse clientConnectionResponse) {
		Intent intent = new Intent();
		if (clientConnectionResponse.getNumReg() == -1) {
			intent.putExtra("respuesta", getString(R.string.actualizacion_ko));
			setResult(RESULT_CANCELED, intent);
		} else {
			intent.putExtra("respuesta", getString(R.string.actualizacion_ok));
			setResult(RESULT_OK, intent);
		}
		finish();
	}
}
