package br.com.usinasantafe.pcp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbconfigvar")
public class ConfigBean extends Entidade {
	
	private static final long serialVersionUID = 1L;

	@DatabaseField(id=true)
	private Long nroLinhaConfig;
    @DatabaseField
	private String senhaConfig;
	@DatabaseField
	private Long matricVigiaConfig;
	@DatabaseField
	private Long idLocalConfig;
	@DatabaseField
	private Long posicaoListaMov;
	@DatabaseField
	private Long tipoMov;
	// 1 - Veículo Próprio
	// 2 - Veículo Visitante/Terceiro
	// 3 - Veículo Residência
	@DatabaseField
	private Long posicaoTela;
	// 1 - Configuração Inicial
	// 2 - Log Inicial
	// 3 - Vigia
	// 4 - Fluxo Normal
	// 5 - Veiculo Seg
	// 6 - Passageiro
	// 7 - Editar Dados
	// 8 - Editar Passageiros
	// 9 - Editar Veiculo Seg

	public ConfigBean() {
	}

	public Long getNroLinhaConfig() {
		return nroLinhaConfig;
	}

	public void setNroLinhaConfig(Long nroLinhaConfig) {
		this.nroLinhaConfig = nroLinhaConfig;
	}

	public String getSenhaConfig() {
		return senhaConfig;
	}

	public void setSenhaConfig(String senhaConfig) {
		this.senhaConfig = senhaConfig;
	}

	public Long getPosicaoTela() {
		return posicaoTela;
	}

	public void setPosicaoTela(Long posicaoTela) {
		this.posicaoTela = posicaoTela;
	}

	public Long getMatricVigiaConfig() {
		return matricVigiaConfig;
	}

	public void setMatricVigiaConfig(Long matricVigiaConfig) {
		this.matricVigiaConfig = matricVigiaConfig;
	}

	public Long getIdLocalConfig() {
		return idLocalConfig;
	}

	public void setIdLocalConfig(Long idLocalConfig) {
		this.idLocalConfig = idLocalConfig;
	}

	public Long getPosicaoListaMov() {
		return posicaoListaMov;
	}

	public void setPosicaoListaMov(Long posicaoListaMov) {
		this.posicaoListaMov = posicaoListaMov;
	}

	public Long getTipoMov() {
		return tipoMov;
	}

	public void setTipoMov(Long tipoMov) {
		this.tipoMov = tipoMov;
	}

}