package br.com.usinasantafe.pcp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbmovequipvisittercpassagvar")
public class MovEquipVisitTercPassagBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idMovEquipVisitTercPassag;
    @DatabaseField
    private Long idMovEquipVisitTerc;
    @DatabaseField
    private Long idVisitTercMovEquipVisitTercPassag;

    public MovEquipVisitTercPassagBean() {
    }

    public Long getIdMovEquipVisitTercPassag() {
        return idMovEquipVisitTercPassag;
    }

    public void setIdMovEquipVisitTercPassag(Long idMovEquipVisitTercPassag) {
        this.idMovEquipVisitTercPassag = idMovEquipVisitTercPassag;
    }

    public Long getIdMovEquipVisitTerc() {
        return idMovEquipVisitTerc;
    }

    public void setIdMovEquipVisitTerc(Long idMovEquipVisitTerc) {
        this.idMovEquipVisitTerc = idMovEquipVisitTerc;
    }

    public Long getIdVisitTercMovEquipVisitTercPassag() {
        return idVisitTercMovEquipVisitTercPassag;
    }

    public void setIdVisitTercMovEquipVisitTercPassag(Long idVisitTercMovEquipVisitTercPassag) {
        this.idVisitTercMovEquipVisitTercPassag = idVisitTercMovEquipVisitTercPassag;
    }
}
