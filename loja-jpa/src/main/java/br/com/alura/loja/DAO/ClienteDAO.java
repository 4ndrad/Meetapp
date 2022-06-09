package br.com.alura.loja.DAO;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Cliente;

public class ClienteDAO {
	private EntityManager em;
	
	public ClienteDAO(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void cadastrar(Cliente cliente) {
		this.em.persist(cliente);
	}

	public Cliente buscarPorID(long id) {
		return em.find(Cliente.class, id);
	}
}
