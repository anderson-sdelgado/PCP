package br.com.usinasantafe.pcp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbmovequippropriovar")
public class MovEquipProprioBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idMovEquipProprio;
    @DatabaseField
    private Long tipoMovEquipProprio;
    @DatabaseField
    private Long idEquipMovEquipProprio;
    @DatabaseField
    private Long idLocalMovEquipProprio;
    @DatabaseField
    private String dthrMovEquipProprio;
    @DatabaseField
    private Long dthrLongMovEquipProprio;
    @DatabaseField
    private Long nroMatricVigiaMovEquipProprio;
    @DatabaseField
    private Long nroMatricColabMovEquipProprio;
    @DatabaseField
    private String destinoMovEquipProprio;
    @DatabaseField
    private Long nroNotaFiscalMovEquipProprio;
    @DatabaseField
    private String observMovEquipProprio;
    @DatabaseField
    private Long statusMovEquipProprio; // // 1 - Aberto; 2 - Finalizado; 3 - Enviar; 4 - Enviado;

    public MovEquipProprioBean() {
    }

    public Long getIdMovEquipProprio() {
        return idMovEquipProprio;
    }

    public void setIdMovEquipProprio(Long idMovEquipProprio) {
        this.idMovEquipProprio = idMovEquipProprio;
    }

    public Long getTipoMovEquipProprio() {
        return tipoMovEquipProprio;
    }

    public void setTipoMovEquipProprio(Long tipoMovEquipProprio) {
        this.tipoMovEquipProprio = tipoMovEquipProprio;
    }

    public Long getIdEquipMovEquipProprio() {
        return idEquipMovEquipProprio;
    }

    public void setIdEquipMovEquipProprio(Long idEquipMovEquipProprio) {
        this.idEquipMovEquipProprio = idEquipMovEquipProprio;
    }

    public Long getIdLocalMovEquipProprio() {
        return idLocalMovEquipProprio;
    }

    public void setIdLocalMovEquipProprio(Long idLocalMovEquipProprio) {
        this.idLocalMovEquipProprio = idLocalMovEquipProprio;
    }

    public String getDthrMovEquipProprio() {
        return dthrMovEquipProprio;
    }

    public void setDthrMovEquipProprio(String dthrMovEquipProprio) {
        this.dthrMovEquipProprio = dthrMovEquipProprio;
    }

    public Long getDthrLongMovEquipProprio() {
        return dthrLongMovEquipProprio;
    }

    public void setDthrLongMovEquipProprio(Long dthrLongMovEquipProprio) {
        this.dthrLongMovEquipProprio = dthrLongMovEquipProprio;
    }

    public Long getNroMatricVigiaMovEquipProprio() {
        return nroMatricVigiaMovEquipProprio;
    }

    public void setNroMatricVigiaMovEquipProprio(Long nroMatricVigiaMovEquipProprio) {
        this.nroMatricVigiaMovEquipProprio = nroMatricVigiaMovEquipProprio;
    }

    public Long getNroMatricColabMovEquipProprio() {
        return nroMatricColabMovEquipProprio;
    }

    public void setNroMatricColabMovEquipProprio(Long nroMatricColabMovEquipProprio) {
        this.nroMatricColabMovEquipProprio = nroMatricColabMovEquipProprio;
    }

    public String getDestinoMovEquipProprio() {
        return destinoMovEquipProprio;
    }

    public void setDestinoMovEquipProprio(String destinoMovEquipProprio) {
        this.destinoMovEquipProprio = destinoMovEquipProprio;
    }

    public Long getNroNotaFiscalMovEquipProprio() {
        return nroNotaFiscalMovEquipProprio;
    }

    public void setNroNotaFiscalMovEquipProprio(Long nroNotaFiscalMovEquipProprio) {
        this.nroNotaFiscalMovEquipProprio = nroNotaFiscalMovEquipProprio;
    }

    public String getObservMovEquipProprio() {
        return observMovEquipProprio;
    }

    public void setObservMovEquipProprio(String observMovEquipProprio) {
        this.observMovEquipProprio = observMovEquipProprio;
    }

    public Long getStatusMovEquipProprio() {
        return statusMovEquipProprio;
    }

    public void setStatusMovEquipProprio(Long statusMovEquipProprio) {
        this.statusMovEquipProprio = statusMovEquipProprio;
    }
}
