package br.com.mdjpapeis.entity;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PEDIDO_VENDA", schema = "MDJPAPEIS")
@SequenceGenerator(name = "GERADOR_DE_SEQUENCIA", sequenceName = "GERADOR_DE_SEQUENCIA", allocationSize=1)
public class PedidoVenda {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_DE_SEQUENCIA")
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
	@Enumerated(EnumType.STRING)
	private StatusVenda status;
	
	public enum StatusVenda{
		PENDENTE,
		PAGO,
		// CANCELADO
	}	
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)	// UNIDIRECIONAL - onde o JoinColumn gera uma chave estrangeira na tabela ITEM_PRODUTO, senão geraria uma tabela intermediária, por padrão, um JoinTable
	@JoinColumn(name = "FK_PEDIDO_VENDA")
	private List<ItemPedido> itensPedidoVenda;
	
	// UNIDIRECIONAL - O LADO MANY É O DONO DO RELACIONAMENTO, SIGNIFICA O LADO EM QUE A CHAVE ESTRANGEIRA FICA
	@ManyToOne
	@JoinColumn(name = "FK_CLIENTE", nullable = false)
	private Cliente cliente;
	
	// Construtor Padrão
	public PedidoVenda(){}

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

	public StatusVenda getStatusVenda() {
		return status;
	}

	public void setStatusVenda(StatusVenda status) {
		this.status = status;
	}

	public List<ItemPedido> getItensPedidoVenda() {
		return itensPedidoVenda;
	}

	public void setItensPedidoVenda(List<ItemPedido> itensPedidoVenda) {
		this.itensPedidoVenda = itensPedidoVenda;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
