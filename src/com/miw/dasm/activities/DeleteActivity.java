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

public class DeleteActivity extends Activity {

	private OnClickListener deleteEvent = new OnClickListener() {

		@Override
		public void onClick(View view) {
			ClientConnectionResponse response = new ClientConnectionAPI(
					getContext(), TypeRequest.DELETE)
					.executeREST(new ClientConnectionRequest(
							((EditText) findViewById(R.id.textDni)).getText()
									.toString()));

			Intent intent = new Intent();
			if (response.getNumReg() == -1) {
				intent.putExtra("respuesta", getString(R.string.borrado_ko));
				setResult(RESULT_CANCELED, intent);
			} else {
				intent.putExtra("respuesta", getString(R.string.borrado_ok));
				setResult(RESULT_OK, intent);
			}
			finish();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete);

		try {
			Persona persona = (Persona) ModelSerializer.getInstance()
					.deserialize(getIntent().getExtras().getByteArray("value"));
			this.setPersona(persona);
		} catch (ClassNotFoundException e) {
			Log.e("ERROR_SERIALIZE", e.getMessage());
		} catch (IOException e) {
			Log.e("ERROR_SERIALIZE", e.getMessage());
		}

		ImageButton button = (ImageButton) findViewById(R.id.buttonDelete);
		button.setOnClickListener(deleteEvent);
	}

	private void setPersona(Persona persona) {
		EditText editText;

		editText = (EditText) findViewById(R.id.textDni);
		editText.setText(persona.getDni(), TextView.BufferType.EDITABLE);
		editText.setFocusable(false);

		editText = (EditText) findViewById(R.id.textNombre);
		editText.setText(persona.getNombre(), TextView.BufferType.EDITABLE);
		editText.setFocusable(false);

		editText = (EditText) findViewById(R.id.textApellidos);
		editText.setText(persona.getApellidos(), TextView.BufferType.EDITABLE);
		editText.setFocusable(false);

		editText = (EditText) findViewById(R.id.textDireccion);
		editText.setText(persona.getDireccion(), TextView.BufferType.EDITABLE);
		editText.setFocusable(false);

		editText = (EditText) findViewById(R.id.textTelefono);
		editText.setText(persona.getTelefono(), TextView.BufferType.EDITABLE);
		editText.setFocusable(false);

		editText = (EditText) findViewById(R.id.textEquipo);
		editText.setText(persona.getEquipo(), TextView.BufferType.EDITABLE);
		editText.setFocusable(false);
	}

	public DeleteActivity getContext() {
		return this;
	}
}
