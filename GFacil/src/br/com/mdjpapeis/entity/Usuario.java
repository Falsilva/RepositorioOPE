package br.com.mdjpapeis.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO", schema = "MDJPAPEIS")
public class Usuario implements Serializable {
	
	@Id	
	@Column(name = "LOGIN", length = 100, nullable = false)
	private String login;
	
	@Column(name = "SENHA", length = 100, nullable = false)
	private String senha;
	
	@Column(name = "NOME", length = 100, nullable = false)
	private String nome;	
	
	@Column(name = "EMAIL", length = 100, unique = true, nullable = false)
	private String email;
	
	/*@ManyToOne(cascade=CascadeType.PERSIST, optional = false)	// O nome da coluna gerado no banco de dados é PERFIL_ID e LOGIN_USUARIO
	@JoinColumn(name = "FK_ID_PERFIL_USUARIO", nullable = false)*/
	
	@Enumerated(EnumType.STRING)
	private Perfil perfil;
	
	public enum Perfil{
		ADMINISTRADOR,
		COMPRADOR,
		VENDEDOR
	}
	
	public Usuario(){
		
	}
	
	public Usuario(String login, String senha){
		this.login = login;
		this.senha = senha;
	}

	public Usuario(String login, String senha, String nome, String email, Perfil perfil){
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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}
