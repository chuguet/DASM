package com.miw.dasm.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.miw.dasm.R;
import com.miw.dasm.connection.ClientConnectionAPI;
import com.miw.dasm.connection.ClientConnectionRequest;
import com.miw.dasm.connection.ClientConnectionResponse;
import com.miw.dasm.connection.TypeRequest;

public class PreferencesActivity extends PreferenceActivity implements
		IActivityCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PrefsFragment()).commit();
	}

	private static class PrefsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Log.d("Preferencias", "boton atras pulsado");
			new ClientConnectionAPI(this, TypeRequest.GET)
					.executeREST(new ClientConnectionRequest(true));
		}
		return false;
	}

	@Override
	public void processResponse(
			ClientConnectionResponse clientConnectionResponse) {
		Integer result = clientConnectionResponse.getNumReg();
		if (result == 0) {
			setResult(RESULT_OK);
			finish();
		} else {
			Toast.makeText(getBaseContext(),
					getString(R.string.servicio_ko), Toast.LENGTH_LONG)
					.show();
		}
	}
}
