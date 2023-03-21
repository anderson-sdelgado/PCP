package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;
import br.com.usinasantafe.pcp.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcp.util.Tempo;

public class MovEquipResidenciaDAO {

    public MovEquipResidenciaDAO() {
    }

    public boolean verMovEquipResidenciaEnviar(){
        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaEnviarList();
        boolean ret = (movEquipResidenciaList.size() > 0);
        movEquipResidenciaList.clear();
        return ret;
    }

    public int qtdeMovEquipResidenciaFechado(){
        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaFechadoList();
        int ret = movEquipResidenciaList.size();
        movEquipResidenciaList.clear();
        return ret;
    }

    public void abrirMovEquipResidencia(Long tipoMov, Long nroAparelho){
        MovEquipResidenciaBean movEquipResidenciaBean = new MovEquipResidenciaBean();
        movEquipResidenciaBean.setTipoMovEquipResidencia(tipoMov);
        movEquipResidenciaBean.setStatusMovEquipResidencia(1L);
        movEquipResidenciaBean.setNroAparelhoMovEquipResidencia(nroAparelho);
        if(tipoMov == 1L){
            movEquipResidenciaBean.setStatusEntradaSaidaMovEquipResidencia(1L);
        } else {
            movEquipResidenciaBean.setStatusEntradaSaidaMovEquipResidencia(2L);
        }
        movEquipResidenciaBean.insert();
    }

    public void finalizarEntradaMovEquipResidencia(Long idLocal, Long nroMatricVigia, String observacao, MovEquipResidenciaBean movEquipResidenciaBean){
        movEquipResidenciaBean.setIdLocalMovEquipResidencia(idLocal);
        movEquipResidenciaBean.setNroMatricVigiaMovEquipResidencia(nroMatricVigia);
        movEquipResidenciaBean.setObservMovEquipResidencia(observacao);
        Long dthr = Tempo.getInstance().dthrAtualLong();
        movEquipResidenciaBean.setDthrLongMovEquipResidencia(dthr);
        movEquipResidenciaBean.setDthrMovEquipResidencia(Tempo.getInstance().dthrLongToString(dthr));
        movEquipResidenciaBean.setStatusMovEquipResidencia(2L);
        movEquipResidenciaBean.update();
    }

    public void finalizarSaidaMovEquipResidencia(Long idLocal, Long nroMatricVigia, String observacao, int posicaoListaMov, MovEquipResidenciaBean movEquipResidenciaSaidaBean){

        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaEntradaList();
        MovEquipResidenciaBean movEquipResidenciaEntradaBean =  movEquipResidenciaList.get(posicaoListaMov);
        movEquipResidenciaEntradaBean.setStatusEntradaSaidaMovEquipResidencia(2L);
        movEquipResidenciaEntradaBean.update();
        movEquipResidenciaList.clear();

        movEquipResidenciaSaidaBean.setNomeVisitanteMovEquipResidencia(movEquipResidenciaEntradaBean.getNomeVisitanteMovEquipResidencia());
        movEquipResidenciaSaidaBean.setVeiculoMovEquipResidencia(movEquipResidenciaEntradaBean.getVeiculoMovEquipResidencia());
        movEquipResidenciaSaidaBean.setPlacaMovEquipResidencia(movEquipResidenciaEntradaBean.getPlacaMovEquipResidencia());

        movEquipResidenciaSaidaBean.setIdLocalMovEquipResidencia(idLocal);
        movEquipResidenciaSaidaBean.setNroMatricVigiaMovEquipResidencia(nroMatricVigia);
        movEquipResidenciaSaidaBean.setObservMovEquipResidencia(observacao);
        Long dthr = Tempo.getInstance().dthrAtualLong();
        movEquipResidenciaSaidaBean.setDthrLongMovEquipResidencia(dthr);
        movEquipResidenciaSaidaBean.setDthrMovEquipResidencia(Tempo.getInstance().dthrLongToString(dthr));
        movEquipResidenciaSaidaBean.setStatusMovEquipResidencia(2L);
        movEquipResidenciaSaidaBean.update();

    }

    public void updateEquipResidenciaEnviar(){
        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaFechadoList();
        for (MovEquipResidenciaBean movEquipResidenciaBean : movEquipResidenciaList) {
            movEquipResidenciaBean.setStatusMovEquipResidencia(3L);
            movEquipResidenciaBean.update();
        }
        movEquipResidenciaList.clear();
    }

