package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.pst.EspecificaPesquisa;

public class MovEquipProprioDAO {

    public MovEquipProprioDAO() {
    }

    public boolean verMovEquipProprioAberto(){
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioAbertoList();
        boolean ret = (movEquipProprioList.size() > 0);
        movEquipProprioList.clear();
        return ret;
    }

    public List<MovEquipProprioBean> movEquipProprioAbertoList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovAberto());
        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        return movEquipProprioBean.get(pesqArrayList);
    }

    public ArrayList<String> movEquipProprioAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("MovEquipProprio");
        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioBean.orderBy("idMovEquipProprio", true);
        for (MovEquipProprioBean movEquipProprioBeanBD : movEquipProprioList) {
            dadosArrayList.add(dadosMovEquipProprio(movEquipProprioBeanBD));
        }
        movEquipProprioList.clear();
        return dadosArrayList;
    }

    private String dadosMovEquipProprio(MovEquipProprioBean movEquipProprioBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(movEquipProprioBean, movEquipProprioBean.getClass()).toString();
    }

    private EspecificaPesquisa getPesqMovAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipProprio");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
