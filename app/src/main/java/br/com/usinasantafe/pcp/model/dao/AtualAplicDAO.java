package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.math.BigInteger;
import java.security.MessageDigest;

import br.com.usinasantafe.pcp.BuildConfig;
import br.com.usinasantafe.pcp.model.bean.AtualAplicBean;

public class AtualAplicDAO {

    public String dadosAplic(Long nroAparelho, String versaoAplic){

        AtualAplicBean atualAplicBean = new AtualAplicBean();
        atualAplicBean.setVersao(versaoAplic);
        atualAplicBean.setNroAparelho(nroAparelho);;

        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(atualAplicBean, atualAplicBean.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        return json.toString();
    }

    public String getAtualBDToken(){
        AtualAplicBean atualAplicBean = new AtualAplicBean();
        return getToken(atualAplicBean);
    }

    private String getToken(AtualAplicBean atualAplicBean){

        String token = "";

        try {

            ConfigDAO configDAO = new ConfigDAO();
            token = "PCP-VERSAO_" + BuildConfig.VERSION_NAME + "-" + configDAO.getConfig().getNroAparelhoConfig();
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(token.getBytes(),0, token.length());
            token = (new BigInteger(1, m.digest()).toString(16).toUpperCase());

        } catch (Exception e) {
            LogErroDAO.getInstance().insertLogErro(e);
        }

        atualAplicBean.setToken(token);
        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(atualAplicBean, atualAplicBean.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        return json.toString();

    }

    public String token(){

        String token = "";

        try {

            ConfigDAO configDAO = new ConfigDAO();
            token = "PCP-VERSAO_" + BuildConfig.VERSION_NAME + "-" + configDAO.getConfig().getNroAparelhoConfig();
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(token.getBytes(),0, token.length());
            token = (new BigInteger(1, m.digest()).toString(16).toUpperCase());

        } catch (Exception e) {
            LogErroDAO.getInstance().insertLogErro(e);
        }

        return token;

    }
}