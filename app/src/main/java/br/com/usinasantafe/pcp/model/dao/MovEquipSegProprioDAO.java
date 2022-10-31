package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipSegProprioBean;
import br.com.usinasantafe.pcp.model.pst.EspecificaPesquisa;

public class MovEquipSegProprioDAO {

    public MovEquipSegProprioDAO() {
    }

    public List<MovEquipSegProprioBean> movEquipSegProprioIdMovEquipList(Long idMovEquipProprio){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdMovEquip(idMovEquipProprio));
        MovEquipSegProprioBean movEquipSegProprioBean = new MovEquipSegProprioBean();
        return movEquipSegProprioBean.get(pesqArrayList);
    }

    public void inserirMovEquipSegProprio(Long idMovEquipProprio, Long idEquip){
        MovEquipSegProprioBean movEquipSegProprioBean = new MovEquipSegProprioBean();
        movEquipSegProprioBean.setIdMovEquipProprio(idMovEquipProprio);
        movEquipSegProprioBean.setIdEquipMovEquipSegProprio(idEquip);
        movEquipSegProprioBean.insert();
    }

    public void deleteMovEquipSegProprio(Long idMovEquipProprio){
        List<MovEquipSegProprioBean> movEquipSegProprioList = movEquipSegProprioIdMovEquipList(idMovEquipProprio);
        for (MovEquipSegProprioBean movEquipSegProprioBean : movEquipSegProprioList){
            movEquipSegProprioBean.delete();
        }
    }

    public ArrayList<String> movEquipSegProprioAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("MovEquipSegProprio");
        MovEquipSegProprioBean movEquipSegProprioBean = new MovEquipSegProprioBean();
        List<MovEquipSegProprioBean> movEquipSegProprioList = movEquipSegProprioBean.orderBy("idMovEquipSegProprio", true);
        for (MovEquipSegProprioBean movEquipSegProprioBeanBD : movEquipSegProprioList) {
            dadosArrayList.add(dadosMovEquipSegProprio(movEquipSegProprioBeanBD));
        }
        movEquipSegProprioList.clear();
        return dadosArrayList;
    }

    private String dadosMovEquipSegProprio(MovEquipSegProprioBean movEquipSegProprioBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(movEquipSegProprioBean, movEquipSegProprioBean.getClass()).toString();
    }

    public String dadosEnvioMovEquipSegProprio(List<MovEquipSegProprioBean> movEquipSegProprioList){

        JsonArray jsonArrayApont = new JsonArray();

        for (MovEquipSegProprioBean movEquipSegProprioBean : movEquipSegProprioList) {
            Gson gsonItemImp = new Gson();
            jsonArrayApont.add(gsonItemImp.toJsonTree(movEquipSegProprioBean, movEquipSegProprioBean.getClass()));
        }

        movEquipSegProprioList.clear();

        JsonObject jsonMovEquipSegProprio = new JsonObject();
        jsonMovEquipSegProprio.add("movequipsegproprio", jsonArrayApont);

        return jsonMovEquipSegProprio.toString();

    }

    public List<MovEquipSegProprioBean> movEquipSegProprioEnvioList(ArrayList<Long> idMovEquipProprioList){
        MovEquipSegProprioBean movEquipSegProprioBean = new MovEquipSegProprioBean();
        return movEquipSegProprioBean.inAndOrderBy("idMovEquipProprio", idMovEquipProprioList, "idMovEquipSegProprio", true);
    }

    private EspecificaPesquisa getPesqIdMovEquip(Long idMovEquip){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipProprio");
        pesquisa.setValor(idMovEquip);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
