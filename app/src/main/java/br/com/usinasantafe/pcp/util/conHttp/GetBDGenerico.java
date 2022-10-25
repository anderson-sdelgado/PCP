package br.com.usinasantafe.pcp.util.conHttp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.util.AtualDadosServ;

public class GetBDGenerico extends AsyncTask<String, Void, String> {

	private static GetBDGenerico instance = null;
	private String tipo = null;
	private String activity;
	
	private UrlsConexaoHttp urlsConexaoHttp;

	public GetBDGenerico() {
	}

    public static GetBDGenerico getInstance() {
        if (instance == null)
        instance = new GetBDGenerico();
        return instance;
    }

	@Override
	protected String doInBackground(String... arg) {
		
		String resultado = "";
		BufferedReader bufferedReader = null;
		
		this.tipo = arg[0];
		this.activity = arg[1];
		String url = "";
		
		try {
			
			Object object = new Object();
            Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl); 
			
            for (Field field : retClasse.getDeclaredFields()) {
                String campo = field.getName();
                if(campo.equals(tipo)){
                	url = "" + retClasse.getField(campo).get(object);
               }
            }

			URL urlCon = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection) urlCon.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(false);
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts(), new java.security.SecureRandom());
			connection.setSSLSocketFactory(sc.getSocketFactory());
			connection.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String s, SSLSession sslSession) {
					return true;
				}
			});
			connection.connect();

			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while((line = bufferedReader.readLine()) != null){
				stringBuffer.append(line + LS);
			}
			bufferedReader.close();
			resultado = stringBuffer.toString();

			connection.disconnect();
            
		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (Exception erro) {
					LogErroDAO.getInstance().insertLogErro(erro);
				}
			}
		}
		finally{
			
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (Exception e) {
					LogErroDAO.getInstance().insertLogErro(e);
				}
			}
			
		}
		
		return resultado;
	}
	
	protected void onPostExecute(String result) {

		try {
			LogProcessoDAO.getInstance().insertLogProcesso("AtualDadosServ.getInstance().manipularDadosHttp('" + tipo + "', result);", activity);
			AtualDadosServ.getInstance().manipularDadosHttp(tipo, result, activity);
		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
		}

    }

	public TrustManager[] trustAllCerts(){
		return new TrustManager[]{
				new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers()
					{
						return null;
					}
					public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
					public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
				}
		};
	}

}
