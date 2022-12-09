package br.com.usinasantafe.pcp.control;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipResidenciaBean;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipResidenciaDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class MovVeicResidenciaCTR {

    public boolean verEnvioMovEquipResidenciaFech() {
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.verMovEquipResidenciaFechado();
    }

    public void abrirMovEquipResidencia(Long tipoMov){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.abrirMovEquipResidencia(tipoMov);
    }

    public void fecharMovEquipResidencia(String observacao, String activity){

        ConfigCTR configCTR = new ConfigCTR();
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.fecharMovEquipResidencia(configCTR.getConfig().getMatricVigiaConfig(), observacao, configCTR.getConfig().getPosicaoListaMov());

        EnvioDadosServ.getInstance().envioDados(activity);

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

    public void setNomeVisitanteResidencia(String nomeVisitante){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setNomeVisitanteResidencia(nomeVisitante);
    }

    public void setVeiculoResidencia(String veiculo){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setVeiculoResidencia(veiculo);
    }

    public void setPlacaResidencia(String placa){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        movEquipResidenciaDAO.setPlacaResidencia(placa);
    }

    public List<MovEquipResidenciaBean> movEquipResidenciaList(){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.movEquipResidenciaEntradaList();
    }

    public String dadosEnvioMovEquipResidencia(){
        MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
        return movEquipResidenciaDAO.dadosEnvioMovEquipResidencia();
    }

    public void updateMovEquipResidenciaFechado(String result, String activity){

        try {

            String[] retorno = result.split("_");

            MovEquipResidenciaDAO movEquipResidenciaDAO = new MovEquipResidenciaDAO();
            ArrayList<Long> movEquipResidenciaArrayList = movEquipResidenciaDAO.idMovEquipResidenciaArrayList(retorno[1]);
            movEquipResidenciaDAO.updateEquipResidenciaEnvio(movEquipResidenciaArrayList);

//            deleteMovEquipResidenciaEnviado();

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
