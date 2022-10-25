package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;
import br.com.usinasantafe.pcp.model.pst.EspecificaPesquisa;

public class MovEquipVisitTercDAO {

    public MovEquipVisitTercDAO() {
    }

    public boolean verMovEquipVisitTercAberto(){
        List<MovEquipVisitTercBean> boletimMMList = movEquipVisitTercAbertoList();
        boolean ret = (boletimMMList.size() > 0);
        boletimMMList.clear();
        return ret;
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercAbertoList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovAberto());
        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        return movEquipVisitTercBean.get(pesqArrayList);
    }


    public ArrayList<String> movEquipVisitTercAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("MovEquipVisitTerc");
        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercBean.orderBy("idMovEquipVisitTerc", true);
        for (MovEquipVisitTercBean movEquipVisitTercBeanBD : movEquipVisitTercList) {
            dadosArrayList.add(dadosEquipVisitTerc(movEquipVisitTercBeanBD));
        }
        movEquipVisitTercList.clear();
        return dadosArrayList;
    }

    private String dadosEquipVisitTerc(MovEquipVisitTercBean movEquipVisitTercBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(movEquipVisitTercBean, movEquipVisitTercBean.getClass()).toString();
    }


    private EspecificaPesquisa getPesqMovAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipVisitTerc");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
