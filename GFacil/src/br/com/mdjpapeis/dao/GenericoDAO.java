package br.com.mdjpapeis.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.PersistenceException;

public interface GenericoDAO<E> extends Serializable{

	public void inserir(E e) throws PersistenceException;
	
	public void atualizar(E e) throws PersistenceException;
	
	public void excluir(E e) throws PersistenceException;
	
	public List<E> listar() throws PersistenceException;
}
