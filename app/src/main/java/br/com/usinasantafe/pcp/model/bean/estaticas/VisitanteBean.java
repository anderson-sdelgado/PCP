package br.com.usinasantafe.pcp.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbvisitanteest")
public class VisitanteBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idVisitante;
    @DatabaseField
    private String cpfVisitante;
    @DatabaseField
    private String nomeVisitante;
    @DatabaseField
    private String empresaVisitante;

    public VisitanteBean() {
    }

    public Long getIdVisitante() {
        return idVisitante;
    }

    public void setIdVisitante(Long idVisitante) {
        this.idVisitante = idVisitante;
    }

    public String getCpfVisitante() {
        return cpfVisitante;
    }

    public void setCpfVisitante(String cpfVisitante) {
        this.cpfVisitante = cpfVisitante;
    }

    public String getNomeVisitante() {
        return nomeVisitante;
    }

    public void setNomeVisitante(String nomeVisitante) {
        this.nomeVisitante = nomeVisitante;
    }

    public String getEmpresaVisitante() {
        return empresaVisitante;
    }

    public void setEmpresaVisitante(String empresaVisitante) {
        this.empresaVisitante = empresaVisitante;
    }
}
