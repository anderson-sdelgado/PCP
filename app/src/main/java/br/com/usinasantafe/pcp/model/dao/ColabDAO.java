package br.com.usinasantafe.pcp.model.dao;

import java.util.List;

import br.com.usinasantafe.pcp.model.bean.estaticas.ColabBean;

public class ColabDAO {

    public ColabDAO() {
    }

    public boolean verColab(Long matricColab){
        List<ColabBean> colabList = colabList(matricColab);
        boolean ret = colabList.size() > 0;
        colabList.clear();
        return ret;
    }

    public ColabBean getColab(Long matricColab){
        List<ColabBean> colabList = colabList(matricColab);
        ColabBean colabBean = colabList.get(0);
        colabList.clear();
        return colabBean;
    }

    private List<ColabBean> colabList(Long matricFunc){
        ColabBean colabBean = new ColabBean();
        return colabBean.get("matricColab", matricFunc);
    }

}
