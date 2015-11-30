package br.com.mdjpapeis.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import br.com.mdjpapeis.entity.Cliente;
import br.com.mdjpapeis.entity.PerfilUsuario;

public class PerfilUsuarioDAO implements GenericoDAO<PerfilUsuario>{

	// CADASTRA O PERFIL DE USUÁRIO
	@Override
	public void inserir(PerfilUsuario perfilUsuario) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(perfilUsuario);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();
		
	}

	// ATUALIZA O PERFIL DE USUÁRIO
	@Override
	public void atualizar(PerfilUsuario perfilUsuario) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		entityManager.merge(perfilUsuario);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();
	}

	// EXCLUI O PERFIL DE USUÁRIO
	@Override
	public void excluir(PerfilUsuario perfilUsuario) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o perfil gerenciável pelo entityManager, necessário para usar o método remove a seguir
			perfilUsuario = entityManager.find(PerfilUsuario.class, perfilUsuario.getId());
			
			entityManager.remove(perfilUsuario);		
			entityManager.getTransaction().commit();
			entityManager.close();
		
		}catch(IllegalArgumentException ex){
			System.out.println("PerfilUsuarioDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);
		
		}catch(PersistenceException ex){
			System.out.println("PerfilUsuarioDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);
			
		}finally{
					
			conexao.close();
		}
	}

	// LISTA OS PERFIS DE USUÁRIOS
	@Override
	public List<PerfilUsuario> listar() throws PersistenceException {
		
		List<PerfilUsuario> perfis = null;
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT PU FROM PerfilUsuario PU";
			EntityManager entityManager = conexao.createEntityManager();
			perfis = entityManager.createQuery(queryJPQL).getResultList();
			
			if(perfis.size() == 0){
				perfis = null;
			}
			
			entityManager.close();
			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return perfis;
	}

}
