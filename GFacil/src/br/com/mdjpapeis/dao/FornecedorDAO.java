package br.com.mdjpapeis.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.mdjpapeis.entity.Cliente;
import br.com.mdjpapeis.entity.Fornecedor;

public class FornecedorDAO implements GenericoDAO<Fornecedor>{

	// CADASTRA O FORNECEDORES
	@Override
	public void inserir(Fornecedor fornecedor) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(fornecedor);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();
	}

	// ATUALIZA O FORNECEDORES
	@Override
	public void atualizar(Fornecedor fornecedor) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		entityManager.merge(fornecedor);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();
	}

	// EXCLUI O FORNECEDOR
	@Override
	public void excluir(Fornecedor fornecedor) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o fornecedor managed (gerenciável) pelo entityManager, necessário para usar o método remove a seguir
			fornecedor = entityManager.getReference(Fornecedor.class, fornecedor.getCodigo());
			
			entityManager.remove(fornecedor);		
			entityManager.getTransaction().commit();		
			entityManager.close();		
		}catch(IllegalArgumentException ex){
			System.out.println("FornecedorDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);
		
		}catch(PersistenceException ex){
			System.out.println("FornecedorDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);
			
		}finally{
					
			conexao.close();
		}

	}

	// LISTA OS FORNECEDORES
	@Override
	public List<Fornecedor> listar() throws PersistenceException {
		
		List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT F FROM Fornecedor F";
			EntityManager entityManager = conexao.createEntityManager();		
			fornecedores = entityManager.createQuery(queryJPQL).getResultList();
			
			if(fornecedores.size() == 0){
				fornecedores = null;
			}
			
			entityManager.close();
		
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return fornecedores;
	}

	// BUSCA O FORNECEDOR PELO CÓDIGO
	public Fornecedor buscaFornecedorPorCodigo(Fornecedor fornecedor){
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		Fornecedor forn = null;
		
		try{
			
			EntityManager entityManager = conexao.createEntityManager();						
			forn = entityManager.find(Fornecedor.class, fornecedor.getCodigo());			
			entityManager.close();
			
		}catch(IllegalArgumentException ex){
			
			System.out.println("Fornecedor não encontrado");
			ex.printStackTrace();
			
		}finally{			
			conexao.close();			
		}
		
		return forn;
	}
	
	// BUSCA O FORNECEDOR POR CNPJ
	public Fornecedor buscaFornecedorPorCNPJ(Fornecedor fornecedor){
			
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		List<Fornecedor> fornecedores = null;
		Fornecedor forn = null;
		try{
					
			EntityManager entityManager = conexao.createEntityManager();
					
			if(fornecedor.getCnpj() != null & !fornecedor.getCnpj().isEmpty()){
					
				String queryJPQL =	"SELECT F FROM Fornecedor F WHERE F.cnpj LIKE :cnpj";
				Query query = entityManager.createQuery(queryJPQL);
				query.setParameter("cnpj", fornecedor.getCnpj());
				fornecedores = (List<Fornecedor>)query.getResultList();
					
				if(fornecedores.size() != 0){
					for(Fornecedor forn2 : fornecedores){
						forn = forn2;
					}
				}
			}
				
			entityManager.close();
					
		}catch(IllegalArgumentException ex){					
			ex.printStackTrace();					
		}finally{					
			conexao.close();			
		}
		
		return forn;
	}
	
	// BUSCA FORNECEDOR PELA INSCRIÇÃO ESTADUAL
	public Fornecedor buscaFornecedorPorInscEstadual(Fornecedor fornecedor) {
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		List<Fornecedor> fornecedores = null;
		Fornecedor forn = null;
		try{					
			EntityManager entityManager = conexao.createEntityManager();					
			if(fornecedor.getInscEstadual() != null & !fornecedor.getInscEstadual().isEmpty()){					
				String queryJPQL =	"SELECT F FROM Fornecedor F WHERE F.inscEstadual LIKE :inscEstadual";
				Query query = entityManager.createQuery(queryJPQL);
				query.setParameter("inscEstadual", fornecedor.getInscEstadual());
				fornecedores = (List<Fornecedor>)query.getResultList();					
				if(fornecedores.size() != 0){
					for(Fornecedor forn2 : fornecedores){
						forn = forn2;
					}
				}
			}				
			entityManager.close();
					
		}catch(IllegalArgumentException ex){					
			ex.printStackTrace();					
		}finally{					
			conexao.close();			
			return forn;
		}
	}
		
	// LISTA FORNECEDORES PELA EMPRESA
	public List<Fornecedor> buscaFornecedorPorEmpresa(Fornecedor fornecedor){
				
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		List<Fornecedor> fornecedores = null;
				
		try{
					
			EntityManager entityManager = conexao.createEntityManager();
					
			if(fornecedor.getEmpresa() != null & !fornecedor.getEmpresa().isEmpty()){
				String queryJPQL =	"SELECT F FROM Fornecedor F WHERE F.empresa LIKE :empresa";
				Query query = entityManager.createQuery(queryJPQL);
				query.setParameter("empresa", fornecedor.getEmpresa() + "%");
				fornecedores = query.getResultList();
				
				if(fornecedores.size() == 0){
					fornecedores = null;
				}
			}
				
			entityManager.close();
					
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();					
		}finally{					
			conexao.close();			
		}
		
		return fornecedores;
	}
}
