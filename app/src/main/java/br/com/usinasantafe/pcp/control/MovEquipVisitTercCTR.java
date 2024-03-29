package br.com.usinasantafe.pcp.control;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.estaticas.TerceiroBean;
import br.com.usinasantafe.pcp.model.bean.estaticas.VisitanteBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.ConfigBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercPassagBean;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipVisitTercDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipVisitTercPassagDAO;
import br.com.usinasantafe.pcp.model.dao.TerceiroDAO;
import br.com.usinasantafe.pcp.model.dao.VisitanteDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class MovEquipVisitTercCTR {

    private MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO;

    public MovEquipVisitTercCTR() {
    }

    public MovEquipVisitTercPassagDAO getMovEquipVisitTercPassagDAO(){
        if (movEquipVisitTercPassagDAO == null)
            movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        return movEquipVisitTercPassagDAO;
    }

    public boolean verEnvioMovEquipVisitTercEnviar(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.verMovEquipVisitTercEnviar();
    }

    public boolean verTerceiroCpf(String cpfTerceiro){
        TerceiroDAO terceiroDAO = new TerceiroDAO();
        return terceiroDAO.verTerceiroCpf(cpfTerceiro);
    }

    public boolean verVisitanteCpf(String cpfVisitante){
        VisitanteDAO visitanteDAO = new VisitanteDAO();
        return visitanteDAO.verVisitanteCpf(cpfVisitante);
    }

    public int qtdeMovEquipVisitTercFechado(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.qtdeMovEquipVisitTercFechado();
    }

    public void abrirMovEquipVisitTerc(Long tipoMov){
        ConfigCTR configCTR = new ConfigCTR();
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.abrirMovEquipVisitTerc(tipoMov, configCTR.getConfig().getNroAparelhoConfig());
    }

    public void inserirMovEquipVisitTercPassag(Long idVisitTerc){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        movEquipVisitTercPassagDAO.inserirMovEquipVisitTercPassag(movEquipVisitTercDAO.getMovEquipVisitTercAberto().getIdMovEquipVisitTerc(), idVisitTerc);
    }

    public void inserirMovEquipVisitTercPassag(int posicao, Long idVisitTerc){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        movEquipVisitTercPassagDAO.inserirMovEquipVisitTercPassag(movEquipVisitTercDAO.getMovEquipVisitTercFechado(posicao).getIdMovEquipVisitTerc(), idVisitTerc);
    }

    public void finalizarMovEquipVisitTerc(String observacao){
        ConfigCTR configCTR = new ConfigCTR();
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercAberto();
        ConfigBean configBean = configCTR.getConfig();
        if(movEquipVisitTercBean.getStatusEntradaSaidaMovEquipVisitTerc() == 1L){
            movEquipVisitTercDAO.finalizarEntradaMovEquipVisitTerc(configBean.getIdLocalConfig(), configBean.getMatricVigiaConfig(), observacao, movEquipVisitTercBean);
        } else {
            movEquipVisitTercDAO.finalizarSaidaMovEquipVisitTerc(configBean.getIdLocalConfig(), configBean.getMatricVigiaConfig(), observacao, configBean.getPosicaoListaMov().intValue(), movEquipVisitTercBean);
        }
    }

    public void atualizarEnviarMovEquipVisitTerc(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.updateEquipVisitTercEnviar();
    }

    public void deleteMovEquipVisitTercAberto(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercDAO.movEquipVisitTercAbertoList();
        for(MovEquipVisitTercBean movEquipVisitTercBean : movEquipVisitTercList){
            movEquipVisitTercPassagDAO.deleteMovEquipVisitTercPassagIdMovEquip(movEquipVisitTercBean.getIdMovEquipVisitTerc());
            movEquipVisitTercDAO.deleteMovEquipVisitTerc(movEquipVisitTercBean.getIdMovEquipVisitTerc());
        }
        movEquipVisitTercList.clear();
    }

    public void deleteMovEquipVisitTercPassag(MovEquipVisitTercPassagBean movEquipVisitTercPassagBean){
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        movEquipVisitTercPassagDAO.deleteMovEquipVisitTercPassagIdMovEquipPassag(movEquipVisitTercPassagBean.getIdMovEquipVisitTercPassag());
    }

    public MovEquipVisitTercBean getMovEquipVisitTercAberto(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.getMovEquipVisitTercAberto();
    }

    public MovEquipVisitTercBean getMovEquipVisitTercFechado(int posicao){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.getMovEquipVisitTercFechado(posicao);
    }

    public TerceiroBean getTerceiroCpf(String cpfTerceiro){
        TerceiroDAO terceiroDAO = new TerceiroDAO();
        return terceiroDAO.getTerceiroCpf(cpfTerceiro);
    }

    public TerceiroBean getTerceiroId(Long idTerceiro){
        TerceiroDAO terceiroDAO = new TerceiroDAO();
        return terceiroDAO.getTerceiroId(idTerceiro);
    }

    public String empresasTerc(Long idTerceiro){
        String empresas = "";
        TerceiroDAO terceiroDAO = new TerceiroDAO();
        List<TerceiroBean> terceiroList = terceiroDAO.terceiroIdList(idTerceiro);
        for(TerceiroBean terceiroBean : terceiroList) {
            empresas = empresas + terceiroBean.getEmpresaTerceiro() + "\n";
        }
        return empresas;
    }

    public VisitanteBean getVisitanteCpf(String cpfVisitante){
        VisitanteDAO visitanteDAO = new VisitanteDAO();
        return visitanteDAO.getVisitanteCpf(cpfVisitante);
    }

    public VisitanteBean getVisitanteId(Long idVisitante){
        VisitanteDAO visitanteDAO = new VisitanteDAO();
        return visitanteDAO.getVisitanteId(idVisitante);
    }

    public ArrayList<String> getMovEquipVisitTerc(MovEquipVisitTercBean movEquipVisitTercBean){

        ArrayList<String> itens = new ArrayList<String>();
        ConfigCTR configCTR = new ConfigCTR();
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        TerceiroDAO terceiroDAO = new TerceiroDAO();
        VisitanteDAO visitanteDAO = new VisitanteDAO();

        itens.add("DTHR: " + movEquipVisitTercBean.getDthrMovEquipVisitTerc());

        if(movEquipVisitTercBean.getTipoMovEquipVisitTerc() == 1L){
            itens.add("ENTRADA");
        } else {
            itens.add("SAÍDA");
        }

        itens.add("VEÍCULO: " + movEquipVisitTercBean.getVeiculoMovEquipVisitTerc());
        itens.add("PLACA: " + movEquipVisitTercBean.getPlacaMovEquipVisitTerc());

        if(movEquipVisitTercBean.getTipoVisitTercMovEquipVisitTerc() == 2L){
            itens.add("TERCEIRO");
        } else {
            itens.add("VISITANTE");
        }

        if(movEquipVisitTercBean.getTipoVisitTercMovEquipVisitTerc() == 2L){
            TerceiroBean terceiroBean =  terceiroDAO.getTerceiroId(movEquipVisitTercBean.getIdVisitTercMovEquipVisitTerc());
            itens.add("MOTORISTA: " + terceiroBean.getCpfTerceiro() + " - " + terceiroBean.getNomeTerceiro());
        } else {
            VisitanteBean visitanteBean = visitanteDAO.getVisitanteId(movEquipVisitTercBean.getIdVisitTercMovEquipVisitTerc());
            itens.add("MOTORISTA: " + visitanteBean.getCpfVisitante() + " - " + visitanteBean.getNomeVisitante());
        }

        String passageiro = "";
        List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList = movEquipVisitTercPassagDAO.movEquipVisitTercPassagIdMovEquipList(movEquipVisitTercBean.getIdMovEquipVisitTerc());
        for(MovEquipVisitTercPassagBean movEquipVisitTercPassagBean : movEquipVisitTercPassagList) {
            if(movEquipVisitTercBean.getTipoVisitTercMovEquipVisitTerc() == 2L){
                TerceiroBean terceiroBean = terceiroDAO.getTerceiroId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());
                passageiro = passageiro + terceiroBean.getCpfTerceiro() + " - " + terceiroBean.getNomeTerceiro() + "; ";
            } else {
                VisitanteBean visitanteBean = visitanteDAO.getVisitanteId(movEquipVisitTercPassagBean.getIdVisitTercMovEquipVisitTercPassag());
                passageiro = passageiro + visitanteBean.getCpfVisitante() + " - " + visitanteBean.getNomeVisitante() + "; ";
            }
        }

        itens.add("PASSAGEIRO(S): " + passageiro);
        itens.add("VIGIA: " + movEquipVisitTercBean.getNroMatricVigiaMovEquipVisitTerc() + " - " + configCTR.getColabMatric(movEquipVisitTercBean.getNroMatricVigiaMovEquipVisitTerc()).getNomeColab());

        if(movEquipVisitTercBean.getDestinoMovEquipVisitTerc() != null){
            itens.add("DESTINO: " +  movEquipVisitTercBean.getDestinoMovEquipVisitTerc());
        } else {
            itens.add("DESTINO:");
        }

        if(movEquipVisitTercBean.getObservMovEquipVisitTerc() != null){
            itens.add("OBS.:\n" +  movEquipVisitTercBean.getObservMovEquipVisitTerc());
        } else {
            itens.add("OBS.:");
        }
        return itens;
    }

    public void setTipoVisitTerc(Long tipoVisitTerc){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setTipoVisitTerc(tipoVisitTerc);
    }

    public void setIdVisitTerc(Long idVisitTerc){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setIdVisitTerc(idVisitTerc);
    }

    public void setIdVisitTerc(int posicao, Long idVisitTerc){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setIdVisitTerc(posicao, idVisitTerc);
    }

    public void setVeiculoVisitTerc(String veiculo){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setVeiculoVisitTerc(veiculo);
    }

    public void setVeiculoVisitTerc(int posicao, String veiculo){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setVeiculoVisitTerc(posicao, veiculo);
    }

    public void setPlacaVisitTerc(String placa){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setPlacaVisitTerc(placa);
    }

    public void setPlacaVisitTerc(int posicao, String placa){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setPlacaVisitTerc(posicao, placa);
    }

    public void setDestinoVisitTerc(String destino){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setDestinoVisitTerc(destino);
    }

    public void setDestinoVisitTerc(int posicao, String destino){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setDestinoVisitTerc(posicao, destino);
    }

    public void setObservacaoVisitTerc(int posicao, String obsersacao) {
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setObservacaoVisitTerc(posicao, obsersacao);
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercFechadoList(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.movEquipVisitTercFechadoList();
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercEntradaList(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.movEquipVisitTercEntradaList();
    }

    public List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList() {
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        return movEquipVisitTercPassagDAO.movEquipVisitTercPassagIdMovEquipList(movEquipVisitTercDAO.getMovEquipVisitTercAberto().getIdMovEquipVisitTerc());
    }

    public List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagFechadoList(int posicao) {
        MovEquipVisitTercDAO movEquipProprioDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        return movEquipVisitTercPassagDAO.movEquipVisitTercPassagIdMovEquipList(movEquipProprioDAO.getMovEquipVisitTercFechado(posicao).getIdMovEquipVisitTerc());
    }

//    public String dadosEnvioMovEquipVisitTerc(){
//
//        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
//        ArrayList<Long> idMovEquipVisitTercArrayList = movEquipVisitTercDAO.idMovEquipVisitTercArrayList(movEquipVisitTercDAO.movEquipVisitTercEnviarList());
//        String dadosMovEquipVisitTerc = movEquipVisitTercDAO.dadosEnvioMovEquipVisitTerc();
//
//        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
//        String dadosEnvioMovEquipVisitTercPassag = movEquipVisitTercPassagDAO.dadosEnvioMovEquipVisitTercPassag(movEquipVisitTercPassagDAO.movEquipProprioPassagEnvioList(idMovEquipVisitTercArrayList));
//
//        return dadosMovEquipVisitTerc + "_" + dadosEnvioMovEquipVisitTercPassag;
//
//    }

    public List<MovEquipVisitTercBean> dadosEnvioMovEquipVisitTerc(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercDAO.movEquipVisitTercEnviarList();
        for (int i = 0; i < movEquipVisitTercList.size(); i++) {
            List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList = movEquipVisitTercPassagDAO.movEquipVisitTercPassagIdMovEquipList(movEquipVisitTercList.get(i).getIdMovEquipVisitTerc());
            movEquipVisitTercList.get(i).setMovEquipVisitTercPassagList(movEquipVisitTercPassagList);
        }
        return movEquipVisitTercList;
    }

    public void updateMovEquipVisitTerc(List<MovEquipVisitTercBean> movEquipVisitTercList, String activity){

        try {

            MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
            ArrayList<Long> movEquipVisitTercArrayList = movEquipVisitTercDAO.idMovEquipVisitTercArrayList(movEquipVisitTercList);
            movEquipVisitTercDAO.updateEquipVisitTercEnviado(movEquipVisitTercArrayList);

            deleteMovEquipVisitTercEnviado();

            EnvioDadosServ.getInstance().envioDados(activity);

        }
        catch (Exception e){
            EnvioDadosServ.status = 1;
            LogErroDAO.getInstance().insertLogErro(e);
        }

    }

    public void deleteMovEquipVisitTercEnviado(){

        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        ArrayList<Long> idMovEquipVisitTercArrayList = movEquipVisitTercDAO.idMovEquipVisitTercExcluirArrayList();

        for (Long idMovEquipVisitTerc : idMovEquipVisitTercArrayList) {

            MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
            movEquipVisitTercPassagDAO.deleteMovEquipVisitTercPassagIdMovEquip(idMovEquipVisitTerc);

            movEquipVisitTercDAO.deleteMovEquipVisitTerc(idMovEquipVisitTerc);

        }

        idMovEquipVisitTercArrayList.clear();

    }
}
