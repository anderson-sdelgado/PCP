package br.com.usinasantafe.pcp.control;

import android.app.ProgressDialog;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.AtualAplicBean;
import br.com.usinasantafe.pcp.model.bean.estaticas.ColabBean;
import br.com.usinasantafe.pcp.model.bean.estaticas.EquipBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.LogErroBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.LogProcessoBean;
import br.com.usinasantafe.pcp.model.dao.ColabDAO;
import br.com.usinasantafe.pcp.model.dao.ConfigDAO;
import br.com.usinasantafe.pcp.model.dao.EquipDAO;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.LogProcessoDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipProprioDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipSegProprioDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipVisitTercDAO;
import br.com.usinasantafe.pcp.model.dao.VisitanteDAO;
import br.com.usinasantafe.pcp.util.AtualDadosServ;
import br.com.usinasantafe.pcp.util.VerifDadosServ;

public class ConfigCTR {

    public boolean hasElemConfig(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.hasElements();
    }

    public boolean hasElemVisitante(){
        VisitanteDAO visitanteDAO = new VisitanteDAO();
        return visitanteDAO.hasElements();
    }

    public boolean verSenha(String senha){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.verSenha(senha);
    }

    public boolean verColab(Long matricColab){
        ColabDAO colabDAO = new ColabDAO();
        return colabDAO.verColab(matricColab);
    }

    public boolean verEquipNro(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        return equipDAO.verEquipNro(nroEquip);
    }

    public ColabBean getColab(Long matricColab){
        ColabDAO colabDAO = new ColabDAO();
        return colabDAO.getColab(matricColab);
    }

    public ConfigBean getConfig(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.getConfig();
    }

    public EquipBean getEquipId(Long idEquip) {
        EquipDAO equipDAO = new EquipDAO();
        return equipDAO.getEquipId(idEquip);
    }

    public EquipBean getEquipNro(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        return equipDAO.getEquipNro(nroEquip);
    }

    public void salvarConfig(Long numLinha, String senha){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.salvarConfig(numLinha, senha);
    }

    public void setPosicaoTela(Long posicaoTela){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.setPosicaoTela(posicaoTela);
    }

    public void setMatricVigia(Long matricVigia){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.setMatricVigia(matricVigia);
    }

    public void setPosicaoListaMov(Long posicaoListaMov){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.setPosicaoListaMov(posicaoListaMov);
    }

    public void setTipoMov(Long tipoMov){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.setTipoMov(tipoMov);
    }

    ////////////////////////////////////// ATUALIZAR DADOS ////////////////////////////////////////

    public void atualTodasTabelas(Context tela, ProgressDialog progressDialog, String activity){
        LogProcessoDAO.getInstance().insertLogProcesso("AtualDadosServ.getInstance().atualTodasTabBD(tela, progressDialog, activity);", activity);
        AtualDadosServ.getInstance().atualTodasTabBD(tela, progressDialog, activity);
    }

    public void atualDados(String tipoAtual, int tipoReceb, String activity) {
        LogProcessoDAO.getInstance().insertLogProcesso("ArrayList classeArrayList = classeAtual(tipoAtual);\n" +
                "        AtualDadosServ.getInstance().atualGenericoBD(classeArrayList, tipoReceb, activity);", activity);
        ArrayList classeArrayList = classeAtual(tipoAtual);
        AtualDadosServ.getInstance().atualGenericoBD(classeArrayList, tipoReceb, activity);
    }

    public void atualDados(Context telaAtual, Class telaProx, ProgressDialog progressDialog, String tipoAtual, int tipoReceb, String activity) {
        LogProcessoDAO.getInstance().insertLogProcesso("ArrayList classeArrayList = classeAtual(tipoAtual);\n" +
                "        AtualDadosServ.getInstance().atualGenericoBD(telaAtual, telaProx, progressDialog, classeArrayList, tipoReceb, activity);", activity);
        ArrayList classeArrayList = classeAtual(tipoAtual);
        AtualDadosServ.getInstance().atualGenericoBD(telaAtual, telaProx, progressDialog, classeArrayList, tipoReceb, activity);
    }

    public void atualDados(Context telaAtual, Class telaProx, ProgressDialog progressDialog, String tipoAtual, int tipoReceb, String activity, Class telaProxAlt, String dado) {
        LogProcessoDAO.getInstance().insertLogProcesso("ArrayList classeArrayList = classeAtual(tipoAtual);\n" +
                "        AtualDadosServ.getInstance().atualGenericoBD(telaAtual, telaProx, progressDialog, classeArrayList, tipoReceb, activity, telaProxAlt, dado);", activity);
        ArrayList classeArrayList = classeAtual(tipoAtual);
        AtualDadosServ.getInstance().atualGenericoBD(telaAtual, telaProx, progressDialog, classeArrayList, tipoReceb, activity, telaProxAlt, dado);
    }

    public ArrayList<String> classeAtual(String tipoAtual){
        ArrayList<String> classeArrayList = new ArrayList();
        switch (tipoAtual) {
            case "Colab":
                classeArrayList.add("ColabBean");
                break;
            case "Equip":
                classeArrayList.add("EquipBean");
                break;
            case "VisitanteTerceiro":
                classeArrayList.add("VisitanteBean");
                classeArrayList.add("TerceiroBean");
                break;
        }
        return classeArrayList;
    }


    public AtualAplicBean recAtual(String result) {

        AtualAplicBean atualAplicBean = new AtualAplicBean();

        try {

            JSONObject jObj = new JSONObject(result);
            JSONArray jsonArray = jObj.getJSONArray("dados");

            if (jsonArray.length() > 0) {
                ConfigDAO configDAO = new ConfigDAO();
                atualAplicBean = configDAO.recAtual(jsonArray);
            }

        } catch (Exception e) {
            VerifDadosServ.status = 1;
            LogErroDAO.getInstance().insertLogErro(e);
        }
        return atualAplicBean;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////


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
