package br.com.mdjpapeis.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.mdjpapeis.entity.Cliente;
import br.com.mdjpapeis.entity.Usuario;

public class ClienteDAO implements GenericoDAO<Cliente>{

	// CADASTRA O CLIENTE
	@Override
	public void inserir(Cliente cliente) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		entityManager.close();		
		conexao.close();
	}

	// ATUALIZA O CLIENTE
	@Override
	public void atualizar(Cliente cliente) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		EntityManager entityManager =  conexao.createEntityManager();		
		entityManager.getTransaction().begin();		
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();		
		entityManager.close();		
		conexao.close();
	}

	// EXCLUI O CLIENTE
	@Override
	public void excluir(Cliente cliente) throws PersistenceException {
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
			
		try{
			EntityManager entityManager = conexao.createEntityManager();		
			entityManager.getTransaction().begin();
			
			// Tornando o cliente gerenciável pelo entityManager, necessário para usar o método remove a seguir
			cliente = entityManager.find(Cliente.class, cliente.getCodigo());
			
			entityManager.remove(cliente);		
			entityManager.getTransaction().commit();
			entityManager.close();
		
		}catch(IllegalArgumentException ex){
			System.out.println("ClienteDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);
		
		}catch(PersistenceException ex){
			System.out.println("ClienteDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);
			
		}finally{
					
			conexao.close();
		}
	}

	// LISTA OS CLIENTES
	@Override
	public List<Cliente> listar() throws PersistenceException {		
		
		List<Cliente> clientes = null;
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		
		try{
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL =	"SELECT C FROM Cliente C";
			EntityManager entityManager = conexao.createEntityManager();
			clientes = entityManager.createQuery(queryJPQL).getResultList();
			
			if(clientes.size() == 0){
				clientes = null;
			}
			
			entityManager.close();
			
		}catch(PersistenceException ex){
			ex.printStackTrace();
		}finally{
			conexao.close();
		}
		return clientes;
	}

	// BUSCA O CLIENTE PELO CÓDIGO
	public Cliente buscaClientePorCodigo(Cliente cliente){
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		Cliente cli = null;
		
		try{
			
			EntityManager entityManager = conexao.createEntityManager();
			cli = entityManager.find(Cliente.class, cliente.getCodigo());			
			entityManager.close();
			
		}catch(IllegalArgumentException ex){
						
			ex.printStackTrace();
			
		}finally{			
			conexao.close();
		}	
		
		return cli;
	}
	
	// BUSCA O CLIENTE POR CNPJ
	public Cliente buscaClientePorCNPJ(Cliente cliente){
		
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		List<Cliente> clientes = null;
		Cliente cli = null;
		try{
				
			EntityManager entityManager = conexao.createEntityManager();
				
			if(cliente.getCnpj() != null & !cliente.getCnpj().isEmpty()){
				
				String queryJPQL =	"SELECT C FROM Cliente C WHERE C.cnpj LIKE :cnpj";
				Query query = entityManager.createQuery(queryJPQL);
				query.setParameter("cnpj", cliente.getCnpj());
				clientes = (List<Cliente>)query.getResultList();
				
				if(clientes.size() != 0){
					for(Cliente cli2 : clientes){
						cli = cli2;
					}
				}
			}
			
			entityManager.close();
				
		}catch(IllegalArgumentException ex){
				
			ex.printStackTrace();
				
		}finally{
				
			conexao.close();			
			return cli;
		}
	}
	
	// LISTA CLIENTES PELA EMPRESA
	public List<Cliente> buscaClientePorEmpresa(Cliente cliente){
			
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");		
		List<Cliente> clientes = null;
			
		try{
				
			EntityManager entityManager = conexao.createEntityManager();
				
			if(cliente.getEmpresa() != null & !cliente.getEmpresa().isEmpty()){
				String queryJPQL =	"SELECT C FROM Cliente C WHERE C.empresa LIKE :empresa";
				Query query = entityManager.createQuery(queryJPQL);
				query.setParameter("empresa", cliente.getEmpresa() + "%");
				clientes = query.getResultList();
				if(clientes.size() == 0){
					clientes = null;
				}
			}
			
			entityManager.close();
				
		}catch(IllegalArgumentException ex){
				
			ex.printStackTrace();
				
		}finally{
				
			conexao.close();			
			return clientes;
		}			
	}	
}
