package br.com.usinasantafe.pcp.control;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pcp.model.bean.estaticas.TerceiroBean;
import br.com.usinasantafe.pcp.model.bean.estaticas.VisitanteBean;
import br.com.usinasantafe.pcp.model.bean.variaveis.MovEquipVisitTercBean;
import br.com.usinasantafe.pcp.model.dao.LogErroDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipProprioDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipSegProprioDAO;
import br.com.usinasantafe.pcp.model.dao.MovEquipVisitTercDAO;
import br.com.usinasantafe.pcp.model.dao.TerceiroDAO;
import br.com.usinasantafe.pcp.model.dao.VisitanteDAO;
import br.com.usinasantafe.pcp.util.EnvioDadosServ;

public class MovVeicVisitTercCTR {

    public MovVeicVisitTercCTR() {
    }

    public boolean verMovEquipVisitTercAberto(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.verMovEquipVisitTercAberto();
    }

    public boolean verEnvioMovEquipVisitTercFech(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.verMovEquipVisitTercFechado();
    }

    public boolean verTerceiroCpf(String cpfTerceiro){
        TerceiroDAO terceiroDAO = new TerceiroDAO();
        return terceiroDAO.verTerceiroCpf(cpfTerceiro);
    }

    public boolean verVisitanteCpf(String cpfVisitante){
        VisitanteDAO visitanteDAO = new VisitanteDAO();
        return visitanteDAO.verVisitanteCpf(cpfVisitante);
    }

    public TerceiroBean getTerceiroCpf(String cpfTerceiro){
        TerceiroDAO terceiroDAO = new TerceiroDAO();
        return terceiroDAO.getTerceiroCpf(cpfTerceiro);
    }

    public TerceiroBean getTerceiroId(Long idTerceiro){
        TerceiroDAO terceiroDAO = new TerceiroDAO();
        return terceiroDAO.getTerceiroId(idTerceiro);
    }

    public VisitanteBean getVisitanteCpf(String cpfVisitante){
        VisitanteDAO visitanteDAO = new VisitanteDAO();
        return visitanteDAO.getVisitanteCpf(cpfVisitante);
    }

    public VisitanteBean getVisitanteId(Long idVisitante){
        VisitanteDAO visitanteDAO = new VisitanteDAO();
        return visitanteDAO.getVisitanteId(idVisitante);
    }

    public MovEquipVisitTercBean getMovEquipVisitTercAberto(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.getMovEquipVisitTercAberto();
    }

    public void abrirMovEquipVisitTerc(Long tipoMov){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.abrirMovEquipVisitTerc(tipoMov);
    }

    public void fecharMovEquipVisitTerc(String observacao){
        ConfigCTR configCTR = new ConfigCTR();
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.fecharMovEquipVisitTerc(configCTR.getConfig().getMatricVigiaConfig(), observacao);
    }

    public void deleteMovEquipVisitTercAberto(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        List<MovEquipVisitTercBean> movEquipVisitTercList = movEquipVisitTercDAO.movEquipVisitTercAbertoList();
        for(MovEquipVisitTercBean movEquipVisitTercBean : movEquipVisitTercList){
            movEquipVisitTercDAO.deleteMovEquipVisitTerc(movEquipVisitTercBean.getIdMovEquipVisitTerc());
        }
        movEquipVisitTercList.clear();
    }

    public void setTipoVisitTerc(Long tipoVisitTerc){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setTipoVisitTerc(tipoVisitTerc);
    }

    public void setIdVisitTerc(Long idVisitTerc){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setIdVisitTerc(idVisitTerc);
    }

    public void setVeiculoVisitTerc(String veiculo){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setVeiculoVisitTerc(veiculo);
    }

    public void setPlacaVisitTerc(String placa){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setPlacaVisitTerc(placa);
    }

    public void setDestinoVisitTerc(String destino){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.setDestinoVisitTerc(destino);
    }

    public List<MovEquipVisitTercBean> movEquipVisitTercAllList(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.movEquipVisitTercAllList();
    }

    public String dadosEnvioMovEquipVisitTerc(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.dadosEnvioMovEquipVisitTerc();
    }

    public void updateMovEquipVisitFechado(String result, String activity){

        try {

            String[] retorno = result.split("_");

            MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
            ArrayList<Long> movEquipProprioArrayList = movEquipVisitTercDAO.idMovEquipVisitTercArrayList(retorno[1]);
            movEquipVisitTercDAO.updateEquipVisitTercEnvio(movEquipProprioArrayList);

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
            movEquipVisitTercDAO.deleteMovEquipVisitTerc(idMovEquipVisitTerc);
        }

        idMovEquipVisitTercArrayList.clear();

    }
}
