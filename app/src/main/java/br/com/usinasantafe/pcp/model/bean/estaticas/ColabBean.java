package br.com.usinasantafe.pcp.model.bean.estaticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tbcolaboradorest")
public class ColabBean {

    private static final long serialVersionUID = 1L;

    @DatabaseField(id=true)
    private Long matricColab;
    @DatabaseField
    private String nomeColab;

    public ColabBean() {
    }

    public Long getMatricColab() {
        return matricColab;
    }

    public void setMatricColab(Long matricColab) {
        this.matricColab = matricColab;
    }

    public String getNomeColab() {
        return nomeColab;
    }

    public void setNomeColab(String nomeColab) {
        this.nomeColab = nomeColab;
    }
}
