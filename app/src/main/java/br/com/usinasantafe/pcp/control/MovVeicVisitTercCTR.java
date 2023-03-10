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

public class MovVeicVisitTercCTR {

    private MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO;

    public MovVeicVisitTercCTR() {
    }

    public MovEquipVisitTercPassagDAO getMovEquipVisitTercPassagDAO(){
        if (movEquipVisitTercPassagDAO == null)
            movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        return movEquipVisitTercPassagDAO;
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

    public void abrirMovEquipVisitTerc(Long tipoMov){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        movEquipVisitTercDAO.abrirMovEquipVisitTerc(tipoMov);
    }

    public void inserirMovEquipVisitTercPassag(Long idVisitTerc){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        movEquipVisitTercPassagDAO.inserirMovEquipVisitTercPassag(movEquipVisitTercDAO.getMovEquipVisitTercAberto().getIdMovEquipVisitTerc(), idVisitTerc);
    }

    public void fecharMovEquipVisitTerc(String observacao, String activity){

        ConfigCTR configCTR = new ConfigCTR();
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercBean movEquipVisitTercBean = getMovEquipVisitTercAberto();
        ConfigBean configBean = configCTR.getConfig();
        if(movEquipVisitTercBean.getStatusEntradaSaidaMovEquipVisitTerc() == 1L){
            movEquipVisitTercDAO.fecharEntradaMovEquipVisitTerc(configBean.getIdLocalConfig(), configBean.getMatricVigiaConfig(), observacao, movEquipVisitTercBean);
        } else {
            movEquipVisitTercDAO.fecharSaidaMovEquipVisitTerc(configBean.getIdLocalConfig(), configBean.getMatricVigiaConfig(), observacao, configBean.getPosicaoListaMov().intValue(), movEquipVisitTercBean);
        }

        EnvioDadosServ.getInstance().envioDados(activity);

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

    public List<MovEquipVisitTercBean> movEquipVisitTercEntradaList(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.movEquipVisitTercEntradaList();
    }

    public List<MovEquipVisitTercPassagBean> movEquipVisitTercPassagList() {
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        return movEquipVisitTercPassagDAO.movEquipVisitTercPassagIdMovEquipList(movEquipVisitTercDAO.getMovEquipVisitTercAberto().getIdMovEquipVisitTerc());
    }

    public String dadosEnvioMovEquipVisitTerc(){

        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        ArrayList<Long> idMovEquipVisitTercArrayList = movEquipVisitTercDAO.idMovEquipVisitTercArrayList(movEquipVisitTercDAO.movEquipVisitTercFechadoList());
        String dadosMovEquipVisitTerc = movEquipVisitTercDAO.dadosEnvioMovEquipVisitTerc();


        MovEquipVisitTercPassagDAO movEquipVisitTercPassagDAO = new MovEquipVisitTercPassagDAO();
        String dadosEnvioMovEquipVisitTercPassag = movEquipVisitTercPassagDAO.dadosEnvioMovEquipVisitTercPassag(movEquipVisitTercPassagDAO.movEquipProprioPassagEnvioList(idMovEquipVisitTercArrayList));

        return dadosMovEquipVisitTerc + "_" + dadosEnvioMovEquipVisitTercPassag;

    }

    public void updateMovEquipVisitTercFechado(String result, String activity){

        try {

            String[] retorno = result.split("_");

            MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
            ArrayList<Long> movEquipVisitTercArrayList = movEquipVisitTercDAO.idMovEquipVisitTercArrayList(retorno[1]);
            movEquipVisitTercDAO.updateEquipVisitTercEnvio(movEquipVisitTercArrayList);

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
