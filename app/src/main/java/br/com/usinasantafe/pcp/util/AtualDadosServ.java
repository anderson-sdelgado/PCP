package br.com.usinasantafe.pcp.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import br.com.usinasantafe.pcp.control.ConfigCTR;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.model.pst.GenericRecordable;
import br.com.usinasantafe.pcp.util.conHttp.GetBDGenerico;
import br.com.usinasantafe.pcp.util.conHttp.UrlsConexaoHttp;

public class AtualDadosServ {

	private ArrayList tabAtualArrayList;
	private static AtualDadosServ instance = null;
	private int contAtualBD = 0;
	private String classe = "";
	private ProgressDialog progressDialog;
	private int qtdeBD = 0;
	private GenericRecordable genericRecordable;
	private Context telaAtual;
	private Class telaProx;
	private int tipoReceb;
	private Class telaProxAlt;
	private String dado;
	
	public AtualDadosServ() {
		genericRecordable = new GenericRecordable();
	}
	
    public static AtualDadosServ getInstance() {
        if (instance == null)
        instance = new AtualDadosServ();
        return instance;
    }
	
	@SuppressWarnings("unchecked")
	public void manipularDadosHttp(String tipo, String result, String activity){

		LogProcessoDAO.getInstance().insertLogProcesso("if(!result.equals(\"\")){", activity);
		if(!result.equals("")){

			try{

				Log.i("PMM", "TIPO -> " + tipo);
				Log.i("PMM", "RESULT -> " + result);

				JSONObject jObj = new JSONObject(result);
				JSONArray jsonArray = jObj.getJSONArray("dados");
				Class classe = Class.forName(manipLocalClasse(tipo));

				LogProcessoDAO.getInstance().insertLogProcesso("genericRecordable.deleteAll('" + classe + "');", activity);
				genericRecordable.deleteAll(classe);

				for(int i = 0; i < jsonArray.length(); i++){
					JSONObject objeto = jsonArray.getJSONObject(i);
					Gson gson = new Gson();
					genericRecordable.insert(gson.fromJson(objeto.toString(), classe), classe);
				}

				if(contAtualBD > 0){
					LogProcessoDAO.getInstance().insertLogProcesso("if(contAtualBD > 0){\n" +
							"\t\t\t\t\tatualizandoBD(activity);", activity);
					atualizandoBD(activity);
				}

			}
			catch (Exception e) {
				LogErroDAO.getInstance().insertLogErro(e);
			}

		} else {
			LogProcessoDAO.getInstance().insertLogProcesso("} else {\n" +
					"\t\t\tencerrar(activity);", activity);
			encerrar(activity);
		}

	}
	

	public void atualTodasTabBD(Context telaAtual, ProgressDialog progressDialog, String activity){

		this.tipoReceb = 1;
		this.telaAtual = telaAtual;
		this.progressDialog = progressDialog;

		allClasses();
		startAtualizacao(activity);

	}

	public void atualGenericoBD(ArrayList classeArrayList, int tipoReceb, String activity){

		this.tipoReceb = tipoReceb;

		selecionarClasses(classeArrayList);
		startAtualizacao(activity);

	}

	public void atualGenericoBD(Context telaAtual, Class telaProx, ProgressDialog progressDialog, ArrayList classeArrayList, int tipoReceb, String activity){

		this.tipoReceb = tipoReceb;
		this.telaAtual = telaAtual;
		this.telaProx = telaProx;
		this.progressDialog = progressDialog;

		selecionarClasses(classeArrayList);
		startAtualizacao(activity);

	}

	public void atualGenericoBD(Context telaAtual, Class telaProx, ProgressDialog progressDialog, ArrayList classeArrayList, int tipoReceb, String activity, Class telaProxAlt, String dado){

		this.tipoReceb = tipoReceb;
		this.telaAtual = telaAtual;
		this.telaProx = telaProx;
		this.progressDialog = progressDialog;
		this.telaProxAlt = telaProxAlt;
		this.dado = dado;

		selecionarClasses(classeArrayList);
		startAtualizacao(activity);

	}

	public void allClasses(){

		try {

			tabAtualArrayList = new ArrayList();

			Class<?> retClasse = Class.forName(UrlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				if(campo.contains("Bean")){
					tabAtualArrayList.add(campo);
				}
			}

		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
		}

	}

	public void selecionarClasses(ArrayList classeArrayList){

		try {

			tabAtualArrayList = new ArrayList();

			Class<?> retClasse = Class.forName(UrlsConexaoHttp.localUrl);

			for (Field field : retClasse.getDeclaredFields()) {
				String campo = field.getName();
				for (int i = 0; i < classeArrayList.size(); i++) {
					String classe = (String) classeArrayList.get(i);
					if(campo.equals(classe)){
						tabAtualArrayList.add(campo);
					}
				}
			}

		} catch (Exception e) {
			LogErroDAO.getInstance().insertLogErro(e);
		}

	}

	public void startAtualizacao(String activity){

		classe = (String) tabAtualArrayList.get(contAtualBD);
		String[] url = {classe, activity};
		contAtualBD++;

		LogProcessoDAO.getInstance().insertLogProcesso("getBDGenerico.execute('" + classe + "');", activity);
		GetBDGenerico getBDGenerico = new GetBDGenerico();
		getBDGenerico.execute(url);

	}

