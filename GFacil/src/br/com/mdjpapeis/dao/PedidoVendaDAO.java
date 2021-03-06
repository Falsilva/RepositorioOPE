package br.com.mdjpapeis.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.mdjpapeis.entity.PedidoCompra;
import br.com.mdjpapeis.entity.PedidoVenda;
import br.com.mdjpapeis.entity.PedidoVenda.StatusVenda;

public class PedidoVendaDAO implements GenericoDAO<PedidoVenda> {

	// CADASTRA UM PEDIDO DE VENDA
	@Override
	public void inserir(PedidoVenda pedidoVenda) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(pedidoVenda);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();
	}

	// ATUALIZA UM PEDIDO DE VENDA
	@Override
	public void atualizar(PedidoVenda pedidoVenda) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		
		PedidoVenda ped = entityManager.find(PedidoVenda.class, pedidoVenda.getnPedido());
		ped.setDataAbertura(pedidoVenda.getDataAbertura());		
		if(pedidoVenda.getDataPagamento() != null && !pedidoVenda.getDataPagamento().equals("")){
			System.out.println("DATA PGTO NO DAO: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoVenda.getDataPagamento().getTime()));
			ped.setDataPagamento(pedidoVenda.getDataPagamento());
		}
		ped.setCliente(pedidoVenda.getCliente());
		ped.setItensPedidoVenda(pedidoVenda.getItensPedidoVenda());
		ped.setStatusVenda(pedidoVenda.getStatusVenda());
		ped.setValorTotal(pedidoVenda.getValorTotal());
		
		entityManager.merge(pedidoVenda);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();
	}

	// EXCLUI UM PEDIDO DE VENDA
	@Override
	public void excluir(PedidoVenda pedidoVenda) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o pedido gerenciável pelo entityManager, necessário para usar o método remove a seguir
			pedidoVenda = entityManager.find(PedidoVenda.class, pedidoVenda.getnPedido());
			
			entityManager.remove(pedidoVenda);		
			entityManager.getTransaction().commit();
			entityManager.close();		
		}catch(IllegalArgumentException ex){
			System.out.println("PedidoVendaDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);		
		}catch(PersistenceException ex){
			System.out.println("PedidoVendaDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);			
		}finally{					
			conexao.close();
		}
	}

	// LISTA OS PEDIDOS DE VENDAS
	@Override
	public List<PedidoVenda> listar() throws PersistenceException {
		List<PedidoVenda> pedidosVendas = new ArrayList();
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT PV FROM PedidoVenda PV";		
			EntityManager entityManager = conexao.createEntityManager();		
			pedidosVendas = entityManager.createQuery(queryJPQL).getResultList();			
			if(pedidosVendas.size() == 0){
				pedidosVendas = null;
			}			
			entityManager.close();			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return pedidosVendas;
	}
	
	public PedidoVenda buscaPedidoVendaPorNumeroPedido(long codigo){
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
				
		PedidoVenda pedidoVenda = null;
		
		try{
			
			EntityManager entityManager = conexao.createEntityManager();						
			pedidoVenda = entityManager.find(PedidoVenda.class, codigo);			
			entityManager.close();
			
		}catch(IllegalArgumentException ex){
			
			ex.printStackTrace();
			
		}finally{
			
			conexao.close();			
			return pedidoVenda;
		}		
	}

	public List<PedidoVenda> listaUltimosPendentes(StatusVenda status) {
		List<PedidoVenda> pedidosVendas = null;
		List<PedidoVenda> pedVendas = null;
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT PV FROM PedidoVenda PV WHERE PV.status = :status ORDER BY PV.nPedido DESC";			
			EntityManager entityManager = conexao.createEntityManager();

			Query query = entityManager.createQuery(queryJPQL);
			//query.setFirstResult(startPosition);
			query.setMaxResults(5);
			query.setParameter("status", status);
			pedVendas = (List<PedidoVenda>)query.getResultList();			
			
			if(pedVendas.size() != 0){
				pedidosVendas = new ArrayList<PedidoVenda>();
				if(pedVendas.size() != 1){
					for(int i = pedVendas.size() - 1; i >= 0; i--){
						pedidosVendas.add(pedVendas.get(i));
					}
				}else{
					pedidosVendas = pedVendas;
				}
			}					
				
			entityManager.close();			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return pedidosVendas;
	}
}
