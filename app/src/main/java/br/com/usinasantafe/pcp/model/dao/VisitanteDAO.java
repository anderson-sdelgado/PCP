package br.com.usinasantafe.pcp.model.dao;

import java.util.List;

import br.com.usinasantafe.pcp.model.bean.estaticas.VisitanteBean;

public class VisitanteDAO {

    public VisitanteDAO() {
    }

    public boolean hasElements(){
        VisitanteBean visitanteBean = new VisitanteBean();
        return visitanteBean.hasElements();
    }

    public boolean verVisitanteCpf(String cpfVisitante){
        List<VisitanteBean> visitanteList = visitanteCpfList(cpfVisitante);
        boolean ret = visitanteList.size() > 0;
        visitanteList.clear();
        return ret;
    }

    public VisitanteBean getVisitanteCpf(String cpfVisitante){
        List<VisitanteBean> visitanteList = visitanteCpfList(cpfVisitante);
        VisitanteBean visitanteBean = visitanteList.get(0);
        visitanteList.clear();
        return visitanteBean;
    }

    public VisitanteBean getVisitanteId(Long idVisitante){
        List<VisitanteBean> visitanteList = visitanteIdList(idVisitante);
        VisitanteBean visitanteBean = visitanteList.get(0);
        visitanteList.clear();
        return visitanteBean;
    }

    private List<VisitanteBean> visitanteCpfList(String cpfVisitante){
        VisitanteBean visitanteBean = new VisitanteBean();
        return visitanteBean.get("cpfVisitante", cpfVisitante);
    }

    private List<VisitanteBean> visitanteIdList(Long idVisitante){
        VisitanteBean visitanteBean = new VisitanteBean();
        return visitanteBean.get("idVisitante", idVisitante);
    }

}
