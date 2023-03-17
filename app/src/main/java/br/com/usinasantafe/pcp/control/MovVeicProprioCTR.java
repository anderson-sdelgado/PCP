package br.com.usinasantafe.pcp.control;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioPassagBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioSegBean;
import br.com.usinasantafe.pcp.model.dao.EquipDAO;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipProprioDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipProprioPassagDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipProprioSegDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class MovVeicProprioCTR {

    private MovEquipProprioPassagDAO movEquipProprioPassagDAO;

    public MovVeicProprioCTR() {
    }

    public MovEquipProprioPassagDAO getMovEquipProprioPassagDAO(){
        if (movEquipProprioPassagDAO == null)
            movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        return movEquipProprioPassagDAO;
    }

    public boolean verEnvioMovEquipProprioEnviar() {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.verMovEquipProprioEnviar();
    }

    public int qtdeMovEquipProprioFechado(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.qtdeMovEquipProprioFechado();
    }

    public void abrirMovEquipProprio(Long tipoMov){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.abrirMovEquipProprio(tipoMov);
    }

    public void inserirMovEquipProprioSeg(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        movEquipProprioSegDAO.inserirMovEquipProprioSeg(movEquipProprioDAO.getMovEquipProprioAberto().getIdMovEquipProprio(), equipDAO.getEquipNro(nroEquip).getIdEquip());
    }

    public void inserirMovEquipProprioSeg(int posicao, Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        movEquipProprioSegDAO.inserirMovEquipProprioSeg(movEquipProprioDAO.getMovEquipProprioFechado(posicao).getIdMovEquipProprio(), equipDAO.getEquipNro(nroEquip).getIdEquip());
    }

    public void inserirMovEquipProprioPassag(Long matricColab){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        movEquipProprioPassagDAO.inserirMovEquipProprioPassag(movEquipProprioDAO.getMovEquipProprioAberto().getIdMovEquipProprio(), matricColab);
    }

    public void inserirMovEquipProprioPassag(int posicao, Long matricColab){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        movEquipProprioPassagDAO.inserirMovEquipProprioPassag(movEquipProprioDAO.getMovEquipProprioFechado(posicao).getIdMovEquipProprio(), matricColab);
    }

    public void finalizarMovEquipProprio(String observacao){
        ConfigCTR configCTR = new ConfigCTR();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.finalizarMovEquipProprio(configCTR.getConfig().getIdLocalConfig(), configCTR.getConfig().getMatricVigiaConfig(), observacao);
    }

    public void atualizarEnviarMovEquipProprio(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.updateMovEquipProprioEnviar();
    }

    public void deleteMovEquipProprioAberto(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioDAO.movEquipProprioAbertoList();
        for(MovEquipProprioBean movEquipProprioBean : movEquipProprioList){
            movEquipProprioPassagDAO.deleteMovEquipProprioPassagIdMovEquip(movEquipProprioBean.getIdMovEquipProprio());
            movEquipProprioSegDAO.deleteMovEquipProprioSegIdMovEquip(movEquipProprioBean.getIdMovEquipProprio());
            movEquipProprioDAO.deleteMovEquipProprio(movEquipProprioBean.getIdMovEquipProprio());
        }
        movEquipProprioList.clear();
    }

    public void deleteMovEquipProprioSeg(MovEquipProprioSegBean movEquipProprioSegBean){
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        movEquipProprioSegDAO.deleteMovEquipProprioSegIdMovEquipSeg(movEquipProprioSegBean.getIdMovEquipProprioSeg());
    }

    public void deleteMovEquipProprioPassag(MovEquipProprioPassagBean movEquipProprioPassagBean){
        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        movEquipProprioPassagDAO.deleteMovEquipProprioPassagIdMovEquipPassag(movEquipProprioPassagBean.getIdMovEquipProprioPassag());
    }

    public MovEquipProprioBean getMovEquipProprioAberto(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.getMovEquipProprioAberto();
    }

    public MovEquipProprioBean getMovEquipProprioFechado(int posicao){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.getMovEquipProprioFechado(posicao);
    }

    public ArrayList<String> getMovEquipProprio(MovEquipProprioBean movEquipProprioBean){

        ArrayList<String> itens = new ArrayList<String>();
        ConfigCTR configCTR = new ConfigCTR();
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();

        itens.add("DTHR: " + movEquipProprioBean.getDthrMovEquipProprio());

        if(movEquipProprioBean.getTipoMovEquipProprio() == 1L){
            itens.add("ENTRADA");
        } else {
            itens.add("SAÍDA");
        }

        itens.add("VEÍCULO: " + configCTR.getEquipId(movEquipProprioBean.getIdEquipMovEquipProprio()).getNroEquip());
        itens.add("MOTORISTA: " + movEquipProprioBean.getNroMatricColabMovEquipProprio() + " - " + configCTR.getColabMatric(movEquipProprioBean.getNroMatricColabMovEquipProprio()).getNomeColab());

        String passageiro = "";
        List<MovEquipProprioPassagBean> movEquipProprioPassagList = movEquipProprioPassagDAO.movEquipProprioPassagIdMovEquipList(movEquipProprioBean.getIdMovEquipProprio());
        for(MovEquipProprioPassagBean movEquipProprioPassagBean : movEquipProprioPassagList){
            passageiro = passageiro + movEquipProprioPassagBean.getNroMatricMovEquipProprioPassag() + " - " + configCTR.getColabMatric(movEquipProprioBean.getNroMatricColabMovEquipProprio()).getNomeColab() + "; ";
        }

        itens.add("PASSAGEIRO(S): " + passageiro);
        itens.add("DESTINO: " +  movEquipProprioBean.getDestinoMovEquipProprio());

        String equipSec = "";
        List<MovEquipProprioSegBean> movEquipProprioSegList = movEquipProprioSegDAO.movEquipProprioSegIdMovEquipList(movEquipProprioBean.getIdMovEquipProprio());
        for(MovEquipProprioSegBean movEquipProprioSegBean : movEquipProprioSegList){
            equipSec = equipSec + configCTR.getEquipId(movEquipProprioSegBean.getIdEquipMovEquipProprioSeg()).getNroEquip() + " - ";
        }
        movEquipProprioSegList.clear();

        itens.add("VEÍCULO SECUNDÁRIO: " + equipSec);

        if(movEquipProprioBean.getNroNotaFiscalMovEquipProprio() != null){
            itens.add("NRO NOTA FISCAL: " +  movEquipProprioBean.getNroNotaFiscalMovEquipProprio());
        } else {
            itens.add("NRO NOTA FISCAL: ");
        }

        if(movEquipProprioBean.getObservMovEquipProprio() != null){
            itens.add("OBS.:\n" +  movEquipProprioBean.getObservMovEquipProprio());
        } else {
            itens.add("OBS.:");
        }
        return itens;
    }

    public void setNroMatricColabProprio(Long nroMatricColab){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setNroMatricColab(nroMatricColab);
    }

    public void setNroMatricColabProprio(int posicao, Long nroMatricColab){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setNroMatricColab(posicao, nroMatricColab);
    }

    public void setNroEquipProprio(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setIdEquip(equipDAO.getEquipNro(nroEquip).getIdEquip());
    }

    public void setNroEquipProprio(int posicao, Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setIdEquip(posicao, equipDAO.getEquipNro(nroEquip).getIdEquip());
    }

    public void setDescrDestinoProprio(String descrDestino){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setDescrDestino(descrDestino);
    }

    public void setDescrDestinoProprio(int posicao, String descrDestino){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setDescrDestino(posicao, descrDestino);
    }

    public void setNroNotaFiscalProprio(Long nroNotaFiscal){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setNroNotaFiscal(nroNotaFiscal);
    }

    public void setNroNotaFiscalProprio(int posicao, Long nroNotaFiscal){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setNroNotaFiscal(posicao, nroNotaFiscal);
    }

    public void setObservacaoProprio(int posicao, String observacao){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setObservacao(posicao, observacao);
    }

    public List<MovEquipProprioBean> movEquipProprioFechadoList(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.movEquipProprioFechadoList();
    }

    public List<MovEquipProprioSegBean> movEquipProprioSegAbertoList() {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        return movEquipProprioSegDAO.movEquipProprioSegIdMovEquipList(movEquipProprioDAO.getMovEquipProprioAberto().getIdMovEquipProprio());
    }

    public List<MovEquipProprioSegBean> movEquipProprioSegFechadoList(int posicao) {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        return movEquipProprioSegDAO.movEquipProprioSegIdMovEquipList(movEquipProprioDAO.getMovEquipProprioFechado(posicao).getIdMovEquipProprio());
    }

    public List<MovEquipProprioPassagBean> movEquipProprioPassagAbertoList() {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        return movEquipProprioPassagDAO.movEquipProprioPassagIdMovEquipList(movEquipProprioDAO.getMovEquipProprioAberto().getIdMovEquipProprio());
    }

    public List<MovEquipProprioPassagBean> movEquipProprioPassagFechadoList(int posicao) {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        return movEquipProprioPassagDAO.movEquipProprioPassagIdMovEquipList(movEquipProprioDAO.getMovEquipProprioFechado(posicao).getIdMovEquipProprio());
    }

    public String dadosEnvioMovEquipProprio(){

        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        ArrayList<Long> idMovEquipProprioArrayList = movEquipProprioDAO.idMovEquipProprioArrayList(movEquipProprioDAO.movEquipProprioEnviarList());
        String dadosMovEquipProprio = movEquipProprioDAO.dadosEnvioMovEquipProprio();

        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        String dadosEnvioMovEquipProprioSeg = movEquipProprioSegDAO.dadosEnvioMovEquipProprioSeg(movEquipProprioSegDAO.movEquipProprioSegEnvioList(idMovEquipProprioArrayList));

        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        String dadosEnvioMovEquipProprioPassag = movEquipProprioPassagDAO.dadosEnvioMovEquipProprioPassag(movEquipProprioPassagDAO.movEquipProprioPassagEnvioList(idMovEquipProprioArrayList));

        return dadosMovEquipProprio + "_" + dadosEnvioMovEquipProprioSeg + "_" + dadosEnvioMovEquipProprioPassag;

    }

    public void updateMovEquipProprio(String result, String activity){

        try {

            String[] retorno = result.split("_");

            MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
            ArrayList<Long> movEquipProprioArrayList = movEquipProprioDAO.idMovEquipProprioArrayList(retorno[1]);
            movEquipProprioDAO.updateMovEquipProprioEnviado(movEquipProprioArrayList);

            deleteMovEquipProprioEnviado();

            EnvioDadosServ.getInstance().envioDados(activity);

        }
        catch (Exception e){
            EnvioDadosServ.status = 1;
            LogErroDAO.getInstance().insertLogErro(e);
        }

    }

    public void deleteMovEquipProprioEnviado(){

        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        ArrayList<Long> idMovEquipProprioArrayList = movEquipProprioDAO.idMovEquipProprioExcluirArrayList();

        for (Long idMovEquipProprio : idMovEquipProprioArrayList) {

            MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
            movEquipProprioSegDAO.deleteMovEquipProprioSegIdMovEquip(idMovEquipProprio);

            MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
            movEquipProprioPassagDAO.deleteMovEquipProprioPassagIdMovEquip(idMovEquipProprio);

            movEquipProprioDAO.deleteMovEquipProprio(idMovEquipProprio);

        }

        idMovEquipProprioArrayList.clear();

    }

}
