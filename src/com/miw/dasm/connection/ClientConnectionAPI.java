package com.miw.dasm.connection;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.miw.dasm.activities.MainActivity;
import com.miw.dasm.model.HandlerPersona;

public class ClientConnectionAPI extends AsyncTask<String, Void, String>
		implements IClientConnectionAPI {

	private MainActivity mainActivity;
	private TypeRequest typeRequest;
	private boolean error;
	private final String URL = "http://demo.calamar.eui.upm.es/dasmapi/v1/miw17/fichas";

	public ClientConnectionAPI(MainActivity mainActivity, TypeRequest typeRequest) {
		super();
		this.typeRequest = typeRequest;
		this.mainActivity = mainActivity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		error = false;
		this.mainActivity.showDialog();
	}

	@Override
	protected String doInBackground(String... params) {
		String datos = null;
		String dni = params[0];
		String urlFinal = URL;
		if (!dni.isEmpty()) {
			urlFinal += "/" + dni;
		}
		try {
			AndroidHttpClient httpClient = AndroidHttpClient
					.newInstance("AndroidHttpClient");
			HttpRequestBase httpRequestBase = getRequestBase(urlFinal);
			HttpResponse res = httpClient.execute(httpRequestBase);
			datos = EntityUtils.toString(res.getEntity());
			httpClient.close();
		} catch (IOException e) {
			Log.e("API_ERROR", e.getMessage());
			error = true;
		}
		return datos;
	}

	private HttpRequestBase getRequestBase(String urlFinal) {
		HttpRequestBase result = null;

		if (typeRequest.equals(TypeRequest.DELETE)) {
			result = new HttpDelete(urlFinal);
		} else if (typeRequest.equals(TypeRequest.PUT)) {
			result = new HttpPut(urlFinal);
		} else if (typeRequest.equals(TypeRequest.POST)) {
			result = new HttpPost(urlFinal);
		} else if (typeRequest.equals(TypeRequest.GET)) {
			result = new HttpGet(urlFinal);
		}

		return result;
	}

	@Override
	protected void onPostExecute(String response) {
		this.mainActivity.closeDialog();
		if (error) {
			Toast.makeText(getMainActivity().getBaseContext(),
					"Error en la conexion", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public ClientConnectionResponse execute(
			ClientConnectionRequest clientConnectionRequest) {
		ClientConnectionResponse res = null;
		try {
			String responseSerialize = this.execute(
					clientConnectionRequest.getDni()).get();
			Integer numReg = new JSONArray(responseSerialize).getJSONObject(0)
					.getInt("NUMREG");
			HandlerPersona handlerPersona = new HandlerPersona(
					responseSerialize);
			res = new ClientConnectionResponse(handlerPersona, numReg);
		} catch (InterruptedException e) {
			Log.e("API_ERROR", e.getMessage());
			Toast.makeText(getMainActivity().getBaseContext(),
					"Error en la respuesta del servidor", Toast.LENGTH_SHORT)
					.show();
		} catch (ExecutionException e) {
			Log.e("API_ERROR", e.getMessage());
			Toast.makeText(getMainActivity().getBaseContext(),
					"Error en la respuesta del servidor", Toast.LENGTH_SHORT)
					.show();
		} catch (JSONException e) {
			Log.e("API_ERROR", e.getMessage());
			Toast.makeText(getMainActivity().getBaseContext(),
					"Error en la respuesta del servidor", Toast.LENGTH_SHORT)
					.show();
		}
		return res;
	}

	public MainActivity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

}
