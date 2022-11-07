package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.LogProcessoBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcp.util.Tempo;

public class MovEquipProprioDAO {

    public MovEquipProprioDAO() {
    }

    public boolean verMovEquipProprioAberto(){
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioAbertoList();
        boolean ret = (movEquipProprioList.size() > 0);
        movEquipProprioList.clear();
        return ret;
    }

    public boolean verMovEquipProprioFechado(){
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioFechadoList();
        boolean ret = (movEquipProprioList.size() > 0);
        movEquipProprioList.clear();
        return ret;
    }

    public MovEquipProprioBean getMovEquipProprioAberto(){
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioAbertoList();
        MovEquipProprioBean movEquipProprioBean = movEquipProprioList.get(0);
        movEquipProprioList.clear();
        return movEquipProprioBean;
    }

    public MovEquipProprioBean getMovEquipProprioId(Long idMovEquipProprio){
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioIdList(idMovEquipProprio);
        MovEquipProprioBean movEquipProprioBean = movEquipProprioList.get(0);
        movEquipProprioList.clear();
        return movEquipProprioBean;
    }

    public void abrirMovEquipProprio(Long tipoMov){
        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        movEquipProprioBean.setTipoMovEquipProprio(tipoMov);
        movEquipProprioBean.setStatusMovEquipProprio(1L);
        movEquipProprioBean.insert();
    }

    public void fecharMovEquipProprio(Long nroMatricVigia, String observacao){
        MovEquipProprioBean movEquipProprioBean = getMovEquipProprioAberto();
        movEquipProprioBean.setNroMatricVigiaMovEquipProprio(nroMatricVigia);
        movEquipProprioBean.setObservacaoMovEquipProprio(observacao);
        Long dthr = Tempo.getInstance().dthrAtualLong();
        movEquipProprioBean.setDthrLongMovEquipProprio(dthr);
        movEquipProprioBean.setDthrMovEquipProprio(Tempo.getInstance().dthrLongToString(dthr));
        movEquipProprioBean.setStatusMovEquipProprio(2L);
        movEquipProprioBean.update();
    }

    public void updateMovEquipProprioEnvio(ArrayList<Long> idMovEquipProprioArrayList){

        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioList(idMovEquipProprioArrayList);

        for (MovEquipProprioBean movEquipProprioBean : movEquipProprioList) {
            movEquipProprioBean.setStatusMovEquipProprio(3L);
            movEquipProprioBean.update();
        }

        movEquipProprioList.clear();
        idMovEquipProprioArrayList.clear();

    }

    public void deleteMovEquipProprio(Long idMovEquipProprio){
        MovEquipProprioBean movEquipProprioBean = getMovEquipProprioId(idMovEquipProprio);
        movEquipProprioBean.delete();
    }

