package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioPassagBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioSegBean;
import br.com.usinasantafe.pcp.model.pst.EspecificaPesquisa;

public class MovEquipProprioPassagDAO {

    private MovEquipProprioPassagBean movEquipProprioPassagBean;

    public MovEquipProprioPassagDAO() {
    }

    public MovEquipProprioPassagBean getMovEquipProprioPassagBean() {
        return movEquipProprioPassagBean;
    }

    public void setMatricPassagMovEquipProprioPassag(Long matricColab) {
        movEquipProprioPassagBean = new MovEquipProprioPassagBean();
        movEquipProprioPassagBean.setMatricColabMovEquipProprioPassag(matricColab);
    }

    public List<MovEquipProprioPassagBean> movEquipProprioPassagIdMovEquipList(Long idMovEquipProprio){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdMovEquip(idMovEquipProprio));
        MovEquipProprioPassagBean movEquipProprioPassagBean = new MovEquipProprioPassagBean();
        return movEquipProprioPassagBean.get(pesqArrayList);
    }

    public List<MovEquipProprioPassagBean> movEquipProprioPassagIdMovEquipPassagList(Long idMovEquipProprioPassag){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqIdMovEquipPassag(idMovEquipProprioPassag));
        MovEquipProprioPassagBean movEquipProprioPassagBean = new MovEquipProprioPassagBean();
        return movEquipProprioPassagBean.get(pesqArrayList);
    }

    public void inserirMovEquipProprioPassag(Long idMovEquipProprio, Long matricColab){
        MovEquipProprioPassagBean movEquipProprioPassagBean = new MovEquipProprioPassagBean();
        movEquipProprioPassagBean.setIdMovEquipProprio(idMovEquipProprio);
        movEquipProprioPassagBean.setMatricColabMovEquipProprioPassag(matricColab);
        movEquipProprioPassagBean.insert();
    }

    public void deleteMovEquipProprioPassagIdMovEquip(Long idMovEquipProprio){
        List<MovEquipProprioPassagBean> movEquipProprioPassagList = movEquipProprioPassagIdMovEquipList(idMovEquipProprio);
        for (MovEquipProprioPassagBean movEquipProprioPassagBean : movEquipProprioPassagList){
            movEquipProprioPassagBean.delete();
        }
        movEquipProprioPassagList.clear();
    }

    public void deleteMovEquipProprioPassagIdMovEquipPassag(Long idMovEquipProprioPassag){
        List<MovEquipProprioPassagBean> movEquipProprioPassagList = movEquipProprioPassagIdMovEquipPassagList(idMovEquipProprioPassag);
        MovEquipProprioPassagBean movEquipProprioPassagBean = movEquipProprioPassagList.get(0);
        movEquipProprioPassagBean.delete();
        movEquipProprioPassagList.clear();
    }

    public ArrayList<String> movEquipProprioPassagAllArrayList(ArrayList<String> dadosArrayList){
        dadosArrayList.add("MovEquipProprioPassag");
        MovEquipProprioPassagBean movEquipProprioPassagBean = new MovEquipProprioPassagBean();
        List<MovEquipProprioPassagBean> movEquipProprioPassagList = movEquipProprioPassagBean.orderBy("idMovEquipProprioPassag", true);
        for (MovEquipProprioPassagBean movEquipProprioPassagBeanBD : movEquipProprioPassagList) {
            dadosArrayList.add(dadosMovEquipProprioPassag(movEquipProprioPassagBeanBD));
        }
        movEquipProprioPassagList.clear();
        return dadosArrayList;
    }

    private String dadosMovEquipProprioPassag(MovEquipProprioPassagBean movEquipProprioPassagBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(movEquipProprioPassagBean, movEquipProprioPassagBean.getClass()).toString();
    }

    public String dadosEnvioMovEquipProprioPassag(List<MovEquipProprioPassagBean> movEquipProprioPassagList){

        JsonArray jsonArrayApont = new JsonArray();

        for (MovEquipProprioPassagBean movEquipProprioPassagBean : movEquipProprioPassagList) {
            Gson gsonMovEquipProprioPassag = new Gson();
            jsonArrayApont.add(gsonMovEquipProprioPassag.toJsonTree(movEquipProprioPassagBean, movEquipProprioPassagBean.getClass()));
        }

        movEquipProprioPassagList.clear();

        JsonObject jsonMovEquipProprioPassag = new JsonObject();
        jsonMovEquipProprioPassag.add("movequippropriopassag", jsonArrayApont);

        return jsonMovEquipProprioPassag.toString();

    }

    public List<MovEquipProprioPassagBean> movEquipProprioPassagEnvioList(ArrayList<Long> idMovEquipProprioList){
        MovEquipProprioPassagBean movEquipProprioPassagBean = new MovEquipProprioPassagBean();
        return movEquipProprioPassagBean.inAndOrderBy("idMovEquipProprio", idMovEquipProprioList, "idMovEquipProprioPassag", true);
    }

    private EspecificaPesquisa getPesqIdMovEquip(Long idMovEquip){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipProprio");
        pesquisa.setValor(idMovEquip);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdMovEquipPassag(Long idMovEquipProprioPassag){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipProprioPassag");
        pesquisa.setValor(idMovEquipProprioPassag);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
