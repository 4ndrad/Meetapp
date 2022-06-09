package br.com.alura.loja.teste;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import br.com.alura.loja.DAO.CategoriaDAO;
import br.com.alura.loja.DAO.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class TesteCriteria {
	public static void main(String[] args) {
		popularBancoDedados();
		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		
		produtoDao.buscarPorParametrosComCriteria("PS5", null, null);
	}

	private static void popularBancoDedados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Iphone X", "Celular da Apple", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("1500"), videogames);
		Produto mac = new Produto("Mac", "Macbook pro Apple", new BigDecimal("2000"), informatica);
		
		EntityManager em = JPAUtil.getEntityManager();

		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		
		// como ta em RESOURCE_LOCAL, precisa disso para delimitar uma transação
		em.getTransaction().begin();

		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(mac);

		// commit
		em.getTransaction().commit();
		em.close();
	}
}
