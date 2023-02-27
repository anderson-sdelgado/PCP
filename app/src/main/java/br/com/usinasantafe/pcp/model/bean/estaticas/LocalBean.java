package br.com.usinasantafe.pcp.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tblocalest")
public class LocalBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long idLocal;
    @DatabaseField
    private String descrLocal;

    public LocalBean() {
    }

    public Long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Long idLocal) {
        this.idLocal = idLocal;
    }

    public String getDescrLocal() {
        return descrLocal;
    }

    public void setDescrLocal(String descrLocal) {
        this.descrLocal = descrLocal;
    }
}
