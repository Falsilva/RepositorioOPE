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

@Entity
@Table(name = "PERFIL_USUARIO", schema = "MDJPAPEIS")
@SequenceGenerator(name = "GERADOR_DE_SEQUENCIA", sequenceName = "GERADOR_DE_SEQUENCIA", allocationSize=1)
public class PerfilUsuario implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_DE_SEQUENCIA")
	@Column(name = "ID", nullable = false)
	private int id;
	
	@Column(name = "PERFIL")
	private String perfil;
	
	//@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	//@JoinColumn(name = "LOGIN_USUARIO")	// @JoinColumn indica qual será a coluna utilizada para identificação
	@OneToMany(mappedBy = "perfil")	
	private List<Usuario> usuarios;
	
	public PerfilUsuario(){
		
	}
	
	public PerfilUsuario(String perfil){
		this.perfil = perfil;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public List<Usuario> getUsuario() {
		return usuarios;
	}
	
	public void setUsuario(List<Usuario> usuario) {
		this.usuarios = usuario;
	}

	
}
