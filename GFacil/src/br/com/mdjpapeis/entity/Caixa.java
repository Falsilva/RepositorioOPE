package br.com.mdjpapeis.entity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CAIXA", schema = "MDJPAPEIS")
@SequenceGenerator(name = "SEQ_CAIXA", sequenceName = "SEQ_CAIXA", allocationSize=1)
public class Caixa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_DE_SEQUENCIA")
	@Column(name = "CODIGO", nullable = false)
	private long codigo;
		
	@Column(name = "TOTAL_ENTRADA", nullable = true, scale = 2, precision = 10)
	private BigDecimal totalEntrada;
	
	@Column(name = "TOTAL_SAIDA", nullable = true, scale = 2, precision = 10)
	private BigDecimal totalSaida;
	
	@Column(name = "SALDO", nullable = true, scale = 2, precision = 10)
	private BigDecimal saldo;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="FK_CAIXA")
	List<Movimentacao> movimentacoes;
	
	// Construtor Padrão
	public Caixa(){}
	
	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getTotalEntrada() {
		return totalEntrada;
	}

	public void setTotalEntrada(BigDecimal totalEntrada) {
		this.totalEntrada = totalEntrada;
	}

	public BigDecimal getTotalSaida() {
		return totalSaida;
	}

	public void setTotalSaida(BigDecimal totalSaida) {
		this.totalSaida = totalSaida;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;		
	}
}
