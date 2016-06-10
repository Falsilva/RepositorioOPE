package br.com.mdjpapeis.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import br.com.mdjpapeis.entity.PedidoCompra;
import br.com.mdjpapeis.entity.Usuario;

public class PedidoCompraDAO implements GenericoDAO<PedidoCompra> {

	// CADASTRA UM PEDIDO DE COMPRA
	@Override
	public void inserir(PedidoCompra pedidoCompra) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(pedidoCompra);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();
	}

	// ATUALIZA UM PEDIDO DE COMPRA
	@Override
	public void atualizar(PedidoCompra pedidoCompra) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		
		PedidoCompra ped = entityManager.find(PedidoCompra.class, pedidoCompra.getnPedido());
		ped.setDataAbertura(pedidoCompra.getDataAbertura());		
		if(pedidoCompra.getDataPagamento() != null && !pedidoCompra.getDataPagamento().equals("")){
			System.out.println("DATA PGTO NO DAO: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoCompra.getDataPagamento().getTime()));
			ped.setDataPagamento(pedidoCompra.getDataPagamento());
		}
		ped.setFornecedor(pedidoCompra.getFornecedor());
		ped.setItensPedidoCompra(pedidoCompra.getItensPedidoCompra());
		ped.setStatusCompra(pedidoCompra.getStatusCompra());
		ped.setValorTotal(pedidoCompra.getValorTotal());
		
		entityManager.merge(ped);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();
	}

	// EXCLUI UM PEDIDO DE COMPRA
	@Override
	public void excluir(PedidoCompra pedidoCompra) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o pedido gerenciável pelo entityManager, necessário para usar o método remove a seguir
			pedidoCompra = entityManager.find(PedidoCompra.class, pedidoCompra.getnPedido());
			
			entityManager.remove(pedidoCompra);		
			entityManager.getTransaction().commit();
			entityManager.close();		
		}catch(IllegalArgumentException ex){
			System.out.println("PedidoCompraDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);		
		}catch(PersistenceException ex){
			System.out.println("PedidoCompraDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);			
		}finally{					
			conexao.close();
		}
	}

	// LISTA OS UM PEDIDOS DE COMPRAS
	@Override
	public List<PedidoCompra> listar() throws PersistenceException {
		List<PedidoCompra> pedidosCompras = new ArrayList();
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT PC FROM PedidoCompra PC";		
			EntityManager entityManager = conexao.createEntityManager();		
			pedidosCompras = entityManager.createQuery(queryJPQL).getResultList();			
			if(pedidosCompras.size() == 0){
				pedidosCompras = null;
			}			
			entityManager.close();			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return pedidosCompras;
	}
	
	public PedidoCompra buscaPedidoCompraPorNumeroPedido(long nPedido){
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
				
		PedidoCompra pedidoCompra = null;
		
		try{
			
			EntityManager entityManager = conexao.createEntityManager();						
			pedidoCompra = entityManager.find(PedidoCompra.class, nPedido);			
			entityManager.close();
			
		}catch(IllegalArgumentException ex){
			
			ex.printStackTrace();
			
		}finally{
			
			conexao.close();			
			return pedidoCompra;
		}		
	}
}
