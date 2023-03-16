package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercPassagBean;
import br.com.usinasantafe.pcp.model.pst.EspecificaPesquisa;

public class MovEquipVisitTercPassagDAO {

    private MovEquipVisitTercPassagBean movEquipVisitTercPassagBean;

    public MovEquipVisitTercPassagDAO() {
    }

    public MovEquipVisitTercPassagBean getMovEquipVisitTercPassagBean() {
        return movEquipVisitTercPassagBean;
    }

    public void setIdVisitTercMovEquipVisitTercPassag(Long idVisitTerc) {
        movEquipVisitTercPassagBean = new MovEquipVisitTercPassagBean();
        movEquipVisitTercPassagBean.setIdVisitTercMovEquipVisitTercPassag(idVisitTerc);
    }

    public List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagIdMovEquipList(Long idMovEquipVisitTerc){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdMovEquip(idMovEquipVisitTerc));
        MovEquipVisitTercPassagBean movEquipVisitTercPassagBean = new MovEquipVisitTercPassagBean();
        return movEquipVisitTercPassagBean.get(pesqArrayList);
    }

    public List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagIdMovEquipPassagList(Long idMovEquipVisitTercPassag){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdMovEquipPassag(idMovEquipVisitTercPassag));
        MovEquipVisitTercPassagBean movEquipVisitTercPassagBean = new MovEquipVisitTercPassagBean();
        return movEquipVisitTercPassagBean.get(pesqArrayList);
    }

    public void inserirMovEquipVisitTercPassag(Long idMovEquipVisitTerc, Long idVisitTerc){
        MovEquipVisitTercPassagBean movEquipVisitTercPassagBean = new MovEquipVisitTercPassagBean();
        movEquipVisitTercPassagBean.setIdMovEquipVisitTerc(idMovEquipVisitTerc);
        movEquipVisitTercPassagBean.setIdVisitTercMovEquipVisitTercPassag(idVisitTerc);
        movEquipVisitTercPassagBean.insert();
    }

    public void deleteMovEquipVisitTercPassagIdMovEquip(Long idMovEquipVisitTerc){
        List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList = movEquipVisitTercPassagIdMovEquipList(idMovEquipVisitTerc);
        for (MovEquipVisitTercPassagBean movEquipVisitTercPassagBean : movEquipVisitTercPassagList){
            movEquipVisitTercPassagBean.delete();
        }
        movEquipVisitTercPassagList.clear();
    }

    public void deleteMovEquipVisitTercPassagIdMovEquipPassag(Long idMovEquipVisitTercPassag){
        List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList = movEquipVisitTercPassagIdMovEquipPassagList(idMovEquipVisitTercPassag);
        MovEquipVisitTercPassagBean movEquipVisitTercPassagBean = movEquipVisitTercPassagList.get(0);
        movEquipVisitTercPassagBean.delete();
        movEquipVisitTercPassagList.clear();
    }

    public ArrayList<String> movEquipVisitTercPassagAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("MovEquipVisitTercPassag");
        MovEquipVisitTercPassagBean movEquipVisitTercPassagBean = new MovEquipVisitTercPassagBean();
        List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList = movEquipVisitTercPassagBean.orderBy("idMovEquipVisitTercPassag", true);
        for (MovEquipVisitTercPassagBean movEquipVisitTercPassagBeanBD : movEquipVisitTercPassagList) {
            dadosArrayList.add(dadosMovEquipVisitTercPassag(movEquipVisitTercPassagBeanBD));
        }
        movEquipVisitTercPassagList.clear();
        return dadosArrayList;
    }

    private String dadosMovEquipVisitTercPassag(MovEquipVisitTercPassagBean movEquipVisitTercPassagBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(movEquipVisitTercPassagBean, movEquipVisitTercPassagBean.getClass()).toString();
    }

    public String dadosEnvioMovEquipVisitTercPassag(List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList){

        JsonArray jsonArrayVisitTercPassag = new JsonArray();

        for (MovEquipVisitTercPassagBean movEquipVisitTercPassagBean : movEquipVisitTercPassagList) {
            Gson gsonMovEquipVisitTercPassag = new Gson();
            jsonArrayVisitTercPassag.add(gsonMovEquipVisitTercPassag.toJsonTree(movEquipVisitTercPassagBean, movEquipVisitTercPassagBean.getClass()));
        }

        movEquipVisitTercPassagList.clear();

        JsonObject jsonMovEquipVisitTercPassag = new JsonObject();
        jsonMovEquipVisitTercPassag.add("movequipvisittercpassag", jsonArrayVisitTercPassag);

        return jsonMovEquipVisitTercPassag.toString();

    }

    public List<MovEquipVisitTercPassagBean> movEquipProprioPassagEnvioList(ArrayList<Long> idMovEquipVisitTercList){
        MovEquipVisitTercPassagBean movEquipVisitTercPassagBean = new MovEquipVisitTercPassagBean();
        return movEquipVisitTercPassagBean.inAndOrderBy("idMovEquipVisitTerc", idMovEquipVisitTercList, "idMovEquipVisitTercPassag", true);
    }

    private EspecificaPesquisa getPesqIdMovEquip(Long idMovEquip){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipVisitTerc");
        pesquisa.setValor(idMovEquip);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdMovEquipPassag(Long idMovEquipVisitTercPassag){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipVisitTercPassag");
        pesquisa.setValor(idMovEquipVisitTercPassag);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
