package br.com.usinasantafe.pcp.model.dao;

import java.util.List;

import br.com.usinasantafe.pcp.model.bean.estaticas.TerceiroBean;

public class TerceiroDAO {

    public TerceiroDAO() {
    }

    public boolean verTerceiroCpf(String cpfTerceiro){
        List<TerceiroBean> terceiroList = terceiroCpfList(cpfTerceiro);
        boolean ret = terceiroList.size() > 0;
        terceiroList.clear();
        return ret;
    }

    public TerceiroBean getTerceiroCpf(String cpfTerceiro){
        List<TerceiroBean> terceiroList = terceiroCpfList(cpfTerceiro);
        TerceiroBean terceiroBean = terceiroList.get(0);
        terceiroList.clear();
        return terceiroBean;
    }

    public TerceiroBean getTerceiroId(Long idTerceiro){
        List<TerceiroBean> terceiroList = terceiroIdList(idTerceiro);
        TerceiroBean terceiroBean = terceiroList.get(0);
        terceiroList.clear();
        return terceiroBean;
    }

    private List<TerceiroBean> terceiroCpfList(String cpfTerceiro){
        TerceiroBean terceiroBean = new TerceiroBean();
        return terceiroBean.get("cpfTerceiro", cpfTerceiro);
    }

    private List<TerceiroBean> terceiroIdList(Long idTerceiro){
        TerceiroBean terceiroBean = new TerceiroBean();
        return terceiroBean.get("idTerceiro", idTerceiro);
    }

}
