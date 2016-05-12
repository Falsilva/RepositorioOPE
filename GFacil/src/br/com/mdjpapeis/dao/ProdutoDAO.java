package br.com.mdjpapeis.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import br.com.mdjpapeis.entity.Produto;

public class ProdutoDAO implements GenericoDAO<Produto> {

	// CADASTRA PRODUTO
	@Override
	public void inserir(Produto produto) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(produto);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();		
	}

	// ATUALIZA O PRODUTO
	@Override
	public void atualizar(Produto produto) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		entityManager.merge(produto);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();
	}

	// EXCLUI O PRODUTO
	@Override
	public void excluir(Produto produto) throws PersistenceException {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o produto gerenciável pelo entityManager, necessário para usar o método remove a seguir
			produto = entityManager.find(Produto.class, produto.getCodigo());
			
			entityManager.remove(produto);		
			entityManager.getTransaction().commit();
			entityManager.close();		
		}catch(IllegalArgumentException ex){
			System.out.println("ProdutoDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);		
		}catch(PersistenceException ex){
			System.out.println("ProdutoDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);			
		}finally{					
			conexao.close();
		}
	}

	// LISTA OS PRODUTOS
	@Override
	public List<Produto> listar() throws PersistenceException {
		List<Produto> produtos = null;
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT P FROM Produto P";
			EntityManager entityManager = conexao.createEntityManager();
			produtos = entityManager.createQuery(queryJPQL).getResultList();			
			if(produtos.size() == 0){
				produtos = null;
			}			
			entityManager.close();			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return produtos;
	}
	
	// BUSCA O PRODUTO PELO CÓDIGO
	public Produto buscaProdutoPorCodigo(Produto produto){			
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		Produto prod = null;			
		try{				
			EntityManager entityManager = conexao.createEntityManager();
			prod = entityManager.find(Produto.class, produto.getCodigo());			
			entityManager.close();				
		}catch(IllegalArgumentException ex){							
			ex.printStackTrace();				
		}finally{			
			conexao.close();
		}				
		return prod;
	}
	
	// LISTA PRODUTOS PELO PRODUTO
	public List<Produto> listaProdutosPorProduto(Produto produto){				
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		List<Produto> produtos = null;				
		try{					
			EntityManager entityManager = conexao.createEntityManager();					
			if(produto.getProduto() != null & !produto.getProduto().isEmpty()){
				String queryJPQL =	"SELECT P FROM Produto P WHERE P.produto LIKE :produto";
				Query query = entityManager.createQuery(queryJPQL);
				query.setParameter("produto", produto.getProduto() + "%");
				produtos = query.getResultList();
				if(produtos.size() == 0){
					produtos = null;
				}
			}				
			entityManager.close();					
		}catch(IllegalArgumentException ex){					
			ex.printStackTrace();					
		}finally{					
			conexao.close();			
			return produtos;
		}			
	}
	
	// BUSCA O PRODUTO PELO PRODUTO
	public Produto buscaProdutoPorProduto(Produto produto){				
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		List<Produto> produtos = null;		
		try{					
			EntityManager entityManager = conexao.createEntityManager();					
			if(produto.getProduto() != null & !produto.getProduto().isEmpty()){
				String queryJPQL =	"SELECT P FROM Produto P WHERE P.produto LIKE :produto";
				Query query = entityManager.createQuery(queryJPQL);
				query.setParameter("produto", produto.getProduto());
				produtos = query.getResultList();
				if(produtos.size() == 0){
					produto = null;
				}
			}				
			entityManager.close();					
		}catch(IllegalArgumentException ex){					
			ex.printStackTrace();					
		}finally{					
			conexao.close();			
			return produto;
		}			
	}
}
