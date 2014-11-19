package com.miw.dasm.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.miw.dasm.R;
import com.miw.dasm.connection.ClientConnectionAPI;
import com.miw.dasm.connection.ClientConnectionRequest;
import com.miw.dasm.connection.ClientConnectionResponse;
import com.miw.dasm.connection.TypeRequest;
import com.miw.dasm.model.HandlerPersona;
import com.miw.dasm.model.ModelSerializer;

public class MainActivity extends Activity {

	private static final int ADD_ACTIVITY = 001;
	private static final int EDIT_ACTIVITY = 002;
	private static final int DELETE_ACTIVITY = 003;
	private static final int LIST_ACTIVITY = 004;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_ACTIVITY) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(getBaseContext(),
						data.getStringExtra("respuesta"), Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getBaseContext(),
						getString(R.string.insercion_ko), Toast.LENGTH_SHORT)
						.show();
			}
		} else if (requestCode == EDIT_ACTIVITY) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(getBaseContext(),
						data.getStringExtra("respuesta"), Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getBaseContext(),
						getString(R.string.actualizacion_ko),
						Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == DELETE_ACTIVITY) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(getBaseContext(),
						data.getStringExtra("respuesta"), Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getBaseContext(),
						getString(R.string.borrado_ko), Toast.LENGTH_SHORT)
						.show();
			}
		} else if (requestCode == LIST_ACTIVITY) {
			Toast.makeText(getBaseContext(), getString(R.string.consulta),
					Toast.LENGTH_SHORT).show();
		}
	}

	public MainActivity getContext() {
		return this;
	}

	private final OnClickListener eventButton = new OnClickListener() {
		@Override
		public void onClick(View view) {
			try {
				String dni = ((EditText) findViewById(R.id.textDniSearch))
						.getText().toString();
				if (view.getId() != R.id.buttonSearch && dni.isEmpty()) {
					Toast.makeText(getBaseContext(), "Debe introducir un DNI",
							Toast.LENGTH_SHORT).show();
				} else {
					ClientConnectionResponse response = new ClientConnectionAPI(
							getContext(), TypeRequest.GET)
							.executeREST(new ClientConnectionRequest((dni)));

					launchView(response.getHandlerPersona(), view.getId(), dni);
				}
			} catch (IOException e) {
				Log.e("ERROR_SERIALIZE", e.getMessage());
			}
		}

		private void launchView(HandlerPersona handlerPersona, int idButton,
				String dni) throws IOException {
			Intent intent;
			switch (idButton) {
			case R.id.buttonAdd:
				if (handlerPersona.contains(dni)) {
					Toast.makeText(getBaseContext(), "La persona ya existe",
							Toast.LENGTH_SHORT).show();
				} else {
					intent = new Intent(getBaseContext(), AddActivity.class);
					intent.putExtra("value", dni);
					startActivityForResult(intent, ADD_ACTIVITY);
				}
				break;
			case R.id.buttonDelete:
				if (handlerPersona.contains(dni)) {
					intent = new Intent(getBaseContext(), DeleteActivity.class);
					intent.putExtra("value", ModelSerializer.getInstance()
							.serialize(handlerPersona.get(dni)));
					startActivityForResult(intent, DELETE_ACTIVITY);
				} else {
					Toast.makeText(getBaseContext(), "La persona no existe",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.buttonSearch:
				if (handlerPersona.contains(dni) || dni.isEmpty()) {
					intent = new Intent(getBaseContext(), ListActivity.class);
					intent.putExtra("value", ModelSerializer.getInstance()
							.serialize(handlerPersona));
					startActivityForResult(intent, LIST_ACTIVITY);
				} else {
					Toast.makeText(getBaseContext(), "La persona no existe",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.buttonEdit:
				if (handlerPersona.contains(dni)) {
					intent = new Intent(getBaseContext(), EditActivity.class);
					intent.putExtra("value", ModelSerializer.getInstance()
							.serialize(handlerPersona.get(dni)));
					startActivityForResult(intent, EDIT_ACTIVITY);
				} else {
					Toast.makeText(getBaseContext(), "La persona no existe",
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.findViewById(R.id.buttonAdd).setOnClickListener(this.eventButton);
		this.findViewById(R.id.buttonSearch).setOnClickListener(
				this.eventButton);
		this.findViewById(R.id.buttonEdit).setOnClickListener(this.eventButton);
		this.findViewById(R.id.buttonDelete).setOnClickListener(
				this.eventButton);
	}
}
