package br.com.usinasantafe.pcp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbmovequipsegpropriovar")
public class MovEquipProprioSegBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idMovEquipProprioSeg;
    @DatabaseField
    private Long idMovEquipProprio;
    @DatabaseField
    private Long idEquipMovEquipProprioSeg;

    public MovEquipProprioSegBean() {
    }

    public Long getIdMovEquipProprioSeg() {
        return idMovEquipProprioSeg;
    }

    public void setIdMovEquipProprioSeg(Long idMovEquipProprioSeg) {
        this.idMovEquipProprioSeg = idMovEquipProprioSeg;
    }

    public Long getIdMovEquipProprio() {
        return idMovEquipProprio;
    }

    public void setIdMovEquipProprio(Long idMovEquipProprio) {
        this.idMovEquipProprio = idMovEquipProprio;
    }

    public Long getIdEquipMovEquipProprioSeg() {
        return idEquipMovEquipProprioSeg;
    }

    public void setIdEquipMovEquipProprioSeg(Long idEquipMovEquipProprioSeg) {
        this.idEquipMovEquipProprioSeg = idEquipMovEquipProprioSeg;
    }

}
