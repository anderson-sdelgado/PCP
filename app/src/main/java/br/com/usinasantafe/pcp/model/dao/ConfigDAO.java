package br.com.usinasantafe.pcp.model.dao;

import java.util.List;

import br.com.usinasantafe.pcp.model.bean.variaveis.ConfigBean;

public class ConfigDAO {

    public boolean hasElements(){
        ConfigBean configBean = new ConfigBean();
        return configBean.hasElements();
    }

    public boolean verSenha(String senha){
        ConfigBean configBean = new ConfigBean();
        List<ConfigBean> configList = configBean.get("senhaConfig", senha);
        boolean ret = configList.size() > 0;
        configList.clear();
        return ret;
    }

    public ConfigBean getConfig(){
        ConfigBean configBean = new ConfigBean();
        List<ConfigBean> configList = configBean.all();
        configBean = configList.get(0);
        configList.clear();
        return configBean;
    }

    public void salvarConfig(Long numLinha, String senha){
        ConfigBean configBean = new ConfigBean();
        configBean.deleteAll();
        configBean.setNumLinhaConfig(numLinha);
        configBean.setSenhaConfig(senha);
        configBean.setPosicaoTela(0L);
        configBean.insert();
        configBean.commit();
    }

}