    public ArrayList<Long> idMovEquipProprioExcluirArrayList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovEnviado());

        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioBean.get(pesqArrayList);

        ArrayList<Long> idMovEquipProprioList = new ArrayList<>();
        for (MovEquipProprioBean movEquipProprioBeanBD : movEquipProprioList) {
            if(movEquipProprioBeanBD.getDthrLongMovEquipProprio() < Tempo.getInstance().dthrLongDiaMenos(15)) {
                idMovEquipProprioList.add(movEquipProprioBeanBD.getIdMovEquipProprio());
            }
        }

        movEquipProprioList.clear();
        return idMovEquipProprioList;

    }

    public void setNroMatricColab(Long nroMatricColab){
        MovEquipProprioBean movEquipProprioBean = getMovEquipProprioAberto();
        movEquipProprioBean.setNroMatricColabMovEquipProprio(nroMatricColab);
        movEquipProprioBean.update();
    }

    public void setIdEquip(Long idEquip){
        MovEquipProprioBean movEquipProprioBean = getMovEquipProprioAberto();
        movEquipProprioBean.setIdEquipMovEquipProprio(idEquip);
        movEquipProprioBean.update();
    }

    public void setDescrDestino(String descrDestino){
        MovEquipProprioBean movEquipProprioBean = getMovEquipProprioAberto();
        movEquipProprioBean.setDescrDestinoMovEquipProprio(descrDestino);
        movEquipProprioBean.update();
    }

    public void setNroNotaFiscal(Long nroNotaFiscal){
        MovEquipProprioBean movEquipProprioBean = getMovEquipProprioAberto();
        movEquipProprioBean.setNroNotaFiscalMovEquipProprio(nroNotaFiscal);
        movEquipProprioBean.update();
    }

    public List<MovEquipProprioBean> movEquipProprioAllList(){
        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        return movEquipProprioBean.orderBy("idMovEquipProprio", false);
    }

    public List<MovEquipProprioBean> movEquipProprioList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqDtrhLongDia(Tempo.getInstance().dthrLongDiaMenos(1)));
        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        return movEquipProprioBean.getDateHour(pesqArrayList, "idMovEquipProprio", false);
    }

    public List<MovEquipProprioBean> movEquipProprioAbertoList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovAberto());
        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        return movEquipProprioBean.get(pesqArrayList);
    }

    public List<MovEquipProprioBean> movEquipProprioFechadoList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovFechado());
        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        return movEquipProprioBean.get(pesqArrayList);
    }

    public List<MovEquipProprioBean> movEquipProprioIdList(Long idMovEquipProprio){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovId(idMovEquipProprio));
        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        return movEquipProprioBean.get(pesqArrayList);
    }

    public List<MovEquipProprioBean> movEquipProprioList(ArrayList<Long> idMovEquipProprioArrayList){
        MovEquipProprioBean movEquipProprioBean = new MovEquipProprioBean();
        return movEquipProprioBean.in("idMovEquipProprio", idMovEquipProprioArrayList);
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

    public ArrayList<Long> idMovEquipProprioArrayList(List<MovEquipProprioBean> movEquipProprioList){
        ArrayList<Long> idMovEquipProprioList = new ArrayList<Long>();
        for (MovEquipProprioBean movEquipProprioBean : movEquipProprioList) {
            idMovEquipProprioList.add(movEquipProprioBean.getIdMovEquipProprio());
        }
        movEquipProprioList.clear();
        return idMovEquipProprioList;
    }

    public ArrayList<Long> idMovEquipProprioArrayList(String objeto) throws Exception {

        ArrayList<Long> idMovEquipProprioArrayList = new ArrayList<>();

        JSONObject jObjMovEquipProprio = new JSONObject(objeto);
        JSONArray jsonArrayMovEquipProprio = jObjMovEquipProprio.getJSONArray("movequipproprio");

        for (int i = 0; i < jsonArrayMovEquipProprio.length(); i++) {

            JSONObject objBol = jsonArrayMovEquipProprio.getJSONObject(i);
            Gson gsonBol = new Gson();
            MovEquipProprioBean movEquipProprioBean = gsonBol.fromJson(objBol.toString(), MovEquipProprioBean.class);

            idMovEquipProprioArrayList.add(movEquipProprioBean.getIdMovEquipProprio());

        }

        return idMovEquipProprioArrayList;

    }

    private String dadosMovEquipProprio(MovEquipProprioBean movEquipProprioBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(movEquipProprioBean, movEquipProprioBean.getClass()).toString();
    }

    public String dadosEnvioMovEquipProprio(){
        return dadosMovEquipProprio(movEquipProprioFechadoList());
    }

    private String dadosMovEquipProprio(List<MovEquipProprioBean> movEquipProprioList){

        JsonArray jsonArrayMovEquipProprio = new JsonArray();

        for (MovEquipProprioBean movEquipProprioBean : movEquipProprioList) {
            Gson gsonCabec = new Gson();
            jsonArrayMovEquipProprio.add(gsonCabec.toJsonTree(movEquipProprioBean, movEquipProprioBean.getClass()));
        }

        movEquipProprioList.clear();

        JsonObject jsonMovEquipProprio = new JsonObject();
        jsonMovEquipProprio.add("movequipproprio", jsonArrayMovEquipProprio);

        return jsonMovEquipProprio.toString();
    }

    private EspecificaPesquisa getPesqMovId(Long idMovEquipProprio){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipProprio");
        pesquisa.setValor(idMovEquipProprio);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipProprio");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovFechado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipProprio");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovEnviado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipProprio");
        pesquisa.setValor(3L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqDtrhLongDia(Long dtrhLongDia){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("dthrLongMovEquipProprio");
        pesquisa.setValor(dtrhLongDia);
        pesquisa.setTipo(2);
        return pesquisa;
    }

}
