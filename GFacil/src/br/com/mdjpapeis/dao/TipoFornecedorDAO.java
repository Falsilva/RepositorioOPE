package br.com.mdjpapeis.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import br.com.mdjpapeis.entity.TipoFornecedor;

public class TipoFornecedorDAO implements GenericoDAO<TipoFornecedor> {

	// CADASTRA O TIPO DE FORNECEDOR
	@Override
	public void inserir(TipoFornecedor tipoFornecedor) throws PersistenceException {

		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(tipoFornecedor);
		entityManager.getTransaction().commit();
		entityManager.close();
		conexao.close();

	}

	// ATUALIZA O TIPO DE FORNECEDOR
	@Override
	public void atualizar(TipoFornecedor tipoFornecedor) throws PersistenceException {

		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");
		EntityManager entityManager = conexao.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(tipoFornecedor);
		entityManager.getTransaction().commit();
		entityManager.close();
		conexao.close();
	}

	// EXCLUI O TIPO DE FORNECEDOR
	@Override
	public void excluir(TipoFornecedor tipoFornecedor) throws PersistenceException {

		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");

		try {
			EntityManager entityManager = conexao.createEntityManager();
			entityManager.getTransaction().begin();

			// Tornando o tipo gerenciável pelo entityManager, necessário para usar o método remove a seguir
			tipoFornecedor = entityManager.find(TipoFornecedor.class, tipoFornecedor.getId());

			entityManager.remove(tipoFornecedor);
			entityManager.getTransaction().commit();
			entityManager.close();

		} catch (IllegalArgumentException ex) {
			System.out
					.println("TipoFornecedorDAO - CATCH IllegalArgumentException");
			ex.printStackTrace();
			throw new PersistenceException(ex);

		} catch (PersistenceException ex) {
			System.out.println("TipoFornecedorDAO - CATCH PersistenceException");
			ex.printStackTrace();
			throw new PersistenceException(ex);

		} finally {

			conexao.close();
		}
	}

	// LISTA OS TIPOS DE FORNECEDORES
	@Override
	public List<TipoFornecedor> listar() throws PersistenceException {

		List<TipoFornecedor> tipos = null;
		EntityManagerFactory conexao = Persistence.createEntityManagerFactory("MDJPapeisPU");

		try {
			// Query JPQL (Trabalha com Classes e Objetos Java)
			String queryJPQL = "SELECT TF FROM TipoFornecedor TF";
			EntityManager entityManager = conexao.createEntityManager();
			tipos = entityManager.createQuery(queryJPQL).getResultList();

			if (tipos.size() == 0) {
				tipos = null;
			}

			entityManager.close();

		} catch (PersistenceException ex) {
			ex.printStackTrace();
		} finally {
			conexao.close();
		}
		return tipos;
	}

}
