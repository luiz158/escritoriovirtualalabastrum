package escritoriovirtualalabastrum.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Index;
import org.hibernate.criterion.MatchMode;

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
	private String vNome;
	private String PosIngresso;
	private String Dt_Ingresso;
	private String Tel;
	private String eMail;
	private String Dt_Nasc;
	private String EV;

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

	public Usuario(Integer id) {

		this.setId(id);
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

	public String getDt_Ingresso() {
		return Dt_Ingresso;
	}

	public void setDt_Ingresso(String dt_Ingresso) {
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

}
