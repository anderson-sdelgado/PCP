package br.com.usinasantafe.pcp.model.bean.variaveis;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import br.com.usinasantafe.pcp.model.pst.Entidade;

@DatabaseTable(tableName="tbconfigvar")
public class ConfigBean extends Entidade {
	
	private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId=true)
    private Long idConfig;
	@DatabaseField
	private Long numLinhaConfig;
    @DatabaseField
	private String senhaConfig;
	@DatabaseField
	private Long posicaoTela;
	// 1 - Configuração Inicial

	public ConfigBean() {
	}

	public Long getIdConfig() {
		return idConfig;
	}

	public void setIdConfig(Long idConfig) {
		this.idConfig = idConfig;
	}

	public Long getNumLinhaConfig() {
		return numLinhaConfig;
	}

	public void setNumLinhaConfig(Long numLinhaConfig) {
		this.numLinhaConfig = numLinhaConfig;
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
}