package br.com.usinasantafe.pcp.control;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.LogErroBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.LogProcessoBean;
import br.com.usinasantafe.pcp.model.dao.ConfigDAO;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipProprioDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipSegProprioDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipVisitTercDAO;
import br.com.usinasantafe.pcp.util.AtualDadosServ;

public class ConfigCTR {

    public boolean hasElemConfig(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.hasElements();
    }

    public ConfigBean getConfig(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.getConfig();
    }

    public void salvarConfig(Long numLinha, String senha){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.salvarConfig(numLinha, senha);
    }

    public boolean verSenha(String senha){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.verSenha(senha);
    }

    ////////////////////////////////////// ATUALIZAR DADOS ////////////////////////////////////////

    public void atualTodasTabelas(Context tela, ProgressDialog progressDialog, String activity){
        LogProcessoDAO.getInstance().insertLogProcesso("AtualDadosServ.getInstance().atualTodasTabBD(tela, progressDialog, activity);", activity);
        AtualDadosServ.getInstance().atualTodasTabBD(tela, progressDialog, activity);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////// LOG ///////////////////////////////////////////////

    public List<LogProcessoBean> logProcessoList(){
        LogProcessoDAO logProcessoDAO = new LogProcessoDAO();
        return logProcessoDAO.logProcessoList();
    }

    public ArrayList<String> logBaseDadoList(){
        ArrayList<String> dadosArrayList = new ArrayList<>();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipSegProprioDAO movEquipSegProprioDAO = new MovEquipSegProprioDAO();
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        dadosArrayList = movEquipProprioDAO.movEquipProprioAllArrayList(dadosArrayList);
        dadosArrayList = movEquipSegProprioDAO.movEquipSegProprioAllArrayList(dadosArrayList);
        dadosArrayList = movEquipVisitTercDAO.movEquipVisitTercAllArrayList(dadosArrayList);
        return dadosArrayList;
    }

    public List<LogErroBean> logErroList(){
        LogErroDAO logErroDAO = new LogErroDAO();
        return logErroDAO.logErroBeanList();
    }

    public void deleteLogs(){
        LogProcessoDAO logProcessoDAO = new LogProcessoDAO();
        LogErroDAO logErroDAO = new LogErroDAO();
        logProcessoDAO.deleteLogProcesso();
        logErroDAO.deleteLogErro();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
}
