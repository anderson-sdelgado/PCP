package br.com.usinasantafe.pcp.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbterceiroest")
public class TerceiroBean extends Entidade {

        private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idTerceiro;
    @DatabaseField
    private String cpfTerceiro;
    @DatabaseField
    private String nomeTerceiro;

    public TerceiroBean() {
    }

    public Long getIdTerceiro() {
        return idTerceiro;
    }

    public void setIdTerceiro(Long idTerceiro) {
        this.idTerceiro = idTerceiro;
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
}
