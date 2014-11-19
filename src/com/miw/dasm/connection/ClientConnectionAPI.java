package com.miw.dasm.connection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.miw.dasm.R;
import com.miw.dasm.model.HandlerPersona;

public class ClientConnectionAPI extends
		AsyncTask<ClientConnectionRequest, Void, ClientConnectionResponse>
		implements IClientConnectionAPI {

	private Activity activity;
	private TypeRequest typeRequest;
	private boolean error;
	private final String URL = "http://demo.calamar.eui.upm.es/dasmapi/v1/miw17/fichas";

	public ClientConnectionAPI(Activity activity, TypeRequest typeRequest) {
		super();
		this.typeRequest = typeRequest;
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		error = false;
		this.showDialog();
	}

	public void showDialog() {
		pDialog = ProgressDialog.show(getActivity(),
				getActivity().getString(R.string.information), getActivity()
						.getString(R.string.progress_title), false, true);
	}

	public void closeDialog() {
		pDialog.dismiss();
	}

	private ProgressDialog pDialog;

	@Override
	protected ClientConnectionResponse doInBackground(
			ClientConnectionRequest... params) {
		ClientConnectionResponse result = null;
		String datos;
		String urlFinal = URL;
		ClientConnectionRequest clientConnectionRequest = params[0];
		if (!clientConnectionRequest.getDni().isEmpty()) {
			urlFinal += "/" + clientConnectionRequest.getDni();
		}
		try {
			AndroidHttpClient httpClient = AndroidHttpClient
					.newInstance("AndroidHttpClient");
			HttpUriRequest httpRequestBase = getRequestBase(urlFinal,
					clientConnectionRequest.getPersona());
			HttpResponse response = httpClient.execute(httpRequestBase);
			datos = EntityUtils.toString(response.getEntity());

			Integer numReg = new JSONArray(datos).getJSONObject(0).getInt(
					"NUMREG");
			HandlerPersona handlerPersona = new HandlerPersona(datos);
			result = new ClientConnectionResponse(handlerPersona, numReg);
			httpClient.close();
		} catch (IOException e) {
			Log.e("API_ERROR", e.getMessage());
			error = true;
		} catch (JSONException e) {
			Log.e("API_ERROR", e.getMessage());
			error = true;
		}
		return result;
	}

	private HttpUriRequest getRequestBase(String url, String persona)
			throws UnsupportedEncodingException {
		HttpUriRequest result = null;

		if (typeRequest.equals(TypeRequest.DELETE)) {
			result = new HttpDelete(url);
		} else if (typeRequest.equals(TypeRequest.PUT)) {
			HttpPut httpPut = new HttpPut(url);
			StringEntity stringEntity = new StringEntity(persona, HTTP.UTF_8);
			httpPut.setEntity(stringEntity);
			result = httpPut;
		} else if (typeRequest.equals(TypeRequest.POST)) {
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(persona, HTTP.UTF_8);
			httpPost.setEntity(stringEntity);
			result = httpPost;
		} else if (typeRequest.equals(TypeRequest.GET)) {
			result = new HttpGet(url);
		}

		return result;
	}

	@Override
	protected void onPostExecute(ClientConnectionResponse response) {
		this.closeDialog();
		if (error) {
			Toast.makeText(getActivity().getBaseContext(),
					"Error en la conexion", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public ClientConnectionResponse executeREST(
			ClientConnectionRequest clientConnectionRequest) {
		ClientConnectionResponse res = null;
		try {
			res = this.execute(clientConnectionRequest).get();
		} catch (InterruptedException e) {
			Log.e("API_ERROR", e.getMessage());
			Toast.makeText(getActivity().getBaseContext(),
					"Error en la respuesta del servidor", Toast.LENGTH_SHORT)
					.show();
		} catch (ExecutionException e) {
			Log.e("API_ERROR", e.getMessage());
			Toast.makeText(getActivity().getBaseContext(),
					"Error en la respuesta del servidor", Toast.LENGTH_SHORT)
					.show();
		}
		return res;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
