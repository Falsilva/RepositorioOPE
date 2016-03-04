package br.com.mdjpapeis.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FORNECEDOR", schema = "MDJPAPEIS")
@SequenceGenerator(name = "GERADOR_DE_SEQUENCIA", sequenceName = "GERADOR_DE_SEQUENCIA", allocationSize=1)
public class Fornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_DE_SEQUENCIA")
	@Column(name = "CODIGO", nullable = false)
	private long codigo;	
		
	@Column(name = "EMPRESA", nullable = true)
	private String empresa;
	
	@Column(name = "CONTATO", nullable = false)
	private String contato;
	
	@Column(name = "TELEFONE", nullable = true)
	private String telefone;
	
	@Column(name = "EMAIL", nullable = true)
	private String email;
	
	@Column(name = "ENDERECO", nullable = true)
	private String endereco;
	
	@Column(name = "CNPJ", nullable = true)
	private String cnpj;
	
	@Column(name = "INSC_ESTADUAL", nullable = true)
	private String inscEstadual;
	
	//@ManyToOne(cascade = CascadeType.ALL, optional = false)
	//@JoinColumn(name = "TIPO_FORNECEDOR_ID")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPO_FORNECEDOR_ID")
	private TipoFornecedor tipo;
	
	public Fornecedor(){
		
	}
	
	public Fornecedor(String contato, String telefone, String email){
		this.contato = contato;
		this.telefone = telefone;
		this.email = email;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getInscEstadual() {
		return inscEstadual;
	}

	public void setInscEstadual(String inscEstadual) {
		this.inscEstadual = inscEstadual;
	}

	public TipoFornecedor getTipo() {
		return tipo;
	}

	public void setTipo(TipoFornecedor tipo) {
		this.tipo = tipo;
	}
}
