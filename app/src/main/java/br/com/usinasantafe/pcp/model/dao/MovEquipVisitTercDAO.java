package br.com.usinasantafe.pcp.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercPassagBean;
import br.com.usinasantafe.pcp.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pcp.util.Tempo;

public class MovEquipVisitTercDAO {

    public MovEquipVisitTercDAO() {
    }

    public boolean verMovEquipVisitTercEnviar(){
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercEnviarList();
        boolean ret = (movEquipVisitTercList.size() > 0);
        movEquipVisitTercList.clear();
        return ret;
    }

    public int qtdeMovEquipVisitTercFechado(){
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercFechadoList();
        int qtde = movEquipVisitTercList.size();
        movEquipVisitTercList.clear();
        return qtde;
    }

    public void abrirMovEquipVisitTerc(Long tipoMov, Long nroAparelho){
        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        movEquipVisitTercBean.setTipoMovEquipVisitTerc(tipoMov);
        movEquipVisitTercBean.setStatusMovEquipVisitTerc(1L);
        movEquipVisitTercBean.setNroAparelhoMovEquipVisitTerc(nroAparelho);
        if(tipoMov == 1L){
            movEquipVisitTercBean.setStatusEntradaSaidaMovEquipVisitTerc(1L);
        } else {
            movEquipVisitTercBean.setStatusEntradaSaidaMovEquipVisitTerc(2L);
        }
        movEquipVisitTercBean.insert();
    }

    public void finalizarEntradaMovEquipVisitTerc(Long idLocal, Long nroMatricVigia, String observacao, MovEquipVisitTercBean movEquipVisitTercBean){
        movEquipVisitTercBean.setIdLocalMovEquipVisitTerc(idLocal);
        movEquipVisitTercBean.setNroMatricVigiaMovEquipVisitTerc(nroMatricVigia);
        movEquipVisitTercBean.setObservMovEquipVisitTerc(observacao);
        Long dthr = Tempo.getInstance().dthrAtualLong();
        movEquipVisitTercBean.setDthrLongMovEquipVisitTerc(dthr);
        movEquipVisitTercBean.setDthrMovEquipVisitTerc(Tempo.getInstance().dthrLongToString(dthr));
        movEquipVisitTercBean.setStatusMovEquipVisitTerc(2L);
        movEquipVisitTercBean.update();
    }

    public void finalizarSaidaMovEquipVisitTerc(Long idLocal, Long nroMatricVigia, String observacao, int posicaoListaMov, MovEquipVisitTercBean movEquipVisitTercSaidaBean){

        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercEntradaList();
        MovEquipVisitTercBean movEquipVisitTercEntradaBean =  movEquipVisitTercList.get(posicaoListaMov);
        movEquipVisitTercEntradaBean.setStatusEntradaSaidaMovEquipVisitTerc(2L);
        movEquipVisitTercEntradaBean.update();
        movEquipVisitTercList.clear();

        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList = movEquipVisitTercPassagDAO.movEquipVisitTercPassagIdMovEquipList(movEquipVisitTercEntradaBean.getIdMovEquipVisitTerc());
        for(MovEquipVisitTercPassagBean movEquipVisitTercPassagEntradaBean : movEquipVisitTercPassagList){
            MovEquipVisitTercPassagBean movEquipVisitTercPassagSaidaBean = new MovEquipVisitTercPassagBean();
            movEquipVisitTercPassagSaidaBean.setIdMovEquipVisitTerc(movEquipVisitTercSaidaBean.getIdMovEquipVisitTerc());
            movEquipVisitTercPassagSaidaBean.setIdVisitTercMovEquipVisitTercPassag(movEquipVisitTercPassagEntradaBean.getIdVisitTercMovEquipVisitTercPassag());
            movEquipVisitTercPassagSaidaBean.insert();
        }

        movEquipVisitTercSaidaBean.setTipoVisitTercMovEquipVisitTerc(movEquipVisitTercEntradaBean.getTipoVisitTercMovEquipVisitTerc());
        movEquipVisitTercSaidaBean.setIdVisitTercMovEquipVisitTerc(movEquipVisitTercEntradaBean.getIdVisitTercMovEquipVisitTerc());
        movEquipVisitTercSaidaBean.setVeiculoMovEquipVisitTerc(movEquipVisitTercEntradaBean.getVeiculoMovEquipVisitTerc());
        movEquipVisitTercSaidaBean.setPlacaMovEquipVisitTerc(movEquipVisitTercEntradaBean.getPlacaMovEquipVisitTerc());

        movEquipVisitTercSaidaBean.setIdLocalMovEquipVisitTerc(idLocal);
        movEquipVisitTercSaidaBean.setNroMatricVigiaMovEquipVisitTerc(nroMatricVigia);
        movEquipVisitTercSaidaBean.setObservMovEquipVisitTerc(observacao);
        Long dthr = Tempo.getInstance().dthrAtualLong();
        movEquipVisitTercSaidaBean.setDthrLongMovEquipVisitTerc(dthr);
        movEquipVisitTercSaidaBean.setDthrMovEquipVisitTerc(Tempo.getInstance().dthrLongToString(dthr));
        movEquipVisitTercSaidaBean.setStatusMovEquipVisitTerc(2L);
        movEquipVisitTercSaidaBean.update();

    }

