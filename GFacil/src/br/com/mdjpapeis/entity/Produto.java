package br.com.mdjpapeis.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUTO", schema = "MDJPAPEIS")
@SequenceGenerator(name = "GERADOR_DE_SEQUENCIA", sequenceName = "GERADOR_DE_SEQUENCIA", allocationSize=1)
public class Produto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_DE_SEQUENCIA")
	@Column(name = "CODIGO", nullable = false)
	private long codigo;	
	
	@Column(name = "PRODUTO", nullable = false)
	private String produto;
	
	@Column(name = "PRECO_COMPRA", nullable = false, scale = 2, precision = 10)
	private BigDecimal precoCompra;
	
	@Column(name = "PRECO_VENDA", nullable = false, scale = 2, precision = 10)
	private BigDecimal precoVenda;
	
	// Construtor Padrão
	public Produto(){}
	
	// Construtor utilizado para ItemProduto
	public Produto(long codigoProduto){
		this.codigo = codigoProduto;
	}
	
	public Produto(String produto, BigDecimal precoCompra, BigDecimal precoVenda){
		this.produto = produto;
		this.precoCompra = precoCompra;
		this.precoVenda = precoVenda;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(BigDecimal precoCompra) {
		this.precoCompra = precoCompra;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}
}
