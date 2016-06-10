package br.com.mdjpapeis.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import br.com.mdjpapeis.entity.ItemPedido;

public class ItemPedidoDAO implements GenericoDAO<ItemPedido> {

	// CADASTRA UM ITEM DE PRODUTO
	@Override
	public void inserir(ItemPedido itemPedido) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(itemPedido);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();
	}

	// ATUALIZA UM ITEM DE PRODUTO
	@Override
	public void atualizar(ItemPedido itemPedido) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		entityManager.merge(itemPedido);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();		
	}

	// EXCLUI UM ITEM DE PRODUTO
	@Override
	public void excluir(ItemPedido itemPedido) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o item gerenciável pelo entityManager, necessário para usar o método remove a seguir
			itemPedido = entityManager.find(ItemPedido.class, itemPedido.getCodigo());
			
			entityManager.remove(itemPedido);		
			entityManager.getTransaction().commit();
			entityManager.close();		
		}catch(IllegalArgumentException ex){
			System.out.println("ItemPedidoDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);		
		}catch(PersistenceException ex){
			System.out.println("ItemPedidoDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);			
		}finally{					
			conexao.close();
		}
	}

	// LISTA OS ITENS DE PRODUTOS
	@Override
	public List<ItemPedido> listar() throws PersistenceException {
		List<ItemPedido> itensPedidos = new ArrayList();
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT IP FROM ItemPedido IP";		
			EntityManager entityManager = conexao.createEntityManager();		
			itensPedidos = entityManager.createQuery(queryJPQL).getResultList();			
			if(itensPedidos.size() == 0){
				itensPedidos = null;
			}			
			entityManager.close();			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}		
		return itensPedidos;
	}	
}
