package com.miw.dasm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.miw.dasm.model.Persona;

public class AddActivity extends Activity {

	private OnClickListener insertEvent = new OnClickListener() {

		@Override
		public void onClick(View view) {
			ClientConnectionResponse response = new ClientConnectionAPI(
					getContext(), TypeRequest.POST)
					.executeREST(new ClientConnectionRequest((getPersona())));

			Intent intent = new Intent();
			if (response.getNumReg() == -1) {
				intent.putExtra("respuesta", getString(R.string.insercion_ko));
				setResult(RESULT_CANCELED, intent);
			} else {
				intent.putExtra("respuesta", getString(R.string.insercion_ok));
				setResult(RESULT_OK, intent);
			}
			finish();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		EditText editText = (EditText) findViewById(R.id.textDni);
		editText.setText(getIntent().getExtras().getString("value"),
				TextView.BufferType.EDITABLE);
		editText.setFocusable(false);

		ImageButton button = (ImageButton) findViewById(R.id.buttonAdd);
		button.setOnClickListener(insertEvent);
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

	public AddActivity getContext() {
		return this;
	}
}
