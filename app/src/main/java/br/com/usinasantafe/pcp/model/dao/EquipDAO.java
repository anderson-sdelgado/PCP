package br.com.usinasantafe.pcp.model.dao;

import java.util.List;

import br.com.usinasantafe.pcp.model.bean.estaticas.EquipBean;

public class EquipDAO {

    public boolean verEquipNro(Long nroEquip){
        List<EquipBean> equipList = equipNroList(nroEquip);
        boolean retorno = equipList.size() > 0;
        equipList.clear();
        return retorno;
    }

    public EquipBean getEquipId(Long idEquip){
        List<EquipBean> equipList = equipIdList(idEquip);
        EquipBean equipBean = equipList.get(0);
        equipList.clear();
        return equipBean;
    }

    public EquipBean getEquipNro(Long nroEquip){
        List<EquipBean> equipList = equipNroList(nroEquip);
        EquipBean equipBean = equipList.get(0);
        equipList.clear();
        return equipBean;
    }

    private List<EquipBean> equipIdList(Long idEquip){
        EquipBean equipBean = new EquipBean();
        return equipBean.get("idEquip", idEquip);
    }

    private List<EquipBean> equipNroList(Long nroEquip){
        EquipBean equipBean = new EquipBean();
        return equipBean.get("nroEquip", nroEquip);
    }

}
