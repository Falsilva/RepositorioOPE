package br.com.mdjpapeis.entity;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MOVIMENTACAO", schema = "MDJPAPEIS")
@SequenceGenerator(name = "SEQ_MOVIMENTACAO", sequenceName = "SEQ_MOVIMENTACAO", allocationSize=1)
public class Movimentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOVIMENTACAO")
	@Column(name = "N_LANCAMENTO", nullable = false)
	private long nLancamento;
	
	// Quando o Status for PAGO
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA", nullable = true)
	private Calendar data;
	
	@Column(name = "VALOR_LANCAMENTO", nullable = true, scale = 2, precision = 10)
	private BigDecimal valorLancamento;	
	
	// TIPO DE LANÇAMENTO - ENTRADA OU SAÍDA DE DINHEIRO
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipoLancamento;
		
	public enum TipoLancamento{
		ENTRADA,
		SAIDA	
	}
	
	// UNIDIRECIONAL
	@OneToOne
	@JoinColumn(name = "FK_PEDIDO_COMPRA", nullable=true)
	private PedidoCompra pedidoCompra;
	
	// UNIDIRECIONAL
	@OneToOne
	@JoinColumn(name = "FK_PEDIDO_VENDA", nullable=true)
	private PedidoVenda pedidoVenda;
		
	// Construtor Padrão
	public Movimentacao(){}
	
	public long getnLancamento() {
		return nLancamento;
	}

	public void setnLancamento(long nLancamento) {
		this.nLancamento = nLancamento;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public BigDecimal getValorLancamento() {
		return valorLancamento;
	}

	public void setValorLancamento(BigDecimal valorLancamento) {
		this.valorLancamento = valorLancamento;
	}

	public TipoLancamento getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public PedidoCompra getPedidoCompra() {
		return pedidoCompra;
	}

	public void setPedidoCompra(PedidoCompra pedidoCompra) {
		this.pedidoCompra = pedidoCompra;
		this.data = pedidoCompra.getDataPagamento();
		this.valorLancamento = pedidoCompra.getValorTotal();
		this.tipoLancamento = TipoLancamento.SAIDA;
	}

	public PedidoVenda getPedidoVenda() {
		return pedidoVenda;
	}

	public void setPedidoVenda(PedidoVenda pedidoVenda) {
		this.pedidoVenda = pedidoVenda;
		this.data = pedidoVenda.getDataPagamento();
		this.valorLancamento = pedidoVenda.getValorTotal();
		this.tipoLancamento = TipoLancamento.ENTRADA;
	}
}