    public void updateEquipVisitTercEnviar(){
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercFechadoList();
        for (MovEquipVisitTercBean movEquipVisitTercBean : movEquipVisitTercList) {
            movEquipVisitTercBean.setStatusMovEquipVisitTerc(3L);
            movEquipVisitTercBean.update();
        }
        movEquipVisitTercList.clear();
    }

    public void updateEquipVisitTercEnviado(ArrayList<Long> idMovEquipVisitTercArrayList) {
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercEntradaList(idMovEquipVisitTercArrayList);
        for (MovEquipVisitTercBean movEquipVisitTercBean : movEquipVisitTercList) {
            movEquipVisitTercBean.setStatusMovEquipVisitTerc(4L);
            movEquipVisitTercBean.update();
        }
        movEquipVisitTercList.clear();
        idMovEquipVisitTercArrayList.clear();
    }

    public void deleteMovEquipVisitTerc(Long idMovEquipVisitTerc){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercId(idMovEquipVisitTerc);
        movEquipVisitTercBean.delete();
    }

    public ArrayList<Long> idMovEquipVisitTercExcluirArrayList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovEnviado());
        pesqArrayList.add(getPesqStatusSaida());

        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercBean.get(pesqArrayList);

        ArrayList<Long> idMovEquipVisitTercList = new ArrayList<>();
        for (MovEquipVisitTercBean movEquipVisitTercBeanBD : movEquipVisitTercList) {
            idMovEquipVisitTercList.add(movEquipVisitTercBeanBD.getIdMovEquipVisitTerc());
        }

        movEquipVisitTercList.clear();
        return idMovEquipVisitTercList;

    }

    public MovEquipVisitTercBean getMovEquipVisitTercAberto(){
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercAbertoList();
        MovEquipVisitTercBean movEquipVisitTercBean = movEquipVisitTercList.get(0);
        movEquipVisitTercList.clear();
        return movEquipVisitTercBean;
    }

    public MovEquipVisitTercBean getMovEquipVisitTercFechado(int posicao){
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercFechadoList();
        MovEquipVisitTercBean movEquipVisitTercBean = movEquipVisitTercList.get(posicao);
        movEquipVisitTercList.clear();
        return movEquipVisitTercBean;
    }

    public  MovEquipVisitTercBean getMovEquipVisitTercId(Long idMovEquipVisitTerc){
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercAbertoId(idMovEquipVisitTerc);
        MovEquipVisitTercBean movEquipVisitTercBean = movEquipVisitTercList.get(0);
        movEquipVisitTercList.clear();
        return movEquipVisitTercBean;
    }

    public void setTipoVisitTerc(Long tipoVisitTerc){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercAberto();
        movEquipVisitTercBean.setTipoVisitTercMovEquipVisitTerc(tipoVisitTerc);
        movEquipVisitTercBean.update();
    }

    public void setIdVisitTerc(Long idVisitTerc){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercAberto();
        movEquipVisitTercBean.setIdVisitTercMovEquipVisitTerc(idVisitTerc);
        movEquipVisitTercBean.update();
    }

    public void setIdVisitTerc(int posicao, Long idVisitTerc){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercFechado(posicao);
        movEquipVisitTercBean.setIdVisitTercMovEquipVisitTerc(idVisitTerc);
        movEquipVisitTercBean.update();
    }

    public void setVeiculoVisitTerc(String veiculo){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercAberto();
        movEquipVisitTercBean.setVeiculoMovEquipVisitTerc(veiculo);
        movEquipVisitTercBean.update();
    }

    public void setVeiculoVisitTerc(int posicao, String veiculo){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercFechado(posicao);
        movEquipVisitTercBean.setVeiculoMovEquipVisitTerc(veiculo);
        movEquipVisitTercBean.update();
    }

    public void setPlacaVisitTerc(String placa){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercAberto();
        movEquipVisitTercBean.setPlacaMovEquipVisitTerc(placa);
        movEquipVisitTercBean.update();
    }

    public void setPlacaVisitTerc(int posicao, String placa){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercFechado(posicao);
        movEquipVisitTercBean.setPlacaMovEquipVisitTerc(placa);
        movEquipVisitTercBean.update();
    }

    public void setDestinoVisitTerc(String destino){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercAberto();
        movEquipVisitTercBean.setDestinoMovEquipVisitTerc(destino);
        movEquipVisitTercBean.update();
    }

    public void setDestinoVisitTerc(int posicao, String destino){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercFechado(posicao);
        movEquipVisitTercBean.setDestinoMovEquipVisitTerc(destino);
        movEquipVisitTercBean.update();
    }

    public void setObservacaoVisitTerc(int posicao, String obsersacao){
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercFechado(posicao);
        movEquipVisitTercBean.setObservMovEquipVisitTerc(obsersacao);
        movEquipVisitTercBean.update();
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercAbertoList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovAberto());
        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        return movEquipVisitTercBean.get(pesqArrayList);
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercFechadoList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovFechado());
        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        return movEquipVisitTercBean.get(pesqArrayList);
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercEnviarList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovEnviar());
        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        return movEquipVisitTercBean.get(pesqArrayList);
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercAbertoId(Long idMovEquipVisitTerc){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqMovId(idMovEquipVisitTerc));
        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        return movEquipVisitTercBean.get(pesqArrayList);
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercEntradaList(ArrayList<Long> idMovEquipVisitTercArrayList){
        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        return movEquipVisitTercBean.in("idMovEquipVisitTerc", idMovEquipVisitTercArrayList);
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercEntradaList(){
        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqStatusEntrada());
        MovEquipVisitTercBean movEquipVisitTercBean = new MovEquipVisitTercBean();
        return movEquipVisitTercBean.getAndOrderBy(pesqArrayList, "idMovEquipVisitTerc", false);
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

    public ArrayList<Long> idMovEquipVisitTercArrayList(List<MovEquipVisitTercBean> movEquipVisitTercList){
        ArrayList<Long> idMovEquipVisitTercList = new ArrayList<Long>();
        for (MovEquipVisitTercBean movEquipVisitTercBean : movEquipVisitTercList) {
            idMovEquipVisitTercList.add(movEquipVisitTercBean.getIdMovEquipVisitTerc());
        }
        movEquipVisitTercList.clear();
        return idMovEquipVisitTercList;
    }

    public ArrayList<Long> idMovEquipVisitTercArrayList(String objeto) throws Exception {

        ArrayList<Long> idMovEquipVisitTercArrayList = new ArrayList<>();

        JSONObject jObjMovEquipVisitTerc = new JSONObject(objeto);
        JSONArray jsonArrayMovEquipVisitTerc = jObjMovEquipVisitTerc.getJSONArray("movequipvisitterc");

        for (int i = 0; i < jsonArrayMovEquipVisitTerc.length(); i++) {

            JSONObject objBol = jsonArrayMovEquipVisitTerc.getJSONObject(i);
            Gson gsonBol = new Gson();
            MovEquipVisitTercBean movEquipVisitTercBean = gsonBol.fromJson(objBol.toString(), MovEquipVisitTercBean.class);

            idMovEquipVisitTercArrayList.add(movEquipVisitTercBean.getIdMovEquipVisitTerc());

        }

        return idMovEquipVisitTercArrayList;

    }

    private String dadosEquipVisitTerc(MovEquipVisitTercBean movEquipVisitTercBean){
        Gson gsonCabec = new Gson();
        return gsonCabec.toJsonTree(movEquipVisitTercBean, movEquipVisitTercBean.getClass()).toString();
    }

    public String dadosEnvioMovEquipVisitTerc() {
        return dadosMovEquipVisitTerc(movEquipVisitTercEnviarList());
    }

    private String dadosMovEquipVisitTerc(List<MovEquipVisitTercBean> movEquipVisitTercList){

        JsonArray jsonArrayBoletim = new JsonArray();

        for (MovEquipVisitTercBean movEquipVisitTercBean : movEquipVisitTercList) {
            Gson gsonMovEquipVisitTerc = new Gson();
            jsonArrayBoletim.add(gsonMovEquipVisitTerc.toJsonTree(movEquipVisitTercBean, movEquipVisitTercBean.getClass()));
        }

        movEquipVisitTercList.clear();

        JsonObject jsonBoletim = new JsonObject();
        jsonBoletim.add("movequipvisitterc", jsonArrayBoletim);

        return jsonBoletim.toString();
    }

    private EspecificaPesquisa getPesqMovId(Long idMovEquipVisitTerc){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idMovEquipVisitTerc");
        pesquisa.setValor(idMovEquipVisitTerc);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipVisitTerc");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovFechado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipVisitTerc");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovEnviar(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipVisitTerc");
        pesquisa.setValor(3L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqMovEnviado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusMovEquipVisitTerc");
        pesquisa.setValor(4L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusEntrada(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusEntradaSaidaMovEquipVisitTerc");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqStatusSaida(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusEntradaSaidaMovEquipVisitTerc");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
