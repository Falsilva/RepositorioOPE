package br.com.mdjpapeis.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import br.com.mdjpapeis.entity.Caixa;
import br.com.mdjpapeis.entity.PedidoCompra;

public class CaixaDAO implements GenericoDAO<Caixa> {

	// CADASTRA UM CAIXA
	@Override
	public void inserir(Caixa caixa) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(caixa);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();
	}

	// ATUALIZA UM CAIXA
	@Override
	public void atualizar(Caixa caixa) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		entityManager.merge(caixa);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();
	}

	// EXCLUI UM CAIXA
	@Override
	public void excluir(Caixa caixa) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o caixa gerenciável pelo entityManager, necessário para usar o método remove a seguir
			caixa = entityManager.find(Caixa.class, caixa.getCodigo());
			
			entityManager.remove(caixa);		
			entityManager.getTransaction().commit();
			entityManager.close();		
		}catch(IllegalArgumentException ex){
			System.out.println("CaixaDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);		
		}catch(PersistenceException ex){
			System.out.println("CaixaDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);			
		}finally{					
			conexao.close();
		}
	}

	// LISTA OS CAIXAS
	@Override
	public List<Caixa> listar() throws PersistenceException {
		List<Caixa> caixas = new ArrayList();
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT C FROM Caixa C";		
			EntityManager entityManager = conexao.createEntityManager();		
			caixas = entityManager.createQuery(queryJPQL).getResultList();			
			if(caixas.size() == 0){
				caixas = null;
			}			
			entityManager.close();			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return caixas;
	}
	
	public Caixa buscaCaixaPorCodigo(long codigo){
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");				
		Caixa caixa = null;		
		try{			
			EntityManager entityManager = conexao.createEntityManager();						
			caixa = entityManager.find(Caixa.class, codigo);			
			entityManager.close();			
		}catch(IllegalArgumentException ex){			
			ex.printStackTrace();			
		}finally{			
			conexao.close();			
			return caixa;
		}		
	}
	
}
