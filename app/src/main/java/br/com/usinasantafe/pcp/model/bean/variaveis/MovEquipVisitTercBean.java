package br.com.usinasantafe.pcp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbmovequipvisittercvar")
public class MovEquipVisitTercBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idMovEquipVisitTerc;
    @DatabaseField
    private Long tipoMovEquipVisitTerc;
    @DatabaseField
    private Long idVisitTercMovEquipVisitTerc;
    @DatabaseField
    private Long tipoVisitTercMovEquipVisitTerc;
    @DatabaseField
    private String dthrMovEquipVisitTerc;
    @DatabaseField
    private Long dthrLongMovEquipVisitTerc;
    @DatabaseField
    private Long nroMatricVigiaMovEquipVisitTerc;
    @DatabaseField
    private Long idLocalMovEquipVisitTerc;
    @DatabaseField
    private String veiculoMovEquipVisitTerc;
    @DatabaseField
    private String placaMovEquipVisitTerc;
    @DatabaseField
    private String destinoMovEquipVisitTerc;
    @DatabaseField
    private String observacaoMovEquipVisitTerc;
    @DatabaseField
    private Long statusEntradaSaidaMovEquipVisitTerc; // 1 - Entrada; 2 - Saida
    @DatabaseField
    private Long statusMovEquipVisitTerc; // 1 - Aberto; 2 - Fechado; 3 - Enviado

    public MovEquipVisitTercBean() {
    }

    public Long getIdMovEquipVisitTerc() {
        return idMovEquipVisitTerc;
    }

    public void setIdMovEquipVisitTerc(Long idMovEquipVisitTerc) {
        this.idMovEquipVisitTerc = idMovEquipVisitTerc;
    }

    public Long getTipoMovEquipVisitTerc() {
        return tipoMovEquipVisitTerc;
    }

    public void setTipoMovEquipVisitTerc(Long tipoMovEquipVisitTerc) {
        this.tipoMovEquipVisitTerc = tipoMovEquipVisitTerc;
    }

    public Long getIdVisitTercMovEquipVisitTerc() {
        return idVisitTercMovEquipVisitTerc;
    }

    public void setIdVisitTercMovEquipVisitTerc(Long idVisitTercMovEquipVisitTerc) {
        this.idVisitTercMovEquipVisitTerc = idVisitTercMovEquipVisitTerc;
    }

    public Long getTipoVisitTercMovEquipVisitTerc() {
        return tipoVisitTercMovEquipVisitTerc;
    }

    public void setTipoVisitTercMovEquipVisitTerc(Long tipoVisitTercMovEquipVisitTerc) {
        this.tipoVisitTercMovEquipVisitTerc = tipoVisitTercMovEquipVisitTerc;
    }

    public String getDthrMovEquipVisitTerc() {
        return dthrMovEquipVisitTerc;
    }

    public void setDthrMovEquipVisitTerc(String dthrMovEquipVisitTerc) {
        this.dthrMovEquipVisitTerc = dthrMovEquipVisitTerc;
    }

    public Long getDthrLongMovEquipVisitTerc() {
        return dthrLongMovEquipVisitTerc;
    }

    public void setDthrLongMovEquipVisitTerc(Long dthrLongMovEquipVisitTerc) {
        this.dthrLongMovEquipVisitTerc = dthrLongMovEquipVisitTerc;
    }

    public Long getNroMatricVigiaMovEquipVisitTerc() {
        return nroMatricVigiaMovEquipVisitTerc;
    }

    public void setNroMatricVigiaMovEquipVisitTerc(Long nroMatricVigiaMovEquipVisitTerc) {
        this.nroMatricVigiaMovEquipVisitTerc = nroMatricVigiaMovEquipVisitTerc;
    }

    public Long getIdLocalMovEquipVisitTerc() {
        return idLocalMovEquipVisitTerc;
    }

    public void setIdLocalMovEquipVisitTerc(Long idLocalMovEquipVisitTerc) {
        this.idLocalMovEquipVisitTerc = idLocalMovEquipVisitTerc;
    }

    public String getVeiculoMovEquipVisitTerc() {
        return veiculoMovEquipVisitTerc;
    }

    public void setVeiculoMovEquipVisitTerc(String veiculoMovEquipVisitTerc) {
        this.veiculoMovEquipVisitTerc = veiculoMovEquipVisitTerc;
    }

    public String getPlacaMovEquipVisitTerc() {
        return placaMovEquipVisitTerc;
    }

    public void setPlacaMovEquipVisitTerc(String placaMovEquipVisitTerc) {
        this.placaMovEquipVisitTerc = placaMovEquipVisitTerc;
    }

    public String getDestinoMovEquipVisitTerc() {
        return destinoMovEquipVisitTerc;
    }

    public void setDestinoMovEquipVisitTerc(String destinoMovEquipVisitTerc) {
        this.destinoMovEquipVisitTerc = destinoMovEquipVisitTerc;
    }

    public String getObservacaoMovEquipVisitTerc() {
        return observacaoMovEquipVisitTerc;
    }

    public void setObservacaoMovEquipVisitTerc(String observacaoMovEquipVisitTerc) {
        this.observacaoMovEquipVisitTerc = observacaoMovEquipVisitTerc;
    }

    public Long getStatusEntradaSaidaMovEquipVisitTerc() {
        return statusEntradaSaidaMovEquipVisitTerc;
    }

    public void setStatusEntradaSaidaMovEquipVisitTerc(Long statusEntradaSaidaMovEquipVisitTerc) {
        this.statusEntradaSaidaMovEquipVisitTerc = statusEntradaSaidaMovEquipVisitTerc;
    }

    public Long getStatusMovEquipVisitTerc() {
        return statusMovEquipVisitTerc;
    }

    public void setStatusMovEquipVisitTerc(Long statusMovEquipVisitTerc) {
        this.statusMovEquipVisitTerc = statusMovEquipVisitTerc;
    }
}
