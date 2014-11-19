package com.miw.dasm.activities;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.miw.dasm.R;
import com.miw.dasm.model.HandlerPersona;
import com.miw.dasm.model.ModelSerializer;
import com.miw.dasm.model.Persona;

public class ListActivity extends Activity {

	private Integer page = 0;

	private HandlerPersona personas;

	private final OnClickListener eventButton = new OnClickListener() {
		@Override
		public void onClick(View view) {
			String texto;
			switch (view.getId()) {
			case (R.id.buttonFirst):
				setPersona(personas.get(0));
				page = 0;
				texto = getString(R.string.contador_registros).replace(
						"#page#", String.valueOf(page + 1)).replace(
						"#total_pages#", String.valueOf(personas.size()));
				((TextView) findViewById(R.id.contadorRegistros))
						.setText(texto);
				break;
			case (R.id.buttonLast):
				setPersona(personas.get(personas.size() - 1));
				page = personas.size() - 1;
				texto = getString(R.string.contador_registros).replace(
						"#page#", String.valueOf(page + 1)).replace(
						"#total_pages#", String.valueOf(personas.size()));
				((TextView) findViewById(R.id.contadorRegistros))
						.setText(texto);
				break;
			case (R.id.buttonPrevious):
				setPersona(personas.get(page - 1));
				page--;
				texto = getString(R.string.contador_registros).replace(
						"#page#", String.valueOf(page + 1)).replace(
						"#total_pages#", String.valueOf(personas.size()));
				((TextView) findViewById(R.id.contadorRegistros))
						.setText(texto);
				break;
			case (R.id.buttonNext):
				setPersona(personas.get(page + 1));
				page++;
				texto = getString(R.string.contador_registros).replace(
						"#page#", String.valueOf(page + 1)).replace(
						"#total_pages#", String.valueOf(personas.size()));
				((TextView) findViewById(R.id.contadorRegistros))
						.setText(texto);
				break;
			}
			rebuildButtons();
		}

	};

	private void rebuildButtons() {
		ImageButton buttonFirst = (ImageButton) findViewById(R.id.buttonFirst);
		ImageButton buttonPrevious = (ImageButton) findViewById(R.id.buttonPrevious);
		ImageButton buttonNext = (ImageButton) findViewById(R.id.buttonNext);
		ImageButton buttonLast = (ImageButton) findViewById(R.id.buttonLast);
		if (page == 0) {
			buttonFirst.setEnabled(false);
			buttonPrevious.setEnabled(false);
			buttonNext.setEnabled(true);
			buttonLast.setEnabled(true);
		} else if (page == personas.size() - 1) {
			buttonFirst.setEnabled(true);
			buttonPrevious.setEnabled(true);
			buttonNext.setEnabled(false);
			buttonLast.setEnabled(false);
		} else {
			buttonFirst.setEnabled(true);
			buttonPrevious.setEnabled(true);
			buttonNext.setEnabled(true);
			buttonLast.setEnabled(true);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		try {
			this.personas = (HandlerPersona) ModelSerializer.getInstance()
					.deserialize(getIntent().getExtras().getByteArray("value"));
			if (personas.size() == 1) {
				((ImageButton) findViewById(R.id.buttonFirst))
						.setVisibility(View.GONE);
				((ImageButton) findViewById(R.id.buttonPrevious))
						.setVisibility(View.GONE);
				((ImageButton) findViewById(R.id.buttonNext))
						.setVisibility(View.GONE);
				((ImageButton) findViewById(R.id.buttonLast))
						.setVisibility(View.GONE);
				((TextView) findViewById(R.id.contadorRegistros))
						.setVisibility(View.GONE);
			} else {
				String texto = getString(R.string.contador_registros).replace(
						"#page#", String.valueOf(page + 1)).replace(
						"#total_pages#", String.valueOf(personas.size()));
				((TextView) findViewById(R.id.contadorRegistros))
						.setText(texto);
			}
			Persona persona = this.personas.get(page);

			this.setPersona(persona);
		} catch (ClassNotFoundException e) {
			Log.e("ERROR_SERIALIZE", e.getMessage());
		} catch (IOException e) {
			Log.e("ERROR_SERIALIZE", e.getMessage());
		}

		ImageButton buttonFirst = (ImageButton) findViewById(R.id.buttonFirst);
		ImageButton buttonLast = (ImageButton) findViewById(R.id.buttonLast);
		ImageButton buttonPrevious = (ImageButton) findViewById(R.id.buttonPrevious);
		ImageButton buttonNext = (ImageButton) findViewById(R.id.buttonNext);
		buttonFirst.setOnClickListener(eventButton);
		buttonLast.setOnClickListener(eventButton);
		buttonPrevious.setOnClickListener(eventButton);
		buttonNext.setOnClickListener(eventButton);

		rebuildButtons();
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
}
