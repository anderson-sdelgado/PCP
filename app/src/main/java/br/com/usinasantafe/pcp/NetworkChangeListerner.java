package br.com.usinasantafe.pcp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.util.ConnectNetwork;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;
import br.com.usinasantafe.pcp.util.Tempo;
import br.com.usinasantafe.pcp.view.ActivityGeneric;


public class NetworkChangeListerner extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        logConfig();
//        logProcesso();
//        logErro();
//        logBaseDados();
        if(ConnectNetwork.isConnected(context)){
            ActivityGeneric.connectNetwork = true;
            LogProcessoDAO.getInstance().insertLogProcesso("if(ConnectNetwork.isConnected(context)){\n" +
                    "            ActivityGeneric.connectNetwork = true;\n" +
                    "Tempo.getInstance().zerarDifTempo()", context.getClass().getName());
            Tempo.getInstance().zerarDifTempo();
            if (EnvioDadosServ.status == 1) {
                LogProcessoDAO.getInstance().insertLogProcesso("if (EnvioDadosServ.status == 1) {\n" +
                        "EnvioDadosServ.getInstance().envioDados(context.getClass().getName());", context.getClass().getName());
                EnvioDadosServ.getInstance().envioDados(context.getClass().getName());
            }
        }
        else{
            LogProcessoDAO.getInstance().insertLogProcesso("else{\n" +
                    "            ActivityGeneric.connectNetwork = false;", context.getClass().getName());
            ActivityGeneric.connectNetwork = false;
        }
    }
//
//    public void logConfig(){
//        ConfigBean configBean = new ConfigBean();
//        List<ConfigBean> configList = configBean.all();
//        for(ConfigBean configBeanBD : configList){
//            Log.i("PMM", dadosConfig(configBeanBD));
//        }
//    }
//
//    private String dadosConfig(ConfigBean configBean){
//        Gson gsonCabec = new Gson();
//        return gsonCabec.toJsonTree(configBean, configBean.getClass()).toString();
//    }
//
//    public void logProcesso(){
//        LogProcessoBean logProcessoBean = new LogProcessoBean();
//        List<LogProcessoBean> logProcessoList = logProcessoBean.orderBy("idLogProcesso", false);
//        for(LogProcessoBean logProcessoBeanBD : logProcessoList){
//            Log.i("PMM", dadosProcesso(logProcessoBeanBD));
//        }
//    }
//
//    private String dadosProcesso(LogProcessoBean logProcessoBean){
//        Gson gsonCabec = new Gson();
//        return gsonCabec.toJsonTree(logProcessoBean, logProcessoBean.getClass()).toString();
//    }
//
//    public void logErro(){
//        LogErroBean logErroBean = new LogErroBean();
//        List<LogErroBean> logErroList = logErroBean.orderBy("idLogErro", false);
//        Log.i("PMM", "Log Erro");
//        for(LogErroBean logErroBeanBD : logErroList){
//            Log.i("PMM", dadosErro(logErroBeanBD));
//        }
//    }
//
//    private String dadosErro(LogErroBean logErroBean){
//        Gson gsonCabec = new Gson();
//        return gsonCabec.toJsonTree(logErroBean, logErroBean.getClass()).toString();
//    }
//
//    public void logBaseDados(){
//        ConfigCTR configCTR = new ConfigCTR();
//        List<String> logBaseDadoList = configCTR.logBaseDadoList();
//        Log.i("PMM", "Log Base Dados");
//        for(String s : logBaseDadoList){
//            Log.i("PMM", s);
//        }
//    }
//

}
