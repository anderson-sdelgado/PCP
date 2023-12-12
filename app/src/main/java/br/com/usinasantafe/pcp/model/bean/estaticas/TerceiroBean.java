package br.com.usinasantafe.pcp.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbterceiroest")
public class TerceiroBean extends Entidade {

        private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idTerceiro;
    @DatabaseField
    private Long idBDTerceiro;
    @DatabaseField
    private String cpfTerceiro;
    @DatabaseField
    private String nomeTerceiro;
    @DatabaseField
    private String empresaTerceiro;

    public TerceiroBean() {
    }

    public Long getIdTerceiro() {
        return idTerceiro;
    }

    public void setIdTerceiro(Long idTerceiro) {
        this.idTerceiro = idTerceiro;
    }

    public Long getIdBDTerceiro() {
        return idBDTerceiro;
    }

    public void setIdBDTerceiro(Long idBDTerceiro) {
        this.idBDTerceiro = idBDTerceiro;
    }

    public String getCpfTerceiro() {
        return cpfTerceiro;
    }

    public void setCpfTerceiro(String cpfTerceiro) {
        this.cpfTerceiro = cpfTerceiro;
    }

    public String getNomeTerceiro() {
        return nomeTerceiro;
    }

    public void setNomeTerceiro(String nomeTerceiro) {
        this.nomeTerceiro = nomeTerceiro;
    }

    public String getEmpresaTerceiro() {
        return empresaTerceiro;
    }

    public void setEmpresaTerceiro(String empresaTerceiro) {
        this.empresaTerceiro = empresaTerceiro;
    }
}
