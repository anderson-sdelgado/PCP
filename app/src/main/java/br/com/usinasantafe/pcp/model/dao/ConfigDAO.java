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

    public void salvarConfig(Long nroAparelho, String senha){
        ConfigBean configBean = new ConfigBean();
        configBean.deleteAll();
        configBean.setNroAparelhoConfig(nroAparelho);
        configBean.setSenhaConfig(senha);
        configBean.setMatricVigiaConfig(0L);
        configBean.setIdLocalConfig(0L);
        configBean.setPosicaoTela(0L);
        configBean.insert();
        configBean.commit();
    }

    public void fecharApont(){
        ConfigBean configBean = getConfig();
        configBean.setMatricVigiaConfig(0L);
        configBean.setIdLocalConfig(0L);
        configBean.update();
    }

    public void setPosicaoTela(Long posicaoTela){
        ConfigBean configBean = getConfig();
        configBean.setPosicaoTela(posicaoTela);
        configBean.update();
    }

    public void setMatricVigia(Long matricVigia){
        ConfigBean configBean = getConfig();
        configBean.setMatricVigiaConfig(matricVigia);
        configBean.update();
    }

    public void setIdLocal(Long idLocal){
        ConfigBean configBean = getConfig();
        configBean.setIdLocalConfig(idLocal);
        configBean.update();
    }

    public void setPosicaoListaMov(Long posicaoListaMov){
        ConfigBean configBean = getConfig();
        configBean.setPosicaoListaMov(posicaoListaMov);
        configBean.update();
    }

    public void setTipoMov(Long tipoMov){
        ConfigBean configBean = getConfig();
        configBean.setTipoMov(tipoMov);
        configBean.update();
    }

}
