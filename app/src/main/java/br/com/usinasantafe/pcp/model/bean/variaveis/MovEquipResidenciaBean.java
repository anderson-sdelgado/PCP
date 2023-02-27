package br.com.usinasantafe.pcp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbmovequipresidenciavar")
public class MovEquipResidenciaBean extends Entidade {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idMovEquipResidencia;
    @DatabaseField
    private Long tipoMovEquipResidencia;
    @DatabaseField
    private String dthrMovEquipResidencia;
    @DatabaseField
    private Long dthrLongMovEquipResidencia;
    @DatabaseField
    private Long nroMatricVigiaMovEquipResidencia;
    @DatabaseField
    private Long idLocalMovEquipResidencia;
    @DatabaseField
    private String nomeVisitanteMovEquipResidencia;
    @DatabaseField
    private String veiculoMovEquipResidencia;
    @DatabaseField
    private String placaMovEquipResidencia;
    @DatabaseField
    private String observacaoMovEquipResidencia;
    @DatabaseField
    private Long statusEntradaSaidaMovEquipResidencia; // 1 - Apenas Entrada; 2 - Entrada e Saída
    @DatabaseField
    private Long statusMovEquipResidencia; // 1 - Aberto; 2 - Fechado; 3 - Enviado

    public MovEquipResidenciaBean() {
    }

    public Long getIdMovEquipResidencia() {
        return idMovEquipResidencia;
    }

    public void setIdMovEquipResidencia(Long idMovEquipResidencia) {
        this.idMovEquipResidencia = idMovEquipResidencia;
    }

    public Long getTipoMovEquipResidencia() {
        return tipoMovEquipResidencia;
    }

    public void setTipoMovEquipResidencia(Long tipoMovEquipResidencia) {
        this.tipoMovEquipResidencia = tipoMovEquipResidencia;
    }

    public String getDthrMovEquipResidencia() {
        return dthrMovEquipResidencia;
    }

    public void setDthrMovEquipResidencia(String dthrMovEquipResidencia) {
        this.dthrMovEquipResidencia = dthrMovEquipResidencia;
    }

    public Long getDthrLongMovEquipResidencia() {
        return dthrLongMovEquipResidencia;
    }

    public void setDthrLongMovEquipResidencia(Long dthrLongMovEquipResidencia) {
        this.dthrLongMovEquipResidencia = dthrLongMovEquipResidencia;
    }

    public Long getNroMatricVigiaMovEquipResidencia() {
        return nroMatricVigiaMovEquipResidencia;
    }

    public void setNroMatricVigiaMovEquipResidencia(Long nroMatricVigiaMovEquipResidencia) {
        this.nroMatricVigiaMovEquipResidencia = nroMatricVigiaMovEquipResidencia;
    }

    public Long getIdLocalMovEquipResidencia() {
        return idLocalMovEquipResidencia;
    }

    public void setIdLocalMovEquipResidencia(Long idLocalMovEquipResidencia) {
        this.idLocalMovEquipResidencia = idLocalMovEquipResidencia;
    }

    public String getNomeVisitanteMovEquipResidencia() {
        return nomeVisitanteMovEquipResidencia;
    }

    public void setNomeVisitanteMovEquipResidencia(String nomeVisitanteMovEquipResidencia) {
        this.nomeVisitanteMovEquipResidencia = nomeVisitanteMovEquipResidencia;
    }

    public String getVeiculoMovEquipResidencia() {
        return veiculoMovEquipResidencia;
    }

    public void setVeiculoMovEquipResidencia(String veiculoMovEquipResidencia) {
        this.veiculoMovEquipResidencia = veiculoMovEquipResidencia;
    }

    public String getPlacaMovEquipResidencia() {
        return placaMovEquipResidencia;
    }

    public void setPlacaMovEquipResidencia(String placaMovEquipResidencia) {
        this.placaMovEquipResidencia = placaMovEquipResidencia;
    }

    public String getObservacaoMovEquipResidencia() {
        return observacaoMovEquipResidencia;
    }

    public void setObservacaoMovEquipResidencia(String observacaoMovEquipResidencia) {
        this.observacaoMovEquipResidencia = observacaoMovEquipResidencia;
    }

    public Long getStatusEntradaSaidaMovEquipResidencia() {
        return statusEntradaSaidaMovEquipResidencia;
    }

    public void setStatusEntradaSaidaMovEquipResidencia(Long statusEntradaSaidaMovEquipResidencia) {
        this.statusEntradaSaidaMovEquipResidencia = statusEntradaSaidaMovEquipResidencia;
    }

    public Long getStatusMovEquipResidencia() {
        return statusMovEquipResidencia;
    }

    public void setStatusMovEquipResidencia(Long statusMovEquipResidencia) {
        this.statusMovEquipResidencia = statusMovEquipResidencia;
    }
}
