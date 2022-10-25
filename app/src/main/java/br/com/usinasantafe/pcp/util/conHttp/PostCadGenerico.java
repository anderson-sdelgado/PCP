package br.com.usinasantafe.pcp.util.conHttp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class PostCadGenerico extends AsyncTask<String, Void, String> {

	private static PostCadGenerico instance = null;
	private Map<String, Object> parametrosPost = null;
	private String activity;

	public PostCadGenerico() {
	}

    public static PostCadGenerico getInstance() {
        if (instance == null)
        instance = new PostCadGenerico();
        return instance;
    }

	@Override
	protected String doInBackground(String... arg) {
		
		BufferedReader bufferedReader = null;
		String resultado = null;
		
		String url = arg[0];
		this.activity = arg[1];

		try {

			String parametros = getQueryString(parametrosPost);
			URL urlCon = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection) urlCon.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
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

			OutputStream out = connection.getOutputStream();
			byte[] bytes = parametros.getBytes("UTF8");
			out.write(bytes);
			out.flush();
			out.close();

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
			EnvioDadosServ.status = 1;
			LogErroDAO.getInstance().insertLogErro(e);
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (Exception er) {
					LogErroDAO.getInstance().insertLogErro(er);
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
			Log.i("PMM", "VALOR RECEBIDO CAD --> " + result);
			EnvioDadosServ.getInstance().recDados(result, activity);
		} catch (Exception e) {
			EnvioDadosServ.status = 1;
			LogErroDAO.getInstance().insertLogErro(e);
		}
		
    }

	public void setParametrosPost(Map<String, Object> parametrosPost) {
		this.parametrosPost = parametrosPost;
	}

	private String getQueryString(Map<String, Object> params){
		if (params == null || params.size() == 0) {
			return null;
		}
		String urlParams = null;
		Iterator<String> e = (Iterator<String>) params.keySet().iterator();
		while (e.hasNext()) {
			String chave = (String) e.next();
			Object objValor = params.get(chave);
			String valor = objValor.toString();
			urlParams = urlParams == null ? "" : urlParams + "&";
			urlParams += chave + "=" + valor;
		}
		return urlParams;
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
