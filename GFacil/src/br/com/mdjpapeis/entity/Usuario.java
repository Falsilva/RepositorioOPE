package br.com.mdjpapeis.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO", schema = "MDJPAPEIS")
public class Usuario implements Serializable {
	
	@Id
	@Column(name = "LOGIN", nullable = false)
	private String login;
	
	@Column(name = "SENHA", nullable = false)
	private String senha;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "EMAIL", unique = true, nullable = false)
	private String email;
		
	//@ManyToOne(cascade=CascadeType.ALL, optional = false)	// O nome da coluna gerado no banco de dados � PERFIL_ID e LOGIN_USUARIO
	//@JoinColumn(name = "PERFIL_ID")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PERFIL_ID")
	private PerfilUsuario perfil;
	
	public Usuario(){
		
	}
	
	public Usuario(String login, String senha){
		this.login = login;
		this.senha = senha;
	}

	public Usuario(String login, String senha, String nome, String email, PerfilUsuario perfil){
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.perfil = perfil;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PerfilUsuario getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilUsuario perfil) {
		this.perfil = perfil;
	}

}
