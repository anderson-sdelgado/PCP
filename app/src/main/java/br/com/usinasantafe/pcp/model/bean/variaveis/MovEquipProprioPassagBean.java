package br.com.usinasantafe.pcp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbmovequippropriopassagvar")
public class MovEquipProprioPassagBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idMovEquipProprioPassag;
    @DatabaseField
    private Long idMovEquipProprio;
    @DatabaseField
    private Long nroMatricMovEquipProprioPassag;

    public MovEquipProprioPassagBean() {
    }

    public Long getIdMovEquipProprioPassag() {
        return idMovEquipProprioPassag;
    }

    public void setIdMovEquipProprioPassag(Long idMovEquipProprioPassag) {
        this.idMovEquipProprioPassag = idMovEquipProprioPassag;
    }

    public Long getIdMovEquipProprio() {
        return idMovEquipProprio;
    }

    public void setIdMovEquipProprio(Long idMovEquipProprio) {
        this.idMovEquipProprio = idMovEquipProprio;
    }

    public Long getNroMatricMovEquipProprioPassag() {
        return nroMatricMovEquipProprioPassag;
    }

    public void setNroMatricMovEquipProprioPassag(Long nroMatricMovEquipProprioPassag) {
        this.nroMatricMovEquipProprioPassag = nroMatricMovEquipProprioPassag;
    }

}
