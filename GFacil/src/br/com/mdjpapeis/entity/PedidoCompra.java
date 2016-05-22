package br.com.mdjpapeis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PEDIDO_COMPRA", schema = "MDJPAPEIS")
@SequenceGenerator(name = "SEQ_PEDIDO", sequenceName = "SEQ_PEDIDO", allocationSize=1)
public class PedidoCompra implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDO")
	@Column(name = "N_PEDIDO", nullable = false)
	private long nPedido;	
	
	// Quando o Status for PENDENTE
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_ABERTURA", nullable = false)
	private Calendar dataAbertura;
	
	// Quando o Status for PAGO
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_PAGAMENTO", nullable = true)
	private Calendar dataPagamento;
	
	// Quando o Status for CANCELADO
	/*
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_CANCELAMENTO", nullable = true)
	private Calendar dataCancelamento;
	*/
	// Atributo para a somatória dos valores dos itens
	@Column(name = "VALOR_TOTAL", nullable = false, scale = 2, precision = 10)
	private BigDecimal valorTotal;
	
	// STATUS - PENDENTE, PAGO, CANCELADO
	@Enumerated(EnumType.STRING) //@Column(name = "STATUS", nullable = false) NAO FUNCIONOU com o @Column
	private StatusCompra status;
	
	public enum StatusCompra{
		PENDENTE,
		PAGO,
		// CANCELADO
	}
	
	// UM PEDIDO TEM UMA LISTA DE ITENS	- o atributo mappedBy indica o relacionamento bidirecional	
	// @OneToMany(mappedBy = "pedidoCompra") BIDIRECIONAL
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)	// UNIDIRECIONAL - onde o JoinColumn gera uma chave estrangeira na tabela ITEM_PRODUTO, senão geraria uma tabela intermediária, por padrão, um JoinTable
	@JoinColumn(name = "FK_PEDIDO_COMPRA")
	private List<ItemPedido> itensPedidoCompra;	
	
	// UNIDIRECIONAL
	@ManyToOne
	@JoinColumn(name = "FK_FORNECEDOR", nullable = false)
	private Fornecedor fornecedor;	
	
	// Construtor Padrão
	public PedidoCompra(){}

	public long getnPedido() {
		return nPedido;
	}

	public void setnPedido(long nPedido) {
		this.nPedido = nPedido;
	}

	public Calendar getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Calendar dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Calendar getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Calendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	/*
	public Calendar getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Calendar dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	*/
	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public StatusCompra getStatusCompra() {
		return status;
	}

	public void setStatusCompra(StatusCompra status) {
		this.status = status;
	}

	public List<ItemPedido> getItensPedidoCompra() {
		return itensPedidoCompra;
	}

	public void setItensPedidoCompra(List<ItemPedido> itensPedidoCompra) {
		this.itensPedidoCompra = itensPedidoCompra;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}	
}
