package br.com.mdjpapeis.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.mdjpapeis.entity.Movimentacao;
import br.com.mdjpapeis.entity.PedidoCompra;

public class MovimentacaoDAO implements GenericoDAO<Movimentacao> {

	@Override
	public void inserir(Movimentacao movimentacao) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(movimentacao);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();
	}

	@Override
	public void atualizar(Movimentacao movimentacao) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		entityManager.merge(movimentacao);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();
	}

	@Override
	public void excluir(Movimentacao movimentacao) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o caixa gerenciável pelo entityManager, necessário para usar o método remove a seguir
			movimentacao = entityManager.find(Movimentacao.class, movimentacao.getnLancamento());
			
			entityManager.remove(movimentacao);		
			entityManager.getTransaction().commit();
			entityManager.close();		
		}catch(IllegalArgumentException ex){
			System.out.println("MovimentacaoDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);		
		}catch(PersistenceException ex){
			System.out.println("MovimentacaoDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);			
		}finally{					
			conexao.close();
		}
	}

	public void excluirMovimentacaoCompra(PedidoCompra pedidoCompra) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		List<Movimentacao> movimentacoes = null;
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			String queryJPQL =	"SELECT M FROM Movimentacao M WHERE M.pedidoCompra = :pedidoCompra";
			
			Query query = entityManager.createQuery(queryJPQL);
			query.setParameter("pedidoCompra", pedidoCompra);
			movimentacoes = (List<Movimentacao>)query.getResultList();
			
			if(movimentacoes.size() != 0){
				for(Movimentacao mov : movimentacoes){
					if(mov.getPedidoCompra().getnPedido() == pedidoCompra.getnPedido())
						entityManager.remove(mov);
				}
			}
			
			// Tornando o caixa gerenciável pelo entityManager, necessário para usar o método remove a seguir
			//movimentacao = entityManager.find(Movimentacao.class, nPedidoCompra);
			
			//entityManager.remove(movimentacao);		
			entityManager.getTransaction().commit();
			entityManager.close();		
		}catch(IllegalArgumentException ex){
			System.out.println("MovimentacaoDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);		
		}catch(PersistenceException ex){
			System.out.println("MovimentacaoDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);			
		}finally{					
			conexao.close();
		}
	}
	
	@Override
	public List<Movimentacao> listar() throws PersistenceException {
		List<Movimentacao> movimentacoes = new ArrayList();
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT M FROM Movimentacao M";		
			EntityManager entityManager = conexao.createEntityManager();		
			movimentacoes = entityManager.createQuery(queryJPQL).getResultList();			
			if(movimentacoes.size() == 0){
				movimentacoes = null;
			}			
			entityManager.close();			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return movimentacoes;
	}
		
	public List<Movimentacao> listarPorMes(LocalDate dataInicial, LocalDate dataFinal) throws PersistenceException {
				
		List<Movimentacao> movimentacoes = new ArrayList();
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT M FROM Movimentacao M WHERE M.data BETWEEN :datainicial AND :datafinal";		
			EntityManager entityManager = conexao.createEntityManager();
			entityManager.setProperty("datainicial", dataInicial);
			entityManager.setProperty("datafinal", dataFinal);
			movimentacoes = entityManager.createQuery(queryJPQL).getResultList();			
			if(movimentacoes.size() == 0){
				movimentacoes = null;
			}			
			entityManager.close();			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return movimentacoes;
	}

	public Movimentacao buscaPorPedidoCompra(PedidoCompra pedidoCompra) {
		Movimentacao movimentacao = null;
		List<Movimentacao> movimentacoes = new ArrayList();
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT M FROM Movimentacao M WHERE M.pedidoCompra = :pedidoCompra";		
			EntityManager entityManager = conexao.createEntityManager();
			
			System.out.println("MovimentacaoDAO - buscaPorPedidoCompra - CHEGOU ATE AQUI!");
			
			Query query = entityManager.createQuery(queryJPQL);
			query.setParameter("pedidoCompra", pedidoCompra);
			movimentacoes = (List<Movimentacao>)query.getResultList();		
			
			
			if(movimentacoes.size() == 1){
				System.out.println("ACHOU MOVIMENTACAO POR PEDIDO");
				movimentacao = movimentacoes.get(0);
			}
			entityManager.close();			
		}catch(PersistenceException ex){
			movimentacao = null;
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return movimentacao;
	}
}
