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

    public boolean verEnvioMovEquipProprioFech() {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.verMovEquipProprioFechado();
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

    public void inserirMovEquipProprioPassag(Long matricColab){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        movEquipProprioPassagDAO.inserirMovEquipProprioPassag(movEquipProprioDAO.getMovEquipProprioAberto().getIdMovEquipProprio(), matricColab);
    }

    public void fecharMovEquipProprio(String observacao, String activity){

        ConfigCTR configCTR = new ConfigCTR();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.fecharMovEquipProprio(configCTR.getConfig().getIdLocalConfig(), configCTR.getConfig().getMatricVigiaConfig(), observacao);

        EnvioDadosServ.getInstance().envioDados(activity);

    }

    public void deleteMovEquipProprioAberto(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioDAO.movEquipProprioAbertoList();
        for(MovEquipProprioBean movEquipProprioBean : movEquipProprioList){
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

    public ArrayList<String> getMovEquipProprio(MovEquipProprioBean movEquipProprioBean){
        ArrayList<String> itens = new ArrayList<String>();
        ConfigCTR configCTR = new ConfigCTR();
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        itens.add("DTHR: " + movEquipProprioBean.getDthrMovEquipProprio());
        if(movEquipProprioBean.getTipoMovEquipProprio() == 1L){
            itens.add("ENTRADA");
        } else {
            itens.add("SAÍDA");
        }
        itens.add("VEÍCULO: " + configCTR.getEquipId(movEquipProprioBean.getIdEquipMovEquipProprio()).getNroEquip());
        itens.add("COLABORADOR: " + movEquipProprioBean.getNroMatricColabMovEquipProprio() + " - " + configCTR.getColabMatric(movEquipProprioBean.getNroMatricColabMovEquipProprio()).getNomeColab());
        itens.add("VIGIA: " + movEquipProprioBean.getNroMatricVigiaMovEquipProprio() + " - " + configCTR.getColabMatric(movEquipProprioBean.getNroMatricVigiaMovEquipProprio()).getNomeColab());
        itens.add("DESTINO: " +  movEquipProprioBean.getDescrDestinoMovEquipProprio());

        String equipSec = "";
        List<MovEquipProprioSegBean> movEquipSegProprioList = movEquipProprioSegDAO.movEquipProprioSegIdMovEquipList(movEquipProprioBean.getIdMovEquipProprio());
        for(MovEquipProprioSegBean movEquipProprioSegBean : movEquipSegProprioList){
            equipSec = equipSec + configCTR.getEquipId(movEquipProprioSegBean.getIdEquipMovEquipProprioSeg()).getNroEquip() + " - ";
        }
        itens.add("VEÍCULO SECUNDÁRIO: " + equipSec);
        if(movEquipProprioBean.getNroNotaFiscalMovEquipProprio() != null){
            itens.add("NRO NOTA FISCAL: " +  movEquipProprioBean.getNroNotaFiscalMovEquipProprio());
        } else {
            itens.add("NRO NOTA FISCAL: ");
        }
        if(movEquipProprioBean.getObservacaoMovEquipProprio() != null){
            itens.add("OBS.:\n" +  movEquipProprioBean.getObservacaoMovEquipProprio());
        } else {
            itens.add("OBS.:");
        }
        return itens;
    }

    public void setNroMatricColab(Long nroMatricColab){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setNroMatricColab(nroMatricColab);
    }

    public void setEquip(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setIdEquip(equipDAO.getEquipNro(nroEquip).getIdEquip());
    }

    public void setDescrDestino(String descrDestino){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setDescrDestino(descrDestino);
    }

    public void setNroNotaFiscal(Long nroNotaFiscal){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.setNroNotaFiscal(nroNotaFiscal);
    }

    public List<MovEquipProprioBean> movEquipProprioAllList(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.movEquipProprioAllList();
    }

    public List<MovEquipProprioBean> movEquipProprioList(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.movEquipProprioList();
    }

    public List<MovEquipProprioSegBean> movEquipProprioSegList() {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        return movEquipProprioSegDAO.movEquipProprioSegIdMovEquipList(movEquipProprioDAO.getMovEquipProprioAberto().getIdMovEquipProprio());
    }

    public List<MovEquipProprioPassagBean> movEquipProprioPassagList() {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        return movEquipProprioPassagDAO.movEquipProprioPassagIdMovEquipList(movEquipProprioDAO.getMovEquipProprioAberto().getIdMovEquipProprio());
    }

    public String dadosEnvioMovEquipProprio(){

        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        ArrayList<Long> idMovEquipProprioArrayList = movEquipProprioDAO.idMovEquipProprioArrayList(movEquipProprioDAO.movEquipProprioFechadoList());
        String dadosMovEquipProprio = movEquipProprioDAO.dadosEnvioMovEquipProprio();

        MovEquipProprioSegDAO movEquipProprioSegDAO = new MovEquipProprioSegDAO();
        String dadosEnvioMovEquipProprioSeg = movEquipProprioSegDAO.dadosEnvioMovEquipProprioSeg(movEquipProprioSegDAO.movEquipProprioSegEnvioList(idMovEquipProprioArrayList));

        MovEquipProprioPassagDAO movEquipProprioPassagDAO = new MovEquipProprioPassagDAO();
        String dadosEnvioMovEquipProprioPassag = movEquipProprioPassagDAO.dadosEnvioMovEquipProprioPassag(movEquipProprioPassagDAO.movEquipProprioPassagEnvioList(idMovEquipProprioArrayList));

        return dadosMovEquipProprio + "_" + dadosEnvioMovEquipProprioSeg + "_" + dadosEnvioMovEquipProprioPassag;
    }

    public void updateMovEquipProprioFechado(String result, String activity){

        try {

            String[] retorno = result.split("_");

            MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
            ArrayList<Long> movEquipProprioArrayList = movEquipProprioDAO.idMovEquipProprioArrayList(retorno[1]);
            movEquipProprioDAO.updateMovEquipProprioEnvio(movEquipProprioArrayList);

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

            movEquipProprioDAO.deleteMovEquipProprio(idMovEquipProprio);

        }

        idMovEquipProprioArrayList.clear();

    }

}
