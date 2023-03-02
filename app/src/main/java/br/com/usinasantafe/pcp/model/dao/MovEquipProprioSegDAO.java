package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioSegBean;
import br.com.usinasantafe.pcp.model.pst.EspecificaPesquisa;

public class MovEquipProprioSegDAO {

    public MovEquipProprioSegDAO() {
    }

    public List<MovEquipProprioSegBean> movEquipProprioSegIdMovEquipList(Long idMovEquipProprio){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdMovEquip(idMovEquipProprio));
        MovEquipProprioSegBean movEquipProprioSegBean = new MovEquipProprioSegBean();
        return movEquipProprioSegBean.get(pesqArrayList);
    }

    public List<MovEquipProprioSegBean> movEquipProprioSegIdMovEquipSegList(Long idMovEquipSegProprio){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdMovEquipSeg(idMovEquipSegProprio));
        MovEquipProprioSegBean movEquipProprioSegBean = new MovEquipProprioSegBean();
        return movEquipProprioSegBean.get(pesqArrayList);
    }

    public void inserirMovEquipProprioSeg(Long idMovEquipProprio, Long idEquip){
        MovEquipProprioSegBean movEquipProprioSegBean = new MovEquipProprioSegBean();
        movEquipProprioSegBean.setIdMovEquipProprio(idMovEquipProprio);
        movEquipProprioSegBean.setIdEquipMovEquipProprioSeg(idEquip);
        movEquipProprioSegBean.insert();
    }

    public void deleteMovEquipProprioSegIdMovEquip(Long idMovEquipProprio){
        List<MovEquipProprioSegBean> movEquipSegProprioList = movEquipProprioSegIdMovEquipList(idMovEquipProprio);
        for (MovEquipProprioSegBean movEquipProprioSegBean : movEquipSegProprioList){
            movEquipProprioSegBean.delete();
        }
        movEquipSegProprioList.clear();
    }

    public void deleteMovEquipProprioSegIdMovEquipSeg(Long idMovEquipSegProprio){
        List<MovEquipProprioSegBean> movEquipSegProprioList = movEquipProprioSegIdMovEquipSegList(idMovEquipSegProprio);
        MovEquipProprioSegBean movEquipProprioSegBean = movEquipSegProprioList.get(0);
        movEquipProprioSegBean.delete();
        movEquipSegProprioList.clear();
    }

    public ArrayList<String> movEquipProprioSegAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("MovEquipProprioSeg");
        MovEquipProprioSegBean movEquipProprioSegBean = new MovEquipProprioSegBean();
        List<MovEquipProprioSegBean> movEquipProprioSegList = movEquipProprioSegBean.orderBy("idMovEquipSegProprio", true);
        for (MovEquipProprioSegBean movEquipProprioSegBeanBD : movEquipProprioSegList) {
            dadosArrayList.add(dadosMovEquipProprioSeg(movEquipProprioSegBeanBD));
        }
        movEquipProprioSegList.clear();
        return dadosArrayList;
    }

    private String dadosMovEquipProprioSeg(MovEquipProprioSegBean movEquipProprioSegBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(movEquipProprioSegBean, movEquipProprioSegBean.getClass()).toString();
    }

    public String dadosEnvioMovEquipProprioSeg(List<MovEquipProprioSegBean> movEquipSegProprioList){

        JsonArray jsonArrayApont = new JsonArray();

        for (MovEquipProprioSegBean movEquipProprioSegBean : movEquipSegProprioList) {
            Gson gsonMovEquipSegProprio = new Gson();
            jsonArrayApont.add(gsonMovEquipSegProprio.toJsonTree(movEquipProprioSegBean, movEquipProprioSegBean.getClass()));
        }

        movEquipSegProprioList.clear();

        JsonObject jsonMovEquipSegProprio = new JsonObject();
        jsonMovEquipSegProprio.add("movequipsegproprio", jsonArrayApont);

        return jsonMovEquipSegProprio.toString();

    }

    public List<MovEquipProprioSegBean> movEquipSegProprioEnvioList(ArrayList<Long> idMovEquipProprioList){
        MovEquipProprioSegBean movEquipProprioSegBean = new MovEquipProprioSegBean();
        return movEquipProprioSegBean.inAndOrderBy("idMovEquipProprio", idMovEquipProprioList, "idMovEquipProprioSeg", true);
    }

    private EspecificaPesquisa getPesqIdMovEquip(Long idMovEquip){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipProprio");
        pesquisa.setValor(idMovEquip);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdMovEquipSeg(Long idMovEquipProprioSeg){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipProprioSeg");
        pesquisa.setValor(idMovEquipProprioSeg);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
