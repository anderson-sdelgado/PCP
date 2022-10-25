package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipSegProprioBean;

public class MovEquipSegProprioDAO {

    public MovEquipSegProprioDAO() {
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

}
