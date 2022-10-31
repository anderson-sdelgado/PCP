package br.com.usinasantafe.pcp.control;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipProprioBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipSegProprioBean;
import br.com.usinasantafe.pcp.model.dao.EquipDAO;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipProprioDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipSegProprioDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class MovVeicProprioCTR {

    public MovVeicProprioCTR() {
    }

    public boolean verMovEquipProprioAberto(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.verMovEquipProprioAberto();
    }

    public boolean verEnvioMovEquipProprioFech() {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.verMovEquipProprioFechado();
    }

    public MovEquipProprioBean getMovEquipProprioAberto(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.getMovEquipProprioAberto();
    }

    public void abrirMovEquipProprio(Long tipoMov){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.abrirMovEquipProprio(tipoMov);
    }

    public void inserirMovEquipSegProprio(Long nroEquip){
        EquipDAO equipDAO = new EquipDAO();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipSegProprioDAO movEquipSegProprioDAO = new MovEquipSegProprioDAO();
        movEquipSegProprioDAO.inserirMovEquipSegProprio(movEquipProprioDAO.getMovEquipProprioAberto().getIdMovEquipProprio(), equipDAO.getEquipNro(nroEquip).getIdEquip());
    }

    public void fecharMovEquipProprio(String observacao, String activity){

        ConfigCTR configCTR = new ConfigCTR();
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        movEquipProprioDAO.fecharMovEquipProprio(configCTR.getConfig().getMatricVigiaConfig(), observacao);

        EnvioDadosServ.getInstance().envioDados(activity);

    }

    public void deleteMovEquipProprioAberto(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipSegProprioDAO movEquipSegProprioDAO = new MovEquipSegProprioDAO();
        List<MovEquipProprioBean> movEquipProprioList = movEquipProprioDAO.movEquipProprioAbertoList();
        for(MovEquipProprioBean movEquipProprioBean : movEquipProprioList){
            movEquipSegProprioDAO.deleteMovEquipSegProprio(movEquipProprioBean.getIdMovEquipProprio());
            movEquipProprioDAO.deleteMovEquipProprio(movEquipProprioBean.getIdMovEquipProprio());
        }
        movEquipProprioList.clear();
    }

    public void deleteMovEquipSegProprio(MovEquipSegProprioBean movEquipSegProprioBean){

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

    public List<MovEquipSegProprioBean> movEquipSegProprioList() {
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        MovEquipSegProprioDAO movEquipSegProprioDAO = new MovEquipSegProprioDAO();
        return movEquipSegProprioDAO.movEquipSegProprioIdMovEquipList(movEquipProprioDAO.getMovEquipProprioAberto().getIdMovEquipProprio());
    }

    public String dadosEnvioMovEquipProprio(){

        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        ArrayList<Long> idMovEquipProprioArrayList = movEquipProprioDAO.idMovEquipProprioArrayList(movEquipProprioDAO.movEquipProprioFechadoList());
        String dadosMovEquipProprio = movEquipProprioDAO.dadosEnvioMovEquipProprio();

        MovEquipSegProprioDAO movEquipSegProprioDAO = new MovEquipSegProprioDAO();
        String dadosEnvioMovEquipSegProprio = movEquipSegProprioDAO.dadosEnvioMovEquipSegProprio(movEquipSegProprioDAO.movEquipSegProprioEnvioList(idMovEquipProprioArrayList));

        return dadosMovEquipProprio + "_" + dadosEnvioMovEquipSegProprio;
    }

    public void updateMovEquipProprioFechado(String result, String activity){

        try {

            String[] retorno = result.split("_");

            MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
            ArrayList<Long> movEquipProprioArrayList = movEquipProprioDAO.idMovEquipProprioArrayList(retorno[1]);
            movEquipProprioDAO.updateMovEquipProprioEnvio(movEquipProprioArrayList);

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

            MovEquipSegProprioDAO movEquipSegProprioDAO = new MovEquipSegProprioDAO();
            movEquipSegProprioDAO.deleteMovEquipSegProprio(idMovEquipProprio);

            movEquipProprioDAO.deleteMovEquipProprio(idMovEquipProprio);

        }

        idMovEquipProprioArrayList.clear();

    }

}
