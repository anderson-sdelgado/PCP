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

    public List<MovEquipProprioSegBean> movEquipSegProprioIdMovEquipList(Long idMovEquipProprio){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdMovEquip(idMovEquipProprio));
        MovEquipProprioSegBean movEquipProprioSegBean = new MovEquipProprioSegBean();
        return movEquipProprioSegBean.get(pesqArrayList);
    }

    public List<MovEquipProprioSegBean> movEquipSegProprioIdMovEquipSegList(Long idMovEquipSegProprio){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdMovEquipSeg(idMovEquipSegProprio));
        MovEquipProprioSegBean movEquipProprioSegBean = new MovEquipProprioSegBean();
        return movEquipProprioSegBean.get(pesqArrayList);
    }

    public void inserirMovEquipSegProprio(Long idMovEquipProprio, Long idEquip){
        MovEquipProprioSegBean movEquipProprioSegBean = new MovEquipProprioSegBean();
        movEquipProprioSegBean.setIdMovEquipProprio(idMovEquipProprio);
        movEquipProprioSegBean.setIdEquipMovEquipProprioSeg(idEquip);
        movEquipProprioSegBean.insert();
    }

    public void deleteMovEquipSegProprioIdMovEquip(Long idMovEquipProprio){
        List<MovEquipProprioSegBean> movEquipSegProprioList = movEquipSegProprioIdMovEquipList(idMovEquipProprio);
        for (MovEquipProprioSegBean movEquipProprioSegBean : movEquipSegProprioList){
            movEquipProprioSegBean.delete();
        }
        movEquipSegProprioList.clear();
    }

    public void deleteMovEquipProprioIdMovEquipSeg(Long idMovEquipSegProprio){
        List<MovEquipProprioSegBean> movEquipSegProprioList = movEquipSegProprioIdMovEquipSegList(idMovEquipSegProprio);
        MovEquipProprioSegBean movEquipProprioSegBean = movEquipSegProprioList.get(0);
        movEquipProprioSegBean.delete();
        movEquipSegProprioList.clear();
    }

    public ArrayList<String> movEquipSegProprioAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("MovEquipSegProprio");
        MovEquipProprioSegBean movEquipProprioSegBean = new MovEquipProprioSegBean();
        List<MovEquipProprioSegBean> movEquipSegProprioList = movEquipProprioSegBean.orderBy("idMovEquipSegProprio", true);
        for (MovEquipProprioSegBean movEquipProprioSegBeanBD : movEquipSegProprioList) {
            dadosArrayList.add(dadosMovEquipSegProprio(movEquipProprioSegBeanBD));
        }
        movEquipSegProprioList.clear();
        return dadosArrayList;
    }

    private String dadosMovEquipSegProprio(MovEquipProprioSegBean movEquipProprioSegBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(movEquipProprioSegBean, movEquipProprioSegBean.getClass()).toString();
    }

    public String dadosEnvioMovEquipSegProprio(List<MovEquipProprioSegBean> movEquipSegProprioList){

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
        return movEquipProprioSegBean.inAndOrderBy("idMovEquipProprio", idMovEquipProprioList, "idMovEquipSegProprio", true);
    }

    private EspecificaPesquisa getPesqIdMovEquip(Long idMovEquip){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipProprio");
        pesquisa.setValor(idMovEquip);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdMovEquipSeg(Long idMovEquipSegProprio){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipSegProprio");
        pesquisa.setValor(idMovEquipSegProprio);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
