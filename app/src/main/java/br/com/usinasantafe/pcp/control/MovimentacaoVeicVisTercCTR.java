package br.com.usinasantafe.pcp.control;

import br.com.usinasantafe.pcp.model.dao.MovEquipVisitTercDAO;

public class MovimentacaoVeicVisTercCTR {

    public MovimentacaoVeicVisTercCTR() {
    }

    public boolean verMovEquipProprioAberto(){
        MovEquipVisitTercDAO movEquipVisitTercDAO = new MovEquipVisitTercDAO();
        return movEquipVisitTercDAO.verMovEquipVisitTercAberto();
    }

}
