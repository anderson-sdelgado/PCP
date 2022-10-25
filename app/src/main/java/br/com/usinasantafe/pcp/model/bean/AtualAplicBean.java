package br.com.usinasantafe.pcp.model.bean;

/**
 * Created by anderson on 24/07/2017.
 */

public class AtualAplicBean {

    private Long idEquipAtual;
    private Long idCheckList;
    private String versaoAtual;
    private String versaoNova;
    private Long flagAtualApp;
    private Long flagAtualCheckList;
    private String dthr;

    public AtualAplicBean() {
    }

    public Long getIdEquipAtual() {
        return idEquipAtual;
    }

    public void setIdEquipAtual(Long idEquipAtual) {
        this.idEquipAtual = idEquipAtual;
    }

    public Long getIdCheckList() {
        return idCheckList;
    }

    public void setIdCheckList(Long idCheckList) {
        this.idCheckList = idCheckList;
    }

    public String getVersaoAtual() {
        return versaoAtual;
    }

    public void setVersaoAtual(String versaoAtual) {
        this.versaoAtual = versaoAtual;
    }

    public String getVersaoNova() {
        return versaoNova;
    }

    public void setVersaoNova(String versaoNova) {
        this.versaoNova = versaoNova;
    }

    public Long getFlagAtualApp() {
        return flagAtualApp;
    }

    public void setFlagAtualApp(Long flagAtualApp) {
        this.flagAtualApp = flagAtualApp;
    }

    public Long getFlagAtualCheckList() {
        return flagAtualCheckList;
    }

    public void setFlagAtualCheckList(Long flagAtualCheckList) {
        this.flagAtualCheckList = flagAtualCheckList;
    }

    public String getDthr() {
        return dthr;
    }

    public void setDthr(String dthr) {
        this.dthr = dthr;
    }
}
