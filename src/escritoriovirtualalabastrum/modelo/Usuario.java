package escritoriovirtualalabastrum.modelo;

import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;

import escritoriovirtualalabastrum.auxiliar.MalaDireta;
import escritoriovirtualalabastrum.auxiliar.PontuacaoAuxiliar;
import escritoriovirtualalabastrum.controller.PontuacaoController;
import escritoriovirtualalabastrum.hibernate.Entidade;
import escritoriovirtualalabastrum.hibernate.HibernateUtil;
import escritoriovirtualalabastrum.util.Util;

@Entity
public class Usuario implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	private InformacoesFixasUsuario informacoesFixasUsuario;

	private String CPF;
	private String PosAtual;
	private String PosAbrev;
	private String vNome;
	private String PosIngresso;
	private GregorianCalendar Dt_Ingresso;
	private String Tel;
	private String eMail;
	private String Dt_Nasc;
	private String EV;
	private String cadSexo;
	private String cadEstCivil;
	private String cadCEP;
	private String cadBairro;
	private String cadCidade;
	private String cadUF;
	private String cadCelular;
	private String cadEndereco;
	private String vNomeTitular2;
	private String Dt_NascTitular2;
	private String SexoTitular2;
	private String EstCivilTitular2;
	private String CPFTitular2;
	private String RGTitular2;
	private String EmissorTitular2;
	private String TelTitular2;
	private String CelTitular2;
	private String eMailTitular2;
	private String cadCCorrente;
	private String cadBanco;
	private String cadAgencia;
	private String cadTipoConta;
	private String DesquaLider;
	private String cadRG;
	private String cadOrgaoExpedidor;
	private String cadAtividade;
	private String fake;
	private String msg;
	private String cadCredito;
	private String Dt_Dem;
	private String Dt_S;
	private String Dt_M;
	private String Dt_GB;
	private String Dt_GP;
	private String Dt_GO;
	private String Dt_GE;
	private String Dt_LA;
	private String Dt_CR;
	private String Dt_DR;
	private String Dt_DD;
	private String Dt_DS;
	private String Dt_Pres;

	@Transient
	private PontuacaoAuxiliar pontuacaoAuxiliar;

	@Index(name = "index_id_Codigo")
	private Integer id_Codigo;

	@Index(name = "index_id_Patroc")
	private Integer id_Patroc;

	@Index(name = "index_id_Dem")
	private Integer id_Dem;

	@Index(name = "index_id_S")
	private Integer id_S;

	@Index(name = "index_id_M")
	private Integer id_M;

	@Index(name = "index_id_GB")
	private Integer id_GB;

	@Index(name = "index_id_GP")
	private Integer id_GP;

	@Index(name = "index_id_GO")
	private Integer id_GO;

	@Index(name = "index_id_GE")
	private Integer id_GE;

	@Index(name = "index_id_M1")
	private Integer id_M1;

	@Index(name = "index_id_M2")
	private Integer id_M2;

	@Index(name = "index_id_M3")
	private Integer id_M3;

	@Index(name = "index_id_M4")
	private Integer id_M4;

	@Index(name = "index_id_M5")
	private Integer id_M5;

	@Index(name = "index_id_M6")
	private Integer id_M6;

	@Index(name = "index_id_M7")
	private Integer id_M7;

	@Index(name = "index_id_M8")
	private Integer id_M8;

	@Index(name = "index_id_M9")
	private Integer id_M9;

	@Index(name = "index_id_M10")
	private Integer id_M10;

	@Index(name = "index_id_LA")
	private Integer id_LA;

	@Index(name = "index_id_LA1")
	private Integer id_LA1;

	@Index(name = "index_id_LA2")
	private Integer id_LA2;

	@Index(name = "index_id_CR")
	private Integer id_CR;

	@Index(name = "index_id_CR1")
	private Integer id_CR1;

	@Index(name = "index_id_CR2")
	private Integer id_CR2;

	@Index(name = "index_id_DR")
	private Integer id_DR;

	@Index(name = "index_id_DD")
	private Integer id_DD;

	@Index(name = "index_id_DS")
	private Integer id_DS;

	@Index(name = "index_id_Pres")
	private Integer id_Pres;

	public Usuario() {

	}

	public Usuario(Integer id_Codigo) {

		this.setId_Codigo(id_Codigo);
	}

	public InformacoesFixasUsuario obterInformacoesFixasUsuario() {

		if (Util.preenchido(informacoesFixasUsuario)) {

			return informacoesFixasUsuario;
		}

		InformacoesFixasUsuario informacoesFixasUsuario = new InformacoesFixasUsuario();
		informacoesFixasUsuario.setCodigoUsuario(this.id_Codigo);

		HibernateUtil hibernateUtil = new HibernateUtil();

		informacoesFixasUsuario = hibernateUtil.selecionar(informacoesFixasUsuario, MatchMode.EXACT);
		this.setInformacoesFixasUsuario(informacoesFixasUsuario);

		hibernateUtil.fecharSessao();

		return informacoesFixasUsuario;
	}

	public void calcularPontuacao() {

		calcularPontuacao(null, null);
	}

	public void calcularPontuacao(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		HibernateUtil hibernateUtil = new HibernateUtil();

		PontuacaoController pontuacaoController = new PontuacaoController(null, hibernateUtil, null, null);

		List<Criterion> restricoes = pontuacaoController.definirRestricoesDatas(dataInicial, dataFinal);

		PontuacaoAuxiliar pontuacaoAuxiliar = pontuacaoController.calcularPontuacoes(restricoes, new MalaDireta(this, 0));
		pontuacaoAuxiliar.calcularTotal();
		pontuacaoAuxiliar.verificarAtividade();
		this.setPontuacaoAuxiliar(pontuacaoAuxiliar);

		hibernateUtil.fecharSessao();
	}

	public boolean isAtivo() {

		this.calcularPontuacao();

		return this.getPontuacaoAuxiliar().isAtivo();
	}

	public boolean isAtivo(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {

		this.calcularPontuacao(dataInicial, dataFinal);

		return this.getPontuacaoAuxiliar().isAtivo();
	}

	public String getAniversario() {

		String dia = this.Dt_Nasc.split("/")[0];
		String mes = this.Dt_Nasc.split("/")[1];

		return dia + "/" + mes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InformacoesFixasUsuario getInformacoesFixasUsuario() {
		return informacoesFixasUsuario;
	}

	public void setInformacoesFixasUsuario(InformacoesFixasUsuario informacoesFixasUsuario) {
		this.informacoesFixasUsuario = informacoesFixasUsuario;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getPosAtual() {
		return PosAtual;
	}

	public void setPosAtual(String posAtual) {
		PosAtual = posAtual;
	}

	public String getPosAbrev() {
		return PosAbrev;
	}

	public void setPosAbrev(String posAbrev) {
		PosAbrev = posAbrev;
	}

	public String getvNome() {
		return vNome;
	}

	public void setvNome(String vNome) {
		this.vNome = vNome;
	}

	public String getPosIngresso() {
		return PosIngresso;
	}

	public void setPosIngresso(String posIngresso) {
		PosIngresso = posIngresso;
	}

	public GregorianCalendar getDt_Ingresso() {
		return Dt_Ingresso;
	}

	public void setDt_Ingresso(GregorianCalendar dt_Ingresso) {
		Dt_Ingresso = dt_Ingresso;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getDt_Nasc() {
		return Dt_Nasc;
	}

	public void setDt_Nasc(String dt_Nasc) {
		Dt_Nasc = dt_Nasc;
	}

	public String getEV() {
		return EV;
	}

	public void setEV(String eV) {
		EV = eV;
	}

	public String getCadSexo() {
		return cadSexo;
	}

	public void setCadSexo(String cadSexo) {
		this.cadSexo = cadSexo;
	}

	public String getCadEstCivil() {
		return cadEstCivil;
	}

	public void setCadEstCivil(String cadEstCivil) {
		this.cadEstCivil = cadEstCivil;
	}

	public String getCadCEP() {
		return cadCEP;
	}

	public void setCadCEP(String cadCEP) {
		this.cadCEP = cadCEP;
	}

	public String getCadEndereco() {
		return cadEndereco;
	}

	public void setCadEndereco(String cadEndereco) {
		this.cadEndereco = cadEndereco;
	}

	public String getCadBairro() {
		return cadBairro;
	}

	public void setCadBairro(String cadBairro) {
		this.cadBairro = cadBairro;
	}

	public String getCadCidade() {
		return cadCidade;
	}

	public void setCadCidade(String cadCidade) {
		this.cadCidade = cadCidade;
	}

	public String getCadUF() {
		return cadUF;
	}

	public void setCadUF(String cadUF) {
		this.cadUF = cadUF;
	}

	public String getCadCelular() {
		return cadCelular;
	}

	public void setCadCelular(String cadCelular) {
		this.cadCelular = cadCelular;
	}

	public String getvNomeTitular2() {
		return vNomeTitular2;
	}

	public void setvNomeTitular2(String vNomeTitular2) {
		this.vNomeTitular2 = vNomeTitular2;
	}

	public String getDt_NascTitular2() {
		return Dt_NascTitular2;
	}

	public void setDt_NascTitular2(String dt_NascTitular2) {
		Dt_NascTitular2 = dt_NascTitular2;
	}

	public String getSexoTitular2() {
		return SexoTitular2;
	}

	public void setSexoTitular2(String sexoTitular2) {
		SexoTitular2 = sexoTitular2;
	}

	public String getEstCivilTitular2() {
		return EstCivilTitular2;
	}

	public void setEstCivilTitular2(String estCivilTitular2) {
		EstCivilTitular2 = estCivilTitular2;
	}

	public String getCPFTitular2() {
		return CPFTitular2;
	}

	public void setCPFTitular2(String cPFTitular2) {
		CPFTitular2 = cPFTitular2;
	}

	public String getRGTitular2() {
		return RGTitular2;
	}

	public void setRGTitular2(String rGTitular2) {
		RGTitular2 = rGTitular2;
	}

	public String getEmissorTitular2() {
		return EmissorTitular2;
	}

	public void setEmissorTitular2(String emissorTitular2) {
		EmissorTitular2 = emissorTitular2;
	}

	public String getTelTitular2() {
		return TelTitular2;
	}

	public void setTelTitular2(String telTitular2) {
		TelTitular2 = telTitular2;
	}

	public String getCelTitular2() {
		return CelTitular2;
	}

	public void setCelTitular2(String celTitular2) {
		CelTitular2 = celTitular2;
	}

	public String geteMailTitular2() {
		return eMailTitular2;
	}

	public void seteMailTitular2(String eMailTitular2) {
		this.eMailTitular2 = eMailTitular2;
	}

	public String getCadCCorrente() {
		return cadCCorrente;
	}

	public void setCadCCorrente(String cadCCorrente) {
		this.cadCCorrente = cadCCorrente;
	}

	public String getCadBanco() {
		return cadBanco;
	}

	public void setCadBanco(String cadBanco) {
		this.cadBanco = cadBanco;
	}

	public String getCadAgencia() {
		return cadAgencia;
	}

	public void setCadAgencia(String cadAgencia) {
		this.cadAgencia = cadAgencia;
	}

	public String getCadTipoConta() {
		return cadTipoConta;
	}

	public void setCadTipoConta(String cadTipoConta) {
		this.cadTipoConta = cadTipoConta;
	}

	public String getDesquaLider() {
		return DesquaLider;
	}

	public void setDesquaLider(String desquaLider) {
		DesquaLider = desquaLider;
	}

	public String getCadRG() {
		return cadRG;
	}

	public void setCadRG(String cadRG) {
		this.cadRG = cadRG;
	}

	public String getCadOrgaoExpedidor() {
		return cadOrgaoExpedidor;
	}

	public void setCadOrgaoExpedidor(String cadOrgaoExpedidor) {
		this.cadOrgaoExpedidor = cadOrgaoExpedidor;
	}

	public String getCadAtividade() {
		return cadAtividade;
	}

	public void setCadAtividade(String cadAtividade) {
		this.cadAtividade = cadAtividade;
	}

	public String getCadCredito() {
		return cadCredito;
	}

	public void setCadCredito(String cadCredito) {
		this.cadCredito = cadCredito;
	}

	public PontuacaoAuxiliar getPontuacaoAuxiliar() {
		return pontuacaoAuxiliar;
	}

	public void setPontuacaoAuxiliar(PontuacaoAuxiliar pontuacaoAuxiliar) {
		this.pontuacaoAuxiliar = pontuacaoAuxiliar;
	}

	public Integer getId_Codigo() {
		return id_Codigo;
	}

	public void setId_Codigo(Integer id_Codigo) {
		this.id_Codigo = id_Codigo;
	}

	public Integer getId_Patroc() {
		return id_Patroc;
	}

	public void setId_Patroc(Integer id_Patroc) {
		this.id_Patroc = id_Patroc;
	}

	public Integer getId_Dem() {
		return id_Dem;
	}

	public void setId_Dem(Integer id_Dem) {
		this.id_Dem = id_Dem;
	}

	public Integer getId_S() {
		return id_S;
	}

	public void setId_S(Integer id_S) {
		this.id_S = id_S;
	}

	public Integer getId_M() {
		return id_M;
	}

	public void setId_M(Integer id_M) {
		this.id_M = id_M;
	}

	public Integer getId_GB() {
		return id_GB;
	}

	public void setId_GB(Integer id_GB) {
		this.id_GB = id_GB;
	}

	public Integer getId_GP() {
		return id_GP;
	}

	public void setId_GP(Integer id_GP) {
		this.id_GP = id_GP;
	}

	public Integer getId_GO() {
		return id_GO;
	}

	public void setId_GO(Integer id_GO) {
		this.id_GO = id_GO;
	}

	public Integer getId_GE() {
		return id_GE;
	}

	public void setId_GE(Integer id_GE) {
		this.id_GE = id_GE;
	}

	public Integer getId_M1() {
		return id_M1;
	}

	public void setId_M1(Integer id_M1) {
		this.id_M1 = id_M1;
	}

	public Integer getId_M2() {
		return id_M2;
	}

	public void setId_M2(Integer id_M2) {
		this.id_M2 = id_M2;
	}

	public Integer getId_M3() {
		return id_M3;
	}

	public void setId_M3(Integer id_M3) {
		this.id_M3 = id_M3;
	}

	public Integer getId_M4() {
		return id_M4;
	}

	public void setId_M4(Integer id_M4) {
		this.id_M4 = id_M4;
	}

	public Integer getId_M5() {
		return id_M5;
	}

	public void setId_M5(Integer id_M5) {
		this.id_M5 = id_M5;
	}

	public Integer getId_LA() {
		return id_LA;
	}

	public void setId_LA(Integer id_LA) {
		this.id_LA = id_LA;
	}

	public Integer getId_LA1() {
		return id_LA1;
	}

	public void setId_LA1(Integer id_LA1) {
		this.id_LA1 = id_LA1;
	}

	public Integer getId_LA2() {
		return id_LA2;
	}

	public void setId_LA2(Integer id_LA2) {
		this.id_LA2 = id_LA2;
	}

	public Integer getId_CR() {
		return id_CR;
	}

	public void setId_CR(Integer id_CR) {
		this.id_CR = id_CR;
	}

	public Integer getId_CR1() {
		return id_CR1;
	}

	public void setId_CR1(Integer id_CR1) {
		this.id_CR1 = id_CR1;
	}

	public Integer getId_CR2() {
		return id_CR2;
	}

	public void setId_CR2(Integer id_CR2) {
		this.id_CR2 = id_CR2;
	}

	public Integer getId_DR() {
		return id_DR;
	}

	public void setId_DR(Integer id_DR) {
		this.id_DR = id_DR;
	}

	public Integer getId_DD() {
		return id_DD;
	}

	public void setId_DD(Integer id_DD) {
		this.id_DD = id_DD;
	}

	public Integer getId_DS() {
		return id_DS;
	}

	public void setId_DS(Integer id_DS) {
		this.id_DS = id_DS;
	}

	public Integer getId_Pres() {
		return id_Pres;
	}

	public void setId_Pres(Integer id_Pres) {
		this.id_Pres = id_Pres;
	}

	public String getDt_Dem() {
		return Dt_Dem;
	}

	public void setDt_Dem(String dt_Dem) {
		Dt_Dem = dt_Dem;
	}

	public String getDt_S() {
		return Dt_S;
	}

	public void setDt_S(String dt_S) {
		Dt_S = dt_S;
	}

	public String getDt_M() {
		return Dt_M;
	}

	public void setDt_M(String dt_M) {
		Dt_M = dt_M;
	}

	public String getDt_GB() {
		return Dt_GB;
	}

	public void setDt_GB(String dt_GB) {
		Dt_GB = dt_GB;
	}

	public String getDt_GP() {
		return Dt_GP;
	}

	public void setDt_GP(String dt_GP) {
		Dt_GP = dt_GP;
	}

	public String getDt_GO() {
		return Dt_GO;
	}

	public void setDt_GO(String dt_GO) {
		Dt_GO = dt_GO;
	}

	public String getDt_GE() {
		return Dt_GE;
	}

	public void setDt_GE(String dt_GE) {
		Dt_GE = dt_GE;
	}

	public String getDt_LA() {
		return Dt_LA;
	}

	public void setDt_LA(String dt_LA) {
		Dt_LA = dt_LA;
	}

	public String getDt_CR() {
		return Dt_CR;
	}

	public void setDt_CR(String dt_CR) {
		Dt_CR = dt_CR;
	}

	public String getDt_DR() {
		return Dt_DR;
	}

	public void setDt_DR(String dt_DR) {
		Dt_DR = dt_DR;
	}

	public String getDt_DD() {
		return Dt_DD;
	}

	public void setDt_DD(String dt_DD) {
		Dt_DD = dt_DD;
	}

	public String getDt_DS() {
		return Dt_DS;
	}

	public void setDt_DS(String dt_DS) {
		Dt_DS = dt_DS;
	}

	public String getDt_Pres() {
		return Dt_Pres;
	}

	public void setDt_Pres(String dt_Pres) {
		Dt_Pres = dt_Pres;
	}

	public Integer getId_M6() {
		return id_M6;
	}

	public void setId_M6(Integer id_M6) {
		this.id_M6 = id_M6;
	}

	public Integer getId_M7() {
		return id_M7;
	}

	public void setId_M7(Integer id_M7) {
		this.id_M7 = id_M7;
	}

	public Integer getId_M8() {
		return id_M8;
	}

	public void setId_M8(Integer id_M8) {
		this.id_M8 = id_M8;
	}

	public Integer getId_M9() {
		return id_M9;
	}

	public void setId_M9(Integer id_M9) {
		this.id_M9 = id_M9;
	}

	public Integer getId_M10() {
		return id_M10;
	}

	public void setId_M10(Integer id_M10) {
		this.id_M10 = id_M10;
	}

	public String getFake() {
		return fake;
	}

	public void setFake(String fake) {
		this.fake = fake;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
