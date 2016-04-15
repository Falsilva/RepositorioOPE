package br.com.mdjpapeis.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
/*
@Entity
@Table(name = "TIPO_FORNECEDOR", schema = "MDJPAPEIS")
@SequenceGenerator(name = "GERADOR_DE_SEQUENCIA", sequenceName = "GERADOR_DE_SEQUENCIA", allocationSize=1)*/
public class TipoFornecedor implements Serializable{
	/*
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_DE_SEQUENCIA")
	@Column(name = "ID", nullable = false)*/
	private int id;
	
	//@Column(name = "TIPO", nullable = false)
	private String tipo;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@JoinColumn(name = "CODIGO_FORNECEDOR")
	//@OneToMany(mappedBy = "tipo")
	private List<Fornecedor> fornecedores;

	public TipoFornecedor(){
		
	}
	
	public TipoFornecedor(String tipo){
		this.tipo = tipo;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}
}

