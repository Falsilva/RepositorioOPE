package br.com.mdjpapeis.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.mdjpapeis.entity.Usuario;

public class UsuarioDAO implements GenericoDAO<Usuario> {

	// CADASTRA O USUÁRIO
	@Override
	public void inserir(Usuario usuario) throws PersistenceException {

		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();
	}

	// ATUALIZA O USUÁRIO
	@Override
	public void atualizar(Usuario usuario) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		entityManager.merge(usuario);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();
	}

	// EXCLUI O USUÁRIO
	@Override
	public void excluir(Usuario usuario) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o usuário gerenciável pelo entityManager, necessário para usar o método remove a seguir
			usuario = entityManager.find(Usuario.class, usuario.getLogin());
			
			entityManager.remove(usuario);		
			entityManager.getTransaction().commit();
			entityManager.close();
		
		}catch(IllegalArgumentException ex){
			System.out.println("UsuarioDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);
		
		}catch(PersistenceException ex){
			System.out.println("UsuarioDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);
			
		}finally{
					
			conexao.close();
		}
	}

	// LISTA OS USUÁRIOS
	@Override
	public List<Usuario> listar() throws PersistenceException {
		
		List<Usuario> usuarios = new ArrayList();
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT U FROM Usuario U";		
			EntityManager entityManager = conexao.createEntityManager();		
			usuarios = entityManager.createQuery(queryJPQL).getResultList();
			
			if(usuarios.size() == 0){
				usuarios = null;
			}
			
			entityManager.close();
			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}		
		
		return usuarios;
	}

	// BUSCA O USUÁRIO PELO LOGIN
	public Usuario buscaUsuarioPorLogin(Usuario usuario){
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
				
		Usuario user = null;
		
		try{
			
			EntityManager entityManager = conexao.createEntityManager();						
			user = entityManager.find(Usuario.class, usuario.getLogin());			
			entityManager.close();
			
		}catch(IllegalArgumentException ex){
			
			ex.printStackTrace();
			
		}finally{
			
			conexao.close();			
			return user;
		}		
	}
	
	// BUSCA O USUÁRIO PELO EMAIL
	public Usuario buscaUsuarioPorEmail(Usuario usuario){
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
				
		Usuario user = null;
		
		try{
			
			EntityManager entityManager = conexao.createEntityManager();			
			
			String queryJPQL =	"SELECT U FROM Usuario U WHERE U.email = :email";
			
			Query query = entityManager.createQuery(queryJPQL);
			query.setParameter("email", usuario.getEmail());
			
			List<Usuario> usuarios = query.getResultList();
			for(Usuario u : usuarios){
				user = u;
			}					
			
			entityManager.close();
			
		}catch(IllegalArgumentException ex){		
			
			ex.printStackTrace();
			
		}finally{
			
			conexao.close();			
			return user;
		}		
	}
	
	// LISTA USUÁRIOS PELO NOME
	public List<Usuario> buscaUsuarioPorNome(Usuario usuario){
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		List<Usuario> users = null;
		
		try{
			
			EntityManager entityManager = conexao.createEntityManager();
			
			if(usuario.getNome() != null & !usuario.getNome().isEmpty()){
				String queryJPQL =	"SELECT U FROM Usuario U WHERE U.nome LIKE :nome";
				Query query = entityManager.createQuery(queryJPQL);
				query.setParameter("nome", usuario.getNome() + "%");
				users = query.getResultList();
				if(users.size() == 0){
					users = null;
				}
			}
			
			entityManager.close();
			
		}catch(IllegalArgumentException ex){
			
			ex.printStackTrace();
			
		}finally{
			
			conexao.close();			
			return users;
		}
		
	}
	
}
