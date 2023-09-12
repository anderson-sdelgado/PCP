package br.com.usinasantafe.pcp.control;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipResidenciaDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class MovEquipResidenciaCTR {

    public boolean verEnvioMovEquipResidenciaEnviar() {
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.verMovEquipResidenciaEnviar();
    }

    public int qtdeMovEquipResidenciaFechado(){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.qtdeMovEquipResidenciaFechado();
    }

    public void abrirMovEquipResidencia(Long tipoMov){
        ConfigCTR configCTR = new ConfigCTR();
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.abrirMovEquipResidencia(tipoMov, configCTR.getConfig().getNroAparelhoConfig());
    }

    public void finalizarMovEquipResidencia(String observacao){
        ConfigCTR configCTR = new ConfigCTR();
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        MovEquipResidenciaBean movEquipResidenciaBean = getMovEquipResidenciaAberto();
        ConfigBean configBean = configCTR.getConfig();
        if(movEquipResidenciaBean.getStatusEntradaSaidaMovEquipResidencia() == 1L){
            movEquipResidenciaDAO.finalizarEntradaMovEquipResidencia(configBean.getIdLocalConfig(), configBean.getMatricVigiaConfig(), observacao, movEquipResidenciaBean);
        } else {
            movEquipResidenciaDAO.finalizarSaidaMovEquipResidencia(configBean.getIdLocalConfig(), configBean.getMatricVigiaConfig(), observacao, configBean.getPosicaoListaMov().intValue(), movEquipResidenciaBean);
        }
    }

    public void atualizarEnviarMovEquipResidencia(){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.updateEquipResidenciaEnviar();
    }

    public void deleteMovEquipResidenciaAberto(){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        List<MovEquipResidenciaBean> movEquipResidenciaList = movEquipResidenciaDAO.movEquipResidenciaAbertoList();
        for(MovEquipResidenciaBean movEquipResidenciaBean : movEquipResidenciaList){
            movEquipResidenciaDAO.deleteMovEquipResidencia(movEquipResidenciaBean.getIdMovEquipResidencia());
        }
        movEquipResidenciaList.clear();
    }

    public MovEquipResidenciaBean getMovEquipResidenciaAberto(){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.getMovEquipResidenciaAberto();
    }

    public MovEquipResidenciaBean getMovEquipResidenciaFechado(int posicao){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.getMovEquipResidenciaFechado(posicao);
    }

    public ArrayList<String> getMovEquipResidencia(MovEquipResidenciaBean movEquipResidenciaBean){

        ArrayList<String> itens = new ArrayList<>();

        itens.add("DTHR: " + movEquipResidenciaBean.getDthrMovEquipResidencia());

        if(movEquipResidenciaBean.getTipoMovEquipResidencia() == 1L){
            itens.add("ENTRADA");
        } else {
            itens.add("SAÍDA");
        }

        itens.add("VEÍCULO: " + movEquipResidenciaBean.getVeiculoMovEquipResidencia());
        itens.add("PLACA: " + movEquipResidenciaBean.getPlacaMovEquipResidencia());
        itens.add("VISITANTE: " + movEquipResidenciaBean.getNomeVisitanteMovEquipResidencia());

        if(movEquipResidenciaBean.getObservMovEquipResidencia() != null){
            itens.add("OBS.:\n" +  movEquipResidenciaBean.getObservMovEquipResidencia());
        } else {
            itens.add("OBS.:");
        }
        return itens;
    }

    public void setNomeVisitanteResidencia(String nomeVisitante){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setNomeVisitanteResidencia(nomeVisitante);
    }

    public void setNomeVisitanteResidencia(int posicao, String nomeVisitante){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setNomeVisitanteResidencia(posicao, nomeVisitante);
    }

    public void setVeiculoResidencia(String veiculo){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setVeiculoResidencia(veiculo);
    }

    public void setVeiculoResidencia(int posicao, String veiculo){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setVeiculoResidencia(posicao, veiculo);
    }

    public void setPlacaResidencia(String placa){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setPlacaResidencia(placa);
    }

    public void setPlacaResidencia(int posicao, String placa){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setPlacaResidencia(posicao, placa);
    }

    public void setObservacaoResidencia(int posicao, String observacao){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setObservacaoResidencia(posicao, observacao);
    }

    public List<MovEquipResidenciaBean> movEquipResidenciaFechadoList(){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.movEquipResidenciaFechadoList();
    }

    public List<MovEquipResidenciaBean> movEquipResidenciaList(){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.movEquipResidenciaEntradaList();
    }

    public List<MovEquipResidenciaBean> dadosEnvioMovEquipResidencia(){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.movEquipResidenciaEnviarList();
    }

    public void updateMovEquipResidencia(List<MovEquipResidenciaBean> movEquipResidenciaList, String activity){

        try {

            MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
            ArrayList<Long> movEquipResidenciaArrayList = movEquipResidenciaDAO.idMovEquipResidenciaArrayList(movEquipResidenciaList);
            movEquipResidenciaDAO.updateEquipResidenciaEnviado(movEquipResidenciaArrayList);

            deleteMovEquipResidenciaEnviado();

            EnvioDadosServ.getInstance().envioDados(activity);

        }
        catch (Exception e){
            EnvioDadosServ.status = 1;
            LogErroDAO.getInstance().insertLogErro(e);
        }

    }

    public void deleteMovEquipResidenciaEnviado(){

        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        ArrayList<Long> idMovEquipResidenciaArrayList = movEquipResidenciaDAO.idMovEquipResidenciaExcluirArrayList();

        for (Long idMovEquipResidencia : idMovEquipResidenciaArrayList) {
            movEquipResidenciaDAO.deleteMovEquipResidencia(idMovEquipResidencia);
        }

        idMovEquipResidenciaArrayList.clear();

    }
}