	public void atualizandoBD(String activity){

		LogProcessoDAO.getInstance().insertLogProcesso("public void atualizandoBD(String activity){\n" +
				"qtdeBD = tabAtualArrayList.size();", activity);
		qtdeBD = tabAtualArrayList.size();
		if(contAtualBD < tabAtualArrayList.size()){

			LogProcessoDAO.getInstance().insertLogProcesso("if(contAtualBD < tabAtualArrayList.size()){\n" +
					"this.progressDialog.setProgress((contAtualBD * 100) / qtdeBD);\n" +
					"        classe = (String) tabAtualArrayList.get(contAtualBD);\n" +
					"String[] url = {classe, activity};\n" +
					"contAtualBD++;\n" +
					"GetBDGenerico getBDGenerico = new GetBDGenerico();\n" +
					"        getBDGenerico.execute(url);\n" +
					"        getBDGenerico.execute('" + classe + "');", activity);
			if(this.tipoReceb < 4) {
				this.progressDialog.setProgress((contAtualBD * 100) / qtdeBD);
			}

			classe = (String) tabAtualArrayList.get(contAtualBD);
			String[] url = {classe, activity};
			contAtualBD++;
			GetBDGenerico getBDGenerico = new GetBDGenerico();
			getBDGenerico.execute(url);

		} else {

			LogProcessoDAO.getInstance().insertLogProcesso("} else {", activity);
			contAtualBD = 0;
			if(this.tipoReceb < 4) {
				this.progressDialog.dismiss();
			}

			if(this.tipoReceb == 1){

				LogProcessoDAO.getInstance().insertLogProcesso("if(this.tipoReceb == 1){\n" +
						"contAtualBD = 0;\n" +
						"this.progressDialog.dismiss();\n" +
						"AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);\n" +
						"alerta.setTitle(\"ATENCAO\");\n" +
						"alerta.setMessage(\"ATUALIZAÇÃO REALIZADA COM SUCESSO OS DADOS.\");", activity);

				AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
				alerta.setTitle("ATENCAO");
				alerta.setMessage("ATUALIZAÇÃO REALIZADA COM SUCESSO OS DADOS.");
				alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
								"@Override\n" +
								"public void onClick(DialogInterface dialog, int which) {", activity);
					}
				});

				alerta.show();

			} else if(this.tipoReceb == 2) {

				LogProcessoDAO.getInstance().insertLogProcesso("} else if(this.tipoReceb == 2) {\n" +
						"contAtualBD = 0;\n" +
						"this.progressDialog.dismiss();\n" +
						"AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);\n" +
						"alerta.setTitle(\"ATENCAO\");\n" +
						"alerta.setMessage(\"ATUALIZAÇÃO REALIZADA COM SUCESSO OS DADOS.\");", activity);
				AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
				alerta.setTitle("ATENCAO");
				alerta.setMessage("ATUALIZAÇÃO REALIZADA COM SUCESSO OS DADOS.");
				alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
								"@Override\n" +
								"public void onClick(DialogInterface dialog, int which) {\n" +
								"progressDialog.dismiss();\n" +
								"Intent it = new Intent(telaAtual, telaProx);\n" +
								"telaAtual.startActivity(it);", activity);
						Intent it = new Intent(telaAtual, telaProx);
						telaAtual.startActivity(it);
					}
				});
				alerta.show();

			} else if(this.tipoReceb == 3) {

				ConfigCTR configCTR = new ConfigCTR();
				if(configCTR.verPropriedade(Long.parseLong(dado))){

					Intent it = new Intent(telaAtual, telaProxAlt);
					telaAtual.startActivity(it);

				} else {

					LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
							"                        AlertDialog.Builder alerta = new AlertDialog.Builder(PropriedadeActivity.this);\n" +
							"                        alerta.setTitle(\"ATENÇÃO\");\n" +
							"                        alerta.setMessage(\"CÓDIGO DA SEÇÃO INCORRETO, POR FAVOR CERTIFIQUE-SE DE QUE O CÓDIGO REGISTRADO ESTÁ CORRETO OU POSSUI O.S. DE COLHEITA ABERTA E TENTE NOVAMENTE.\");", activity);
					AlertDialog.Builder alerta = new AlertDialog.Builder(telaAtual);
					alerta.setTitle("ATENÇÃO");
					alerta.setMessage("CÓDIGO DA SEÇÃO INCORRETO, POR FAVOR CERTIFIQUE-SE DE QUE O CÓDIGO REGISTRADO ESTÁ CORRETO OU POSSUI O.S. DE COLHEITA ABERTA E TENTE NOVAMENTE.");
					alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
									"                            @Override\n" +
									"                            public void onClick(DialogInterface dialog, int which) {", activity);
						}
					});
					alerta.show();

				}

			}
		}

	}

	public void encerrar(String activity){

		LogProcessoDAO.getInstance().insertLogProcesso("public void encerrar(String activity){", activity);
		if(this.tipoReceb == 1){
			LogProcessoDAO.getInstance().insertLogProcesso("if(this.tipoReceb == 1){\n" +
					"this.progressDialog.dismiss();\n" +
					"AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);\n" +
					"alerta.setTitle(\"ATENCAO\");\n" +
					"alerta.setMessage(\"FALHA NA CONEXAO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.\");", activity);
			this.progressDialog.dismiss();
			AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
			alerta.setTitle("ATENCAO");
			alerta.setMessage("FALHA NA CONEXAO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
			alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					LogProcessoDAO.getInstance().insertLogProcesso("alerta.setPositiveButton(\"OK\", new DialogInterface.OnClickListener() {\n" +
							"@Override\n" +
							"public void onClick(DialogInterface dialog, int which) {", activity);
				}
			});
			alerta.show();
			
		}
	}

	public String manipLocalClasse(String classe){
	    if(classe.contains("Bean")){
	    	classe = UrlsConexaoHttp.localPSTEstatica + classe;
	    }
		return classe;
	}

}
