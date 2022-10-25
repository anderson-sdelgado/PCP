package br.com.usinasantafe.pcp.control;

import br.com.usinasantafe.pcp.model.dao.MovEquipProprioDAO;

public class MovimentacaoVeicProprioCTR {

    public MovimentacaoVeicProprioCTR() {
    }

    public boolean verMovEquipProprioAberto(){
        MovEquipProprioDAO movEquipProprioDAO = new MovEquipProprioDAO();
        return movEquipProprioDAO.verMovEquipProprioAberto();
    }

}
