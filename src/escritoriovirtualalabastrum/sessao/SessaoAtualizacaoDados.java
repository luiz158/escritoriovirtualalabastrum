package escritoriovirtualalabastrum.sessao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class SessaoAtualizacaoDados {

	private String dataNascimento;
	private String sexo;
	private String estadoCivil;
	private String CEP;
	private String endereco;
	private String numeroEndereco;
	private String complementoEndereco;
	private String bairro;
	private String cidade;
	private String estado;
	private String telefoneResidencial;
	private String telefoneComercial;
	private String telefoneResidencialDDD;
	private String telefoneComercialDDD;
	private String telefoneCelular1;
	private String telefoneCelular1DDD;
	private String telefoneCelular2;
	private String telefoneCelular2DDD;
	private String email;
	private String CEPEntregaMercadoria;
	private String enderecoEntregaMercadoria;
	private String numeroEnderecoEntregaMercadoria;
	private String complementoEnderecoEntregaMercadoria;
	private String bairroEntregaMercadoria;
	private String cidadeEntregaMercadoria;
	private String estadoEntregaMercadoria;
	private String nomeSegundoTitular;
	private String dataNascimentoSegundoTitular;
	private String sexoSegundoTitular;
	private String estadoCivilSegundoTitular;
	private String CPFSegundoTitular;
	private String RGSegundoTitular;
	private String emissorSegundoTitular;
	private String emailSegundoTitular;
	private String titularConta;
	private String banco;
	private String tipoConta;
	private String numeroAgencia;
	private String numeroConta;

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public String getTelefoneResidencialDDD() {
		return telefoneResidencialDDD;
	}

	public void setTelefoneResidencialDDD(String telefoneResidencialDDD) {
		this.telefoneResidencialDDD = telefoneResidencialDDD;
	}

	public String getTelefoneComercialDDD() {
		return telefoneComercialDDD;
	}

	public void setTelefoneComercialDDD(String telefoneComercialDDD) {
		this.telefoneComercialDDD = telefoneComercialDDD;
	}

	public String getTelefoneCelular1() {
		return telefoneCelular1;
	}

	public void setTelefoneCelular1(String telefoneCelular1) {
		this.telefoneCelular1 = telefoneCelular1;
	}

	public String getTelefoneCelular1DDD() {
		return telefoneCelular1DDD;
	}

	public void setTelefoneCelular1DDD(String telefoneCelular1DDD) {
		this.telefoneCelular1DDD = telefoneCelular1DDD;
	}

	public String getTelefoneCelular2() {
		return telefoneCelular2;
	}

	public void setTelefoneCelular2(String telefoneCelular2) {
		this.telefoneCelular2 = telefoneCelular2;
	}

	public String getTelefoneCelular2DDD() {
		return telefoneCelular2DDD;
	}

	public void setTelefoneCelular2DDD(String telefoneCelular2DDD) {
		this.telefoneCelular2DDD = telefoneCelular2DDD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCEPEntregaMercadoria() {
		return CEPEntregaMercadoria;
	}

	public void setCEPEntregaMercadoria(String cEPEntregaMercadoria) {
		CEPEntregaMercadoria = cEPEntregaMercadoria;
	}

	public String getEnderecoEntregaMercadoria() {
		return enderecoEntregaMercadoria;
	}

	public void setEnderecoEntregaMercadoria(String enderecoEntregaMercadoria) {
		this.enderecoEntregaMercadoria = enderecoEntregaMercadoria;
	}

	public String getNumeroEnderecoEntregaMercadoria() {
		return numeroEnderecoEntregaMercadoria;
	}

	public void setNumeroEnderecoEntregaMercadoria(String numeroEnderecoEntregaMercadoria) {
		this.numeroEnderecoEntregaMercadoria = numeroEnderecoEntregaMercadoria;
	}

	public String getComplementoEnderecoEntregaMercadoria() {
		return complementoEnderecoEntregaMercadoria;
	}

	public void setComplementoEnderecoEntregaMercadoria(String complementoEnderecoEntregaMercadoria) {
		this.complementoEnderecoEntregaMercadoria = complementoEnderecoEntregaMercadoria;
	}

	public String getBairroEntregaMercadoria() {
		return bairroEntregaMercadoria;
	}

	public void setBairroEntregaMercadoria(String bairroEntregaMercadoria) {
		this.bairroEntregaMercadoria = bairroEntregaMercadoria;
	}

	public String getCidadeEntregaMercadoria() {
		return cidadeEntregaMercadoria;
	}

	public void setCidadeEntregaMercadoria(String cidadeEntregaMercadoria) {
		this.cidadeEntregaMercadoria = cidadeEntregaMercadoria;
	}

	public String getEstadoEntregaMercadoria() {
		return estadoEntregaMercadoria;
	}

	public void setEstadoEntregaMercadoria(String estadoEntregaMercadoria) {
		this.estadoEntregaMercadoria = estadoEntregaMercadoria;
	}

	public String getNomeSegundoTitular() {
		return nomeSegundoTitular;
	}

	public void setNomeSegundoTitular(String nomeSegundoTitular) {
		this.nomeSegundoTitular = nomeSegundoTitular;
	}

	public String getDataNascimentoSegundoTitular() {
		return dataNascimentoSegundoTitular;
	}

	public void setDataNascimentoSegundoTitular(String dataNascimentoSegundoTitular) {
		this.dataNascimentoSegundoTitular = dataNascimentoSegundoTitular;
	}

	public String getSexoSegundoTitular() {
		return sexoSegundoTitular;
	}

	public void setSexoSegundoTitular(String sexoSegundoTitular) {
		this.sexoSegundoTitular = sexoSegundoTitular;
	}

	public String getEstadoCivilSegundoTitular() {
		return estadoCivilSegundoTitular;
	}

	public void setEstadoCivilSegundoTitular(String estadoCivilSegundoTitular) {
		this.estadoCivilSegundoTitular = estadoCivilSegundoTitular;
	}

	public String getCPFSegundoTitular() {
		return CPFSegundoTitular;
	}

	public void setCPFSegundoTitular(String cPFSegundoTitular) {
		CPFSegundoTitular = cPFSegundoTitular;
	}

	public String getRGSegundoTitular() {
		return RGSegundoTitular;
	}

	public void setRGSegundoTitular(String rGSegundoTitular) {
		RGSegundoTitular = rGSegundoTitular;
	}

	public String getEmissorSegundoTitular() {
		return emissorSegundoTitular;
	}

	public void setEmissorSegundoTitular(String emissorSegundoTitular) {
		this.emissorSegundoTitular = emissorSegundoTitular;
	}

	public String getEmailSegundoTitular() {
		return emailSegundoTitular;
	}

	public void setEmailSegundoTitular(String emailSegundoTitular) {
		this.emailSegundoTitular = emailSegundoTitular;
	}

	public String getTitularConta() {
		return titularConta;
	}

	public void setTitularConta(String titularConta) {
		this.titularConta = titularConta;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

}
