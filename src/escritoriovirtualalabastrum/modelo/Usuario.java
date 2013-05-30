package escritoriovirtualalabastrum.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class Usuario implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private String id_Codigo;
	private String PosAtual;
	private String vNome;
	private String PosIngresso;
	private String Dt_Ingresso;
	private String Tel;
	private String eMail;
	private String Dt_Nasc;
	private String id_Patroc;
	private String id_Dem;
	private String id_S;
	private String id_M;
	private String id_GB;
	private String id_GP;
	private String id_GO;
	private String id_GE;
	private String id_M1;
	private String id_M2;
	private String id_M3;
	private String id_M4;
	private String id_M5;
	private String id_LA;
	private String id_LA1;
	private String id_LA2;
	private String id_CR;
	private String id_CR1;
	private String id_CR2;
	private String id_DR;
	private String id_DD;
	private String id_DS;
	private String id_Pres;
	private String EV;

	public Usuario() {

	}

	public Usuario(Integer id) {

		this.setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getId_Patroc() {
		return id_Patroc;
	}

	public void setId_Patroc(String id_Patroc) {
		this.id_Patroc = id_Patroc;
	}

	public String getId_Dem() {
		return id_Dem;
	}

	public void setId_Dem(String id_Dem) {
		this.id_Dem = id_Dem;
	}

	public String getId_S() {
		return id_S;
	}

	public void setId_S(String id_S) {
		this.id_S = id_S;
	}

	public String getId_M() {
		return id_M;
	}

	public void setId_M(String id_M) {
		this.id_M = id_M;
	}

	public String getId_GB() {
		return id_GB;
	}

	public void setId_GB(String id_GB) {
		this.id_GB = id_GB;
	}

	public String getId_GP() {
		return id_GP;
	}

	public void setId_GP(String id_GP) {
		this.id_GP = id_GP;
	}

	public String getId_GO() {
		return id_GO;
	}

	public void setId_GO(String id_GO) {
		this.id_GO = id_GO;
	}

	public String getId_GE() {
		return id_GE;
	}

	public void setId_GE(String id_GE) {
		this.id_GE = id_GE;
	}

	public String getId_M1() {
		return id_M1;
	}

	public void setId_M1(String id_M1) {
		this.id_M1 = id_M1;
	}

	public String getId_M2() {
		return id_M2;
	}

	public void setId_M2(String id_M2) {
		this.id_M2 = id_M2;
	}

	public String getId_M3() {
		return id_M3;
	}

	public void setId_M3(String id_M3) {
		this.id_M3 = id_M3;
	}

	public String getId_M4() {
		return id_M4;
	}

	public void setId_M4(String id_M4) {
		this.id_M4 = id_M4;
	}

	public String getId_M5() {
		return id_M5;
	}

	public void setId_M5(String id_M5) {
		this.id_M5 = id_M5;
	}

	public String getId_LA() {
		return id_LA;
	}

	public void setId_LA(String id_LA) {
		this.id_LA = id_LA;
	}

	public String getId_LA1() {
		return id_LA1;
	}

	public void setId_LA1(String id_LA1) {
		this.id_LA1 = id_LA1;
	}

	public String getId_LA2() {
		return id_LA2;
	}

	public void setId_LA2(String id_LA2) {
		this.id_LA2 = id_LA2;
	}

	public String getId_CR() {
		return id_CR;
	}

	public void setId_CR(String id_CR) {
		this.id_CR = id_CR;
	}

	public String getId_CR1() {
		return id_CR1;
	}

	public void setId_CR1(String id_CR1) {
		this.id_CR1 = id_CR1;
	}

	public String getId_CR2() {
		return id_CR2;
	}

	public void setId_CR2(String id_CR2) {
		this.id_CR2 = id_CR2;
	}

	public String getId_DR() {
		return id_DR;
	}

	public void setId_DR(String id_DR) {
		this.id_DR = id_DR;
	}

	public String getId_DD() {
		return id_DD;
	}

	public void setId_DD(String id_DD) {
		this.id_DD = id_DD;
	}

	public String getId_DS() {
		return id_DS;
	}

	public void setId_DS(String id_DS) {
		this.id_DS = id_DS;
	}

	public String getId_Pres() {
		return id_Pres;
	}

	public void setId_Pres(String id_Pres) {
		this.id_Pres = id_Pres;
	}

	public String getEV() {
		return EV;
	}

	public void setEV(String eV) {
		EV = eV;
	}

	public String getId_Codigo() {
		return id_Codigo;
	}

	public void setId_Codigo(String id_Codigo) {
		this.id_Codigo = id_Codigo;
	}

}