    public void updateEquipResidenciaEnviado(ArrayList<Long> idMovEquipResidenciaArrayList){
        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaEntradaList(idMovEquipResidenciaArrayList);
        for (MovEquipResidenciaBean movEquipResidenciaBean : movEquipResidenciaList) {
            movEquipResidenciaBean.setStatusMovEquipResidencia(4L);
            movEquipResidenciaBean.update();
        }
        movEquipResidenciaList.clear();
        idMovEquipResidenciaArrayList.clear();
    }

    public void deleteMovEquipResidencia(Long idMovEquipResidencia){
        MovEquipResidenciaBean movEquipResidenciaBean = getMovEquipResidenciaId(idMovEquipResidencia);
        movEquipResidenciaBean.delete();
    }

    public ArrayList<Long> idMovEquipResidenciaExcluirArrayList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovEnviado());
        pesqArrayList.add(getPesqStatusSaida());

        MovEquipResidenciaBean movEquipResidenciaBean = new MovEquipResidenciaBean();
        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaBean.get(pesqArrayList);

        ArrayList<Long> idMovEquipResidenciaList = new ArrayList<>();
        for (MovEquipResidenciaBean movEquipResidenciaBeanBD : movEquipResidenciaList) {
            idMovEquipResidenciaList.add(movEquipResidenciaBeanBD.getIdMovEquipResidencia());
        }

        movEquipResidenciaList.clear();
        return idMovEquipResidenciaList;

    }

    public MovEquipResidenciaBean getMovEquipResidenciaAberto(){
        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaAbertoList();
        MovEquipResidenciaBean movEquipResidenciaBean = movEquipResidenciaList.get(0);
        movEquipResidenciaList.clear();
        return movEquipResidenciaBean;
    }

    public MovEquipResidenciaBean getMovEquipResidenciaFechado(int posicao){
        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaFechadoList();
        MovEquipResidenciaBean movEquipResidenciaBean = movEquipResidenciaList.get(posicao);
        movEquipResidenciaList.clear();
        return movEquipResidenciaBean;
    }

    public  MovEquipResidenciaBean getMovEquipResidenciaId(Long idMovEquipResidencia){
        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaAbertoId(idMovEquipResidencia);
        MovEquipResidenciaBean movEquipResidenciaBean = movEquipResidenciaList.get(0);
        movEquipResidenciaList.clear();
        return movEquipResidenciaBean;
    }

    public void setNomeVisitanteResidencia(String nomeVisitante){
        MovEquipResidenciaBean movEquipResidenciaBean = getMovEquipResidenciaAberto();
        movEquipResidenciaBean.setNomeVisitanteMovEquipResidencia(nomeVisitante);
        movEquipResidenciaBean.update();
    }

    public void setNomeVisitanteResidencia(int posicao, String nomeVisitante){
        MovEquipResidenciaBean movEquipResidenciaBean = getMovEquipResidenciaFechado(posicao);
        movEquipResidenciaBean.setNomeVisitanteMovEquipResidencia(nomeVisitante);
        movEquipResidenciaBean.update();
    }

    public void setVeiculoResidencia(String veiculo){
        MovEquipResidenciaBean movEquipResidenciaBean = getMovEquipResidenciaAberto();
        movEquipResidenciaBean.setVeiculoMovEquipResidencia(veiculo);
        movEquipResidenciaBean.update();
    }

    public void setVeiculoResidencia(int posicao, String veiculo){
        MovEquipResidenciaBean movEquipResidenciaBean = getMovEquipResidenciaFechado(posicao);
        movEquipResidenciaBean.setVeiculoMovEquipResidencia(veiculo);
        movEquipResidenciaBean.update();
    }

    public void setPlacaResidencia(String placa){
        MovEquipResidenciaBean movEquipResidenciaBean = getMovEquipResidenciaAberto();
        movEquipResidenciaBean.setPlacaMovEquipResidencia(placa);
        movEquipResidenciaBean.update();
    }

    public void setPlacaResidencia(int posicao, String placa){
        MovEquipResidenciaBean movEquipResidenciaBean = getMovEquipResidenciaFechado(posicao);
        movEquipResidenciaBean.setPlacaMovEquipResidencia(placa);
        movEquipResidenciaBean.update();
    }

    public void setObservacaoResidencia(int posicao, String observacao){
        MovEquipResidenciaBean movEquipResidenciaBean = getMovEquipResidenciaFechado(posicao);
        movEquipResidenciaBean.setObservMovEquipResidencia(observacao);
        movEquipResidenciaBean.update();
    }

    public List<MovEquipResidenciaBean> movEquipResidenciaAbertoId(Long idMovEquipResidencia){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovId(idMovEquipResidencia));
        MovEquipResidenciaBean movEquipResidenciaBean = new MovEquipResidenciaBean();
        return movEquipResidenciaBean.get(pesqArrayList);
    }

    public List<MovEquipResidenciaBean> movEquipResidenciaEntradaList(ArrayList<Long> idMovEquipResidenciaArrayList){
        MovEquipResidenciaBean movEquipResidenciaBean = new MovEquipResidenciaBean();
        return movEquipResidenciaBean.in("idMovEquipResidencia", idMovEquipResidenciaArrayList);
    }

    public List<MovEquipResidenciaBean> movEquipResidenciaEntradaList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusEntrada());
        MovEquipResidenciaBean movEquipResidenciaBean = new MovEquipResidenciaBean();
        return movEquipResidenciaBean.getAndOrderBy(pesqArrayList, "idMovEquipResidencia", false);
    }

    public List<MovEquipResidenciaBean> movEquipResidenciaAbertoList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovAberto());
        MovEquipResidenciaBean movEquipResidenciaBean = new MovEquipResidenciaBean();
        return movEquipResidenciaBean.get(pesqArrayList);
    }

    public List<MovEquipResidenciaBean> movEquipResidenciaFechadoList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovFechado());
        MovEquipResidenciaBean movEquipResidenciaBean = new MovEquipResidenciaBean();
        return movEquipResidenciaBean.get(pesqArrayList);
    }

    public List<MovEquipResidenciaBean> movEquipResidenciaEnviarList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovEnviar());
        MovEquipResidenciaBean movEquipResidenciaBean = new MovEquipResidenciaBean();
        return movEquipResidenciaBean.get(pesqArrayList);
    }

    public ArrayList<Long> idMovEquipResidenciaArrayList(String objeto) throws Exception {

        ArrayList<Long> idMovEquipResidenciaArrayList = new ArrayList<>();

        JSONObject jObjMovEquipResidencia = new JSONObject(objeto);
        JSONArray jsonArrayMovEquipResidencia = jObjMovEquipResidencia.getJSONArray("movequipresidencia");

        for (int i = 0; i < jsonArrayMovEquipResidencia.length(); i++) {

            JSONObject objMovEquipResidencia = jsonArrayMovEquipResidencia.getJSONObject(i);
            Gson gsonMovEquipResidencia = new Gson();
            MovEquipResidenciaBean movEquipResidenciaBean = gsonMovEquipResidencia.fromJson(objMovEquipResidencia.toString(), MovEquipResidenciaBean.class);

            idMovEquipResidenciaArrayList.add(movEquipResidenciaBean.getIdMovEquipResidencia());

        }

        return idMovEquipResidenciaArrayList;

    }

    public String dadosEnvioMovEquipResidencia() {
        return dadosMovEquipResidencia(movEquipResidenciaEnviarList());
    }

    private String dadosMovEquipResidencia(List<MovEquipResidenciaBean> movEquipResidenciaList){

        JsonArray jsonArrayBoletim = new JsonArray();

        for (MovEquipResidenciaBean movEquipResidenciaBean : movEquipResidenciaList) {
            Gson gsonMovEquipResidencia = new Gson();
            jsonArrayBoletim.add(gsonMovEquipResidencia.toJsonTree(movEquipResidenciaBean, movEquipResidenciaBean.getClass()));
        }

        movEquipResidenciaList.clear();

        JsonObject jsonBoletim = new JsonObject();
        jsonBoletim.add("movequipresidencia", jsonArrayBoletim);

        return jsonBoletim.toString();
    }

    private EspecificaPesquisa getPesqMovId(Long idMovEquipResidencia){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipResidencia");
        pesquisa.setValor(idMovEquipResidencia);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipResidencia");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovFechado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipResidencia");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovEnviar(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipResidencia");
        pesquisa.setValor(3L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovEnviado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipResidencia");
        pesquisa.setValor(4L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusEntrada(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusEntradaSaidaMovEquipResidencia");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusSaida(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusEntradaSaidaMovEquipResidencia");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
