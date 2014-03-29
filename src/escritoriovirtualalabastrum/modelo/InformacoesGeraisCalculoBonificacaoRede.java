package escritoriovirtualalabastrum.modelo;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import escritoriovirtualalabastrum.hibernate.Entidade;

@Entity
public class InformacoesGeraisCalculoBonificacaoRede implements Entidade {

	@Id
	@GeneratedValue
	private Integer id;

	private Integer ano;
	private Integer mes;
	private GregorianCalendar dataHoraCalculo;
	private Integer tempoDeProcessamentoRelatorio;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public GregorianCalendar getDataHoraCalculo() {
		return dataHoraCalculo;
	}

	public void setDataHoraCalculo(GregorianCalendar dataHoraCalculo) {
		this.dataHoraCalculo = dataHoraCalculo;
	}

	public Integer getTempoDeProcessamentoRelatorio() {
		return tempoDeProcessamentoRelatorio;
	}

	public void setTempoDeProcessamentoRelatorio(Integer tempoDeProcessamentoRelatorio) {
		this.tempoDeProcessamentoRelatorio = tempoDeProcessamentoRelatorio;
	}
}
