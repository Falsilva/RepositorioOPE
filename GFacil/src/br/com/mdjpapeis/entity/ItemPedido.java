package br.com.mdjpapeis.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_PEDIDO", schema = "MDJPAPEIS")
@SequenceGenerator(name = "SEQ_ITEM_PEDIDO", sequenceName = "SEQ_ITEM_PEDIDO", allocationSize=1)
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM_PEDIDO")
	@Column(name = "CODIGO", nullable = false)
	private long codigo;
	
	@Column(name = "PESO", nullable = false, scale = 3, precision = 11)
	private BigDecimal peso;
	
	@Column(name = "VALOR_ITEM", nullable = false, scale = 2, precision = 10)
	private BigDecimal valorItem;	// valor peso x preco unitario do produto
		
	// UNIDIRECIONAL
	@OneToOne
	@JoinColumn(name = "FK_PRODUTO", nullable = false)
	private Produto produto;
	
	// Construtor Padrão
	public ItemPedido(){}
	
	public ItemPedido(Produto produto, BigDecimal peso, BigDecimal preco){
		this.produto = produto;
		this.peso = peso;
		this.valorItem = peso.multiply(preco);		
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getValorItem() {
		return valorItem;
	}

	public void setValorItem(BigDecimal valorItem) {
		this.valorItem = valorItem;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
