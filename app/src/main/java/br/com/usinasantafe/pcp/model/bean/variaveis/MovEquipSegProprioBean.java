package br.com.usinasantafe.pcp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbmovequipsegpropriovar")
public class MovEquipSegProprioBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idMovEquipSegProprio;
    @DatabaseField
    private Long idMovEquipProprio;
    @DatabaseField
    private Long idEquipMovEquipSegProprio;

    public MovEquipSegProprioBean() {
    }

    public Long getIdMovEquipSegProprio() {
        return idMovEquipSegProprio;
    }

    public void setIdMovEquipSegProprio(Long idMovEquipSegProprio) {
        this.idMovEquipSegProprio = idMovEquipSegProprio;
    }

    public Long getIdMovEquipProprio() {
        return idMovEquipProprio;
    }

    public void setIdMovEquipProprio(Long idMovEquipProprio) {
        this.idMovEquipProprio = idMovEquipProprio;
    }

    public Long getIdEquipMovEquipSegProprio() {
        return idEquipMovEquipSegProprio;
    }

    public void setIdEquipMovEquipSegProprio(Long idEquipMovEquipSegProprio) {
        this.idEquipMovEquipSegProprio = idEquipMovEquipSegProprio;
    }

}
